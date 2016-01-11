package Controler;

import java.util.ArrayList;

import Model.Set;
import View.SetContainerView;

public class SetContainerControler {

	
	private SetContainerView vue;
	private ArrayList<Set> setList;
	
	
	public SetContainerControler(ArrayList<Set> setList){
		
		this.setList = setList;
		vue = new SetContainerView();
		
		populateSetAccordion();
	}
	
	public void populateSetAccordion(){
		
		for(Set set : setList){
			
			SetControler setControler = new SetControler(set);
			vue.addAccordionBar(set.getName(), set.getColor_r(),set.getColor_g(),set.getColor_b(), setControler.getVue());
			
		}
	}
	
	public SetContainerView getVue(){
		return vue;
	}
}
