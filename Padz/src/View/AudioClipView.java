package View;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.GridLayout;
import javax.swing.BoxLayout;

public class AudioClipView extends JPanel {

	/**
	 * Create the panel.
	 */
	public AudioClipView() {
		setLayout(null);
		
		this.setBounds(0, 0, 800, 200);
		this.setPreferredSize(new Dimension(800,200));
		this.setMinimumSize(new Dimension(800,200));
		JPanel filePanel = new JPanel();
		filePanel.setBounds(10, 11, 267, 178);
		filePanel.setBorder(new TitledBorder(null, "File", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(filePanel);
		filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.X_AXIS));
		
		JPanel audioPanel = new JPanel();
		audioPanel.setBounds(287, 11, 503, 178);
		audioPanel.setBorder(new TitledBorder(null, "Audio", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(audioPanel);

	}
}
