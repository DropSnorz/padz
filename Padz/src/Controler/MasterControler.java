package Controler;

import View.MasterView;

public class MasterControler {
	
	MasterView vue;
	
	public MasterControler(){
		
		vue = new MasterView();
		
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

}
