package Model;

import javax.sound.sampled.AudioInputStream;

public abstract class AudioClip implements IMixable {
	
	
	boolean isPlaying;
	boolean isLoaded;
	String path;
	
	Set set;
	
	public abstract void play();
	public abstract void stop();
	public abstract AudioInputStream getAudioStream();

	
	public boolean getIsPlaying(){
		
		return isPlaying;
	}
	
	public boolean getIsLoaded(){
		return isLoaded;
	}
	
	public Set getSet(){
		
		return set;
	}
	
	public void setSet(Set set){
		
		this.set = set;
		set.addClip(this);
	}
	
	public String getFileName(){
		
		
		if(path.contains("/")){
			
			
			String str =  path.substring(path.lastIndexOf("/")); 
			return str.substring(1); // remove "/"

		}
		return path;
	}
			


}
