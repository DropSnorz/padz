package Controler;

import java.util.List;

import Model.StreamedAudioClip;
import Model.AudioClip;
import Model.Set;
import View.PadContainerView;
import View.PadView;

public class PadContainerControler {

	PadContainerView vue;
	List<Set> setList;
	
	
	public PadContainerControler(List<Set> setList){
		
		vue = new PadContainerView();
		this.setList = setList;
		
		createContainer();
		
	}
	
	public void createContainer(){
		
		for(Set set : setList){
			
			int i = 0;
			int j = 0;
			for(AudioClip clip : set.getAudioClipList() ){
				
				PadControler controler = new PadControler(clip);
				vue.addPadView(controler.getVue(), i, j);
				i = i + 1;
				if(i == 10){
					i = 0;
					j = j + 1;
				}
			}
		}
	}
	
	public PadContainerView getVue(){
		
		return vue;
	}
}
