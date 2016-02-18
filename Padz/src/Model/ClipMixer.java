package Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import Controler.AudioFeedbackDispatcher;

public class ClipMixer extends StreamMixer {

	//StreamMixer streamMixer;
	List<AudioClip> audioClipList;
	private Set set;
	private AudioFeedbackDispatcher audioFeedbackDispatcher;


	public ClipMixer(AudioFormat audioFormat,Set set){
		super(audioFormat);
		
		this.set = set;
		//ClipMixer(audioFormat, new ArrayList<LoadedAudioClip>());

	}


	public ClipMixer(AudioFormat audioFormat, List<AudioClip> audioClipList,Set set) {
		super(audioFormat);
		//ArrayList<AudioInputStream> audioInputStreamList = new ArrayList<AudioInputStream>();
		this.set = set;
		this.audioClipList = audioClipList;
		for (AudioClip clip : audioClipList){

			if(clip.isLoaded){
				mixableEntityList.add(clip);
			}
		}

	}


	public int read(byte[] buffer, int nOffset, int length){

		updateStreams();

		int val = 0;
		try {
			val =  super.read(buffer, nOffset, length);
			
			if(audioFeedbackDispatcher != null){
				audioFeedbackDispatcher.DispatchSetStereoAudioSource(buffer, val, set);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return val;

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
	
	public void setAudioFeedbackDispatcher(AudioFeedbackDispatcher afd){
		this.audioFeedbackDispatcher = afd;
		
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
