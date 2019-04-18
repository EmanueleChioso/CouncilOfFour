import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import model.PrizesHandler;
import model.map.CityColor;
import model.player.Player;

/**
 * 
 */

/**
 * @author Emanuele Chioso, Lorenzo Della Penna
 *
 */
public class TestPrizeHandler {

	@Test
	public void prizeHandlerTest() {
		PrizesHandler p = new PrizesHandler();

		CityColor blue = CityColor.BLUE;
		CityColor gold = CityColor.GOLD;
		CityColor bronze = CityColor.BRONZE;
		CityColor silver = CityColor.SILVER;

		assertTrue(p.checkColorPrizeAvailability(blue));
		assertTrue(p.checkColorPrizeAvailability(gold));
		assertTrue(p.checkColorPrizeAvailability(silver));
		assertTrue(p.checkColorPrizeAvailability(bronze));
		assertTrue(p.checkKingPrizeAvailability());

		Player player = new Player("lore");

		assertEquals(0, p.showFirstKingPrizeIndexAvailable());
		p.useKingPrize(player);

		assertEquals(25, player.getPoints());
		p.useColorPrize(player, blue);
		System.out.println(player.getPoints());

		assertEquals(30, player.getPoints());

		p.useKingPrize(player);
		p.useKingPrize(player);
		p.useKingPrize(player);
		p.useKingPrize(player);
		assertEquals(-1, p.showFirstKingPrizeIndexAvailable());
		Assert.assertFalse(p.checkKingPrizeAvailability());

		int num = player.getPoints();
		p.useKingPrize(player);
		assertEquals(num, player.getPoints());

		//  region prize
		p.useRegionPrize(player, 0);
		assertEquals(num + 5, player.getPoints());
		assertTrue(p.checkRegionPrizeAvailability(1));

		// gh.fourthPrimaryAction("lore", numToUse , "D");
		//
		// gh.fourthPrimaryAction("lore", blue2 , "L");
		//

	}

}
