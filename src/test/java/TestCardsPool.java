import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.cards.CardsPool;
import model.cards.PermitCard;
import model.map.Region;

/**
 * 
 */

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class TestCardsPool {
	private CardsPool pool;
	private Region sea;
	private Region hill;
	private Region mountain;
	private List<PermitCard> seaDeck;
	private List<PermitCard> hillDeck;
	private List<PermitCard> mountainDeck;
	@Test
	public void test() {
		sea=new Region();
		hill=new Region();
		mountain=new Region();
		seaDeck= new ArrayList<>();
		hillDeck= new ArrayList<>();
		mountainDeck= new ArrayList<>();
		pool=new CardsPool(sea, hill, mountain);
		seaDeck=pool.getSeaDeck();
		hillDeck=pool.getHillDeck();
		mountainDeck=pool.getMountainDeck();
		assertEquals(15,seaDeck.size());
		assertEquals(15,hillDeck.size());
		assertEquals(15,mountainDeck.size());
		for(int i=0; i<seaDeck.size(); i++){
			assertEquals(seaDeck.get(i).getRegion(), sea );
		}
		for(int i=0; i<hillDeck.size(); i++){
			assertEquals(hillDeck.get(i).getRegion(), hill );
		}
		for(int i=0; i<mountainDeck.size(); i++){
			assertEquals(mountainDeck.get(i).getRegion(), mountain );
		}
	}

}
