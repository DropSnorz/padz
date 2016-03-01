package Controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Model.AudioClip;
import Model.LoadedAudioClip;
import Model.Set;
import Model.StreamedAudioClip;
import View.AudioClipView;

public class AudioClipControler implements ChangeListener, ActionListener, ListSelectionListener{
    AudioClipView audioView;
	AudioClip clip;
	double gain;
	double start;
	double end;
	int onOff;
	String path;
	String newPath;
	//SamplerControler sampler;
	AudioClipControler(AudioClip clip){
		audioView=new AudioClipView(clip.getDurationSeconds());
		audioView.gainGauge.addChangeListener(this);
		audioView.start.addChangeListener(this);
		audioView.end.addChangeListener(this);
		audioView.onOffSelect.addListSelectionListener(this);
		audioView.fileChoice.addActionListener(this);
	//	sampler=new SamplerControler();
		this.start=clip.getStart();
		this.end=clip.getEnd();
		this.clip=clip;
		this.path=clip.getPath();
		clip.setGain(1);
	}
	
	public AudioClipView getView() {
		return audioView;
	}
	
	//public double getStartTime(){
	
	//}
	@Override
	public void actionPerformed(ActionEvent e){
		JButton source = (JButton)e.getSource();
		int returnVal = audioView.newFile.showOpenDialog(source);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
	            newPath=audioView.newFile.getSelectedFile().getAbsolutePath();
		}
		//Set previousSet= clip.getSet();
		StreamedAudioClip newClip = new StreamedAudioClip(newPath);
		System.out.println(newPath);
		//newClip.setSet(previousSet);
		this.clip=newClip;
		clip.setPath(newPath);
		//sampler.clipList.add(newClip);
		//updateView(0,clip.getDurationSeconds(),50,0,newPath);
	}

	@Override
	//TODO refacto : GainValue uniquement sur le controleur
	 public void stateChanged(ChangeEvent e) {
       if(e.getSource()==audioView.gainGauge){
    	   JSlider source = (JSlider)e.getSource();
	       if (source.getValueIsAdjusting()) {
	           this.gain =  source.getValue()*0.01;
	           clip.setGain(gain);
	       }
       }else if(e.getSource()==audioView.start){
    	   JSpinner source = (JSpinner)e.getSource();
	           this.start =  (double)source.getValue();
	           clip.setStart(start);
	           System.out.println(clip.getStart());
	       //}
       }else if(e.getSource()==audioView.end){
    	   JSpinner source = (JSpinner)e.getSource();
	           this.end =  (double)source.getValue();
	           clip.setEnd(end);
	           System.out.println(clip.getEnd());
       } 
   }
	@Override
	public void valueChanged(ListSelectionEvent e) {
 	   JList source =(JList)e.getSource();
 	   this.onOff=source.getAnchorSelectionIndex();
 	   clip.setLoop(this.onOff);
 	   if(this.onOff==1){
 		   System.out.println("On");
 	   }else if(this.onOff==0){
 		   System.out.println("Off");
 	   }
	}


	public void setModel(AudioClip clip){
		this.clip=clip;
		audioView.fileBox.setText(clip.getPath());
		if (clip.getEnd()==0){
			updateView(clip.getStart(),(Math.round(clip.getDurationSeconds()/0.1)*0.1)-0.1, clip.getGain(), clip.getLoop(), clip.getPath());
		}else{
			if(clip.getStart()>clip.getEnd()){
				clip.setStart(clip.getEnd());
			}
			updateView(clip.getStart(), clip.getEnd(), clip.getGain(), clip.getLoop(), clip.getPath());
		}
		audioView.fileBox.setText(clip.getPath());
		audioView.repaint();
	}
	public void updateView(double start, double end, int gain, int loop, String path){
		audioView.start.setValue(start);
		audioView.end.setValue(end);
		audioView.gainGauge.setValue(gain);		
		audioView.onOffSelect.setSelectedIndex(loop);
		audioView.fileBox.setText(clip.getPath());
	}

	
}
