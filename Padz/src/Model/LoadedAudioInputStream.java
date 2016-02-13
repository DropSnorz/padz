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
	
	public int startTime;
	public int endTime;

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
		
		
		readHead = 0;
	}
	
	public int setHeadOnTimeMillis(long time){
		
		if(time > 0){
			
			this.getFrameLength(); 
			int result = (int) (format.getFrameRate()/ 1000 * time);
			
			readHead = result;
			return result;
		}
		
		
		return - 1;
	}
	
	public int read(byte[] outputData,int offset, int length){
		
		if(readHead == 0){
			System.out.println(System.currentTimeMillis());
		}
		int dataRead = 0;
		boolean continuePlaying = true;
		
		if(readHead > dataSize){
			return - 1;
		}
		
		while(continuePlaying && readHead < dataSize && dataRead < length){
			
			outputData[dataRead + offset] = data[readHead];
			
			
			readHead = readHead + 1;
			dataRead = dataRead + 1;
			
		}
		
		return dataRead;
		
		
	}
	
	public int available(){
		
		return dataSize - readHead;
	}
	
	public int getDataSize() {
		return dataSize;
	}
	
	
	
	
	
	

}
