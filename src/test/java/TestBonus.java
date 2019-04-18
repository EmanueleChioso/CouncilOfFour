import static org.junit.Assert.assertEquals;

import org.junit.Test;

import model.bonus.Bonus;
import model.bonus.ChooseBonus;
import model.bonus.CoinBonus;
import model.bonus.NoBonus;
import model.bonus.PermitBonus;
import model.bonus.PrimitiveBonus;
import model.player.Player;

/**
 * 
 */

/**
 * @author Emanuele Chioso, Lorenzo Della Penna
 *
 */
public class TestBonus {

	@SuppressWarnings("deprecation")
	@Test
	public void remainingBonusesTest() {

		Player player = new Player("lore");
		PrimitiveBonus p = new PermitBonus(1);
		Bonus b = new Bonus(p);

		b.activateBonus(player);
		assertEquals(1, player.getPermitBonus());
		assertEquals(p.toString(), "PECARDS x 1");

		b = new NoBonus();
		assertEquals(b.toString(), "No Bonus");

		p = new CoinBonus(1);
		b = new Bonus(p);
		b.activateBonus(player);
		assertEquals(1, player.getCoin());

		p = new ChooseBonus(1);
		b = new Bonus(p);
		b.activateBonus(player);
		assertEquals(1, player.getChooseBonus());
	}

}
