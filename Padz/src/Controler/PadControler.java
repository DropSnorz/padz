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
	
	public PadControler(AudioClip clip){
		
		this.clip = clip;
		vue = new PadView();
		
		
		vue.addMouseListener(this);
		vue.BT_Play.addMouseListener(this);
	}
	
	public PadView getVue(){
		return vue;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
				
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
			
			System.out.println("CLICK !");
			clip.play();
		
		}
		
	}

}
