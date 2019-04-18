/**
 * 
 */
package network.common;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna
 *
 */
public enum Command {
	
	PLAYER("player"),
	ACTION("action"),
	COUNCIL("council"),
	PERMIT("permit"),
	REGION("region"),
	CARDNUMS("cardNums"),
	BUILTCITY("builtCity"),
	COUNCILLOR("councillor"),
	KING("king"),
	
	
	
	JOINGAME("join"),
	END("end"),
	CONNECT("connect"),
	FASTACTION1("fa1"),
	FASTACTION2("fa2"),
	FASTACTION3("fa3"),
	FASTACTION4("fa3"),
	PRIMARYACTION1("pa1"),
	PRIMARYACTION2("pa2"),
	PRIMARYACTION3("pa3"),
	PRIMARYACTION4("pa4"),
	SELLCARD("sellc"),
	BUYCARD("buyc"),
	SELLASS("sella"),
	BUYASSISTANT("buya");
	private final String text;

    /**
     * @param text
     */
    private Command(String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }

}
