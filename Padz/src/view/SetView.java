package view;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import view.component.LevelMeter;

import javax.swing.JLabel;
import javax.swing.JButton;

public class SetView extends JPanel {
	
	public JButton BT_ColorChooser;
	public JButton BT_Options;
	private LevelMeter rightMeter;
	private LevelMeter leftMeter;
	public JSlider SL_Gain;

	/**
	 * Create the panel.
	 */
	public SetView() {
		
		setLayout(null);
		setPreferredSize(new Dimension(260, 200));
		
		SL_Gain = new JSlider();
		SL_Gain.setPaintTicks(true);
		SL_Gain.setValue(100);
		SL_Gain.setOrientation(SwingConstants.VERTICAL);
		SL_Gain.setBounds(10, 11, 50, 140);
		add(SL_Gain);
		
		JLabel lblGain = new JLabel("Gain");
		lblGain.setHorizontalAlignment(SwingConstants.CENTER);
		lblGain.setBounds(23, 162, 30, 14);
		add(lblGain);
		
		BT_Options = new JButton("Options...");
		BT_Options.setBounds(101, 147, 89, 23);
		add(BT_Options);
		
		BT_ColorChooser = new JButton("Color...");
		BT_ColorChooser.setBounds(101, 113, 89, 23);
		add(BT_ColorChooser);
		
		rightMeter = new LevelMeter();
		rightMeter.setPreferredSize(new Dimension(9, 100));
		rightMeter.setBounds(230, 43, 9, 133);
		add(rightMeter);
		
		leftMeter = new LevelMeter();
		leftMeter.setPreferredSize(new Dimension(9, 100));
		leftMeter.setBounds(216, 43, 9, 133);
		add(leftMeter);
		
		

	}
	
	public void setLeftMeterData(float rms,float peak){
		leftMeter.setAmplitude(rms);
        leftMeter.setPeak(peak);
	}
	public void setRightMeterData(float rms,float peak){
		rightMeter.setAmplitude(rms);
		rightMeter.setPeak(peak);
	}
	
}
