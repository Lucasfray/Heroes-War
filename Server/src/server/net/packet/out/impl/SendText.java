package server.net.packet.out.impl;

import server.model.players.Player;
import server.net.packet.out.DispatchPacket;

public class SendText implements DispatchPacket {

	private int id = -1;
	private String text = null;
	
	public SendText(String text, int id) {
		this.text = text;
		this.id = id;
	}
	
	@Override
	public void sendPacket(Player player) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrameVarSizeWord(126);
			player.getOutStream().writeString(text);
			player.getOutStream().writeWordA(id);
			player.getOutStream().endFrameVarSizeWord();
			player.flushOutStream();
		}

	}

}
