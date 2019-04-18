package view;

public class GUIFactory {
	
	private static String cli = "CLI";
	private static String gui = "GUI";
	
	private GUIFactory(){
	}
	
	public static UserInterface getGUI(String scelta) {
		
		UserInterface  ret = null; 
		if (scelta.equalsIgnoreCase(cli)) 
			ret = new Cli();
		if (scelta.equalsIgnoreCase(gui)) 
			;
			/*
			 * per ora non fai niente :D
			 * EHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEHEH
			 */
		return ret;

	}
	
	
	
	
	
}
