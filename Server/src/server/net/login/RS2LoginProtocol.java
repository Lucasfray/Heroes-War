package server.net.login;

import java.security.SecureRandom;
import java.util.logging.Logger;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import server.model.players.Player;
import server.model.players.PlayerLoadHandler;
import server.net.packet.PacketBuilder;
import server.util.ISAACCipher;
import server.util.Misc;

public class RS2LoginProtocol extends FrameDecoder {
	protected enum LoginStage {
		CONNECTED(0), LOGGING_IN(1);
		
		private int id;
		
		private LoginStage(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
		
	}
	
	private static final Logger logger = Logger.getLogger(RS2LoginProtocol.class.getSimpleName());
	private LoginStage stage = LoginStage.CONNECTED;
	
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		if(!channel.isConnected()) {
			return null;
		}
		
		switch (stage) {
		case CONNECTED:
			if (buffer.readableBytes() < 2) {
				return null;
			}
			int request = buffer.readUnsignedByte();
			if (request != 14) {
				logger.warning("Invalid login request: " + request);
				channel.close();
				return null;
			}
			buffer.readUnsignedByte();
			channel.write(new PacketBuilder().putLong(0).put((byte) 0).putLong(new SecureRandom().nextLong()).toPacket());
			stage = LoginStage.LOGGING_IN;
			return null;
			
		case LOGGING_IN:
			if (buffer.readableBytes() < 2) {
				return null;
			}
			
			int loginType = buffer.readByte();
			if (loginType != 16 && loginType != 18) {
				logger.warning("Invalid login type: " + loginType);
			}
			
			int blockLength = buffer.readByte() & 0xff;
			if (buffer.readableBytes() < blockLength) {
				return null;
			}
			
			buffer.readByte();
			
			int clientVersion = buffer.readShort();
			if (clientVersion != 317) {
				logger.warning("Invalid client version: " + clientVersion);
				channel.close();
				return null;
			}
			
			buffer.readByte();
			
			for (int i = 0; i < 9; i++) {
				buffer.readInt();
			}
			
			buffer.readByte();
			
			int rsaOpcode = buffer.readByte();
			if (rsaOpcode != 10) {
				logger.warning("Unable to decode RSA block properly!");
				channel.close();
				return null;
			}
			
			long clientHalf = buffer.readLong();
			long serverHalf = buffer.readLong();
			
			int[] seed = { 
					(int) (clientHalf >> 32), (int) clientHalf, (int) (serverHalf >> 32), (int) serverHalf 
			};
			
			ISAACCipher inCipher = new ISAACCipher(seed);
			
			for (int i = 0; i < seed.length; i++) {
				seed[i] += 50;
			}
			
			ISAACCipher outCipher = new ISAACCipher(seed);
			buffer.readInt();
			
			String username = Misc.formatPlayerName(Misc.getRS2String(buffer));
			String password = Misc.getRS2String(buffer);
			
			channel.getPipeline().replace("decoder", "decoder", new RS2Decoder(inCipher));
			
			return login(channel, inCipher, outCipher, username, password);
		}
		
		return null;
	}
	
	private Player login(Channel channel, ISAACCipher inCipher, ISAACCipher outCipher, String username, String password) {
		int returnCode = 2;
		if (!username.matches("[A-Za-z0-9 ]+")) {
			returnCode = 4;
		}
		
		if (username.length() > 12) {
			returnCode = 8;
		}
		
		Player player = new Player(channel, -1);
		player.getOutStream().packetEncryption = outCipher;
		player.getInStream().packetEncryption = inCipher;
		
		if (returnCode == 2) {
			logger.info(username + ":" + password);
			
			PacketBuilder pb = new PacketBuilder();
			pb.put((byte) 2);
			pb.put((byte) player.playerRights);
			pb.put((byte) 0);
			channel.write(pb.toPacket());
			
		}
		
		PlayerLoadHandler.getSingleton().loginPlayer(player);
		
		return player;
	}

}
