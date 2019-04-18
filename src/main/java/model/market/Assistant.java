package model.market;

import java.io.Serializable;

import model.player.Player;
/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class Assistant implements Sellable, Serializable {
	
	
	/**
	 * a class that is used to create a package that contains a number 
	 * of assistants and a price. this is because,
	 * we implemented assistants as integer and not as a class
	 */
	private static final long serialVersionUID = 226546485221705587L;
	private int price;
	private Player player; 
	private int quantity;
	
	
	/**
	 * takes 3 params, a player whose assistants belong to, 
	 * the quantity of assistants in sale,
	 * the price the player decided to set
	 * 
	 * @param player
	 * @param quantity
	 * @param price
	 */
	public  Assistant(Player player, int quantity, int price) {
		
		this.player=player;
		this.price=price;
		this.quantity=quantity;
		
	}
	
	/* (non-Javadoc)
	 * @see model.market.Sellable#getQuantity()
	 */
	@Override
	public int getQuantity(){
		return this.quantity;
	}
	
	/* (non-Javadoc)
	 * @see model.market.Sellable#getPrice()
	 */
	@Override
	public int getPrice(){
		return this.price;
	}
	
	/* (non-Javadoc)
	 * @see model.market.Sellable#getSeller()
	 */
	@Override
	public Player getSeller(){
		return this.player;
	}

	/* (non-Javadoc)
	 * @see model.market.Sellable#setPrice(int, model.player.Player)
	 */
	@Override
	public void setPrice(int price, Player player) {
		this.price=price;
		
	}

	/* (non-Javadoc)
	 * @see model.market.Sellable#getType()
	 */
	@Override
	public int getType() {
		return 2;
	}

}
