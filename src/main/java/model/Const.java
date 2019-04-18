package model;

/**
 * our const class
 * 
 * @author Emanuele Chioso, Lorenzo Della Penna
 *
 */
public final class Const {

	// game
	public static final int POLITICCARDS = 200;
	public static final int CITIESPERREGION = 5;
	public static final int EMPORIUMSTOWIN = 1;
	public static final int CITIES = 15;
	public static final int COINS = 20;
	public static final int NOBILITY = 21;
	public static final int POINTS = 100;
	public static final int POLITICDECK = 20;
	public static final int PERMITCARDS = 45;
	public static final int PERMITDECK = 15;
	public static final int VISIBLEPERMITS = 2;
	public static final int REGIONS = 3;
	public static final int COUNCIL = 4;
	public static final int MAX_PLAYERS = 8;
	public static final int MIN_PLAYERS = 2;
	public static final int KINGCOUNCIL = 3;
	public static final int COUNCILORS = 4;
	public static final double[] PERC = {  0.8,  0.65,  0.50,  0.4,  0.30,  0,25};

	// file
	public static final String FILENAME = "map";
	public static final String FILEBONUSINDEX = "";

	// controller
	public static final int GAMESTARTTIME = 20000;
	public static final int PLAYERTIME = 240000;

	// prizes
	public static final int KINGTILES = 5;
	public static final int BLUEPRIZE = 5;
	public static final int BRONZEPRIZE = 8;
	public static final int SILVERPRIZE = 12;
	public static final int GOLDPRIZE = 20;
	public static final int REGIONPRIZE = 5;
	public static final int REMAININGCOUNCILLORS = 8;
	public static final int INITIALKINGCITY = 9;

	// cli strings
	public static final String WARNING = "[WARNING]";
	public static final String COMMUNICATION = "[COMMUNICATION]";
	public static final String YES = "y";
	public static final String NO = "n";
	public static final String MARKET = "MARKET";
	public static final String DISCONNECTED = "[DISCONNECTED]";
	public static final String SISTEMA = "SISTEMA";
	public static final String MESSAGEKICKED = " you did not do any action in your turn and the system kicked you.";
	public static final String MESSAGEFASTACTIONS = " you have no more fast actions";
	public static final String MESSAGEASSISTANTS = " Sorry, Not enought assistans!";
	public static final String MESSAGEEVERITHINGOK = " everything ok!";
	public static final String ACTION = "action";

	private Const() {
	};
}
