package model.map;

import java.io.Serializable;

import model.Const;
import model.player.CardCouncilColor;

//@LorenzoDellaPenna

public class Council implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8842039524656732791L;
	private Councillor[] councilors;

	 
	
	/**
	 * it takes 4 councilors from a councilors-pool that should be initialized
	 * in gameControl
	 * 
	 * @param coun1
	 * @param coun2
	 * @param coun3
	 * @param coun4
	 */
	public Council(Councillor coun1, Councillor coun2, Councillor coun3, Councillor coun4) {
		councilors = new Councillor[Const.COUNCIL];
		councilors[0] = coun1;
		councilors[1] = coun2;
		councilors[2] = coun3;
		councilors[3] = coun4;
	}

	
	/**
	 * @param num
	 * @return the color of the councillor to the specified index
	 */
	public CardCouncilColor getColor(int num) {
		return councilors[num].getColor();
	}

	/**
	 * used to move a council with the given councillor 
	 * @param coun
	 * @return the councillor that has been fired
	 */
	public Councillor moveCouncil(Councillor coun) {

		Councillor tempBefore;
		Councillor tempAfter;
		Councillor theReturn;
		theReturn = councilors[3];
		tempBefore = coun;

		for (int i = 0; i < Const.COUNCIL; i++) {
			tempAfter = councilors[i];
			councilors[i] = tempBefore;
			tempBefore = tempAfter;
		}
		return theReturn;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		String ret = "formation: ";
		for(int i=0; i<Const.COUNCIL;i++){
			ret= ret+(i+1)+"-"+councilors[i].toString()+" ";
		}
		return ret;
	}


	/**
	 * @return all the councillors in the council
	 */
	public Councillor[] getCouncilors() {
		return this.councilors;
	}
	
}
