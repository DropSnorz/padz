package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class SetPropertiesView extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public JTextField TXT_Name;
	public JComboBox CB_Trigger_Type;
	public JButton BT_OK;
	public JButton BT_Cancel;
	

	/**
	 * Create the dialog.
	 */
	public SetPropertiesView() {
		setResizable(false);
		setTitle("Set Properties");
		setBounds(100, 100, 407, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblName.setBounds(27, 30, 46, 14);
		contentPanel.add(lblName);
		
		TXT_Name = new JTextField();
		TXT_Name.setBounds(83, 27, 279, 20);
		contentPanel.add(TXT_Name);
		TXT_Name.setColumns(10);
		
		JLabel lblTrigger = new JLabel("Trigger:");
		lblTrigger.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTrigger.setBounds(27, 76, 46, 14);
		contentPanel.add(lblTrigger);
		
		CB_Trigger_Type = new JComboBox();
		CB_Trigger_Type.setBounds(83, 73, 279, 20);
		contentPanel.add(CB_Trigger_Type);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				BT_OK = new JButton("OK");
				BT_OK.setActionCommand("OK");
				buttonPane.add(BT_OK);
				getRootPane().setDefaultButton(BT_OK);
			}
			{
				BT_Cancel = new JButton("Cancel");
				BT_Cancel.setActionCommand("Cancel");
				buttonPane.add(BT_Cancel);
			}
		}
	}
}
