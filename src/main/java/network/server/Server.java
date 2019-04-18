package network.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.GamesController;
import network.server.rmi.ConnectionHandler;
import network.server.socket.SocketServer;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class Server {

	private static final int RMIPORT = 3000;
	private static List<String> connectedClients;
	private static ConnectionHandler handler;
	private static Server instance = null;
	private static GamesController gm;


	/**
	 * private constructor to disable the implementation
	 */
	private Server() {
		gm = GamesController.getInstance();
		connectedClients = new ArrayList<>();
	}

	/**
	 * @return the instance of this class, server is a singleton 
	 */
	public synchronized static Server getInstance() {
		if (instance == null) {
			instance = new Server();
		}
		return instance;
	}

	/**
	 * Server publishes his RMI registry, starts listening socket connections (this will never happen)
	 * 
	 * @param args
	 * @throws RemoteException
	 */
	public void startServer() throws RemoteException {

		Logger.getGlobal().info("Starting server!");
		gm = GamesController.getInstance();
		// rmi
		Registry registry = LocateRegistry.createRegistry(RMIPORT);
		handler = new ConnectionHandler();
		registry.rebind("COF", handler);

		// socket
		SocketServer serverSocket = new SocketServer();
		Thread running = new Thread(serverSocket);
		running.start();
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Server ready!");
		while (true) {
			try {
				String string = read.readLine();
				if ("Q".equals(string)) {
					break;
				}
			} catch (IOException e) {
				Logger.getGlobal().log(Level.ALL, "Error.", e);
			}
		}
		System.exit(0);
	}

	/**
	 * @return the main GamesController
	 */
	public static GamesController getGm() {
		return gm;
	}

	/**
	 * @return the connectedClients
	 */
	public static List<String> getConnectedClients() {
		return connectedClients;
	}

	/**
	 * @param connectedClients the connectedClients to set
	 */
	public static void addClientName (String newPlayer) {
		connectedClients.add(newPlayer);
	}
	

	public static void main(String[] args) {

		Server s = Server.getInstance();
		try {
			s.startServer();
		} catch (RemoteException e) {
			Logger.getGlobal().log(Level.ALL, "Error.", e);
		}

	}


}
