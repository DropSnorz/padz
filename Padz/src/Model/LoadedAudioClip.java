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


	private LoadedAudioClip(){

		gEffect=new GainEffect();
		effectList.add(gEffect);


	}
	public LoadedAudioClip(String path){
		this();
		super.path = path;
		loadClip(path);

		super.loop=0;
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

				this.audioStream = new LoadedAudioInputStream(audioStreamSeq,audioStreamSeq.getFormat(),audioStreamSeq.getFrameLength());

				int fileSize=this.audioStream.getDataSize();
				int fileFrameSize=this.audioStream.getFormat().getFrameSize();
				float fileFrameRate=this.audioStream.getFormat().getSampleRate(); 
				durationSeconds=(fileSize/(fileFrameSize*fileFrameRate));


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

			float frameRate = audioStream.getFormat().getFrameRate();
			int frameSize = audioStream.getFormat().getFrameSize();

			int endSample  = (int) (end * frameRate * frameSize);

			System.out.println(endSample);
			if(endSample % frameSize != 0 ){

				//TODO : correct error on sample offset
				System.out.println("Error");
			}
			else{
				audioStream.endSample = endSample;
			}
		}

	}

	public void setStart(double start) {
		this.start = start;

		if(isLoaded){
			float frameRate = audioStream.getFormat().getFrameRate();
			int frameSize = audioStream.getFormat().getFrameSize();


			int startSample = (int) (start * frameRate * frameSize);

			if(startSample % frameSize != 0 ){

				//TODO : correct error on sample offset
			}
			else{
				audioStream.startSample = startSample;
			}
		}

	}

	public void setLoop(int loop){

		this.loop = loop;

		if(isLoaded){
			if(loop == 1){
				audioStream.setLoop(true);
			}
			else{
				audioStream.setLoop(false);
			}
		}
	}



	@Override
	public AudioInputStream getAudioStream() {
		return audioStream;
	}


}
