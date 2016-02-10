package View;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel;
import org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.BoxLayout;
import java.awt.GridLayout;

public class SamplerView extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;

	/**
	 * Create the frame.
	 */
	private JPanel bottomPane;
	public SamplerView() {
		
		setLookAndFeel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1043, 628);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		bottomPane = new JPanel();
		contentPane.add(bottomPane, BorderLayout.SOUTH);
		bottomPane.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		
		//scrollPane.setLayout(new BorderLayout());
		
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel LB_logo = new JLabel("");
		panel.add(LB_logo, BorderLayout.WEST);
		LB_logo.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/logo.png")));
		
		JPanel MenuPanel = new JPanel();
		panel.add(MenuPanel, BorderLayout.CENTER);
		GridBagLayout gbl_MenuPanel = new GridBagLayout();
		gbl_MenuPanel.columnWeights = new double[]{1.0};
		gbl_MenuPanel.columnWidths = new int[]{0};
		MenuPanel.setLayout(gbl_MenuPanel);
		
		JMenuBar menuBar = new JMenuBar();
		GridBagConstraints gbc_menuBar = new GridBagConstraints();
		gbc_menuBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_menuBar.gridx = 0;
		gbc_menuBar.gridy = 0;
		gbc_menuBar.anchor = GridBagConstraints.NORTH;
		MenuPanel.add(menuBar, gbc_menuBar);
		
		JMenu Menu_File = new JMenu("File");
		menuBar.add(Menu_File);
		
		JMenuItem MItem_New = new JMenuItem("New project");
		Menu_File.add(MItem_New);
		
		JMenuItem mntmOpenProject = new JMenuItem("Open project...");
		Menu_File.add(mntmOpenProject);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		Menu_File.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		Menu_File.add(mntmSaveAs);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		Menu_File.add(mntmExit);
		
		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About Padz...");
		mnHelp.add(mntmAbout);
		
		//bottomPane.add(new PadView(), BorderLayout.EAST);
	}
	
	
	
	public void addToContentPane(Component comp, Object constraint){
		scrollPane.setViewportView(comp);
		
	}
	
	public void addToContentPaneEast(JPanel panel){
		contentPane.add(panel, BorderLayout.EAST);
	}
	
	public void addToBottomPane(JPanel pane){
		bottomPane.add(pane,BorderLayout.CENTER);
	}
	
	public void addToBottomPaneRight(JPanel panel){
		bottomPane.add(panel,BorderLayout.EAST);
	}
	
	public void setLookAndFeel(){
		
		
	            try {
					UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel");
	            	
	    	       // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            JFrame.setDefaultLookAndFeelDecorated(true);
		        UIManager.put(SubstanceLookAndFeel.SHOW_EXTRA_WIDGETS, Boolean.TRUE);
		           
	 
	}
}
