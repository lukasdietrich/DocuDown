package de.splashfish;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.SwingUtilities;

public class ConsoleList extends JList<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6014026812152156862L;
	
	private DefaultListModel<String> model;
	
	public ConsoleList() {
		this.model = new DefaultListModel<String>();
		this.setModel(model);
	}
	
	public void log(String text) {
		model.addElement(text);
		SwingUtilities.invokeLater(new updateState());
	}
	
	public void clear() {
		model.clear();
	}
	
	private class updateState implements Runnable {

		@Override
		public void run() {
			ConsoleList.this.ensureIndexIsVisible(model.size()-1);
			ConsoleList.this.repaint();
		}
		
	}

}
