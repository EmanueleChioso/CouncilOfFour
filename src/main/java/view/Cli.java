package view;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import model.Const;
import model.cards.Deck;
import model.cards.PermitCard;
import model.cards.PoliticCard;
import model.map.City;
import model.map.Council;
import model.map.CouncillorsPool;
import model.map.GameMap;
import model.map.NobilityTrack;
import model.market.Sellable;
import model.player.Player;
import view.cli.SuperCoolCli;

public class Cli implements UserInterface, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8791129769727279205L;
	private static final String[] MENUCOUNCIL = { "Sea Council", "Hill Council", "Mountain Council" };
	private static final String[] MENUACTIONS = { "Primary Action", "Secondary Action" };
	private static final String[] MENUPRIMARYACTIONS = { "satisfy a council", "satisfy king's council",
			"switch a council councilors", "build emporium" };
	private static final String[] MENUSECONDARYACTIONS = { "buy an assistant", "use an assistant to change cards",
			"send an assistant to change a council", "pay three assistants for a primary action" };
	private static final String[] MENUPERMITS = { "Sea permits", "Hill permits", "Mountain permits" };
	private static final String[] MENUSECONDARYACTIONSWITHPASS = { "buy an assistant",
			"use an assistant to change cards", "send an assistant to change a council",
			"pay three assistants for a primary action", "pass the turn" };
	private static final String[] MENUSELLABLE = { "Politic cards", "Permit cards", "Assistants" };
	private static final String[] MAPS = { "easy 1   - Jerusalem ", "easy 2   - Xi’an", "easy 3   - Plovdiv",
			"medium 1 - Lisbon", "medium 2 - Athens", "hard 1   - Damascus", "hard 2   - Istanbul", "hard 3   - Rome",
			"Choose your own Random map by difficult! " };

	private static final String[] CITYNAMES = { "Arkon", "Burgen", "Castrum", "Dorful", "Esti", "Framek", "Graden",
			"Hellar", "Indur", "Juvelar", "Kultos", "Lyram", "Merkatim", "Naris", "Osium" };

	public static final String PLAYERSTRING = "Player ";

	private static Scanner readin = new Scanner(System.in);

	private static PrintStream out = System.out;

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#menu(java.lang.String[])
	 */
	@Override
	public int menu(String[] menu) {
		int ret;
		for (int i = 0; i < menu.length; i++) {
			print(i + 1 + ". " + menu[i]);
		}
		do {

			ret = getNumber(menu.length);
		} while (ret == -1);
		return ret;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#printDetailedMap(model.map.GameMap,
	 * java.util.List)
	 */
	@Override
	public void printDetailedMap(GameMap map, List<String> playersNames) {
		SuperCoolCli cli = new SuperCoolCli(map, playersNames);
		cli.print();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#printMap(model.map.GameMap)
	 */
	@Override
	public void printMap(GameMap map) {

		print();
		printNoLine("  |");
		for (int i = 0; i < Const.CITIES; i++)
			printNoLine(" " + map.getCity(i).getLetter() + " |");
		print();

		for (int i = 0; i < Const.CITIES; i++) {
			printNoLine(map.getCity(i).getLetter() + " |");
			for (int j = 0; j < Const.CITIES; j++) {
				if (map.getConnection(i, j))
					printNoLine(" " + map.getCity(j).getLetter() + " |");
				else
					printNoLine("   |");
			}
			print();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#printCouncil(int, model.map.Council)
	 */
	@Override
	public void printCouncil(int i, Council council) {
		if (i == 0)
			print("1. SEA COUNCIL");
		else if (i == 1)
			print("2. HILL COUNCIL");
		else if (i == 2)
			print("3. MOUNTAIN COUNCIL");
		else if (i == 3)
			print("4. KING COUNCIL");
		print(council.toString());
		print();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#printPoliticCards(model.player.Player)
	 */
	@Override
	public void printPoliticCards(Player player) {

		if (player.getNumPoliticCard() > 0) {
			print(player.getName() + " these are your politic cards: ");
			for (int i = 0; i < player.getNumPoliticCard(); i++) {
				printNoLine("[" + (i + 1) + ": " + player.getPoliticCard(i).getColor() + "] ");
			}

			print("");
		} else {
			print("you have no Politic cards!");
		}

	}

	/**
	 * prints the player's corresponding to the name given statistics and number
	 * of cards
	 * 
	 * @param name
	 */
	@Override
	public void printPlayer(Player player) {
		printPlayerStat(player);
		print("POLITIC CARDS: " + player.getNumPoliticCard());
		print("PERMIT CARDS: " + player.getNumPermitCard());
		print("");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#welcome()
	 */
	@Override
	public void welcome() {
		print();
		print("..######...#######..##.....##.##....##..######..####.##...........#######..########....########..#######..##.....##.########.. ");
		print(".##....##.##.....##.##.....##.###...##.##....##..##..##..........##.....##.##..........##.......##.....##.##.....##.##.....##. ");
		print(".##.......##.....##.##.....##.####..##.##........##..##..........##.....##.##..........##.......##.....##.##.....##.##.....##. ");
		print(".##.......##.....##.##.....##.##.##.##.##........##..##..........##.....##.######......######...##.....##.##.....##.########.. ");
		print(".##.......##.....##.##.....##.##..####.##........##..##..........##.....##.##..........##.......##.....##.##.....##.##...##... ");
		print(".##....##.##.....##.##.....##.##...###.##....##..##..##..........##.....##.##..........##.......##.....##.##.....##.##....##.. ");
		print("..######...#######...#######..##....##..######..####.########.....#######..##..........##........#######...#######..##.....##. ");
		print();
	}

	/**
	 * @param player
	 * @return
	 */
	@Override
	public int showPossibleActions(String[] string) {
		int ret;
		for (int i = 0; i < string.length; i++) {
			print(i + 1 + ". " + string[i]);
		}
		ret = getNumber(string.length);
		return ret;
	}

	/**
	 * Simplifies the world
	 * 
	 * @param s
	 */
	private static void print(String s) {

		out.println(s);
	}

	/**
	 * simplifies the world without going down
	 * 
	 * @param s
	 */
	private static void printNoLine(String s) {
		out.print(s);
	}

	/**
	 * now that the world is simplier, let's do nothing
	 */
	private static void print() {
		out.println();
	}

	/**
	 * return a number between 0 and num-1
	 * 
	 * @param num
	 * @return int
	 */
	@SuppressWarnings("resource")
	static int getNumber(int num) {
		Boolean quit;
		String myLine = readin.nextLine();
		int ret = 0;

		do {
			quit = true;
			if (myLine.length() != 0) {
				ret = (myLine.charAt(0)) - 48;
				if (ret < 1 || ret > num) {
					print("try again");
					quit = false;
					myLine = readin.nextLine();
				}
			}
		} while (!quit);
		ret -= 1;
		return ret;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("resource")
	static int getBigNumber() {
		Boolean quit;
		String myLine = readin.nextLine();
		int ret = 0;

		do {
			quit = true;
			if (myLine.length() != 0) {
				ret = Integer.parseInt(myLine);
				if (ret < 1 || ret > 50000) {
					print("try again");
					quit = false;
					myLine = readin.nextLine();
				}
			}
		} while (!quit);
		ret -= 1;
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askPrimaryOrSecondary()
	 */
	@Override
	public int askPrimaryOrSecondary() {
		int ret;
		ret = menu(MENUACTIONS);
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askAction(int)
	 */
	@Override
	public int askAction(int primaryOrSecondary) {
		int ret = -1;
		if (primaryOrSecondary == 0)
			ret = menu(MENUPRIMARYACTIONS);
		if (primaryOrSecondary == 1)
			ret = menu(MENUSECONDARYACTIONS) + 4;
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askPrimary()
	 */
	@Override
	public int askPrimary() {
		return menu(MENUPRIMARYACTIONS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askSecondary()
	 */
	@Override
	public int askSecondary() {
		return menu(MENUSECONDARYACTIONS) + 4;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askCouncil()
	 */
	@Override
	public int askCouncil() {
		print("What council you'd like to satisfty?");
		return menu(MENUCOUNCIL);
	}

	/**
	 * return true if communication is not a Warning!
	 * 
	 * @param communication
	 * @return true false if the communication is a warning!
	 */
	public boolean checkComunication(String communication) {
		String splitted[] = communication.split(" ");
		if ("[WARNING]".equals(splitted[0])) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askMarket(java.util.List)
	 */
	@Override
	public boolean askMarket(List<Sellable> items) {
		if (items.isEmpty()) {
			print("MARKET empty!");
			return false;
		}
		print("MARKET:");
		for (int i = 0; i < items.size(); i++) {
			print((i + 1) + ": " + items.get(i).toString() + " - Price: " + items.get(i).getPrice());
		}
		print("Do you want to buy something from market?");
		return askYorN();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#printRemainingCouncilors(java.util.ArrayList)
	 */
	@Override
	public void printRemainingCouncilors(CouncillorsPool pool) {

		print("AVAILABLE COUNCILORS:");
		for (int i = 0; i < pool.getLenght(); i++) {
			print((i + 1) + "-" + pool.getCouncillor(i).toString());
		}
		print("");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#printAllPlayerStats()
	 */
	@Override
	public void printAllPlayerStats(List<Player> players) {

		for (int i = 0; i < players.size(); i++)
			printPlayer(players.get(i));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#printNobilityTrack()
	 */
	@Override
	public void printNobilityTrack(NobilityTrack track) {

		print(track.toString());

	}

	/**
	 * prints all statistics + cards in hand and Permit card it must be used
	 * only for the corresponding player
	 * 
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#printDetailedPlayer(java.lang.String)
	 */
	@Override
	public void printDetailedPlayer(Player player) {
		printPlayerStat(player);
		printPoliticCards(player);
		printPermitCards(player);
		print();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#printDrawCard(model.cards.PoliticCard)
	 */
	@Override
	public void printDrawCard(PoliticCard card) {
		print("[COMUNICATION]:  you draw a " + card.getColor() + " card!");
		print("");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#printPermitCards()
	 */
	@Override
	public void printPermitCards(Player player) {

		if (player.getNumPermitCard() > 0) {
			for (int i = 0; i < player.getNumPermitCard(); i++) {
				printNoLine("Permit card " + (i + 1) + ": " + player.getPermitCard(i).toString());
			}

		} else {
			print("No Permit cards!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#printVisiblePermitcCards(int)
	 */
	@Override
	public void printVisiblePermitcCards(int region, PermitCard[] permits) {
		if (region == 0)
			print("Sea Council");
		else if (region == 1)
			print("Hill Council");
		else if (region == 2)
			print("Mountain Council");

		for (PermitCard s : permits) {
			print(s.toString());
		}

	}

	/**
	 * prints only player's statistics (non-Javadoc)
	 * 
	 * @see view.UserInterface#printPlayerStat(java.lang.String)
	 */
	@Override
	public void printPlayerStat(Player player) {

		print(player.getName());
		print("POINTS: " + player.getPoints());
		print("COINS: " + player.getCoin());
		print("ASSISTANTS: " + player.getAssistants());
		print("NOBILITY: " + player.getNobility());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#printActionsRemaining()
	 */
	@Override
	public void printActionsRemaining(boolean primary, boolean secondary) {
		if (primary && secondary) {
			print("You can do both Primary and Secondary actions");
			return;
		}
		if (primary)
			print("You can still do your Primary Action");
		else if (secondary)
			print("You can still do you secondary Action");
		else
			print("You haven't got any action left");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#printCouncils()
	 */
	@Override
	public void printCouncils(GameMap map) {
		for (int i = 0; i < Const.COUNCIL; i++) {

			printCouncil(i, map.getCouncil(i));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askCouncilorChange()
	 */
	@Override
	public int askCouncillorToChange() {
		print("Which councillor of the remaining 8 would you like to use?");
		return getNumber(Const.REMAININGCOUNCILLORS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askPlayerCardsToUse()
	 */
	@Override
	public List<Integer> askPlayerCardsToUse() {
		print();
		print("Insert the numbers of the cards you'd like to use to satisfy the council!");
		print("[Q] or [q] to exit card selection!");
		List<Integer> cards = new ArrayList<>();
		String line = readin.nextLine();
		while (true) {
			if ("q".equalsIgnoreCase(line) || cards.size() == 4) {
				break;
			}
			if (cards.contains(Integer.parseInt(line))) {
				print("You have selected it before, retry");
			} else {
				cards.add(Integer.parseInt(line));
			}
			line = readin.nextLine();
		}
		return cards;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askPermit(int)
	 */
	@Override
	public int askPermitToChange() {

		print("Where would you like to change the Permit Cards ?");

		return menu(MENUPERMITS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askCouncilChange()
	 */
	@Override
	public int askCouncilChange() {
		print("What council you'd like to change?");
		return getNumber(Const.COUNCIL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askPermitToBuy()
	 */
	@Override
	public int askPermitToBuy() {
		print("Wich permit would you like to buy? 1 or 2");
		return getNumber(Const.VISIBLEPERMITS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#printKingCouncil()
	 */
	@Override
	public void printKingCouncil(Council council) {

		this.printCouncil(Const.KINGCOUNCIL, council);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askKingDestination()
	 */
	@Override
	public int askKingDestination() {
		print("where would you like to send the king?");
		String line;
		int destination;
		line = readin.nextLine();
		while (true) {
			char letter = line.charAt(0);
			if (letter >= 'A' && letter <= 'O') {
				destination = letter - 'A';
				print(" gli RITTORNO un " + destination);

				return destination;
			}
			if (letter >= 'a' && letter <= 'o') {
				destination = letter - 'a';
				print(" gli RITTORNO un " + destination);

				return destination;
			}
			line = readin.nextLine();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#itsYourTurn()
	 */
	@Override
	public void itsYourTurn() {
		print();
		print("   _ _   _                               _                    ");
		print("  (_) |_( )__   _   _  ___  _   _ _ __  | |_ _   _ _ __ _ __  ");
		print("  | | __|/ __| | | | |/ _ \\| | | | '__| | __| | | | '__| '_ \\ ");
		print("  | | |_ \\__ \\ | |_| | (_) | |_| | |    | |_| |_| | |  | | | |");
		print("  |_|\\__||___/  \\__, |\\___/ \\__,_|_|     \\__|\\__,_|_|  |_| |_|");
		print("                |___/                                         ");
		print();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askSecondaryWithPass()
	 */
	@Override
	public int askSecondaryWithPass() {
		return menu(MENUSECONDARYACTIONSWITHPASS) + 4;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askpermitToUse()
	 */
	@Override
	public int askpermitToUse(String[] permits) {
		print("Which PermitCard would you like to use?");
		return menu(permits);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askCity(model.cards.PermitCard)
	 */
	@Override
	public int askCity(String[] initials) {
		print("Which city would you like to build?");
		return menu(initials);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askWhatToSell()
	 */
	@Override
	public int askWhatToSell() {
		print("What you'd like to sell?");

		return menu(MENUSELLABLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askWhatPermitCards()
	 */
	@Override
	public int askWhatPermitCards(List<PermitCard> cards) {
		print();
		print("Insert the number of the card you'd like to sell!");
		print("[Q] or [q] to exit card selection!");
		print("these are your cards");
		String[] toStrings = new String[cards.size()];
		for (int i = 0; i < cards.size(); i++)
			toStrings[i] = cards.get(i).toString();

		return menu(toStrings);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askWhatPoliticCards()
	 */
	@Override
	public int askWhatPoliticCards(List<PoliticCard> cards) {
		print();
		print("Insert the number of the card you'd like to sell!");
		print("[Q] or [q] to exit card selection!");
		print("these are your cards");
		String[] toStrings = new String[cards.size()];
		for (int i = 0; i < cards.size(); i++)
			toStrings[i] = cards.get(i).toString();

		return menu(toStrings);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askWhatAssistants()
	 */
	@Override
	public int askWhatAssistants(int assi) {
		print();
		print("Insert the number of Assistants you'd like to sell");
		print("[Q] or [q] to exit!");
		return getNumber(assi) + 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askCost()
	 */
	@Override
	public int askCost() {
		print("set a price!");
		print("remember... max coins you'll get is 20 and you cannot sell the same item two times!");
		return getBigNumber();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askYouWannaSell()
	 */
	@Override
	public boolean askYouWannaSell() {
		print("It's market time... do you want to sell something?");
		return askYorN();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askYouWannaSellSomethingMore()
	 */
	@Override
	public boolean askYouWannaSellSomethingMore() {
		print("Do you want to sell something more?");
		return askYorN();
	}

	@Override
	public int askWhatToBuy(List<Sellable> items) {
		String[] itemsString = new String[items.size()];
		for (int i = 0; i < items.size(); i++) {
			itemsString[i] = items.get(i).toString() + " - Price: " + items.get(i).getPrice();
		}
		print("So, what do you want to buy?");
		return menu(itemsString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askWhatToBuyAgain()
	 */
	@Override
	public boolean askWhatToBuyAgain(List<Sellable> items) {
		if (items.size() == 0) {
			print("MARKET empty!");
			return false;
		}
		print("Do you want to buy something else?");
		return askYorN();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#showPerformedAction(int)
	 */
	@Override
	public void showPerformedAction(int act) {
		if (act == 0)
			print("You bought a permit card satisfying the council! Well Done Sir.");
		else if (act == 1)
			print("You built an emporium satisfying the council of the king!");
		else if (act == 2)
			print("You earned 4 coins changing the councillor in the council!");
		else if (act == 3)
			print("You built an emporium using your permit card! ");
		else if (act == 4)
			print("You spent 3 coins to get an assistant");
		else if (act == 5)
			print("You spent 1 Assistant to change the visible permits of the council!");
		else if (act == 6)
			print("You spent 1 Assistant to change the councillor in the council!");
		else if (act == 7)
			print("You spent 3 Assistants to get another primary action!");
		else if (act == 8)
			print("You passed!");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#printAssistants(model.player.Player)
	 */
	@Override
	public void printAssistants(Player player) {
		print("You have " + player.getAssistants() + " assistants.");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askMap()
	 */
	@Override
	public int askMap() {
		print("In which map would you like to play?");
		print();
		int ret = menu(MAPS) + 1;
		if( ret == 9 ){
			print();
			print("Choose your difficult! ");
			print("Insert a number from 1 to 5  [1 = very easy] [ 5 = impossible game!]");
			print();
			int num = getNumber(5);
			return 10+num;
		}
		else
			print("You choose the map " + MAPS[ret]);
			
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askWhatCityBonus()
	 */
	@Override
	public int askWhatCityBonus(List<City> cities) {
		print("you can choose the bonus you want among the cities where you built an emporium!");
		for (int i = 0; i < cities.size(); i++) {
			System.out.println((i + 1) + cities.get(i).getLetter());
		}
		return getNumber(cities.size());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#askPermitToGetBetweenVisible()
	 */
	@Override
	public int askPermitToGetBetweenVisible() {
		print("what permit card you'd like to get? type 1 to 6");
		return getNumber(Const.REGIONS * Const.VISIBLEPERMITS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#printChanges(int, java.lang.String, int,
	 * java.lang.String)
	 */
	@Override
	public void printChanges(int i, String name, int council, String returnString) {
		print();
		if (i == 0) {
			print(PLAYERSTRING + name + " did a primary action");
			print(PLAYERSTRING + name + " satisfied #" + council + " council using " + returnString);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#printChanges(int, java.lang.String, int)
	 */
	@Override
	public void printChanges(int i, String name, int num) {
		print();
		if (i == 1) {
			print(PLAYERSTRING + name + " did a primary action");
			print(PLAYERSTRING + name + " moved the king in the city " + CITYNAMES[num] + " and built a city.");
		} else if (i == 2) {
			print(PLAYERSTRING + name + " did a primary action");
			print(PLAYERSTRING + name + " changed #" + num + " council disposition!");
		} else if (i == 3) {
			print(PLAYERSTRING + name + " did a primary action");
			print(PLAYERSTRING + name + " used his Permit Card to build the city " + CITYNAMES[num]);
		} else if (i == 5) {
			print(PLAYERSTRING + name + " did a fast action");
			print(PLAYERSTRING + name + " changed #" + num + " region's permit cards");
			print("new permit cards are:");
		} else if (i == 6) {
			print(PLAYERSTRING + name + " did a fast action");
			print(PLAYERSTRING + name + " used an assistant to change #" + num + " council disposition!");

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#printChanges(int, java.lang.String)
	 */
	@Override
	public void printChanges(int i, String name) {
		print();
		if (i == 4) {
			print(PLAYERSTRING + name + " did a fast action");
			print(PLAYERSTRING + name + " payed 3 coins to get an assistant");

		} else if (i == 7) {
			print(PLAYERSTRING + name + " did a fast action");
			print(PLAYERSTRING + name + " payed 3 Assistants to get an additional primary action");

		}
		if (i == 8)
			print(PLAYERSTRING + name + " passes the turn without doing the fast action");

	}

	/**
	 * @return true is choice is y, flase otherwise
	 */
	private boolean askYorN() {
		String line;
		do {
			print("[y] or [n]");
			line = readin.nextLine();
		} while (!Const.YES.equalsIgnoreCase(line) && !Const.NO.equalsIgnoreCase(line));
		if (Const.YES.equalsIgnoreCase(line))
			return true;
		return false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#warningAssistants(int)
	 */
	@Override
	public void warningAssistants(int assistants) {
		print(Const.WARNING + " You do not have " + assistants + " coins to do that!");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#warningCoins()
	 */
	@Override
	public void warningCoins(int coins) {
		print(Const.WARNING + " You do not have " + coins + " coins to do that!");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#warningPermit()
	 */
	@Override
	public void warningPermit() {
		print(Const.WARNING + " You do not have any permit card!");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#printTransaction(java.lang.String)
	 */
	@Override
	public void simpleCommunication(String communication) {
		print(communication);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.UserInterface#finalScore(java.util.List)
	 */
	@Override
	public void finalScore(List<Player> players) {
		int place = 1;
		print("   __ _             _                           ");
		print("  / _(_)_ __   __ _| |  ___  ___ ___  _ __ ___  ");
		print(" | |_| | '_ \\ / _` | | / __|/ __/ _ \\| '__/ _ \\ ");
		print(" |  _| | | | | (_| | | \\__ \\ (_| (_) | | |  __/ ");
		print(" |_| |_|_| |_|\\__,_|_| |___/\\___\\___/|_|  \\___| ");	                                               
		print("");
		print("");
		print("#1 " + players.get(0).getName());
		for (int i = 1; i < players.size(); i++) {
			if (players.get(i).getPoints() == players.get(i - 1).getPoints()) {
				print("#" + place + " " + players.get(i).getName());
			} else {
				place = i;
				print("#" + place + " " + players.get(i).getName());
			}
		}
	}
}
