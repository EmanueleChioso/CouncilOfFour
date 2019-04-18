package model.bonus;

import java.io.Serializable;
import java.util.ArrayList;

import model.player.Player;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class Bonus implements Serializable {

	private static final long serialVersionUID = 1352808719698038615L;
	private ArrayList<PrimitiveBonus> bonuses;

	
	public Bonus() {
		bonuses = new ArrayList();
	}

	public Bonus(PrimitiveBonus bonus1) {
		bonuses = new ArrayList();
		bonuses.add(bonus1);
	}

	public Bonus(PrimitiveBonus bonus1, PrimitiveBonus bonus2) {
		bonuses = new ArrayList();
		bonuses.add(bonus1);
		bonuses.add(bonus2);

	}

	/**
	 * for all primitive bonuses, activates their function
	 * @param player
	 */
	public void activateBonus(Player player) {
		for (PrimitiveBonus bonus : bonuses) {
			bonus.activatePrimitiveBonus(player);
		}
	}

	/**
	 * add a primitive bonus to primitive bonus arrayList
	 * @param bonus
	 */
	public void addBonus(PrimitiveBonus bonus) {
		bonuses.add(bonus);
	}

	/**
	 * @param index
	 * @return the bonus at the given index
	 */
	public PrimitiveBonus getPrimitiveBonus(int index) {
		return bonuses.get(index);
	}

	/**
	 * @return the size of primitive bonus array 
	 */
	public int getNumBonus() {
		return bonuses.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String ret = "";
		for (int i = 0; i < bonuses.size(); i++) {
			ret = ret.concat(bonuses.get(i).toString());
			ret = ret.concat(" ");
		}
		return ret;
	}

}
