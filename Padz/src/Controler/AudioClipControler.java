package Controler;

import Model.AudioClip;
import View.AudioClipView;

public class AudioClipControler {
	AudioClipView view;
	AudioClipControler(AudioClip clip){
		view=new AudioClipView(clip.getDurationSeconds());
	}
	public AudioClipView getView() {
		return view;
	}
	
}
