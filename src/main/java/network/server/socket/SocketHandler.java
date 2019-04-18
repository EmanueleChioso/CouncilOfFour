package network.server.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.ControllerInterface;
import controller.Game;
import model.cards.PermitCard;
import model.cards.PoliticCard;
import model.map.Council;
import model.map.CouncillorsPool;
import model.map.GameMap;
import model.map.NobilityTrack;
import model.market.Sellable;
import model.player.Player;
import network.common.Command;
import network.server.Server;

public class SocketHandler extends Thread implements ControllerInterface {

	// private static final Command[] basicCommands = { Command.CONNECT,
	// Command.JOINGAME };
	private Socket socket, toClientSocket;
	private BufferedReader in;
	private BufferedReader toClientIn;
	private PrintWriter out;
	private PrintWriter toClientOut;
	private String playerName;
	private boolean stop;
	private Timer timer;
	private Server server;
	private SocketHandler instance = this;

	private String input, output;
	private boolean listening;

	public SocketHandler(Socket socket) {
		super();
		this.socket = socket;
		this.listening = false;

		server = Server.getInstance();
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
		} catch (IOException e) {
			Logger.getGlobal().log(Level.ALL, "Error.", e);
		}
	}

	@Override
	public void run() {
		try {
			listening = true;
			while ((input = in.readLine()) != null && listening) {
				String[] splitted = input.split("&");
				Map<String, String> params = new HashMap<String, String>();
				for (String s : splitted) {
					params.put(s.split("=")[0], s.split("=")[1]);
				}
				output = handleCommand(params);
				out.println(output);
			}
		} catch (IOException e) {
			Logger.getGlobal().log(Level.ALL, "Error.", e);
		}
	}

	private String handleCommand(Map<String, String> param) throws RemoteException {
		// Get the target object and the comm
		String player = param.get("client");
		String comm = param.get("command");
		String ret = "notACommand";

		if (Command.CONNECT.toString().equalsIgnoreCase(comm)) {

			this.playerName = param.get("client");
			ret = "server=" + player + "&command=" + Command.CONNECT;
		} else if (Command.JOINGAME.toString().equalsIgnoreCase(comm)) {
			/*
			 * } else if (Command.END.toString().equalsIgnoreCase(comm)) { }
			 * else if (Command.FASTACTION1.toString().equalsIgnoreCase(comm)) {
			 * } else if (Command.FASTACTION2.toString().equalsIgnoreCase(comm))
			 * { } else if
			 * (Command.FASTACTION3.toString().equalsIgnoreCase(comm)) { } else
			 * if (Command.FASTACTION4.toString().equalsIgnoreCase(comm)) { }
			 * else if
			 * (Command.PRIMARYACTION1.toString().equalsIgnoreCase(comm)) { }
			 * else if
			 * (Command.PRIMARYACTION2.toString().equalsIgnoreCase(comm)) { }
			 * else if
			 * (Command.PRIMARYACTION3.toString().equalsIgnoreCase(comm)) { }
			 * else if
			 * (Command.PRIMARYACTION4.toString().equalsIgnoreCase(comm)) { }
			 * else if (Command.SELLCARD.toString().equalsIgnoreCase(comm)) { }
			 * else if (Command.BUYCARD.toString().equalsIgnoreCase(comm)) { }
			 * else if (Command.BUYASSISTANT.toString().equalsIgnoreCase(comm))
			 * {
			 * 
			 */
		}
		return ret;
	}

	/**
	 * @return
	 */
	private SocketHandler getInstance() {
		if (instance == null)
			instance = this;
		return instance;
	}

	/**
	 * @return socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * Method that cleans the resources used by the SocketHandler.
	 * 
	 * @throws IOException
	 *             if something cannot be closed.
	 */
	public void Close() throws IOException {
		stop = true;
		in.close();
		out.close();
		socket.close();
		if (timer != null)
			timer.cancel();
		if (toClientIn != null)
			toClientIn.close();
		if (toClientOut != null)
			toClientOut.close();
		if (toClientSocket != null)
			toClientSocket.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#passaMarket(java.lang.String)
	 */
	@Override
	public void passaMarket(String name) throws RemoteException {
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#askTurn(java.lang.String)
	 */
	@Override
	public String askTurn(String name) throws RemoteException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#avviaPartita(java.util.List)
	 */
	@Override
	public void avviaPartita(List<String> giocatori) throws RemoteException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#mapSetted()
	 */
	@Override
	public boolean mapSetted() throws RemoteException {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#setMap(model.map.GameMap)
	 */
	@Override
	public void setMap(GameMap map) throws RemoteException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getMap()
	 */
	@Override
	public GameMap getMap() throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getPlayer(java.lang.String)
	 */
	@Override
	public Player getPlayer(String name) throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#update()
	 */
	@Override
	public String update() throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#needToUpdate()
	 */
	@Override
	public boolean needToUpdate() throws RemoteException {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getCounciorsPool()
	 */
	@Override
	public CouncillorsPool getCounciorsPool() throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getAllPlayersStat()
	 */
	@Override
	public List<Player> getAllPlayersStat() throws RemoteException {

		return new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getGame()
	 */
	@Override
	public Game getGame() throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#drawCard(java.lang.String)
	 */
	@Override
	public PoliticCard drawCard(String name) throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#drawCard(java.lang.String, int, int)
	 */
	@Override
	public PermitCard drawCard(String name, int index, int region) throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#firstPrimaryAction(java.lang.String,
	 * int, int, java.util.List)
	 */
	@Override
	public String firstPrimaryAction(String name, int council, int permit, List<Integer> cards) throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#secondPrimaryAction(java.lang.String,
	 * java.util.List, int)
	 */
	@Override
	public String secondPrimaryAction(String name, List<Integer> cards, int destination) throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#thirdPrimaryAction(java.lang.String,
	 * int, int)
	 */
	@Override
	public String thirdPrimaryAction(String name, int council, int councillor) throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#fourthPrimaryAction(java.lang.String,
	 * int, int)
	 */
	@Override
	public String fourthPrimaryAction(String name, int permit, int letter) throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#firstFastAction(java.lang.String)
	 */
	@Override
	public String firstFastAction(String name) throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#secondFastAction(java.lang.String,
	 * int)
	 */
	@Override
	public String secondFastAction(String name, int region) throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#thirdFastAction(java.lang.String,
	 * int, int)
	 */
	@Override
	public String thirdFastAction(String name, int council, int councillor) throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#fourthFastAction(java.lang.String)
	 */
	@Override
	public String fourthFastAction(String name) throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#checkPrimaryAction(java.lang.String)
	 */
	@Override
	public boolean checkPrimaryAction(String name) throws RemoteException {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#checkFastAction(java.lang.String)
	 */
	@Override
	public boolean checkFastAction(String name) throws RemoteException {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getVisiblePermitCards(int)
	 */
	@Override
	public PermitCard[] getVisiblePermitCards(int index) throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#passaTurnoPrimary(java.lang.String)
	 */
	@Override
	public void passaTurnoPrimary(String name) throws RemoteException {
		return;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#passaTurnoSecondary(java.lang.String)
	 */
	@Override
	public void passaTurnoSecondary(String name) throws RemoteException {
		return;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.ControllerInterface#passaTurnoMarketCompra(java.lang.String)
	 */
	@Override
	public String passaTurnoMarketCompra(String name) throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.ControllerInterface#passaTurnoMarketVendi(java.lang.String)
	 */
	@Override
	public String passaTurnoMarketVendi(String name) throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#sellPolitcCards(java.lang.String,
	 * int, int)
	 */
	@Override
	public boolean sellPolitcCards(String name, int politicCards, int cost) throws RemoteException {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#sellPermitCards(java.lang.String,
	 * int, int)
	 */
	@Override
	public boolean sellPermitCards(String name, int permitCards, int cost) throws RemoteException {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#sellAssistants(java.lang.String, int,
	 * int)
	 */
	@Override
	public void sellAssistants(String name, int assistants, int cost) throws RemoteException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getMarketContent()
	 */
	@Override
	public List<Sellable> getMarketContent() throws RemoteException {
		
		return new ArrayList<>();
	}
	/* (non-Javadoc)
	 * @see controller.ControllerInterface#buyItem(java.lang.String, int)
	 */
	@Override
	public String buyItem(String name, int action2) throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#iAmNotAFastAction(java.lang.String)
	 */
	@Override
	public String iAmNotAFastAction(String name) throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getCouncil(int)
	 */
	@Override
	public Council getCouncil(int num) throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getPermitCards(java.lang.String)
	 */
	@Override
	public List<PermitCard> getPermitCards(String name) throws RemoteException {

		return new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getPoliticCards(java.lang.String)
	 */
	@Override
	public List<PoliticCard> getPoliticCards(String name) throws RemoteException {

		return new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getAssistants(java.lang.String)
	 */
	@Override
	public int getAssistants(String name) throws RemoteException {

		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getNobilityTrack()
	 */
	@Override
	public NobilityTrack getNobilityTrack() throws RemoteException {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getNumPlayers()
	 */
	@Override
	public int getNumPlayers() throws RemoteException {

		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getPlayersNames()
	 */
	@Override
	public List<String> getPlayersNames() throws RemoteException {
		
		return new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getMarketItemsOnSale()
	 */
	@Override
	public Set<Sellable> getMarketItemsOnSale() throws RemoteException {

		return new HashSet<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#finalScore()
	 */
	@Override
	public List<Player> finalScore() throws RemoteException {

		return new ArrayList<>();
	}

}
