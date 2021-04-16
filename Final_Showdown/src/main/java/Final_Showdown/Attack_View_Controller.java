package Final_Showdown;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import models.P_Attack.Attack;

public class Attack_View_Controller extends PrimaryController {
	//variables
	PrimaryController main;
	Character_Creation_Controller chara_creator;
	ObservableList<Attack> attacks;
	
	//texts
	@FXML
	protected Label lab_power;
	@FXML
	protected Label lab_cd;
	@FXML
	protected Label lab_hit;
	
	//table
	@FXML
	protected TableView<Attack> table;
	@FXML
	protected TableColumn<Attack, String> col_name;
	
	//methods
	@FXML
	protected void setController(PrimaryController main, Character_Creation_Controller chara, Attack aux) {
		this.main=main;
		this.chara_creator=chara;
		this.attacks=chara.attacks;
		this.table.setItems(attacks);
		col_name.setCellValueFactory(eachAttack -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(eachAttack.getValue().getName());
			return v;
		});
		this.lab_power.setText(aux.getPower()+"");
		this.lab_cd.setText(aux.getCd()+"");
		this.lab_hit.setText(aux.getHit_rate()+"");
	}

	@FXML
	protected void setController(PrimaryController main, ObservableList<Attack>attacks) {
		this.main=main;
		this.attacks=attacks;
	}
	
	@FXML
	protected void updateStats() {
		if(attacks!=null&&attacks.size()>0) {
			if(table.getSelectionModel().getSelectedItem()!=null) {
				lab_power.setText(table.getSelectionModel().getSelectedItem().getPower()+"");
				lab_cd.setText(table.getSelectionModel().getSelectedItem().getCd()+"");
				lab_hit.setText(table.getSelectionModel().getSelectedItem().getHit_rate()+"");
			}
			else {
				lab_power.setText("");
				lab_cd.setText("");
				lab_hit.setText("");
			}
		}
		else {
			lab_power.setText("");
			lab_cd.setText("");
			lab_hit.setText("");
		}
	}
	
	
}
