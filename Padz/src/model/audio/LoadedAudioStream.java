package model.audio;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import org.tritonus.share.sampled.TConversionTool;

public class LoadedAudioStream extends AudioStream {
		
	int data[][];
	int readHead;
	int dataSize;
	
	public int startSample = 0;
	public int uncheckedEndSample = - 1;
	public int endSample;
	private boolean isLoop;
	
	public LoadedAudioStream(AudioInputStream stream) {
		
		this.format = stream.getFormat();
		this.dataSize = (int) (stream.getFrameLength());
		
		data = AudioEncoder.decode(stream);
	}
	
	public void resetReadHead(){
		
		
		readHead = startSample;
	}
	
	public int setHeadOnTimeMillis(long time){
		return 0;
	}
	
	public AudioData read(int length){
				
		int dataRead = 0;
		boolean continuePlaying = true;
		AudioData audioData = new AudioData(this.format, length);
		
		if(readHead > dataSize){
			return audioData;
		}
		if(uncheckedEndSample != - 1 && uncheckedEndSample < readHead){
			
			endSample = uncheckedEndSample;
			uncheckedEndSample = - 1;
		}
		
		while(continuePlaying && readHead < endSample && dataRead < length){
			
			for(int chan = 0; chan < format.getChannels(); chan++){
				audioData.put(data[chan][readHead], chan, dataRead);
			}
			
			readHead = readHead + 1;
			dataRead = dataRead + 1;
			
			if(isLoop && readHead >= endSample){
				
				readHead = startSample + (readHead - endSample);
			}
		}
		
		return audioData;
	}
	
	public int available(){
		
		return endSample - readHead;
	}
	
	public int getDataSize() {
		return dataSize;
	}

	
	public int getStartSample() {
		return startSample;
	}


	public void setStartSample(int startSample) {
		this.startSample = startSample;
	}


	public int getEndSample() {
		
		return endSample;
	}


	public void setEndSample(int endSample) {
		
		this.uncheckedEndSample = endSample;
	}


	public boolean isLoop() {
		return isLoop;
	}


	public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}


	public AudioFormat getFormat() {
		return format;
	}


	@Override
	public long getFrameLength() {
		
		return dataSize;
	}

}
