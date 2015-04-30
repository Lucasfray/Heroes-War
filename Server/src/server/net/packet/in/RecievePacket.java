package server.net.packet.in;

import server.model.players.Player;

public interface RecievePacket {

	public void recievePacket(Player player, int packetType, int packetSize);
	
}
