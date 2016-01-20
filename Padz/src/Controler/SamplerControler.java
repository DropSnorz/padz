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
import Model.AudioClip;
import Model.LoadedAudioClip;
import Model.PadGridModel;
import Model.Set;
import View.SamplerView;

public class SamplerControler {


	SamplerView vue;
	ArrayList<Set> setList;

	private PadContainerControler padContainerControler;
	private SetContainerControler setContainerControler;


	public SamplerControler(){

		vue = new SamplerView();
		vue.setVisible(true);


		Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		Mixer globalMixer = AudioSystem.getMixer(mixerInfo[0]);

		ArrayList<AudioClip> clipList = new ArrayList<AudioClip>();
		setList = new ArrayList<Set>();
		LoadedAudioClip clip1 = new LoadedAudioClip("C:/Users/Arthur/Documents/Ableton/drum.wav");
		StreamedAudioClip clip2 = new StreamedAudioClip("C:/Users/Arthur/Documents/Ableton/lead.wav");

		
	

		Set set1 = new Set("(default)",clip1.getAudioStream().getFormat());
		
		PadGridModel padGridModel = new PadGridModel(8,5,set1);
		clipList.add(clip1);
		padGridModel.addAudioClip(clip1, 0, 0);
		clipList.add(clip2);
		padGridModel.addAudioClip(clip2, 1, 0);
		
		
		clip1.setSet(set1);
		clip2.setSet(set1);

		setList.add(set1);
		setList.add(new Set("Set 1",231, 76, 60));
		setList.add(new Set("Set 2",41, 128, 185));

		padContainerControler = new PadContainerControler(padGridModel,setList);
		vue.addToContentPane(padContainerControler.getVue(), BorderLayout.SOUTH);
		
		setContainerControler = new SetContainerControler(setList);
		vue.addToContentPaneEast(setContainerControler.getVue());



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
