package View;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SamplerView extends JFrame {

	private JPanel contentPane;
	private JPanel audioClipViewPane;
	private JScrollPane scrollPane;

	/**
	 * Create the frame.
	 */
	private JPanel bottomPane;
	public SamplerView() {
		
		setLookAndFeel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1043, 628);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
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
	        UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	       // handle exception
	    }
	    catch (ClassNotFoundException e) {
	       // handle exception
	    }
	    catch (InstantiationException e) {
	       // handle exception
	    }
	    catch (IllegalAccessException e) {
	       // handle exception
	    }
	}
}
