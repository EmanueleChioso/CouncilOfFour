package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import model.Const;
import model.PrizesHandler;
import model.cards.CardsPool;
import model.cards.PermitDeck;
import model.cards.PoliticDeck;
import model.map.City;
import model.map.CityColor;
import model.map.Council;
import model.map.CouncillorsPool;
import model.map.GameMap;
import model.map.KingsLanding;
import model.map.NobilityTrack;
import model.map.Region;
import model.player.Player;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna
 *
 */
public class Game implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1150010904957845915L;

	private List<Player> players;

	private transient CardsPool caPool;
	private transient PrizesHandler prizes;
	private CouncillorsPool coPool;
	private GameMap map;
	private Region sea;
	private Region hill;
	private Region mountain;
	private KingsLanding kingsLanding;
	private PoliticDeck poDeck;
	private PermitDeck seaDeck;
	private PermitDeck hillDeck;
	private PermitDeck mountainDeck;
	private NobilityTrack nobTrack;
	private Council seaCouncil;
	private Council hillCouncil;
	private Council mountainCouncil;
	private Council kingCouncil;

	/**
	 * constructor, creates cards pool, councillors pool and prizes
	 */
	public Game() {
		caPool = new CardsPool(sea, hill, mountain);
		coPool = new CouncillorsPool();
		prizes = new PrizesHandler();
	}

	/**
	 * @param giocatori
	 */
	public void addPlayers(List<String> giocatori) {
		players = new ArrayList<>();

		for (String client : giocatori) {
			players.add(new Player(client));
		}
		// init players stats
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setAssistants(i + 1);
			players.get(i).setCoin(i + 10);
		}
	}

	/**
	 * initialize game settings, councils, councillors, decks, regions, nobility
	 * track
	 */
	public void initGame() {
		nobTrack = new NobilityTrack();
		// copying regions from map
		sea = map.getSeaRegion();
		hill = map.getHillRegion();
		mountain = map.getMountainRegion();
		kingsLanding = map.getKingsLanding();
		// setting up decks
		poDeck = new PoliticDeck();
		poDeck.createDeck();
		seaDeck = new PermitDeck();
		hillDeck = new PermitDeck();
		mountainDeck = new PermitDeck();
		seaDeck.initDeck(caPool.getSeaDeck());
		hillDeck.initDeck(caPool.getHillDeck());
		mountainDeck.initDeck(caPool.getMountainDeck());
		// assigning decks to regions
		sea.setPermitDeck(seaDeck);
		hill.setPermitDeck(hillDeck);
		mountain.setPermitDeck(mountainDeck);
		// showing first two cards for each permit deck
		sea.initVisibCards(seaDeck.draw(), seaDeck.draw());
		hill.initVisibCards(hillDeck.draw(), hillDeck.draw());
		mountain.initVisibCards(mountainDeck.draw(), mountainDeck.draw());
		// randomizing council creation
		seaCouncil = new Council(coPool.removeCouncillorToCreateCouncil(), coPool.removeCouncillorToCreateCouncil(),
				coPool.removeCouncillorToCreateCouncil(), coPool.removeCouncillorToCreateCouncil());
		hillCouncil = new Council(coPool.removeCouncillorToCreateCouncil(), coPool.removeCouncillorToCreateCouncil(),
				coPool.removeCouncillorToCreateCouncil(), coPool.removeCouncillorToCreateCouncil());
		mountainCouncil = new Council(coPool.removeCouncillorToCreateCouncil(),
				coPool.removeCouncillorToCreateCouncil(), coPool.removeCouncillorToCreateCouncil(),
				coPool.removeCouncillorToCreateCouncil());
		kingCouncil = new Council(coPool.removeCouncillorToCreateCouncil(), coPool.removeCouncillorToCreateCouncil(),
				coPool.removeCouncillorToCreateCouncil(), coPool.removeCouncillorToCreateCouncil());
		// setting councils in place
		sea.setCouncil(seaCouncil);
		hill.setCouncil(hillCouncil);
		mountain.setCouncil(mountainCouncil);
		kingsLanding.setCouncil(kingCouncil);

		nobTrack = new NobilityTrack();
		// give all players 6 cards
		for (int i = 0; i < players.size(); i++) {
			for (int j = 0; j < 6; j++) {
				players.get(i).addCard(poDeck.draw());
			}
		}
	}

	/**
	 * sets the map chosen by first player
	 * 
	 * @param map
	 * @param initialPlayers
	 */
	public void setMap(GameMap map, int initialPlayers) {
		this.map = map;
		map.setInitialPlayers(initialPlayers);
	}

	/**
	 * used in tests and it is the simply GETTER for the map
	 * @return the gamemap
	 */
	public GameMap getMap(){
		return this.map;
	}
	/**
	 * @param name
	 * @return the player identified by his name
	 */
	public Player getPlayer(String name) {
		Player ret = null;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getName().equals(name)) {
				ret = players.get(i);
			}
		}
		return ret;

	}

	/**
	 * @return a list containing all players
	 */
	public List<Player> getAllPlayers() {
		return this.players;
	}

	/**
	 * used to get remaining councillors in pool or to return the object
	 * 
	 * @return the councillors pool
	 */
	public CouncillorsPool getCouncilorsPool() {
		return this.coPool;
	}

	/**
	 * @return game's nobility track
	 */
	public NobilityTrack getNobilityTrack() {
		return this.nobTrack;
	}

	/**
	 * @return game's politic deck
	 */
	public PoliticDeck getPoliticDeck() {
		return this.poDeck;
	}

	/**
	 * used to perform build action, it build the emporium in the specified city
	 * and add the player to the list of players that built in that city.
	 * activate city bonus on player
	 * 
	 * @param player
	 * @param city
	 */
	public synchronized boolean buildEmporiumAndAddBonusesToPlayer(Player player, City city) {
		city.setEmporium(player);
		Set<City> connectedCities = map.getPlayerCitiesConnectedToThisOne(city, player);
		for (City c : connectedCities) {
			c.getBonus().activateBonus(player);
			Logger.getGlobal().info("il player " + player.getName() + " ha preso il seguente bonus per aver costruito: "
					+ c.getBonus().toString());
		}

		activateRegionBonuses(player, city);

		activateColorBonuses(player, city);
		if (player.getEmporiums() <= 10 - Const.EMPORIUMSTOWIN)
			return true;
		return false;

	}

	/**
	 * used to activate cities color bonus
	 * 
	 * @param player
	 * @param city
	 */
	private void activateColorBonuses(Player player, City city) {
		CityColor col = city.getColor();
		if (!col.equals(CityColor.PURPLE) && map.checkColorBonus(player, col)
				&& prizes.checkColorPrizeAvailability(col)) {
			prizes.useColorPrize(player, col);
			if (prizes.checkKingPrizeAvailability()) {
				prizes.useKingPrize(player);
			}

		}

	}

	/**
	 * used to activate regions bonus
	 * 
	 * @param player
	 * @param city
	 */
	private void activateRegionBonuses(Player player, City city) {
		int region = map.getRegionNumFromCity(city);
		if (map.checkRegionBonus(player, region) && prizes.checkRegionPrizeAvailability(region)) {
			prizes.useRegionPrize(player, region);
			if (prizes.checkKingPrizeAvailability()) {
				prizes.useKingPrize(player);
			}
		}

	}

	/**
	 * @return this game's prizes handler
	 */
	public PrizesHandler getPrizes() {
		return this.prizes;
	}

	public List<Player> getWinner() {
		checkNobility();
		checkPermit();
		List<Player> ret = new ArrayList<>(players);
		Collections.sort(ret, new MyComparator("poin"));
		return ret;
	}

	private void checkNobility() {
		int contPrimo = 1;
		int i;
		int contSecondo = 1;
		List<Player> nobPl = new ArrayList<>();
		nobPl.addAll(players);
		Collections.sort(nobPl, new MyComparator("nobi"));
		for (i = 1; i < nobPl.size(); i++) {
			if (nobPl.get(i).getNobility() == nobPl.get(0).getNobility()) {
				contPrimo++;
			} else {
				break;
			}
		}
		if (contPrimo < 2) {
			for (int j = i; j < nobPl.size(); j++) {
				if (nobPl.get(j).getNobility() == nobPl.get(i - 1).getNobility()) {
					contSecondo++;
				}
			}
			nobPl.get(0).addPoints(5);
			for (int j = i; j < i + contSecondo; j++) {
				nobPl.get(j).addPoints(2);
			}
		} else {
			for (int k = 0; k < contPrimo; k++) {
				nobPl.get(k).addPoints(5);
			}
		}
	}

	private void checkPermit() {
		int contPrimo = 1;
		List<Player> permPl = new ArrayList<>();
		permPl.addAll(players);
		Collections.sort(permPl, new MyComparator("perm"));
		for (int i = 1; i < permPl.size(); i++) {
			if (permPl.get(i).getNumPermitCard() == permPl.get(0).getNumPermitCard()) {
				contPrimo++;
			}
		}
		for (int i = 0; i < contPrimo; i++) {
			permPl.get(i).addPoints(3);
		}
	}

	private class MyComparator implements Comparator<Player> {

		private String what;

		/**
		 * 
		 */
		public MyComparator(String whatToCompare) {
			what = whatToCompare;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Player o1, Player o2) {

			if ("nobi".equals(what))
				return compareNobi(o1, o2);
			else if ("perm".equals(what))
				return comparePerm(o1, o2);
			else
				// if("poin".equals(what))
				// tolto solo per abbassare la complessita ciclomatica e la
				// copertura. se ti serve riaggiungila! :D ggwp
				return comparePoin(o1, o2);

		}

		private int compareNobi(Player o1, Player o2) {
			return o1.getNobility() > o2.getNobility() ? -1 : o1.getNobility() < o2.getNobility() ? 0 : 1;
		}

		private int comparePerm(Player o1, Player o2) {
			return o1.getNumPermitCard() > o2.getNumPermitCard() ? -1
					: o1.getNumPermitCard() < o2.getNumPermitCard() ? 0 : 1;
		}

		private int comparePoin(Player o1, Player o2) {
			return o1.getPoints() > o2.getPoints() ? -1 : o1.getPoints() < o2.getPoints() ? 0 : 1;
		}
	}

}