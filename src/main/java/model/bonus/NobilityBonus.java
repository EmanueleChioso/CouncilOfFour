package model.bonus;

import model.player.Player;

public class NobilityBonus implements PrimitiveBonus{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3633640756048370387L;
	int nobNum;
	
	/**
	 * takes a param, the number on nobility bonus that will be given to a player
	 * @param num
	 */
	public NobilityBonus(int num){
		this.nobNum= num;
		
	}
	/* (non-Javadoc)
	 * @see model.bonus.PrimitiveBonus#activatePrimitiveBonus(model.player.Player)
	 */
	@Override
	public void activatePrimitiveBonus(Player player) {
		player.addNobilty(nobNum);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NOBIL x " +this.nobNum;
	}
	
}
