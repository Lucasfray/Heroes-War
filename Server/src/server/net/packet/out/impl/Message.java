package server.net.packet.out.impl;

import server.model.players.Player;
import server.net.packet.out.DispatchPacket;

public class Message implements DispatchPacket {

	private String message = null;
	
	public Message(String text) {
		this.message = text;
	}
	
	@Override
	public void sendPacket(Player player) {
		if (player.getOutStream() != null) {
			player.getOutStream().createFrameVarSize(253);
			player.getOutStream().writeString(message);
			player.getOutStream().endFrameVarSize();
		}

	}

}
