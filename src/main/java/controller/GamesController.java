/**
 * 
 */
package controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Const;

/**
 * the class that handle all games starting and continuity!
 * this is the main component that controls all game basics
 * @author Emanuele Chioso, Lorenzo Della Penna
 *
 */
public class GamesController {

	private static GamesController instance;

	private static List<ControllerInterface> runningGames;
	private static int numOfGame;
	private static int numOfClients;
	private static List<String> clients;

	/**
	 * 
	 */
	private GamesController() {
		runningGames = new ArrayList<>();
		clients = new ArrayList<String>();
		numOfGame = 0;
		numOfClients = 0;
		try {
			runningGames.add(new GameHandler());
		} catch (RemoteException e) {
			Logger.getGlobal().log(Level.ALL, "Error. Could not instanciate the Game Handler", e);
		}
	}


	/**
	 * @return the incoming game
	 * @throws RemoteException
	 * @throws InterruptedException
	 */
	public synchronized ControllerInterface getIncomingGame(String nome) throws RemoteException, InterruptedException {

		ControllerInterface gameHandler = runningGames.get(numOfGame);
		numOfClients++;
		clients.add(nome);
		if (numOfClients != 2) {
			System.out.println("uno");
			wait();
		} else {
			System.out.println("due");
			scheduleIncomingGame();
			wait();
		}

		// System.out.println("numOfGames = "+ numOfGame +"\nrunningGames =
		// "+runningGames.toString() +" size "+runningGames.size());

		return gameHandler;
	}

	/**
	 * @throws RemoteException
	 */
	private synchronized void scheduleIncomingGame() throws RemoteException {
		System.out.println("partita avviata tra 20 sec");
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public synchronized void run() {
				try {

					System.out.println("partita avviata");
					runningGames.get(numOfGame).avviaPartita(clients);
					numOfClients = 0;
					clients = new ArrayList<String>();
					numOfGame++;
					addGameController(new GameHandler());
					GamesController.getInstance().notifyThem();
				} catch (RemoteException e) {
					Logger.getGlobal().log(Level.ALL, "Error.", e);
				}
			}
		}, Const.GAMESTARTTIME);

	}

	protected synchronized void notifyThem() {
		notifyAll();
	}


	/**
	 * Singleton pattern
	 * 
	 * @return the instance of the class
	 */
	public  static synchronized GamesController getInstance() {
		if (instance == null)
			instance = new GamesController();
		return instance;
	}

	/**
	 * @param numOfGame
	 * @return the controllerInterface at the specified ID
	 * @throws RemoteException
	 */
	public ControllerInterface getGameHandler(int numOfGame) throws RemoteException {
		return runningGames.get(numOfGame);

	}

	/**
	 * @param gameHandler
	 */
	public static void addGameController(ControllerInterface gameHandler) {
		runningGames.add(gameHandler);
	}

	/**
	 * @return the number of games
	 */
	public static int getNumOfGame() {
		return numOfGame;
	}

	/**
	 * @param numOfGame
	 */
	public static void setNumOfGame(int numOfGame) {
		GamesController.numOfGame = numOfGame;
	}

	/**
	 * @return the number of connected clients
	 */
	public static int getNumOfClients() {
		return numOfClients;
	}

	/**
	 * @param numOfClients
	 */
	public static void setNumOfClients(int numOfClients) {
		GamesController.numOfClients = numOfClients;
	}

}