package model.cards;

import java.io.Serializable;
import java.util.ArrayList;

import model.Const;
import model.player.CardCouncilColor;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class PoliticDeck implements Deck, Serializable {
	
	private static final long serialVersionUID = 2907495047668698529L;
	private ArrayList<PoliticCard> politiCards;

	/**
	 * initialize the cards arrayList
	 */
	public PoliticDeck() {
		politiCards = new ArrayList<>();
	}

	/**
	 * creates a deck by adding random cards to a maximum of Const.POLITICCARDS
	 */
	public void createDeck() {
		for (int i = 0; i < Const.POLITICCARDS; i++) {
			CardCouncilColor color = CardCouncilColor.randomColor();
			politiCards.add(new PoliticCard(color));
		}
	}

	/* (non-Javadoc)
	 * @see model.cards.Deck#draw()
	 */
	public PoliticCard draw() {
		PoliticCard drawn;
		drawn = politiCards.get(0);
		politiCards.remove(0);
		return drawn;
	}

	/**
	 * @param index
	 * @return the cards to the specified index
	 */
	public PoliticCard getCard(int index) {
		return politiCards.get(index);
	}

	/**
	 * @return the size of cards arrayList
	 */
	public int getNumCards() {
		return politiCards.size();
	}
}
