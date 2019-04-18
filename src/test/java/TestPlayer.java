import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import model.cards.PermitCard;
import model.cards.PoliticCard;
import model.map.Region;
import model.market.Assistant;
import model.player.CardCouncilColor;
import model.player.Player;

/**
 * 
 */

/**
 * @author Emanuele Chioso, Lorenzo Della Penna
 *
 */
public class TestPlayer {
	private static final String name = "lore";
	Player player = new Player(name);


	@Test
	public void dataManipulation() {
		player.setName(name);
		assertEquals(name, player.getName());
		assertEquals(0, player.getNobility());

		assertEquals(false, player.checkAssistants(5));
		assertEquals(false, player.checkCoins(10));
		assertEquals(0, player.getChooseBonus());
		assertEquals(0, player.getNumPoliticCard());
		assertEquals(0, player.getNumPermitCard());

		player.addPoliticBonus(1);
		player.addChooseBonus(1);
		player.addCard(new PoliticCard(CardCouncilColor.BLACK));
		player.addCard(new PermitCard(new Region()));

		assertEquals(1, player.getChooseBonus());
		assertEquals(1, player.getPoliticBonus());
		assertEquals(1, player.getNumPermitCard());
		assertEquals(1, player.getNumPoliticCard());

		player.subChooseBonus(1);
		player.subPoliticBonus(1);

		assertEquals(0, player.getChooseBonus());
		assertEquals(0, player.getPoliticBonus());
		assertEquals(0, player.getNumUsedPermitCards());

		player = new Player(name);

		player.addCard(new PoliticCard(CardCouncilColor.BLACK));
		player.addCard(new PermitCard(new Region()));
		player.useCard(player.getPermitCard(0));
		player.useCard(player.getPoliticCard(0));

		assertEquals(0, player.getNumPermitCard());
		assertEquals(0, player.getNumPoliticCard());
		assertEquals(1, player.getNumUsedPermitCards());
		
		Assistant t = new Assistant ( player,10,1);
		player.addSellable(t);
		
		assertEquals(10,player.getAssistants());
		player.subSellable(t);

		Assert.assertEquals(0, player.getAssistants());
		
		
		
		player.addPermitBonus(1);
	
		assertEquals(1, player.getPermitBonus() );
		player.subPermitBonus(1);

		assertEquals(0, player.getPermitBonus() );
		
		
	}
	@Test
	public void playerCard(){
		player = new Player(name);
		player.addCard(new PoliticCard(CardCouncilColor.BLACK));
		player.addCard(new PoliticCard(CardCouncilColor.BLACK));
		player.addCard(new PoliticCard(CardCouncilColor.BLACK));
		
		assertTrue( player.checkPoliticCard(CardCouncilColor.BLACK));
		assertTrue( player.checkPoliticCard(3, CardCouncilColor.BLACK));
		
		assertFalse(  player.checkPoliticCard(CardCouncilColor.BLUE) );
		assertFalse( player.checkPoliticCard(3, CardCouncilColor.BLUE));
		
//		player.addPoliticBonus(1);
//		assertTrue(player.checkpoli)
		
	}

	
	
	
	
	
	
	
	
	
	
	
}
