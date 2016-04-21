package Model;



/*
 *	MixingAudioInputStream.java
 *
 *	This file is part of jsresources.org
 *
 *	This code follows an idea of Paul Sorenson.
 */

/*
 * Copyright (c) 1999 - 2001 by Matthias Pfisterer
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
|<---            this code is formatted to fit into 80 columns             --->|
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/*
 * This is a class of Tritonus. It's not one of the best ideas to use it here.
 * However, we really don't want to reimplement its functionality here.
 * You need to have tritonus_share.jar in the classpath. 
 * Get it from http://www.tritonus.org .  
 */
import org.tritonus.share.sampled.TConversionTool;

import Controler.AudioFeedbackDispatcher;



/**
 * Mixing of multiple AudioInputStreams to one AudioInputStream. This class
 * takes a collection of AudioInputStreams and mixes them together. Being a
 * subclass of AudioInputStream itself, reading from instances of this class
 * behaves as if the mixdown result of the input streams is read.
 * 
 * @author Matthias Pfisterer
 */
public class StreamMixer
extends		AudioStream
{
	private static final boolean	DEBUG = false;

	protected ArrayList<IMixable>			mixableEntityList;
	protected AudioFeedbackDispatcher audioFeedBackDispatcher;


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

/*
	public int read()
			throws	IOException
	{
		if (DEBUG) { out("MixingAudioInputStream.read(): begin"); }
		int	nSample = 0;
		Iterator<IMixable>	audioEntityIterator = mixableEntityList.iterator();

		while (audioEntityIterator.hasNext())
		{
			IMixable audioEntity = audioEntityIterator.next();
			AudioInputStream	stream =  audioEntity.getAudioStream();
			int	nByte = stream.read();
			if (nByte == -1)
			{
				
				  The end of this stream has been signaled.
				  We remove the stream from our list.
				 
				audioEntityIterator.remove();
				continue;
			}
			else
			{
				
				  what about signed/unsigned?
				 
				nSample += nByte;
			}
		}
		if (DEBUG) { out("MixingAudioInputStream.read(): end"); }
		return (byte) (nSample & 0xFF);
	}
*/


/*	public int read(byte[] abData, int nOffset, int nLength)


			throws	IOException
	{
		if (DEBUG)
		{
			out("MixingAudioInputStream.read(byte[], int, int): begin");
			out("MixingAudioInputStream.read(byte[], int, int): requested length: " + nLength);
		}
		int	nChannels = getFormat().getChannels();
		int	nFrameSize = getFormat().getFrameSize();
		
		  This value is in bytes. Note that it is the storage size.
		  It may be four bytes for 24 bit samples.
		 
		int	nSampleSize = nFrameSize / nChannels;
		boolean	bBigEndian = getFormat().isBigEndian();
		AudioFormat.Encoding	encoding = getFormat().getEncoding();
		if (DEBUG)
		{
			out("MixingAudioInputStream.read(byte[], int, int): channels: " + nChannels);
			out("MixingAudioInputStream.read(byte[], int, int): frame size: " + nFrameSize);
			out("MixingAudioInputStream.read(byte[], int, int): sample size (bytes, storage size): " + nSampleSize);
			out("MixingAudioInputStream.read(byte[], int, int): big endian: " + bBigEndian);
			out("MixingAudioInputStream.read(byte[], int, int): encoding: " + encoding);
		}
		byte[]	abBuffer = new byte[nFrameSize];
		int[]	anMixedSamples = new int[nChannels];
		for (int nFrameBoundry = 0; nFrameBoundry < nLength; nFrameBoundry += nFrameSize)
		{
			if (DEBUG) { out("MixingAudioInputStream.read(byte[], int, int): frame boundry: " + nFrameBoundry); }
			for (int i = 0; i < nChannels; i++)
			{
				anMixedSamples[i] = 0;
			}
			Iterator<IMixable>	audioEntityIterator = mixableEntityList.iterator();
			while (audioEntityIterator.hasNext())
			{
				IMixable audioEntity = audioEntityIterator.next();
				AudioStream	stream = audioEntity.getAudioStream();
				if (DEBUG)
				{
					out("MixingAudioInputStream.read(byte[], int, int): AudioInputStream: " + stream);
				}
				int	nBytesRead = stream.read(abBuffer, 0, nFrameSize);

				if (DEBUG)
				{
					out("MixingAudioInputStream.read(byte[], int, int): bytes read: " + nBytesRead);
				}
				
				  TODO: we have to handle incomplete reads.
				 
				if (nBytesRead == -1)
				{
					
					  The end of the current stream has been signaled.
					  We remove it from the list of streams.
					 
					audioEntityIterator.remove();
					continue;
				}
				for (int nChannel = 0; nChannel < nChannels; nChannel++)
				{
					int	nBufferOffset = nChannel * nSampleSize;
					int	nSampleToAdd = 0;
					if (encoding.equals(AudioFormat.Encoding.PCM_SIGNED))
					{
						switch (nSampleSize)
						{
						case 1:
							nSampleToAdd = abBuffer[nBufferOffset];
							break;
						case 2:
							nSampleToAdd = TConversionTool.bytesToInt16(abBuffer, nBufferOffset, bBigEndian);							
							break;
						case 3:
							nSampleToAdd = TConversionTool.bytesToInt24(abBuffer, nBufferOffset, bBigEndian);
							break;
						case 4:
							nSampleToAdd = TConversionTool.bytesToInt32(abBuffer, nBufferOffset, bBigEndian);
							break;
						}
					}
					// TODO: pcm unsigned
					else if (encoding.equals(AudioFormat.Encoding.ALAW))
					{
						nSampleToAdd = TConversionTool.alaw2linear(abBuffer[nBufferOffset]);
					}
					else if (encoding.equals(AudioFormat.Encoding.ULAW))
					{
						nSampleToAdd = TConversionTool.ulaw2linear(abBuffer[nBufferOffset]);
					}
					
					
					//TODO : refacto TConversiontool.ToDouble()
					//TODO : process frames-block
					double temp[] = new double[1];
					temp[0] = (double) nSampleToAdd;
					for(IEffect effect : audioEntity.getEffectRack()){
						effect.ProcessDoubleReplacing(temp, 1);
					}
					
					
					anMixedSamples[nChannel] += temp[0];
					
				} // loop over channels
			} // loop over streams
			if (DEBUG)
			{
				out("MixingAudioInputStream.read(byte[], int, int): starting to write to buffer passed by caller");
			}
			for (int nChannel = 0; nChannel < nChannels; nChannel++)
			{
				if (DEBUG)
				{
					out("MixingAudioInputStream.read(byte[], int, int): channel: " + nChannel);
				}
				int	nBufferOffset = nOffset + nFrameBoundry  * nFrameSize + nChannel * nSampleSize;
				if (DEBUG)
				{
					out("MixingAudioInputStream.read(byte[], int, int): buffer offset: " + nBufferOffset);
				}
				if (encoding.equals(AudioFormat.Encoding.PCM_SIGNED))
				{
					switch (nSampleSize)
					{
					case 1:
						abData[nBufferOffset] = (byte) anMixedSamples[nChannel];
						break;
					case 2:
						TConversionTool.intToBytes16(anMixedSamples[nChannel], abData, nBufferOffset, bBigEndian);
						break;
					case 3:
						TConversionTool.intToBytes24(anMixedSamples[nChannel], abData, nBufferOffset, bBigEndian);
						break;
					case 4:
						TConversionTool.intToBytes32(anMixedSamples[nChannel], abData, nBufferOffset, bBigEndian);
						break;
					}
				}
				// TODO: pcm unsigned
				else if (encoding.equals(AudioFormat.Encoding.ALAW))
				{
					abData[nBufferOffset] = TConversionTool.linear2alaw((short) anMixedSamples[nChannel]);
				}
				else if (encoding.equals(AudioFormat.Encoding.ULAW))
				{
					abData[nBufferOffset] = TConversionTool.linear2ulaw(anMixedSamples[nChannel]);
				}
			} // (final) loop over channels
		} // loop over frames
		if (DEBUG)
		{
			out("MixingAudioInputStream.read(byte[], int, int): end");
		}
		
		return nLength;
	}
*/

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
	
	private void setAudioFeedbackDispatcher(AudioFeedbackDispatcher afd){
		
		this.audioFeedBackDispatcher = afd;
	}
}

/*** MixingAudioInputStream.java ***/

