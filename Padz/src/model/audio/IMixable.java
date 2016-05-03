package model.audio;

import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;

public interface IMixable {

	public AudioStream getAudioStream();
	public void addEffect(IEffect effect);
	public void addEffect(IEffect effect,int i) throws Exception;
	public ArrayList<IEffect> getEffectRack();
	
}
