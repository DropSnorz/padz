package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PadContainerView extends JPanel {

	/**
	 * Create the panel.
	 */

	
	public JPanel[][] panelHolder;
	
	int x = 10;
	int y = 10;
	
	public PadContainerView() {
		
		this.setLayout(new GridLayout(x,y,0,0));
		panelHolder = new JPanel[x][y];
		
		for(int m = 0; m < x; m++) {
			for(int n = 0; n < y; n++) {
				
				panelHolder[m][n] = new JPanel();
				this.add(panelHolder[m][n]);
				//panelHolder[m][n].setBorder(BorderFactory.createLineBorder(Color.black));
				
			}
		}
	}
	
	public void addPadView(PadView vue, int i, int j){
		
		panelHolder[j][i].add(vue);
	}
}
