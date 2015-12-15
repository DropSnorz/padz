package Controler;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

import Model.StreamedAudioClip;
import Model.Set;
import View.SamplerView;

public class SamplerControler {

	
	SamplerView vue;
	ArrayList<Set> setList;
	
	PadContainerControler padContainerControler;
	
	
	public SamplerControler(){
		
		vue = new SamplerView();
		vue.setVisible(true);
		
		
		 Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		 Mixer globalMixer = AudioSystem.getMixer(mixerInfo[0]);
		 
		 ArrayList<StreamedAudioClip> clipList = new ArrayList<StreamedAudioClip>();
		 setList = new ArrayList<Set>();
		 StreamedAudioClip clip1 = new StreamedAudioClip("C:/Users/Arthur/Documents/Ableton/drum.wav");
		 StreamedAudioClip clip2 = new StreamedAudioClip("C:/Users/Arthur/Documents/Ableton/lead.wav");
		 
		 clipList.add(clip1);
		 clipList.add(clip2);
		 
		 Set set1 = new Set(clipList);
		 
		 setList.add(set1);
		 
		padContainerControler = new PadContainerControler(setList);

			
		vue.addToContentPane(padContainerControler.getVue(), BorderLayout.SOUTH);
			
		 
		 Line.Info[] mLineInfo = globalMixer.getSourceLineInfo();
		 
		 DataLine.Info info = new DataLine.Info(SourceDataLine.class, 
		 clip1.getAudioStream().getFormat());
		
		 try {
			SourceDataLine mInputMixer = (SourceDataLine) globalMixer.getLine(mLineInfo[0]);
			AudioProcess audioProcess = new AudioProcess(mInputMixer,clip1.getAudioStream().getFormat(),set1);			
			audioProcess.start();
			
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		 
	}
}
