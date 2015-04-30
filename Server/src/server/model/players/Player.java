package server.model.players;

import java.util.LinkedList;
import java.util.Queue;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;

import server.net.packet.Packet;
import server.net.packet.Packet.Type;
import server.net.packet.PacketHandler;
import server.net.packet.out.DispatchPacket;
import server.util.Stream;

public class Player {
	
	private boolean connected = false;
	private Channel channel;
	private int packetType = -1;
	private int packetSize = 0;
	
	private Queue<Packet> queuedPackets = new LinkedList<Packet>();
	
	public void queuePacket(Packet message) {
		synchronized (queuedPackets) {
			queuedPackets.add(message);
		}		
	}
	
	public boolean processQueuedPackets() {
		synchronized (queuedPackets) {
			Packet p = null;
			while ((p = queuedPackets.poll()) != null) {
				inStream.currentOffset = 0;
				packetType = p.getOpcode();
				packetSize = p.getLength();
				inStream.buffer = p.getPayload().array();
				if (packetType > 0) {
					PacketHandler.getSingleton().processPacket(this, packetType, packetSize);
				}
			}
		}
		return true;
	}
	
	public String playerName;
	public String playerPass;
	public int playerRights;
	public int playerId;
	private Stream inStream;
	private Stream outStream;
	
	public int runEnergy = 100;
	
	public Player(Channel channel, int playerId) {
		this.playerId = playerId;
		this.playerRights = 0;
		
		this.channel = channel;
		
		outStream = new Stream(new byte[Stream.BUFFER_SIZE]);
		outStream.currentOffset = 0;

		inStream = new Stream(new byte[Stream.BUFFER_SIZE]);
		inStream.currentOffset = 0;
		
		PlayerHandler.getSingleton().addPlayer(this);
	}
	
	public void flushOutStream() {
		if (!channel.isConnected() || outStream.currentOffset == 0) {
			return;
		}
		byte[] temp = new byte[outStream.currentOffset];
		System.arraycopy(outStream.buffer, 0, temp, 0, temp.length);
		Packet packet = new Packet(-1, Type.FIXED, ChannelBuffers.wrappedBuffer(temp));
		channel.write(packet);
		outStream.currentOffset = 0;
	}
	
	public void send(DispatchPacket dispatch) {
		if (dispatch instanceof DispatchPacket)
			dispatch.sendPacket(this);
	}
	
	public Stream getInStream() {
		return inStream;
	}

	public void setInStream(Stream inStream) {
		this.inStream = inStream;
	}

	public Stream getOutStream() {
		return outStream;
	}

	public void setOutStream(Stream outStream) {
		this.outStream = outStream;
	}
	public boolean isConnected() {
		return connected;
	}
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
}