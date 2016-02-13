package Controler;

import java.io.IOException;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import Model.Set;
import Model.SetMixer;

public class AudioProcess extends Thread {

	private AudioFormat format;
	private SourceDataLine inputMixer;
	private AudioFeedbackDispatcher audioFeedbackDispatcher;
	private boolean stopped = false;
	
	private SetMixer setMixer;
	
	int bufferSize = 128;

	public Set set1;

	public AudioProcess(SourceDataLine inputMixer,AudioFormat format, AudioFeedbackDispatcher afd,List<Set> setList){

		super("AudioProcess");
		
		this.inputMixer = inputMixer;
		this.format = format;
		this.audioFeedbackDispatcher = afd;
		
		
		this.setMixer = new SetMixer(format);
		
		for(Set set: setList){
			
			setMixer.addSet(set);
		}
	}
	public void run(){

		
		setPriority(Thread.MAX_PRIORITY);
		try {
			inputMixer.open(format);
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		inputMixer.start();

		int numBytesRead = 0;

		byte myData[] = new byte[bufferSize];


		while (!stopped){
		
			try {
				numBytesRead = setMixer.read(myData, 0, bufferSize);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//numBytesRead = clip1.getAudioStream().read(myData, 0, 100);
			if (numBytesRead == -1) break;
			//total += numBytesRead; 
			//System.out.println(System.currentTimeMillis());
			inputMixer.write(myData, 0, numBytesRead);
			audioFeedbackDispatcher.DispatchMasterStereoAudioSource(myData, numBytesRead);


		}

	}
}
