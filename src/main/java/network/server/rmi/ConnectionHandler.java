package network.server.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.ControllerInterface;
import controller.GamesController;
import network.common.RMIServerInterface;
import network.server.Server;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class ConnectionHandler extends UnicastRemoteObject implements RMIServerInterface {

	private static final long serialVersionUID = 4628100136428881373L;

	public ConnectionHandler() throws RemoteException {
		super();
	}

	/* (non-Javadoc)
	 * @see network.common.RMIServerInterface#login(java.lang.String)
	 */
	@Override
	public ControllerInterface login(String name) throws RemoteException {
		Logger.getGlobal().info("client "+name+" connected using an RMI connection.");
		 
		System.out.println(name + " got connected...");
		Server.addClientName(name);
		GamesController gm = GamesController.getInstance();
		ControllerInterface controller = null;
		try {
			controller = gm.getIncomingGame(name);
		} catch (InterruptedException e) {

			Logger.getGlobal().log(Level.ALL, "failed  GETINCOMINGGAME. game is confused, so confused to hit himself. CLOSING THREAD", e);
			 Thread.currentThread().interrupt();
		}
		return controller;
	}

	/* (non-Javadoc)
	 * @see network.common.RMIServerInterface#checkIfNameIsTaken(java.lang.String)
	 */
	@Override
	public boolean checkIfNameIsTaken(String name) throws RemoteException {
		for (String aName : Server.getConnectedClients())
			if (aName.equalsIgnoreCase(name))
				return true;
		return false;
	}

}
