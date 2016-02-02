package View;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import java.awt.GridLayout;

import javax.sound.sampled.FloatControl;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;

public class AudioClipView extends JPanel {

	/**
	 * Create the panel.
	 */
	public AudioClipView(double endSound){
		setLayout(null);
		
		this.setBounds(0, 0, 800, 200);
		this.setPreferredSize(new Dimension(800,200));
		this.setMinimumSize(new Dimension(800,200));
	//Initialisation du Panel File
		JPanel filePanel = new JPanel();
		JTextField fileBox = new JTextField(20);
		JButton fileChoice = new JButton("...");
		filePanel.setBounds(10, 11, 267, 178);
		filePanel.setBorder(new TitledBorder(null, "File", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(filePanel);
		//filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.X_AXIS));
		filePanel.add(fileBox);
		filePanel.add(fileChoice);
		
	//Initialisation du Panel audio	
		JPanel audioPanel = new JPanel();		
		double i = 0.0;
		int min=0, max=100, init=50;
		SpinnerNumberModel audioNumberStart = new SpinnerNumberModel(i,i,i+endSound,0.1);
		SpinnerNumberModel audioNumberEnd = new SpinnerNumberModel(i,i,i+endSound,0.1);
		JSpinner start = new JSpinner(audioNumberStart);
		JSpinner end = new JSpinner(audioNumberEnd);
		String[] onOff = {"On","Off"};
		JList onOffSelect = new JList(onOff);
		JSlider gainGauge=new JSlider(JSlider.VERTICAL, min, max, init);
		gainGauge.setMajorTickSpacing(25);
		gainGauge.setMinorTickSpacing(1);
		gainGauge.setPaintTicks(true);
		gainGauge.setPaintLabels(true);
		
		//FloatControl volume;
		onOffSelect.setSelectedIndex(1);
		audioPanel.setBounds(287, 11, 503, 178);
		audioPanel.setBorder(new TitledBorder(null, "Audio", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(audioPanel);
		audioPanel.add(start);
		audioPanel.add(end);
		audioPanel.add("Loop",onOffSelect);
		audioPanel.add("gain",gainGauge);
	}
}
