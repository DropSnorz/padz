package model.event;

import controler.AudioFeedbackDispatcher;
import model.audio.AudioData;

public class MasterFeedbackListener extends AudioFeedbackListener {

	public MasterFeedbackListener(AudioFeedbackDispatcher audioFeedbackDispatcher){
		this.audioFeedbackDispatcher = audioFeedbackDispatcher;
		
	}
	
	public void dispatch(AudioData audioData){
		
		audioFeedbackDispatcher.DispatchMasterStereoAudioSource(audioData);
	}
}
