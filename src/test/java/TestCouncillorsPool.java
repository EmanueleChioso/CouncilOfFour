import static org.junit.Assert.*;

import org.junit.Test;

import model.map.Council;
import model.map.Councillor;
import model.map.CouncillorsPool;
import model.player.CardCouncilColor;

/**
 * 
 */

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class TestCouncillorsPool {
	private CouncillorsPool pool;
	private Councillor coun;
	private Councillor coun1;
	private Councillor coun2;
	private Councillor coun3;
	private Councillor coun4;
	private Councillor coun5;
	
	private Council council;
	
	@Test
	public void test() {
		pool = new CouncillorsPool();
		assertEquals(24, pool.getLenght());
		coun = new Councillor(CardCouncilColor.BLACK);
		pool.addCouncillor(coun);
		assertEquals(25, pool.getLenght());
		assertEquals(coun, pool.getCouncillor(pool.getLenght()-1));
		assertTrue(pool.checkCouncillorColor(CardCouncilColor.BLACK));
		pool.removeFromPool(coun);
		assertEquals(24, pool.getLenght());
		coun1 = pool.removeCouncillorToCreateCouncil();
		assertEquals(23, pool.getLenght());
		coun2 = pool.removeCouncillorToCreateCouncil();
		coun3 = pool.removeCouncillorToCreateCouncil();
		coun4 = pool.removeCouncillorToCreateCouncil();
		coun5 = pool.removeCouncillorToCreateCouncil();
		
		council = new Council(coun2, coun3, coun4, coun5);
		council.moveCouncil(coun);
		assertEquals(coun.getColor(),council.getColor(0));
		assertEquals(coun2.getColor(),council.getColor(1));
		assertEquals(coun3.getColor(),council.getColor(2));
		assertEquals(coun4.getColor(),council.getColor(3));
		
	}

}
