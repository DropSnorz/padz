package Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;

public class ClipMixer extends StreamMixer {
	
	//StreamMixer streamMixer;
	List<StreamedAudioClip> audioClipList;
	
	
	public ClipMixer(AudioFormat audioFormat){
		super(audioFormat, new ArrayList());
		//ClipMixer(audioFormat, new ArrayList<LoadedAudioClip>());
		
	}
	
	
	public ClipMixer(AudioFormat audioFormat, List<StreamedAudioClip> audioClipList) {
		super(audioFormat,new ArrayList());
		//ArrayList<AudioInputStream> audioInputStreamList = new ArrayList<AudioInputStream>();
		
		this.audioClipList = audioClipList;
		for (StreamedAudioClip clip : audioClipList){
			
			audioInputStreamList.add(clip.getAudioStream());
		}
		
	}
	

	public int read(byte[] buffer, int nOffset, int length){
		
		updateStreams();
		
		try {
			return super.read(buffer, nOffset, length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
		
	}
	
	public void updateStreams(){
		
		for(StreamedAudioClip clip : audioClipList){
			
			try {
				if(clip.getAudioStream().available() <= 0){
					clip.isPlaying = false;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(clip.getIsPlaying() == false){
				
				if(audioInputStreamList.contains(clip.getAudioStream())){
					
					audioInputStreamList.remove(clip.getAudioStream());
				}
			}
			else {
				
				if(!audioInputStreamList.contains(clip.getAudioStream())){
					
					System.out.println("ADD");

					audioInputStreamList.add(clip.getAudioStream());
				}
			}
			
			
		}
	}
	
	

	
}
