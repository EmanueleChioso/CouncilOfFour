package model.cards;

import java.io.Serializable;

import model.market.Sellable;
import model.player.CardCouncilColor;
import model.player.Player;


/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class PoliticCard implements Card, Sellable,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9110107175800539252L;
	private CardCouncilColor color;
	private int price;
	private Player player;
	/**
	 * takes a param, the card color
	 */
	public PoliticCard(CardCouncilColor color) {
		this.color=color;
	}
	
	/**
	 * @param color
	 */
	public void setColor(CardCouncilColor color){
		this.color=color;
	}
	
	/**
	 * @return this card's color
	 */
	public CardCouncilColor getColor(){
		return this.color;
	}
	
	/**
	 * @param player
	 */
	public void useCard(Player player) {
		player.subCard(this);
		
	}

	/* (non-Javadoc)
	 * @see model.market.Sellable#getPrice()
	 */
	@Override
	public int getPrice() {
		return this.price;
	}

	/* (non-Javadoc)
	 * @see model.market.Sellable#setPrice(int, model.player.Player)
	 */
	@Override
	public void setPrice(int price,Player player) {
		this.price=price;
		this.player=player;
		
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Politic card: "+this.color;
	}

	/* (non-Javadoc)
	 * @see model.market.Sellable#getPlayer()
	 */
	@Override
	public Player getSeller() {
		return this.player;
	}

	/* (non-Javadoc)
	 * @see model.market.Sellable#getType()
	 */
	@Override
	public int getType() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see model.market.Sellable#getQuantity()
	 */
	@Override
	public int getQuantity() {
		return 1;
	}




}
