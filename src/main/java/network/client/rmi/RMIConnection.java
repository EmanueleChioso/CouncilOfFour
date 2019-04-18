/**
 * 
 */
package network.client.rmi;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.ControllerInterface;
import network.client.ClientNetworkInterface;
import network.common.RMIServerInterface;
import view.UserInterface;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna
 *
 */
public class RMIConnection implements Serializable, ClientNetworkInterface {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3255472712586144795L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see network.server.NetworkInterface#connect()
	 */
	private static final int PORT = 3000;

	private transient RMIServerInterface game;
	private boolean registryNotLocated= true;

	// private RMIClientInterface callableClient;
	private transient Registry registry;

	private String registryname = "COF";

	// private Server server = Server.getInstance();

	/* (non-Javadoc)
	 * @see network.client.ClientNetworkInterface#connect(java.lang.String, view.UserInterface)
	 */
	@Override
	public ControllerInterface connect(String name, UserInterface ui) throws InterruptedException {
		ControllerInterface controller = null;

		try {
			registry = LocateRegistry.getRegistry(PORT);
			game = (RMIServerInterface) registry.lookup(registryname);

			// callableClient = new RMICallableClient(name, ui);
			controller = game.login(name);
			// remote.disconnect(userName, this);
		} catch (RemoteException | NotBoundException e) {

			Logger.getGlobal().log(Level.ALL, "Error.", e);
			return null;
		}
		return controller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see network.server.NetworkInterface#disconnect(java.lang.String)
	 */
	@Override
	public void disconnect(String name) {
		System.out.println("You were kicked from the server, please reconnect!");
		game = null;

	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see network.client.ClientNetworkInterface#checkName(java.lang.String)
	 */
	@Override
	public boolean checkName(String name) {
		boolean ret = false;

		try {
			if (registryNotLocated) {
				registry = LocateRegistry.getRegistry(PORT);
				game = (RMIServerInterface) registry.lookup(registryname);
				registryNotLocated=false;
			}

			ret = game.checkIfNameIsTaken(name);

		} catch (RemoteException | NotBoundException e) {
			Logger.getGlobal().log(Level.ALL, "Error.", e);
		}

		return ret;
	}

}
