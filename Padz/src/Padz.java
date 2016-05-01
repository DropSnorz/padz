import java.awt.EventQueue;
import java.io.IOException;

import javax.sound.sampled.*;

import controler.AudioProcess;
import controler.SamplerControler;
import model.Set;
import view.SamplerView;

public class Padz {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 
		 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SamplerControler samplerControler = new SamplerControler();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
