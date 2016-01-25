package Controler;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import Model.Set;
import Model.SetMixer;

public class AudioProcess extends Thread {

	private AudioFormat format;
	private SourceDataLine inputMixer;
	private boolean stopped = false;
	
	private SetMixer setMixer;
	
	int bufferSize = 128;

	public Set set1;

	public AudioProcess(SourceDataLine inputMixer,AudioFormat format,Set set1){

		super("AudioProcess");
		
		this.inputMixer = inputMixer;
		this.format = format;
		this.set1 = set1;
		
		
		this.setMixer = new SetMixer(format);

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
				numBytesRead = set1.getAudioStream().read(myData, 0, bufferSize);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//numBytesRead = clip1.getAudioStream().read(myData, 0, 100);
			if (numBytesRead == -1) break;
			//total += numBytesRead; 
			//System.out.println(System.currentTimeMillis());
			inputMixer.write(myData, 0, numBytesRead);

		}

	}
}
