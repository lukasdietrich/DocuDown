package de.splashfish;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileFilter;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class UserInterface extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2835607386532663027L;
	private JLabel lblStylesheet;
	private JLabel lblTitel;
	private JLabel lblPath;
	
	private JTextField txtStylesheet;
	private JTextField txtTitle;	
	private JTextField txtPath;
	
	private JButton btnStylesheet;
	private JButton btnPath;

	private JProgressBar progressBar;
	private JScrollPane scrollPane;
	private ConsoleList consoleList;
	private JButton btnCreate;
	private JLabel lblDestination;
	private JTextField txtDestination;
	private JButton btnDestination;
	private JButton btnDefault;
	private JCheckBox chckbxIndex;
	private JCheckBox chckbxOpen;

	/**
	 * Create the frame.
	 */
	public UserInterface() {
		setMinimumSize(new Dimension(400, 200));
		setTitle("DocuDown");
		setBounds(100, 100, 570, 343);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.lblStylesheet = new JLabel("StyleSheet");
		this.txtStylesheet = new JTextField();
		this.txtStylesheet.setEditable(false);
		this.txtStylesheet.setText("default");
		this.txtStylesheet.setColumns(10);
		this.lblTitel = new JLabel("Titel");
		this.txtTitle = new JTextField();
		this.txtTitle.setText("Unbenannt");
		this.txtTitle.setColumns(10);
		this.btnStylesheet = new JButton("");
		this.btnStylesheet.setToolTipText("durchsuchen");
		this.btnStylesheet.setIcon(new ImageIcon(UserInterface.class.getResource("/images/open.png")));
		this.btnStylesheet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				File last = new File(UserInterface.this.txtStylesheet.getText());
				if(last.exists() && last.isDirectory())
					fc.setCurrentDirectory(last);
				if(fc.showOpenDialog(UserInterface.this) == JFileChooser.APPROVE_OPTION)
					UserInterface.this.txtStylesheet.setText(fc.getSelectedFile().getAbsolutePath());
			}
		});
		
		this.lblPath = new JLabel("Inhalte");
		
		this.txtPath = new JTextField();
		this.txtPath.setEditable(false);
		this.txtPath.setText(System.getProperty("user.home"));
		this.txtPath.setColumns(10);
		
		this.btnPath = new JButton("");
		this.btnPath.setToolTipText("durchsuchen");
		this.btnPath.setIcon(new ImageIcon(UserInterface.class.getResource("/images/open.png")));
		this.btnPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				File last = new File(UserInterface.this.txtPath.getText());
				if(last.exists() && last.isDirectory())
					fc.setCurrentDirectory(last);
				if(fc.showOpenDialog(UserInterface.this) == JFileChooser.APPROVE_OPTION) {
					UserInterface.this.txtPath.setText(fc.getSelectedFile().getAbsolutePath());
				}
			}
		});
		this.progressBar = new JProgressBar();
		this.progressBar.setValue(100);
		this.progressBar.setIndeterminate(false);
		this.scrollPane = new JScrollPane();
		
		this.btnCreate = new JButton("Erstellen");
		this.btnCreate.setToolTipText("DocuDown Dokumentation erstellen");
		this.btnCreate.setIcon(new ImageIcon(UserInterface.class.getResource("/images/tick.png")));
		this.btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File dest = new File(UserInterface.this.txtDestination.getText());
				File src = new File(UserInterface.this.txtPath.getText());
				File css = null;
				try {
					css = new File(UserInterface.class.getResource("/generate-template/style.css").toURI());
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(!UserInterface.this.txtStylesheet.getText().equals("default"))
					css = new File(UserInterface.this.txtStylesheet.getText());

				if(src.exists()) {
					UserInterface.this.consoleList.clear();
					Processor processor = new Processor(UserInterface.this.txtTitle.getText(), dest, src.listFiles(new FileFilter() {

						@Override
						public boolean accept(File arg0) {
							return (arg0.getName().endsWith(".mdown"));
						}
						
					}), css, new Processor.ProcessorStateListener() {
						
						@Override
						public void stateChanged(String desc, int percent) {
							if(percent > -1)
								UserInterface.this.progressBar.setValue(percent);
							UserInterface.this.consoleList.log(desc);
						}
						
					});
						
					new Thread(processor).start();
				}
			}
		});
		this.lblDestination = new JLabel("Ziel");
		this.txtDestination = new JTextField();
		this.txtDestination.setEditable(false);
		this.txtDestination.setText(System.getProperty("user.home"));
		this.txtDestination.setColumns(10);
		this.btnDestination = new JButton("");
		this.btnDestination.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				File last = new File(UserInterface.this.txtDestination.getText());
				if(last.exists() && last.isDirectory())
					fc.setCurrentDirectory(last);
				if(fc.showOpenDialog(UserInterface.this) == JFileChooser.APPROVE_OPTION) {
					UserInterface.this.txtDestination.setText(fc.getSelectedFile().getAbsolutePath());
				}
			}
		});
		this.btnDestination.setToolTipText("durchsuchen");
		this.btnDestination.setIcon(new ImageIcon(UserInterface.class.getResource("/images/open.png")));
		
		this.btnDefault = new JButton("");
		this.btnDefault.setToolTipText("Auf Standard");
		this.btnDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserInterface.this.txtStylesheet.setText("default");
			}
		});
		this.btnDefault.setIcon(new ImageIcon(UserInterface.class.getResource("/images/undo.png")));
		this.chckbxIndex = new JCheckBox("Index anlegen");
		this.chckbxIndex.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				UserInterface.this.chckbxOpen.setEnabled(((JCheckBox)arg0.getSource()).isSelected());
			}
		});
		this.chckbxOpen = new JCheckBox("anschließend öffnen");
		this.chckbxOpen.setEnabled(false);
		this.chckbxOpen.setSelected(true);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(this.scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(this.progressBar, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
							.addComponent(this.chckbxIndex)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(this.chckbxOpen)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(this.btnCreate))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(this.lblStylesheet)
								.addComponent(this.lblTitel)
								.addComponent(this.lblPath)
								.addComponent(this.lblDestination))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(this.txtTitle, GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(this.txtDestination, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
										.addComponent(this.txtPath, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(this.txtStylesheet, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(this.btnDefault)))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(this.btnDestination)
										.addComponent(this.btnPath)
										.addComponent(this.btnStylesheet))))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(this.lblTitel)
						.addComponent(this.txtTitle, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(this.lblStylesheet)
						.addComponent(this.txtStylesheet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.btnStylesheet)
						.addComponent(this.btnDefault))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(this.lblPath)
						.addComponent(this.txtPath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.btnPath))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(this.lblDestination)
						.addComponent(this.txtDestination, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(this.btnDestination))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(this.scrollPane, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(this.chckbxOpen)
							.addComponent(this.btnCreate)
							.addComponent(this.chckbxIndex))
						.addComponent(this.progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		{
			this.consoleList = new ConsoleList();
			this.scrollPane.setViewportView(this.consoleList);
		}
		getContentPane().setLayout(groupLayout);
	}
}
