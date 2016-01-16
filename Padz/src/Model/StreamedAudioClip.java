package Model;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import javax.sound.sampled.*;

public class StreamedAudioClip extends AudioClip {
	
	
	AudioInputStream audioStream;
	
	public StreamedAudioClip(String path){
		
		super.path = path;
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
			
			isLoaded = true;
            
            
            
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public AudioInputStream getAudioStream() {
		return audioStream;
	}

	public void setAudioStream(AudioInputStream audioStream) {
		this.audioStream = audioStream;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
