package Model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;
import javax.sound.sampled.Line.Info;

import org.tritonus.share.sampled.AudioFormatSet;
import org.tritonus.share.sampled.AudioFormats;

import resources.AppResources;

public class Set {
	
	
	String name;
	int color_r;
	int color_g;
	int color_b;
	List<AudioClip> audioClipList;
	Mixer globalMixer;
	ClipMixer clipMixer;

	
	
	public Set(String name, AudioFormat format){
		
		
		this.audioClipList = new ArrayList<AudioClip>();
		this.name = name;
		clipMixer = new ClipMixer(format,audioClipList);
		
		updateClipSet();
		
		color_r = AppResources.UI_Background_Color.getRed();
		color_g = AppResources.UI_Background_Color.getGreen();
		color_b = AppResources.UI_Background_Color.getBlue();
	}
	
	public Set(String name, AudioFormat format, int r, int g, int b){
		this(name,format);
		
		color_r = r;
		color_g = g;
		color_b = b;
	}
	
	public Set(String name){
		audioClipList = new ArrayList<AudioClip>();
		this.name = name;
		
		color_r = AppResources.UI_Background_Color.getRed();
		color_g = AppResources.UI_Background_Color.getGreen();
		color_b = AppResources.UI_Background_Color.getBlue();
	
		
		//TODO set clipMixer and AudioFormat
		//clipMixer = new ClipMixer(null);
	}
	
	public Set(String name, int r, int g, int b){
		this(name);
		
		color_r = r;
		color_g = g;
		color_b = b;
		
	}
	
	public void updateClipSet(){
		
		for(AudioClip clip : audioClipList){
			
			clip.setSet(this);
		}
	}
	
	public void addClip(AudioClip clip){
		
		audioClipList.add(clip);
		
		if(clip.set != this){
			clip.setSet(this);
		}
	}
	
	public void notifyClipPlay(AudioClip clip){
		
		clipMixer.addAudioClip(clip);
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getColor_r() {
		return color_r;
	}

	public void setColor_r(int color_r) {
		this.color_r = color_r;
	}

	public int getColor_g() {
		return color_g;
	}

	public void setColor_g(int color_g) {
		this.color_g = color_g;
	}

	public int getColor_b() {
		return color_b;
	}

	public void setColor_b(int color_b) {
		this.color_b = color_b;
	}

	public List<AudioClip> getAudioClipList(){
		return audioClipList;
	}
	
	public AudioInputStream getStream(){
		
		return clipMixer;
	}

	
	
	
}
