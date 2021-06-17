package Final_Showdown;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.P_User.User;
import models.P_User.UserDAO;
import utils.CodeGenerator;
import utils.MailSender;

public class User_Creator_Controller {

	//buttons
	@FXML
	private Button btn_create;
	@FXML
	private Button btn_cancel;
	
	//texts

	@FXML 
	private TextField txt_username;
	@FXML 
	private TextField txt_mail;
	@FXML 
	private TextField txt_password;
	@FXML 
	private TextField txt_repassword;
	@FXML
	private PasswordField pas_password;
	@FXML
	private PasswordField pas_repassword;
	
	//other
	@FXML
	CheckBox che_showPasswords;
	
	@FXML
	private void create() {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setTitle("Confirmación");
		alert.setContentText(" Para la creación del usuario se necesita\n la confirmación del correo electrónico.\n"
		+" ¿Quire continuar?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			UserDAO.loadAllUsers();
			
			if(txt_username.getText().matches("")
				||txt_mail.getText().matches("")
				||txt_password.getText().matches("")
				||txt_repassword.getText().matches("")) {
				
				String f="";
				if(txt_username.getText().matches("")) {
					f+= " -Debe rellenar el campo \"Nombre de Usuario\".\n";
				}
				if(txt_mail.getText().matches("")) {
					f+= " -Debe rellenar el campo \"Correo Electrónico\".\n";
				}
				if(txt_password.getText().matches("")) {
					f+= " -Debe rellenar el campo \"Contraseña\".\n";
				}
				if(txt_repassword.getText().matches("")) {
					f+= " -Debe rellenar el campo \"Confirmar Contraseña\".\n";
				}
				
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setHeaderText(null);
				alert2.setTitle("Información");
				alert2.setContentText(f);
				alert2.showAndWait();
				
			}
			
			else if(UserDAO.getUserByName(txt_username.getText())!=null
					||UserDAO.getUserByMail(txt_mail.getText())!=null
					||!txt_password.getText().matches(txt_repassword.getText())
					||!txt_mail.getText().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
				
				String f="";
				
				UserDAO.loadAllUsers();
				
				if(UserDAO.getUserByName(txt_username.getText())!=null) {
					f+=" -Nombre de usuario ya existente.\n";
				}
				
				if(UserDAO.getUserByMail(txt_mail.getText())!=null) {
					f+=" -Correo electrónico ya en uso.\n";
				}
				
				if(!txt_mail.getText().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
					f+=" -Correo electrónico no válido.\n";
				}
				
				if(!txt_password.getText().matches(txt_repassword.getText())) {
					f+=" -Las contraseñas no coinciden.\n";
				}
				
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setHeaderText(null);
				alert2.setTitle("Información");
				alert2.setContentText(f);
				alert2.showAndWait();
				
			}
			
			else {
				
					//creamos usuario temporal...
					
					String code=CodeGenerator.getCode();
					UserDAO.loadAllUsers();
					int newId=UserDAO.getNewId();
					User user=new User(newId,txt_mail.getText(),txt_username.getText(),txt_password.getText(),code,false);
					UserDAO.guardar(user);
					
					try {
						MailSender.sendMail(user.getMail(), "Código de verificación de cuenta.", "Su clave para verificar su cuenta es "+code);
						
						Alert alert2 = new Alert(AlertType.INFORMATION);
						alert2.setHeaderText(null);
						alert2.setTitle("Información");
						alert2.setContentText(" Se ha creado la cuenta correctamente.\n");
						alert2.showAndWait();
						
						Stage stage = (Stage) this.btn_create.getScene().getWindow();
						stage.close();
						
						try {
							FXMLLoader loader = new FXMLLoader(getClass().getResource("mail_validator.fxml"));
							Parent root;
							root = loader.load();
							Mail_Validation_Controller mv= loader.getController();
							mv.setController(code, user);
							Scene scene= new Scene(root);
							Stage stage2= new Stage();
							stage2.setScene(scene);
							Image image= new Image("file:src/main/resources/images/icons/icon_app.jpg");
							stage2.setTitle("Validación de Correo Electrónico");
							stage2.getIcons().add(image);
							stage2.setResizable(false);;
							stage2.initModality(Modality.APPLICATION_MODAL);
							
							stage2.show();
						
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (Exception e) {
						// TODO: handle exception
						Alert alert2 = new Alert(AlertType.INFORMATION);
						alert2.setHeaderText(null);
						alert2.setTitle("Información");
						alert2.setContentText(" Error al enviar el mensaje.\n");
						alert2.showAndWait();
					}
			}
		}
		
		
	}
	
	@FXML
	public void cancel() {
		Stage stage = (Stage) this.btn_cancel.getScene().getWindow();
		stage.close();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
			Parent root;
			root = loader.load();
			Scene scene= new Scene(root);
			Stage stage2= new Stage();
			stage2.setScene(scene);
			Image image= new Image("file:src/main/resources/images/icons/icon_app.jpg");
			stage2.setTitle("Inicio de Sesión");
			stage2.getIcons().add(image);
			stage2.setResizable(false);;
			stage2.initModality(Modality.APPLICATION_MODAL);
			
			stage2.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void show_lock_Password() {

		if (che_showPasswords.isSelected()) {
			txt_password.setVisible(true);
			pas_password.setVisible(false);
			txt_repassword.setVisible(true);
			pas_repassword.setVisible(false);
		} else {
			pas_password.setVisible(true);
			txt_password.setVisible(false);
			pas_repassword.setVisible(true);
			txt_repassword.setVisible(false);
		}
	}

	@FXML
	public void password_to_visiblepassword() {
		txt_password.setText(pas_password.getText());
	}

	@FXML
	public void visiblepassword_to_password() {
		pas_password.setText(txt_password.getText());
	}
	
	@FXML
	public void repassword_to_revisiblepassword() {
		txt_repassword.setText(pas_repassword.getText());
	}

	@FXML
	public void revisiblepassword_to_repassword() {
		pas_repassword.setText(txt_repassword.getText());
	}
}
