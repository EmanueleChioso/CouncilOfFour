package model.bonus;

import java.io.Serializable;

import model.player.Player;

@FunctionalInterface
public interface PrimitiveBonus extends Serializable {
	
	/**
	 * activates the bonus on the specified player
	 * @param player
	 */
	public void activatePrimitiveBonus(Player player);

	/**
	 * @return the overridden toString 
	 */
	@Override
	public String toString();

}
