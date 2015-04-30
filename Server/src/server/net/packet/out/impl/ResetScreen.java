package server.net.packet.out.impl;

import server.model.players.Player;
import server.net.packet.out.DispatchPacket;

public class ResetScreen implements DispatchPacket {

	@Override
	public void sendPacket(Player player) {
		if (player.getOutStream() != null && player != null) {
			player.getOutStream().createFrame(107);
			player.flushOutStream();
		}

	}

}
