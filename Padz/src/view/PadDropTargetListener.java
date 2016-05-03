package view;

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

import controler.PadControler;
import model.AudioClip;

public class PadDropTargetListener implements DropTargetListener{


	PadControler targetControler;
	PadView target;

	public PadDropTargetListener(PadControler controler,PadView target){

		this.targetControler = controler;
		this.target = target;
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

		System.out.println("Drop! ");

		target.updateDragAndDropFeedback(false, dtde.getLocation());

		Transferable transferable = dtde.getTransferable();

		if(dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){

			dtde.acceptDrop(DnDConstants.ACTION_COPY);

			List data;
			try {
				data = (List) transferable.getTransferData(DataFlavor.javaFileListFlavor);

				if(data != null && data.size() > 0){

					targetControler.getDropContent(data);

					dtde.dropComplete(true);

				}

			} catch (UnsupportedFlavorException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else if (dtde.isDataFlavorSupported(TransferableAudioClip.audioClipFlavor)){

			System.out.println("AudioClip data detected ");

			dtde.acceptDrop(DnDConstants.ACTION_COPY);

			try {
				Object data = transferable.getTransferData(TransferableAudioClip.audioClipFlavor);

				AudioClip droppedAudioClip = (AudioClip)data;

				if(droppedAudioClip != targetControler.getClip()){

					targetControler.loadClipFormDrop((AudioClip) data);
					dtde.dropComplete(true);
					
					
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
		else if (dtde.isDataFlavorSupported(TransferableAudioClip.audioClipFlavor)){

			dtde.acceptDrag(DnDConstants.ACTION_COPY);
		}
		else{

			dtde.rejectDrag();
		}
	}

}
