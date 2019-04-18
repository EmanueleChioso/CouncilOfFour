package model.bonus;

import model.player.Player;

public class CoinBonus implements PrimitiveBonus{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5928932746584670273L;
	int coinNum;
	
	/**
	 * takes a param, the number of coins that will be given to a player
	 * @param num
	 */
	public CoinBonus (int num){
		this.coinNum = num;
	}
	/* (non-Javadoc)
	 * @see model.bonus.PrimitiveBonus#activatePrimitiveBonus(model.player.Player)
	 */
	@Override
	public void activatePrimitiveBonus(Player player) {
		player.addCoin(coinNum);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "COINS x " +this.coinNum;
	}
}
