package server.model.players;

import server.Server;
import server.net.packet.out.impl.Message;
import server.net.packet.out.impl.ResetScreen;
import server.net.packet.out.impl.SendSidebarInterface;
import server.net.packet.out.impl.SendText;

public class PlayerLoadHandler {

	private static PlayerLoadHandler getSingleton;
	
	public static PlayerLoadHandler getSingleton() {
		if (getSingleton == null)
			getSingleton = new PlayerLoadHandler();
		
		return getSingleton;
	}
	
	public void loginPlayer(Player player) {
		
		player.getOutStream().createFrame(249);
		player.getOutStream().writeByteA(1);
		player.getOutStream().writeWordBigEndianA(0);
		
		player.send(new ResetScreen());
		
		player.send(new Message("Welcome to " + Server.SERVER_NAME + "."));
		
		player.send(new SendSidebarInterface(1, 3917));
		player.send(new SendSidebarInterface(2, 638));
		player.send(new SendSidebarInterface(3, 3213));
		player.send(new SendSidebarInterface(4, 1644));
		player.send(new SendSidebarInterface(5, 5608));
		player.send(new SendSidebarInterface(6, 1151));
		player.send(new SendSidebarInterface(7, 18128));
		player.send(new SendSidebarInterface(8, 5065));
		player.send(new SendSidebarInterface(9, 5715));
		player.send(new SendSidebarInterface(10, 2449));
		player.send(new SendSidebarInterface(11, 904));
		player.send(new SendSidebarInterface(12, 147));
		player.send(new SendSidebarInterface(13, -1));
		player.send(new SendSidebarInterface(0, 2423));
		
		player.send(new SendText(player.runEnergy + "%", 149));
	}
	
}
