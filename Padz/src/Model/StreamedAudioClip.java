package Model;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import javax.sound.sampled.*;

public class StreamedAudioClip {

	AudioInputStream audioStream;
	boolean isPlaying;
	String path;
	
	
	
	public StreamedAudioClip(String path){
		
		loadClip(path);
		
	}
	
	public void play(){
		isPlaying = true;
		
		try {
			audioStream.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void loadClip(String path){
		
		File audioFile = new File(path);
		isPlaying = false;
		try {
			InputStream audioSrc = getClass().getResourceAsStream(path);
			//add buffer for mark/reset support
			
			audioStream = AudioSystem.getAudioInputStream(audioFile);

			
			InputStream bufferedIn = new BufferedInputStream(audioStream);
			
			audioStream = new AudioInputStream(bufferedIn,audioStream.getFormat(),audioStream.getFrameLength());
			DataLine.Info clipInfo = new DataLine.Info(Clip.class,audioStream.getFormat());         
			
			audioStream.mark(10000000);         
            
            
            
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean getIsPlaying(){
		return isPlaying;
	}

	public AudioInputStream getAudioStream() {
		return audioStream;
	}

	public void setAudioStream(AudioInputStream audioStream) {
		this.audioStream = audioStream;
	}
	
	
	
}
