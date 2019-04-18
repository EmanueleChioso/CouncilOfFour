package network.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

import controller.ControllerInterface;

public interface RMIServerInterface extends Remote{
	
	/**
	 * the method that makes us connect a client to an incoming game
	 * @param name
	 * @return RMIGameHandler
	 * @throws RemoteException
	 */
	public ControllerInterface login(String name) throws RemoteException;
	
	/**
	 * @param name
	 * @return true if name is taken
	 * @throws RemoteException
	 */
	public boolean checkIfNameIsTaken(String name) throws RemoteException;
	
}
