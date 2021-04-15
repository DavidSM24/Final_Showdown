package Final_Showdown;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import models.P_Attack.AttackDAO;
import models.P_Character.Rol;
import models.P_Character.RolDAO;

/**
 * JavaFX App
 */
public class App extends Application {
	
	public void start(Stage stage) throws IOException {
		
		RolDAO repositoryRols= RolDAO.getInstance();
		repositoryRols.loadFile();
		AttackDAO repositoryAttacks=AttackDAO.getInstance();
		repositoryAttacks.loadFile();
		
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