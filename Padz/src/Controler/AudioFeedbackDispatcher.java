package Controler;

import javax.sound.sampled.AudioFormat;
import javax.swing.SwingUtilities;

public class AudioFeedbackDispatcher {

	AudioFormat format;
	MasterControler masterControler;


	float lastPeakRight = 0f;
	float lastPeakLeft = 0f;    


	public AudioFeedbackDispatcher(AudioFormat format,MasterControler masterControler){

		this.format = format;
		this.masterControler = masterControler;
	}
	
	public void DispatchMasterStereoAudioSource(byte[] data,int size){
		
		float[] samplesRight = new float[size];
		float[] samplesLeft = new float[size];
		
		for(int i = 0, s = 0,t=0; i < size;) {
			
			//Copy to left
			int sample = 0;

			sample |= data[i++] & 0xFF; // (reverse these two lines
			sample |= data[i++] << 8;   //  if the format is big endian)

			// normalize to range of +/-1.0f
			samplesLeft[s++] = sample / 32768f;
			
			//Copy to right
			sample = 0;
			sample |= data[i++] & 0xFF; // (reverse these two lines
			sample |= data[i++] << 8;   //  if the format is big endian)

			// normalize to range of +/-1.0f
			samplesRight[t++] = sample / 32768f;
			
		}
		
		float peakLeft = getPeakLeft(samplesLeft);
		float peakRight = getPeakRight(samplesRight);
		float rmsLeft = getRms(samplesLeft);
		float rmsRight = getRms(samplesRight);
		
		setMasterDataOnEDT(rmsLeft,peakLeft,rmsRight,peakRight);
		
		
	}
	
	public float getPeakRight(float[] samples){
		
		float peak = 0f;
		for(float sample : samples) {

			float abs = Math.abs(sample);
			if(abs > peak) {
				peak = abs;
			}
		}
		
		if(lastPeakRight > peak) {
			peak = lastPeakRight * 0.875f;
		}

		lastPeakRight = peak;
		
		return peak;
	}
	
	public float getPeakLeft(float[] samples){
		
		float peak = 0f;
		for(float sample : samples) {

			float abs = Math.abs(sample);
			if(abs > peak) {
				peak = abs;
			}
		}
		
		if(lastPeakLeft > peak) {
			peak = lastPeakLeft * 0.875f;
		}

		lastPeakLeft = peak;
		
		return peak;
		
	}
	
	public float getRms(float[] samples){
		
		float rms = 0f;
		for(float sample : samples) {

			rms += sample * sample;
		}

		rms = (float)Math.sqrt(rms / samples.length);
		
		return rms;
	}


	public void setMasterDataOnEDT(float rmsLeft, float peakLeft,float rmsRight, float peakRight){
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				masterControler.setLeftMeterData(rmsLeft, peakLeft);
				masterControler.setRightMeterData(rmsRight, rmsRight);
			}
		});
		
	}

}
