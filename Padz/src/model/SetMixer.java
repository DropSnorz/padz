package model;

import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;

import controler.AudioFeedbackDispatcher;
import model.audio.StreamMixer;
import model.event.AudioFeedbackListener;
import model.event.MasterFeedbackListener;

public class SetMixer extends StreamMixer {
		
	public SetMixer(AudioFormat format){
		super(format);
		
		
	}
	
	
	public void addSet(Set set){
		
		super.mixableEntityList.add(set);
		
	}
	
	public void setAudioFeedbackDispatcher(AudioFeedbackDispatcher afd){
		
		AudioFeedbackListener listener = new MasterFeedbackListener(afd);
		setAudioFeedbackListener(listener);
	}
	
	public void removeSet(Set set){
		
		super.mixableEntityList.remove(set);
	}
	

}
