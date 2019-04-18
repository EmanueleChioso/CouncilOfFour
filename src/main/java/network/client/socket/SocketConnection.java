/**
 * 
 */
package network.client.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.ControllerInterface;
import network.client.Client;
import network.client.ClientNetworkInterface;
import network.common.Command;
import view.UserInterface;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna
 *
 */
public class SocketConnection implements ClientNetworkInterface, Runnable {

	private static final int SOCKETPORT = 2000;

	private boolean listening;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	private String input, output;

	/**
	 * 
	 */
	public SocketConnection() {
		super();
		this.listening = false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		try {
			listening = true;
			while ((input = in.readLine()) != null && listening) {
				System.out.println("[Server]: " + input);
				String[] splitted = input.split("&");
				Map<String, String> params = new HashMap<String, String>();
				for (String s : splitted) {
					params.put(s.split("=")[0], s.split("=")[1]);
				}
				output = handleCommand(params);
				// out.println(output);
				System.out.println("[toServer ]:" + output);
			}
		} catch (IOException e) {
			Logger.getGlobal().log(Level.ALL, "Error.", e);
		}
	}

	private void startListening() {
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public ControllerInterface connect(String name, UserInterface ui) {

		try (Socket socket = new Socket("localhost", SOCKETPORT) ){
			
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			startListening();
		} catch (IOException e) {
			Logger.getGlobal().log(Level.ALL, "Error.", e);
		} finally {

			try {
				socket.close();
			} catch (IOException e) {
				Logger.getGlobal().log(Level.ALL, "error", e);
			}
		}
		return null;
	}

	private String handleCommand(Map<String, String> param) throws RemoteException {
		// Get the target object and the comm
		String player = param.get("server");
		String comm = param.get("command");
		String ret = "notACommand";

		if (Command.CONNECT.toString().equalsIgnoreCase(comm)) {

			ret = "server=" + player + "&command=" + Command.CONNECT;
		} else if (Command.JOINGAME.toString().equalsIgnoreCase(comm)) {

		} else if (Command.END.toString().equalsIgnoreCase(comm)) {
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see network.server.NetworkInterface#disconnect(java.lang.String)
	 */
	@Override
	public void disconnect(String name) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see network.client.ClientNetworkInterface#checkName(java.lang.String)
	 */
	@Override
	public boolean checkName(String name) {
		return false;
	}

}
