package View;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class MasterView extends JPanel {

	/**
	 * Create the panel.
	 */
	private LevelMeter leftMeter;
	private LevelMeter rightMeter;
	
	public MasterView() {
		setLayout(null);
		this.setPreferredSize(new Dimension(274, 200));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Master", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 251, 178);
		add(panel);
		panel.setLayout(null);
		
		JSlider slider = new JSlider();
		slider.setBounds(169, 11, 10, 156);
		panel.add(slider);
		slider.setOrientation(SwingConstants.VERTICAL);
		
		leftMeter = new LevelMeter();
		leftMeter.setPreferredSize(new Dimension(9, 100));
		leftMeter.setBounds(205, 22, 9, 133);
		panel.add(leftMeter);
		
		rightMeter = new LevelMeter();
		rightMeter.setPreferredSize(new Dimension(9, 100));
		rightMeter.setBounds(219, 22, 9, 133);
		panel.add(rightMeter);

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
