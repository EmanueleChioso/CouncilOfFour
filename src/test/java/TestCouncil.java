

import static org.junit.Assert.*;

import org.junit.Test;

import model.map.Council;
import model.map.Councillor;
import model.player.CardCouncilColor;
public class TestCouncil {
	Councillor coun1 = new Councillor(CardCouncilColor.BLACK);
	Councillor coun2 = new Councillor(CardCouncilColor.ORANGE);
	Councillor coun3 = new Councillor(CardCouncilColor.BLUE);
	Councillor coun4 = new Councillor(CardCouncilColor.PINK);
	Councillor coun5 = new Councillor(CardCouncilColor.PURPLE);
	Councillor coun6;
	Councillor[] list;
	Council council;
	Council expected = new Council(coun5,coun1,coun2,coun3);
	//CouncilorsPool pool = CouncilorsPool.getInstance(); 
	@Test
	public void test() {
		
		council =  new Council(coun1, coun2, coun3, coun4);
		coun6 = council.moveCouncil(coun5);
		assertEquals(council.getColor(0),coun5.getColor());
		assertEquals(council.getColor(1),coun1.getColor());
		assertEquals(council.getColor(2),coun2.getColor());
		assertEquals(council.getColor(3),coun3.getColor());
		assertEquals(coun6, coun4);
		list = council.getCouncilors();
		assertEquals(council.getColor(0),list[0].getColor());
		assertEquals(council.getColor(1),list[1].getColor());
		assertEquals(council.getColor(2),list[2].getColor());
		assertEquals(council.getColor(3),list[3].getColor());
	}
}
