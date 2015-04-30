package server.net.packet.out;

import server.model.players.Player;

public interface DispatchPacket {

	public void sendPacket(Player player);
	
}
