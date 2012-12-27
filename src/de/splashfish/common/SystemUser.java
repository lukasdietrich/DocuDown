package de.splashfish.common;

import java.io.File;

/**
 * 
 * @author lukas
 *
 */
public class SystemUser {

	/**
	 * getDirectory finds the user's working directory according to the currently
	 * used operating system.
	 * <table>
	 * <tr><td>Windows:</td><td><i>%APPDATA%</i></td></tr>
	 * <tr><td>Mac:</td><td><i>~/Library/Application Support/</i></td></tr>
	 * <tr><td>*nux:</td><td><i>~/</i></td></tr>
	 * <tr><td>fallback:</td><td><i>{@link System}.getProperty("user.dir")</i></td></tr>
	 * </table>
	 * @return user's working directory
	 */
	public static File getWorkingDirectory() {
	    String OS = System.getProperty("os.name").toUpperCase();
	    
	    if (OS.contains("WIN"))
	        return new File(System.getenv("APPDATA"));
	    else if (OS.contains("MAC"))
	        return new File(System.getProperty("user.home") + "/Library/Application Support");
	    else if (OS.contains("NUX"))
	        return new File(System.getProperty("user.home"));
	    
	    return new File(System.getProperty("user.dir"));
	}
	
	/**
	 * is equivalent to new File({@link SystemUser}.getWorkingDirectory(), sub : String);
	 * 
	 * @param sub a subordinate directory or file
	 * @return a subordinate directory or file within the working directory
	 */
	public static File getWorkingDirectory(String sub) {
		return new File(getWorkingDirectory(), sub);
	}
	
}
