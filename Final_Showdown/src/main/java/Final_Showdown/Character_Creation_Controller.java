package Final_Showdown;

import java.io.File;
import java.io.IOException;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.P_Attack.Attack;
import models.P_Character.Rol;
import models.P_Character.RolDAO;

public class Character_Creation_Controller {
	
	//variables
	ObservableList<Attack> attacks;
	RolDAO repoR = RolDAO.getInstance();
	List<Rol> roles=repoR.OL_getAllRols();
	
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
	protected Button btn_atk1;
	@FXML
	protected Button btn_atk2;
	@FXML
	protected Button btn_atk3;
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
	protected ImageView image_presentation;
	@FXML
	protected ImageView image_card;

	//méthods
	@FXML
	private void add() {
		
	}
	
	@FXML
	private void setAttack1() {
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("attack_generator.fxml"));
			Parent root = loader.load();
			Attack_Generator_Controller attack_controller= loader.getController();
			attack_controller.setController(me, attack_controller, 0);
			
			if(attacks!=null&&attacks.get(0)!=null) {
				attack_controller.txt_name.setText(attacks.get(0).getName());
				attack_controller.txt_power.setText(attacks.get(0).getPower()+"");
				attack_controller.txt_cd.setText(attacks.get(0).getCd()+"");
				attack_controller.txt_hit.setText(attacks.get(0).getHit_rate()+"");
			}
			
			Scene scene= new Scene(root);
			Stage stage= new Stage();
			stage.getIcons().add(new Image("file:src/main/resources/images/icon_attack_creator.png"));
			stage.setTitle("Generador de Ataques");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void setAttack2() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("attack_generator.fxml"));
			Parent root = loader.load();
			Attack_Generator_Controller attack_controller= loader.getController();
			attack_controller.setController(me, attack_controller, 1);
			
			if(attacks!=null&&attacks.get(1)!=null) {
				attack_controller.txt_name.setText(attacks.get(1).getName());
				attack_controller.txt_power.setText(attacks.get(1).getPower()+"");
				attack_controller.txt_cd.setText(attacks.get(1).getCd()+"");
				attack_controller.txt_hit.setText(attacks.get(1).getHit_rate()+"");
			}
			
			Scene scene= new Scene(root);
			Stage stage= new Stage();
			stage.getIcons().add(new Image("file:src/main/resources/images/icon_attack_creator.png"));
			stage.setTitle("Generador de Ataques");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void setAttack3() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("attack_generator.fxml"));
			Parent root = loader.load();
			Attack_Generator_Controller attack_controller= loader.getController();
			attack_controller.setController(me, attack_controller, 2);
			
			if(attacks!=null&&attacks.get(2)!=null) {
				attack_controller.txt_name.setText(attacks.get(2).getName());
				attack_controller.txt_power.setText(attacks.get(2).getPower()+"");
				attack_controller.txt_cd.setText(attacks.get(2).getCd()+"");
				attack_controller.txt_hit.setText(attacks.get(2).getHit_rate()+"");
			}
			
			Scene scene= new Scene(root);
			Stage stage= new Stage();
			stage.getIcons().add(new Image("file:src/main/resources/images/icon_attack_creator.png"));
			stage.setTitle("Generador de Ataques");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	protected void setController(PrimaryController dad, Character_Creation_Controller me, ObservableList<Rol> roles) {
		this.dad=dad;
		this.me=me;
		this.com_rol.setItems(roles);
		this.com_rol.setValue(roles.get(0));
	}
	
	@FXML
	protected void updateRolStats() {
		//setear base
		txt_rol_hp.setText(com_rol.getSelectionModel().getSelectedItem().getHp_base()+"");
		txt_rol_atk.setText(com_rol.getSelectionModel().getSelectedItem().getAtk_base()+"");
		txt_rol_def.setText(com_rol.getSelectionModel().getSelectedItem().getDef_base()+"");
		txt_rol_spe.setText(com_rol.getSelectionModel().getSelectedItem().getSpe_base()+"");
		
		//calcular total
		txt_total_hp.setText(Integer.parseInt(txt_rol_hp.getText())+Integer.parseInt(txt_hp.getText())+"");
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
				txt_total_hp.setText(Integer.parseInt(txt_rol_hp.getText())+ini+"");
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
}
