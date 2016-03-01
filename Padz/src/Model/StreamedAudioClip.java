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
		super.start=0;
		super.end=super.getDurationSeconds();
		super.loop=0;
		loadClip(path);
	}

	public void playFromUserInput(){
		
		play();
		set.notifyClipPlay(this);
		isPlaying = false;
		while(this.loop==1){
			if(isPlaying==false){
					play();
					set.notifyClipPlay(this);
					isPlaying = false;
			}
		}
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

		if(audioFile.exists()){


			try {
				InputStream audioSrc = getClass().getResourceAsStream(path);

				audioStream = AudioSystem.getAudioInputStream(audioFile);


				InputStream bufferedIn = new BufferedInputStream(audioStream);
				audioStream = new AudioInputStream(bufferedIn,audioStream.getFormat(),audioStream.getFrameLength());
				DataLine.Info clipInfo = new DataLine.Info(Clip.class,audioStream.getFormat());         

				//TODO remove mark by using LoadedAudioClip
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
		else{
			
			isLoaded = false;
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
