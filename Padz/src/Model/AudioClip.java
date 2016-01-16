package Model;

import javax.sound.sampled.AudioInputStream;

public abstract class AudioClip {
	
	
	boolean isPlaying;
	boolean isLoaded;
	String path;
	
	
	public abstract void play();
	public abstract void stop();
	
	
	public boolean getIsPlaying(){
		
		return isPlaying;
	}
	
	public boolean getIsLoaded(){
		return isLoaded;
	}
	
	public String getFileName(){
		
		
		if(path.contains("/")){
			
			
			String str =  path.substring(path.lastIndexOf("/")); 
			return str.substring(1); // remove "/"

		}
		return path;
	}
	
	public abstract AudioInputStream getAudioStream();
		


}
