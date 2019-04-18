package model.cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class PermitDeck  implements Deck, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2738538307846469050L;
	private ArrayList<PermitCard> permitCards;

	/**
	 * init cards arrayList
	 */
	public PermitDeck() {
		permitCards = new ArrayList<>();
	}

	
	/**
	 * @return the size of cards arrayList
	 */
	public int getNumCards() {
		return permitCards.size();
	}

	/**
	 * @param index
	 * @return the card to the specified index
	 */
	public PermitCard getCard(int index) {
		return permitCards.get(index);
	}

	/**
	 * initialize the deck and shuffles it
	 * @param card
	 */
	public void initDeck(List<PermitCard> card) {
		this.permitCards.addAll(card);
		Collections.shuffle(permitCards);
	}

	/* (non-Javadoc)
	 * @see model.cards.Deck#draw()
	 */
	public PermitCard draw() {
		PermitCard ret = permitCards.get(0);
		permitCards.remove(ret);
		return ret;
	}

	/**
	 * used to reinsert cards that have been replaced from the board
	 */
	public void reinsertCard(PermitCard card) {
		permitCards.add(card);
	}
}
