package Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

public class ClipMixer extends StreamMixer {

	//StreamMixer streamMixer;
	List<AudioClip> audioClipList;


	public ClipMixer(AudioFormat audioFormat){
		super(audioFormat);
		//ClipMixer(audioFormat, new ArrayList<LoadedAudioClip>());

	}


	public ClipMixer(AudioFormat audioFormat, List<AudioClip> audioClipList) {
		super(audioFormat);
		//ArrayList<AudioInputStream> audioInputStreamList = new ArrayList<AudioInputStream>();

		this.audioClipList = audioClipList;
		for (AudioClip clip : audioClipList){

			if(clip.isLoaded){
				mixableEntityList.add(clip);
			}
		}

	}


	public int read(byte[] buffer, int nOffset, int length){

		updateStreams();

		try {
			return super.read(buffer, nOffset, length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;

	}
	
	public void addAudioClip(AudioClip clip){
		
		
		if(!mixableEntityList.contains(clip)){
			
			
			mixableEntityList.add(clip);
		}
		
	}
	
	public void removeAudioClip(AudioClip clip){
		
		audioClipList.remove(clip);
		mixableEntityList.remove(clip);
	}

	public void updateStreams(){
		
		for(AudioClip clip : audioClipList){

			if(clip.isLoaded){
				try {
					if(clip.getAudioStream().available() <= 0){

						//Si le clip est terminé (fin du stream)
						clip.isPlaying = false;

					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if(clip.getIsPlaying() == false){


					mixableEntityList.remove(clip);

				}
				else {

					if(!mixableEntityList.contains(clip)){						

						mixableEntityList.add(clip);
						
						System.out.println("ADD ---- " + System.currentTimeMillis() );

					}
				}


			}
		}

	}




}
