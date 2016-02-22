package Model;

import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;

public abstract class AudioClip implements IMixable {
	
	boolean isPlaying;
	boolean isLoaded;
	float durationSeconds;
	String path;
	double gainValue=1;
	protected double start;
	protected double end;
	protected int loop;
	GainEffect gEffect;
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
	public String getPath(){
		return path;
	}
	public String getFileName(){
		
		
		if(path.contains("/") && isLoaded){
			
			
			String str =  path.substring(path.lastIndexOf("/")); 
			return str.substring(1); // remove "/"

		}
		else if(path.contains("\\") && isLoaded){
			
			
			String str =  path.substring(path.lastIndexOf("\\")); 
			return str.substring(1); // remove "/"

		}
		else{
			
			return "";
		}
		
	}
			
	public float getDurationSeconds() {
		return durationSeconds;
	}

	public void setGain(double gainValue){
		this.gainValue=gainValue;
		gEffect.setGain(gainValue);
	}
	public int getGain(){
		return (int)(gainValue*100);
	}
	public double getStart() {
		return start;
	}
	public void setStart(double start) {
		this.start = start;
	}
	public double getEnd() {
		return end;
	}
	public void setEnd(double end) {
		this.end = end;
	}
	public int getLoop() {
		return loop;
	}
	public void setLoop(int loop) {
		this.loop = loop;
	}

}
