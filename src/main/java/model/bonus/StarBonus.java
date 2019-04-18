package model.bonus;

import model.player.Player;

public class StarBonus implements PrimitiveBonus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7292816981537772913L;
	int num;
	/**
	 * takes a param, the number of star bnus that will be given to a player
	 * @param num
	 */
	public StarBonus(int num){
		this.num=num;
	}
	
	/* (non-Javadoc)
	 * @see model.bonus.PrimitiveBonus#activatePrimitiveBonus(model.player.Player)
	 */
	@Override
	public void activatePrimitiveBonus(Player player) {
		player.addPrimaryAction();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "STAR x " +this.num ;
	}
	
	
	
	
	
}
