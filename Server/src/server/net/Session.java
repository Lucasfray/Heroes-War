package server.net;

import org.jboss.netty.channel.Channel;

import server.model.players.Player;

public class Session {
	private Channel channel;
	private Player player;
	
	public Session(Channel channel) {
		this.channel = channel;
	}
	
	public void close() {
		channel.close();
	}

	public Channel getChannel() {
		return channel;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player Player) {
		this.player = Player;
	}
}
