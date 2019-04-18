/**
 * 
 */
package model.cards;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.bonus.Bonus;
import model.bonus.PrimitiveBonusFactory;
import model.map.Region;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class CardsPool {
	/**
	 * reads from a configuration file and creates all cards and deck
	 */
	private List<PermitCard> seaCards;
	private List<PermitCard> hillCards;
	private List<PermitCard> mountainCards;
	private Region sea;
	private Region hill;
	private Region mountain;
	
	String[] splitted;
	Bonus bonus;
	PermitCard myCard;
	
	/**
	 * @param sea
	 * @param hill
	 * @param mountain
	 */
	public CardsPool(Region sea, Region hill, Region mountain) {
		
		seaCards = new ArrayList<>();
		hillCards = new ArrayList<>();
		mountainCards = new ArrayList<>();
		
		this.sea=sea;
		this.hill=hill;
		this.mountain=mountain;
		
		try {
			BufferedReader fileIn = new BufferedReader(new FileReader("cardConfigurationFile.txt"));
			String myLine = fileIn.readLine();
			while(myLine!=null){
				splitted = myLine.split("&");
				bonus = new Bonus();
				myCard = null;
				
				if("s".equals(splitted[0])){
					myCard = new PermitCard(this.sea);
				}
				else if("h".equals(splitted[0])){
					myCard = new PermitCard(this.hill);
				}
				else if("m".equals(splitted[0])){
					myCard = new PermitCard(this.mountain);
				}
				
				cityLoop();
				
				
				
				myCard.setBonus(bonus);
				if("s".equals(splitted[0])){
					seaCards.add(myCard);
				}
				else if("h".equals(splitted[0])){
					hillCards.add(myCard);
				}
				else if("m".equals(splitted[0])){
					mountainCards.add(myCard);
				}
				myLine = fileIn.readLine();
			}
		} catch ( IOException e) {
			Logger.getGlobal().log(Level.ALL, "Error.", e);
		}
	}
	
	
	/**
	 * it easy our code
	 */
	private void cityLoop() {
		for (int i = 1; i < splitted.length; i++) {
			if (splitted[i].length() == 1) {
				myCard.setInitials(splitted[i]);
			}
			if (splitted[i].length() == 5) {
				bonus.addBonus(PrimitiveBonusFactory.getPrimitiveBonus(splitted[i]));
			}
		}

	}

	/**
	 * @return the list of sea cards
	 */
	public List<PermitCard> getSeaDeck(){
		return seaCards;
	}
	/**
	 * @return the list of hill cards
	 */
	public List<PermitCard> getHillDeck(){
		return hillCards;
	}
	/**
	 * @return the list of mountain cards
	 */
	public List<PermitCard> getMountainDeck(){
		return mountainCards;
	}
}
