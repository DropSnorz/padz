package Model;

import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;

public class SetMixer extends StreamMixer {
		
	
	public SetMixer(AudioFormat format){
		super(format);
		
	}
	
	public void addSet(Set set){
		
		super.mixableEntityList.add(set);
	}
	
	public void removeSet(Set set){
		
		super.mixableEntityList.remove(set);
	}

}
