package network.server.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna Class that implements a
 *         SocketServer. Accepts incoming connection, instantiates and starts a
 *         SocketHandler for each connection.
 */
public class SocketServer implements Runnable {

	private int port;
	private String address;
	private ServerSocket server;
	private boolean listening;
	private String status;
	private boolean stop;
	private List<SocketHandler> handlers;


	/**
	 * basic constructor.
	 * listens on localhost at port 200
	 */
	public SocketServer() {
		this.port = 2000;
		this.address = "localhost";
		listening = false;
		status = "Created";
		handlers = new Vector<SocketHandler>();
	}

	/**
	 * @return the status of the server
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return gets the number of the port. (2000)
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 
	 * @return the address of the server "localhost"
	 */
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * makes the server listen and accepts the incoming connections
	 * 
	 * @throws IOException
	 */
	private void startListening() throws IOException {
		if (!listening) {
			server = new ServerSocket(port);
			status = "Listening";
			listening = true;
			while (listening) {

				Socket socket = server.accept();
				SocketHandler newHandler = new SocketHandler(socket);
				handlers.add(newHandler);
				newHandler.start();
				if(stop)
					listening=false;
			}
		}
	}

	/**
	 * stops the server and interrupts the listening loop.
	 */
	public void stop(){
		stop=true;
	}
	/**
	 * Method that stops the server from listening. It closes all the open
	 * sockets
	 * 
	 * @throws IOException
	 *             if some socket cannot be closed.
	 */
	public void endListening() throws IOException {
		if (listening) {
			listening = false;
			for (SocketHandler openSocketHandler : handlers)
				openSocketHandler.Close();

			server.close();
			status = "Closed.";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			startListening();
		} catch (IOException e) {
			Logger.getGlobal().log(Level.ALL, "error.", e);
		}
	}
}
