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
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.ImageIcon;

public class AudioClipView extends JPanel {
	public JSlider SL_Gain;
	public SpinnerNumberModel audioNumberStart;
	public SpinnerNumberModel audioNumberEnd; 
	public JFileChooser newFile;

	JPanel audioPanel;
	public JTextField fileBox;
	public JSpinner SP_Start;
	public JSpinner SP_End;
	public JButton BT_FileChoice;
	public JToggleButton BT_Loop;
	public AudioClipView(double endSound){
		setLayout(null);
		
		this.setBounds(0, 0, 800, 200);
		this.setPreferredSize(new Dimension(800,200));
		this.setMinimumSize(new Dimension(800,200));

		JPanel filePanel = new JPanel();
		fileBox = new JTextField(20);
		fileBox.setBounds(10, 22, 231, 20);
		BT_FileChoice = new JButton("...");
		BT_FileChoice.setBounds(251, 21, 45, 23);
		newFile = new JFileChooser();
		filePanel.setBounds(10, 11, 306, 178);
		filePanel.setBorder(new TitledBorder(null, "File", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(filePanel);
		filePanel.setLayout(null);
		filePanel.add(fileBox);
		filePanel.add(BT_FileChoice);
		
		audioPanel = new JPanel();		
		SpinnerNumberModel spinnerStartModel = new SpinnerNumberModel(0.0,0.0,0.0,0.1);
		SpinnerNumberModel spinnerEndModel = new SpinnerNumberModel(0.0,0.0,0.0,0.1);
		SP_Start = new JSpinner(spinnerStartModel);
		SP_Start.setBounds(24, 50, 61, 20);
		SP_End = new JSpinner(spinnerEndModel);
		SP_End.setBounds(115, 50, 60, 20);
		
		int min=0, max=100, init=50;
		SL_Gain=new JSlider(SwingConstants.HORIZONTAL, min, max, init);
		SL_Gain.setPaintTicks(true);
		SL_Gain.setBounds(10, 96, 336, 45);
		SL_Gain.setMajorTickSpacing(25);
		SL_Gain.setMinorTickSpacing(2);
		audioPanel.setBounds(326, 11, 464, 178);
		audioPanel.setBorder(new TitledBorder(null, "Audio", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(audioPanel);
		audioPanel.setLayout(null);
		audioPanel.add(SP_Start);
		audioPanel.add(SP_End);
		audioPanel.add(SL_Gain);
		
		JLabel lblStart = new JLabel("Start");
		lblStart.setBounds(24, 27, 46, 14);
		audioPanel.add(lblStart);
		
		JLabel lblEnd = new JLabel("End");
		lblEnd.setBounds(115, 27, 31, 14);
		audioPanel.add(lblEnd);
		
		JLabel lblLoop = new JLabel("Loop");
		lblLoop.setBounds(242, 27, 46, 14);
		audioPanel.add(lblLoop);
		
		JLabel lblGain = new JLabel("Gain");
		lblGain.setBounds(172, 141, 46, 14);
		audioPanel.add(lblGain);
		
		BT_Loop = new JToggleButton("");
		BT_Loop.setIcon(new ImageIcon(AudioClipView.class.getResource("/resources/img/icon-loop.png")));
		BT_Loop.setBounds(242, 50, 61, 21);
		audioPanel.add(BT_Loop);
	}
}


