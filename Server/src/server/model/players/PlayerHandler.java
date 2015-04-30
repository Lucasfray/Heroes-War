package server.model.players;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerHandler {
	
	private List<Player> players = new CopyOnWriteArrayList<Player>();
	
	private static PlayerHandler getSingleton;
	
	public static PlayerHandler getSingleton() {
		if (getSingleton == null)
			getSingleton = new PlayerHandler();
		
		return getSingleton;
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public List<Player> getPlayersOnline() {
		return players;
	}
}
