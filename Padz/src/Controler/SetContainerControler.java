package Controler;

import java.util.ArrayList;

import Model.Set;
import View.SetContainerView;

public class SetContainerControler {

	
	private SetContainerView vue;
	private ArrayList<Set> setList;
	
	private SamplerControler samplerControler;
	
	
	public SetContainerControler(ArrayList<Set> setList, SamplerControler samplerControler){
		
		this.setList = setList;
		this.samplerControler = samplerControler;
		vue = new SetContainerView();
		
		populateSetAccordion();
	}
	
	public void populateSetAccordion(){
		
		for(Set set : setList){
			
			SetControler setControler = new SetControler(set,this);
			vue.addAccordionBar(set.getName(), set.getColor_r(),set.getColor_g(),set.getColor_b(), setControler.getVue(),setControler);
			
		}
	}
	
	public void requestPadSelectionMode(Set set){
		
		samplerControler.updatePadSelectionMode(set);
		
	}
	
	public SetContainerView getVue(){
		return vue;
	}
}
