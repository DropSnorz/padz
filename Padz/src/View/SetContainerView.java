package View;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import Controler.SetControler;
import javax.swing.ImageIcon;

public class SetContainerView extends JPanel {

	/**
	 * Create the panel.
	 */
	JAccordion accordion;
	private JButton BT_NewSet;
	private JButton BT_RemoveSet;
	private JScrollPane scrollPane;
	
	public SetContainerView() {
		
		setLayout(new BorderLayout());
		
		accordion = new JAccordion();
		
		
		//accordion.addBar("(default)",accordion.getDummyPanel("Content..."));
		//accordion.addBar("Set 1", accordion.getDummyPanel("Content..."));
		//accordion.addBar("Set 2", accordion.getDummyPanel("Content..."));
		
		JPanel buttonsPanel = new JPanel();
		this.add(buttonsPanel, BorderLayout.NORTH);
		buttonsPanel.setLayout(new FlowLayout());
		
		BT_NewSet = new JButton("New Set");
		BT_NewSet.setIcon(new ImageIcon(SetContainerView.class.getResource("/resources/img/icon-add.png")));
		
		buttonsPanel.add(BT_NewSet);
		
		BT_RemoveSet = new JButton("Remove Set");
		BT_RemoveSet.setIcon(new ImageIcon(SetContainerView.class.getResource("/resources/img/icon-remove.png")));
		buttonsPanel.add(BT_RemoveSet);
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel rigidPanel = new JPanel();
		rigidPanel.setPreferredSize(new Dimension(5,0));
		this.add(rigidPanel,BorderLayout.WEST);
		
		scrollPane.setViewportView(accordion);

	}
	
	public void addAccordionBar(String name, JPanel panel, SetControler setControler){
		
		accordion.addBar(name, panel,setControler);
	}
	
	public void updateAccordionView(){
		accordion.updateView();
	}

}
