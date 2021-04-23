package Final_Showdown;

import java.io.File;
import java.io.IOException;
import java.time.zone.ZoneOffsetTransitionRule.TimeDefinition;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.P_Attack.Attack;
import models.P_Attack.AttackDAO;
import models.P_Character.Character;
import models.P_Character.CharacterDAO;
import models.P_Character.Rol;
import models.P_Character.RolDAO;

public class Character_Creation_Controller {
	
	//variables
	ObservableList<Attack> attacks=AttackDAO.attacks;
	ObservableList<Rol> roles=RolDAO.roles;
	ObservableList<Character> charas= CharacterDAO.charas;
	
	protected PrimaryController dad;
	protected Character_Creation_Controller me;
	private String path_image_presentation="";
	private String path_image_card="";
	private int pnts=400;
	
	//buttons
	@FXML
	protected Button btn_create;
	@FXML
	protected Button btn_cancel;
	@FXML
	protected Button btn_attack_view;
	@FXML
	protected Button btn_image_presentation;
	@FXML
	protected Button btn_image_card;
	
	//texts
	@FXML
	protected TextField txt_name;
	@FXML
	protected TextField txt_rol_hp=new TextField();
	@FXML
	protected TextField txt_rol_atk=new TextField();
	@FXML
	protected TextField txt_rol_def;
	@FXML
	protected TextField txt_rol_spe;
	@FXML
	protected TextField txt_hp;
	@FXML
	protected TextField txt_atk;
	@FXML
	protected TextField txt_def;
	@FXML
	protected TextField txt_spe;
	@FXML
	protected TextField txt_total_hp;
	@FXML
	protected TextField txt_total_atk;
	@FXML
	protected TextField txt_total_def;
	@FXML
	protected TextField txt_total_spe;
	@FXML
	protected TextField txt_image_presentation;
	@FXML
	protected TextField txt_image_card;

	@FXML
	protected Label lab_a1;
	@FXML
	protected Label lab_a2;
	@FXML
	protected Label lab_a3;
	@FXML
	protected Label lab_pnts;
	
	//others
	@FXML
	protected ComboBox<Rol> com_rol;
	@FXML
	protected ComboBox<Attack> com_att_1;
	@FXML
	protected ComboBox<Attack> com_att_2;
	@FXML
	protected ComboBox<Attack> com_att_3;
	@FXML
	protected ImageView image_presentation;
	@FXML
	protected ImageView image_card;

	//méthods
	@FXML
	private void add() {
		
	}
	
	@FXML
	private void set_Presentation() {
		File file=null;
		FileChooser filechooser= new FileChooser();
		filechooser.setTitle("Selecionar imagen...");
		try {
			file=filechooser.showOpenDialog(null);
			if(file!=null&&file.getPath().matches(".+\\.png")||file.getPath().matches(".+\\.jpg")) {
				Image img= new Image ("file:\\"+file.getPath());
				image_presentation.setImage(img);
				txt_image_presentation.setText(file.getPath());
			}else { //extension incorrecta
				Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setHeaderText(null);
	    		alert.setTitle("Información");
	    		alert.setContentText("Formato incorrecto: Debe elegir un tipo de archivo jpg o png.");
	    		alert.showAndWait();
			}
		}catch (Exception e) {
			// TODO: handle exception;
		}		
	}
	
	@FXML
	private void set_Card() {
		File file=null;
		FileChooser filechooser= new FileChooser();
		filechooser.setTitle("Selecionar imagen...");
		try {
			file=filechooser.showOpenDialog(null);
			if(file!=null&&file.getPath().matches(".+\\.png")||file.getPath().matches(".+\\.jpg")) {
				Image img= new Image ("file:\\"+file.getPath());
				image_card.setImage(img);
				txt_image_card.setText(file.getPath());
			}else { //extension incorrecta
				Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setHeaderText(null);
	    		alert.setTitle("Información");
	    		alert.setContentText("Formato incorrecto: Debe elegir un tipo de archivo jpg o png.");
	    		alert.showAndWait();
			}
		}catch (Exception e) {
			// TODO: handle exception;
		}		
	}
	
	@FXML
	private void cancel() {
		Stage stage = (Stage) this.btn_cancel.getScene().getWindow();
		stage.close();
	}

	protected void setController(PrimaryController dad, Character_Creation_Controller me) {
		this.dad=dad;
		this.me=me;
		this.com_rol.setItems(this.roles);
		this.com_rol.setValue(roles.get(0));
		if(attacks!=null&&attacks.size()>0) {
			com_att_1.setItems(attacks);
			com_att_1.setValue(attacks.get(0));
			com_att_2.setItems(attacks);
			com_att_2.setValue(attacks.get(0));
			com_att_3.setItems(attacks);
			com_att_3.setValue(attacks.get(0));
		}
	}
	
	@FXML
	protected void updateRolStats() {
		//setear base
		txt_rol_hp.setText(com_rol.getSelectionModel().getSelectedItem().getHp_base()+"");
		txt_rol_atk.setText(com_rol.getSelectionModel().getSelectedItem().getAtk_base()+"");
		txt_rol_def.setText(com_rol.getSelectionModel().getSelectedItem().getDef_base()+"");
		txt_rol_spe.setText(com_rol.getSelectionModel().getSelectedItem().getSpe_base()+"");
		
		//calcular total
		txt_total_hp.setText(Integer.parseInt(txt_rol_hp.getText())+(Integer.parseInt(txt_hp.getText())*2)+"");
		txt_total_atk.setText(Integer.parseInt(txt_rol_atk.getText())+Integer.parseInt(txt_atk.getText())+"");
		txt_total_def.setText(Integer.parseInt(txt_rol_def.getText())+Integer.parseInt(txt_def.getText())+"");
		txt_total_spe.setText(Integer.parseInt(txt_rol_spe.getText())+Integer.parseInt(txt_spe.getText())+"");
		
	}
	
	@FXML 
	private void updateTextSHP() {
		if(txt_hp.getText().matches("[0-9]+")) {
			int ini=Integer.parseInt(txt_hp.getText());
			
			if(Integer.parseInt(txt_hp.getText())+
			Integer.parseInt(txt_atk.getText())+
			Integer.parseInt(txt_def.getText())+
			Integer.parseInt(txt_spe.getText())<=400) { //es valido
				txt_total_hp.setText(Integer.parseInt(txt_rol_hp.getText())+(ini*2)+"");
				pnts=400-(ini+
						Integer.parseInt(txt_atk.getText())+
						Integer.parseInt(txt_def.getText())+
						Integer.parseInt(txt_spe.getText()));
						
				lab_pnts.setText(pnts+"");
			}
			else{ //se pasa
				Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setHeaderText(null);
	    		alert.setTitle("Información");
	    		alert.setContentText("Los valores establecidos sobrepasan el máximo de Puntos de Refuerzo disponibles.");
	    		alert.showAndWait();
				txt_hp.setText(0+"");
				pnts=400-(
						Integer.parseInt(txt_atk.getText())+
						Integer.parseInt(txt_def.getText())+
						Integer.parseInt(txt_spe.getText()));
						
				lab_pnts.setText(pnts+"");
				txt_total_hp.setText(txt_rol_hp.getText());
			}	
		}
		else {			
			txt_hp.setText(0+"");
			pnts=400-(0+
					Integer.parseInt(txt_atk.getText())+
					Integer.parseInt(txt_def.getText())+
					Integer.parseInt(txt_spe.getText()));
					
			lab_pnts.setText(pnts+"");
			txt_total_hp.setText(txt_rol_hp.getText());
		}
		
	}
	
	@FXML 
	private void updateTextSATK() {
		if(txt_atk.getText().matches("[0-9]+")) {
			int ini=Integer.parseInt(txt_atk.getText());
			
			if(Integer.parseInt(txt_hp.getText())+
			Integer.parseInt(txt_atk.getText())+
			Integer.parseInt(txt_def.getText())+
			Integer.parseInt(txt_spe.getText())<=400) { //es valido
				
				txt_total_atk.setText(Integer.parseInt(txt_rol_atk.getText())+ini+"");
				pnts=400-(ini+
						Integer.parseInt(txt_hp.getText())+
						Integer.parseInt(txt_def.getText())+
						Integer.parseInt(txt_spe.getText()));
						
				lab_pnts.setText(pnts+"");
			}
			else{ //se pasa
				Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setHeaderText(null);
	    		alert.setTitle("Información");
	    		alert.setContentText("Los valores establecidos sobrepasan el máximo de Puntos de Refuerzo disponibles.");
	    		alert.showAndWait();
				txt_atk.setText(0+"");
				pnts=400-(
						Integer.parseInt(txt_hp.getText())+
						Integer.parseInt(txt_def.getText())+
						Integer.parseInt(txt_spe.getText()));
						
				lab_pnts.setText(pnts+"");
				txt_total_atk.setText(txt_rol_atk.getText());
			}	
		}
		else {			
			txt_atk.setText(0+"");
			pnts=400-(0+
					Integer.parseInt(txt_hp.getText())+
					Integer.parseInt(txt_def.getText())+
					Integer.parseInt(txt_spe.getText()));
					
			lab_pnts.setText(pnts+"");
			txt_total_atk.setText(txt_rol_atk.getText());
		}
	}
	
	@FXML 
	private void updateTextSDEF() {
		if(txt_def.getText().matches("[0-9]+")) {
			int ini=Integer.parseInt(txt_def.getText());
			
			if(Integer.parseInt(txt_hp.getText())+
			Integer.parseInt(txt_atk.getText())+
			Integer.parseInt(txt_def.getText())+
			Integer.parseInt(txt_spe.getText())<=400) { //es valido
				
				txt_total_def.setText(Integer.parseInt(txt_rol_def.getText())+ini+"");
				pnts=400-(ini+
						Integer.parseInt(txt_hp.getText())+
						Integer.parseInt(txt_atk.getText())+
						Integer.parseInt(txt_spe.getText()));
						
				lab_pnts.setText(pnts+"");
			}
			else{ //se pasa
				Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setHeaderText(null);
	    		alert.setTitle("Información");
	    		alert.setContentText("Los valores establecidos sobrepasan el máximo de Puntos de Refuerzo disponibles.");
	    		alert.showAndWait();
				txt_def.setText(0+"");
				pnts=400-(
						Integer.parseInt(txt_hp.getText())+
						Integer.parseInt(txt_atk.getText())+
						Integer.parseInt(txt_spe.getText()));
						
				lab_pnts.setText(pnts+"");
				txt_total_def.setText(txt_rol_def.getText());
			}	
		}
		else {			
			txt_def.setText(0+"");
			pnts=400-(0+
					Integer.parseInt(txt_hp.getText())+
					Integer.parseInt(txt_atk.getText())+
					Integer.parseInt(txt_spe.getText()));
					
			lab_pnts.setText(pnts+"");
			txt_total_def.setText(txt_rol_def.getText());
		}
	}
	
	@FXML 
	private void updateTextSSPE() {
		if(txt_spe.getText().matches("[0-9]+")) {
			int ini=Integer.parseInt(txt_spe.getText());
			
			if(Integer.parseInt(txt_hp.getText())+
			Integer.parseInt(txt_atk.getText())+
			Integer.parseInt(txt_def.getText())+
			Integer.parseInt(txt_spe.getText())<=400) { //es valido
				
				txt_total_spe.setText(Integer.parseInt(txt_rol_spe.getText())+ini+"");
				pnts=400-(ini+
						Integer.parseInt(txt_hp.getText())+
						Integer.parseInt(txt_atk.getText())+
						Integer.parseInt(txt_def.getText()));
						
				lab_pnts.setText(pnts+"");
			}
			else{ //se pasa
				Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setHeaderText(null);
	    		alert.setTitle("Información");
	    		alert.setContentText("Los valores establecidos sobrepasan el máximo de Puntos de Refuerzo disponibles.");
	    		alert.showAndWait();
				txt_spe.setText(0+"");
				pnts=400-(
						Integer.parseInt(txt_hp.getText())+
						Integer.parseInt(txt_atk.getText())+
						Integer.parseInt(txt_def.getText()));
						
				lab_pnts.setText(pnts+"");
				txt_total_spe.setText(txt_rol_spe.getText());
			}	
		}
		else {			
			txt_def.setText(0+"");
			pnts=400-(0+
					Integer.parseInt(txt_hp.getText())+
					Integer.parseInt(txt_atk.getText())+
					Integer.parseInt(txt_def.getText()));
					
			lab_pnts.setText(pnts+"");
			txt_total_spe.setText(txt_rol_spe.getText());
		}
	}
	
	@FXML
	private void updateRols() {
		if(com_att_1.getSelectionModel().getSelectedItem()!=null) {
			attacks.set(0, com_att_1.getSelectionModel().getSelectedItem());
		}
		if(com_att_2.getSelectionModel().getSelectedItem()!=null) {
			attacks.set(1, com_att_2.getSelectionModel().getSelectedItem());
		}
		if(com_att_3.getSelectionModel().getSelectedItem()!=null) {
			attacks.set(2, com_att_3.getSelectionModel().getSelectedItem());
		}
	}

	@FXML
	private void viewAttacks() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("attack_view.fxml"));
			Parent root = loader.load();
			
			Attack_View_Controller attack_view=loader.getController();
			attack_view.setController(dad, me, attacks.get(0));
			Scene scene= new Scene(root);
			Stage stage= new Stage();
			stage.getIcons().add(new Image("file:src/main/resources/images/icon_attack_creator.png"));
			stage.setTitle("Visualizador de Ataques");
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
