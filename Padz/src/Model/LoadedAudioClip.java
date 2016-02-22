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
		super.start=0;
		super.end=super.getDurationSeconds();
		super.loop=0;
		loadClip(path);
		
	
	}

	public LoadedAudioClip(Set set){

		super.path = "";
		this.set = set;
		isLoaded = false;
	}

	private boolean loadClip(String path){

		File audioFile = new File(path);
		isPlaying = false;
		
		

		if(audioFile.exists()){
			try {

				AudioInputStream audioStreamSeq = AudioSystem.getAudioInputStream(audioFile);

				this.audioStream = new LoadedAudioInputStream(audioStreamSeq,audioStreamSeq.getFormat(),audioStreamSeq.getFrameLength());
				
				int fileSize=this.audioStream.getDataSize();
				int fileFrameSize=this.audioStream.getFormat().getFrameSize();
				float fileFrameRate=this.audioStream.getFormat().getSampleRate(); 
				durationSeconds=(fileSize/(fileFrameSize*fileFrameRate));
				gEffect=new GainEffect();
				effectList.add(gEffect);
				
				
				isLoaded = true;

			} catch (UnsupportedAudioFileException e) {
				
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return false;
			}
			
			return true;
		}
		else{
			
			isLoaded = false;
			
			return false;
		}
	}

	public void playFromUserInput(){

		
		if(isLoaded){
			play();
			set.notifyClipPlay(this);
			
		}
	
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
