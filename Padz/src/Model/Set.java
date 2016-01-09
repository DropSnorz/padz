package Model;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;
import javax.sound.sampled.Line.Info;

public class Set {
	
	
	String name;
	List<AudioClip> audioClipList;
	Mixer globalMixer;
	ClipMixer clipMixer;

	
	
	public Set(List<AudioClip> audioClipList){
/*		

		this.globalMixer = globalMixer;
		List<AudioInputStream> streamList = new ArrayList<AudioInputStream>();
		
		streamList.add(clip.getAudioStream());
		streamList.add(clip2.getAudioStream());
		*/
		
		this.audioClipList = audioClipList;
		
		clipMixer = new ClipMixer(audioClipList.get(0).getAudioStream().getFormat(),audioClipList);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<AudioClip> getAudioClipList(){
		return audioClipList;
	}
	
	public AudioInputStream getStream(){
		
		return clipMixer;
	}

	
	
	
}
