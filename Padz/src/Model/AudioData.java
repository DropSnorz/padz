package Model;

import javax.sound.sampled.AudioFormat;

public class AudioData {

	
	int[][] data;
	AudioFormat format;
	int samples;
	
	AudioData(AudioFormat format, int samples){
		
		this.format = format;
		this.samples = samples;
		data = new int[format.getChannels()] [samples];
		
	}
	
	public int getSamples() {
		return samples;
	}

	public void put(int sample, int channel, int offset){
		data[channel][offset] = sample;
	}

	public int[][] getData() {
		return data;
	}
	public int getData(int channel, int offset){
		return data[channel][offset];
	}

	public void setData(int[][] data) {
		this.data = data;
	}

	public AudioFormat getFormat() {
		return format;
	}
	
	
	
	
}
