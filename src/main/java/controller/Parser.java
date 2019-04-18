/**
 * 
 */
package controller;

import java.util.HashMap;
import java.util.Map;

import network.common.Command;

/**
 * @author Emanuele Chioso, Lorenzo Della Penna
 *
 */
public class Parser {

	private Parser() {
	}

	public static Map<String, String> parseCommand(String s) {
		Map<String, String> params = new HashMap<>();
		String[] singleParams = s.split("&");
		for (String singleParam : singleParams) {
			String[] splittedSingleParam = singleParam.split("=");
			if (splittedSingleParam.length > 1)
				params.put(splittedSingleParam[0], splittedSingleParam[1]);
		}
		return params;
	}

	/**
	 * @param cardnums
	 * @param string
	 * @return
	 */
	public static String cmd(Command c, String string) {
		return c.toString()+"="+string;
		
	}

	/**
	 * @param cmd
	 * @param cmd2
	 * @param cmd3
	 * @param cmd4
	 * @return
	 */
	public static String concat(String cmd, String cmd2, String cmd3, String cmd4) {
		return cmd + "&" + cmd4 + "&" + cmd2 + "&" + cmd3;
	}

	/**
	 * @param cmd
	 * @param cmd2
	 * @return
	 */
	public static String concat(String cmd, String cmd2) {
		return cmd + "&" + cmd2;
	}

	/**
	 * @param cmd
	 * @param cmd2
	 * @param cmd3
	 * @return
	 */
	public static String concat(String cmd, String cmd2, String cmd3) {
		return cmd + "&" + cmd2+ "&" + cmd3;
	}
	
	
}
