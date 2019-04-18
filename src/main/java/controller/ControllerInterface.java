/**
 * 
 */
package controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

import model.cards.PermitCard;
import model.cards.PoliticCard;
import model.map.Council;
import model.map.CouncillorsPool;
import model.map.GameMap;
import model.map.NobilityTrack;
import model.market.Sellable;
import model.player.Player;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna
 *
 */
/**
 * @author Emanuele Chioso, Lorenzo Della Penna
 *
 */
public interface ControllerInterface extends Remote {
	
	

	/**
	 * * @param name
	 * 
	 * @return "S" if it's this client's turn
	 * @return "N" if it's not this client turn
	 * @return "sMarket" if it's sell market turn
	 * @return "M" if it's buy market turn
	 * @return "DC" if client is disconnected
	 * 
	 * @throws RemoteException
	 */
	public String askTurn(String name) throws RemoteException;
	
	/**
	 * @param name
	 * @throws RemoteException
	 */
	public void passaMarket(String name) throws RemoteException;

	/**
	 * @param giocatori
	 * @throws RemoteException
	 */
	void avviaPartita(List<String> giocatori) throws RemoteException;

	/**
	 * @return
	 * @throws RemoteException
	 */
	public boolean mapSetted() throws RemoteException;

	/**
	 * @param map
	 * @throws RemoteException
	 */
	public void setMap(GameMap map) throws RemoteException;

	/**
	 * @return
	 * @throws RemoteException
	 */
	public GameMap getMap() throws RemoteException;

	/**
	 * @param name
	 * @return
	 * @throws RemoteException
	 */
	public Player getPlayer(String name) throws RemoteException;

	/**
	 * @return
	 * @throws RemoteException
	 */
	public String update() throws RemoteException;

	/**
	 * @return
	 * @throws RemoteException
	 */
	public boolean needToUpdate() throws RemoteException;

	/**
	 * @return
	 * @throws RemoteException
	 */
	public CouncillorsPool getCounciorsPool() throws RemoteException;

	/**
	 * @return
	 * @throws RemoteException
	 */
	public List getAllPlayersStat() throws RemoteException;

	/**
	 * @return
	 * @throws RemoteException
	 */
	public Game getGame() throws RemoteException;

	/**
	 * @param name
	 * @return
	 * @throws RemoteException
	 */
	public PoliticCard drawCard(String name) throws RemoteException;

	/**
	 * @param name
	 * @param index
	 * @param region
	 * @return
	 * @throws RemoteException
	 */
	public PermitCard drawCard(String name, int index, int region) throws RemoteException;

	/**
	 * @param name
	 * @param council
	 * @param permit
	 * @param cards
	 * @return
	 * @throws RemoteException
	 */
	public String firstPrimaryAction(String name, int council, int permit, List<Integer> cards) throws RemoteException;

	/**
	 * @param name
	 * @param cards
	 * @param destination
	 * @return
	 * @throws RemoteException
	 */
	public String secondPrimaryAction(String name, List<Integer> cards, int destination) throws RemoteException;

	/**
	 * @param name
	 * @param council
	 * @param councillor
	 * @return
	 * @throws RemoteException
	 */
	public String thirdPrimaryAction(String name, int council, int councillor) throws RemoteException;

	/**
	 * @param name
	 * @param permit
	 * @param letter
	 * @return
	 * @throws RemoteException
	 */
	public String fourthPrimaryAction(String name, int permit, int letter) throws RemoteException;

	/**
	 * @param name
	 * @return
	 * @throws RemoteException
	 */
	public String firstFastAction(String name) throws RemoteException;

	/**
	 * @param name
	 * @param region
	 * @return
	 * @throws RemoteException
	 */
	public String secondFastAction(String name, int region) throws RemoteException;

	/**
	 * @param name
	 * @param council
	 * @param councillor
	 * @return
	 * @throws RemoteException
	 */
	public String thirdFastAction(String name, int council, int councillor) throws RemoteException;

	/**
	 * @param name
	 * @return
	 * @throws RemoteException
	 */
	public String fourthFastAction(String name) throws RemoteException;

	/**
	 * @param name
	 * @return
	 * @throws RemoteException
	 */
	public boolean checkPrimaryAction(String name) throws RemoteException;

	/**
	 * @param name
	 * @return
	 * @throws RemoteException
	 */
	public boolean checkFastAction(String name) throws RemoteException;

	/**
	 * @param index
	 * @return
	 * @throws RemoteException
	 */
	public PermitCard[] getVisiblePermitCards(int index) throws RemoteException;

	/**
	 * @param name
	 * @throws RemoteException
	 */
	public void passaTurnoPrimary(String name) throws RemoteException;

	/**
	 * @param name
	 * @throws RemoteException
	 */
	public void passaTurnoSecondary(String name) throws RemoteException;

	/**
	 * @param name
	 * @return
	 * @throws RemoteException
	 */
	public String passaTurnoMarketCompra(String name) throws RemoteException;

	/**
	 * @param name
	 * @return
	 * @throws RemoteException
	 */
	public String passaTurnoMarketVendi(String name) throws RemoteException;

	/**
	 * @param name
	 * @param politicCards
	 * @param cost
	 */
	public boolean sellPolitcCards(String name, int politicCards, int cost) throws RemoteException;;

	/**
	 * @param name
	 * @param permitCards
	 * @param cost
	 */
	public boolean sellPermitCards(String name, int permitCards, int cost) throws RemoteException;;

	/**
	 * @param name
	 * @param assistants
	 * @param cost
	 */
	public void sellAssistants(String name, int assistants, int cost) throws RemoteException;

	/**
	 * @return
	 * @throws RemoteException
	 */
	public List<Sellable> getMarketContent() throws RemoteException;

	/**
	 * @param name
	 * @param action2
	 */
	public String buyItem(String name, int action2) throws RemoteException;

	/**
	 * @param name
	 * @return
	 * @throws RemoteException
	 */
	public String iAmNotAFastAction(String name) throws RemoteException;

	/**
	 * @param num
	 * @return
	 */
	public Council getCouncil(int num) throws RemoteException;

	/**
	 * @param name
	 * @return
	 */
	public List<PermitCard> getPermitCards(String name) throws RemoteException;

	/**
	 * @param name
	 * @return
	 */
	public List<PoliticCard> getPoliticCards(String name) throws RemoteException;

	/**
	 * @param name
	 * @return
	 */
	public int getAssistants(String name) throws RemoteException;

	/**
	 * @return
	 * @throws RemoteException
	 */
	public NobilityTrack getNobilityTrack() throws RemoteException;

	/**
	 * @return
	 * @throws RemoteException
	 */
	public int getNumPlayers() throws RemoteException;

	/**
	 * @return
	 * @throws RemoteException
	 */
	public List<String> getPlayersNames() throws RemoteException;

	/**
	 * @return
	 * @throws RemoteException
	 */
	public Set<Sellable> getMarketItemsOnSale() throws RemoteException;

	/**
	 * @return
	 * @throws RemoteException
	 */
	public List<Player> finalScore() throws RemoteException;

}
