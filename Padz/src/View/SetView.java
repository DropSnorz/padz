package View;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JButton;

public class SetView extends JPanel {
	
	public JButton BT_ColorChooser;
	public JButton BT_Options;

	/**
	 * Create the panel.
	 */
	public SetView() {
		
		setLayout(null);
		setPreferredSize(new Dimension(260, 200));
		
		JSlider SL_Gain = new JSlider();
		SL_Gain.setValue(100);
		SL_Gain.setOrientation(SwingConstants.VERTICAL);
		SL_Gain.setBounds(23, 22, 30, 140);
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
		
		

	}
}
