package Final_Showdown;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.P_Character.Rol;
import models.P_Character.RolDAO;

public class PrimaryController {
	
	//variables
	protected PrimaryController me;
	RolDAO repoRol= RolDAO.getInstance();
	protected ObservableList<Rol> roles=repoRol.OL_getAllRols();
	
	//buttons	
	@FXML
	private Button btn_add;
	@FXML
	private Button btn_view;
	@FXML
	private Button btn_video;
	
	//table
	@FXML
	private TableView<Character> table_char;
	@FXML
	private TableColumn<Character, String> col_name;
    
	//other

	//methods
	@FXML
	private void add() {
		
		/*RolDAO rolRepository= RolDAO.getInstance();
		
		rolRepository.addNewRol(new Rol(0, "Guerrero", 2000, 250, 200, 75));
		rolRepository.addNewRol(new Rol(1, "Asesino", 1500, 300, 150, 150));
		rolRepository.addNewRol(new Rol(2, "Breaker",  1000, 400, 150, 100));
		rolRepository.addNewRol(new Rol(3, "Tanque",   3000, 200, 250, 50));
		
		System.out.println(rolRepository.getAllRols());
		
		rolRepository.saveFile();*/
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("character_creation.fxml"));
			Parent root = loader.load();
			Character_Creation_Controller chara_generator= loader.getController();
			chara_generator.setController(me, chara_generator,roles);
			chara_generator.image_presentation.setImage(new Image("file:src/main/resources/images/default_fighter.png"));
			chara_generator.image_card.setImage(new Image("file:src/main/resources/images/default_fighter.png"));
			chara_generator.updateRolStats();
			Scene scene= new Scene(root);
			Stage stage= new Stage();
			Image icon= new Image("file:src/main/resources/images/icon_chara_creator.jpg");
			stage.getIcons().add(icon);
			stage.setTitle("Creador de Personajes");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	@FXML
	private void view() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("image_panel.fxml"));
			Parent root;
			root = loader.load();
			
			Image_View image_controller= loader.getController();
			image_controller.setImage("file:\\C:\\Users\\david\\eclipse-workspace\\Final_Showdown\\src\\main\\resources\\images\\gokuo.png");
			
			Scene scene= new Scene(root);
			Stage stage= new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();
			
	
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected void setController (PrimaryController me) {
		this.me=me;
	}
}
