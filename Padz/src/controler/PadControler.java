package controler;

import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import model.AudioClip;
import model.LoadedAudioClip;
import model.Set;
import view.PadDragSourceListener;
import view.PadDropTargetListener;
import view.PadView;
import view.TransferableAudioClip;

public class PadControler implements MouseListener, ActionListener, DragGestureListener {


	private PadView vue;
	private AudioClip clip;
	private PadDragSourceListener dragSourceListener;
	private PadDropTargetListener dropTargetListener;
	private PadContainerControler padContainerControler;
	public boolean handleSetChanges = false;

	public PadControler(AudioClip clip, PadContainerControler padContainerControler){

		this.padContainerControler = padContainerControler;
		this.clip = clip;

		vue = new PadView(this);
		vue.addMouseListener(this);
		vue.BT_Play.addMouseListener(this);
		vue.BT_Stop.addMouseListener(this);

		vue.setDragGestureListener(this);
		
		dragSourceListener = new PadDragSourceListener(this, this.getVue());
		dropTargetListener = new PadDropTargetListener(this, this.getVue());
		
		vue.setDragSourceListener(dragSourceListener);
		vue.setDropTargetListener(dropTargetListener);
		
		updateUI();
	}

	public void select(){

		vue.drawSelectedCursor(vue.getGraphics());
		vue.setTickEnabled(true);

	}

	public void deselect(){

		vue.repaint();
		vue.setTickEnabled(false);
	}

	
	public void getDropContent(List<File> data){

		loadFileFromDrop(data.get(0));

	}
	
	public void loadFileFromDrop(File file){

		AudioClip previousClip = this.clip;
		Set previousSet = clip.getSet();
		LoadedAudioClip audioClip = new LoadedAudioClip(file.getAbsolutePath());
		audioClip.setSet(previousSet);
		previousClip.stop();
		this.clip = audioClip;
		previousClip.delete();
		updateUI();
	}

	public void loadClipFormDrop(AudioClip audioClip){

		AudioClip previousClip = this.clip;
		previousClip.stop();
		this.clip = audioClip;
		previousClip.delete();
		updateUI();

	}
	
	public void resetClipFromDrag(){
		
		Set currentClipSet = clip.getSet();
		AudioClip newClip = new LoadedAudioClip(currentClipSet);
		this.clip =  newClip;
		updateUI();
		
	}
	public void updateUI(){

		vue.LB_FileName.setText(clip.getFileName());
		vue.setSetColor(clip.getSet().getColor_r(), clip.getSet().getColor_g(), clip.getSet().getColor_b());
		
		if(clip.getIsPlaying()){
			vue.setPlayingIcon();
			vue.showStopButton();
		}
		else{
			vue.setStopIcon();
			vue.hideStopButton();
		}

		vue.repaint();

	}
	public void setHandleSetChanges(boolean value){
		handleSetChanges = value;
	}

	public PadView getVue(){
		return vue;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		if(e.getSource() == vue){

			if(handleSetChanges){

				clip.setSet(padContainerControler.getSelectedSet());

				updateUI();
			}
			else{

				padContainerControler.setSlectedPad(this);

			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

		if(e.getSource() == vue.BT_Play){
		
			clip.playFromUserInput();
			padContainerControler.updateUI();
			
		}
		else if (e.getSource() == vue.BT_Stop){
			
			clip.stop();
			updateUI();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if(e.getSource() == vue.BT_Play){
			
			
		}
	}

	public AudioClip getClip() {
		return clip;
	}
	
	public void setCip(AudioClip clip){
		this.clip = clip;
		updateUI();
	}

	@Override
	public void dragGestureRecognized(DragGestureEvent dge) {

		dge.startDrag(DragSource.DefaultMoveDrop, new TransferableAudioClip(clip));

	}

}
