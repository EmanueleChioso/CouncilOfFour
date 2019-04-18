package model.bonus;

import model.player.Player;

public class PermitBonus implements PrimitiveBonus	{
	
	private static final long serialVersionUID = 2251609441447501749L;
	private int num;
	
	/**
	 * takes a param, the number of permit bonus that will be given to a player
	 * @param num
	 */
	public PermitBonus(int num){
		this.num=num;
	}
	
	/* (non-Javadoc)
	 * @see model.bonus.PrimitiveBonus#activatePrimitiveBonus(model.player.Player)
	 */
	@Override
	public void activatePrimitiveBonus(Player player) {
		player.addPermitBonus(num);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PECARDS x " +this.num;
	}

}
