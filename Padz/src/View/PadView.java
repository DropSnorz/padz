package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.io.File;
import java.util.List;
import java.util.TooManyListenersException;

import javax.swing.JPanel;

import Controler.PadContainerControler;
import Controler.PadControler;
import Model.AudioClip;
import resources.AppResources;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class PadView extends JPanel {

	/**
	 * Create the panel.
	 */

	PadControler padControler;


	public JButton BT_Play;
	public JLabel LB_FileName;
	private boolean tickEnabled = false;
	ImageIcon playingIcon;
	ImageIcon stopIcon;


	DragSource source;
	DragSourceListener dragSourceListener;


	DropTarget dropTarget;
	DropTargetListener dropTargetListener;



	Point dragPoint;
	boolean dragOver;


	private int padSize = 72;
	private int padMargin = 10;


	public PadView(PadControler padControler) {

		this.padControler = padControler;

		this.setSize(new Dimension(padSize + 2*padMargin,padSize + 2*padMargin));
		this.setPreferredSize(new Dimension(padSize + 2*padMargin, padSize + 2*padMargin));
		setLayout(null);

		JPanel PadPanel = new JPanel();
		PadPanel.setBounds(10, 10, 10, 10);
		PadPanel.setSize(new Dimension(padSize,padSize));
		PadPanel.setPreferredSize(new Dimension(padSize, padSize));
		PadPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		add(PadPanel);
		PadPanel.setLayout(null);

		LB_FileName = new JLabel("");
		LB_FileName.setBounds(9, 11, 54, 16);
		PadPanel.add(LB_FileName);
		LB_FileName.setHorizontalAlignment(SwingConstants.CENTER);
		
		playingIcon = new ImageIcon(getClass().getClassLoader().getResource("resources/img/icon-play-green.png"));
		stopIcon = new ImageIcon(getClass().getClassLoader().getResource("resources/img/icon-play.png"));
		
		BT_Play = new JButton("");
		BT_Play.setIcon(stopIcon);
		BT_Play.setBounds(10, 38, 53, 23);
		PadPanel.add(BT_Play);


		source= new DragSource();
		
		dropTarget = new DropTarget(this,DnDConstants.ACTION_COPY,null);
		dragOver = false;
		


	}
	
	public void setPlayingIcon(){
		
		BT_Play.setIcon(playingIcon);

	}
	public void setStopIcon(){
		
		BT_Play.setIcon(stopIcon);

	}

	public void drawSelectedCursor(Graphics g){



		Graphics2D graph = (Graphics2D) g;

		//Haut gauche
		graph.setColor(AppResources.SelectionCursor_Color);
		graph.drawLine(5 + padMargin,5+ padMargin,5+ padMargin,10+ padMargin);
		graph.drawLine(5+ padMargin, 5+ padMargin, 10+ padMargin , 5+ padMargin);

		//Haut droit
		graph.drawLine(padSize + padMargin - 5, 5+ padMargin, padSize + padMargin - 10, 5+ padMargin);
		graph.drawLine(padSize + padMargin - 5, 5+ padMargin, padSize+ padMargin - 5,  10+ padMargin);

		//Bas droit
		graph.drawLine(padSize+ padMargin - 5, padSize+ padMargin - 5, padSize+ padMargin - 5, padSize+ padMargin - 10 );
		graph.drawLine(padSize+ padMargin - 5, padSize+ padMargin - 5, padSize+ padMargin - 10, padSize+ padMargin - 5);


		//Bas Gauche
		graph.drawLine(padMargin + 5, padSize+ padMargin - 5, padMargin + 10, padSize+ padMargin - 5);
		graph.drawLine(padMargin + 5, padSize+ padMargin - 5, padMargin + 5, padSize+ padMargin - 10);
		//graph.draw(new Line2D.Double(20,20,20,20));


	}

	public void drawDragAndDropArea(Graphics g){

		Graphics2D graph = (Graphics2D) g;

		graph.setColor(new Color (0,255,0,30));

		graph.fill(new Rectangle(this.getWidth(),this.getHeight()));


	}

	public void setSetColor(int r, int g, int b){

		this.setBackground(new Color(r,g,b));
	}

	public void paint(Graphics g){

		super.paint(g);		

		if(tickEnabled){
			drawSelectedCursor(g);
		}

		if(dragOver && dragPoint != null){

			drawDragAndDropArea(g);
		}
	}

	public void setTickEnabled(boolean value){
		tickEnabled = value;
	}

	public void setDragGestureListener(DragGestureListener dgl){

		source.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, dgl);


	}
	
	public void  setDragSourceListener(DragSourceListener dragSourceListener){
		
		this.dragSourceListener = dragSourceListener;
		
	}
	
	public void setDropTargetListener(DropTargetListener dropTargetListener){
		
		this.dropTargetListener = dropTargetListener;
	}


	// DRAG AND DROP


	@Override
	public void addNotify(){
		super.addNotify();

		try {
			this.getDropTarget().addDropTargetListener(dropTargetListener);
			this.getDragSource().addDragSourceListener(dragSourceListener);
		} catch (TooManyListenersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void removeNotify(){
		super.removeNotify();

		this.getDropTarget().removeDropTargetListener(dropTargetListener);
		this.getDragSource().removeDragSourceListener(dragSourceListener);

	}

	public DropTarget getDropTarget(){

		return dropTarget;


	}

	public DragSource getDragSource(){

		return source;
	}

	public void updateDragAndDropFeedback(boolean isDragOver, Point dragPoint){

		this.dragOver = isDragOver;
		this.dragPoint = dragPoint;

		repaint();


	}


}
