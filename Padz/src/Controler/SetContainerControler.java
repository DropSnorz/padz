package Controler;

import View.SetContainerView;

public class SetContainerControler {

	
	private SetContainerView vue;
	
	
	public SetContainerControler(){
		
		vue = new SetContainerView();
	}
	
	public SetContainerView getVue(){
		return vue;
	}
}
