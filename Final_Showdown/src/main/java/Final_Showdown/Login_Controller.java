package Final_Showdown;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;

import P_Game.ScenaryDAO;
import P_Game.Sesion;
import P_Game.SesionDAO;
import javafx.application.Platform;
import javafx.event.EventHandler;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.P_Attack.AttackDAO;
import models.P_Character.CharacterDAO;
import models.P_User.User;
import models.P_User.UserDAO;
import utils.MailSender;

public class Login_Controller {

	// buttons
	@FXML
	protected Button btn_login;

	// texts
	@FXML
	protected TextField txt_username;
	@FXML
	protected TextField txt_visible_Password;
	@FXML
	protected PasswordField txt_password;
	@FXML
	protected Text btn_txt_forgot;
	@FXML
	protected Text btn_txt_create_user;

	// other
	@FXML
	protected CheckBox che_showPassword;

	@FXML
	public void login() {

		boolean correct=true;
		User user = new User();
		user.setUsername(txt_username.getText());

		// comprobar campos no vacios
		if (txt_username.getText().matches("") || txt_password.getText().matches("")) { // algun campo vacío
			correct=false;
			String f = "";
			if (txt_username.getText().matches("")) {
				f = " -Debe rellenar el campo \"Nombre de Usuario\".\n";
			}
			if (txt_password.getText().matches("")) {
				f += " -Debe rellenar el campo \"Contraseña\".";
			}

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setTitle("Información");
			alert.setContentText(f);
			alert.showAndWait();
		}
		
		//comporobar usuario existe
		if(correct) {
			correct=false;
			
			UserDAO.loadAllUsers(); //refresca
			
			if (UserDAO.getUserByName(txt_username.getText()) == null) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Información");
				alert.setContentText(" El usuario que ha introducido no existe.\n Inténtelo de nuevo.");
				alert.showAndWait();
			}

			else {
				UserDAO.loadAllUsers();
				
				user = UserDAO.getUserByName(txt_username.getText());
				correct=true;
			}
			
			if(correct) {
				if(txt_visible_Password.getText().matches(user.getPassword())) { //todo correcto////////////////////////////////////////
					
					if(SesionDAO.isConnected(user)) { //está conectado////////////////////////////////////////
						
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText(null);
						alert.setTitle("Información");
						alert.setContentText(" Este usuario ya ha iniciado sesión.");
						alert.showAndWait();
					}
					
					else if(!user.getUsername().matches("root")&&!user.isConfirmed()) {
						System.out.println(user.isConfirmed());
						try {
							Alert alert = new Alert(AlertType.CONFIRMATION);
							alert.setHeaderText(null);
							alert.setTitle("Confirmación");
							alert.setContentText(" Para la utilización de este usuario se necesita\n la confirmación del correo electrónico.\n"
							+" Se enviará un mensaje a su correo, ¿Quire continuar?");

							Optional<ButtonType> result = alert.showAndWait();
							if (result.get() == ButtonType.OK){
									
								MailSender.sendMail(user.getMail(), "Código de verificación de cuenta.", "Su clave para verificar su cuenta es "+user.getUsercode());

								Alert alert2 = new Alert(AlertType.INFORMATION);
								alert2.setHeaderText(null);
								alert2.setTitle("Información");
								alert2.setContentText(" Mensaje enviado correctamente.\n");
								alert2.showAndWait();
								
								Stage stage = (Stage) this.btn_login.getScene().getWindow();
								stage.close();
								
								FXMLLoader loader = new FXMLLoader(getClass().getResource("mail_validator.fxml"));
								Parent root;
								root = loader.load();
								Mail_Validation_Controller mv= loader.getController();
								mv.setController(user.getUsercode(), user);
								Scene scene= new Scene(root);
								Stage stage2= new Stage();
								stage2.setScene(scene);
								Image image= new Image("file:src/main/resources/images/icons/icon_app.jpg");
								stage2.setTitle("Validación de Correo Electrónico");
								stage2.getIcons().add(image);
								stage2.setResizable(false);;
								stage2.initModality(Modality.APPLICATION_MODAL);							
								stage2.show();
							}
							
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					else { //logear...////////////////////////////////////////////////////////////////////////////////
						
						System.out.println("se logeo!");
						ScenaryDAO.loadScenaries(user.getId());
						AttackDAO.loadAttacks(user.getId());
						CharacterDAO.loadCharacters(user.getId());
						System.out.println(CharacterDAO.charas);
						
						Stage stage = (Stage) this.btn_login.getScene().getWindow();
						stage.close();
						
						try {
							FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
							Parent root = loader.load();
							PrimaryController primary= loader.getController();
							Timestamp ts=new Timestamp(System.currentTimeMillis());
							Sesion s=new Sesion(SesionDAO.getNewId(),user.getId(),ts);
							primary.setController(primary, s);
							primary.sendSession();
							Scene scene= new Scene(root);
							Stage stage2= new Stage();
							stage2.setScene(scene);
							Image image= new Image("file:src/main/resources/images/icons/icon_app.jpg");
							stage2.setTitle("Final Showdown");
							stage2.getIcons().add(image);
							stage2.setResizable(false);;
							stage2.initModality(Modality.APPLICATION_MODAL);
							stage2.setOnCloseRequest(new EventHandler<WindowEvent>() {
							       @Override
							       public void handle(WindowEvent e) {
							          try {
										
									} catch (Exception e2) {
										// TODO: handle exception
									}
							    	  Platform.exit();
							          System.exit(0);
							       }
							    });
							stage2.show();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}
				}
				else { //contraseña incorrecta
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setTitle("Información");
					alert.setContentText(" Contraseña incorrecta.\n Inténtelo de nuevo.");
					alert.showAndWait();
				}
			}
		}
	}

	@FXML
	public void create() {
		
		Stage stage = (Stage) this.btn_txt_create_user.getScene().getWindow();
		stage.close();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("user_generator.fxml"));
			Parent root;
			root = loader.load();
			User_Creator_Controller ucc=loader.getController();
			Scene scene= new Scene(root);
			Stage stage2= new Stage();
			stage2.setScene(scene);
			Image image= new Image("file:src/main/resources/images/icons/icon_app.jpg");
			stage2.setTitle("Creación de Usuario");
			stage2.getIcons().add(image);
			stage2.setResizable(false);;
			stage2.initModality(Modality.APPLICATION_MODAL);
			stage2.setOnCloseRequest(new EventHandler<WindowEvent>() {
			       @Override
			       public void handle(WindowEvent e) {
			          try {
						
					} catch (Exception e2) {
						// TODO: handle exception
					}
			    	  ucc.cancel();
			       }
			    });
			stage2.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@FXML
	public void changecreate() {
		btn_txt_create_user.setFill(Color.DODGERBLUE);
		btn_txt_create_user.setUnderline(true);
	}

	@FXML
	public void forgot() {
		
		if (txt_username.getText().matches("")) { 
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setTitle("Información");
			alert.setContentText(" Debe rellenar el campo \"Nombre de Usuario\" para usar esta función.\n");
			alert.showAndWait();
		}
		else {
			
			UserDAO.loadAllUsers();
			
			if(UserDAO.getUserByName(txt_username.getText()) != null) {
				User user=UserDAO.getUserByName(txt_username.getText());
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setHeaderText(null);
				alert.setTitle("Confirmación");
				alert.setContentText(" Se enviará un mensaje a su correo electrónico con \n la contraseña."
				+" ¿Quire continuar?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					
					try {
						MailSender.sendMail(user.getMail(), "Recuperación de contraseña para Final Showdown", "Su contraseña para iniciar sesión en Final Showdown es la siguiente: "+user.getPassword());
						
						Alert alert2 = new Alert(AlertType.INFORMATION);
						alert2.setHeaderText(null);
						alert2.setTitle("Información");
						alert2.setContentText(" Mensaje enviado correctamente.\n");
						alert2.showAndWait();
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
			else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Información");
				alert.setContentText(" El nombre de usuario introducido para la recuperación de contraseña no existe.\n");
				alert.showAndWait();
			}
		}
	}

	@FXML
	public void changeForgot() {
		btn_txt_forgot.setFill(Color.DODGERBLUE);
		btn_txt_forgot.setUnderline(true);
	}

	@FXML
	public void resetTexts() {
		btn_txt_create_user.setFill(Color.WHITE);
		btn_txt_forgot.setFill(Color.WHITE);

		btn_txt_create_user.setUnderline(false);
		btn_txt_forgot.setUnderline(false);
	}

	@FXML
	public void show_lock_Password() {

		if (che_showPassword.isSelected()) {
			txt_visible_Password.setVisible(true);
			txt_password.setVisible(false);
		} else {
			txt_password.setVisible(true);
			txt_visible_Password.setVisible(false);
		}
	}

	@FXML
	public void password_to_visiblepassword() {
		txt_visible_Password.setText(txt_password.getText());
	}

	@FXML
	public void visiblepassword_to_password() {
		txt_password.setText(txt_visible_Password.getText());
	}

}
