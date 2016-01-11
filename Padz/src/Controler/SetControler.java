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
	
	public SetControler(Set set){
		
		this.set = set;
		vue = new SetView();
		vue.BT_ColorChooser.addActionListener(this);
		
	}
	
	public void openColorChooser(){
		
		Color currentColor = new Color(set.getColor_r(),set.getColor_g(),set.getColor_b());
		
		Color newColor = JColorChooser.showDialog(vue, "Choose a new color",currentColor );
		
		//TODO update color (model and ui)
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
