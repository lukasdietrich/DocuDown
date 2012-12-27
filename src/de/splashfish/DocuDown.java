package de.splashfish;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class DocuDown {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UserInterface ui = new UserInterface();
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// cross-platform look
		} finally {
			SwingUtilities.updateComponentTreeUI(ui);
			ui.setVisible(true);
		}

	}

}
