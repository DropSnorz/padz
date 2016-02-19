package View;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import Model.AudioClip;

public class TransferableAudioClip implements Transferable {

	
	public static DataFlavor audioClipFlavor = new DataFlavor(AudioClip.class,"Audio Clip Object");

	
	AudioClip audioClip;
	DataFlavor supportedFlavor[] = { audioClipFlavor };


	public TransferableAudioClip(AudioClip audioClip){

		this.audioClip = audioClip;

	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {

		if(flavor.equals(audioClipFlavor)){

			return audioClip;
		}
		else{

			throw new UnsupportedFlavorException(flavor);
		}
		// TODO Auto-generated method stub
	}


	@Override
	public DataFlavor[] getTransferDataFlavors() {

		return supportedFlavor;
	}
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {

		if(flavor.equals(audioClipFlavor)){

			return true;
		}
		else{


			return false;
		}
	}




}
