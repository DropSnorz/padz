package controler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;

import model.Set;
import model.event.TriggerType;
import view.SetPropertiesView;

public class SetPropertiesControler implements ActionListener {
	
	
	private SetPropertiesView vue;
	private Set set;
	
	public SetPropertiesControler(Set set){
		
		
		vue = new SetPropertiesView();
		this.set = set;
		
		DefaultComboBoxModel<TriggerType> comboModel = new DefaultComboBoxModel<TriggerType>();

		for(TriggerType type : TriggerType.values()){
			
			comboModel.addElement(type);
			
		}
		
		vue.CB_Trigger_Type.setModel(comboModel);
		vue.CB_Trigger_Type.setSelectedItem(set.getTriggerType());
		vue.TXT_Name.setText(set.getName());
		
		vue.BT_Cancel.addActionListener(this);
		vue.BT_OK.addActionListener(this);
		
		vue.setVisible(true);
		
	}
	
	private void saveSetInfo(){
		
		set.setName(vue.TXT_Name.getText());
		set.setTriggerType((TriggerType)vue.CB_Trigger_Type.getSelectedItem());
		vue.dispose();
	}
	public SetPropertiesView getVue(){
		
		return vue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == vue.BT_OK){
			saveSetInfo();
		}
		else if(e.getSource() == vue.BT_Cancel){
			vue.dispose();
		}
		
	}

}
