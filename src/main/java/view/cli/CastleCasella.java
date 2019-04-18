/**
 * 
 */
package view.cli;

import java.util.ArrayList;
import java.util.List;

import model.player.Player;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class CastleCasella extends Casella{
	
	char[][] castle;
	private static final int ROWS=8;
	private static final int COLUMNS=28;
	private static final String c1Row = " _   |~  _   ";
	private static final String c2Row = "[_]--\'--[_]  ";
	private static final String c3Row = "|'|\"\"`\"\"|'|  ";
	private static final String c4Row = "| | /^\\ | |  ";
	private static final String c5Row = "|_|_|I|_|_|  ";
	private static final String IN = "[x]";
	private static final String NOTIN = "[ ]";
	private int numPlayers;
	private String color;
	private String name;
	private String bonus;
	private String compName;
	private String compBonus;
	private String compColor;
	private String finalPlayers;
	private String finalString;
	private String final1Row;
	private String final2Row;
	private String final3Row;
	private String final4Row;
	private String final5Row;
	private String final6Row;
	private List<String> names;
	private List<String> playersList;
	private static final String KING = " ಠ_ರೃ KING";
	private static final String BONUS = "Bonus: ";
	private static final String COLOR = "Color: ";
	private static final String PLAYERS = "Players: ";
	
	/**
	 * takes the number of players that are going to play in this map
	 * and a list of their names
	 * @param players
	 * @param playersNames
	 */
	public CastleCasella(int players, List<String> playersNames) {
		names = new ArrayList<>();
		names = playersNames;
		castle = new char[ROWS][COLUMNS];
		this.numPlayers = players;
		playersList = new ArrayList<>();
	}
	
	/**
	 * @param color, the city's color
	 * @param name, the city's name
	 * @param bonus, the city's bonus
	 * @param kingsLanding, the king's location
	 * @param players, the player list
	 */
	public void initCasellas(String color, String name , String bonus, String kingsLanding, List<Player> players){
		this.bonus=bonus;
		this.color=color;
		this.name=name;
		int cont = 0;
		for(int i=0; i<players.size(); i++){
			playersList.add(players.get(i).getName());
		}
		compName=fillSpace(name);
		compBonus=fillSpace(BONUS.concat(bonus));
		final1Row=fillSpace(c1Row.concat(COLOR));
		final2Row=fillSpace(c2Row.concat(color));
		final3Row=fillSpace(c3Row);
		if(kingsLanding.equals(name)){
			final4Row=fillSpace(c4Row.concat(KING));
		}
		else{
			final4Row=fillSpace(c4Row);
		}
		final5Row=fillSpace(c5Row);
		final6Row=PLAYERS;
		//prints "[ ]" if there are no player's emporiums in this city
		if(players.size()==0){
			for(int i=0; i<names.size(); i++){
				final6Row=final6Row.concat(NOTIN);
			}
		}else{
			//prints "[x]" if there is an emporium, it prints an "x" in the exact space corresponding to player's number
			for(int i=0; i<names.size(); i++){
				if(playersList.contains(names.get(i))){
					final6Row=final6Row.concat(IN);
				}else{
					final6Row=final6Row.concat(NOTIN);
				}
			}
		}
		final6Row=fillSpace(final6Row);
		finalString=compName.concat(final1Row).concat(final2Row).concat(final3Row).concat(final4Row).concat(final5Row).concat(compBonus).concat(final6Row);
		for(int i=0; i<ROWS; i++){
			for(int j=0; j<COLUMNS; j++,cont++){
				castle[i][j]=finalString.charAt(cont);		
			}
		}
	}
	
	/**
	 * fills a given string to 28 chars, so that it can be placed in the matrix
	 * @param string
	 * @return a filled string
	 */
	public String fillSpace(String string){
		String ret=string;
		int toFill = COLUMNS - string.length();
		for(int i=0; i<toFill; i++){
			ret = ret.concat(" ");
		}
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see view.cli.Casella#printRow(int)
	 */
	@Override
	public void printRow(int i) {
		for(int j=0; j<COLUMNS; j++){
			print(castle[i][j]);
		}
	}
	
	/**
	 * the short way of System.out.print(String)
	 * @param c
	 */
	public void print(char c){
		System.out.print(c);
	}
}
