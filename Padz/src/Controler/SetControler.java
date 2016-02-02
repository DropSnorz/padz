package Controler;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;

import Model.Set;
import View.SetView;

public class SetControler implements ActionListener {

	private SetView vue;
	private Set set;
	
	private SetContainerControler setContainerControler;
	
	public SetControler(Set set, SetContainerControler setContainerControler){
		
		this.set = set;
		this.setContainerControler = setContainerControler;
		vue = new SetView();
		vue.BT_ColorChooser.addActionListener(this);
		
	}
	
	public void openColorChooser(){
		
		Color currentColor = new Color(set.getColor_r(),set.getColor_g(),set.getColor_b());
		
		Color newColor = JColorChooser.showDialog(vue, "Choose a new color",currentColor );
		
		set.setColor_r(newColor.getRed());
		set.setColor_g(newColor.getGreen());
		set.setColor_b(newColor.getBlue());
		
		//TODO update color (model and ui)
		
		setContainerControler.setModelChanged();
	}
	
	public void requestForHandle(){
		
		setContainerControler.requestPadSelectionMode(set);
		
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
		
	}
}
