package model.event;

import controler.AudioFeedbackDispatcher;
import model.audio.AudioData;

public abstract class AudioFeedbackListener {

	AudioFeedbackDispatcher audioFeedbackDispatcher;
	public abstract void dispatch(AudioData audioData);
	
	
}
