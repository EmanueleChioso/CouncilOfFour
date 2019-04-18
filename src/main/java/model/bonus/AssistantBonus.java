package model.bonus;

import model.player.Player;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class AssistantBonus implements PrimitiveBonus{

	private static final long serialVersionUID = 6526281787649683195L;
	
	int assNum;
		
	/**
	 * @param num
	 */
	public AssistantBonus(int num){
		this.assNum= num;
		
	}
	
	/* (non-Javadoc)
	 * @see model.bonus.PrimitiveBonus#activatePrimitiveBonus(model.player.Player)
	 */
	@Override
	public void activatePrimitiveBonus(Player player) {
		player.addAssistants(assNum);
	}
		
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ASSI x " +this.assNum;
	}
	
}
