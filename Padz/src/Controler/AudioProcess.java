package Controler;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import Model.Set;

public class AudioProcess extends Thread {

	private AudioFormat format;
	private SourceDataLine inputMixer;
	private boolean stopped = false;

	//temp

	int bufferSize = 128;

	public Set set1;

	public AudioProcess(SourceDataLine inputMixer,AudioFormat format,Set set1){

		super("AudioProcess");
		
		this.inputMixer = inputMixer;
		this.format = format;
		this.set1 = set1;

	}


	public void run(){

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
			
			yield();
		
			try {
				numBytesRead = set1.getStream().read(myData, 0, bufferSize);
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
