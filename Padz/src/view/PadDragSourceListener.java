package view;

import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;

import controler.PadControler;

public class PadDragSourceListener implements DragSourceListener {

	
	PadControler sourceControler;
	PadView source;
	
	public PadDragSourceListener(PadControler controler,PadView source){
		
		this.sourceControler = controler;
		this.source = source;
	}
	
	@Override
	public void dragDropEnd(DragSourceDropEvent dsde) {
		
		System.out.println("src= " + dsde.getSource().getClass().getName());
		if(dsde.getDropSuccess()){
			
			
			sourceControler.resetClipFromDrag();
			
			
		}
		else{
			
			//Drag and drop fail callback
			
		}
		
	}

	@Override
	public void dragEnter(DragSourceDragEvent dsde) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void dragExit(DragSourceEvent dse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragOver(DragSourceDragEvent dsde) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dropActionChanged(DragSourceDragEvent dsde) {
		// TODO Auto-generated method stub
		
	}

}
