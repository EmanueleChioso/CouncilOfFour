package model.map;
//@LorenzoDellaPenna

import java.io.Serializable;

import model.player.CardCouncilColor;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class Councillor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6719278192429082227L;
	private CardCouncilColor color;

	/**
	 * it takes a param, the councillor color
	 * @param color
	 */
	public Councillor(CardCouncilColor color) {
		this.color = color;
	}

	/**
	 * @return the councillor's color
	 */
	public CardCouncilColor getColor() {
		return this.color;
	}

	/**
	 * @param col
	 */
	public void setColor(CardCouncilColor col) {
		this.color = col;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return this.color.toString();
	}
}
