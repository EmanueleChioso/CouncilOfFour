package model.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import model.Const;
import model.cards.PermitCard;
import model.cards.PoliticCard;
import model.market.Sellable;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class Player implements Serializable {

	private static final long serialVersionUID = 5931554202149618662L;
	private String name;
	private int emporiums;
	private int coins;
	private int nobilty;
	private int assistants;
	private int points;
	private int primaryAction;
	private boolean fastAction; // set to TRUE if he has a fast action to do
	private int chooseBonus;
	private int permitBonus;
	private int politicBonus;

	private ArrayList<PoliticCard> politicCards;
	private ArrayList<PermitCard> permitCards;
	private ArrayList<PermitCard> usedPermitCards;

	

	/**
	 * takes a param, the name of the player
	 * initialize number of emporiums, nobility,
	 * points, number of primary and fast actions.
	 * 
	 * 
	 * @param name
	 */
	public Player(String name) {
		this.emporiums = 10;
		this.name = name;
		this.nobilty = 0;
		this.points = 0;
		this.chooseBonus = 0;
		this.permitBonus = 0;
		this.politicBonus = 0;
		politicCards = new ArrayList<>();
		permitCards = new ArrayList<>();
		usedPermitCards = new ArrayList<>();
		primaryAction = 1;
		fastAction = true;

	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param num
	 */
	public void addPoints(int num) {
		this.points = points + num;
	}

	/**
	 * @return the player's victory points
	 */
	public int getPoints() {
		return this.points;
	}

	/**
	 * -1 emporiums
	 */
	public void subEmporium() {
		this.emporiums -= 1;
	}

	/**
	 * method used to keep track of the choose bonuses that we have to give 
	 * to the player at the end of any action
	 * @param num
	 */
	public void addChooseBonus(int num) {
		this.chooseBonus += num;
	}

	/**
	 * @param num
	 */
	public void subChooseBonus(int num) {
		this.chooseBonus -= num;
	}

	/**
	 * method used to keep track of the  permit bonuses that we have to give 
	 * to the player at the end of any actions
	 * @param num
	 */
	public void addPermitBonus(int num) {
		this.permitBonus += num;
	}

	/**
	 * @param num
	 */
	public void subPermitBonus(int num) {
		this.permitBonus -= num;
	}

	/**
	 * @return the times we have to add a choose bonus to the player
	 */
	public int getChooseBonus() {
		return this.chooseBonus;
	}
	/**
	 * check if cost is more than coins
	 * @param num
	 * @return false if money is less than cost, true otherwise
	 */
	public boolean checkCoins(int num) {  
		if (num > coins) {
			return false;
		}
		return true;
	}

	/**
	 * @return the times we need to add a permit bonus to the player
	 */
	public int getPermitBonus() {
		return this.permitBonus;
	}

	/**
	 * method used to keep track of the politic bonus that we need to give 
	 * to the player at the end of any action
	 * @param num
	 */
	public void addPoliticBonus(int num) {
		this.politicBonus += num;
	}

	public void subPoliticBonus(int num) {
		this.politicBonus -= num;
	}

	/**
	 * @return the times we need to give a politic bonus to the player
	 */
	public int getPoliticBonus() {
		return this.politicBonus;
	}

	/**
	 * @return player's coins
	 */
	public int getCoin() {
		return this.coins;
	}

	/**
	 * @param num
	 */
	public void setCoin(int num) {
		this.coins = num;
	}

	/**
	 * @param num
	 */
	public void subCoin(int num) {
		this.coins = coins - num;
	}

	/**
	 * player won't get more than 20 coins 
	 * @param num
	 */
	public void addCoin(int num) {
		this.coins = coins + num;
		if (coins > Const.COINS) { 
			coins = Const.COINS;
		}
	}
	/**
	 * check if cost is more than assistants
	 * @param num
	 * @return false if assistants are less than cost, true otherwise
	 */
	public boolean checkAssistants(int num) { 
		if (num > assistants)
			return false;
		return true;
	}

	/**
	 * @return player's assistants
	 */
	public int getAssistants() {
		return this.assistants;
	}

	/**
	 * @param num
	 */
	public void setAssistants(int num) {
		this.assistants = num;
	}

	/**
	 * @param num
	 */
	public void subAssistants(int num) {
		this.assistants = assistants - num;
	}

	/**
	 * @param num
	 */
	public void addAssistants(int num) {
		this.assistants = assistants + num;
	}

	/**
	 * @return player's nobility
	 */
	public int getNobility() {
		return this.nobilty;
	}

	/**
	 * @param nobNum
	 */
	public void addNobilty(int nobNum) {
		this.nobilty = nobilty + nobNum;
	}

	/**
	 * @return player's number of politic cards
	 */
	public int getNumPoliticCard() {
		return politicCards.size();
	}
	
	
	/**
	 * check if cards of the specified color are equal or more than the number given
	 * @param num
	 * @param col
	 * @return true if cards
	 */
	public boolean checkPoliticCard(int num, CardCouncilColor col) {
		int cardColorNum = 0;
		for (int i = 0; i < politicCards.size(); i++) {
			if (politicCards.get(i).getColor().equals(col)) {
				cardColorNum++;
			}
		}
		if (cardColorNum >= num) {
			return true;
		}
		return false;
	}
 
	/**
	 * used to check if the requested cards is in the array
	 * @param col
	 * @return
	 */
	public boolean checkPoliticCard(CardCouncilColor col) {
		for (int i = 0; i < politicCards.size(); i++) {
			if (politicCards.get(i).getColor().equals(col))
				return true;
		}
		return false;
	}

	/**
	 * used to get the i-esima politcCard
	 * @param index
	 * @return
	 */
	public PoliticCard getPoliticCard(int index) {
		return politicCards.get(index);
	}
 
	/**
	 * once we know the card is usable, we remove it
	 * @param card
	 */
	public void subCard(PoliticCard card) {
		politicCards.remove(card);
	}

	/**
	 * @param card
	 */
	public void addCard(PoliticCard card) {
		politicCards.add(card);
	}

	/**
	 * @param card
	 */
	public void subCard(PermitCard card) {
		permitCards.remove(card);
	}

	/**
	 * @return the number of permit cards used by the player
	 */
	public int getNumUsedPermitCards() {
		return usedPermitCards.size();
	}

	/**
	 * add the given card to used cards list
	 * @param card
	 */
	public void addUsedPermitCard(PermitCard card) {
		usedPermitCards.add(card);
	}

	/**
	 * @param card
	 */
	public void addCard(PermitCard card) {
		permitCards.add(card);
	}
	
	/**
	 * method used when a player takes a permit card,
	 * it activates all the bonuses 
	 * @param card
	 */
	public void takesPermitCard(PermitCard card) {
		permitCards.add(card);
		card.activateBonuses(this);
	}

	/**
	 * @param index
	 * @return the permit card to the specified index
	 */
	public PermitCard getPermitCard(int index) {
		return permitCards.get(index);
	}

	/**
	 * @return the number of permit card 
	 */
	public int getNumPermitCard() {
		return permitCards.size();
	}

	/**
	 * @param card
	 */
	public void useCard(PoliticCard card) { 
		card.useCard(this);
	}

	/**
	 * @param card
	 */
	public void useCard(PermitCard card) { 
		card.useCard(this);
	}

	/**
	 * method used to reset all player's actions at the beginning of his turn 
	 */
	public void resetActions() {
		this.primaryAction = 1;
		this.fastAction = true;
	}
	
	/**
	 *reset the player bonuses 
	*/
	public void resetBonuses() {
		this.chooseBonus = 0;
		this.permitBonus = 0;
		this.politicBonus = 0;
	}

	/**
	 * used when a player has the possibility to perform another primary action
	 */
	public void addPrimaryAction() {
		this.primaryAction += 1;
	}

	/**
	 * 
	 */
	public void subPrimaryAction() {
		this.primaryAction -= 1;
	}

	/**
	 * methos used to know how mant times a player must perform a primary action
	 * @return the number of primary actions that a player has
	 */
	public int getPrimaryActionCount() {
		return this.primaryAction;
	}

	/**
	 * after doing a fast action, this is set to false
	 */
	public void setFastActionDone() {
		this.fastAction = false;
	}

	/**
	 * @return true if a player has a fast action to do 
	 */
	public boolean checkFastAction() {
		return this.fastAction;
	}

	/**
	 * @param item
	 */
	public void subSellable(Sellable item) {
		switch (item.getType()) {
		case 0:
			politicCards.remove(item);
			break;
		case 1:
			permitCards.remove(item);
			break;
		case 2:
			assistants = assistants - item.getQuantity();
			break;
		default:
			return;
		}

	}

	/**
	 * items have a type given by an integer if type is 0, it's a politic card
	 * if type is 1, it's a permit card if type is 2, it'a assistants pack
	 * 
	 * @param item
	 */
	public void addSellable(Sellable item) {
		int type = item.getType();
		if( type==0 )
			politicCards.add((PoliticCard) item);
		else if ( type==1)
			permitCards.add((PermitCard) item);
		else if (type==2)
			assistants = assistants + item.getQuantity();
		

	}

	/**
	 * @param permit
	 * @param initial
	 * @return true if the permit card has got that initial in it 
	 */
	public boolean checkPermitCard(int permit, int initial) {
		return this.permitCards.get(permit).checkInitials(initial);
	}

	/**
	 * @param permit
	 * @param initial
	 * @return true if the Permit card checked has got that initial in it.
	 */
	public boolean checkPermitCard(int permit, String initial) {
		return this.permitCards.get(permit).checkInitials(initial);

	}

	/**
	 * @param permit
	 * @return true if player's number of card is > than the one given
	 */
	public boolean checkPermit(int permit) {
		if (this.permitCards.size() > permit)
			return true;
		return false;
	}

	/**
	 * uses the cards putting it in the used cards. the bonuses must be handled
	 * in the gamehandler.
	 * 
	 * @param permitCard
	 */
	public void usePermitCard(int permit) {
		this.usedPermitCards.add(this.permitCards.remove(permit));
		

	}

	/**
	 * finds and removes a card that is of that color. use it after checking
	 * that the card is there.
	 * 
	 * @param c
	 */
	public void subPoliticCards(List<CardCouncilColor> c) {
		PoliticCard tmp;
		for (int i = 0; i < c.size(); i++) {

			tmp = findACardOfThatColor(c.get(i));
			this.politicCards.remove(tmp);
		}
	}

	/**
	 * finds a card of that color
	 * 
	 * @param CardCouncilColor
	 * @return the first card found of that color
	 */
	private PoliticCard findACardOfThatColor(CardCouncilColor col) {
		for (PoliticCard p : this.politicCards)
			if (p.getColor().equals(col))
				return p;
		return null;
	}

	/**
	 * @return the entire player's list of politic cards
	 */
	public List<PoliticCard> getPoliticCards() {
		return this.politicCards;
	}

	/**
	 * @return the entire player's list of permit cards
	 */
	public List<PermitCard> getPermitCards() {
		return this.permitCards;
	}

	/**
	 * @return the number of emporiums the player still has
	 */
	public int getEmporiums() {
		return this.emporiums;
	}

}
