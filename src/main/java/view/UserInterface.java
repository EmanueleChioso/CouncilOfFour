package view;

import java.util.List;
import java.util.Set;

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

public interface UserInterface {

	/**
	 * method that takes an array of strings and displays it like if it was a menu
	 * gives the possibility to choose between the numbers of the elements
	 * @param menu
	 * @return an integer that represent the choice of the user
	 */
	public int menu(String[] menu);

	/**
	 * asks the player the map
	 * @return the map index
	 */
	public int askMap();


	/**
	 * prints the map matrix
	 * @param map
	 */
	public void printMap(GameMap map);

	/**
	 * prints the detailed map
	 * @param map
	 * @param playersNames
	 */
	public void printDetailedMap(GameMap map, List<String> playersNames);

	/**
	 * print the council at the give index 
	 * @param i
	 * @param council
	 */
	public void printCouncil(int i, Council council);

	/**
	 * prints the councillors remaining in the pool
	 * @param pool
	 */
	public void printRemainingCouncilors(CouncillorsPool pool);

	/**
	 * prints all visible permit cards and asks to choose
	 * @return an integer representing the chosen permit
	 */
	public int askPermitToGetBetweenVisible();

	/**
	 * print player stat
	 * @param player
	 */
	public void printPlayer(Player player);

	/**
	 * print player stat and shows cards in hand
	 * @param player
	 */
	public void printDetailedPlayer(Player player);

	/**
	 * print all players stat
	 * @param players
	 */
	public void printAllPlayerStats(List<Player> players);

	/**
	 * print given player politic cards
	 * @param player
	 */
	public void printPoliticCards(Player player);

	/**
	 * print given player permit cards
	 * @param player
	 */
	public void printPermitCards(Player player);

	/**
	 * print given region index visible permit cards
	 * @param region
	 * @param permits
	 */
	public void printVisiblePermitcCards(int region, PermitCard[] permits);

	/**
	 * prints "council of four"
	 */
	public void welcome();

	/**
	 * print all possible actions 
	 * @param string
	 * @return an integer, representing the chosen action
	 */
	public int showPossibleActions(String[] string);

	/**
	 * prints a choice between primary and secondary 
	 * @return the chosen action
	 */
	public int askPrimaryOrSecondary();

	/**
	 * @param primaryOrSecondary
	 * @return
	 */
	public int askAction(int primaryOrSecondary);

	/**
	 * ask only primary action
	 * @return the number of the chosen action
	 */
	public int askPrimary();

	/**
	 * ask only fast action
	 * @return the number of the chosen action
	 */
	public int askSecondary();

	/**
	 * shows all items 
	 * @param items
	 * @return true if player wants to buy something
	 */
	public boolean askMarket(List<Sellable> items);

	/**
	 * print nobility track
	 * @param track
	 */
	public void printNobilityTrack(NobilityTrack track);

	/**
	 * print player stat, no num of permit and politic
	 * @param player
	 */
	public void printPlayerStat(Player player);

	/**
	 * print all cities between the one in which payer built 
	 * and asks what city bonus wants
	 * @return the chosen city
	 */
	public int askWhatCityBonus(List<City> cities);



	/**
	 * prints the drawn card
	 * @param card
	 */
	public void printDrawCard(PoliticCard card);

	/**
	 * prints the remaining action
	 * @param primary
	 * @param secondary
	 */
	void printActionsRemaining(boolean primary, boolean secondary);

	/**
	 * prints all councils 
	 * @param map
	 */
	public void printCouncils(GameMap map);

	/**
	 * asks what council 
	 * @return the chosen council
	 */
	public int askCouncil();

	/**
	 * prints all remaining councillors 
	 * @return the chosen councillor to change a council with
	 */
	public int askCouncillorToChange();

	/**
	 * show all player cards and asks what to use
	 * @return the list of cards that a player wants to use
	 */
	public List<Integer> askPlayerCardsToUse();

	/**
	 * @return the integer of the chosen region
	 */
	public int askPermitToChange();

	/**
	 * @return the integer of the chosen permit
	 */
	public int askPermitToBuy();

	/**
	 * @return the integer of the chosen council to change
	 */
	public int askCouncilChange();

	/**
	 * takes the number of permit cards player has in hands
	 * @param permits
	 * @return the integer of the chosen permit
	 */
	public int askpermitToUse(String[] permits);

	/**
	 * @param initials
	 * @return the integer of the chosen city
	 */
	public int askCity(String[] initials);

	/**
	 * prints king council
	 * @param council
	 */
	public void printKingCouncil(Council council);

	/**
	 * @return the integer of the chosen city
	 */
	public int askKingDestination();

	/**
	 * prints "it's your turn"
	 */
	public void itsYourTurn();

	/**
	 * @return the integer of the chose item
	 */
	public int askWhatToSell();

	/**
	 * shows player's permit cards and asks which one to sell
	 * @param cards
	 * @return the integer of the chosen card
	 */
	public int askWhatPermitCards(List<PermitCard> cards);

	/**
	 * shows player's politic cards and asks which one to sell
	 * @param cards
	 * @return the integer of the chosen card
	 */
	public int askWhatPoliticCards(List<PoliticCard> cards);

	/**
	 * asks how many assistants to put in sale
	 * @param assi
	 * @return the number of assistants to sell
	 */
	public int askWhatAssistants(int assi);

	/**
	 * @return the chosen cost
	 */
	public int askCost();

	/**
	 * @return true if the player wants to sell items
	 */
	public boolean askYouWannaSell();

	/**
	 * @return true if player wants to sell something more
	 */
	public boolean askYouWannaSellSomethingMore();

	/**
	 * @param items
	 * @return the integer of the chosen market item
	 */
	public int askWhatToBuy(List<Sellable> items);

	/**
	 * @param items
	 * @return true if player wants to buy again
	 */
	public boolean askWhatToBuyAgain(List<Sellable> items);

	/**
	 * asks the secondary action with pass possibility
	 * @return the integer of the chose action
	 */
	public int askSecondaryWithPass();

	/**
	 * print detail of action performed
	 * @param act
	 */
	public void showPerformedAction(int act);

	/**
	 * show how many assistants a player has
	 * @param player
	 */
	public void printAssistants(Player player);

	/**
	 * warning that player do not have enough assistants
	 * @param i
	 */
	public void warningAssistants(int i);

	/**
	 * warnings that player do not have enough coins
	 * @param coins
	 */
	public void warningCoins(int coins);

	/**
	 * warning a player do not have permits
	 */
	public void warningPermit();

	/**
	 * print changes during the action
	 * @param i
	 * @param name
	 * @param council
	 * @param returnString
	 */
	public void printChanges(int i, String name, int council, String returnString);

	/**
	 * print changes during the action
	 * @param i
	 * @param name
	 * @param num
	 */
	public void printChanges(int i, String name, int num);

	/**
	 * print changes during the action
	 * @param i
	 * @param name
	 */
	public void printChanges(int i, String name);

	/**
	 * perform simple string comunication
	 * @param communication
	 */
	public void simpleCommunication(String communication);

	/**
	 * shows final score
	 * @param players
	 */
	public void finalScore(List<Player> players);

}
