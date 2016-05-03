package controler;

import java.util.ArrayList;
import java.util.List;

import model.AudioClip;
import model.PadGridModel;
import model.Set;
import view.PadContainerView;
import view.PadView;

public class PadContainerControler {

	private PadContainerView vue;
	private PadGridModel padGridModel;
	private ArrayList<PadControler> padControlerList;
	private SamplerControler samplerControler;
	private PadControler selectedPad;
	private  Set selectedSet;



	public PadContainerControler(PadGridModel padGridModel,List<Set> setList, SamplerControler samplerControler){

		vue = new PadContainerView();
		//this.setList = setList;
		this.padGridModel = padGridModel;
		this.samplerControler = samplerControler;
		padControlerList = new ArrayList<PadControler>();
		selectedPad = null;
		createContainer();		
		
	}

	public void createContainer(){

		for(int i = 0; i < padGridModel.getSizeX(); i++){

			for(int j = 0; j < padGridModel.getSizeY(); j++){

				PadControler controler = new PadControler(padGridModel.getAudioClip(i, j),this);
				padControlerList.add(controler);
				vue.addPadView(controler.getVue(), i, j);

			}
		}
	}
	
	public void updateUI()
	{
		
		for(PadControler padControler : padControlerList){
			
			padControler.updateUI();
		}
	}
	
	public void selectFirstPad(){
		
		setSlectedPad(padControlerList.get(0));
	}

	public void setSlectedPad(PadControler padControler){

		for(PadControler controler : padControlerList){

			if(controler.equals(padControler)){
				controler.select();
				selectedPad = controler;
			}
			else{

				controler.deselect();
			}
		}
		
		samplerControler.notifySelectedPadChanges();

	}

	public void startPadSelectionMode(Set set){

		this.selectedSet = set;
		for(PadControler controler : padControlerList){

			controler.setHandleSetChanges(true);
		}
	}
	
	public void stopPadSelectionMode(){
		
		for(PadControler controler : padControlerList){

			controler.setHandleSetChanges(false);
		}
	}
	
	public void updatePadFromClip(AudioClip clip){
		
		for(PadControler controler : padControlerList){
			
			if(controler.getClip() == clip){
				
				controler.updateUI();
			}
		}
	}


	public Set getSelectedSet(){

		return selectedSet;

	}

	public PadControler getSelectedPad(){

		return selectedPad;
	}

	public PadContainerView getVue(){

		return vue;
	}
}
