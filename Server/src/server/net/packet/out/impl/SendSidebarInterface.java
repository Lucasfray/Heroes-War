package server.net.packet.out.impl;

import server.model.players.Player;
import server.net.packet.out.DispatchPacket;

public class SendSidebarInterface implements DispatchPacket {

	private int menuId = -1, form = -1;
	
	public SendSidebarInterface(int menuId, int form) {
		this.menuId = menuId;
		this.form = form;
	}
	
	@Override
	public void sendPacket(Player player) {
		if (player.getOutStream() != null) {
			player.getOutStream().createFrame(71);
			player.getOutStream().writeWord(form);
			player.getOutStream().writeByteA(menuId);
		}

	}

}
