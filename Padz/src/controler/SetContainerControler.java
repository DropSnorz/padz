package controler;

import java.util.ArrayList;

import model.Set;
import view.SetContainerView;

public class SetContainerControler {

	
	private SetContainerView vue;
	private ArrayList<Set> setList;
	private ArrayList<SetControler> setControlerList;
	
	private SetControler selectedSetControler;
	private SamplerControler samplerControler;
	
	
	public SetContainerControler(ArrayList<Set> setList, SamplerControler samplerControler){
		
		this.setList = setList;
		this.samplerControler = samplerControler;
		setControlerList = new ArrayList<SetControler>();
		vue = new SetContainerView();
		
		populateSetAccordion();
	}
	
	public void populateSetAccordion(){
		
		for(Set set : setList){
			
			SetControler setControler = new SetControler(set,this);
			setControlerList.add(setControler);
			vue.addAccordionBar(set.getName(), setControler.getVue(),setControler);
			
		}
		
		selectedSetControler = setControlerList.get(0);
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
	
	public void setSelectedSetControler(SetControler setControler){
		this.selectedSetControler = setControler;
	}
	
	public SetControler getSelectedSetControler(){
		return selectedSetControler;
	}
	
	public SetContainerView getVue(){
		return vue;
	}
}
