import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.bonus.PrimitiveBonusFactory;
import model.cards.PermitCard;
import model.cards.PoliticCard;
import model.map.Region;
import model.market.Market;
import model.market.Sellable;
import model.player.CardCouncilColor;
import model.player.Player;

public class TestMarket {

	Market market;
	PermitCard pe;
	PoliticCard po;
	Region region;
	Player buyer;
	Player seller;
	List<Sellable> list;

	@Test
	public void test() {
		list = new ArrayList<>();
		buyer = new Player("lele");
		seller = new Player("lore");
		market = new Market();
		region = new Region();
		pe = new PermitCard(region);
		pe.setPrice(12, seller);
		po = new PoliticCard(CardCouncilColor.PINK);
		po.setPrice(6, seller);
		seller.addCard(pe);
		seller.addCard(po);

		assertEquals(1, seller.getNumPermitCard());
		assertEquals(1, seller.getNumPoliticCard());
		assertNull(PrimitiveBonusFactory.getPrimitiveBonus("stri4ngTooLong"));
		buyer.addCoin(20);
		market.addMarketItem(pe);
		assertFalse(market.addMarketItem(pe));
		assertTrue(market.addMarketItem(po));
		list = market.getAllItems();
		assertEquals(pe, list.get(1));
		assertEquals(po, list.get(0));
		assertEquals(2, market.getSize());
		assertEquals(po, market.getItem(0));
		assertEquals(pe, market.getItem(1));
		market.sell(pe, buyer, seller);
		assertEquals(12, seller.getCoin());
		market.sell(po, buyer, seller);
		assertEquals(18, seller.getCoin());
		assertEquals(2, buyer.getCoin());
		assertEquals(0, seller.getNumPermitCard());
		assertEquals(0, seller.getNumPoliticCard());

	}
}