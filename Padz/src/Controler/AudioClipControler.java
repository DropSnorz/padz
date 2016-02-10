package Controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Model.AudioClip;
import View.AudioClipView;

public class AudioClipControler implements ChangeListener{
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
	//TODO récupéré gain et autre valeur
	//double gain=1;
	//@Override
	/*public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		gain=audioView.gainValue;		
	}*/

	@Override
	 public void stateChanged(ChangeEvent e) {
       JSlider source = (JSlider)e.getSource();
       if (source.getValueIsAdjusting()) {
           audioView.gainValue = source.getValue()*0.01;
       	System.out.println(audioView.gainValue);
       	clip.setGain(audioView.gainValue);
       }
       
   }
	
}
