package Model;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

import org.tritonus.share.sampled.TConversionTool;

public class AudioEncoder {


	public static int[][] decode(AudioInputStream stream){

		AudioFormat format = stream.getFormat();
		int size = (int) stream.getFrameLength();
		int channels = format.getChannels();
		int frameSize = format.getFrameSize();
		int sampleSize = frameSize/channels;
		boolean isBigEndian = format.isBigEndian();

		int bufferSplit = 64;
		int bufferSize = frameSize * bufferSplit;

		int [][]data = new int[channels][size];

		int dataRead = 0 ; 

		while(dataRead < size){

			byte buffer[] = new byte [bufferSize];
			try {
				int nReadValues = stream.read(buffer, 0, bufferSize); // * buffer size

				int nStep = (int) nReadValues/frameSize;

				for(int step = 0; step < nStep ; step++ ){

					for(int chan = 0; chan < channels; chan++){
						
						int bufferOffset = step * channels * sampleSize + ((chan * sampleSize));
						
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
					dataRead = dataRead + 1;
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		return data;
	}

	public static int encode(AudioData audioData, byte[] data){

		AudioFormat format = audioData.getFormat();
		int sampleSize = format.getFrameSize() / format.getChannels();

		int bufferOffset = 0;
		for(int s = 0; s < audioData.getSamples(); s++){
			for(int chan = 0; chan < format.getChannels(); chan++){

				if (format.getEncoding().equals(AudioFormat.Encoding.PCM_SIGNED))
				{
					switch (sampleSize)
					{
					case 1:
						data[bufferOffset] = (byte) audioData.getData(chan, s);
						break;
					case 2:
						TConversionTool.intToBytes16(audioData.getData(chan, s), data, bufferOffset, format.isBigEndian());
						break;
					case 3:
						TConversionTool.intToBytes24(audioData.getData(chan, s), data, bufferOffset,  format.isBigEndian());
						break;
					case 4:
						TConversionTool.intToBytes32(audioData.getData(chan, s), data, bufferOffset,  format.isBigEndian());
						break;

					}
				}

				// TODO: pcm unsigned
				else if (format.getEncoding().equals(AudioFormat.Encoding.ALAW))
				{
					data[bufferOffset] = TConversionTool.linear2alaw((short) audioData.getData(chan, s));
				}
				else if (format.getEncoding().equals(AudioFormat.Encoding.ULAW))
				{
					data[bufferOffset] = TConversionTool.linear2ulaw(audioData.getData(chan, s));
				}

				bufferOffset += sampleSize;

			}
		}

		return bufferOffset;
	}
}
