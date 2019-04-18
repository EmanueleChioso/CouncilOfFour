/**
 * 
 */
package view.cli;

/**
 * this class will be extended by CastleCasella to create game's map
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class Casella {
	private static final int ROWS =8;
	private static final int COLUMNS = 10;
	private char[][] casella;
	
	/**
	 * creates a char matrix and fill it with " ";
	 */
	public Casella() {
		casella = new char[ROWS][COLUMNS];
		for(int i=0; i<ROWS; i++){
			for(int j=0; j<COLUMNS; j++){
				casella[i][j]=' ';
			}
		}
	}
	
	/**
	 * takes a param, the index from which the casella must be printed
	 * @param i
	 */
	public void printRow(int i){
		for(int j=0; j<COLUMNS; j++){
			System.out.print(casella[i][j]);
		}
	}
}
