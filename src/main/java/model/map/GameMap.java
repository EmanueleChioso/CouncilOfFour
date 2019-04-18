package model.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import model.Const;
import model.bonus.Bonus;
import model.bonus.NoBonus;
import model.bonus.PrimitiveBonusFactory;
import model.player.Player;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna
 *
 */
public class GameMap extends Observable implements Serializable {

	private static final String[] CITYNAMES = { "Arkon", "Burgen", "Castrum", "Dorful", "Esti", "Framek", "Graden",
			"Hellar", "Indur", "Juvelar", "Kultos", "Lyram", "Merkatim", "Naris", "Osium" };
	private static final String[] BASICBONUSES = { "poin1", "poin2", "poin3", "assi1", "assi2", "coin1", "coin2",
			"coin3", "poli1", "nobi1", "nobi1" };
	private static final CityColor[] COLORS = { CityColor.GOLD, CityColor.BRONZE, CityColor.SILVER, CityColor.BLUE,
			CityColor.SILVER, CityColor.GOLD, CityColor.SILVER, CityColor.GOLD, CityColor.BRONZE, CityColor.PURPLE,
			CityColor.GOLD, CityColor.BLUE, CityColor.SILVER, CityColor.BRONZE, CityColor.GOLD };

	private static final String[] BASICSOUBLEBONUSES = { "assi1&coin1", "assi1&poli1", "coin1&poin1" };

	private static final long serialVersionUID = -4613696808169491703L;
	private Region seaLand;
	private Region hillLand;
	private Region mountainLand;
	private boolean[][] matrix;
	private KingsLanding kingsLanding;
	private City[] cities;
	private int players;

	/**
	 * creates a standard map and initiates regions and cities.
	 */
	public GameMap() {

		this.matrix = new boolean[Const.CITIES][Const.CITIES];
		initMatrix();
		this.cities = new City[Const.CITIES];
		initCities();
		int citiesPerRegion = Const.CITIES / Const.REGIONS;
		this.seaLand = new Region(Arrays.copyOf(this.cities, citiesPerRegion));
		this.hillLand = new Region(Arrays.copyOfRange(this.cities, 1 + citiesPerRegion, 2 * citiesPerRegion));
		this.mountainLand = new Region(Arrays.copyOfRange(this.cities, 1 + 2 * citiesPerRegion, 3 * citiesPerRegion));
		this.kingsLanding = new KingsLanding(cities[Const.INITIALKINGCITY]);
	}

	/**
	 * randomize a matrix to create a random map
	 */
	protected void randomizeMatrix(double difficult) {
		do {

			initMatrix();

			createOnlyThreeRandomConnections();

			randomizeConnectionsInsideRegions( difficult);

			for (int i = 0; i < Const.CITIES; i++)
				matrix[i][i] = false;
		} while (mapQualityCheckNotPassed());
	}

	/**
	 * randomize all connections insede the regions
	 */
	private void randomizeConnectionsInsideRegions(double perc) {
		for (int i = 0; i < Const.CITIES; i++) {
			for (int j = i + 1; j < Const.CITIES; j++) {
				randomizeConnection(i, j, perc);
			}
		}

	}

	/**
	 * takes the indexes of the matrix and randomize the connection between
	 * them.
	 * 
	 * @param i
	 * @param j
	 */
	private void randomizeConnection(int i, int j, double perc) {
		if (insideASingleRegion(i, j)) {
			if (Math.random() < perc) {

				addConnection(i, j);
			} else {
				removeConnection(i, j);
			}
		}
	}

	/**
	 * 
	 * @param i
	 * @param j
	 * @return true if the I and J combination is connection inside a region.
	 *         not a connection between regions
	 */
	private boolean insideASingleRegion(int i, int j) {
		if (i < 5 && j < 5)
			return true;
		if (insideHillRegion(i, j))
			return true;
		if (i > 9 && j > 9)
			return true;
		return false;
	}

	/**
	 * a silble method that returns true if the indexes given are a connection
	 * between the Hill region. usefull to improve readbility of the code
	 * 
	 * @param i
	 * @param j
	 * @return true if the indexes are a connection inside Hill (from/to hill
	 *         city)
	 */
	private boolean insideHillRegion(int i, int j) {
		if (i <= 4)
			return false;
		if (j <= 4)
			return false;
		if (i >= 10)
			return false;
		if (j >= 10)
			return false;
		return true;
	}

	/**
	 * create 3 and only 3 connections between first region and second, does the
	 * same for the second and third. it works on the matrix of the GameMap.
	 * 
	 */
	private void createOnlyThreeRandomConnections() {
		int city1;
		int city2;
		int cont = 0;
		while (cont < 3) {
			city1 = (int) (Math.random() * 5);
			city2 = (int) (Math.random() * 5);
			if (addConnection(city1, city2 + 5))
				cont++;
		}
		cont = 0;
		while (cont < 3) {
			city1 = (int) (Math.random() * 5);
			city2 = (int) (Math.random() * 5);
			if (addConnection(city1 + 5, city2 + 10))
				cont++;

		}

	}

	/**
	 * qualityChecks to call to see if a random map ha got every city connected
	 * to all the others, to see if there are no more than 3 connections between
	 * the cities and to see if there is no seaCity connected to a Mountain One
	 * 
	 * @return true if the qualitCheck is >NOT< ssed 
	 * 
	 */
	public boolean mapQualityCheckNotPassed() {
		if (seaMountainConnectionFound()) {
			return true;
		}
		if (notThreeRegionConnections()) {
			return true;
		}
		if (foundLoneCity()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return true if the check is not passed 
	 */
	private boolean notThreeRegionConnections() {
		int count1 = 0;
		int count2 = 0;
		for (int i = 0; i < 5; i++)
			for (int j = 5; j < 10; j++)
				if (matrix[i][j])
					count1++;
		for (int i = 5; i < 10; i++)
			for (int j = 10; j < 15; j++)
				if (matrix[i][j])
					count2++;
		if (count1 == 3 && count2 == 3)
			return false;
		return true;
	}

	/**
	 * RICEVE l' INTERO TRA 0 E 14. 0 e 14!!! return the cost that would need to
	 * move the king from a place to another. used a DFS that simply returns
	 * when hits the city. used SET instead ArrayList to manage duplications and
	 * make a simplier alghoritm
	 * 
	 * @param destination
	 * @return
	 */
	public int kingCost(int destination) {
		City tmp = cities[destination];
		City king = kingsLanding.getKingLocation();
		if (king.equals(tmp))
			return 0;
		return realKingCostCalculation(tmp);
	}

	/**
	 * return the cost that would need to move the king from a place to another.
	 * used a DFS that simply returns when hits the city. used SET instead
	 * ArrayList to manage duplications and make a simplier alghoritm
	 * 
	 * @param destination
	 *            is a CITY
	 * @return the cost to move the king. the cost is 2*every city he crosses.
	 *         return 999 if error occurres
	 */
	public int kingCost(City destination) {
		City king = kingsLanding.getKingLocation();
		if (king.equals(destination))
			return 0;
		return realKingCostCalculation(destination);
	}

	private int realKingCostCalculation(City destination) {
		int cost = 0;
		City king = kingsLanding.getKingLocation();
		Set<City> toVisit = new HashSet<>();
		for (City c : cities) {
			toVisit.add(c);
		}
		toVisit.remove(king);

		Set<City> visited = new HashSet<>();
		visited.add(king);

		Set<City> thisTurnVisit = getAdjCities(king);
		Set<City> nextTurnVisit = new HashSet<>();

		for (int i = 0; i < Const.CITIES && !toVisit.isEmpty(); i++) {
			cost++;

			// se la trovo ora. ok si ritorna
			for (City c : thisTurnVisit) {
				if (c.equals(destination))
					return cost * 2;

			}
			// se non l' ho trovata.. queste le ho visitate e le loro adiacenti
			// le devo visitare
			for (City c : thisTurnVisit) {
				nextTurnVisit.addAll(getAdjCities(c));
				visited.add(c);
				toVisit.remove(c);
			}

			for (City c : visited) {
				if (nextTurnVisit.contains(c))
					nextTurnVisit.remove(c);
				toVisit.remove(c);
			}

			thisTurnVisit.clear();
			thisTurnVisit.addAll(nextTurnVisit);
			nextTurnVisit.clear();

		}

		return 999;
	}

	/**
	 * receives a city and a player, returns all the cities that have and
	 * emporium of that player, connected to the one given.
	 * 
	 * @param city
	 * @param player
	 * @return the cities connected as a SET, including the one given by param
	 */
	public Set<City> getPlayerCitiesConnectedToThisOne(City city, Player player) {

		Set<City> connected = new HashSet<>();
		connected.add(city);

		Set<City> thisTurnVisit = getAdjCities(city);
		Set<City> nextTurnVisit = new HashSet<>();

		while (!thisTurnVisit.isEmpty()) {
			// se la trovo ed ha un emporio, la aggiungo alla return.
			for (City c : thisTurnVisit) {
				if (c.playerBuiltHere(player)) {
					connected.add(c);
					nextTurnVisit.addAll(getAdjCities(c));
				}
			}
			thisTurnVisit.clear();
			thisTurnVisit.addAll(nextTurnVisit);

			nextTurnVisit.clear();

		}

		return connected;

	}

	/**
	 * @param city
	 * @return the adjcient cities to the one given
	 */
	private Set<City> getAdjCities(City city) {
		int cityNum = getCityIndex(city);
		Set<City> ret = new HashSet<>();
		for (int i = 0; i < Const.CITIES; i++) {
			if (matrix[cityNum][i])
				ret.add(cities[i]);
		}
		return ret;
	}

	/**
	 * searches for the city and returns the index
	 * @param city
	 * @return the city given. -1 for error
	 */
	private int getCityIndex(City city) {
		for (int i = 0; i < cities.length; i++) {
			if (cities[i].equals(city))
				return i;
		}

		return -1;
	}

	public City getKingLocation() {
		return kingsLanding.getKingLocation();

	}

	/**
	 * @return true if there a city not connected to any other
	 */
	public boolean foundLoneCity() {
		int cityPerRegion = Const.CITIES / Const.REGIONS;
		boolean[] flag = new boolean[cityPerRegion];

		for (int i = 0; i < 3; i++) {
			for (int l = 0; l < 5; l++) {
				for (int k = 0; k < 5; k++) {
					flag[k] = matrix[i * 5 + l][i * 5 + k];
				}
				if (allFalseBoolean(flag))
					return true;
			}
		}
		return false;
	}

	/**
	 * init the bonuses, colors, and gives the bonuses randomly
	 * 
	 */
	public void initCities() {
		List<Bonus> temp = new ArrayList<>();
		for (String b : BASICBONUSES) {
			temp.add(new Bonus(PrimitiveBonusFactory.getPrimitiveBonus(b)));
		}
		for (String doubleb : BASICSOUBLEBONUSES) {
			temp.add(new Bonus(PrimitiveBonusFactory.getPrimitiveBonus(doubleb.split("&")[0]),
					PrimitiveBonusFactory.getPrimitiveBonus(doubleb.split("&")[1])));
		}
		Collections.shuffle(temp);

		for (int i = 0; i < Const.CITIES; i++) {

			if (i == 9) {

				cities[i] = new City(CITYNAMES[i], new NoBonus(), COLORS[i]);
			}

			else if (i < 9) {
				cities[i] = new City(CITYNAMES[i], temp.get(i), COLORS[i]);
			} else {
				cities[i] = new City(CITYNAMES[i], temp.get(i - 1), COLORS[i]);

			}

		}

	}

	/**
	 * inits the boolean matrix to false
	 */
	public void initMatrix() {
		for (int i = 0; i < Const.CITIES; i++) {
			for (int j = 0; j < Const.CITIES; j++) {
				matrix[i][j] = false;
			}
		}
	}

	/**
	 * @return true if it's found a connection between the two regions
	 */
	private boolean seaMountainConnectionFound() {
		for (int i = 0; i < 5; i++) {
			for (int j = 10; j < 15; j++) {
				if (matrix[i][j] || matrix[j][i])
					return true;
			}
		}
		return false;
	}

	/**
	 * @param array
	 * @return true if the array of arrays given is all false
	 */
	private boolean allFalseBoolean(boolean[] array) {
		for (boolean b : array)
			if (b)
				return false;
		return true;
	}

	/**
	 * add the connection between city1 and city2, returns true if they were not
	 * connected return false they were already connected
	 * 
	 * @param city1
	 * @param city2
	 * @return false if it didn't do anything and the connection already existed
	 */
	public boolean addConnection(int city1, int city2) {
		if (matrix[city1][city2])
			return false;
		matrix[city1][city2] = true;
		matrix[city2][city1] = true;
		return true;

	}

	/**
	 * removes the connection between city1 and city2, returns true if the
	 * connection existed, return false if it already was false.
	 * 
	 * @param int
	 *            city1
	 * @param int
	 *            ity2
	 */
	public boolean removeConnection(int city1, int city2) {
		if (!matrix[city1][city2])
			return false;
		matrix[city1][city2] = false;
		matrix[city2][city1] = false;
		return true;
	}

	public boolean getConnection(int city1, int city2) {
		return matrix[city1][city2];
	}

	public City getCity(String i) {
		char a = i.charAt(0);
		for (City c : cities)
			if (c.getname().charAt(0) == a)
				
				return c;
		return null;
	}

	public City getCity(int i) {
		return cities[i];
	}

	public City getCity(char initial) {
		return cities[initial - 'A'];
	}

	public int getCityN(char initial) {
		return initial - 'A';
	}

	public City[] getCities() {
		return cities;
	}

	public Region getSeaRegion() {
		return this.seaLand;
	}

	public Region getHillRegion() {
		return this.hillLand;
	}

	public Region getMountainRegion() {
		return this.mountainLand;
	}

	public KingsLanding getKingsLanding() {
		return this.kingsLanding;
	}

	/**
	 * @return the council of the index 0-sea 1-hill 2-mountain 3-king
	 */
	public Council getCouncil(int i) {
		if (i == 0)
			return seaLand.getCouncil();
		if (i == 1)
			return hillLand.getCouncil();
		if (i == 2)
			return mountainLand.getCouncil();
		if (i == 3)
			return kingsLanding.getCouncil();

		return null;
	}

	/**
	 * @param i
	 * @return the region of the index 0-sea 1-hill 2-mountain
	 */
	public Region getRegion(int i) {
		if (i == 0)
			return this.seaLand;
		if (i == 1)
			return this.hillLand;
		if (i == 2)
			return this.mountainLand;

		return null;
	}

	/**
	 * checks if the player is building a city that would give him a Color bonus.
	 * if the player does, he gets the bonus and eventually the king prize if available
	 * @param player
	 * @param city
	 */
	public boolean checkColorBonus(Player player, CityColor col) {
		Set<City> sameColorCities = new HashSet<>();
		for (City c : cities) {
			if (c.getColor().equals(col))
				sameColorCities.add(c);
		}
		boolean playerBuiltHere;

		for (City c : sameColorCities) {
			playerBuiltHere = false;
			for (Player p : c.getEmporiums()) {
				if (p.equals(player)) {
					playerBuiltHere = true;
				}
			}
			if (!playerBuiltHere) {
				return false;
			}
		}
		return true;
	}

	/**
	 * checks if the player is building a city that would give him a region bonus.
	 * if the player does, he gets the bonus and eventually the king prize if available
	 * @param player
	 * @param city
	 */
	public boolean checkRegionBonus(Player player, int region) {
		boolean playerBuiltHere;
		for (City c : getRegion(region).getCities()) {
			playerBuiltHere = false;
			for (Player p : c.getEmporiums()) {
				if (p.equals(player)) {
					playerBuiltHere = true;
				}
			}
			if (!playerBuiltHere) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param city
	 * @return the region of the city, -1 for error
	 */
	public int getRegionNumFromCity(City city) {
		for (int i = 0; i < Const.CITIESPERREGION; i++) {
			if (this.seaLand.getCity(i).equals(city))
				return 0;
			else if (this.hillLand.getCity(i).equals(city))
				return 1;
			else if (this.mountainLand.getCity(i).equals(city))
				return 2;
		}
		return -1;
	}

	/**
	 * @param initialPlayers
	 */
	public void setInitialPlayers(int initialPlayers) {
		this.players = initialPlayers;
	}

	public int getNumPlayers() {
		return this.players;
	}

	/**
	 * does not modify anythig, returns the set of cities where he built
	 * emporiums
	 * 
	 * @param player
	 * @return the set of cities where he has got an emporium
	 */
	public List<City> getPlayerCities(Player player) {
		List<City> ret = new ArrayList<>();
		for (City c : cities) {
			for (Player p : c.getEmporiums()) {
				if (p.equals(player)) {
					ret.add(c);
				}
			}
		}
		return ret;

	}
}
