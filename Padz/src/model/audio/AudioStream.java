package model.audio;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;

import utils.exception.UnavailableStreamTypeException;

public abstract class AudioStream {

	AudioFormat format;
	public abstract AudioData read(int length);
	public abstract long getFrameLength();
	public abstract int available();
	
	public int read(byte[] data, int offset, int length) throws UnavailableStreamTypeException, IOException{
		
		throw new UnavailableStreamTypeException();
	}
	
	
	public AudioFormat getFormat(){
		return this.format;
	}
}
