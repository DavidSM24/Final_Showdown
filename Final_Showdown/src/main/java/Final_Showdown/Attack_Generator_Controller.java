package Final_Showdown;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.P_Attack.Attack;

public class Attack_Generator_Controller{
	
	//variables
	protected Character_Creation_Controller dad;
	protected Attack_Generator_Controller me;
	int a_number=0;
	
	//buttons
	@FXML
	private Button btn_save;
	@FXML
	private Button btn_exit;
	
	//texts
	@FXML
	TextField txt_name;
	@FXML
	TextField txt_power;
	@FXML
	TextField txt_cd;
	@FXML
	TextField txt_hit; 
	
	//methods
	
	@FXML
	private void save() {
		
		if(
				this.txt_name.getText().equals("")
				||this.txt_power.getText().equals("")
				||this.txt_cd.getText().equals("")
				||this.txt_hit.getText().equals("")
				||!this.txt_power.getText().matches("[0-9]+")
				||!this.txt_cd.getText().matches("[0-9]+")
				||!this.txt_hit.getText().matches("[0-9]+")) {
			Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setHeaderText(null);
    		alert.setTitle("Información");
    		String f="";
    		if(this.txt_name.getText().matches("")) {
    			f+="\n -Debe rellenar el campo Nombre.";
    		}
    		if(this.txt_power.getText().matches("")) {
    			f+="\n -Debe rellenar el campo Potencia.";
    		}
    		else if(!txt_power.getText().matches("[0-9]+")) {
    			f+="\n -Debe rellenar el campo Potencia con un valor numérico.";
    		}
    		if(this.txt_cd.getText().matches("")) {
    			f+="\n -Debe rellenar el campo Coste.";
    		}
    		else if(!this.txt_cd.getText().matches("[0-9]+")) {
    			f+="\n -Debe rellenar el campo Coste con valores numéricos.";
    		}
    		if(this.txt_hit.getText().matches("")) {
    			f+="\n -Debe rellenar el campo Acierto.";
    		}
    		else if(!this.txt_hit.getText().matches("[0-9]+")){
    			f+="\n -Debe rellenar el campo Acierto con valores numéricos.";
    		}
    				
    		alert.setContentText(f);
    		alert.showAndWait();
		}
		else {
			if(dad.attacks==null) {
				dad.attacks= FXCollections.observableArrayList();
			}
			if(dad.attacks.size()==0) {
				dad.attacks.add(null);
				dad.attacks.add(null);
				dad.attacks.add(null);
			}
			switch(a_number) {
			
			case 0:
					dad.attacks.set(0, new Attack(0,txt_name.getText(),
					Integer.parseInt(txt_power.getText()),
					Integer.parseInt(txt_cd.getText()),
					Integer.parseInt(txt_hit.getText())));
					dad.lab_a1.setText(this.txt_name.getText());
					Stage stage = (Stage) this.btn_save.getScene().getWindow();
					stage.close();
					break;
					
			case 1:
					dad.attacks.set(1, new Attack(0,txt_name.getText(),
					Integer.parseInt(txt_power.getText()),
					Integer.parseInt(txt_cd.getText()),
					Integer.parseInt(txt_hit.getText())));
					dad.lab_a2.setText(this.txt_name.getText());
					Stage stage2 = (Stage) this.btn_save.getScene().getWindow();
					stage2.close();
					break;
			case 2:
					dad.attacks.set(2, new Attack(0,txt_name.getText(),
					Integer.parseInt(txt_power.getText()),
					Integer.parseInt(txt_cd.getText()),
					Integer.parseInt(txt_hit.getText())));
					dad.lab_a3.setText(this.txt_name.getText());
					Stage stage3 = (Stage) this.btn_save.getScene().getWindow();
					stage3.close();
					break;
			default:
			}
		}
		
		
	}
	
	@FXML
	private void cancel() {
		Stage stage = (Stage) this.btn_exit.getScene().getWindow();
		stage.close();
	}
	
	protected void setController(Character_Creation_Controller dad, Attack_Generator_Controller me, int n) {
		this.me=me;
		this.dad=dad;
		this.a_number=n;
	}
}
