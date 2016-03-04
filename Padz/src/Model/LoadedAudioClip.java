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
		loadClip(path);

		
		super.loop=0;
		setStart(0);
		setEnd(getDurationSeconds());



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

	public void setEnd(double end) {
		this.end = end;


		float frameRate = audioStream.getFormat().getFrameRate();
		int frameSize = audioStream.getFormat().getFrameSize();

		int endSample  = (int) (end * frameRate * frameSize);

		System.out.println(endSample);
		if(endSample % frameSize != 0 ){

			//TODO : auto-correct
			System.out.println("Error");
		}
		else{
			audioStream.endSample = endSample;
		}





	}

	public void setStart(double start) {
		this.start = start;

		float frameRate = audioStream.getFormat().getFrameRate();
		int frameSize = audioStream.getFormat().getFrameSize();


		int startSample = (int) (start * frameRate * frameSize);

		if(startSample % frameSize != 0 ){

			//TODO : auto-correct
		}
		else{
			audioStream.startSample = startSample;
		}

	}
	
	public void setLoop(int loop){
		
		this.loop = loop;
		
		if(loop == 1){
			audioStream.setLoop(true);
		}
		else{
			audioStream.setLoop(false);
		}
	}



	@Override
	public AudioInputStream getAudioStream() {
		return audioStream;
	}

	
}
