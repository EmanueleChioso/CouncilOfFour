/**
 * 
 */
package model;

import java.util.EnumMap;

import model.map.CityColor;
import model.player.Player;


/**
 * @author Emanuele Chioso, Lorenzo Della Penna
 *
 */
public class PrizesHandler {
	
	private static final int[] KINGPRIZES = { 25, 18, 12, 7, 3 };
	
	private static EnumMap<CityColor, Integer> colorPrizes;
	private EnumMap<CityColor, Boolean> colorsAvailability;
	private boolean[] regionPrizesBool;
	private boolean[] kingPrizesBool;

		  
	/**
	 * checks if there still is a king prize.
	 * 
	 * @return true if there is a king prize available, false if there isn't any
	 */

	public PrizesHandler() {
		regionPrizesBool = new boolean[Const.REGIONS];
		kingPrizesBool = new boolean[KINGPRIZES.length];

		for (int i =0 ; i < Const.REGIONS ; i ++ ) {
			regionPrizesBool[i] = true;
		}
		for (int i =0 ; i < Const.KINGTILES ; i ++ ) {
			kingPrizesBool[i] = true;
		}

		colorPrizes = new EnumMap<> (CityColor.class);
		colorPrizes.put(CityColor.BLUE, Const.BLUEPRIZE);
		colorPrizes.put(CityColor.SILVER, Const.SILVERPRIZE);
		colorPrizes.put(CityColor.GOLD, Const.GOLDPRIZE);
		colorPrizes.put(CityColor.BRONZE, Const.BRONZEPRIZE);
		colorPrizes.put(CityColor.PURPLE, 0);
		
		colorsAvailability =  new EnumMap<> (CityColor.class);
		colorsAvailability.put(CityColor.BLUE, new Boolean(true));
		colorsAvailability.put(CityColor.BRONZE, new Boolean(true));
		colorsAvailability.put(CityColor.SILVER, new Boolean(true));
		colorsAvailability.put(CityColor.GOLD, new Boolean(true));
		colorsAvailability.put(CityColor.PURPLE, new Boolean(true));

		

	}

	/**
	 * @return the first king prize available, without using it
	 */
	public int showFirstKingPrizeIndexAvailable() {
		if (checkKingPrizeAvailability()) 
			return  kingFirstAvailable();
			
		return -1;
	}

	/**
	 * does not use the kingprize but shows the first king prize availability
	 * 
	 * @return
	 */
	private int kingFirstAvailable() {
		for (int i = 0; i < kingPrizesBool.length; i++) {
			if (kingPrizesBool[i])
				return i;
		}
		return -1;
	}

	/**
	 * checks if there is a king prize and uses the first available, returns the
	 * used bonus
	 * 
	 * @param region
	 * @return the king prize you used
	 */
	public void useKingPrize(Player player) {
		if (checkKingPrizeAvailability()) {
			int num = kingFirstAvailable();
			if (num != -1) {
				kingPrizesBool[num] = false;
				player.addPoints(KINGPRIZES[num]);
			}

		}

	}

	/**
	 * uses the color if the bonus is still available
	 * @param player 
	 * 
	 * @param col
	 * @return true if the prize was given
	 */
	public synchronized boolean useColorPrize(Player player, CityColor col) {
		
		boolean colorBonusAvailable = colorsAvailability.get(col);
		if (colorBonusAvailable) {
			colorsAvailability.put(col, false);
			player.addPoints(colorPrizes.get(col));
		}

		return colorBonusAvailable;

	}

	/**
	 * gets the region num (0 1 2) and returns the prize you get if you built in
	 * everycity
	 * 
	 * @param region
	 * @return true if the prize was given
	 */
	public boolean useRegionPrize(Player player, int region) {
		boolean donezo= checkRegionPrizeAvailability(region);
		if (donezo) {
			regionPrizesBool[region] = false;
			player.addPoints( Const.REGIONPRIZE);
		}
		
		return donezo;
	}

	public boolean checkRegionPrizeAvailability(int region) {
		return regionPrizesBool[region];
	}

	public boolean checkKingPrizeAvailability() {
		for (boolean a : kingPrizesBool) {
			if (a)
				return a;
		}
		return false;
	}

	/**
	 * @param col
	 * @return
	 */
	public boolean checkColorPrizeAvailability(CityColor col) {
		return this.colorsAvailability.get(col);
	}

}
