/**
 * 
 */
package network.client;

import java.rmi.RemoteException;

import network.client.rmi.RMIConnection;
import network.client.socket.SocketConnection;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class ConnectionFactory {
	private String scelta;
	/*
	public ConnectionFactory(String scelta) {
		this.scelta=scelta;
	}
	*/
	
	/**
	 * takes a param that is the chosen connection
	 * @param scelta
	 * @return an interface that is the one chosen by the client at the beginning
	 * @throws RemoteException
	 */
	public static ClientNetworkInterface setConnection(String scelta) throws RemoteException{
		ClientNetworkInterface temp=null;
		if("1".equals(scelta)){
			temp = new RMIConnection();
		}
		if("2".equals(scelta)){
			temp = new SocketConnection();
		}
		return temp;
	}
}
