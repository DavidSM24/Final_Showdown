package Final_Showdown;

import java.io.File;
import java.util.Optional;

import P_Game.Scenary;
import P_Game.ScenaryDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.P_Attack.AttackDAO;
import models.P_Character.Character;
import models.P_Character.CharacterDAO;
import utils.FileUtilities;

public class Scenaries_Manage_Controller {
	
	//variables
	protected PrimaryController dad;
	protected Scenaries_Manage_Controller me;
	Scenary s=null;
	
	//buttons
	@FXML
	private Button btn_create;
	@FXML
	private Button btn_exit;
	@FXML
	private Button btn_set_photo;
	
	//texts
	@FXML
	private TextField txt_name;
	@FXML
	private TextField txt_photo;
	
	//other
	@FXML
	private ImageView img_photo;
	
	//metodos
	
	public void setController(PrimaryController dad, Scenaries_Manage_Controller me, Scenary s) {
		this.dad=dad;
		this.me=me;
		
		this.s=s;
		if(this.s!=null) {
			btn_create.setText("Editar");
			btn_create.setText("Editar");
			
			txt_name.setText(s.getName());
			txt_photo.setText(s.getResource());
			File f=new File("file:"+s.getResource());
			Image img=new Image(f.getPath());
			img_photo.setImage(img);
		}
	}
	
	@FXML
	public void set_Photo() {
		File file=null;
		FileChooser filechooser= new FileChooser();
		filechooser.setTitle("Selecionar imagen...");
		try {
			file=filechooser.showOpenDialog(null);
			if(file!=null&&file.getPath().matches(".+\\.png")||file.getPath().matches(".+\\.jpg")) {
				Image img= new Image ("file:\\"+file.getPath());
				img_photo.setImage(img);
				txt_photo.setText(file.getPath());
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
	public void add() {
		File image=new File(txt_photo.getText());
		
		if(txt_name.getText().matches("")||txt_photo.getText().matches("")) {
			Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setHeaderText(null);
    		alert.setTitle("Información");
    		String f="";
    		if(
    				!this.txt_photo.getText().matches(".+\\.jpg")
						&&
					!this.txt_photo.getText().matches(".+\\.png")
						&&
					!this.txt_photo.getText().matches("")) {
    			f+="\n -El archivo solicitado para la imagen no es ni png ni jpg.";
    		}
    		if(this.txt_name.getText().matches("")) {
    			f+="\n -Debe rellenar el campo Nombre.";
    		}
		}
		else if(!txt_photo.getText().matches("")&&!image.exists()) {
			Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setHeaderText(null);
    		alert.setTitle("Información");
    		alert.setContentText(" -El recurso para la imagen seleccionado no existe.");
    		alert.showAndWait();
		}
		
		else { //todo correcto
			
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setTitle("Confirmación");
			alert.setContentText(" ¿Desea generar y guardar el escenario que ha creado?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				if(s==null) { //insert
					s=new Scenary();
					s.setId(ScenaryDAO.getNewId());
					s.setName(txt_name.getText());
					s.setId_user(PrimaryController.ss.getId_user());
					
					//photo
					String realaddress= "src/main/resources/images/game/scenaries/sr"+s.getId()+".jpg";
					s.setResource(realaddress);
					FileUtilities.saveImage(txt_photo.getText(),realaddress);
					
					ScenaryDAO.guardar(s);
					
					dad.setTableAndDetailsInfo();
					
					Alert alert4=new Alert(AlertType.INFORMATION);
		    		alert4.setHeaderText(null);
		    		alert4.setTitle("Información");
		    		alert4.setContentText("¡Escenario guardado con éxito!");
		    		alert4.showAndWait();
		    		
		    		Stage stage = (Stage) this.btn_create.getScene().getWindow();
		    		stage.close();
				}
				else { //update
					Alert alert2 = new Alert(AlertType.CONFIRMATION);
					alert2.setHeaderText(null);
					alert2.setTitle("Sobreescribir");
					alert2.setContentText(" Estás intentando sobreescribir un escenario, ¿estás seguro de que quieres eliminar el escenario existente?");

					Optional<ButtonType> result2 = alert2.showAndWait();
					if (result2.get() == ButtonType.OK){
						s.setId(ScenaryDAO.getNewId());
						s.setName(txt_name.getText());
						
						if(!txt_photo.getText().equals(s.getResource())){
							String realaddress= "src/main/resources/images/game/scenaries/sr"+s.getId()+".jpg";
							s.setResource(realaddress);
							FileUtilities.saveImage(txt_photo.getText(),realaddress);
						}
						
						ScenaryDAO.guardar(s);
						
						dad.setTableAndDetailsInfo();
						
						Alert alert4=new Alert(AlertType.INFORMATION);
			    		alert4.setHeaderText(null);
			    		alert4.setTitle("Información");
			    		alert4.setContentText("¡Escenario guardado con éxito!");
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
				//cancel...
			}
		}
	}
	
	@FXML
	public void cancel() {
		Stage stage = (Stage) this.btn_exit.getScene().getWindow();
		stage.close();
	}
}
