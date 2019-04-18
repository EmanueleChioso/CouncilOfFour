import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.Const;
import model.bonus.Bonus;
import model.map.City;
import model.map.GameMap;
import model.map.MapFactory;
import model.player.Player;

/**
 * 
 */

/**
 * @author Emanuele Chioso, Lorenzo Della Penna
 *
 */
public class TestGameMap {
	GameMap m ;
	Player p;


	@Test
	public void nullMap() {
		assertEquals(null, MapFactory.getMap(0));
	}

	
	@Test
	public void addRemoveConnections() {
		 m = MapFactory.getMap(1);
		m.addConnection(0, 1);
		assertEquals(true, m.removeConnection(0, 1));
		assertEquals(false, m.getConnection(0, 1));
		assertEquals(true, m.addConnection(0, 1));
		assertEquals(true, m.getConnection(0, 1));
	}

	@Test
	public void qualityMapsChecks() {
		 m = MapFactory.getMap(1);

		for (int i = 0; i < Const.CITIES; i++)
			for (int j = 0; j < Const.CITIES; j++)
				m.addConnection(i, j);

		assertEquals(false, m.foundLoneCity());
		assertEquals(true, m.mapQualityCheckNotPassed());

		// ------------

		for (int i = 0; i < Const.CITIES; i++)
			m.removeConnection(0, i);

		assertEquals(true, m.foundLoneCity());

		// -----------
		for (int i = 0; i < Const.CITIES; i++) {
			m.addConnection(0, i);
			m.removeConnection(5, i);
		}
		m.removeConnection(0, 5);

		assertEquals(true, m.foundLoneCity());
		// ------------

	}
	
	@Test
	public void cityConnectionsInRegions() {

		m= MapFactory.getMap(1);
		m.addConnection(0, 14);
		assertEquals(true, m.mapQualityCheckNotPassed());
		m.removeConnection(0, 14);

		// ----------
		m.addConnection(0, 7);
		assertEquals(true, m.mapQualityCheckNotPassed());
		m.removeConnection(0, 7);
		// --------
		m.addConnection(7, 12);
		assertEquals(true, m.mapQualityCheckNotPassed());

	}

	@Test
	public void randomMap() {
		assertEquals(GameMap.class, MapFactory.getMap(12).getClass());
	}

	
	
	

	@Test
	public void playerCities() {
		 p = new Player("lore");
		 m= MapFactory.getMap(1);
		
		List<City>  citiesToCheck = new ArrayList<>();
		City city = new City("+", null, null);
		City city2 = new City("z", null, null);
		
		for( int i =0 ; i < Const.REGIONS ; i++ ){
			System.out.println( m.getCity(i));
			m.getCity(i).setEmporium(p);
			citiesToCheck.add(m.getCity(i));
			m.getCity(i).setBonusType(new Bonus());
			
		}

			
			
		assertEquals( citiesToCheck, m.getPlayerCities(p));
		assertEquals(-1, city.getInt());
		assertEquals(25, city2.getInt());
		assertEquals('z',city2.getLetter());
	}
	
	
	
	
	
	
}
