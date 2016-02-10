package View;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.GridLayout;

import javax.sound.sampled.FloatControl;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

public class AudioClipView extends JPanel /*implements ChangeListener*/ {
	public JSlider gainGauge;
	public double gainValue=0.5;
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
		filePanel.add(fileBox);
		filePanel.add(fileChoice);
		
	//Initialisation du Panel audio	
		JPanel audioPanel = new JPanel();		
		double i = 0.0;
		int min=0, max=100, init=50;
		SpinnerNumberModel audioNumberStart = new SpinnerNumberModel(i,i,i+endSound,0.1);
		SpinnerNumberModel audioNumberEnd = new SpinnerNumberModel(i,i,i+endSound,0.1);
		JSpinner start = new JSpinner(audioNumberStart);
		start.setBounds(67, 39, 51, 20);
		JSpinner end = new JSpinner(audioNumberEnd);
		end.setBounds(128, 39, 39, 20);
		String[] onOff = {"On","Off"};
		JList onOffSelect = new JList(onOff);
		onOffSelect.setBounds(193, 41, 31, 38);
		gainGauge=new JSlider(SwingConstants.HORIZONTAL, min, max, init);
		gainGauge.setBounds(55, 103, 336, 45);
		gainGauge.setMajorTickSpacing(25);
		gainGauge.setMinorTickSpacing(1);
		gainGauge.setPaintTicks(true);
		gainGauge.setPaintLabels(true);
		onOffSelect.setSelectedIndex(1);
		audioPanel.setBounds(238, 11, 503, 178);
		audioPanel.setBorder(new TitledBorder(null, "Audio", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(audioPanel);
		audioPanel.setLayout(null);
		audioPanel.add(start);
		audioPanel.add(end);
		audioPanel.add(onOffSelect);
		audioPanel.add(gainGauge);
		
		JLabel lblStart = new JLabel("Start");
		lblStart.setBounds(73, 14, 46, 14);
		audioPanel.add(lblStart);
		
		JLabel lblEnd = new JLabel("End");
		lblEnd.setBounds(128, 14, 31, 14);
		audioPanel.add(lblEnd);
		
		JLabel lblLoop = new JLabel("Loop");
		lblLoop.setBounds(190, 14, 46, 14);
		audioPanel.add(lblLoop);
		
		JLabel lblGain = new JLabel("Gain");
		lblGain.setBounds(205, 152, 46, 14);
		audioPanel.add(lblGain);
		System.out.println(gainValue);
	}
}


