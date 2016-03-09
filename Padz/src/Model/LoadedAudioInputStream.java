package Model;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

public class LoadedAudioInputStream extends AudioInputStream {
	
	byte[] data;
	int readHead;

	int dataSize;
	
	public int startSample;
	public int uncheckedEndSample = - 1;
	public int endSample;
	
	private boolean isLoop;
	

	public LoadedAudioInputStream(InputStream stream, AudioFormat format, long arg2) {
		super(stream, format, arg2);
		
		DataInputStream dis = new DataInputStream(stream);
		dataSize = (int) (this.getFrameLength() * format.getFrameSize());

		    data = new byte[dataSize];
		    try {
				dis.readFully(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		// TODO Auto-generated constructor stub	
	}
	
	
	public void resetReadHead(){
		
		
		readHead = startSample;
	}
	
	public int setHeadOnTimeMillis(long time){
		
		if(time > 0){
			
			this.getFrameLength(); 
			int result = (int) (format.getFrameRate()/ 1000 * time);
			System.out.println(result);
			readHead = result;

			return result;
		}
		
		
		return - 1;
	}
	
	public int read(byte[] outputData,int offset, int length){
		
		
		int dataRead = 0;
		boolean continuePlaying = true;
		
		if(readHead > dataSize){
			return - 1;
		}
		if(uncheckedEndSample != - 1 && uncheckedEndSample < readHead){
			
			endSample = uncheckedEndSample;
			uncheckedEndSample = - 1;
		}
	
		
		while(continuePlaying && readHead < endSample && dataRead < length){
			
			outputData[dataRead + offset] = data[readHead];
			
			
			readHead = readHead + 1;
			dataRead = dataRead + 1;
			

			if(isLoop && readHead >= endSample){
				
				readHead = startSample + (readHead - endSample);
			}
		}
		
		
		return dataRead;
		
		
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
	
	
	

}
