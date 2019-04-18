package model.map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Const;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class MapFactory {
	
	/**
	 * 
	 */
	private MapFactory() {
	}


	/**
	 * @param mapNum
	 * @return the map given a specific number
	 */
	public static GameMap getMap(int mapNum) {
		if (mapNum >= 10){
			return randomMap(Const.PERC[mapNum-10]);
			
		}
		if (mapNum > 0 && mapNum <= 8)
			return mapFromFile(mapNum);
		return null;

	}

	/**
	 * @return the random map using the Percentuale
	 */
	private static GameMap randomMap(double perc) {
		GameMap m = new GameMap();
		m.randomizeMatrix(perc);
		return m;
	}

	/**
	 * @param mapNumber
	 * @return a GameMap read from the txt 1/8
	 */
	private static GameMap mapFromFile(int mapNumber) {
		GameMap m;
		try (BufferedReader fileIN = new BufferedReader(
				new FileReader(Const.FILENAME + String.valueOf(mapNumber) + ".txt"))) {
			m = new GameMap();
			int city1;
			int city2;
			String myLine = fileIN.readLine();
			while (myLine != null) {
				city1 = Character.getNumericValue(myLine.charAt(0)) - 10;
				city2 = Character.getNumericValue(myLine.charAt(2)) - 10;
				m.addConnection(city1, city2);
				myLine = fileIN.readLine();

			}

		} catch (IOException e) {
			m = null;
			Logger.getGlobal().log(Level.ALL, "MAP NOT INSTANCIATED", e);

		}
		return m;
	}
}
