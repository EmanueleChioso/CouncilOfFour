package model.market;

import model.player.Player;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public interface Sellable{
	/**
	 * sets the price and the player whose item belongs to 
	 * @param price
	 * @param player
	 */
	public void setPrice(int price, Player player);
	/**
	 * @return the price of the item.
	 */
	public int getPrice();
	/**
	 * @return the player whose item belong to 
	 */
	public Player getSeller();
	/**
	 * @return the "type" of the item in sale
	 */
	public int getType();
	/**
	 * a method used only by assistants sellable item
	 * @return the number of elements that the item contains 
	 */
	public int getQuantity();
}
