package Controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Model.AudioClip;
import Model.StreamedAudioClip;
import View.PadView;

public class PadControler implements MouseListener, ActionListener {

	
	private PadView vue;
	private AudioClip clip;
	
	private PadContainerControler padContainerControler;
	
	public PadControler(AudioClip clip, PadContainerControler padContainerControler){
		
		this.padContainerControler = padContainerControler;
		this.clip = clip;
		
		vue = new PadView();
		vue.LB_FileName.setText(clip.getFileName());
		vue.addMouseListener(this);
		vue.BT_Play.addMouseListener(this);
	}
	
	public void select(){
		
		vue.drawSelectedCursor(vue.getGraphics());
		vue.setTickEnabled(true);


	}
	
	public void deselect(){
		
		vue.repaint();
		vue.setTickEnabled(false);
	}
	
	public PadView getVue(){
		return vue;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == vue){
			
			padContainerControler.setSlectedPad(this);
			
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == vue.BT_Play){
			
			//System.out.println("Click !");
			//System.out.println(System.currentTimeMillis());

			clip.play();
		
		}
		

		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == vue.BT_Play){
			
			System.out.println("CLICK Pressed/Released !");
		
		}
		
	}

}
