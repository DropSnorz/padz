package Controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Model.AudioClip;
import View.AudioClipView;

public class AudioClipControler implements ChangeListener, ActionListener, ListSelectionListener{
    AudioClipView audioView;
	AudioClip clip;
	double gain;
	double start;
	double end;
	int onOff;
	AudioClipControler(AudioClip clip){
		audioView=new AudioClipView(clip.getDurationSeconds());
		audioView.gainGauge.addChangeListener(this);
		audioView.start.addChangeListener(this);
		audioView.end.addChangeListener(this);
		audioView.onOffSelect.addListSelectionListener(this);
		this.start=clip.getStart();
		this.end=clip.getEnd();
		this.clip=clip;
		clip.setGain(1);
	}
	
	public AudioClipView getView() {
		return audioView;
	}
	
	//public double getStartTime(){
	
	//}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
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
	       //if (source.getValueIsAdjusting()) {
	           this.start =  (double)source.getValue();
	           //this.gainInt = source.getValue();
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
		updateView(clip.getStart(), clip.getEnd(), clip.getGain(), clip.getLoop());
		audioView.repaint();
	}
	public void updateView(double start, double end, int gain, int loop){
		audioView.start.setValue(start);
		audioView.end.setValue(end);
		audioView.gainGauge.setValue(gain);		
		audioView.onOffSelect.setSelectedIndex(loop);
	}

	
}
