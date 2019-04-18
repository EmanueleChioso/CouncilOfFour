import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import controller.GameHandler;
import controller.Parser;
import model.cards.PermitCard;
import model.cards.PoliticCard;
import model.map.GameMap;
import model.map.MapFactory;
import model.player.CardCouncilColor;
import model.player.Player;

/**
 * 
 */

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class TestGameHandler {
	
	private GameHandler gh;
	private Player pl1;
	private Player pl2;
	private Player pl3;
	private List<String> players;
	private List<Player> realPlayers;
	private GameMap map;
	
	
	@Test
	public void test() throws RemoteException {
		gh = new GameHandler();
		players = new ArrayList<>();
		realPlayers = new ArrayList<>();
		pl1 = new Player("lele");
		pl2 = new Player("lore");
		pl3 = new Player("gio");
		realPlayers.add(pl1);
		realPlayers.add(pl2);
		realPlayers.add(pl3);
		players.add(pl1.getName());
		players.add(pl2.getName());
		players.add(pl3.getName());
		gh.avviaPartita(players);
		//same array, same content
		assertArrayEquals(gh.getPlayersNames().toArray(), players.toArray() );
		//different array, same content
		for(int i=0; i<gh.getGame().getAllPlayers().size(); i++){
			assertEquals(gh.getGame().getAllPlayers().get(i).getName(),realPlayers.get(i).getName());
		}
		//first player is same in both arrays
		assertEquals(gh.getGame().getAllPlayers().get(0).getName(), players.get(0));
		assertEquals("S", gh.askTurn("lele"));
		
		//creating a game from 0 to test actions
		
		map = MapFactory.getMap(1);
		gh.setMap(map);
		
		Player player = gh.getGame().getPlayer("lele");
		player.addCard(new PoliticCard(CardCouncilColor.UNICORN));
		player.addCard(new PoliticCard(CardCouncilColor.UNICORN));
		player.addCard(new PoliticCard(CardCouncilColor.UNICORN));
		player.addCard(new PoliticCard(CardCouncilColor.UNICORN));
		player.addCard(new PoliticCard(CardCouncilColor.ORANGE));
		player.addCard(new PoliticCard(CardCouncilColor.PINK));
		
		System.out.println(gh.getGame().getPlayer("lore").getNumPoliticCard());
		System.out.println(gh.getGame().getPlayer("gio").getNumPoliticCard());
		List <Integer> pl1Cards = new ArrayList<>();
		//creating a list of cards used
		pl1Cards.add(7);
		pl1Cards.add(8);
		pl1Cards.add(9);
		pl1Cards.add(10);

		//perform action 1
		
		gh.firstPrimaryAction("lele", 0, 0, pl1Cards );
		//this test works only if the player do not take a permit card that gives politic card as bonus
		assertEquals(8, gh.getPlayer("lele").getNumPoliticCard());
		assertEquals(1, gh.getPlayer("lele").getNumPermitCard());
		assertTrue(!gh.checkPrimaryAction("lele"));
		
		//resetting players actions
		gh.getPlayer("lele").resetActions();
		
		assertTrue(gh.checkPrimaryAction("lele"));
		assertTrue(gh.checkFastAction("lele"));
		
		//readding cards;
		pl1Cards.removeAll(pl1Cards);
		player.addCard(new PoliticCard(CardCouncilColor.UNICORN));
		player.addCard(new PoliticCard(CardCouncilColor.UNICORN));
		player.addCard(new PoliticCard(CardCouncilColor.UNICORN));
		player.addCard(new PoliticCard(CardCouncilColor.UNICORN));
		pl1Cards.add(9);
		pl1Cards.add(10);
		pl1Cards.add(11);
		pl1Cards.add(12);
		//giving pl1 enought money to perform the action
		gh.getPlayer("lele").addCoin(20);
		
		gh.secondPrimaryAction("lele", pl1Cards, 14);
		
		assertEquals("lele", gh.getMap().getCity(14).getEmporiums().get(0).getName() );
		assertEquals(14, gh.getMap().getKingLocation().getInt());
		
		//resetting actions and stats
		gh.getPlayer("lele").resetActions();
		gh.getPlayer("lele").setCoin(0);
		
		gh.thirdPrimaryAction("lele", 1, 1);
		//we tested how council moves in testCouncil
		assertEquals(4, gh.getPlayer("lele").getCoin());
		
		//player should have a permit card right now
		gh.getPlayer("lele").resetActions();
		PermitCard card = new PermitCard(gh.getMap().getSeaRegion());
		card.setInitials("A");
		gh.getPlayer("lele").addCard(card);
		gh.fourthPrimaryAction("lele", 1, 0);
		assertEquals("lele", gh.getMap().getCity(0).getEmporiums().get(0).getName());
		
		//ready to test fast actions
		gh.getPlayer("lele").setCoin(6);
		gh.getPlayer("lele").setAssistants(0);
		gh.firstFastAction("lele");
		assertEquals(3, gh.getPlayer("lele").getCoin());
		assertEquals(1, gh.getPlayer("lele").getAssistants());
		assertTrue(!gh.checkFastAction("lele"));
		
		
		gh.getPlayer("lele").resetActions();
		
		PermitCard card1 = gh.getMap().getRegion(1).getOneVisiblePermit(0);
		PermitCard card2 = gh.getMap().getRegion(1).getOneVisiblePermit(1);
		System.out.println(card1);
		System.out.println(card2);
		String communication = 	gh.secondFastAction("lele", 1);
		System.out.println(communication);
		
		System.out.println(gh.getMap().getRegion(1).getOneVisiblePermit(0));
		System.out.println(gh.getMap().getRegion(1).getOneVisiblePermit(1));
		assertNotEquals(gh.getMap().getRegion(1).getOneVisiblePermit(0) , card1);
		assertNotEquals(gh.getMap().getRegion(1).getOneVisiblePermit(1) , card2);
		
		gh.getPlayer("lele").resetActions();
		
		//readding cards;
		pl1Cards.removeAll(pl1Cards);
		player.addCard(new PoliticCard(CardCouncilColor.UNICORN));
		player.addCard(new PoliticCard(CardCouncilColor.UNICORN));
		player.addCard(new PoliticCard(CardCouncilColor.UNICORN));
		player.addCard(new PoliticCard(CardCouncilColor.UNICORN));
		pl1Cards.add(9);
		pl1Cards.add(10);
		pl1Cards.add(11);
		pl1Cards.add(12);
		
		gh.thirdFastAction("lele", 1, 1);
		//we tested how counil works in another test
		assertEquals(0,gh.getPlayer("lele").getAssistants());
		
		//setting player primary action done to test fourth action
		gh.getPlayer("lele").resetActions();
		gh.getPlayer("lele").addAssistants(3);
		gh.getPlayer("lele").subPrimaryAction();
		assertTrue(!gh.checkPrimaryAction("lele"));
		gh.fourthFastAction("lele");
		assertTrue(gh.checkPrimaryAction("lele"));
		assertEquals(0,gh.getPlayer("lele").getAssistants());
		
		

		
	}
	
	@Test
	public void TestParser(){
		
		Map<String, String> info = new HashMap<>();
		
		info = Parser.parseCommand("action=1&player=lore");
		
		assertEquals("lore", info.get("player")  );
		assertEquals("1", info.get("action")  );
		
		
		
		
	}

}
