package Controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Model.AudioClip;
import View.AudioClipView;

public class AudioClipControler implements ChangeListener, ActionListener{
	AudioClipView audioView;
	AudioClip clip;
	AudioClipControler(AudioClip clip){
		audioView=new AudioClipView(clip.getDurationSeconds());
		audioView.gainGauge.addChangeListener(this);
		this.clip=clip;
		clip.setGain(1);
	}
	
	public AudioClipView getView() {
		return audioView;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}
	

	@Override
	//TODO refacto : GainValue uniquement sur le controleur
	 public void stateChanged(ChangeEvent e) {
       JSlider source = (JSlider)e.getSource();
       if (source.getValueIsAdjusting()) {
           double gain =  source.getValue()*0.01;
       	clip.setGain(gain);
       }
       
   }
	
}
