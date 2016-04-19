package Model;

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
		dataSize = (int) (stream.getFrameLength());
		
		int channels = format.getChannels();
		int frameSize = format.getFrameSize();
		int sampleSize = frameSize/channels;
		boolean isBigEndian = format.isBigEndian();
		
		data = new int[channels][dataSize];
		
		for(int dataRead = 0 ; dataRead < dataSize; dataRead++){
			
			byte buffer[] = new byte [frameSize];
			try {
				stream.read(buffer, 0, frameSize);
				for(int chan = 0; chan < channels; chan++){
					int bufferOffset = chan * sampleSize;
					
					if(format.getEncoding() == AudioFormat.Encoding.PCM_SIGNED){
						
						switch (sampleSize)
						{
						case 1:
							data[chan][dataRead] = buffer[bufferOffset];
							break;
						case 2:
							data[chan][dataRead] = TConversionTool.bytesToInt16(buffer, bufferOffset, isBigEndian);							
							break;
						case 3:
							data[chan][dataRead] = TConversionTool.bytesToInt24(buffer, bufferOffset, isBigEndian);
							break;
						case 4:
							data[chan][dataRead] = TConversionTool.bytesToInt32(buffer, bufferOffset, isBigEndian);
							break;
						}
					}
					
					else if (format.getEncoding() == (AudioFormat.Encoding.ALAW))
					{
						data[chan][dataRead] = TConversionTool.alaw2linear(buffer[bufferOffset]);
					}
					else if (format.getEncoding()==  AudioFormat.Encoding.ULAW)
					{
						data[chan][dataRead] = TConversionTool.ulaw2linear(buffer[bufferOffset]);
					}		
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

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
