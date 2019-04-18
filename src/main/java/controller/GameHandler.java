/**
 * 
 */
package controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Const;
import model.cards.PermitCard;
import model.cards.PoliticCard;
import model.map.City;
import model.map.Council;
import model.map.Councillor;
import model.map.CouncillorsPool;
import model.map.GameMap;
import model.map.NobilityTrack;
import model.market.Assistant;
import model.market.Market;
import model.market.Sellable;
import model.player.CardCouncilColor;
import model.player.Player;
import network.common.Command;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna
 *
 */
public class GameHandler extends UnicastRemoteObject implements ControllerInterface {

	/**
	 * private Map<String, SocketHandler> SocketClientsList;
	 */
	private static final long serialVersionUID = -321878527203943835L;

	private List<String> rmiClientsList;

	private List<String> allPlayers;
	private List<String> onlinePlayers;
	private List<String> shuffledList;
	private List<String> listToIterate;
	private Set<String> playersThatPassedMarketTurn;

	private Map<String, Boolean> playerStatus;
	private List<String> toRemove;
	public final long startTime;
	private boolean isEnded;
	private boolean isRunning;
	private boolean marketPhase;

	private boolean needToUpdate = false;
	private String giocatoreAttualeStr;
	private Player giocatoreAttuale;
	private transient Market market;
	private int playersDCThisTurn;
	private int progrGiocatore;
	private int initialPlayers;
	private transient Timer t;
	private String lastPlayer;
	private boolean lastTurn;
	private String toUpdate;
	private GameMap map;
	private Game game;

	/**
	 * i really like this constructor, look how beautifull it is.
	 * 
	 * @param gameID
	 * 
	 */
	public GameHandler() throws RemoteException {
		playersThatPassedMarketTurn = new HashSet<>();
		playersThatPassedMarketTurn = new HashSet<>();
		startTime = System.currentTimeMillis();
		rmiClientsList = new ArrayList<>();
		listToIterate = new ArrayList<>();
		onlinePlayers = new ArrayList<>();
		shuffledList = new ArrayList<>();
		allPlayers = new ArrayList<>();
		playerStatus = new HashMap<>();
		toRemove = new ArrayList<>();
		this.market = new Market();
		this.marketPhase = false;
		this.isRunning = false;
		this.isEnded = false;
		t = new Timer();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#avviaPartita()
	 */
	@Override
	public void avviaPartita(List<String> giocatori) throws RemoteException {
		this.game = new Game();
		this.initialPlayers = giocatori.size();
		rmiClientsList.addAll(giocatori);
		onlinePlayers.addAll(giocatori);
		allPlayers.addAll(giocatori);
		for (String s : giocatori)
			playerStatus.put(s, true);
		game.addPlayers(giocatori);

		setNextTurn();
	}

	private void setNextTurn() {

		marketPhase = false;
		progrGiocatore = 0;
		playersDCThisTurn = 0;
		market.resetMarket();

		int i = 0;
		for (; i < onlinePlayers.size(); i++) {
			if (toRemove.contains(onlinePlayers.get(i)) || toRemove.contains(Const.MARKET + onlinePlayers.get(i))) {
				playerStatus.put(onlinePlayers.get(i), false);
				onlinePlayers.remove(i);
				i--;
			}

		}

		playersThatPassedMarketTurn.clear();
		shuffledList.clear();
		toRemove.clear();
		listToIterate.clear();

		listToIterate.addAll(onlinePlayers);
		listToIterate.add(Const.MARKET);

		shuffledList.addAll(onlinePlayers);
		Collections.shuffle(shuffledList);
		for (String marketShuffledPlayer : shuffledList) {
			listToIterate.add(Const.MARKET + marketShuffledPlayer);
			game.getPlayer(marketShuffledPlayer).resetActions();
			game.getPlayer(marketShuffledPlayer).resetBonuses();
		}

		giocatoreAttualeStr = listToIterate.get(0);
		giocatoreAttuale = game.getPlayer(listToIterate.get(0));
		playerTimer(giocatoreAttualeStr);

	}

	private void playerTimer(String nome) {
		t.cancel();
		t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				Logger.getGlobal().info("[ KICKING PLAYER ]  " + nome);
				try {
					passaTurno(Const.SISTEMA);
				} catch (RemoteException e) {
					Logger.getGlobal().log(Level.ALL, "Error.", e);
				}
			}
		}, Const.PLAYERTIME);
	}

	@Override
	public synchronized void passaMarket(String clientChePassa) throws RemoteException {

		playersThatPassedMarketTurn.add(clientChePassa);

		if (playersThatPassedMarketTurn.size() + playersDCThisTurn >= onlinePlayers.size()) {

			passaTurno(Const.MARKET);
		} else
			while (playersThatPassedMarketTurn.size() + playersDCThisTurn < onlinePlayers.size()) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					Logger.getGlobal().log(Level.ALL, "Error. INTERRUPTED EXCEPTION. closing thread", e);

					Thread.currentThread().interrupt();
				}
			}

	}

	public synchronized void passaTurno(String clientChePassa) throws RemoteException {
		t.cancel();
		if (!giocatoreAttualeStr.equals(clientChePassa) && !clientChePassa.equals(Const.SISTEMA)
				&& !clientChePassa.equals(Const.MARKET)) {

			return;
		}
		needToUpdate = false;

		if (Const.MARKET.equalsIgnoreCase(clientChePassa)) {
			// market is passing turn
			t.cancel();
			for (String s : toRemove) {
				listToIterate.remove(Const.MARKET + s);
			}
			progrGiocatore -= toRemove.size();
			progrGiocatore++;
			marketPhase = true;
			giocatoreAttualeStr = listToIterate.get(progrGiocatore);
			this.notifyAll();
			return;
		}

		else if (Const.SISTEMA.equalsIgnoreCase(clientChePassa)) {
			// gestione lista di rimozione e passaturno automatico
			t.cancel();
			playersDCThisTurn++;
			playerStatus.put(giocatoreAttualeStr, false);
			toRemove.add(giocatoreAttualeStr);
			progrGiocatore++;
			giocatoreAttualeStr = listToIterate.get(progrGiocatore);

			if (Const.MARKET.equals(giocatoreAttualeStr)) {
				marketPhase = true;
			} else {
				playerTimer(giocatoreAttualeStr);
			}

			this.notifyAll();
			return;

		}
		if (onlinePlayers.size() == 0) {
			isEnded = true;
			return;
		}

		giocatoreAttuale = game.getPlayer(giocatoreAttualeStr);

		// questo if e' la fine di un giro completo del gioco. con market e fasi
		// market. va rimesso tutto a zero e roba varia. qui sire inizializza
		// tutto quanto
		if (listToIterate.indexOf(clientChePassa) + 1 >= listToIterate.size()) {

			setNextTurn();

		} else {
			if (!marketPhase) {
				// sono nella fase normale
				if (giocatoreAttuale.getPrimaryActionCount() == 0 && !giocatoreAttuale.checkFastAction()) {
					if (giocatoreAttuale.getName().equals(lastPlayer))
						isEnded = true;
					progrGiocatore++;
					giocatoreAttualeStr = listToIterate.get(progrGiocatore);
					playerTimer(giocatoreAttualeStr);

				}
			} else {
				// sono nella marketPhase
				progrGiocatore++;
				giocatoreAttualeStr = listToIterate.get(progrGiocatore);
			}

		}

		if (Const.MARKET.equals(giocatoreAttualeStr)) {
			marketPhase = true;
			for (String s : toRemove) {
				if (listToIterate.contains(Const.MARKET + s)) {
					listToIterate.remove(Const.MARKET + s);
				}
				playerStatus.put(s, false);
			}

		}

		this.notifyAll();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#askTurn(java.lang.String)
	 */
	@Override
	public synchronized String askTurn(String name) throws RemoteException {

		if (isEnded)
			return "END";

		else if (giocatoreAttualeStr.equalsIgnoreCase(Const.MARKET))
			return "M";
		else if (name.equals(giocatoreAttualeStr))
			return "S";
		else if ((Const.MARKET + name).equals(giocatoreAttualeStr))
			return "Smarket";
		else if (checkDC(name))
			return "DC";
		boolean waiting = true;
		return "N";
	}

	/**
	 * @param name
	 * @return
	 */
	private boolean checkDC(String name) {
		if (playerStatus.containsKey(name) && !playerStatus.get(name))
			return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getMap()
	 */
	@Override
	public boolean mapSetted() throws RemoteException {
		if (this.map == null) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#setMap(model.map.Map)
	 */
	@Override
	public synchronized void setMap(GameMap map) {
		this.map = map;
		game.setMap(map, initialPlayers);

		game.initGame();
		notifyAll();

	}

	public long timeLeft() {
		long ret = 20 - (System.currentTimeMillis() - startTime) / 1000;
		if (ret > 0)
			return ret;
		return 0;
	}

	public synchronized boolean isReady() {
		if ((timeLeft() == 0
				&& rmiClientsList.size() /* + SocketClientsList.size() */ > Const.MIN_PLAYERS)
				|| rmiClientsList.size() /* + SocketClientsList.size() */ == Const.MAX_PLAYERS) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	public boolean isEnded() {
		return this.isEnded;
	}

	/**
	 * 
	 */
	public int getPlayers() {
		return this./* SocketClientsList.size() + this. */rmiClientsList.size();
	}

	public void addPlayer(String client) {
		rmiClientsList.add(client);
	}

	public boolean isRunning() {
		return this.isRunning;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getMap()
	 */
	@Override
	public GameMap getMap() {
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getPlayer(java.lang.String)
	 */
	@Override
	public Player getPlayer(String name) {
		return game.getPlayer(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#update()
	 */
	@Override
	public String update() throws RemoteException {
		return toUpdate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#needToUpdate()
	 */
	@Override
	public boolean needToUpdate() throws RemoteException {
		return this.needToUpdate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getCounciorsPool()
	 */
	@Override
	public CouncillorsPool getCounciorsPool() throws RemoteException {
		return this.game.getCouncilorsPool();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getAllPlayersStat()
	 */
	@Override
	public List<Player> getAllPlayersStat() throws RemoteException {
		return (ArrayList<Player>) game.getAllPlayers();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getGame()
	 */
	@Override
	public Game getGame() throws RemoteException {
		return this.game;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#drawCard()
	 */
	@Override
	public PoliticCard drawCard(String name) throws RemoteException {
		PoliticCard draw = game.getPoliticDeck().draw();
		game.getPlayer(name).addCard(draw);
		return draw;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#drawCard(java.lang.String, int)
	 */
	@Override
	public PermitCard drawCard(String name, int index, int region) throws RemoteException {
		PermitCard chosen = map.getRegion(region).getAndReplaceVisibleCard(index);
		game.getPlayer(name).addCard(chosen);
		return chosen;
	}

	/**
	 * @param name
	 * @return
	 */
	private boolean removed(String name) {

		if (playerStatus.get(name))
			return false;
		return true;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#firstPrimaryAction(java.lang.String,
	 * int, java.util.ArrayList)
	 */

	@Override
	public synchronized String firstPrimaryAction(String name, int council, int permit, List<Integer> cards)
			throws RemoteException {
		if (removed(name) || !checkPrimaryAction(name) || cards.isEmpty())
			return removedOrPrimaryMessage(name);

		Player player = game.getPlayer(name);
		List<CardCouncilColor> colors;
		List<CardCouncilColor> councilColors = fakeCouncilColors(council);
		int consiglieriBeccati;
		int unicorn;
		int totalCost;

		colors = getColorListFromPlayerHand(cards, name);
		unicorn = countUnicorns(colors);
		consiglieriBeccati = councilorsHitByCard(colors, councilColors);

		totalCost = unicorn + calculateCost(consiglieriBeccati + unicorn);

		if (consiglieriBeccati + unicorn != cards.size()) {
			return Const.WARNING + " cards don't corrispond with council";
		}
		if (!game.getPlayer(name).checkCoins(totalCost)) {
			return Const.WARNING + " not enought money";
		}
		String string = "";
		for (int i = 0; i < colors.size(); i++) {
			string.concat(colors.get(i).toString() + ", ");
		}

		player.subPoliticCards(colors);

		player.takesPermitCard(map.getRegion(council).getAndReplaceVisibleCard(permit));
		player.subCoin(totalCost);

		if (!assignBonus(player).isEmpty() && assignBonus(player).length() > 7) {
			return assignBonus(player);
		}

		toUpdate = Parser.concat(Parser.cmd(Command.PLAYER, name), Parser.cmd(Command.ACTION, "0"),
				Parser.cmd(Command.COUNCIL, Integer.toString(council)), Parser.cmd(Command.CARDNUMS, string));

		game.getPlayer(name).subPrimaryAction();
		needToUpdate = true;
		notifyAll();
		return Const.COMMUNICATION + Const.MESSAGEEVERITHINGOK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#secondPrimaryAction()
	 */
	@Override
	public synchronized String secondPrimaryAction(String name, List<Integer> cards, int destination)
			throws RemoteException {
		if (removed(name) || !checkPrimaryAction(name))
			return removedOrPrimaryMessage(name);

		Player player = game.getPlayer(name);
		List<CardCouncilColor> colors;
		List<CardCouncilColor> councilColors = fakeCouncilColors(Const.KINGCOUNCIL);
		int consiglieriBeccati;
		int unicorn;
		int totalCost;
		int assistantsCost;
		City newPlayerCity = map.getCity(destination);

		colors = getColorListFromPlayerHand(cards, name);
		unicorn = countUnicorns(colors);
		consiglieriBeccati = councilorsHitByCard(colors, councilColors);

		if (consiglieriBeccati + unicorn != cards.size())
			return Const.WARNING + " cards don't corrispond with council";

		totalCost = unicorn + calculateCost(consiglieriBeccati + unicorn) + map.kingCost(destination);

		if (!game.getPlayer(name).checkCoins(totalCost)) {
			return Const.WARNING + " not enought money";
		}
		
		assistantsCost=map.getCity(destination).getEmporiums().size();
		
		if(!game.getPlayer(name).checkAssistants(assistantsCost)){
			return Const.WARNING + "not enought assistants";
		}

		player.subCoin(totalCost);
		player.subPoliticCards(colors);

		lastTurn = game.buildEmporiumAndAddBonusesToPlayer(player, map.getCity(destination));
		if (lastTurn)
			lastPlayer = calculateLastPlayer(name);
		map.getKingsLanding().setKingLocation(newPlayerCity);

		game.getPlayer(name).subPrimaryAction();
		/*
		 * if (!assignBonus(game.getPlayer(name)).isEmpty() &&
		 * assignBonus(game.getPlayer(name)).length() > 7) { return
		 * assignBonus(game.getPlayer(name)); }
		 */
		toUpdate = Parser.concat(Parser.cmd(Command.PLAYER, name), Parser.cmd(Command.ACTION, "1"),
				Parser.cmd(Command.KING, Integer.toString(destination)));
		needToUpdate = true;
		notifyAll();
		return Const.COMMUNICATION + Const.MESSAGEEVERITHINGOK;

	}

	/**
	 * @param onlinePlayers2
	 * @return
	 */
	private String calculateLastPlayer(String theOneWhoBuilt10) {
		String player = onlinePlayers.get(onlinePlayers.size() - 1);
		for (int i = 1; i < onlinePlayers.size(); i++)
			if (onlinePlayers.get(i).equals(theOneWhoBuilt10))
				player = onlinePlayers.get(i - 1);
		return player;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#thirdPrimaryAction()
	 */
	@Override
	public synchronized String thirdPrimaryAction(String name, int council, int councillor) throws RemoteException {

		if (removed(name) || !checkPrimaryAction(name))
			return removedOrPrimaryMessage(name);
		Councillor temp;
		Player player = game.getPlayer(name);
		Council coun = map.getCouncil(council);
		Councillor councill = game.getCouncilorsPool().getCouncillor(councillor);
		if (!checkPrimaryAction(name)) {
			return Const.WARNING + " you have no more primary actions";
		}
		temp = coun.moveCouncil(councill);
		player.addCoin(4);
		player.subPrimaryAction();

		game.getCouncilorsPool().removeFromPool(councill);

		if (!assignBonus(player).isEmpty() && assignBonus(player).length() > 7) {
			return assignBonus(player);
		}
		game.getCouncilorsPool().addCouncillor(temp);
		toUpdate = Parser.concat(Parser.cmd(Command.PLAYER, name), Parser.cmd(Command.ACTION, "2"),
				Parser.cmd(Command.COUNCIL, "" + council),
				Parser.cmd(Command.COUNCILLOR, councill.getColor().toString()));
		needToUpdate = true;
		notifyAll();
		return Const.COMMUNICATION + Const.MESSAGEEVERITHINGOK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#fourthPrimaryAction()
	 */
	@Override
	public synchronized String fourthPrimaryAction(String name, int permit, int initial) throws RemoteException {

		if (removed(name) || !checkPrimaryAction(name))
			return removedOrPrimaryMessage(name);

		Player player = game.getPlayer(name);
		if (!player.checkPermit(permit))
			return Const.WARNING + " You do not have that card";
		City builtCity = map.getCity(player.getPermitCard(permit).getInitial(initial).toUpperCase());
		int builtCityNum = builtCity.getInt();

		lastTurn = game.buildEmporiumAndAddBonusesToPlayer(player, builtCity);
		if (lastTurn)
			lastPlayer = calculateLastPlayer(name);
		
		int assistantsCost=map.getCity(initial).getEmporiums().size();
		
		if(!game.getPlayer(name).checkAssistants(assistantsCost)){
			return Const.WARNING + "not enought assistants";
		}

		player.usePermitCard(permit);

		player.subPrimaryAction();
		if (!assignBonus(player).isEmpty() && assignBonus(player).length() > 7) {
			return assignBonus(player);
		}

		toUpdate = Parser.concat(Parser.cmd(Command.PLAYER, name), Parser.cmd(Command.ACTION, "3"),
				Parser.cmd(Command.BUILTCITY, "" + builtCityNum));
		needToUpdate = true;
		notifyAll();
		return Const.COMMUNICATION + Const.MESSAGEEVERITHINGOK;
	}

	/**
	 * @param name
	 * @return
	 * @throws RemoteException
	 */
	private String removedOrPrimaryMessage(String name) throws RemoteException {
		if (removed(name))
			return Const.WARNING + Const.DISCONNECTED + Const.MESSAGEKICKED;

		if (!checkPrimaryAction(name))
			return Const.WARNING + " you have no more primary actions";
		return Const.WARNING + " you must send at least one card";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#firstFastAction()
	 */
	@Override
	public synchronized String firstFastAction(String name) throws RemoteException {
		if (removed(name) || !checkFastAction(name))
			return removedOrFastAction(name);

		Player player = game.getPlayer(name);
		if (!player.checkCoins(3)) {
			return Const.WARNING + " Sorry, not enought money!";
		}
		player.subCoin(3);
		player.addAssistants(1);
		player.setFastActionDone();
		toUpdate = Parser.concat(Parser.cmd(Command.PLAYER, name), Parser.cmd(Command.ACTION, "4"));
		needToUpdate = true;

		if (!assignBonus(player).isEmpty() && assignBonus(player).length() > 7) {
			return assignBonus(player);
		}
		notifyAll();
		return Const.COMMUNICATION + Const.MESSAGEEVERITHINGOK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#secondFastAction()
	 */
	@Override
	public synchronized String secondFastAction(String name, int region) throws RemoteException {
		if (removed(name) || !checkFastAction(name))
			return removedOrFastAction(name);
		Player player = game.getPlayer(name);
		if (!checkFastAction(name)) {
			return Const.WARNING + Const.MESSAGEFASTACTIONS;
		}
		if (!player.checkAssistants(1)) {
			return Const.WARNING + " Sorry, not enought Assistants!";
		}
		map.getRegion(region).replaceVisiblePermitCards();
		player.setFastActionDone();
		if (!assignBonus(player).isEmpty() && assignBonus(player).length() > 7) {
			return assignBonus(player);
		}

		toUpdate = Parser.concat(Parser.cmd(Command.PLAYER, name), Parser.cmd(Command.ACTION, "5"),
				Parser.cmd(Command.COUNCIL, Integer.toString(region)));
		needToUpdate = true;
		notifyAll();
		return Const.COMMUNICATION + Const.MESSAGEEVERITHINGOK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#thirdFastAction()
	 */
	@Override
	public synchronized String thirdFastAction(String name, int council, int councillor) throws RemoteException {
		if (removed(name) || !checkFastAction(name))
			return removedOrFastAction(name);
		Councillor temp;
		Player player = game.getPlayer(name);
		Council coun = map.getCouncil(council);
		Councillor councill = game.getCouncilorsPool().getCouncillor(councillor);
		if (!player.checkAssistants(1))
			return Const.WARNING + Const.MESSAGEASSISTANTS;
		if (!checkFastAction(name)) {
			return Const.WARNING + Const.MESSAGEFASTACTIONS;
		}
		temp = coun.moveCouncil(councill);
		player.subAssistants(1);
		player.setFastActionDone();
		if (!assignBonus(player).isEmpty() && assignBonus(player).length() > 7) {
			return assignBonus(player);
		}
		game.getCouncilorsPool().addCouncillor(temp);
		toUpdate = Parser.concat(Parser.cmd(Command.PLAYER, name), Parser.cmd(Command.ACTION, "2"),
				Parser.cmd(Command.COUNCIL, Integer.toString(council)),
				Parser.cmd(Command.COUNCILLOR, councill.getColor().toString()));
		needToUpdate = true;
		notifyAll();
		return Const.COMMUNICATION + Const.MESSAGEEVERITHINGOK;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#fourthFastAction()
	 */
	@Override
	public synchronized String fourthFastAction(String name) throws RemoteException {
		if (removed(name) || !checkFastAction(name))
			return removedOrFastAction(name);

		Player player = game.getPlayer(name);
		if (!player.checkAssistants(3)) {
			return Const.WARNING + Const.MESSAGEASSISTANTS;
		}

		player.subAssistants(3);
		player.addPrimaryAction();
		player.setFastActionDone();
		if (!assignBonus(player).isEmpty() && assignBonus(player).length() > 7) {
			return assignBonus(player);
		}
		toUpdate = Parser.concat(Parser.cmd(Command.PLAYER, name), Parser.cmd(Command.ACTION, "7"));
		needToUpdate = true;
		notifyAll();
		return Const.COMMUNICATION + Const.MESSAGEEVERITHINGOK;
	}

	/**
	 * @param name
	 * @return
	 * @throws RemoteException
	 */
	private String removedOrFastAction(String name) throws RemoteException {
		if (removed(name))
			return Const.WARNING + Const.DISCONNECTED + Const.MESSAGEKICKED;

		if (!checkFastAction(name)) {
			return Const.WARNING + Const.MESSAGEFASTACTIONS;
		}
		return null;
	}

	/**
	 * @param player
	 */
	private String assignBonus(Player player) {
		String communication = "[BONUS]";
		// we are smart and we know that this one goes before everything
		game.getNobilityTrack().getBonus(player.getNobility()).activateBonus(player);

		if (player.getChooseBonus() > 0) {
			communication = communication.concat("&choo=" + player.getChooseBonus());
		}
		if (player.getPermitBonus() > 0) {
			communication = communication.concat("&perm=" + player.getPermitBonus());
		}
		if (player.getPoliticBonus() > 0) {
			for (int i = 0; i < player.getPoliticBonus()+1; i++) {
				player.addCard(game.getPoliticDeck().draw());
				player.subPoliticBonus(1);
			}
		}
		return communication;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getVisiblePermitCards(int)
	 */
	@Override
	public PermitCard[] getVisiblePermitCards(int index) throws RemoteException {
		return map.getRegion(index).getVisibilePermits();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#checkPrimaryAction(java.lang.String)
	 */
	@Override
	public boolean checkPrimaryAction(String name) throws RemoteException {
		if (game.getPlayer(name).getPrimaryActionCount() > 0)
			return true;
		return false;
	}

	/*
	 * * return true if action has been done!
	 * 
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#checkFastAction(java.lang.String)
	 * 
	 */
	@Override
	public boolean checkFastAction(String name) throws RemoteException {
		if (game.getPlayer(name).checkFastAction()) {
			return true;
		}
		return false;
	}

	private List<CardCouncilColor> fakeCouncilColors(int council) {

		List<CardCouncilColor> ret = new ArrayList<>();
		Councillor[] councilors = map.getCouncil(council).getCouncilors();
		for (Councillor c : councilors) {
			ret.add(c.getColor());
		}
		return ret;

	}

	private List<CardCouncilColor> getColorListFromPlayerHand(List<Integer> cards, String name) {
		List<CardCouncilColor> ret = new ArrayList<>();
		Player p = game.getPlayer(name);
		for (Integer i : cards) {
			ret.add(p.getPoliticCard(i - 1).getColor());
		}
		return ret;
	}

	/**
	 * da per scontato che non esistano consiglieri unicorno
	 * 
	 * @param cards
	 * @param council
	 * @return
	 */
	private int councilorsHitByCard(List<CardCouncilColor> cards, List<CardCouncilColor> councilColors) {
		List<CardCouncilColor> councilCol = new ArrayList<>();
		councilCol.addAll(councilColors);
		int consiglieriBeccati = 0;

		Integer toRemoveee;
		for (int i = 0; i < cards.size(); i++) {
			toRemoveee = null;
			for (int j = 0; j < councilCol.size(); j++) {
				if (cards.get(i).equals(councilCol.get(j))) {
					toRemoveee = j;
				}
			}

			if (toRemoveee != null) {
				councilCol.remove(toRemoveee.intValue());
				consiglieriBeccati++;
			}

		}
		return consiglieriBeccati;
	}

	/**
	 * @param cols
	 * @return the number of unicorns in the list of colors given
	 */
	private int countUnicorns(List<CardCouncilColor> cols) {
		int ret = 0;
		for (CardCouncilColor c : cols) {
			if (c.equals(CardCouncilColor.UNICORN))
				ret++;
		}
		return ret;
	}

	/**
	 * takes only a number from 1 to 4. if you give something else, returns -1
	 * as an error
	 * 
	 * @param n
	 *            consiglieriBeccati
	 * @return the cost you have to pay with that cards
	 */
	private int calculateCost(int n) {

		if (n == 4)
			return 0;
		if (n == 3)
			return 4;
		if (n == 2)
			return 7;
		if (n == 1)
			return 10;
		else
			return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#passaTurnoSecondary(java.lang.String)
	 */
	@Override
	public void passaTurnoSecondary(String name) throws RemoteException {

		passaTurno(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#passaTurnoSecondary(java.lang.String)
	 */
	@Override
	public synchronized void passaTurnoPrimary(String name) throws RemoteException {

		passaTurno(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.ControllerInterface#passaTurnoMarketCompra(java.lang.String)
	 */
	@Override
	public String passaTurnoMarketCompra(String name) throws RemoteException {
		passaTurno(Const.MARKET + name);
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

		this.passaMarket(name);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#sellPolitcCards(java.lang.String,
	 * java.util.List, int)
	 */
	@Override
	public boolean sellPolitcCards(String name, int politicCards, int cost) {
		PoliticCard card = game.getPlayer(name).getPoliticCard(politicCards);

		if (market.addMarketItem(card)) {
			card.setPrice(cost, game.getPlayer(name));
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#sellPermitCards(java.lang.String,
	 * java.util.List, int)
	 */
	@Override
	public boolean sellPermitCards(String name, int permitCards, int cost) {
		PermitCard card = game.getPlayer(name).getPermitCard(permitCards);

		if (market.addMarketItem(card)) {
			card.setPrice(cost, game.getPlayer(name));
			return true;
		}
		return false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#sellAssistants(java.lang.String, int,
	 * int)
	 */
	@Override
	public void sellAssistants(String name, int assistants, int cost) {
		Assistant ass = new Assistant(game.getPlayer(name), assistants, cost);
		market.addMarketItem(ass);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getMarketContent()
	 */
	@Override
	public List<Sellable> getMarketContent() {
		return market.getAllItems();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#buyItem(java.lang.String, int)
	 */
	@Override
	public String buyItem(String name, int itemSelected) throws RemoteException {
		Player buyer = game.getPlayer(name);
		Sellable obj = market.getItem(itemSelected);

		if (buyer.checkCoins(obj.getPrice())) {
			market.sell(obj, buyer, obj.getSeller());
			return Const.COMMUNICATION + " Transaction done!";
		} else
			return Const.WARNING + " You do not have enough money!";

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#iAmNotAFastAction(java.lang.String)
	 */
	@Override
	public synchronized String iAmNotAFastAction(String name) {

		game.getPlayer(name).setFastActionDone();
		toUpdate = Parser.concat(Parser.cmd(Command.PLAYER, name), Parser.cmd(Command.ACTION, "8"));
		needToUpdate = true;
		notifyAll();
		return Const.COMMUNICATION + " bye bye";

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getCouncil(int)
	 */
	@Override
	public Council getCouncil(int num) throws RemoteException {
		return map.getCouncil(num);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getPermitCards(java.lang.String)
	 */
	@Override
	public List<PermitCard> getPermitCards(String name) {
		return game.getPlayer(name).getPermitCards();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getPoliticCards(java.lang.String)
	 */
	@Override
	public List<PoliticCard> getPoliticCards(String name) {

		return game.getPlayer(name).getPoliticCards();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getAssistants(java.lang.String)
	 */
	@Override
	public int getAssistants(String name) {
		return game.getPlayer(name).getAssistants();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getNobilityTrack()
	 */
	@Override
	public NobilityTrack getNobilityTrack() {
		return game.getNobilityTrack();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getNumPlayers()
	 */
	@Override
	public int getNumPlayers() throws RemoteException {
		return this.initialPlayers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getPlayersNames()
	 */
	@Override
	public List<String> getPlayersNames() {
		List<String> ret = new ArrayList<>();
		ret.addAll(rmiClientsList);
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getMarketItemsOnSale()
	 */
	@Override
	public Set<Sellable> getMarketItemsOnSale() {

		return new HashSet<>(market.getAllItems());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.ControllerInterface#getMarketItemsOnSale()
	 */
	@Override
	public List<Player> finalScore() throws RemoteException {
		return game.getWinner();
	}

}
