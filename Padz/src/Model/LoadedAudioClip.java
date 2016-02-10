package Model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import View.AudioClipView;
public class LoadedAudioClip extends AudioClip {

	private LoadedAudioInputStream audioStream;
	
	public LoadedAudioClip(String path){		
		super.path = path;
		loadClip(path);
		int fileSize=audioStream.getDataSize();
		int fileFrameSize=audioStream.getFormat().getFrameSize();
		float fileFrameRate=audioStream.getFormat().getSampleRate(); 
		durationSeconds=(fileSize/(fileFrameSize*fileFrameRate));
		System.out.println("Duration : "+durationSeconds);
		gEffect=new GainEffect();
		effectList.add(gEffect);
		//audioClipView.
	}
	
	public LoadedAudioClip(Set set){
		
		super.path = "";
		this.set = set;
		isLoaded = false;
	}
	
	private void loadClip(String path){

		File audioFile = new File(path);
		isPlaying = false;
		try {

			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

			this.audioStream = new LoadedAudioInputStream(audioStream,audioStream.getFormat(),audioStream.getFrameLength());
			isLoaded = true;

		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playFromUserInput(){
		
		play();
		set.notifyClipPlay(this);

	}
	
	public void play(){
		
		if(isLoaded){
			audioStream.resetReadHead();
			isPlaying = true;
			
			
		}
	
	}

	@Override
	public void stop() {
		isPlaying = false;
		
	}

	@Override
	public AudioInputStream getAudioStream() {
		return audioStream;
	}



}
