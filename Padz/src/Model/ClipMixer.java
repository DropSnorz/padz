package Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import Controler.AudioFeedbackDispatcher;

public class ClipMixer extends StreamMixer {

	List<AudioClip> audioClipList;
	private Set set;
	private AudioFeedbackDispatcher audioFeedbackDispatcher;

	public ClipMixer(AudioFormat audioFormat,Set set){
		super(audioFormat);
		
		this.set = set;
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

	public AudioData read(int length){
		
		AudioData data = super.read(length);
		
		updateStreams();
		if(audioFeedbackDispatcher != null){
			audioFeedbackDispatcher.dispatch(data, set);
		}
		return data;
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

		Iterator<AudioClip>	audioClipIterator = audioClipList.iterator();

		while (audioClipIterator.hasNext())
		{

			AudioClip clip = audioClipIterator.next();
			if(clip.isLoaded){
				if(clip.getAudioStream().available() <= 0 && clip.getIsPlaying()){

					clip.setPlaying(false); 
					audioFeedbackDispatcher.notifyClipModelChanges(clip);						
				}
				if(clip.getIsPlaying() == false){
					mixableEntityList.remove(clip);
				}
				else {
					if(!mixableEntityList.contains(clip)){						
						mixableEntityList.add(clip);
					}
				}
			}
		}

	}
}
