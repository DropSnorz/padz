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
			vue.addAccordionBar(set.getName(), setControler.getVue(),setControler);
			
			
		}
	}
	
	public void updateUI(){
		
		
		vue.updateAccordionView();
	}
	
	public void setModelChanged(){
		
		updateUI();
		samplerControler.updateUI();
	}
	
	public void requestPadSelectionMode(Set set){
		
		samplerControler.updatePadSelectionMode(set);
		
	}
	
	public SetContainerView getVue(){
		return vue;
	}
}
