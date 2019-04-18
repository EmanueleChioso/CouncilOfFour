package model.market;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.player.Player;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class Market implements Serializable {

	/**
	 * the class that keeps track of all sellable items 
	 */
	private static final long serialVersionUID = -1729540157399911301L;
	private ArrayList<Sellable> items;

	public Market() {
		items = new ArrayList();
	}

	/**
	 * empty the arrayList at the end of market turn 
	 */
	public void resetMarket() {
		items.clear();
	}

	
	/**
	 * @param item
	 * @return true if the item could be put in the list, false if that item was in yet
	 */
	public boolean addMarketItem(Sellable item) {
		if (items.contains(item)) {
			return false;
		}
		items.add(item);
		return true;
	}

	/**
	 * remove an item from the list
	 * @param item
	 */
	public void subMarketItem(Sellable item) {
		items.remove(item);
	}

	/**
	 * orders the list of items in descending cost order
	 */
	public void orderByPrice() {
		Collections.sort(items, new MyComparator());
	}
	
	/**
	 * @param index
	 * @return the items to the specified index
	 */
	public Sellable getItem(int index) {
		return items.get(index);
	}

	/**
	 * @return the number of items in the list
	 */
	public int getSize() {
		return this.items.size();
	}

	/**
	 * takes 3 params, the items that is going to be sell,
	 *  the player that is going to buy it,
	 *  and the player that wants to sell it.
	 *  takes money and add item,
	 *  remove item and add money.
	 *  
	 * @param item
	 * @param buyer
	 * @param seller
	 */
	public void sell(Sellable item, Player buyer, Player seller) {
		
			seller.subSellable(item);
			seller.addCoin(item.getPrice());
			buyer.addSellable(item);
			buyer.subCoin(item.getPrice());
			this.subMarketItem(item);
			
	}

	/**
	 * @return all items in sale
	 */
	public List<Sellable> getAllItems() {
		orderByPrice();
		List<Sellable> marketItems = new ArrayList<>();
		marketItems.addAll(items);
		return marketItems;
	}

	/**
	 * 
	 * a private class that defines a comparator used to order items by price
	 * 
	 * @author Emanuele Chioso, Lorenzo Della Penna 
	 *
	 */
	private class MyComparator implements Comparator<Sellable> {
		

		/* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Sellable item1, Sellable item2) {
			return item1.getPrice() < item2.getPrice() ? -1 : item1.getPrice() < item2.getPrice() ? 0 : 1;
		}

	}

}
