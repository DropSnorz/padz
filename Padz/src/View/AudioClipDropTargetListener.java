package View;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;
import java.util.List;

public class AudioClipDropTargetListener implements DropTargetListener{

	
	PadView target;
	
	public AudioClipDropTargetListener(PadView source){
		
		this.target = source;
	}
	
	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
	
		doDrag(dtde);
		target.updateDragAndDropFeedback(false, dtde.getLocation());
		
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		
		
		target.updateDragAndDropFeedback(false, null);
		
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		
		doDrag(dtde);
		target.updateDragAndDropFeedback(true, dtde.getLocation());
		
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		
		target.updateDragAndDropFeedback(false, dtde.getLocation());
		
		Transferable transferable = dtde.getTransferable();
		
		if(dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
			
			dtde.acceptDrop(DnDConstants.ACTION_COPY);
			
			List data;
			try {
				data = (List) transferable.getTransferData(DataFlavor.javaFileListFlavor);
				
				if(data != null && data.size() > 0){
					
					target.getDropContent(data);
					System.out.println("Drop envent feedback");
				}
				
			} catch (UnsupportedFlavorException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void doDrag(DropTargetDragEvent dtde){
		
		if(dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
			
			dtde.acceptDrag(DnDConstants.ACTION_COPY);
		}
		else{
			
			dtde.rejectDrag();
		}
	}
	
	
	

}
