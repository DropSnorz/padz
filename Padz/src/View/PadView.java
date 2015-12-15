package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

import Controler.PadContainerControler;
import Controler.PadControler;
import Model.StreamedAudioClip;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PadView extends JPanel {

	/**
	 * Create the panel.
	 */
	//temp
	
	private JButton BT_Play;
	private Graphics2D graph;
	
	private int padSize = 72;
	
	
	public PadView() {
		
		this.setSize(new Dimension(padSize,padSize));
		this.setPreferredSize(new Dimension(padSize, padSize));
		setLayout(null);

		this.setBorder(BorderFactory.createLineBorder(Color.black));
		BT_Play = new JButton("Play");
		BT_Play.setBounds(10, 37, 53, 23);
		add(BT_Play);
		

	}
	
	public void drawSelectedCursor(Graphics g){
		
	    Graphics2D graph = (Graphics2D) g;
		
		graph.drawLine(5,5,5,10);
		graph.drawLine(5, 5, 10 , 5);
		
		graph.drawLine(padSize - 5, 5, padSize - 10, 5);
		graph.drawLine(padSize - 5, 5, padSize - 5,  10);

		
		graph.drawLine(padSize - 5, padSize - 5, padSize - 5, padSize - 10 );
		graph.drawLine(padSize - 5, padSize - 5, padSize - 10, padSize - 5);
		
		graph.drawLine(5, padSize - 5, 10, padSize - 5);
		graph.drawLine(5, padSize - 5, 5, padSize - 10);
		//graph.draw(new Line2D.Double(20,20,20,20));
		
		
		
	}
	
	public void setActionListener(ActionListener listener){
		
		BT_Play.addActionListener(listener);
	}
	
	public void paint(Graphics g){
		super.paint(g);
		drawSelectedCursor(g);
		
	}

}
