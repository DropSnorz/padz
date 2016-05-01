package controler;

import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFormat;
import javax.swing.SwingUtilities;

import org.tritonus.share.sampled.TConversionTool;

import model.AudioClip;
import model.Set;
import model.audio.AudioData;
import model.audio.IMixable;

public class AudioFeedbackDispatcher {

	AudioFormat format;
	MasterControler masterControler;
	SetContainerControler setContainerControler;
	PadContainerControler padContainerControler;


	ByteBuffer setAudioBuffer = ByteBuffer.allocate(1024);;
	float lastPeakRight = 0f;
	float lastPeakLeft = 0f;
	

	public AudioFeedbackDispatcher(AudioFormat format,MasterControler masterControler, SetContainerControler setContainerControler, PadContainerControler padContainerControler){

		this.format = format;
		this.masterControler = masterControler;
		this.setContainerControler = setContainerControler;
		this.padContainerControler = padContainerControler;
	}
	

	public void DispatchMasterStereoAudioSource(AudioData audioData){
		
		float peakLeft = getPeak(audioData.getData(0));
		float peakRight = getPeak(audioData.getData(1));
		float rmsLeft = getRms(audioData.getData(0));
		float rmsRight = getRms(audioData.getData(1));

		setMasterDataOnEDT(rmsLeft,peakLeft,rmsRight,peakRight);
	}
	
	public void notifyClipModelChanges(AudioClip clip){
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				padContainerControler.updatePadFromClip(clip);

			}
		});
		
	}

	public void dispatch(AudioData audioData, Set set){

		SetControler setControler = setContainerControler.getSelectedSetControler();

		if(setControler.getSet().equals(set)){
			
			float peakLeft = getPeak(audioData.getData(0));
			float peakRight = getPeak(audioData.getData(1));
			
			float rmsLeft = getRms(audioData.getData(0));
			float rmsRight = getRms(audioData.getData(1));

			setSetDataOnEDT(rmsLeft,peakLeft,rmsRight,peakRight,set);
		}
	}
	
	public void dispatch(AudioData audioData, AudioClip clip){
		
	}
	
	public float getPeak(int[] samples){

		float peak = 0f;
		for(float sample : samples) {
			
			float scaled_sample = sample/32768.0f;
			float abs = Math.abs(scaled_sample);
			if(abs > peak) {
				peak = abs;
			}
		}
		return peak;
	}

	public float getRms(int[] samples){

		
		float rms = 0f;
		for(float sample : samples) {

			float scaled_sample = sample/32768.0f;
			rms += scaled_sample * scaled_sample;
		}

		rms = (float)Math.sqrt(rms / samples.length);

		return rms;
	}


	public void setMasterDataOnEDT(float rmsLeft, float peakLeft,float rmsRight, float peakRight){

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				masterControler.setLeftMeterData(rmsLeft, peakLeft);
				masterControler.setRightMeterData(rmsRight, peakRight);
			}
		});

	}

	public void setSetDataOnEDT(float rmsLeft, float peakLeft, float rmsRight, float peakRight,Set set){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {


				SetControler setControler = setContainerControler.getSelectedSetControler();

				if(setControler.getSet().equals(set)){
					setControler.setLeftMeterData(rmsLeft, peakLeft);
					setControler.setRightMeterData(rmsRight, peakRight);
				}	
			}
		});

	}

}
