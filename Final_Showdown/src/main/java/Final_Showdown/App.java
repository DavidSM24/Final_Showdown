package Final_Showdown;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.List;

import org.w3c.dom.CharacterData;

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

/**
 * JavaFX App
 */
public class App extends Application {
	
	public void start(Stage stage) throws IOException {
		
		ExtraDAO.loadAllExtras();
		RolDAO.loadAllRols();
		AttackDAO.loadAllAttacks();
		CharacterDAO.loadAllCharacters();

		Character c= new Character(2, "prueba", "", "", 0, 0, 0, 0, 0, 0, AttackDAO.attacks.get(0), AttackDAO.attacks.get(1), AttackDAO.attacks.get(2), RolDAO.roles.get(0), "", "");
		CharacterDAO.guardar(c);
		System.out.println(CharacterDAO.charas);
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("primary.fxml"));
		Parent root = loader.load();
		PrimaryController primary= loader.getController();
		primary.setController(primary);
		Scene scene= new Scene(root);
		Stage stage2= new Stage();
		stage2.setScene(scene);
		Image image= new Image("file:src/main/resources/images/icon_app.jpg");
		stage2.setTitle("Final Showdown");
		stage2.getIcons().add(image);
		stage2.initModality(Modality.APPLICATION_MODAL);
		
		stage2.show();
	};
	
    public static void main(String[] args) {
        launch();
        
    }

}