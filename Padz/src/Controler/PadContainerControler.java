package Controler;

import java.util.ArrayList;
import java.util.List;

import Model.StreamedAudioClip;
import Model.AudioClip;
import Model.PadGridModel;
import Model.Set;
import View.PadContainerView;
import View.PadView;

public class PadContainerControler {

	PadContainerView vue;
	List<Set> setList;
	PadGridModel padGridModel;
	ArrayList<PadControler> padControlerList;
	
	PadControler selectedPad;
	
	
	
	public PadContainerControler(PadGridModel padGridModel,List<Set> setList){
		
		vue = new PadContainerView();
		this.setList = setList;
		this.padGridModel = padGridModel;
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
		/*
		for(Set set : setList){
			
			int i = 0;
			int j = 0;
			for(AudioClip clip : set.getAudioClipList() ){
				
				PadControler controler = new PadControler(clip,this);
				padControlerList.add(controler);
				vue.addPadView(controler.getVue(), i, j);
				i = i + 1;
				if(i == 10){
					i = 0;
					j = j + 1;
				}
			}
		}
		
		*/
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
		
	}
	
	public PadControler getSelectedPad(){
		
		return selectedPad;
	}
	
	public PadContainerView getVue(){
		
		return vue;
	}
}
