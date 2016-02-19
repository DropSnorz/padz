package View;

import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;

public class PadDragSourceListener implements DragSourceListener {

	
	PadView source;
	public PadDragSourceListener(PadView source){
		
		this.source = source;
	}
	
	@Override
	public void dragDropEnd(DragSourceDropEvent dsde) {
		
		System.out.println("src= " + dsde.getSource().getClass().getName());
		if(dsde.getDropSuccess()){
			
			source.getAudioDropped(true);
			
		}
		else{
			
			source.getAudioDropped(false);

			
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
