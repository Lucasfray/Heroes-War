package server.net.packet;

import java.util.HashMap;
import java.util.Map;

import server.model.players.Player;
import server.net.packet.in.RecievePacket;
import server.net.packet.in.impl.Chat;
import server.net.packet.in.impl.FocusChange;
import server.net.packet.in.impl.Idle;
import server.net.packet.in.impl.MouseClick;
import server.net.packet.in.impl.MoveCamera;
import server.net.packet.in.impl.RegionLoaded;

public class PacketHandler {
	
	private Map<Integer, RecievePacket> dispatch = new HashMap<Integer, RecievePacket>();
	
	private static PacketHandler getSingleton;
	
	public static PacketHandler getSingleton() {
		if (getSingleton == null)
			getSingleton = new PacketHandler();
		
		return getSingleton;
	}
	
	public void loadPackets() {
		
		long startTime = System.currentTimeMillis();
		
			dispatch.put(3, new FocusChange());
			dispatch.put(0, new Idle());
			dispatch.put(86, new MoveCamera());
			dispatch.put(121, new RegionLoaded());
			dispatch.put(241, new MouseClick());
			dispatch.put(4, new Chat());
			
		long endTime = System.currentTimeMillis();
			
	    long elapsed = endTime-startTime;
	    
	    System.err.println("Loaded packets in " + elapsed + "ms.");
	}
	
	public void processPacket(Player player, int packetType, int packetSize) {
		if (!(packetType >= 0 && packetType <= 253))
			return;
		
		RecievePacket send = dispatch.get(packetType);
		
		if (send != null)
		send.recievePacket(player, packetType, packetSize);
		
	}
	
	/*private static PacketType packetId[] = new PacketType[256];
	
	static {
		SilentPacket u = new SilentPacket();
		packetId[3] = u;
		packetId[202] = u;
		packetId[77] = u;
		packetId[86] = u;
		packetId[78] = u;
		packetId[36] = u;
		packetId[226] = u;
		packetId[246] = u;
		packetId[148] = u;
		packetId[183] = u;
		packetId[230] = u;
		packetId[136] = u;
		packetId[189] = u;
		packetId[152] = u;
		packetId[200] = u;
		packetId[85] = u;
		packetId[165] = u;
		packetId[238] = u;
		packetId[150] = u;
		
		packetId[241] = u; //clicking in game

		
		packetId[185] = new ClickingButtons();
		
		packetId[103] = new Commands();
	}*/
	
	/*public static void processPacket(Client client, int packetType, int packetSize) {
		if(packetType == -1) {
			return;
		}
		PacketType p = packetId[packetType];
		if(p != null) {
			try {
				System.out.println("packet: " + packetType);
				p.processPacket(client, packetType, packetSize);
			} catch(Exception e) {
					e.printStackTrace();
			}
		} else {
			System.out.println("Unhandled packet type: " + packetType + " - size: " + packetSize);
		}
	}*/
	
}
