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
	private double adjust;
	public LoadedAudioClip(String path){		
		super.path = path;
		super.start=0;
		super.end=super.getDurationSeconds();
		super.loop=0;
		loadClip(path);
		adjust=0;
		
	
	}

	public LoadedAudioClip(Set set){

		super.path = "";
		this.setSet(set);
		isLoaded = false;
	}

	private boolean loadClip(String path){

		File audioFile = new File(path);
		isPlaying = false;
		
		

		if(audioFile.exists()){
			try {

				AudioInputStream audioStreamSeq = AudioSystem.getAudioInputStream(audioFile);

				this.audioStream = new LoadedAudioInputStream(audioStreamSeq,audioStreamSeq.getFormat(),audioStreamSeq.getFrameLength(), this);
				
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
		int frameSize=audioStream.getFormat().getFrameSize();
		if(isLoaded){
			System.out.println(audioStream.dataSize);
			if((this.start==0)){
				configNewEnd();
				audioStream.resetReadHead();
				isPlaying = true;
			}else{
				configNewStart();
				configNewEnd();
				audioStream.readHead =(int)((audioStream.dataSize/this.getDurationSeconds())*(this.adjust));
				System.out.println((int)(((audioStream.dataSize/this.getDurationSeconds())*this.adjust))%frameSize);
				adjust=0;
				isPlaying=true;
				
			}
		}
	}

	public int configNewEnd(){
		int newEnd;
		if (this.end==(this.getDurationSeconds())){
			audioStream.setDataSizeAltEnd(audioStream.getDataSize());
			newEnd=audioStream.getDataSize();
		}else {
			audioStream.setDataSizeAltEnd((int)((audioStream.dataSize/this.getDurationSeconds())*(this.end)));
			newEnd=(int)((audioStream.dataSize/this.getDurationSeconds()*(this.end)));
		}
		return newEnd;
	}
	
	public int configNewStart(){
		int frameSize=audioStream.getFormat().getFrameSize();
		System.out.println(frameSize);
		this.adjust=this.start;
		while ((int)((audioStream.dataSize/this.getDurationSeconds())*this.adjust)%frameSize!=0){
			System.out.println("Asjust init : "+this.adjust);
			this.adjust=(Math.round(this.adjust*10d)/10d)-0.05;
			System.out.println("Adjust :"+this.adjust);
		}
		return (int)((audioStream.dataSize/this.getDurationSeconds())*(this.adjust));
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
