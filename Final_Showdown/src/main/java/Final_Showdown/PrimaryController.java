package Final_Showdown;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.P_Attack.Attack;
import models.P_Attack.AttackDAO;
import models.P_Character.CharacterDAO;
import models.P_Character.Character;

public class PrimaryController {
	
	//variables
	protected PrimaryController me;
	protected ObservableList<Attack> attacks= AttackDAO.attacks;
	protected ObservableList<Character> charas=CharacterDAO.charas;
	protected ObservableList<Character> filter_charas=null;
	protected Character c=null;
	protected Attack a=null;
	
	//buttons		
	@FXML
	private Button btn_create_1;
	@FXML
	private Button btn_edit_1;
	@FXML
	private Button btn_delete_1;
	
	@FXML
	private Button btn_create_2;
	@FXML
	private Button btn_edit_2;
	@FXML
	private Button btn_delete_2;
	
	//table
	@FXML
	protected TableView<Character> table_char;
	@FXML
	protected TableColumn<Character, String> col_name;
	
	@FXML
	protected TableView<Attack> table_attack;
	@FXML
	protected TableColumn<Attack, String> col_attack;
    
	//texts
	@FXML
	protected TextField txt_filter;
	@FXML
	protected Label lab_name;
	@FXML
	protected Label lab_universe;
	@FXML
	protected Label lab_band;
	@FXML
	protected Label lab_rol;
	@FXML
	protected Label lab_power;	
	
	@FXML
	protected TextField txt_filter_2;
	@FXML
	protected Label lab_name_2;
	@FXML
	protected Label lab_power_2;
	@FXML
	protected Label lab_cost;
	@FXML
	protected Label lab_hit_rate;
	@FXML
	protected Label lab_extra_name;
	
	//others
	@FXML
	protected ImageView img_view_chara;
	@FXML
	protected ImageView img_view_attack;
	
	//methods
	@FXML
	private void createCharacter() {
		
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("character_creation.fxml"));
			Parent root = loader.load();
			Character_Creation_Controller chara_generator= loader.getController();
			chara_generator.setController(me, chara_generator, null); //probando 
			chara_generator.image_presentation.setImage(new Image("file:src/main/resources/images/default_fighter.png"));
			chara_generator.image_card.setImage(new Image("file:src/main/resources/images/default_fighter.png"));
			chara_generator.updateRolStats();
			Scene scene= new Scene(root);
			Stage stage= new Stage();
			Image icon= new Image("file:src/main/resources/images/icons/icon_chara_creator.jpg");
			stage.getIcons().add(icon);
			stage.setTitle("Creador de Personajes");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	@FXML
	private void editCharacter() {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("character_creation.fxml"));
			Parent root = loader.load();
			Character_Creation_Controller chara_generator= loader.getController();
			chara_generator.setController(me, chara_generator, table_char.getSelectionModel().getSelectedItem()); //probando 
			chara_generator.updateRolStats();
			Scene scene= new Scene(root);
			Stage stage= new Stage();
			Image icon= new Image("file:src/main/resources/images/icons/icon_chara_creator.jpg");
			stage.getIcons().add(icon);
			stage.setTitle("Creador de Personajes");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void deleteCharacter() {

		if(c!=null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setTitle("Confirmación");
			alert.setContentText(" ¿Seguro que desea eliminar este personaje?\n"
									+ " Esta acción no se podrá deshacer.");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				CharacterDAO.eliminar(c);
				select();
				Alert alert2=new Alert(AlertType.INFORMATION);
	    		alert2.setHeaderText(null);
	    		alert2.setTitle("Información");
	    		alert2.setContentText(" El personaje se ha eliminado correctamente.");
	    		alert2.showAndWait();
			}
		}
	}
	
	@FXML
	private void createAttack() {		
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("attack_creator.fxml"));
			Parent root;
			root = loader.load();
			Attack_Generator_Controller agc=loader.getController();
			agc.setController(null, agc, null);
			agc.photo.setImage(new Image("file:src/main/resources/images/attacks/adefault.jpg"));
			Scene scene= new Scene(root);
			Stage stage= new Stage();
			stage.getIcons().add(new Image("file:src/main/resources/images/icons/icon_attack_creator.png"));
			stage.setTitle("Generador de Ataques");
			stage.setResizable(false);;
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@FXML
	private void editAttack() {		
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("attack_creator.fxml"));
			Parent root;
			root = loader.load();
			Attack_Generator_Controller agc=loader.getController();
			agc.setController(null, agc, table_attack.getSelectionModel().getSelectedItem());
			Scene scene= new Scene(root);
			Stage stage= new Stage();
			stage.getIcons().add(new Image("file:src/main/resources/images/icons/icon_attack_creator.png"));
			stage.setTitle("Generador de Ataques");
			stage.setResizable(false);;
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	protected void setController (PrimaryController me) {
		this.me=me;
		table_char.setItems(charas);
		table_attack.setItems(attacks);
		select();
		select_2();
		setTableAndDetailsInfo();
		
	}
	
	protected void setTableAndDetailsInfo() {
		if (charas.size()>0) {
			col_name.setCellValueFactory(eachchara->{
	    		SimpleStringProperty v=new SimpleStringProperty();
	    		v.setValue(eachchara.getValue().getName());
	    		return v;
	    	});
		}
		
		if (attacks.size()>0) {
			col_attack.setCellValueFactory(eachattack->{
	    		SimpleStringProperty v=new SimpleStringProperty();
	    		if(eachattack.getValue().equals(attacks.get(0))){
	    			v.setValue(eachattack.getValue().getName()+" (Predeterminado)");
	    		}
	    		else {
	    			v.setValue(eachattack.getValue().getName());
	    		}
	    		return v;
	    	});
		}		
	}
	
	@FXML
	private void select() { //actualizar esto cuando tenga tiempo por una variable power
		if(table_char.getSelectionModel().getSelectedItem()!=null) {
			c= this.table_char.getSelectionModel().getSelectedItem();;
		}
		else {
			c=null;
			btn_delete_1.setDisable(true);
			btn_edit_1.setDisable(true);
		}

		if(c!=null) {//setea los valores del chara
			btn_delete_1.setDisable(false);
			btn_edit_1.setDisable(false);
			lab_name.setText(c.getName());
			lab_universe.setText(c.getUniverse());
			lab_band.setText(c.getBand());
			lab_rol.setText(c.getRol().getName());
			lab_power.setText(
					(c.getHp()
					+c.getAtk()
					+c.getDef()
					+c.getSpe())+"");
			
			File f=new File("file:"+c.getPhoto_face());
			Image img=new Image(f.getPath());
			img_view_chara.setImage(img);			
		}
		else {// no hay seleccion --> todo a ""
			lab_name.setText("");
			lab_universe.setText("");
			lab_band.setText("");
			lab_rol.setText("");
			lab_power.setText("");
			img_view_chara.setImage(null);
		}
		
		
	}
	
	@FXML
	private void select_2() {
		if(table_attack.getSelectionModel().getSelectedItem()!=null) {
			a= this.table_attack.getSelectionModel().getSelectedItem();
		}
		else {
			a=null;
			btn_delete_2.setDisable(true);
			btn_edit_2.setDisable(true);
		}

		if(a!=null) {//setea los valores del atta
			if(a!=attacks.get(0)) {
				btn_delete_2.setDisable(false);
				btn_edit_2.setDisable(false);
			}
			else {
				btn_edit_2.setDisable(true);
				btn_delete_2.setDisable(true);
			}
			
			lab_name_2.setText(a.getName());
			lab_power_2.setText(a.getPower()+"");
			lab_cost.setText(a.getCost()+"");
			lab_hit_rate.setText(a.getHit_rate()+"");
			lab_extra_name.setText(a.getExtra().getName());
			
			File f=new File("file:"+a.getPhoto());
			Image img=new Image(f.getPath());
			img_view_attack.setImage(img);			
		}
		
		else {// no hay seleccion --> todo a ""
			lab_name_2.setText("");
			lab_power_2.setText("");
			lab_cost.setText("");
			lab_hit_rate.setText("");
			lab_extra_name.setText("");
			img_view_attack.setImage(null);
		}
	}
	
	@FXML
	private void filter(){
		if(!txt_filter.getText().matches("")) {
			ObservableList<Character> filter= FXCollections.observableArrayList();
			filter=CharacterDAO.getCharactersByName(txt_filter.getText());
			table_char.setItems(filter);
			setTableAndDetailsInfo();
		}
		else {
			table_char.setItems(charas);
			setTableAndDetailsInfo();
		}
	}
}
