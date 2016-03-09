package Controler;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Model.Set;
import View.SetView;

public class SetControler implements ChangeListener, ActionListener {

	private SetView vue;
	private Set set;

	private SetContainerControler setContainerControler;

	public SetControler(Set set, SetContainerControler setContainerControler){

		this.set = set;
		this.setContainerControler = setContainerControler;
		vue = new SetView();
		vue.BT_ColorChooser.addActionListener(this);
		vue.BT_Options.addActionListener(this);
		vue.SL_Gain.addChangeListener(this);

	}

	public void openColorChooser(){

		Color currentColor = new Color(set.getColor_r(),set.getColor_g(),set.getColor_b());

		Color newColor = JColorChooser.showDialog(vue, "Choose a new color",currentColor );

		if(newColor != null){
			set.setColor_r(newColor.getRed());
			set.setColor_g(newColor.getGreen());
			set.setColor_b(newColor.getBlue());
		}

		//TODO update color (model and ui)

		setContainerControler.setModelChanged();
	}
	
	public void setLeftMeterData(float rms, float peak){

		vue.setLeftMeterData(rms, peak);

	}

	public void setRightMeterData(float rms, float peak){

		vue.setRightMeterData(rms, peak);
	}

	public void openSetProperties(){

		SetPropertiesControler controler = new SetPropertiesControler(set);
		setContainerControler.setModelChanged();


	}

	public void requestForHandle(){

		setContainerControler.requestPadSelectionMode(set);

	}
	public void requestSelected(){

		setContainerControler.setSelectedSetControler(this);
	}

	public Set getSet(){

		return set;
	}

	public SetView getVue(){		
		return vue;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == vue.BT_ColorChooser){

			openColorChooser();
		}
		else if (e.getSource() == vue.BT_Options){

			openSetProperties();
		}

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
		if(e.getSource() == vue.SL_Gain){
			
			
			if (vue.SL_Gain.getValueIsAdjusting()) {
		        
				double gain = vue.SL_Gain.getValue()*0.01;
		       	set.setGain(gain);
		
			}
		       	
		}
		
	}
}
