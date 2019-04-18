/**
 * 
 */
package model.bonus;

import model.player.Player;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna
 *
 */
public class NoBonus extends Bonus {

	private static final long serialVersionUID = -1856708126355499261L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.bonus.Bonus#activateBonus(model.player.Player)
	 */
	@Override
	public void activateBonus(Player player) {
		// ahhahaha lol, non fa nulla!
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.bonus.Bonus#toString()
	 */
	@Override
	public String toString() {
		return "No Bonus";
	}
}
