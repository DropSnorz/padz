package Controler;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import View.MasterView;

public class MasterControler implements ChangeListener{
	
	MasterView vue;
	AudioProcess audioProcess;
	
	
	
	public MasterControler(){
		
		vue = new MasterView();
		vue.SL_Gain.addChangeListener(this);
		
	}
	
	public void setLeftMeterData(float rms, float peak){
		
		vue.setLeftMeterData(rms, peak);
		
	}
	
	public void setRightMeterData(float rms, float peak){
		
		vue.setRightMeterData(rms, peak);
	}
	
	
	public MasterView getVue(){
		
		return vue;
	}

	public void setAudioProcess(AudioProcess audioProcess){
		
		this.audioProcess = audioProcess;
		
	}
	
	public void setAudioProcessGain(float gain){
		
		if(audioProcess != null){
			
			audioProcess.setGain(gain);
			System.out.println("Called !");
		}
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		
		
		if(e.getSource() == vue.SL_Gain){
			
			if(vue.SL_Gain.getValueIsAdjusting()){
				
				
				double gain = vue.SL_Gain.getValue()*0.01;
				
				//TODO linear to dB
				double gaindB = 20 * Math.log10(gain);
				
				System.out.println(gaindB);
				setAudioProcessGain((float)gaindB);
			}
			
		}
		
	}

}
