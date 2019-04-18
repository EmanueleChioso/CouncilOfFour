package network.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.ControllerInterface;
import controller.Parser;
import model.Const;
import model.cards.PermitCard;
import model.cards.PoliticCard;
import model.map.GameMap;
import model.map.MapFactory;
import model.market.Sellable;
import model.player.Player;
import view.Cli;
import view.GUIFactory;
import view.UserInterface;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna
 *
 */
public class Client {

	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private ClientNetworkInterface net;

	private static String name = null;
	private boolean mapPrinted = false;
	private boolean nameNotAvaible = false;
	private boolean lastTurnWasMine;
	private int action;
	private int actionType;
	private ControllerInterface controller;
	private UserInterface ui;
	private String myTurn;
	private String communication;
	private boolean donezo;
	private int selectedItem;
	private int ass;
	private int assMax;
	private boolean dc;

	/**
	 * the main body of our client.
	 * 
	 * @throws InterruptedException
	 */
	public void start() throws InterruptedException {

		askForName();
		ui = menuGUI();
		net = chooseConnection();
		ui.simpleCommunication("Connection enstablished, w8ing for other palyers...");
		nameNotAvaible = net.checkName(name);
		while (nameNotAvaible) {
			ui.simpleCommunication("name not available.");
			askForName();
			nameNotAvaible = net.checkName(name);
		}
		controller = net.connect(name, ui);
		play(controller);

	}

	/**
	 * takes the RMIGameController and start playing!
	 * 
	 * @param controller
	 * @throws InterruptedException
	 */
	private void play(ControllerInterface controller) throws InterruptedException {
		try {

			ui.welcome();
			// DC = disconnected
			dc = false;
			while (!dc) {
				// check if client need to be updated of some changes
				if (controller.needToUpdate()) {
					update();
				}
				// check if map is not printed but has been set and, in case, it
				// initialize it
				if (!mapPrinted && controller.mapSetted()) {
					init();
				}
				// asks to the controller if it's his turn
				myTurn = controller.askTurn(name);
				// if it's my turn...
				if ("S".equals(myTurn)) {
					// check if map is set, in case is not, it asks to choose a
					// map
					// only first player will actually do this section

					myTurn();
					// if it's market sell turn
				} else if ("Smarket".equals(myTurn)) {
					playYourMarketTurnAndPass();
					// if it's market buy turn
				} else if ("M".equals(myTurn)) {
					sellToMarketAndPassTurn();
					// if it's not my turn
				} else if ("N".equals(myTurn)) {
					lastTurnWasMine = false;
					// if I got disconnected
				} else if ("DC".equals(myTurn)) {
					dc = true;
					ui.simpleCommunication(Const.DISCONNECTED + "");
				} else if ("END".equals(myTurn)) {
					dc = true;
					endGame();
				}

			}

		} catch (RemoteException e) {
			Logger.getGlobal().log(Level.ALL, "Error.", e);
		}

	}

	/**
	 * simply checks to start the turn and play the game
	 * 
	 * @throws RemoteException
	 */
	private void myTurn() throws RemoteException {
		if (!controller.mapSetted()) {
			chooseMap();
			init();
		}
		if (!lastTurnWasMine) {
			ui.itsYourTurn();
			ui.printDrawCard(controller.drawCard(name));
		}
		ui.printDetailedPlayer(controller.getPlayer(name));
		ui.printActionsRemaining(checkPrimary(), checkSecondary());
		// check if player has actions to do
		if (checkPrimary() || checkSecondary()) {
			playTurnAndPass();
		}

	}

	/**
	 * method used during market sell turn
	 * 
	 * @throws RemoteException
	 */
	private void sellToMarketAndPassTurn() throws RemoteException {
		ass=0;
		assMax=controller.getPlayer(name).getAssistants();
		if (ui.askYouWannaSell()) {
			do {
				
				action = ui.askWhatToSell();
				sellAction(action);
			} while (ui.askYouWannaSellSomethingMore());
		}
		ass=0;
		controller.passaTurnoMarketVendi(name);
		
		lastTurnWasMine = false;

	}

	/**
	 * method used during market buy turn
	 * 
	 * @throws RemoteException
	 */
	private void playYourMarketTurnAndPass() throws RemoteException {
		List<Sellable> items;
		items = controller.getMarketContent();
		if (ui.askMarket(items)) {
			do {
				selectedItem = ui.askWhatToBuy(items);
				communication = buyAction(selectedItem);
				ui.simpleCommunication(communication);
				items = controller.getMarketContent();

			} while (ui.askWhatToBuyAgain(items));
		}
		controller.passaTurnoMarketCompra(name);
		lastTurnWasMine = false;

	}

	/**
	 * method used during standard turn
	 * 
	 * @throws RemoteException
	 */
	private void playTurnAndPass() throws RemoteException {

		do {
			if (checkPrimary() && checkSecondary()) {

				actionType = ui.askPrimaryOrSecondary();
				action = ui.askAction(actionType);
			} else if (checkPrimary()) {
				action = ui.askPrimary();
			} else {
				action = ui.askSecondaryWithPass();
			}
		} while (notPerformable(action));
		communication = performAction(action);
		handleBonusCommunication(communication, name);
		donezo = handleCommunication(action, communication);
		dc = checkDC(communication);
		if (donezo) {
			passTurn(action);
		}
		donezo = false;
		lastTurnWasMine = true;
	}

	/**
	 * @param msg
	 * @return true if Client is disconnected, false otherwise
	 */
	private boolean checkDC(String msg) {
		if (msg.contains(Const.DISCONNECTED)) {
			ui.simpleCommunication(msg);
			return true;
		}
		return false;
	}

	/**
	 * method use to verify if the action chosen by the player could not be
	 * performed
	 * 
	 * @param act
	 * @return true is actions could not be performed, false otherwise
	 * @throws RemoteException
	 */
	private boolean notPerformable(int act) throws RemoteException {
		Player player = controller.getPlayer(name);
		if (!player.checkPermit(0) && act == 3) {
			ui.warningPermit();
			return true;
		}
		if (!player.checkCoins(3) && act == 4) {
			ui.warningCoins(3);
			return true;
		}
		if (!player.checkAssistants(1) && (act == 5 || act == 6)) {
			ui.warningAssistants(1);
			return true;
		}
		if (!player.checkAssistants(3) && act == 7) {
			ui.warningAssistants(3);
			return true;
		}
		return false;
	}

	/**
	 * takes a param, an int that represent the number of the chosen action
	 * 
	 * @param action2
	 */
	private String buyAction(int action2) {
		String ret = null;
		try {
			ret = controller.buyItem(name, action2);

		} catch (RemoteException e) {
			Logger.getGlobal().log(Level.ALL, "Error.", e);
		}
		return ret;
	}

	/**
	 * takes a param, the number of the chose action
	 * 
	 * @param action2
	 * @throws RemoteException
	 */
	private void sellAction(int action2) throws RemoteException {
		int politicCard;
		int permitCard;
		int assistants;
		int cost;
		Player player = controller.getPlayer(name);
		switch (action2) {
		// case 0 is politic cards, case 1 in permit cards, case 2 is assistants
		case 0:
			ui.printPoliticCards(player);
			politicCard = ui.askWhatPoliticCards(controller.getPoliticCards(name));

			if (politicCard >= 0) {
				cost = ui.askCost();

				if (!controller.sellPolitcCards(name, politicCard, cost + 1))
					ui.simpleCommunication(
							Const.WARNING + "You cannot sell the same item two times! price will not change.");
			}

			break;
		case 1:
			ui.printPermitCards(player);
			permitCard = ui.askWhatPermitCards(controller.getPermitCards(name));

			if (permitCard >= 0) {
				cost = ui.askCost();

				if (!controller.sellPermitCards(name, permitCard, cost + 1))
					ui.simpleCommunication(
							Const.WARNING + "You cannot sell the same item two times! price will not change.");
			}

			break;
		case 2:
			ui.printAssistants(player);
			ui.simpleCommunication("you can still sell "+assMax+" assistants.");
			assistants = ui.askWhatAssistants(controller.getAssistants(name));

			if (assistants > 0 && assistants<=assMax) {
				cost = ui.askCost();
				assMax -= assistants;
				controller.sellAssistants(name, assistants, cost + 1);
			}
			else 
				ui.simpleCommunication(
						Const.WARNING + "You cannot sell that quantity!");
			break;
		}

	}

	/**
	 * param communication is a string that contains all information on how
	 * action was performed, if it went good or extremely bad
	 * 
	 * @param act
	 * @param comm
	 * @return true if action could be performed, false otherwise
	 */
	private boolean handleCommunication(int act, String comm) {
		if (warningComunication(comm))
			return false;
		ui.showPerformedAction(act);
		return true;

	}

	/**
	 * handles the communication while giving bonuses at the end of any actions
	 * in case the players has to choose between bonuses/permits to get
	 * 
	 * @param name2
	 * @param communication2
	 * @throws RemoteException
	 */
	private void handleBonusCommunication(String communication, String name2) throws RemoteException {
		Map<String, String> info;
		int city;
		int perm;
		PermitCard card = null;
		info = Parser.parseCommand(communication);
		if (info.containsKey("choo")) {
			/* num = */ Integer.parseInt(info.get("choo"));
			city = ui.askWhatCityBonus(controller.getMap().getPlayerCities(controller.getPlayer(name2)));
			controller.getMap().getCity(city).getBonus().activateBonus(controller.getPlayer(name2));
		}
		if (info.containsKey("perm")) {
			/* num = */ Integer.parseInt("perm");
			for (int i = 0; i < Const.REGIONS; i++) {
				ui.printVisiblePermitcCards(i, controller.getVisiblePermitCards(i));
			}

			perm = ui.askPermitToGetBetweenVisible();
			switch (perm) {
			case 0:
				card = controller.getMap().getSeaRegion().getAndReplaceVisibleCard(0);
				break;
			case 1:
				card = controller.getMap().getSeaRegion().getAndReplaceVisibleCard(1);
				break;

			case 2:
				card = controller.getMap().getSeaRegion().getAndReplaceVisibleCard(2);
				break;
			case 3:
				card = controller.getMap().getSeaRegion().getAndReplaceVisibleCard(3);
				break;
			case 4:
				card = controller.getMap().getSeaRegion().getAndReplaceVisibleCard(4);
				break;
			case 5:
				card = controller.getMap().getSeaRegion().getAndReplaceVisibleCard(5);
				break;
			}
			controller.getPlayer(name).addCard(card);
		}
	}
	//
	// /**
	// * @param comm
	// * @return
	// */
	// private boolean bonusCommunication(String comm) {
	// String splitted[] = comm.split("&");
	// if("[BONUS]".equals(splitted[0])){
	// return true;
	// }
	// return false;
	// }

	/**
	 * takes a param, a string that contains all communication data
	 * 
	 * @param c
	 * @return true is communication is a warning message, false otherwise
	 */
	public boolean warningComunication(String c) {
		String[] splitted = c.split(" ");
		if ("[WARNING]".equals(splitted[0])) {
			return true;
		}
		return false;
	}

	/**
	 * take the number of the action 0/7 and passes the turn.
	 * 
	 * @param action2
	 * @throws RemoteException
	 */
	private void passTurn(int action2) throws RemoteException {
		if (action2 >= 0 && action2 <= 3)
			controller.passaTurnoPrimary(name);
		if (action2 > 3 && action2 < 9)
			controller.passaTurnoSecondary(name);
	}

	/**
	 * takes an integer, the number of the chosen action and performs it
	 * 
	 * @param act
	 * @return a string that contains all communication data
	 * @throws RemoteException
	 */
	private String performAction(int act) throws RemoteException {
		String communication2 = null;
		int region;
		int council;
		int councillor;

		if (act < 5)
			return case01234(act);

		switch (act) {

		case 5:
			// second fast action, change visible permit cards
			for (int i = 0; i < Const.REGIONS; i++)
				ui.printVisiblePermitcCards(i, controller.getVisiblePermitCards(i));
			region = ui.askPermitToChange();
			communication2 = controller.secondFastAction(name, region);

			break;
		case 6: // third fast action, change council disposition
			for (int i = 0; i < Const.COUNCIL; i++)
				ui.printCouncil(i, controller.getCouncil(i));
			council = ui.askCouncilChange();
			ui.printRemainingCouncilors(controller.getCounciorsPool());
			councillor = ui.askCouncillorToChange();
			communication2 = controller.thirdFastAction(name, council, councillor);
			break;
		case 7:
			// fourth fast action, buy a primary action
			communication2 = controller.fourthFastAction(name);
			break;
		// pass turn
		case 8:
			communication2 = controller.iAmNotAFastAction(name);
			break;
		default:
			break;
		}

		return communication2;

	}

	/**
	 * @param act
	 * @return the communication string
	 * @throws RemoteException
	 */
	private String case01234(int act) throws RemoteException {
		String communication2 = null;
		int council;
		int councillor;
		int permit;
		int destination;
		int city;
		List<Integer> cards;

		if (act == 0) {
			// first primary action, satisfy a council and get a permit card
			for (int i = 0; i < Const.REGIONS; i++) {
				ui.printCouncil(i, controller.getCouncil(i));
			}
			ui.printPoliticCards(controller.getPlayer(name));
			council = ui.askCouncil();
			cards = ui.askPlayerCardsToUse();
			ui.printVisiblePermitcCards(council, controller.getVisiblePermitCards(council));
			permit = ui.askPermitToBuy();

			communication2 = controller.firstPrimaryAction(name, council, permit, cards);
		} else if (act == 1) {
			// second primary action, satisfy king's council and build a
			// emporium
			ui.printKingCouncil(controller.getCouncil(Const.KINGCOUNCIL));
			ui.printPoliticCards(controller.getPlayer(name));
			cards = ui.askPlayerCardsToUse();
			destination = ui.askKingDestination();
			communication2 = controller.secondPrimaryAction(name, cards, destination);
		}

		else if (act == 2) {
			// third primary action, change council composition
			ui.printCouncils(controller.getMap());
			council = ui.askCouncilChange();
			ui.printRemainingCouncilors(controller.getCounciorsPool());
			councillor = ui.askCouncillorToChange();
			communication2 = controller.thirdPrimaryAction(name, council, councillor);
			ui.printCouncils(controller.getMap());

		} else if (act == 3) {
			// fourth primary action, build an emporium
			if (controller.getPlayer(name).getNumPermitCard() > 0) {
				List<PermitCard> p = controller.getPlayer(name).getPermitCards();
				String[] permits = new String[p.size()];
				for (int i = 0; i < p.size(); i++) {
					permits[i] = p.get(i).toString();
				}
				permit = ui.askpermitToUse(permits);
				city = ui.askCity(p.get(permit).getInitials());
				communication2 = controller.fourthPrimaryAction(name, permit, city);
			} else
				communication2 = Const.WARNING + "You have no Permit Cards";
		} else if (act == 4)
			// first fast action, buy an assistant
			communication2 = controller.firstFastAction(name);

		return communication2;
	}

	/**
	 * a method called at the beginning of all players turn that is used to
	 * update the clients of what other players did in their turn. it will
	 * check, if controller has something to update, will handle the
	 * communication, and will display any changes to the client
	 * 
	 * @throws RemoteException
	 * @throws NumberFormatException
	 * 
	 */
	private void update() throws NumberFormatException, RemoteException {
		String objToUpdate = null;
		Map<String, String> info;
		String player = null;
		String cards = null;
		int builtCity;
		int destination;
		int council;

		try {
			objToUpdate = controller.update();
		} catch (RemoteException e) {
			Logger.getGlobal().log(Level.ALL, "Error.", e);
		}

		info = Parser.parseCommand(objToUpdate);

		player = info.get("player");
		switch (info.get(Const.ACTION)) {
		case "0":
			council = Integer.parseInt(info.get("council"));
			cards = info.get("cards");
			update(0, player, council, cards);
			break;
		case "1":
			destination = Integer.parseInt(info.get("king"));
			update(Integer.parseInt(info.get(Const.ACTION)), player, destination);
			break;
		case "3":
			builtCity = Integer.parseInt(info.get("builtCity"));
			update(Integer.parseInt(info.get(Const.ACTION)), player, builtCity);
			break;
		case "2":
		case "5":
		case "6":
			council = Integer.parseInt(info.get("council"));
			update(Integer.parseInt(info.get(Const.ACTION)), player, council);
			break;
		// case "4": case "7": case "8": potrebbero servire. sono il default.
		default:
			update(Integer.parseInt(info.get(Const.ACTION)), player);
			break;
		}
	}

	/**
	 * @return an interface, the one chosen by the client when asked what
	 *         connection type he wants
	 * 
	 */
	private ClientNetworkInterface chooseConnection() {
		ClientNetworkInterface net = null;
		try {
			String chosen;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			ui.simpleCommunication("Choose your connection:\n1.RMI  2.Socket");
			do {
				chosen = in.readLine();
			} while (!"1".equals(chosen) && !"2".equals(chosen));
			net = ConnectionFactory.setConnection(chosen);
		} catch (IOException e) {
			Logger.getGlobal().log(Level.ALL, "Error.", e);
		}
		return net;
	}

	/**
	 * asks what name the client want to use to be identified
	 */
	private void askForName() {
		String chosen = null;
		in = new BufferedReader(new InputStreamReader(System.in));
		ui = new Cli();
		ui.simpleCommunication("Insert your name!");
		try {
			chosen = in.readLine();
		} catch (IOException e) {
			Logger.getGlobal().log(Level.ALL, "Error while reading the name. ", e);
		}
		setUserName(chosen);
	}

	/**
	 * @return this client name
	 */
	public String getUserName() {
		return Client.name;
	}

	/**
	 * @param name
	 */
	private static void setUserName(String name) {
		Client.name = name;
	}

	/**
	 * @return the type of User interface the client wants to use
	 */
	private UserInterface menuGUI() {
		Cli tempUI = new Cli();
		UserInterface ret;
		int scelta;
		String[] menuUi = { "cli", "gui" };

		scelta = tempUI.menu(menuUi);
		ret = GUIFactory.getGUI(menuUi[scelta]);
		return ret;
	}

	/**
	 * initialize player turn beginning by printing players stats, map, nobility
	 * track, visible permit cards, councils....
	 * 
	 * @throws RemoteException
	 */
	@SuppressWarnings("unchecked")
	private void init() throws RemoteException {
		GameMap m = controller.getMap();
		// List<String> playersNames = new ArrayList<>();
		// playersNames =
		ui.printDetailedMap(m, controller.getPlayersNames());

		ui.printMap(m);
		for (int i = 0; i < 3; i++) {
			ui.printVisiblePermitcCards(i, m.getRegion(i).getVisibilePermits());

		}
		ui.printNobilityTrack(controller.getNobilityTrack());
		for (int i = 0; i < 4; i++) {
			ui.printCouncil(i, m.getCouncil(i));
		}
		ui.printRemainingCouncilors(controller.getCounciorsPool());
		mapPrinted = true;
		ui.printAllPlayerStats(controller.getAllPlayersStat());
	}

	/**
	 * @return true if player has at list a primary action to do
	 * @throws RemoteException
	 */
	private boolean checkPrimary() throws RemoteException {
		return controller.checkPrimaryAction(name);
	}

	/**
	 * @return true if player has a fast action to do
	 * @throws RemoteException
	 */
	private boolean checkSecondary() throws RemoteException {
		return controller.checkFastAction(name);
	}

	/**
	 * this method is invoked by action 1
	 * 
	 * @param i
	 * @param name
	 * @param council
	 * @param returnString
	 * @throws RemoteException
	 */
	public void update(int i, String name, int council, String returnString) throws RemoteException {
		if (i == 0) {
			Player player = controller.getPlayer(name);
			ui.printChanges(i, name, council, returnString);
			ui.printPlayer(player);
			ui.printPermitCards(player);
		}
	}

	/**
	 * this method is invoked by action 2,3,4 and 6
	 * 
	 * @param i
	 * @param name
	 * @param num
	 * @throws RemoteException
	 */
	public void update(int i, String name, int num) throws RemoteException {
		ui.printPlayer(controller.getPlayer(name));
		if (i == 1) {
			ui.printDetailedMap(controller.getMap(), controller.getPlayersNames());
			ui.printChanges(i, name, num);

		} else if (i == 2) {
			ui.printChanges(i, name, num);
			ui.printCouncil(num, controller.getCouncil(num));
		} else if (i == 3) {
			ui.printChanges(i, name, num);
		} else if (i == 5) {
			ui.printChanges(i, name, num);
			ui.printVisiblePermitcCards(num, controller.getVisiblePermitCards(num));

		} else if (i == 6) {
			ui.printChanges(i, name, num);
			ui.printVisiblePermitcCards(num, controller.getVisiblePermitCards(num));
		}

	}

	/**
	 * this method is invoked by action 5 and 8
	 * 
	 * @param i
	 * @param name
	 * @throws RemoteException
	 */
	public void update(int i, String name) throws RemoteException {
		if (i == 4 || i == 7) {
			ui.printChanges(i, name);
			ui.printPlayer(controller.getPlayer(name));
		}

	}


	/**
	 * makes a player choose the map in which he wants to play (only one player
	 * will have this possibility)
	 */
	public void chooseMap() {
		int ret = ui.askMap();
		try {
			controller.setMap(MapFactory.getMap(ret));
		} catch (RemoteException e) {
			Logger.getGlobal().log(Level.ALL, "Error.", e);
		}
	}

	/**
	 * @throws RemoteException
	 * 
	 */
	private void endGame() throws RemoteException {
		ui.finalScore(controller.finalScore());

	}

	public static void main(String[] args) throws InterruptedException {
		Client c = new Client();
		c.start();
	}
}
