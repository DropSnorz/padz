package Model;

import javax.sound.sampled.AudioInputStream;

public abstract class AudioClip {
	
	
	boolean isPlaying;
	String path;
	
	
	public abstract void play();
	public abstract void stop();
	
	
	public boolean getIsPlaying(){
		
		return isPlaying;
	}
	
	public abstract AudioInputStream getAudioStream();
		


}
