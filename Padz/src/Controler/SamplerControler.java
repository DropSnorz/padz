package Controler;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import Model.StreamedAudioClip;
import Model.AudioClip;
import Model.LoadedAudioClip;
import Model.PadGridModel;
import Model.Set;
import View.SamplerView;

public class SamplerControler {


	SamplerView vue;
	ArrayList<Set> setList;
	private MasterControler masterControler;
	private AudioClipControler audioClipControler;
	private PadContainerControler padContainerControler;
	private SetContainerControler setContainerControler;
	
	AudioFeedbackDispatcher audioFeedbackDispatcher;
	
	
	public boolean padSelectionMode = false;


	public SamplerControler(){

		vue = new SamplerView();
		vue.setVisible(true);


		Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		
		for (Mixer.Info info : mixerInfo){
			System.out.println(info);
		}
		Mixer globalMixer = AudioSystem.getMixer(mixerInfo[0]);

		ArrayList<AudioClip> clipList = new ArrayList<AudioClip>();
		setList = new ArrayList<Set>();
		
		String username = System.getProperty("user.name");
		LoadedAudioClip clip1 = new LoadedAudioClip("C:/Users/"+username+"/SoundSample/drum.wav");
		LoadedAudioClip clip2 = new LoadedAudioClip("C:/Users/"+username+"/SoundSample/lead.wav");

		
		AudioFormat format = new AudioFormat(44100,16, 2,true,false);
		System.out.println(format);

		Set set1 = new Set("(default)",format);
		
		PadGridModel padGridModel = new PadGridModel(8,5,set1);
		clipList.add(clip1);
		padGridModel.addAudioClip(clip1, 0, 0);
		clipList.add(clip2);
		padGridModel.addAudioClip(clip2, 1, 0);
		
		
		clip1.setSet(set1);
		clip2.setSet(set1);

		
		//Encoding encoding = new Encoding();
		

		setList.add(set1);
		setList.add(new Set("Set 1", format,231, 76, 60));
		setList.add(new Set("Set 2", format,41, 128, 185));

		padContainerControler = new PadContainerControler(padGridModel,setList);
		vue.addToContentPane(padContainerControler.getVue(), BorderLayout.SOUTH);
		
		padContainerControler.selectFirstPad();
		
		setContainerControler = new SetContainerControler(setList,this);
		vue.addToContentPaneEast(setContainerControler.getVue());
		
		audioClipControler=new AudioClipControler(padContainerControler.getSelectedPad().getClip());
		vue.addToBottomPane(audioClipControler.getView());
		
		masterControler = new MasterControler();
		vue.addToBottomPaneRight(masterControler.getVue());
		
		
		audioFeedbackDispatcher = new AudioFeedbackDispatcher(format,masterControler);

		Line.Info[] mLineInfo = globalMixer.getSourceLineInfo();

		//DataLine.Info info = new DataLine.Info(SourceDataLine.class, 
		///		clip1.getAudioStream().getFormat());

		try {
			SourceDataLine mInputMixer = (SourceDataLine) globalMixer.getLine(mLineInfo[0]);
			AudioProcess audioProcess = new AudioProcess(mInputMixer,format,audioFeedbackDispatcher,set1);			
			audioProcess.start();

		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


	}
	
	public void updateUI(){
		
		padContainerControler.updateUI();
	}
	
	public void updatePadSelectionMode(Set set){
		
		
		if(padSelectionMode==false){
			
			//Start pad selectionMode
			padContainerControler.startPadSelectionMode(set);
			padSelectionMode = true;
			vue.setCursor(Cursor.CROSSHAIR_CURSOR);
		}
		else{
			
			padContainerControler.stopPadSelectionMode();
			padSelectionMode = false;
			//disable pad selection mode
			
			vue.setCursor(Cursor.DEFAULT_CURSOR);
		}
		
	}
}
