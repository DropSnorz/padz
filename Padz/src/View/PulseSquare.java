package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class PulseSquare extends JPanel {

	/**
	 * Create the panel.
	 */
	
	private Color rootColor;
	
	public PulseSquare(int x, int y, Color color) {
		
		this.setPreferredSize(new Dimension(x,y));
		this.rootColor = color;

	}
	
	public void setRootColor(Color color){
		
		rootColor = color; 
		repaint();
	}
	
	public void paint(Graphics g){
		
		super.paint(g);
		
		int offset = 5;
		int size = 5;
		Graphics2D g2 = (Graphics2D)g;
		GradientPaint grad = new GradientPaint(offset,0,new Color(0,0,0,0),5,offset,rootColor);
		g2.setPaint(grad);
		g2.fillRect(offset, 0, getWidth() - 2*offset, size);
		
		
		grad = new GradientPaint(0,offset,new Color(0,0,0,0),5,5, rootColor);
		g2.setPaint(grad);
		g2.fillRect(0, offset, size,  getHeight() - 2*offset);
		
		
		grad = new GradientPaint(offset,getHeight() - offset,rootColor,offset,getHeight(),new Color(0,0,0,0));
		g2.setPaint(grad);
		g2.fillRect(offset, getHeight() - offset, getWidth() - 2*offset, size);
		
		
		grad = new GradientPaint(getWidth() - offset, offset,rootColor,getWidth(),5, new Color(0,0,0,0));
		g2.setPaint(grad);
		g2.fillRect(getWidth() - offset, offset, size,  getHeight() - 2*offset);
		
		
		
		
		
		
		
	}

}
