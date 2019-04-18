package model.map;

import java.io.Serializable;

import model.Const;
import model.cards.PermitCard;
import model.cards.PermitDeck;



/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class Region implements Serializable {

	private static final long serialVersionUID = 1568338502013140735L;
	private City[] cities;
	private PermitCard[] visiblePermits;
	private PermitDeck permitDeck;
	private Council council;

	/**
	 * @param regionCities
	 */
	public Region(City[] regionCities) {
		this.cities = regionCities;
		this.visiblePermits = new PermitCard[Const.VISIBLEPERMITS];
		
	}

	/**
	 * empty constructor, used only for testing!
	 */
	public Region() {
		this.permitDeck = new PermitDeck();
		this.visiblePermits = new PermitCard[Const.VISIBLEPERMITS];
		this.cities = new City[Const.CITIES];
	}
	
	/**
	 * @return the permitDeck
	 */
	public PermitDeck getPermitDeck() {
		return permitDeck;
	}

	/**
	 * @return the council
	 */
	public Council getCouncil() {
		return council;
	}

	/**
	 * @param council
	 */
	public void setCouncil(Council council) {
		this.council = council;
	}

	/**
	 * @param permitDeck
	 */
	public void setPermitDeck(PermitDeck permitDeck) {
		this.permitDeck = permitDeck;
	}


	/**
	 * @param i
	 * @return the city to the specified index
	 */
	public City getCity(int i) {
		return cities[i];
	}

	/**
	 * set the city given to the specified index
	 * @param i
	 * @param city
	 */
	public void setCity(int i, City city) {
		this.cities[i] = city;
	}

	/**
	 * @return the number of cities
	 */
	public int citiesNum() {
		return cities.length;
	}

	/**
	 * takes two cards and sets them to visib permit cards
	 * @param card0
	 * @param card1
	 */
	public void initVisibCards(PermitCard card0, PermitCard card1) {
		visiblePermits[0] = card0;
		visiblePermits[1] = card1;
	}

	/**
	 * takes the card to the specified index, removes it form visible permit
	 * cards and replace it with the first card of the deck
	 * 
	 * @param index
	 * @return PermitCard card
	 */
	public PermitCard getAndReplaceVisibleCard(int index) {
		PermitCard ret;

		ret = visiblePermits[index];

		visiblePermits[index] = permitDeck.draw();
		return ret;
	}

	/**
	 * Warning: is a void, changes the card, it doesn't return anything, it must be used only 
	 * with fast action
	 */
	public void replaceVisiblePermitCards() {

		PermitCard temp;
		for (int i = 0; i < visiblePermits.length; i++) {
			temp = visiblePermits[i];
			visiblePermits[i] = permitDeck.draw();
			permitDeck.reinsertCard(temp);
		}
	}

	/**
	 * @return all 15 cities
	 */
	public City[] getCities() {
		return cities;
	}

	/**
	 * @param i
	 * @return the visible permit card to the specified index
	 */
	public PermitCard getOneVisiblePermit(int i) {
		return this.visiblePermits[i];
	}

	/**
	 * @return both visible permit cards
	 */
	public PermitCard[] getVisibilePermits() {
		return visiblePermits;

	}

}
