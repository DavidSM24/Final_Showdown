package Final_Showdown;


import java.io.File;
import java.util.Optional;

import interfaces.IAttack_Generator_Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.P_Attack.Attack;
import models.P_Attack.AttackDAO;
import models.P_Attack.Extra;
import models.P_Attack.ExtraDAO;
import utils.FileUtilities;

public class Attack_Generator_Controller implements IAttack_Generator_Controller{
	
	//variables
	protected Character_Creation_Controller dad;
	protected Attack_Generator_Controller me;
	protected ObservableList<Extra> extras= ExtraDAO.extras;
	Attack a=null;
	
	//buttons
	@FXML
	protected Button btn_save;
	@FXML
	protected Button btn_save2;
	@FXML
	protected Button btn_exit;
	@FXML
	protected Button btn_import_image;
	@FXML
	protected Button btn_import_media;
	
	//texts
	@FXML
	protected TextField txt_name;
	@FXML
	protected TextField txt_power;
	@FXML
	protected TextField txt_cost;
	@FXML
	protected TextField txt_hit; 
	@FXML
	protected TextField txt_photo;
	@FXML
	protected TextField txt_media;
	@FXML
	protected TextArea are_des;
	
	//others
	@FXML
	protected ImageView photo;
	@FXML
	protected ComboBox<Extra> com_extras;
	@FXML
	protected ComboBox<String> com_animation;
	@FXML
	protected Tab tab1;
	@FXML
	protected Tab tab2;
	@FXML
	protected MediaView media_view;
	
	//methods
	
	public void setController(Character_Creation_Controller dad, Attack_Generator_Controller me, Attack a) {
		
		me=me;
		dad=dad;
		com_extras.setItems(extras);
		com_extras.setValue(extras.get(0));
		updateExtraDescription();
				
		ObservableList<String> animations=FXCollections.observableArrayList();
		animations.add("Base"); animations.add("Fuego");animations.add("Rayo");
		com_animation.setItems(animations);
		com_animation.setValue(animations.get(0));
		
		this.a=a;
		if(this.a!=null) {
			btn_save.setText("Editar");
			btn_save2.setText("Editar");
			
			txt_name.setText(a.getName());
			txt_power.setText(a.getPower()+"");
			txt_cost.setText(a.getCost()+"");
			txt_hit.setText(a.getHit_rate()+"");
			com_extras.setValue(a.getExtra());
			updateExtraDescription();
			txt_photo.setText(a.getPhoto());
			File f=new File("file:"+a.getPhoto());
			Image img=new Image(f.getPath());
			photo.setImage(img);
			if(a.getPhoto().matches("src/main/resources/images/attacks/adefault.jpg")) {
				txt_photo.setText("");
			}
			else {
				txt_photo.setText(a.getPhoto());
			}
			
			if(!a.getMedia().matches("no_resource")) {
				File filestring=new File(a.getMedia());
				Media media= new Media(filestring.toURI().toString());
				MediaPlayer player=new MediaPlayer(media);
				media_view.setMediaPlayer(player);
				txt_media.setText(a.getMedia());
			}
			
			com_animation.setValue(a.getAnimation());
		}
	}
	
	@FXML
	public void save() {
		File image=new File(txt_photo.getText());
		File aux=new File(txt_media.getText());
		
		if(
				this.txt_name.getText().equals("")
				||this.txt_power.getText().equals("")
				||this.txt_cost.getText().equals("")
				||this.txt_hit.getText().equals("")
				||!this.txt_power.getText().matches("[0-9]+")
				||!this.txt_cost.getText().matches("[0-9]+")
				||!this.txt_hit.getText().matches("[0-9]+")
				||(
						!this.txt_photo.getText().matches(".+\\.jpg")
						&&
						!this.txt_photo.getText().matches(".+\\.png")
						&&
						!this.txt_photo.getText().matches("")
				)
				||(
						!this.txt_media.getText().matches(".+\\.mp4")
						&&
						!this.txt_media.getText().matches(""))) {
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
    		
    		if(
    				!this.txt_media.getText().matches(".+\\.mp4")
    				&&
    				!this.txt_media.getText().matches("")
    				) {
    			f+="\n -El archivo solicitado para la media no es mp4.";
    		}
    		
    		if(this.txt_name.getText().matches("")) {
    			f+="\n -Debe rellenar el campo Nombre.";
    		}
    		if(this.txt_power.getText().matches("")) {
    			f+="\n -Debe rellenar el campo Potencia.";
    		}
    		else if(!txt_power.getText().matches("[0-9]+")) {
    			f+="\n -Debe rellenar el campo Potencia con un valor numérico.";
    		}
    		if(this.txt_cost.getText().matches("")) {
    			f+="\n -Debe rellenar el campo Coste.";
    		}
    		else if(!this.txt_cost.getText().matches("[0-9]+")) {
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
		else if(!txt_photo.getText().matches("")&&!image.exists()) {
			Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setHeaderText(null);
    		alert.setTitle("Información");
    		alert.setContentText(" -El recurso para la imagen seleccionado no existe.");
    		alert.showAndWait();
		}
		
		else if(!txt_media.getText().matches("")&&!aux.exists()) {
			Alert alert=new Alert(AlertType.INFORMATION);
    		alert.setHeaderText(null);
    		alert.setTitle("Información");
    		alert.setContentText(" -El recurso para la media seleccionado no existe.");
    		alert.showAndWait();
		}
		
		else { //se añade correctamente
			
			if(txt_photo.getText().matches("")) { //pregunto si generar con predet
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setTitle("Confirmación");
				alert.setContentText(" No hay ningún recurso seleccionado para la imagen. "
						+ "¿Desea crear este ataque con la imagen predeterminada?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){

					//primero saber si es insert o update comprobando id
					if(a==null) { //insert
						
						a=new Attack();

						a.setId(AttackDAO.getNewId());
						a.setName(txt_name.getText());
						a.setPower(Integer.parseInt(txt_power.getText()));
						a.setCost(Integer.parseInt(txt_cost.getText()));
						a.setHit_rate(Integer.parseInt(txt_hit.getText()));
						a.setExtra(com_extras.getSelectionModel().getSelectedItem());
						a.setAnimation(com_animation.getSelectionModel().getSelectedItem());
						a.setId_user(PrimaryController.ss.getId_user());
						
						//photo
						if(!txt_photo.getText().matches("")) {
							String realaddress= "src/main/resources/images/attacks/a"+a.getId()+".jpg";
							a.setPhoto(realaddress);
							FileUtilities.saveImage(txt_photo.getText(),realaddress);
						}
						else {
							a.setPhoto("src/main/resources/images/attacks/adefault.jpg");
						}
						
						
						//media
						if(!txt_media.getText().matches("")) { //con ost
							String realaddress= "src/main/resources/media/attacks/animations_media/am"+a.getId()+".mp4";
							a.setMedia(realaddress);
							FileUtilities.saveImage(txt_media.getText(),realaddress);
						}
						else { //sin media
							a.setMedia("no_resource");
						}
						
						AttackDAO.guardar(a);
						
						Alert alert4=new Alert(AlertType.INFORMATION);
			    		alert4.setHeaderText(null);
			    		alert4.setTitle("Información");
			    		alert4.setContentText("¡Ataque guardado con éxito!");
			    		alert4.showAndWait();
			    		
			    		Stage stage = (Stage) this.btn_save.getScene().getWindow();
			    		stage.close();
					}
					else { //update
						
						Alert alert3 = new Alert(AlertType.CONFIRMATION);
						alert3.setHeaderText(null);
						alert3.setTitle("Sobreescribir");
						alert3.setContentText(" Estás intentando sobreescribir un ataque, ¿estás seguro de que quieres eliminar el ataque existente?");
						
						Optional<ButtonType> result2 = alert3.showAndWait();
						if (result2.get() == ButtonType.OK){
							
							a.setName(txt_name.getText());
							a.setPower(Integer.parseInt(txt_power.getText()));
							a.setCost(Integer.parseInt(txt_cost.getText()));
							a.setHit_rate(Integer.parseInt(txt_hit.getText()));
							a.setExtra(com_extras.getSelectionModel().getSelectedItem());
							
							a.setAnimation(com_animation.getSelectionModel().getSelectedItem());
							
							//photo
							if(!txt_photo.getText().equals(a.getPhoto())){
								
								if(txt_photo.getText().matches("")) { //si es default...
									if(!a.getPhoto().matches("src/main/resources/images/attacks/adefault.jpg")) { //no la tenia default
										FileUtilities.removeFile(a.getPhoto());
										a.setPhoto("src/main/resources/images/attacks/adefault.jpg");
									}
									else { //antes la tenia default
										//no se hace nada...
									}			
								}
								else { //si es personalizada...
									if(a.getPhoto().matches("src/main/resources/images/attacks/adefault.jpg")) { //la tenia default
										String realaddress= "src/main/resources/images/attacks/a"+a.getId()+".jpg";
										a.setPhoto(realaddress);
										FileUtilities.saveImage(txt_photo.getText(),realaddress);
									}
									else { //no la tenia a default... hay que eliminar!!
										String realaddress= "src/main/resources/images/attacks/a"+a.getId()+".jpg";
										a.setPhoto(realaddress);
										FileUtilities.saveImage(txt_photo.getText(),realaddress);
									}
								}
							}
							
							//media
							if(!txt_media.getText().equals(a.getMedia())) {
								if(txt_media.getText().matches("")) { //si no quiero media
									if(!a.getMedia().matches("no_resource")) { //si tenia media, elimino
										FileUtilities.removeFile(a.getMedia());
										a.setMedia("no_resource");
										System.out.println("entro?");
									}
									else { //antes no tenía ost tampoco...
										if(!a.getMedia().matches("no_resource")) {
											FileUtilities.removeFile(a.getMedia());
											
										}
										a.setMedia("no_resource");
									}	
								}
								else { //si quiero ost
									if(a.getMedia().matches("no_resource")) { //la tenia default
										String realaddress= "src/main/resources/media/attacks/animations_media/am"+a.getId()+".mp4";
										a.setMedia(realaddress);
										FileUtilities.saveImage(txt_media.getText(),realaddress);
									}
									else { //no la tenia a default... hay que eliminar/sobreescrivir!!
										String realaddress= "src/main/resources/media/attacks/animations_media/am"+a.getId()+".mp4";
										a.setMedia(realaddress);
										FileUtilities.saveImage(txt_media.getText(),realaddress);
									}
								}
							}
							
							AttackDAO.guardar(a);

							Alert alert4=new Alert(AlertType.INFORMATION);
				    		alert4.setHeaderText(null);
				    		alert4.setTitle("Información");
				    		alert4.setContentText("¡Ataque guardado con éxito!");
				    		alert4.showAndWait();
				    		
				    		Stage stage = (Stage) this.btn_save.getScene().getWindow();
				    		stage.close();
				    		
				    		
						}
						else {
							
						}
						
						
					}
					
					
				} else {
					//no ocurre nada...
				}
			}
			else { //genero directamente
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setTitle("Confirmación");
				alert.setContentText(" ¿Desea generar y guardar el ataque que ha creado?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
				    
					if(a==null) {
						a=new Attack();
						a.setId(AttackDAO.getNewId());
						a.setName(txt_name.getText());
						a.setPower(Integer.parseInt(txt_power.getText()));
						a.setCost(Integer.parseInt(txt_cost.getText()));
						a.setHit_rate(Integer.parseInt(txt_hit.getText()));
						a.setExtra(com_extras.getSelectionModel().getSelectedItem());
						a.setAnimation(com_animation.getSelectionModel().getSelectedItem());
						a.setId_user(PrimaryController.ss.getId_user());
						
						//photo
						if(!txt_photo.getText().matches("")) {
							String realaddress= "src/main/resources/images/attacks/a"+a.getId()+".jpg";
							a.setPhoto(realaddress);
							FileUtilities.saveImage(txt_photo.getText(),realaddress);
						}
						else {
							a.setPhoto("src/main/resources/images/attacks/adefault.jpg");
						}
						
						//media
						if(!txt_media.getText().matches("")) { //con ost
							String realaddress= "src/main/resources/media/attacks/animations_media/am"+a.getId()+".mp4";
							a.setMedia(realaddress);
							FileUtilities.saveImage(txt_media.getText(),realaddress);
						}
						else { //sin media
							a.setMedia("no_resource");
						}
						AttackDAO.guardar(a);

						Alert alert4=new Alert(AlertType.INFORMATION);
			    		alert4.setHeaderText(null);
			    		alert4.setTitle("Información");
			    		alert4.setContentText("¡Ataque guardado con éxito!");
			    		alert4.showAndWait();
			    		
			    		Stage stage = (Stage) this.btn_save.getScene().getWindow();
			    		stage.close();
					}
					else { //update
						Alert alert2 = new Alert(AlertType.CONFIRMATION);
						alert2.setHeaderText(null);
						alert2.setTitle("Sobreescribir");
						alert2.setContentText(" Estás intentando sobreescribir un ataque, ¿estás seguro de que quieres eliminar el ataque existente?");

						Optional<ButtonType> result2 = alert2.showAndWait();
						if (result2.get() == ButtonType.OK){
							
							
							a.setName(txt_name.getText());
							a.setPower(Integer.parseInt(txt_power.getText()));
							a.setCost(Integer.parseInt(txt_cost.getText()));
							a.setHit_rate(Integer.parseInt(txt_hit.getText()));
							a.setExtra(com_extras.getSelectionModel().getSelectedItem());
							a.setAnimation(com_animation.getSelectionModel().getSelectedItem());
							
							//photo
							if(!txt_photo.getText().equals(a.getPhoto())){

								if(txt_photo.getText().matches("")) { //si es default...
									if(!a.getPhoto().matches("src/main/resources/images/attacks/adefault.jpg")) { //no la tenia default
										FileUtilities.removeFile(a.getPhoto());
										a.setPhoto("src/main/resources/images/attacks/adefault.jpg");
									}
									else { //antes la tenia default
										//no se hace nada...
									}			
								}
								else { //si es personalizada...
									if(a.getPhoto().matches("src/main/resources/images/attacks/adefault.jpg")) { //la tenia default
										String realaddress= "src/main/resources/images/attacks/a"+a.getId()+".jpg";
										a.setPhoto(realaddress);
										FileUtilities.saveImage(txt_photo.getText(),realaddress);
									}
									else { //no la tenia a default... hay que eliminar!!
										String realaddress= "src/main/resources/images/attacks/a"+a.getId()+".jpg";
										a.setPhoto(realaddress);
										FileUtilities.saveImage(txt_photo.getText(),realaddress);
									}
								}
							}
							
							//media
							
							if(!txt_media.getText().equals(a.getMedia())) {
								if(txt_media.getText().matches("")) { //si no quiero ost
									if(!a.getMedia().matches("no_resource")) { //si tenia ost
										FileUtilities.removeFile(a.getMedia());
										a.setMedia("no_resource");
									}
									else { //antes no tenía ost tampoco...
										if(!a.getMedia().matches("no_resource")) {
											FileUtilities.removeFile(a.getMedia());
											
										}
										a.setMedia("no_resource");
									}	
								}
								else { //si quiero ost
									if(a.getMedia().matches("no_resource")) { //la tenia default
										String realaddress= "src/main/resources/media/attacks/animations_media/am"+a.getId()+".mp4";
										a.setMedia(realaddress);
										FileUtilities.saveImage(txt_media.getText(),realaddress);
									}
									else { //no la tenia a default... hay que eliminar/sobreescrivir!!
										String realaddress= "src/main/resources/media/attacks/animations_media/am"+a.getId()+".mp4";
										a.setMedia(realaddress);
										FileUtilities.saveImage(txt_media.getText(),realaddress);
									}
								}
							}

							AttackDAO.guardar(a);
														
							Alert alert4=new Alert(AlertType.INFORMATION);
				    		alert4.setHeaderText(null);
				    		alert4.setTitle("Información");
				    		alert4.setContentText("¡Ataque guardado con éxito!");
				    		alert4.showAndWait();
				    		
				    		Stage stage = (Stage) this.btn_save.getScene().getWindow();
				    		stage.close();
						} else {
							//no ocurre nada...
						}
					}
					
				} else {
					//no ocurre nada...
				}
			}	
		}	
	}
	
	@FXML
	public void cancel() {
		Stage stage = (Stage) this.btn_exit.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void set_Attack_Image() {
		File file=null;
		FileChooser filechooser= new FileChooser();
		filechooser.setTitle("Selecionar imagen...");
		try {
			file=filechooser.showOpenDialog(null);
			if(file!=null&&file.getPath().matches(".+\\.png")||file.getPath().matches(".+\\.jpg")) {
				Image img= new Image ("file:\\"+file.getPath());
				photo.setImage(img);
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
	public void set_Media() {
		File file=null;
		FileChooser filechooser= new FileChooser();
		filechooser.setTitle("Selecionar media...");
		try {
			file=filechooser.showOpenDialog(null);
			if(file!=null&&file.getPath().matches(".+\\.mp4")) {
				Media media=new Media(file.toURI().toString());
				MediaPlayer mediaPlayer = new MediaPlayer(media);
				media_view.setMediaPlayer(mediaPlayer);
				txt_media.setText(file.getPath());
			}else { //extension incorrecta
				Alert alert=new Alert(AlertType.INFORMATION);
	    		alert.setHeaderText(null);
	    		alert.setTitle("Información");
	    		alert.setContentText("Formato incorrecto: Debe elegir un tipo de archivo mp4.");
	    		alert.showAndWait();
			}
		}catch (Exception e) {
			// TODO: handle exception;
		}		
	}
	
	@FXML
	public void updateExtraDescription() {
		are_des.setText(com_extras.getSelectionModel().getSelectedItem().getDescription());
	}
		
}
