package Model;

import Controler.AudioFeedbackDispatcher;

public abstract class AudioFeedbackListener {

	AudioFeedbackDispatcher audioFeedbackDispatcher;
	abstract void dispatch(AudioData audioData);
	
	
}
