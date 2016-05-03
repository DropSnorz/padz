package model.effect;

import model.audio.AudioData;
import model.audio.IEffect;

public class GainEffect implements IEffect {
	double gainValue=1;
	
	@Override
	public void ProcessAudioDataReplacing(AudioData inputData, int nSamples) {

		
		for (int s=0; s<nSamples;s++){
			for(int chan = 0; chan < inputData.getFormat().getChannels();chan++)
			{
				int sample = (int) (inputData.getData(chan, s)*gainValue);
				inputData.put(sample, chan, s);

			}
		}
	}
	public void setGain(double gainValue){
		this.gainValue=gainValue;
	}
	
	public double getGain(){
		return this.gainValue;
	}
}
