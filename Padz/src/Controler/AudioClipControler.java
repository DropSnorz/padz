package Controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.AudioClip;
import View.AudioClipView;

public class AudioClipControler implements ActionListener{
	AudioClipView view;
	AudioClipControler(AudioClip clip){
		view=new AudioClipView(clip.getDurationSeconds());
	}
	public AudioClipView getView() {
		return view;
	}
	//TODO récupéré gain et autre valeur
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
