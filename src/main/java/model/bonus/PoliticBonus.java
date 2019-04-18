package model.bonus;

import model.player.Player;

public class PoliticBonus implements PrimitiveBonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7204838366850187605L;
	private int cardNum;

	/**
	 * takes a param, the number of politic bonus that will be given to a player
	 * @param num
	 */
	public PoliticBonus(int num) {
		this.cardNum = num;
	}

	/* (non-Javadoc)
	 * @see model.bonus.PrimitiveBonus#activatePrimitiveBonus(model.player.Player)
	 */
	@Override
	public void activatePrimitiveBonus(Player player) {
		player.addPoliticBonus(cardNum);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "POCARDS x " + this.cardNum;
	}

}