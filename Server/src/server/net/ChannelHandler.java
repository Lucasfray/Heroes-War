package server.net;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import server.model.players.Player;
import server.net.packet.Packet;

public class ChannelHandler extends SimpleChannelHandler {
	private Session session;
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent msg) throws Exception {
		if (msg.getMessage() instanceof Player) {
			session.setPlayer((Player) msg.getMessage());
		} else if (msg.getMessage() instanceof Packet) {
			if (session.getPlayer() != null) {
				session.getPlayer().queuePacket((Packet) msg.getMessage());
			}
		}
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
		if (session == null) {
			session = new Session(ctx.getChannel());
		}
	}
	
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		if (session != null) {
			Player Player = session.getPlayer();
			if (Player != null) {
				Player.setConnected(false);
			}
			session.close();
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		ctx.getChannel().close();
	}

}
