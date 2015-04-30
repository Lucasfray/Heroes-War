package server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;

import server.cores.CoresManager;
import server.net.PipelineFactory;
import server.net.packet.PacketHandler;

public class Server {
	
	static final Logger logger = Logger.getLogger(Server.class.getSimpleName());
	
	private static final int GAME_PORT = 43594;
	
	public static String SERVER_NAME = "RuneScape";
	
	private static Server getSingleton;
	
	public static Server getSingleton() {
		if (getSingleton == null)
			getSingleton = new Server();
		
		return getSingleton;
	}
	
	public static void main(String[] args) {
		Server.getSingleton().start();
	}
	
	private void start() {
		
		loadServerData();
		
		ServerCleaner.getSingleton().execute();
		
		GameLogic.getSingleton().execute();
		
		startGameNetwork();
	}
	
	private void startGameNetwork() {
		ServerBootstrap serverBootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		serverBootstrap.setPipelineFactory(new PipelineFactory(new HashedWheelTimer()));
		serverBootstrap.bind(new InetSocketAddress(GAME_PORT));
	}
	
	private void loadServerData() {
		CoresManager.init();
		PacketHandler.getSingleton().loadPackets();
	}
}
