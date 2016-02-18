package Model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;
import javax.sound.sampled.Line.Info;

import org.tritonus.share.sampled.AudioFormatSet;
import org.tritonus.share.sampled.AudioFormats;

import Controler.AudioFeedbackDispatcher;
import resources.AppResources;

public class Set implements IMixable {


	String name;
	int color_r;
	int color_g;
	int color_b;

	TriggerType triggerType;

	List<AudioClip> audioClipList;
	Mixer globalMixer;
	ClipMixer clipMixer;
	
	GainEffect gainEffect;

	ArrayList<IEffect> effectList;

	public Set(String name, AudioFormat format){


		this.audioClipList = new ArrayList<AudioClip>();
		this.name = name;

		triggerType = TriggerType.DO_NOTHING_WHEN_CLIP_PLAYED;
		effectList = new ArrayList<IEffect>();

		clipMixer = new ClipMixer(format,audioClipList,this);

		updateClipSet();

		color_r = AppResources.Default_Set_Color.getRed();
		color_g = AppResources.Default_Set_Color.getGreen();
		color_b = AppResources.Default_Set_Color.getBlue();
		
		gainEffect = new GainEffect();
		effectList.add(gainEffect);
	}

	public Set(String name, AudioFormat format, int r, int g, int b){
		this(name,format);

		color_r = r;
		color_g = g;
		color_b = b;
	}

	public Set(String name){
		audioClipList = new ArrayList<AudioClip>();
		this.name = name;

		triggerType = TriggerType.DO_NOTHING_WHEN_CLIP_PLAYED;
		effectList = new ArrayList<IEffect>();



		color_r = AppResources.Default_Set_Color.getRed();
		color_g = AppResources.Default_Set_Color.getGreen();
		color_b = AppResources.Default_Set_Color.getBlue();


		//TODO set clipMixer and AudioFormat
		//clipMixer = new ClipMixer(null);
	}

	public Set(String name, int r, int g, int b){
		this(name);

		color_r = r;
		color_g = g;
		color_b = b;

	}

	public void updateClipSet(){

		for(AudioClip clip : audioClipList){

			clip.setSet(this);
		}
	}

	public void addClip(AudioClip clip){

		//TODO fix concurrent access : Thread safe List
		audioClipList.add(clip);

		if(clip.set != this){
			clip.setSet(this);
		}
	}

	public void removeClip(AudioClip clip){

		audioClipList.remove(clip);
		clipMixer.getAudioInputStreamList().remove(clip);

	}

	public void notifyClipPlay(AudioClip clip){

		raiseTrigger(clip);
		clipMixer.addAudioClip(clip);

	}

	private void raiseTrigger(AudioClip source){

		//Trigger dispatcher

		if(triggerType == TriggerType.PLAY_ALL_WHEN_CLIP_PLAYED){
			playAllClip();
		}
		else if(triggerType == TriggerType.STOP_ALL_WHEN_CLIP_PLAYED){
			stopAllClipExcept(source);
		}
		else{
			//Nothing to do
		}

	}

	private void playAllClip(){

		for(AudioClip clipToPlay : audioClipList){

			clipToPlay.play();

		}


	}
	private void stopAllClipExcept(AudioClip clip){

		for(AudioClip clipToStop : audioClipList){

			if(!clipToStop.equals(clip)){
				clipToStop.stop();
			}

		}
	}

	private void stopAllClip(){

		//TODO: implement
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getColor_r() {
		return color_r;
	}

	public void setColor_r(int color_r) {
		this.color_r = color_r;
	}

	public int getColor_g() {
		return color_g;
	}

	public void setColor_g(int color_g) {
		this.color_g = color_g;
	}

	public int getColor_b() {
		return color_b;
	}

	public void setColor_b(int color_b) {
		this.color_b = color_b;
	}



	public TriggerType getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(TriggerType triggerType) {
		this.triggerType = triggerType;
	}

	public List<AudioClip> getAudioClipList(){
		return audioClipList;
	}

	public AudioInputStream getAudioStream(){

		return clipMixer;
	}

	public void addEffect(IEffect effect){
		effectList.add(effect);
	}

	public void addEffect(IEffect effect, int i) throws Exception{

		if(i<effectList.size()){
			effectList.add(effect);
		}
		else{
			throw new Exception("Impossible d'ajouter l'effet è l'index spécifié");
		}
	}
	
	public void setGain(double gain){
		
		gainEffect.setGain(gain);
		
	}
	
	public void setAudioFeedbackDispatcher(AudioFeedbackDispatcher afd){
		clipMixer.setAudioFeedbackDispatcher(afd);
	}

	@Override
	public ArrayList<IEffect> getEffectRack() {
		// TODO Auto-generated method stub
		return effectList;
	}




}
