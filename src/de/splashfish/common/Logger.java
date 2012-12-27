package de.splashfish.common;

import java.io.PrintStream;

/**
 * 
 * @author lukas
 *
 */
public class Logger {

	private static Logger instance;
	
	/**
	 * If there is no Logger created yet, there will be created one,
	 * else the current one is just used.
	 * @return instance of the current logger
	 */
	public static Logger getLogger() {
		if(instance == null)
			instance = new Logger();
		return instance;
	}
	
	private void log(PrintStream stream, String message, boolean toFile) {
		message = DateTime.current("[%d.%M.%y@%h:%m:%s:%n] "+ message);
		stream.println(message);
	}
	
	/**
	 * log uses the systems out stream
	 * @param message message to be logged
	 */
	public void log(String message) {
		this.log(System.out, message, false);
	}
	
	/**
	 * uses the given {@link Exception} to log an error (getLocalizedMessage())
	 * with the given class as a source identifier in front of it
	 * 
	 * @param source
	 * @param e {@link Exception} that was accountable for that exception
	 */
	public void err(Class<?> source, Exception e) {
		this.err(source.getName() + ":", e);
	}
	
	/**
	 * logs an error with the given class as a source identifier in front of it
	 * 
	 * @param source
	 * @param e {@link Exception} that was accountable for that exception
	 */
	public void err(Class<?> source, String error) {
		this.err(source.getName() + ": " + error);
	}
	
	/**
	 * uses the given {@link Exception} to log an error (getLocalizedMessage())
	 * with the given message in front of it
	 * 
	 * @param message
	 * @param e {@link Exception} that was accountable for that exception
	 */
	public void err(String message, Exception e) {
		this.err(message +" "+ e.getMessage());
	}
	
	/**
	 * uses the given {@link Exception} to log an error (getLocalizedMessage())
	 * 
	 * @param e {@link Exception} that was accountable for that exception
	 */
	public void err(Exception e) {
		this.err(e.getMessage());
	}
	
	/**
	 * err uses the systems err stream and writes information into a generic logfile
	 * 
	 * @param error message to be logged
	 */
	public void err(String error) {
		this.log(System.err, error, true);
	}
}
