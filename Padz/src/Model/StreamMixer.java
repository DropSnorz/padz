package Model;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;




public class StreamMixer
extends		AudioStream
{
	private static final boolean	DEBUG = false;

	protected ArrayList<IMixable>			mixableEntityList;
	protected AudioFeedbackListener audioFeedbackListener;


	public StreamMixer(AudioFormat audioFormat)
	{
		format = audioFormat;

		if (DEBUG) { out("MixingAudioInputStream.<init>(): begin"); }
		mixableEntityList = new ArrayList<IMixable>();
		if (DEBUG)
		{
			out("MixingAudioInputStream.<init>(): stream list:");
			for (int i = 0; i < mixableEntityList.size(); i++)
			{
				out("  " + mixableEntityList.get(i));
			}
		}
		if (DEBUG) { out("MixingAudioInputStream.<init>(): end"); }
	}

	/**
	   The maximum of the frame length of the input stream is calculated and returned.
	   If at least one of the input streams has length
	   <code>AudioInputStream.NOT_SPECIFIED</code>, this value is returned.
	 */
	public long getFrameLength()
	{
		long	lLengthInFrames = 0;
		Iterator<IMixable>	streamIterator = mixableEntityList.iterator();
		while (streamIterator.hasNext())
		{
			IMixable	audioEntity =  streamIterator.next();
			long	lLength = audioEntity.getAudioStream().getFrameLength();
			if (lLength == AudioSystem.NOT_SPECIFIED)
			{
				return AudioSystem.NOT_SPECIFIED;
			}
			else
			{
				lLengthInFrames = Math.max(lLengthInFrames, lLength);
			}
		}
		return lLengthInFrames;
	}


	public AudioData read(int length){
		
		Iterator<IMixable>	audioEntityIterator = mixableEntityList.iterator();
		AudioData[] audioDataBuffer = new AudioData[mixableEntityList.size()];
		int count = 0;
		while (audioEntityIterator.hasNext())
		{
			IMixable audioEntity = audioEntityIterator.next();
			AudioData data = audioEntity.getAudioStream().read(length);
			for(IEffect effect : audioEntity.getEffectRack()){
				effect.ProcessAudioDataReplacing(data, length);
			}
			
			audioDataBuffer[count] = data;
			count = count + 1;
		}
		
		AudioData output = new AudioData(format,length);
		
		for(int chan = 0; chan < format.getChannels(); chan++){
			
			for(int sample = 0; sample < length; sample++){
				int mixedSample = 0;
				for(int currentStep = 0; currentStep < count; currentStep++){
					
					mixedSample = mixedSample + audioDataBuffer[currentStep].getData(chan, sample);
				}
				output.put(mixedSample, chan, sample);
			}
		}
		
		if (audioFeedbackListener != null){
			
			audioFeedbackListener.dispatch(output);
		}
		
		return output;
	}

	public int read(byte[] data, int offset, int byte_length)throws IOException{
		
		int sampleSize = format.getFrameSize() / format.getChannels();
		int nSamples =  byte_length / sampleSize / format.getChannels();
		
		AudioData audioData = read(nSamples);
		return AudioEncoder.encode(audioData, data);
		
	}


	/**
	   The minimum of available() of all input stream is calculated and returned.
	 */
	public int available()
	{
		int	nAvailable = 0;
		Iterator<IMixable>	audioEntityIterator = mixableEntityList.iterator();
		while (audioEntityIterator.hasNext())
		{
			AudioStream	stream = audioEntityIterator.next().getAudioStream();
			nAvailable = Math.min(nAvailable, stream.available());
		}
		return nAvailable;
	}



	public void close()
			throws	IOException
	{
		// TODO: should we close all streams in the list?
	}




	public ArrayList<IMixable> getAudioInputStreamList(){

		return mixableEntityList;
	}



	private static void out(String strMessage)
	{
		System.out.println(strMessage);
	}
	
	protected void setAudioFeedbackListener(AudioFeedbackListener afl){
		
		this.audioFeedbackListener = afl;
	}
}

/*** MixingAudioInputStream.java ***/

