package controler;

import java.io.IOException;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import model.Set;
import model.SetMixer;

public class AudioProcess extends Thread {

	private AudioFormat format;
	private SourceDataLine inputMixer;
	private AudioFeedbackDispatcher audioFeedbackDispatcher;
	private boolean stopped = false;
	
	private SetMixer setMixer;
	int bufferSize = 128;

	public AudioProcess(SourceDataLine inputMixer,AudioFormat format, AudioFeedbackDispatcher afd,List<Set> setList){

		super("AudioProcess");
		
		this.inputMixer = inputMixer;
		this.format = format;
		this.audioFeedbackDispatcher = afd;
		this.setMixer = new SetMixer(format);
		setMixer.setAudioFeedbackDispatcher(audioFeedbackDispatcher);
		for(Set set: setList){
			
			setMixer.addSet(set);
		}
	}
	
	public void setGain(float gain){
		
		FloatControl volume = (FloatControl) inputMixer.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(gain);

		
	}
	public void run(){

		
		setPriority(Thread.MAX_PRIORITY);
		try {
			inputMixer.open(format);
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
		inputMixer.start();

		int numBytesRead = 0;

		byte myData[] = new byte[bufferSize];

		while (!stopped){
		
			try {
				numBytesRead = setMixer.read(myData, 0, bufferSize);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (numBytesRead == -1) break;
			
			inputMixer.write(myData, 0, numBytesRead);
			//audioFeedbackDispatcher.DispatchMasterStereoAudioSource(myData, numBytesRead);


		}

	}
}
