package Model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class LoadedAudioClip extends AudioClip  {

	private LoadedAudioInputStream audioStream;

	public LoadedAudioClip(String path){
		
		super.path = path;
		loadClip(path);

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
			InputStream audioSrc = getClass().getResourceAsStream(path);
			//add buffer for mark/reset support

			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

			InputStream bufferedIn = new BufferedInputStream(audioStream);

			this.audioStream = new LoadedAudioInputStream(bufferedIn,audioStream.getFormat(),audioStream.getFrameLength());
			isLoaded = true;

		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void play(){
		
		if(isLoaded){
			audioStream.resetReadHead();
			isPlaying = true;
			
			set.notifyClipPlay(this);
			
		}
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AudioInputStream getAudioStream() {
		return audioStream;
	}



}
