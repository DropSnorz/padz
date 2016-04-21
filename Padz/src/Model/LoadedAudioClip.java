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

	private LoadedAudioStream audioStream;


	private LoadedAudioClip(){

		gEffect=new GainEffect();
		effectList.add(gEffect);


	}
	public LoadedAudioClip(String path){
		this();
		super.path = path;
		loadClip(path);

		super.loop=false;
		setStart(0);
		setEnd(getDurationSeconds());

	}

	public LoadedAudioClip(Set set){

		this();
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
				this.audioStream = new LoadedAudioStream(audioStreamSeq);
				durationSeconds= this.audioStream.getDataSize() / this.audioStream.getFormat().getSampleRate();
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

	public void setEnd(double end) {

		this.end = end;

		if(isLoaded){

			float sampleRate = audioStream.getFormat().getSampleRate();

			int endSample  = (int) (end * sampleRate);

			System.out.println(endSample);
			audioStream.endSample = endSample;

		}

	}

	public void setStart(double start) {
		this.start = start;

		if(isLoaded){
			float sampleRate = audioStream.getFormat().getSampleRate();
			int startSample = (int) (start * sampleRate);
			audioStream.startSample = startSample;
		}
	}


	public void setLoop(boolean loop){

		this.loop = loop;
		if(isLoaded){
			audioStream.setLoop(loop);
		}
	}

	@Override
	public LoadedAudioStream getAudioStream() {
		return audioStream;
	}


}
