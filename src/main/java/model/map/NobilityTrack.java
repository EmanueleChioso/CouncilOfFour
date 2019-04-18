/**
 * 
 */
package model.map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Const;
import model.bonus.Bonus;
import model.bonus.NoBonus;
import model.bonus.PrimitiveBonusFactory;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class NobilityTrack implements Serializable{
	
	private static final long serialVersionUID = -1927186582727701691L;
	private Bonus[] track;
	/**
	 * a file reader that creates a nobility track
	 */
	public NobilityTrack() {
		track = new Bonus[Const.NOBILITY];
		BufferedReader fileIn = null;
		Bonus bonus = null;
		try {
			fileIn = new BufferedReader(new FileReader("nobilityTrackConfigurationFile.txt"));
		} catch (FileNotFoundException e) {
			Logger.getGlobal().log(Level.ALL, "Error,  did not find nobilityTrackConfigurationFile.txt  [FILENOTFOUNDEXCEPTION]", e);
		}
		String myLine = null;
		try {
			myLine = fileIn.readLine();		
			while(myLine!=null){
				String[] splitted = myLine.split("&");
				int index = Integer.parseInt(splitted[0]);
				if("-".equals(splitted[1])){
					bonus = new NoBonus();
				}
				else{
					bonus = new Bonus();
					for(int j=1; j<splitted.length;j++){
						bonus.addBonus(PrimitiveBonusFactory.getPrimitiveBonus(splitted[j]));
					}			
				}
				track[index]=bonus;
				myLine = fileIn.readLine();
			}
		} catch (IOException e) {
			Logger.getGlobal().log(Level.ALL, "Error while reading nobilityTrackConfigurationFile.txt  [IOEXCEPTION] ", e);
		}
	}
	
	/**
	 * @param index
	 * @return return the bonus to the specified index
	 */
	public Bonus getBonus(int index){
		return track[index];
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		String ret = "NOBILITY TRACK\n";
		for(int i=0; i<Const.NOBILITY;i++){
			ret = ret +"[ "+ i +"-"+ getBonus(i).toString()+" ] ";
		}
		return ret+"\n";
	}
}
