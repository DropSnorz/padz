package Model;

import Controler.AudioFeedbackDispatcher;

public class MasterFeedbackListener extends AudioFeedbackListener {

	MasterFeedbackListener(AudioFeedbackDispatcher audioFeedbackDispatcher){
		this.audioFeedbackDispatcher = audioFeedbackDispatcher;
		
	}
	
	public void dispatch(AudioData audioData){
		
		audioFeedbackDispatcher.DispatchMasterStereoAudioSource(audioData);
	}
}
