package server;

import java.util.concurrent.TimeUnit;

import server.cores.CoresManager;
import server.model.players.Player;
import server.model.players.PlayerHandler;

public class GameLogic {

	private static GameLogic getSingleton;
	
	public static GameLogic getSingleton() {
		if (getSingleton == null)
			getSingleton = new GameLogic();
		
		return getSingleton;
	}
	
	public void execute() {	
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				playerLogic();
			}
		}, 600, 600, TimeUnit.MILLISECONDS);
	}
	
	private void playerLogic() {
		for (Player players: PlayerHandler.getSingleton().getPlayersOnline()) {
			players.processQueuedPackets();
		}
	}
	
}
