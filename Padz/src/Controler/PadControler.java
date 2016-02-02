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
	
	public boolean handleSetChanges = false;
	
	public PadControler(AudioClip clip, PadContainerControler padContainerControler){
		
		this.padContainerControler = padContainerControler;
		this.clip = clip;
		
		vue = new PadView();
		vue.LB_FileName.setText(clip.getFileName());
		vue.addMouseListener(this);
		vue.BT_Play.addMouseListener(this);
		
		updateUI();
		}
	
	public void select(){
		
		vue.drawSelectedCursor(vue.getGraphics());
		vue.setTickEnabled(true);


	}
	
	public void deselect(){
		
		vue.repaint();
		vue.setTickEnabled(false);
	}
	
	public void updateUI(){
		
		vue.setSetColor(clip.getSet().getColor_r(), clip.getSet().getColor_g(), clip.getSet().getColor_b());

	}
	public void setHandleSetChanges(boolean value){
		handleSetChanges = value;
	}
	
	public PadView getVue(){
		return vue;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == vue){
			
			if(handleSetChanges){
				
				System.out.println("Updating Pad...");
				clip.setSet(padContainerControler.getSelectedSet());
				
				updateUI();
			}
			else{
				
				padContainerControler.setSlectedPad(this);

			}
			
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
			
			
			System.out.println("  Fire : " + e.getWhen());
			System.out.println("Handled: " + System.currentTimeMillis());
		
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

	public AudioClip getClip() {
		return clip;
	}
	
}
