package model.bonus;

import model.player.Player;

public class ChooseBonus implements PrimitiveBonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1072390879190560955L;
	int num;

	/**
	 * gets a param that is the number of chooseBonus that will be given to the
	 * player
	 * 
	 * @param num
	 */
	public ChooseBonus(int num) {
		this.num = num;
	}

	/* (non-Javadoc)
	 * @see model.bonus.PrimitiveBonus#activatePrimitiveBonus(model.player.Player)
	 */
	@Override
	public void activatePrimitiveBonus(Player player) {
		player.addChooseBonus(this.num);

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CH BONUS x " + this.num;
	}

}
