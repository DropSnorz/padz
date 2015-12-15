package Controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Model.StreamedAudioClip;
import View.PadView;

public class PadControler implements MouseListener, ActionListener {

	
	private PadView vue;
	private StreamedAudioClip clip;
	
	public PadControler(StreamedAudioClip clip){
		
		this.clip = clip;
		vue = new PadView();
		vue.setActionListener(this);
		vue.addMouseListener(this);
	}
	
	public PadView getVue(){
		return vue;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		System.out.println("CLICK EVENT");
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		clip.play();
	}

}
