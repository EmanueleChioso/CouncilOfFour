package network.client;

import controller.ControllerInterface;
import view.UserInterface;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public interface ClientNetworkInterface {

	/**
	 * method used to connect the client using his name
	 * the name will be unique. 
	 * @param name
	 * @param ui
	 * @return RMIGameController
	 * @throws InterruptedException
	 */
	public ControllerInterface connect(String name, UserInterface ui) throws InterruptedException;
	
	/**
	 * @param name
	 * @return true is name is taken
	 */
	public boolean checkName(String name);

	/**
	 * disconnects a client
	 * @param name
	 */
	public void disconnect(String name);
	

}
