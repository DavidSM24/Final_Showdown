package Final_Showdown;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.w3c.dom.CharacterData;

import P_Game.ScenaryDAO;
import P_Game.SesionDAO;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import models.P_Attack.Attack;
import models.P_Attack.AttackDAO;
import models.P_Attack.Extra;
import models.P_Attack.ExtraDAO;
import models.P_Character.Character;
import models.P_Character.CharacterDAO;
import models.P_Character.Rol;
import models.P_Character.RolDAO;
import models.P_User.UserDAO;
import utils.Conexion;
import utils.FileUtilities;

/**
 * JavaFX App
 */
public class App extends Application {
	
	public void start(Stage stage) throws IOException {
		
		Conexion.loadServerInfo();
		
		SesionDAO.activateEvents(); //activa el planificador de eventos de la BBDD en caso de que esté desactivado.
									//PD, se desactiva solo cuando pasa mucho tiempo...
		UserDAO.loadAllUsers();
		ExtraDAO.loadAllExtras();
		RolDAO.loadAllRols();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
		Parent root = loader.load();
		Scene scene= new Scene(root);
		Stage stage2= new Stage();
		stage2.setScene(scene);
		Image image= new Image("file:src/main/resources/images/icons/icon_app.jpg");
		stage2.setTitle("Inicio de Sesión");
		stage2.getIcons().add(image);
		stage2.setResizable(false);;
		stage2.initModality(Modality.APPLICATION_MODAL);
		
		stage2.show();
	};
	
    public static void main(String[] args) {
        launch();
        
    }

}