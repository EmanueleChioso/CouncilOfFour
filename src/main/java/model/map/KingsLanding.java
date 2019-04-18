package model.map;

import java.io.Serializable;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class KingsLanding implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2157370214868564738L;
	private City city;
	private Council council;
	
	/**
	 * takes a param, the city in which the king is placed at the beginning
	 */
	public KingsLanding(City city) {
		this.city = city;
	}
	
	/**
	 * changes king's location and sets it to the city specified
	 * @param city
	 */
	public void setKingLocation(City city){
		this.city=city;
	}
	/**
	 * @return the city in which the king is located
	 */
	public City getKingLocation(){
		return this.city;
	}
	
	/**
	 * @return the city in which the king is located casted to int
	 */
	public int getKingLocationInt(){
		return this.city.getInt();
	}
	
	/**
	 * @return the kin's council
	 */
	public Council getCouncil() {
		return council;
	}
	
	/**
	 * takes a param, the council that is created in game and given to king's landing
	 * @param council
	 */
	public void setCouncil(Council council) {
		this.council = council;
	}
	
	
}
