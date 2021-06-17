package Final_Showdown;

import java.io.File;
import java.io.IOException;
import java.time.zone.ZoneOffsetTransitionRule.TimeDefinition;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import interfaces.ICharacter_Creation_Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
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
import utils.FileUtilities;

public class Character_Creation_Controller implements ICharacter_Creation_Controller{
	
	//variables
	ObservableList<Attack> attacks=AttackDAO.attacks;
	ObservableList<Rol> roles=RolDAO.roles;
	Character c= null;
	
	protected PrimaryController dad;
	protected Character_Creation_Controller me;
	private int pnts=400;
	
	//buttons
	@FXML
	protected Button btn_create;
	@FXML
	protected Button btn_cancel;
	@FXML
	protected Button btn_create2;
	@FXML
	protected Button btn_cancel2;
	@FXML
	protected Button btn_create3;
	@FXML
	protected Button btn_cancel3;
	@FXML
	protected Button btn_attack_view;
	@FXML
	protected Button btn_image_presentation;
	@FXML
	protected Button btn_image_card;
	@FXML
	protected Button btn_ost;
	
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
	protected TextField txt_energy_ini;
	@FXML
	protected TextField txt_energy_recover;
	@FXML
	protected TextField txt_image_presentation;
	@FXML
	protected TextField txt_image_card;
	@FXML
	protected TextField txt_universe;
	@FXML 
	protected TextField txt_ost;
	
	@FXML
	protected Label lab_pnts;
	@FXML
	protected TextArea are_description;
	
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
	protected ComboBox<String> com_band;
	@FXML
	protected ImageView image_presentation;
	@FXML
	protected ImageView image_card;

	//méthods
	
	public void setController(PrimaryController dad, Character_Creation_Controller me, Character chara) {
		this.dad=dad;
		this.me=me;
		this.com_rol.setItems(this.roles);
		this.com_rol.setValue(roles.get(0));
		ObservableList<String> bands=FXCollections.observableArrayList();
		bands.addAll("Héroe","Villano","Neutral");
		com_band.setItems(bands);
		com_band.setValue("Héroe");
		
		if(attacks!=null&&attacks.size()>0) {
			com_att_1.setItems(attacks);
			com_att_1.setValue(attacks.get(0));
			com_att_2.setItems(attacks);
			com_att_2.setValue(attacks.get(0));
			com_att_3.setItems(attacks);
			com_att_3.setValue(attacks.get(0));
			
		}
		if(chara!=null) {
			btn_create.setText("Editar");
			btn_create2.setText("Editar");
			btn_create3.setText("Editar");
			
			c=chara;
			are_description.setText(chara.getDescription());
			txt_name.setText(chara.getName());
			txt_universe.setText(chara.getUniverse());
			com_band.setValue(chara.getBand());
			txt_hp.setText(((chara.getHp()-chara.getRol().getHp_base())/2)+"");
			txt_total_hp.setText(chara.getHp()+"");
			txt_atk.setText((chara.getAtk()-chara.getRol().getAtk_base())+"");
			txt_total_atk.setText(chara.getAtk()+"");
			txt_def.setText((chara.getDef()-chara.getRol().getDef_base())+"");
			txt_total_def.setText(chara.getDef()+"");
			txt_spe.setText((chara.getSpe()-chara.getRol().getSpe_base())+"");
			txt_total_spe.setText(chara.getSpe()+"");
			txt_energy_ini.setText(chara.getEnergy_ini()+"");
			txt_energy_recover.setText(chara.getEnergy_recover()+"");
			com_rol.setValue(chara.getRol());
			updateTextSHP(); //para actualizar los pnts...
			com_att_1.setValue(chara.getA1());
			com_att_2.setValue(chara.getA2());
			com_att_3.setValue(chara.getA3());
			
			File f=new File(c.getPhoto_face());
			Image face= new Image("file:"+f.getPath());
			image_presentation.setImage(face);
			if(!chara.getPhoto_face().equals("src/main/resources/images/characters/face/cfdefault.jpg")) {
				txt_image_presentation.setText(c.getPhoto_face());
			}
			
			File f2=new File(c.getPhoto_card());
			Image card= new Image("file:"+f2.getPath());
			image_card.setImage(card);
			if(!chara.getPhoto_card().equals("src/main/resources/images/characters/card/ccdefault.jpg")) {
				txt_image_card.setText(c.getPhoto_card());
			}
			
			if(!c.getOst().matches("no_resource")) {
				txt_ost.setText(c.getOst());
			}
		}
	}
	
	@FXML
	public void add() {
		File image_presentation=new File(txt_image_presentation.getText());
		File image_card=new File(txt_image_card.getText());
		File ost= new File(txt_ost.getText());
		
		if(
				  txt_name.getText().matches("")
				||txt_universe.getText().matches("")
				||txt_energy_ini.getText().matches("")
				||txt_energy_recover.getText().matches("")
				||!txt_energy_ini.getText().matches("[0-9]+")
				||!txt_energy_recover.getText().matches("[0-9]+")
				||(
						!this.txt_image_presentation.getText().matches(".+\\.jpg")
						&&
						!this.txt_image_presentation.getText().matches(".+\\.png")
						&&
						!this.txt_image_presentation.getText().matches("")
				)
				||(
						!this.txt_image_card.getText().matches(".+\\.jpg")
						&&
						!this.txt_image_card.getText().matches(".+\\.png")
						&&
						!this.txt_image_card.getText().matches("")
				)
				||!this.txt_ost.getText().matches(".+\\.wav")&&!txt_ost.getText().matches("")) 
				
				{
			
			Alert alerterror=new Alert(AlertType.INFORMATION);
    		alerterror.setHeaderText(null);
    		alerterror.setTitle("Información");
    		String f="";
    		
    		if(
    				!this.txt_image_presentation.getText().matches(".+\\.jpg")
						&&
					!this.txt_image_presentation.getText().matches(".+\\.png")
						&&
					!this.txt_image_presentation.getText().matches("")) {
    			f+="\n -El archivo solicitado para la imagen del personaje no es ni png ni jpg.";
    		}
    		
    		if(
    				!this.txt_image_card.getText().matches(".+\\.jpg")
						&&
					!this.txt_image_card.getText().matches(".+\\.png")
						&&
					!this.txt_image_card.getText().matches("")) {
    			f+="\n -El archivo solicitado para la imagen de batalla no es ni png ni jpg.";
    		}
    		
    		if(txt_name.getText().matches("")) {
    			f+="\n -Debe rellenar el campo Nombre.";
    		}
    		
    		if(txt_universe.getText().matches("")) {
    			f+="\n -Debe rellenar el campo Universo.";
    		}
    		    		
    		if(this.txt_energy_ini.getText().matches("")) {
    			f+="\n -Debe rellenar el campo Energía Inicial.";
    		}
    		else if(!txt_energy_ini.getText().matches("[0-9]+")) {
    			f+="\n -Debe rellenar el campo Energía Inicial con un valor numérico.";
    		}
    		
    		if(this.txt_energy_recover.getText().matches("")) {
    			f+="\n -Debe rellenar el campo Recuperación.";
    		}
    		else if(!txt_energy_recover.getText().matches("[0-9]+")) {
    			f+="\n -Debe rellenar el campo Recuperación con un valor numérico.";
    		}
    		
    		if(!txt_ost.getText().matches(".+\\.wav")) {
    			f+="\n -El archivo solicitado para el ost no es un archivo de tipo wav.";
    		}
    		
    		Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setHeaderText(null);
    		alert.setTitle("Información");
    		alert.setContentText(f);
    		alert.showAndWait();
    		
    			
			
		}
		else if((!txt_image_presentation.getText().matches("")&&!image_presentation.exists())
				||(!txt_image_card.getText().matches("")&&!image_card.exists())
				||(!txt_ost.getText().matches("")&&!ost.exists())) {
			String f="";
			if(!txt_image_presentation.getText().matches("")&&!image_presentation.exists()) {
				f+=" -El recurso para la imagen de personaje seleccionado no existe.";
			}
			if(!txt_image_card.getText().matches("")&&!image_card.exists()) {
				f+=" -El recurso para la imagen de batalla seleccionado no existe.";
			}
			if(!txt_ost.getText().matches("")&&!ost.exists()){
				f+=" -El recurso para el ost seleccionado no existe.";
			}
			Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setHeaderText(null);
    		alert.setTitle("Información");
    		alert.setContentText(f);
    		alert.showAndWait();
		}
		
		else { //se añade...
	
			if(txt_image_presentation.getText().matches("")||txt_image_presentation.getText().matches("")||txt_ost.getText().matches("")) {
				String f="";
				if(txt_image_presentation.getText().matches("")) {
					f+=" -No hay imagen para el personaje.\n";
				}
				if(txt_image_card.getText().matches("")) {
					f+=" -No hay imagen para la batalla.\n";
				}
				if(txt_ost.getText().matches("")) {
					f+=" -El personaje no tendrá ost.\n";
				}
				f+=" ¿Desea crear este personaje usando recursos predeterminados?";
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setTitle("Confirmación");
				alert.setContentText(f);

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					generateCharacter();
				}
				else {
					//nada
				}
			}
			else {
				//generar directamente...
				generateCharacter();
			}
			
		}
	}
	
	@FXML
	public void cancel() {
		Stage stage = (Stage) this.btn_cancel.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void set_Presentation() {
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
	public void set_Card() {
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
	public void set_ost() {
		File file=null;
		FileChooser filechooser= new FileChooser();
		filechooser.setTitle("Selecionar archivo wav...");
		try {
			file=filechooser.showOpenDialog(null);
			if(file!=null&&file.getPath().matches(".+\\.wav")) {
				txt_ost.setText(file.getPath());
			}else { //extension incorrecta
				Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setHeaderText(null);
	    		alert.setTitle("Información");
	    		alert.setContentText("Formato incorrecto: Debe elegir archivo de tipo wav.");
	    		alert.showAndWait();
			}
		}catch (Exception e) {
			// TODO: handle exception;
		}	
	}

	@FXML
	public void updateRolStats() {
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
	public void updateTextSHP() {
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
	public void updateTextSATK() {
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
	public void updateTextSDEF() {
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
	public void updateTextSSPE() {
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
			txt_spe.setText(0+"");
			pnts=400-(0+
					Integer.parseInt(txt_hp.getText())+
					Integer.parseInt(txt_atk.getText())+
					Integer.parseInt(txt_def.getText()));
					
			lab_pnts.setText(pnts+"");
			txt_total_spe.setText(txt_rol_spe.getText());
		}
	}

	public void generateCharacter() {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setTitle("Confirmación");
		alert.setContentText(" ¿Desea generar y guardar el personaje que ha creado?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			if(c==null) { // insert
				c=new Character();

				c.setId(CharacterDAO.getNewId());
				c.setName(txt_name.getText());
				c.setUniverse(txt_universe.getText());
				c.setDescription(are_description.getText());
				c.setBand(com_band.getSelectionModel().getSelectedItem());
				c.setHp(Integer.parseInt(txt_total_hp.getText()));
				c.setEnergy_ini(Integer.parseInt(txt_energy_ini.getText()));
				c.setEnergy_recover(Integer.parseInt(txt_energy_recover.getText()));
				c.setAtk(Integer.parseInt(txt_total_atk.getText()));
				c.setDef(Integer.parseInt(txt_total_def.getText()));
				c.setSpe(Integer.parseInt(txt_total_spe.getText()));
				c.setA1(com_att_1.getSelectionModel().getSelectedItem());
				c.setA2(com_att_2.getSelectionModel().getSelectedItem());
				c.setA3(com_att_3.getSelectionModel().getSelectedItem());
				c.setRol(com_rol.getSelectionModel().getSelectedItem());
				c.setId_user(PrimaryController.ss.getId_user());
				
				//face
				if(!txt_image_presentation.getText().matches("")) {
					String realaddress= "src/main/resources/images/characters/face/cf"+c.getId()+".jpg";
					c.setPhoto_face(realaddress);
					FileUtilities.saveImage(txt_image_presentation.getText(),realaddress);
				}
				else {
					c.setPhoto_face("src/main/resources/images/characters/face/cfdefault.jpg");
				}
				
				//card
				if(!txt_image_card.getText().matches("")) {
					String realaddress= "src/main/resources/images/characters/card/cc"+c.getId()+".jpg";
					c.setPhoto_card(realaddress);
					FileUtilities.saveImage(txt_image_card.getText(),realaddress);
				}
				else {
					c.setPhoto_card("src/main/resources/images/characters/card/ccdefault.jpg");
				}
				
				//ost
				if(!txt_ost.getText().matches("")) { //con ost
					String realaddress= "src/main/resources/audio/characters/ost/co"+c.getId()+".wav";
					c.setOst(realaddress);
					FileUtilities.saveAudio(txt_ost.getText(),realaddress);
				}
				else { //sin ost
					c.setOst("no_resource");
				}
				
				CharacterDAO.guardar(c);
				
				dad.setTableAndDetailsInfo();
				
				Alert alert4=new Alert(AlertType.INFORMATION);
	    		alert4.setHeaderText(null);
	    		alert4.setTitle("Información");
	    		alert4.setContentText("¡Personaje guardado con éxito!");
	    		alert4.showAndWait();
	    		
	    		Stage stage = (Stage) this.btn_create.getScene().getWindow();
	    		stage.close();
			}
			else { //update
				Alert alert2 = new Alert(AlertType.CONFIRMATION);
				alert2.setHeaderText(null);
				alert2.setTitle("Sobreescribir");
				alert2.setContentText(" Estás intentando sobreescribir un personaje, ¿estás seguro de que quieres eliminar el personaje existente?");

				Optional<ButtonType> result2 = alert2.showAndWait();
				if (result2.get() == ButtonType.OK){
					c.setName(txt_name.getText());
					c.setUniverse(txt_universe.getText());
					c.setDescription(are_description.getText());
					c.setBand(com_band.getSelectionModel().getSelectedItem());
					c.setHp(Integer.parseInt(txt_total_hp.getText()));
					c.setEnergy_ini(Integer.parseInt(txt_energy_ini.getText()));
					c.setEnergy_recover(Integer.parseInt(txt_energy_recover.getText()));
					c.setAtk(Integer.parseInt(txt_total_atk.getText()));
					c.setDef(Integer.parseInt(txt_total_def.getText()));
					c.setSpe(Integer.parseInt(txt_total_spe.getText()));
					c.setA1(com_att_1.getSelectionModel().getSelectedItem());
					c.setA2(com_att_2.getSelectionModel().getSelectedItem());
					c.setA3(com_att_3.getSelectionModel().getSelectedItem());
					c.setRol(com_rol.getSelectionModel().getSelectedItem());

					//guardamos la info en funcion de si es default o no
					
					//face
					if(!txt_image_presentation.getText().equals(c.getPhoto_face())){
						
						if(txt_image_presentation.getText().matches("")) { //si es default...
							if(!c.getPhoto_face().matches("src/main/resources/images/characters/face/cfdefault.jpg")) { //no la tenia default
								FileUtilities.removeFile(c.getPhoto_face());
								c.setPhoto_face("src/main/resources/images/characters/face/cfdefault.jpg");
							}
							else { //antes la tenia default
								//no se hace nada...
							}			
						}
						else { //si es personalizada...
							if(c.getPhoto_face().matches("src/main/resources/images/characters/face/cfdefault.jpg")) { //la tenia default
								String realaddress= "src/main/resources/images/characters/face/cf"+c.getId()+".jpg";
								c.setPhoto_face(realaddress);
								FileUtilities.saveImage(txt_image_presentation.getText(),realaddress);
							}
							else { //no la tenia a default... hay que eliminar!!
								String realaddress= "src/main/resources/images/characters/face/cf"+c.getId()+".jpg";
								c.setPhoto_face(realaddress);
								FileUtilities.saveImage(txt_image_presentation.getText(),realaddress);
							}
						}
					}
					
					//card
					if(!txt_image_card.getText().equals(c.getPhoto_card())) {
						if(txt_image_card.getText().matches("")) { //si quiero default...
							if(!c.getPhoto_card().matches("src/main/resources/images/characters/card/ccdefault.jpg")) { //no la tenia default
								FileUtilities.removeFile(c.getPhoto_card());
								c.setPhoto_card("src/main/resources/images/characters/card/ccdefault.jpg");
							}
							else { //antes la tenia default
								//no se hace nada...
							}			
						}
						else { //si quiero personalizada...
							if(c.getPhoto_card().matches("src/main/resources/images/characters/card/ccdefault.jpg")) { //la tenia default
								String realaddress= "src/main/resources/images/characters/card/cc"+c.getId()+".jpg";
								c.setPhoto_card(realaddress);
								FileUtilities.saveImage(txt_image_card.getText(),realaddress);
							}
							else { //no la tenia a default... hay que eliminar!!
								String realaddress= "src/main/resources/images/characters/card/cc"+c.getId()+".jpg";
								c.setPhoto_card(realaddress);
								FileUtilities.saveImage(txt_image_card.getText(),realaddress);
							}
						}
					}
					
					//ost
					
					if(!txt_ost.getText().equals(c.getOst())) {
						if(txt_ost.getText().matches("")) { //si no quiero ost
							if(!c.getOst().matches("no_resource")) { //si tenia ost
								FileUtilities.removeFile(c.getOst());
								c.setOst("no_resource");
							}
							else { //antes no tenía ost tampoco...
								//no se hace nada...
							}	
						}
						else { //si quiero ost
							if(c.getOst().matches("no_resource")) { //la tenia default
								String realaddress= "src/main/resources/audio/characters/ost/co"+c.getId()+".wav";
								c.setOst(realaddress);
								FileUtilities.saveAudio(txt_ost.getText(),realaddress);
							}
							else { //no la tenia a default... hay que eliminar/sobreescrivir!!
								String realaddress= "src/main/resources/audio/characters/ost/co"+c.getId()+".wav";
								c.setOst(realaddress);
								FileUtilities.saveAudio(txt_ost.getText(),realaddress);
							}
						}
					}
					
					CharacterDAO.guardar(c);
												
					Alert alert4=new Alert(AlertType.INFORMATION);
		    		alert4.setHeaderText(null);
		    		alert4.setTitle("Información");
		    		alert4.setContentText("¡Personaje guardado con éxito!");
		    		alert4.showAndWait();	
		    		
		    		Stage stage = (Stage) this.btn_create.getScene().getWindow();
		    		stage.close();
				}
				else {
					//cancel...
				}
			}
		}
		else {
			
		}
		
		
	}
}
