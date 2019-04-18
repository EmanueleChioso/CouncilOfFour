import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import controller.Game;
import model.cards.PermitCard;
import model.map.City;
import model.map.MapFactory;
import model.map.Region;
import model.player.Player;

/**
 * 
 */

/**
 * @author Emanuele Chioso, Lorenzo Della Penna
 *
 */
public class TestGame {

	private Player pl1;
	private Player pl2;
	private Player pl3;
	private Player pl4;
	private Player pl5;
	private Player pl6;
	private List<String> list;
	private List<Player> winnerList;
	private List<String> expected;

	@Test
	public void LastTurn() {

		winnerList = new ArrayList<>();
		expected = new ArrayList<>();
		list = new ArrayList<>();
		pl1 = new Player("gino");
		pl2 = new Player("lele");
		pl3 = new Player("lore");
		pl4 = new Player("tiaa");
		pl5 = new Player("kape");
		pl6 = new Player("vaffa");

		Game game = new Game();
		list.add(pl1.getName());
		list.add(pl2.getName());
		list.add(pl3.getName());
		list.add(pl4.getName());
		list.add(pl5.getName());
		list.add(pl6.getName());

		game.addPlayers(list);
		// pl1 has 8 cards & 12 nob
		for (int i = 0; i < 8; i++) {
			game.getPlayer(pl1.getName()).addCard(new PermitCard(new Region()));
		}
		game.getPlayer(pl1.getName()).addNobilty(12);
		game.getPlayer(pl1.getName()).addPoints(35);
		// pl2 has 3 cards & 10 nob
		for (int i = 0; i < 3; i++) {
			game.getPlayer(pl2.getName()).addCard(new PermitCard(new Region()));
		}
		game.getPlayer(pl2.getName()).addNobilty(10);
		game.getPlayer(pl2.getName()).addPoints(30);
		// pl3 has 5 cards & 8 nob
		for (int i = 0; i < 5; i++) {
			game.getPlayer(pl3.getName()).addCard(new PermitCard(new Region()));
		}
		game.getPlayer(pl3.getName()).addNobilty(8);
		game.getPlayer(pl3.getName()).addPoints(60);
		// pl4 has 6 cards & 13 nob
		for (int i = 0; i < 6; i++) {
			game.getPlayer(pl4.getName()).addCard(new PermitCard(new Region()));
		}
		game.getPlayer(pl4.getName()).addNobilty(13);
		game.getPlayer(pl4.getName()).addPoints(52);
		// pl5 has 4 cards & 7 nob
		for (int i = 0; i < 4; i++) {
			game.getPlayer(pl5.getName()).addCard(new PermitCard(new Region()));
		}
		game.getPlayer(pl5.getName()).addNobilty(7);
		game.getPlayer(pl5.getName()).addPoints(34);
		// pl6 has 2 cards & 2 nob
		for (int i = 0; i < 2; i++) {
			game.getPlayer(pl6.getName()).addCard(new PermitCard(new Region()));
		}
		game.getPlayer(pl6.getName()).addNobilty(12);
		game.getPlayer(pl6.getName()).addPoints(23);

		expected.add(pl3.getName());
		expected.add(pl4.getName());
		expected.add(pl1.getName());
		expected.add(pl5.getName());
		expected.add(pl2.getName());
		expected.add(pl6.getName());

		winnerList = game.getWinner();

		System.out.println();
		for (int i = 0; i < winnerList.size(); i++) {

			assertEquals(winnerList.get(i).getName(), expected.get(i));
		}

	
	
	}
}
