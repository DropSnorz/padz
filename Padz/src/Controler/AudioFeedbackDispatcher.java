package Controler;

import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFormat;
import javax.swing.SwingUtilities;

import Model.Set;

public class AudioFeedbackDispatcher {

	AudioFormat format;
	MasterControler masterControler;
	SetContainerControler setContainerControler;


	ByteBuffer buffer = ByteBuffer.allocate(1024);;
	float lastPeakRight = 0f;
	float lastPeakLeft = 0f;
	

	public AudioFeedbackDispatcher(AudioFormat format,MasterControler masterControler, SetContainerControler setContainerControler){

		this.format = format;
		this.masterControler = masterControler;
		this.setContainerControler = setContainerControler;
	}

	public void DispatchMasterStereoAudioSource(byte[] data,int size){

		float[] samplesRight = new float[size/2];
		float[] samplesLeft = new float[size/2];


		splitChannels(data,size,samplesRight,samplesLeft);

		float peakLeft = getPeakLeft(samplesLeft);
		float peakRight = getPeakRight(samplesRight);
		float rmsLeft = getRms(samplesLeft);
		float rmsRight = getRms(samplesRight);

		setMasterDataOnEDT(rmsLeft,peakLeft,rmsRight,peakRight);


	}
	

	public void DispatchSetStereoAudioSource(byte[] data, int size, Set set){

		
		
		SetControler setControler = setContainerControler.getSelectedSetControler();

		if(setControler.getSet().equals(set)){
			
			for(byte atom : data){
				buffer.put(atom);
			}
			
			//System.out.println("ind"+ buffer.position());
			
			if(buffer.position()<128){
				
			}
			else{
				
			buffer.flip();


			byte finalData[] = new byte[buffer.limit() + 1];
			int finalSize = buffer.limit();
			int i = 0;
			while (buffer.hasRemaining()) {
				finalData[i] += buffer.get();
				i = i + 1;
				}
			buffer.clear();
			

			float[] samplesRight = new float[finalSize/2];
			float[] samplesLeft = new float[finalSize/2];

			splitChannels(finalData,finalSize,samplesRight,samplesLeft);

			float peakLeft = getPeakLeft(samplesLeft);
			float peakRight = getPeakRight(samplesRight);
			float rmsLeft = getRms(samplesLeft);
			float rmsRight = getRms(samplesRight);

			setSetDataOnEDT(rmsLeft,peakLeft,rmsRight,peakRight,set);
		}

		}
	}


	public void splitChannels(byte[] data, int size, float[] left, float[] right){

		for(int i = 0, s = 0,t=0; i < size;) {

			//Copy to left
			int sample = 0;

			sample |= data[i++] << 8;   //  if the format is big endian)

			sample |= data[i++] & 0xFF; // (reverse these two lines

			// normalize to range of +/-1.0f
			left[s++] = sample / 32768f;

			//Copy to right
			sample = 0;

			sample |= data[i++] << 8;   //  if the format is big endian)

			sample |= data[i++] & 0xFF; // (reverse these two lines

			// normalize to range of +/-1.0f
			right[t++] = sample / 32768f;

		}


	}
	public float getPeakRight(float[] samples){

		float peak = 0f;
		for(float sample : samples) {

			float abs = Math.abs(sample);
			if(abs > peak) {
				peak = abs;
			}
		}

		/*
		if(lastPeakRight > peak) {
			peak = lastPeakRight * 0.875f;
		}

		lastPeakRight = peak;
		*/

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

		/*
		if(lastPeakLeft > peak) {
			peak = lastPeakLeft * 0.875f;
		}

		lastPeakLeft = peak;
		*/

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
