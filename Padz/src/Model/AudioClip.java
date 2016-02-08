package Model;

import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;

public abstract class AudioClip implements IMixable {
	//TODO methode setGain recup valeur du gain
	//TODO attribut gainEffect
	
	boolean isPlaying;
	boolean isLoaded;
	float durationSeconds;
	String path;
	
	Set set;
	
	ArrayList<IEffect> effectList = new ArrayList<IEffect>();
	
	public abstract void playFromUserInput();
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
	
	public void setSet(Set newSet){
		
		isPlaying = false;
		if(set!= null){
			this.set.removeClip(this);
		}
		this.set = newSet;
		set.addClip(this);
		
	}
	
	public void addEffect(IEffect effect){
		effectList.add(effect);
	}
	
	public void addEffect(IEffect effect, int i) throws Exception{
		
		if(i<effectList.size()){
			effectList.add(effect);
		}
		else{
			throw new Exception("Impossible d'ajouter l'effet � l'index sp馗ifi�");
		}
	}
	
	public ArrayList<IEffect> getEffectRack(){
		return effectList;
	}
	
	public String getFileName(){
		
		
		if(path.contains("/")){
			
			
			String str =  path.substring(path.lastIndexOf("/")); 
			return str.substring(1); // remove "/"

		}
		return path;
	}
			
	public float getDurationSeconds() {
		return durationSeconds;
	}



}
