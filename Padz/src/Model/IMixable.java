package Model;

import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;

public interface IMixable {

	
	public AudioInputStream getAudioStream();
	public void addEffect(IEffect effect);
	public void addEffect(IEffect effect,int i) throws Exception;
	public ArrayList<IEffect> getEffectRack();
	//TODO implement effect rack for parameter/effect control;
	
}
