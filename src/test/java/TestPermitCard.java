import static org.junit.Assert.*;

import org.junit.Test;

import model.bonus.AssistantBonus;
import model.bonus.Bonus;
import model.bonus.PoliticBonus;
import model.bonus.PrimitiveBonus;
import model.cards.CardsPool;
import model.cards.PermitCard;
import model.cards.PermitDeck;
import model.cards.PoliticDeck;
import model.map.Region;
import model.player.Player;

/**
 * 
 */

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class TestPermitCard {
	
	PermitCard card;
	Region region;
	Region region1;
	Region region2;
	Bonus bonus;
	PoliticDeck poDeck;
	PermitDeck peDeck;
	CardsPool pool;
	PrimitiveBonus b1;
	PrimitiveBonus b2;
	Player player;
	
	
	@Test
	public void test() {

		region = new Region();
		region1 = new Region();
		region2 = new Region();
		peDeck=new PermitDeck();
		player = new Player("lele");
		pool = new CardsPool(region, region1, region2);
		poDeck = new PoliticDeck();
		peDeck.initDeck(pool.getSeaDeck());
		card = new PermitCard(region);
		card.setInitials("a");
		card.setInitials("b");
		assertEquals(2,card.getNumInitials());
		b1 = new AssistantBonus(2);
		b2 = new PoliticBonus(2);
		bonus = new Bonus(b1, b2);
		card.setBonus(bonus);
		player.takesPermitCard(card);
		assertEquals(2, player.getAssistants());
		assertEquals(2, player.getPoliticBonus());
	}

}
