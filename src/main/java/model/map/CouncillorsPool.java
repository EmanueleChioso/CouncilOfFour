package model.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Const;
import model.player.CardCouncilColor;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class CouncillorsPool implements Serializable {

	private static final long serialVersionUID = -1469674673118267938L;
	private List<Councillor> deadPool; // yes.. I know, i'm a nerd!

	/**
	 * creates a pool with 24 elements, all councillors, 4 per color
	 */
	public CouncillorsPool() {

		deadPool = new ArrayList();

		for (int i = 0; i < Const.COUNCIL; i++) {
			Councillor councilor = new Councillor(CardCouncilColor.BLACK);
			deadPool.add(councilor);
		}
		for (int i = 0; i < Const.COUNCIL; i++) {
			Councillor councilor = new Councillor(CardCouncilColor.BLUE);
			deadPool.add(councilor);
		}
		for (int i = 0; i < Const.COUNCIL; i++) {
			Councillor councilor = new Councillor(CardCouncilColor.ORANGE);
			deadPool.add(councilor);
		}
		for (int i = 0; i < Const.COUNCIL; i++) {
			Councillor councilor = new Councillor(CardCouncilColor.PINK);
			deadPool.add(councilor);
		}
		for (int i = 0; i < Const.COUNCIL; i++) {
			Councillor councilor = new Councillor(CardCouncilColor.PURPLE);
			deadPool.add(councilor);
		}
		for (int i = 0; i < Const.COUNCIL; i++) {
			Councillor councilor = new Councillor(CardCouncilColor.WHITE);
			deadPool.add(councilor);
		}
	}

	/**
	 * takes the councillor that has to be reinserted in the pool
	 * @param coun
	 */
	public void addCouncillor(Councillor coun) {
		deadPool.add(coun);
	}

	/**
	 * @param color
	 * @return return true if color correspond, false otherwise
	 */
	public boolean checkCouncillorColor(CardCouncilColor color) {
		for (int i = 0; i < deadPool.size(); i++) {
			if (deadPool.get(i).getColor().equals(color)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * used only during council creation, it takes a councillor,
	 * removes it from the pool and add it to the council
	 * @return
	 */
	public Councillor removeCouncillorToCreateCouncil() {
		Random rand = new Random();
		Councillor temp;
		int index;
		index = rand.nextInt(deadPool.size());
		temp = deadPool.get(index);
		deadPool.remove(index);
		return temp;
	}

	/**
	 * @param index
	 * @return the councillor to the specified index
	 */
	public Councillor getCouncillor(int index) {
		return deadPool.get(index);
	}

	/**
	 * @return arrayList size
	 */
	public int getLenght() {
		return deadPool.size();
	}

	/**
	 * remove a councillor from teh pool
	 * @param councill
	 */
	public void removeFromPool(Councillor councill) {
		int i = 0;
		for (; i < deadPool.size(); i++) {
			if (deadPool.get(i).equals(councill)) {
				deadPool.remove(i);
				i--;
			}

		}

	}
}
