package model.cards;

import java.io.Serializable;
import java.util.ArrayList;

import model.bonus.Bonus;
import model.map.Region;
import model.market.Sellable;
import model.player.Player;

public class PermitCard implements Card, Sellable,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1010499626145463594L;
	private Bonus bonuses;
	private int price;
	private ArrayList <String> initials;
	private Region region;
	private Player player;
	
	/**
	 * takes a param, the region, the cards belong to 
	 * @param region
	 */
	public PermitCard(Region region){
		this.region=region;
		initials=new ArrayList<>();
	}
	

	
	/**
	 * @return the number of initials that the cards contains 
	 */
	public int getNumInitials(){
		return this.initials.size();
	}
	
	/**
	 * @param index
	 * @return the initial to the specified index
	 */
	public String getInitial(int index){
		return this.initials.get(index);
	}
	
	/**
	 * used when a player decides to use a permit card
	 * simply add it to used permit cards 
	 * @param player
	 */
	public void useCard(Player player){
		player.subCard(this); //this should go in the array of used politic cards
		player.addUsedPermitCard(this);
	}
	
	/**
	 * activates all primitive bonuses contained in this card
	 * @param player
	 */
	public void activateBonuses(Player player){
		for(int i=0; i<bonuses.getNumBonus(); i++){
			bonuses.getPrimitiveBonus(i).activatePrimitiveBonus(player);;
		}
	}
	
	/**
	 * @param bonuses
	 */
	public void setBonus(Bonus bonuses){
		this.bonuses=bonuses;
	}
	
	/**
	 * @param init
	 */
	public void setInitials(String init){
		initials.add(init);
	}
	
	/**
	 * @return the card's bonus
	 */
	public Bonus getBonus(){
		return this.bonuses;
	}
	
	
	/**
	 * @return the card's region
	 */
	public Region getRegion(){
		return this.region;
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
		String ret = "PERMIT CARD - ";
		ret = ret.concat("cities: ");
		for(int i=0; i<initials.size(); i++){
			ret = ret.concat(initials.get(i).toUpperCase());
			ret = ret.concat(" ");
		}
		ret= ret.concat("BONUS: ");
		ret = ret.concat(getBonus().toString());
		return ret;
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
		return 1;
	}

	/* (non-Javadoc)
	 * @see model.market.Sellable#getQuantity()
	 */
	@Override
	public int getQuantity() {
		return 1;
	}
	
	/**
	 * @return the list of initials contained in this card
	 */
	public String[] getInitials() {
		String[] ret = new String[initials.size()];
		for(int i = 0 ; i < initials.size() ; i++){
			ret[i] = initials.get(i);
		}
		return ret;
	}



	/**
	 * @param toCheck
	 * @return true if check correspond, false otherwise
	 */
	public boolean checkInitials(int toCheck){
		if( this.initials.get(toCheck) !=null)
			return true;
		return false;
	}
	
	
	/**
	 * @param toCheck
	 * @return true if check correspond, flase otherwise
	 */
	public boolean checkInitials(String toCheck){
	for(String s: initials)
		if(s.charAt(0) == toCheck.charAt(0))
			return true;
	return false;
		
		
	}

}
