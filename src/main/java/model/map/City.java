package model.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.bonus.Bonus;
import model.player.Player;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class City implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9079881252810333341L;
	private String name;
	private Bonus bonus;
	private CityColor color;
	private ArrayList<Player> emporiums;


	/**
	 * crates a city with given attributes
	 * @param name
	 * @param bonus
	 * @param color
	 */
	public City(String name, Bonus bonus, CityColor color) {
		this.name = name;
		this.bonus = bonus;
		this.color = color;
		emporiums = new ArrayList<>();
	}
	
	/**
	 * @param bonus
	 */
	public void setBonusType(Bonus bonus) {
		this.bonus = bonus;
	}

	/**
	 * @return the city's bonus
	 */
	public Bonus getBonus() {
		return this.bonus;
	}

	

	/**
	 * @return the city's name
	 */
	public String getname() {
		return this.name;
	}

	/**
	 * @return the initial letter of the city's name
	 */
	public char getLetter() {
			return this.name.charAt(0);
		
	}

	/**
	 * @return the initial letter of the city's name, casted to an integer
	 */
	public int getInt() {
		char first = name.charAt(0);
		if (first >= 'A' && first <= 'Z')
			return first - 'A';
		if (first >= 'a' && first <= 'z')
			return first - 'a';
		return -1;
	}

	/**
	 * returns true if the city has got an emporium in this city 
	 * returns false if the player is NULL
	 * @param player
	 * @return true if the player has and emporium here
	 */
	public boolean playerBuiltHere(Player player) {
		if (emporiums.contains(player))
			return true;
		return false;
	}

	/**
	 * @return the list of player that built in this city 
	 */
	public List<Player> getEmporiums() {
		return this.emporiums;
	}

	/**
	 * add a player to the emporiums arrayList
	 * @param player
	 */
	public void setEmporium(Player player) {
		emporiums.add(player);
		player.subEmporium();
	}

	/**
	 * @return this city's color
	 */
	public CityColor getColor() {
		return this.color;
	}
}
