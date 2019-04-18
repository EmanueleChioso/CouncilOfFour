/**
 * 
 */
package view.cli;

import java.util.ArrayList;
import java.util.List;

import model.map.GameMap;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna
 *
 */
public class SuperCoolCli {
	private Casella[][] board;
	private static final int ROWS = 5;
	private static final int COLUMNS = 9;
	private GameMap map;
	private List<CastleCasella> castleCasellas;
	private int players;

	/**
	 * takes the map chose and the list of clients
	 * 
	 * @param map
	 * @param playersNames
	 */
	public SuperCoolCli(GameMap map, List<String> playersNames) {
		this.players = map.getNumPlayers();
		this.map = map;
		castleCasellas = new ArrayList<>();
		board = new Casella[ROWS][COLUMNS];
		initSuperCoolCli(playersNames);

	}

	/**
	 * initializes the map, a matrix of casellas, creates different types of
	 * casellas by checking their position
	 * 
	 * @param playersNames
	 */
	public void initSuperCoolCli(List<String> playersNames) {
		CastleCasella casella;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if ((i % 2 == 0 && j % 3 == 0) || i % 2 != 0 && (j + 1) % 3 == 0 && i != 5) {
					casella = new CastleCasella(players, playersNames);
					board[i][j] = casella;
					castleCasellas.add(casella);
					// board[i+1][j]= new InfoCasella();
				} else {
					board[i][j] = new Casella();
				}
			}
		}

		// the order in which my castle casellas are printed makes me iterate
		// every 3 casellas and init them like so
		for (int i = 0, j = 0; i < castleCasellas.size(); i += 3, j++) {
			castleCasellas.get(i).initCasellas(map.getCity(j).getColor().toString(), map.getCity(j).getname(),
					map.getCity(j).getBonus().toString(), map.getKingLocation().getname(),
					map.getCity(j).getEmporiums());

		}
		for (int i = 1, j = 5; i < castleCasellas.size(); i += 3, j++) {
			castleCasellas.get(i).initCasellas(map.getCity(j).getColor().toString(), map.getCity(j).getname(),
					map.getCity(j).getBonus().toString(), map.getKingLocation().getname(),
					map.getCity(j).getEmporiums());
		}
		for (int i = 2, j = 10; i < castleCasellas.size(); i += 3, j++) {
			castleCasellas.get(i).initCasellas(map.getCity(j).getColor().toString(), map.getCity(j).getname(),
					map.getCity(j).getBonus().toString(), map.getKingLocation().getname(),
					map.getCity(j).getEmporiums());
		}

	}

	/**
	 * the problem of printing a matrix of char matrix with cli, is that i can
	 * not go back to the first line and go on printing, so i need a method that
	 * prints all casella's first rows, than all casella's second row and so on..
	 */
	public void print() {
		for (int i = 0; i < ROWS; i++) {
			for (int k = 0; k < 8; k++) {
				for (int j = 0; j < COLUMNS; j++) {
					board[i][j].printRow(k);
					if ((j + 1) % 3 == 0) {
						System.out.print("    **    ");
					}
				}
				System.out.println("");
			}
		}
	}
}
