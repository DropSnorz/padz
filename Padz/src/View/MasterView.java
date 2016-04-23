package View;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.pushingpixels.trident.Timeline;

import javax.swing.JToggleButton;
import javax.swing.JScrollBar;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class MasterView extends JPanel {

	/**
	 * Create the panel.
	 */
	private LevelMeter leftMeter;
	private LevelMeter rightMeter;
	private JPanel demoPad;
	public JSlider SL_Gain;
	
	public MasterView() {
		setLayout(null);
		this.setPreferredSize(new Dimension(274, 200));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Master", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 251, 178);
		add(panel);
		panel.setLayout(null);
		
		SL_Gain = new JSlider();
		SL_Gain.setMinimum(1);
		SL_Gain.setPaintTicks(true);
		SL_Gain.setBounds(179, 11, 26, 156);
		panel.add(SL_Gain);
		SL_Gain.setOrientation(SwingConstants.VERTICAL);
		
		leftMeter = new LevelMeter();
		leftMeter.setPreferredSize(new Dimension(9, 100));
		leftMeter.setBounds(205, 22, 9, 133);
		panel.add(leftMeter);
		
		rightMeter = new LevelMeter();
		rightMeter.setPreferredSize(new Dimension(9, 100));
		rightMeter.setBounds(219, 22, 9, 133);
		panel.add(rightMeter);
		
		demoPad = new PulseSquare(40,40,Color.white);
		demoPad.setBounds(40, 40, 73, 73);
		//panel.add(demoPad);

	}
	
	public void setLeftMeterData(float rms,float peak){
		leftMeter.setAmplitude(rms);
        leftMeter.setPeak(peak);
        
        if(rms > 0.5){
        	final Timeline rolloverTimeLine = new Timeline(demoPad);
        	
        	rolloverTimeLine.addPropertyToInterpolate("rootColor", Color.lightGray, new Color(0,0,0,0));
        	rolloverTimeLine.setDuration(300);
        	
        	rolloverTimeLine.play();
        }
        
	}
	public void setRightMeterData(float rms,float peak){
		rightMeter.setAmplitude(rms);
		rightMeter.setPeak(peak);
	}
}
