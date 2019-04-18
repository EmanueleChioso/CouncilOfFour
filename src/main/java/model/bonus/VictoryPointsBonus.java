package model.bonus;

import model.player.Player;

public class VictoryPointsBonus implements PrimitiveBonus{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6955383905208252189L;
	int num;
	/**
	 * takes a param, the number of victory points that will be give to a player
	 * @param num
	 */
	public VictoryPointsBonus(int num) {
		this.num=num;
	}
	
	/* (non-Javadoc)
	 * @see model.bonus.PrimitiveBonus#activatePrimitiveBonus(model.player.Player)
	 */
	@Override
	public void activatePrimitiveBonus(Player player) {
		player.addPoints(num);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "POINTS x " +this.num;
	}
}
