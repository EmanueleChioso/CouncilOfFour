package model.bonus;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna 
 *
 */
public class PrimitiveBonusFactory {

	private static final String ASSISTANTBONUS = "assi";
	private static final String GOLDBONUS = "coin";
	private static final String VICTORYPOINTSBONUS = "poin";

	private static final String NOBILITYBONUS = "nobi";
	private static final String POLITICCARDBONUS = "poli";
	private static final String PERMITCARDBONUS = "perm";

	private static final String CHOOSEBONUS = "choo";
	private static final String STARBONUS = "star";

	/**
	 * a factory that creates all primitive bonus
	 */
	private PrimitiveBonusFactory() {
	}

	/**
	 * takes a string which is the type of the bonus that is going to be created
	 * @param scelta
	 * @return the created primitive bonus
	 */
	public static PrimitiveBonus getPrimitiveBonus(String scelta) {

		String firstPart;
		int num;

		num = Integer.parseInt(scelta.substring(4, 5));
		firstPart = scelta.substring(0, 4).toLowerCase();
		PrimitiveBonus ret = null;
		if (firstPart.equals(ASSISTANTBONUS))
			 ret =new AssistantBonus(num);

		else if (firstPart.equals(GOLDBONUS))
			 ret = new CoinBonus(num);

		else if (firstPart.equals(VICTORYPOINTSBONUS))
			 ret = new VictoryPointsBonus(num);

		else if (firstPart.equals(NOBILITYBONUS))
			 ret = new NobilityBonus(num);

		else if (firstPart.equals(POLITICCARDBONUS))
			 ret = new PoliticBonus(num);

		else if (firstPart.equals(PERMITCARDBONUS))
			 ret = new PermitBonus(num);

		else if (firstPart.equals(CHOOSEBONUS))
			 ret =new ChooseBonus(num);

		else if (firstPart.equals(STARBONUS))
			 ret =new StarBonus(num);
		return ret;

	}
}
