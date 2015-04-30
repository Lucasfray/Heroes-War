package server.net.packet.in.impl;

import server.model.players.Player;
import server.net.packet.in.RecievePacket;

public class MoveCamera implements RecievePacket {

	@Override
	public void recievePacket(Player player, int packetType, int packetSize) {
		System.out.println("Camera!");
	}

}
