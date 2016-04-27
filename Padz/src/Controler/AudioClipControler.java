package Controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

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
import View.AudioClipView;

public class AudioClipControler implements ChangeListener, ActionListener{
	AudioClipView vue;
	PadControler padControler;
	AudioClip clip;

	boolean handleEventFromView = false;

	AudioClipControler(PadControler padControler){
		
		this.padControler = padControler;
		this.clip = padControler.getClip();
		vue=new AudioClipView(clip.getDurationSeconds());
		vue.SL_Gain.addChangeListener(this);
		vue.SP_Start.addChangeListener(this);
		vue.SP_End.addChangeListener(this);
		vue.BT_Loop.addActionListener(this);
		vue.BT_FileChoice.addActionListener(this);
	}

	public AudioClipView getView() {
		return vue;
	}


	@Override
	public void actionPerformed(ActionEvent e){

		if (e.getSource() == vue.BT_FileChoice){
			JButton source = (JButton)e.getSource();
			int returnVal = vue.newFile.showOpenDialog(source);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				
				String newPath=vue.newFile.getSelectedFile().getAbsolutePath();
				AudioClip previousClip = clip;
				Set previousSet= clip.getSet();
				LoadedAudioClip newClip = new LoadedAudioClip(newPath);
				newClip.setSet(previousSet);
				previousClip.stop();
				
				padControler.setCip(newClip);
				this.setModel(padControler);
				
				previousClip.delete();

			}
		}
		else if (e.getSource() == vue.BT_Loop){
				this.clip.setLoop(vue.BT_Loop.isSelected());
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(handleEventFromView){
			if(e.getSource()==vue.SL_Gain){
				JSlider source = (JSlider)e.getSource();
				if (source.getValueIsAdjusting()) {
					double gain =  source.getValue()*0.01;
					clip.setGain(gain);
				}
			}
			else if(e.getSource()==vue.SP_Start){
				JSpinner source = (JSpinner)e.getSource();
				double start =  (double) source.getValue();
				start = Math.round(start * 100) / 100.0;
				clip.setStart(start);

			}else if(e.getSource()==vue.SP_End){
				JSpinner source = (JSpinner)e.getSource();
				double end =  (double)source.getValue();
				end = Math.round(end * 100) / 100.0;
				clip.setEnd(end);
			} 
		}
	}

	public void setModel(PadControler padControler){
		this.padControler = padControler;
		this.clip=padControler.getClip();
		
		vue.fileBox.setText(clip.getPath());
		SpinnerNumberModel spinnerStartModel = new SpinnerNumberModel(0.0,0.0,clip.getDurationSeconds(),0.1);
		vue.SP_Start.setModel(spinnerStartModel);
		SpinnerNumberModel spinnerEndModel = new SpinnerNumberModel(0.0,0.0,clip.getDurationSeconds(),0.1);
		vue.SP_End.setModel(spinnerEndModel);

		if (clip.getEnd()==0){
			updateView(clip.getStart(),(Math.round(clip.getDurationSeconds()/0.1)*0.1)-0.1,(int)(clip.getGain()*100), clip.getLoop(), clip.getPath());
		}
		else{
			if(clip.getStart()>clip.getEnd()){
				clip.setStart(clip.getEnd());
			}
			updateView(clip.getStart(), clip.getEnd(), (int)(clip.getGain()*100), clip.getLoop(), clip.getPath());
		}
		vue.fileBox.setText(clip.getPath());
		vue.repaint();
	}
	public void updateView(double start, double end, int gain, boolean loop, String path){

		handleEventFromView = false;
		vue.SP_Start.setValue(start);
		vue.SP_End.setValue(end);
		vue.SL_Gain.setValue(gain);		
		vue.BT_Loop.setSelected(loop);
		vue.fileBox.setText(clip.getPath());
		handleEventFromView = true;
	}
}
