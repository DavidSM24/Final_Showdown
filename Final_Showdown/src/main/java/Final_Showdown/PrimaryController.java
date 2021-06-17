package Final_Showdown;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import P_Game.Scenary;
import P_Game.ScenaryDAO;
import P_Game.Sesion;
import P_Game.SesionDAO;
import interfaces.IPrimaryController;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.P_Attack.Attack;
import models.P_Attack.AttackDAO;
import models.P_Character.CharacterDAO;
import models.P_Character.Fighter;
import models.P_Character.Character;

public class PrimaryController implements IPrimaryController{
	
	//variables
	protected PrimaryController me;
	protected ObservableList<Attack> attacks= AttackDAO.attacks;
	protected ObservableList<Character> charas=CharacterDAO.charas;
	protected ObservableList<Scenary> scenaries=ScenaryDAO.scenaries;
	protected ObservableList<Character> filter_charas=null;
	protected static Sesion ss=null;
	protected Character c=null;
	protected Attack a=null;
	protected Attack a_pred=null;
	protected Scenary s=null;
	protected Scenary s_pred=null;
	protected boolean running;
	protected File ost= new File("src/main/resources/audio/characters/ost/co16.wav");
	protected String ost_playing=null;
	protected Clip clip=null;
	protected boolean random_p1;
	protected boolean random_p2;
	protected boolean filteringAttacks=false;
	protected boolean multiplayer=false;
	
	//buttons		
	@FXML
	private Button btn_create_1;
	@FXML
	private Button btn_edit_1;
	@FXML
	private Button btn_delete_1;
	
	@FXML
	private Button btn_create_2;
	@FXML
	private Button btn_edit_2;
	@FXML
	private Button btn_delete_2;
	@FXML
	private Button btn_create_3;
	@FXML
	private Button btn_edit_3;
	@FXML
	private Button btn_delete_3;
	@FXML
	private Button btn_luchar;
	@FXML
	private Button btn_play;
	@FXML
	private Button btn_stop;
	
	//table
	@FXML
	protected TableView<Character> table_char;
	@FXML
	protected TableColumn<Character, String> col_name;
	
	@FXML
	protected TableView<Attack> table_attack;
	@FXML
	protected TableColumn<Attack, String> col_attack;
	
	@FXML
	protected TableView<Scenary> table_scenaries;
	@FXML
	protected TableColumn<Scenary, String> col_scenary;
    
	//texts
	@FXML
	protected TextField txt_filter;
	@FXML
	protected Label lab_name;
	@FXML
	protected Label lab_universe;
	@FXML
	protected Label lab_band;
	@FXML
	protected Label lab_rol;
	@FXML
	protected Label lab_power;	
	
	@FXML
	protected TextField txt_filter_2;
	@FXML
	protected Label lab_name_2;
	@FXML
	protected Label lab_power_2;
	@FXML
	protected Label lab_cost;
	@FXML
	protected Label lab_hit_rate;
	@FXML
	protected Label lab_extra_name;
	
	@FXML
	protected TextField txt_filter_3;
	@FXML
	protected Label lab_name_3;
	
	//others
	@FXML
	protected ImageView img_view_scenary;
	@FXML
	protected ImageView img_view_chara;
	@FXML
	protected ImageView img_view_attack;
	@FXML
	protected ImageView img_view_fighter1;
	@FXML
	protected ImageView img_view_fighter2;
	@FXML
	protected ImageView img_view_scenary_battle;
	@FXML
	protected ComboBox<Character> com_fighter1;
	@FXML
	protected ComboBox<Character> com_fighter2;
	@FXML
	protected ComboBox<Scenary> com_scenary;
	@FXML
	private CheckBox che_changeMode;
	@FXML
	private CheckBox che_random_1;
	@FXML
	private CheckBox che_random_2;
	@FXML
	private Pane pan_img_vs;
	
	@FXML
	private MediaView mediaview=new MediaView();
	
	//methods
	
	public void setController (PrimaryController me, Sesion ss) {
		this.ss=ss;
		this.me=me;
		this.a_pred=attacks.get(0);
		this.s_pred=scenaries.get(0);
		
		com_fighter1.setDisable(false);
		random_p1=false;
		com_fighter2.setDisable(false);
		random_p2=false;
		
		table_char.setItems(charas);
		table_attack.setItems(attacks);
		table_scenaries.setItems(scenaries);
		
		if(charas.size()>0) {
			com_fighter1.setItems(charas);
			com_fighter1.setValue(charas.get(0));
			com_fighter2.setItems(charas);
			com_fighter2.setValue(charas.get(0));
		}
		
		if(scenaries.size()>0) {
			com_scenary.setItems(scenaries);
			com_scenary.setValue(scenaries.get(0));
		}
		
		select_Character();
		select_Attack();
		select_Scenary();
		setTableAndDetailsInfo();
		updateFightersInfo();
		updateScenaryInfo();
		btn_play.setDisable(true);
		btn_stop.setDisable(true);
		
		
	}
	
	public void changeMode() {
		if(!multiplayer) {
			multiplayer=true;
		}
		else {
			multiplayer=false;
		}
	}
	
	@FXML
	public void battle() {
		if(charas.size()>0) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("battler.fxml"));
			Parent root;
			try {
				
				Fighter f1=new Fighter(com_fighter1.getSelectionModel().getSelectedItem());
				Fighter f2=new Fighter(com_fighter2.getSelectionModel().getSelectedItem());
				
				if(random_p1&&charas.size()!=1) {
					int random=(int)(Math.floor(Math.random()*charas.size()));
					f1=new Fighter(charas.get(random));
				}
				
				if(random_p2&&charas.size()!=1) {
					int random=(int)(Math.floor(Math.random()*charas.size()));
					f2=new Fighter(charas.get(random));
				}
				
				root = loader.load();
				Battle_Controller battler= loader.getController();
				Scene scene= new Scene(root);
				File f= new File("file:src/main/resources/images/battle_cursor.png");
				Image image = new Image(f.getPath());
				scene.setCursor(new ImageCursor(image));
				battler.setController(f1, f2, com_scenary.getSelectionModel().getSelectedItem(), multiplayer);
				battler.startBattle();

				Stage stage= new Stage();
				stage.setTitle("Final Showdown: "+f1.getName()+" VS "+f2.getName());
				Image image2= new Image("file:src/main/resources/images/icons/icon_app.jpg");
				stage.getIcons().add(image2);
				stage.setResizable(false);;
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setScene(scene);
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				       @Override
				       public void handle(WindowEvent e) {
				    	   if(battler.clip!=null&&battler.clip.isRunning()) {
				    		   Clip clip=battler.clip;
				    		   clip.stop();
				    	   }
				    	   if(battler.mediaPlayer!=null) {
				    		   MediaPlayer mediaPlayer=battler.mediaPlayer;
				    		   mediaPlayer.stop();
				    	   }
				    	   if(battler.timer!=null) {
				    		   battler.timer.cancel();
				    	   }
				       }
				    });
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			Alert alert2=new Alert(AlertType.INFORMATION);
    		alert2.setHeaderText(null);
    		alert2.setTitle("Información");
    		alert2.setContentText(" No hay personajes para luchar."
    				+ "\n Antes de jugar, crea algún personaje.");
    		alert2.showAndWait();
		}	
	}
	
	@FXML
	public void changeRandom_1(){
		if(che_random_1.isSelected()) {
			com_fighter1.setDisable(true);
			random_p1=true;
			updateFightersInfo();
		}
		else {
			com_fighter1.setDisable(false);
			random_p1=false;
			updateFightersInfo();			
		}
	}
	
	@FXML
	public void changeRandom_2(){
		if(che_random_2.isSelected()) {
			com_fighter2.setDisable(true);
			random_p2=true;
			updateFightersInfo();
		}
		else {
			com_fighter2.setDisable(false);
			random_p2=false;
			updateFightersInfo();
			
			
			
		}
	}
	
	@FXML
	public void play(){
		if(ost!=null) {
			AudioInputStream audioInputStream = null;
			try {
				
				if(!running&&c!=null&&!c.getOst().matches("no_resource")) {
					audioInputStream = AudioSystem.getAudioInputStream(ost);
					clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
					running=true;
					btn_play.setDisable(true);
					btn_stop.setDisable(false);
					ost_playing=c.getOst();
				}
				
			} 
			
			catch (UnsupportedAudioFileException | IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
        
	}
	
	@FXML
	public void stop() {
		if(running) {
			clip.stop();
			running=false;
			btn_stop.setDisable(true);
			ost_playing=null;
			if(c!=null&!c.getOst().matches("no_resource")) {
				btn_play.setDisable(false);
			}
		}
	}
		   	
	@FXML
	public void createCharacter() {
		
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("character_creation.fxml"));
			Parent root = loader.load();
			Character_Creation_Controller chara_generator= loader.getController();
			chara_generator.setController(me, chara_generator, null); //probando 
			chara_generator.image_presentation.setImage(new Image("file:src/main/resources/images/default_fighter.png"));
			chara_generator.image_card.setImage(new Image("file:src/main/resources/images/default_fighter.png"));
			chara_generator.updateRolStats();
			Scene scene= new Scene(root);
			Stage stage= new Stage();
			Image icon= new Image("file:src/main/resources/images/icons/icon_chara_creator.jpg");
			stage.getIcons().add(icon);
			stage.setTitle("Creador de Personajes");
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	@FXML
	public void editCharacter() {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("character_creation.fxml"));
			Parent root = loader.load();
			Character_Creation_Controller chara_generator= loader.getController();
			chara_generator.setController(me, chara_generator, table_char.getSelectionModel().getSelectedItem()); //probando 
			chara_generator.updateRolStats();
			Scene scene= new Scene(root);
			Stage stage= new Stage();
			Image icon= new Image("file:src/main/resources/images/icons/icon_chara_creator.jpg");
			stage.getIcons().add(icon);
			stage.setTitle("Creador de Personajes");
			stage.setResizable(false);;
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void deleteCharacter() {

		if(c!=null) {
			String sf="";							
			
			if(ost_playing!=null&&c.getOst().matches(ost_playing)) {
				sf+=" El ost que está sonando se detendrá.\n";
			}
			sf+=" ¿Seguro que desea eliminar este personaje?"
					+ "\n Esta acción no se podrá deshacer.";
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setTitle("Confirmación");
			alert.setContentText(sf);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				
				if(ost_playing!=null&&c.getOst().equals(ost_playing)) {
					stop();
				}
				if(c!=null&&com_fighter1!=null&&com_fighter1.getSelectionModel().getSelectedItem()!=null&&com_fighter1.getSelectionModel().getSelectedItem().equals(c)) {
					com_fighter1.setValue(null);
				}
				if(c!=null&&com_fighter1!=null&&com_fighter2.getSelectionModel().getSelectedItem()!=null&&com_fighter2.getSelectionModel().getSelectedItem().equals(c)) {
					com_fighter2.setValue(null);
				}
				
				CharacterDAO.eliminar(c);
				select_Character();
				Alert alert2=new Alert(AlertType.INFORMATION);
	    		alert2.setHeaderText(null);
	    		alert2.setTitle("Información");
	    		alert2.setContentText(" El personaje se ha eliminado correctamente.");
	    		alert2.showAndWait();
			}
		}
	}
	
	@FXML
	public void createAttack() {		
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("attack_creator.fxml"));
			Parent root;
			root = loader.load();
			Attack_Generator_Controller agc=loader.getController();
			agc.setController(null, agc, null);
			agc.photo.setImage(new Image("file:src/main/resources/images/attacks/adefault.jpg"));
			Scene scene= new Scene(root);
			Stage stage= new Stage();
			stage.getIcons().add(new Image("file:src/main/resources/images/icons/icon_attack_creator.png"));
			stage.setTitle("Generador de Ataques");
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@FXML
	public void editAttack() {		
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("attack_creator.fxml"));
			Parent root;
			root = loader.load();
			Attack_Generator_Controller agc=loader.getController();
			agc.setController(null, agc, table_attack.getSelectionModel().getSelectedItem());
			Scene scene= new Scene(root);
			Stage stage= new Stage();
			stage.getIcons().add(new Image("file:src/main/resources/images/icons/icon_attack_creator.png"));
			stage.setTitle("Generador de Ataques");
			stage.setResizable(false);;
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@FXML
	public void deleteAttack() {
		ObservableList<Character> charasWithThisAttack=CharacterDAO.getCharactersByAttack(a);
		if(charasWithThisAttack.size()>0) { //si existen char con ese atk
			//listar charas con ese atk y preguntar si setear a pred.
			String f="";
			for (Character c: charasWithThisAttack) {
				f+="-"+c.getName()+" ("+c.getUniverse()+")\n";
			}
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText("El ataque \""+a.getName()+"\" de los siguientes personajes se seteará a default:");
			alert.setTitle("Confirmación");
			alert.setContentText(f);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				for(Character c:charasWithThisAttack) {
					if(c.getA1().equals(a)) {
						c.setA1(attacks.get(0));
					}
					if(c.getA2().equals(a)) {
						c.setA2(attacks.get(0));
					}
					if(c.getA3().equals(a)) {
						c.setA3(attacks.get(0));
					}
					CharacterDAO.guardar(c);
				}
				AttackDAO.eliminar(a);
				Alert alert2=new Alert(AlertType.INFORMATION);
	    		alert2.setHeaderText(null);
	    		alert2.setTitle("Información");
	    		alert2.setContentText("¡Se han aplicado todos los cambios y se ha eliminado correctamente el ataque!");
	    		alert2.showAndWait();
			}
			
		}
		else { //si no...
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setTitle("Confirmación");
			alert.setContentText("¿Deseas eliminar este ataque?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				AttackDAO.eliminar(a);
				Alert alert2=new Alert(AlertType.INFORMATION);
	    		alert2.setHeaderText(null);
	    		alert2.setTitle("Información");
	    		alert2.setContentText("¡Se ha eliminado correctamente el ataque!");
	    		alert2.showAndWait();
			}
		}
	}
	
	@FXML
	public void createScenary() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("scenary_manager.fxml"));
			Parent root;
			root = loader.load();
			Scenaries_Manage_Controller smc=loader.getController();
			smc.setController(me, smc, null);
			Scene scene= new Scene(root);
			Stage stage= new Stage();
			stage.getIcons().add(new Image("file:src/main/resources/images/icons/icon_attack_creator.png"));
			stage.setTitle("Generador de Escenarios");
			stage.setResizable(false);;
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error?");
		}
	}
	
	@FXML
	public void editScenary() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("scenary_manager.fxml"));
			Parent root;
			root = loader.load();
			Scenaries_Manage_Controller smc=loader.getController();
			smc.setController(me, smc, table_scenaries.getSelectionModel().getSelectedItem());
			Scene scene= new Scene(root);
			Stage stage= new Stage();
			stage.getIcons().add(new Image("file:src/main/resources/images/icons/icon_attack_creator.png"));
			stage.setTitle("Generador de Escenarios");
			stage.setResizable(false);;
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@FXML
	public void deleteScenary() {
		if(s!=null) {

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setHeaderText(null);
			alert.setTitle("Confirmación");
			alert.setContentText(" ¿Seguro que desea eliminar este escenario?"
					+ "\n Esta acción no se podrá deshacer.");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				
				//setea el escenario a default si el seleccionado para la batalla es el que se va a eliminar... (implementar)
				/*
				if(c!=null&&com_fighter1!=null&&com_fighter1.getSelectionModel().getSelectedItem()!=null&&com_fighter1.getSelectionModel().getSelectedItem().equals(c)) {
					com_fighter1.setValue(null);
				}
				*/
				
				ScenaryDAO.eliminar(s);
				select_Scenary();
				Alert alert2=new Alert(AlertType.INFORMATION);
	    		alert2.setHeaderText(null);
	    		alert2.setTitle("Información");
	    		alert2.setContentText(" El escenario se ha eliminado correctamente.");
	    		alert2.showAndWait();
			}
		}
	}
	
	@FXML
	public void select_Character() { //actualizar esto cuando tenga tiempo por una variable power
		
		if(charas.size()>0) {
			if(table_char.getSelectionModel().getSelectedItem()!=null) {
				c= this.table_char.getSelectionModel().getSelectedItem();;
			}
			else {
				c=null;
				btn_delete_1.setDisable(true);
				btn_edit_1.setDisable(true);
			}

			if(c!=null) {//setea los valores del chara
				btn_delete_1.setDisable(false);
				btn_edit_1.setDisable(false);
				lab_name.setText(c.getName());
				lab_universe.setText(c.getUniverse());
				lab_band.setText(c.getBand());
				lab_rol.setText(c.getRol().getName());
				lab_power.setText(
						(c.getHp()
						+c.getAtk()
						+c.getDef()
						+c.getSpe())+"");
				
				File f=new File("file:"+c.getPhoto_face());
				Image img=new Image(f.getPath());
				img_view_chara.setImage(img);		
				
				if(!c.getOst().matches("no_resource")) {
					ost=new File(c.getOst());
					
					if(!running) {
						btn_play.setDisable(false);
					}
				}
				else {
					btn_play.setDisable(true);
				}				
			}
			else {// no hay seleccion --> todo a ""
				lab_name.setText("");
				lab_universe.setText("");
				lab_band.setText("");
				lab_rol.setText("");
				lab_power.setText("");
				img_view_chara.setImage(null);
				ost=null;
			}
		}
		else { //no hay charas
			c=null;
			lab_name.setText("");
			lab_universe.setText("");
			lab_band.setText("");
			lab_rol.setText("");
			lab_power.setText("");
			img_view_chara.setImage(null);
			ost=null;
			btn_edit_1.setDisable(true);
			btn_delete_1.setDisable(true);
			btn_play.setDisable(true);
		}
		
	}
	
	@FXML
	public void select_Attack() {
		if(table_attack.getSelectionModel().getSelectedItem()!=null) {
			a= this.table_attack.getSelectionModel().getSelectedItem();
		}
		else {
			a=null;
			btn_delete_2.setDisable(true);
			btn_edit_2.setDisable(true);
		}

		if(a!=null) {//setea los valores del atta
			if(a!=a_pred) {
				btn_delete_2.setDisable(false);
				btn_edit_2.setDisable(false);
			}
			else {
				btn_edit_2.setDisable(true);
				btn_delete_2.setDisable(true);
			}
			
			lab_name_2.setText(a.getName());
			lab_power_2.setText(a.getPower()+"");
			lab_cost.setText(a.getCost()+"");
			lab_hit_rate.setText(a.getHit_rate()+"");
			lab_extra_name.setText(a.getExtra().getName());
			
			File f=new File("file:"+a.getPhoto());
			Image img=new Image(f.getPath());
			img_view_attack.setImage(img);			
		}
		
		else {// no hay seleccion --> todo a ""
			lab_name_2.setText("");
			lab_power_2.setText("");
			lab_cost.setText("");
			lab_hit_rate.setText("");
			lab_extra_name.setText("");
			img_view_attack.setImage(null);
		}
	}
	
	public void select_Scenary() {
		if(table_scenaries.getSelectionModel().getSelectedItem()!=null) {
			s= this.table_scenaries.getSelectionModel().getSelectedItem();
		}
		else {
			s=null;
			btn_delete_3.setDisable(true);
			btn_edit_3.setDisable(true);
		}

		if(s!=null) {//setea los valores del scenary
			if(s!=s_pred) {
				btn_delete_3.setDisable(false);
				btn_edit_3.setDisable(false);
			}
			else {
				btn_edit_3.setDisable(true);
				btn_delete_3.setDisable(true);
			}
			
			lab_name_3.setText(s.getName());
			
			File f=new File("file:"+s.getResource());
			Image img=new Image(f.getPath());
			img_view_scenary.setImage(img);			
		}
		
		else {// no hay seleccion --> todo a ""
			lab_name_3.setText("");
			img_view_scenary.setImage(null);
		}
	}
	
	public void setTableAndDetailsInfo() {
		if (charas.size()>0) {
			col_name.setCellValueFactory(eachchara->{
	    		SimpleStringProperty v=new SimpleStringProperty();
	    		v.setValue(eachchara.getValue().getName());
	    		return v;
	    	});
		}

		if (attacks.size()>0) {
			col_attack.setCellValueFactory(eachattack->{	
				SimpleStringProperty v=new SimpleStringProperty();
	    		if(eachattack.getValue().equals(a_pred)){
	    			v.setValue(eachattack.getValue().getName()+" (Predeterminado)");
	    		}
	    		else {
	    			v.setValue(eachattack.getValue().getName());
	    		}
	    		return v;
	    	});

		}	
		
		if (scenaries.size()>0) {
			col_scenary.setCellValueFactory(eachscenary->{	
				SimpleStringProperty v=new SimpleStringProperty();
	    		if(eachscenary.getValue().equals(s_pred)){
	    			v.setValue(eachscenary.getValue().getName()+" (Predeterminado)");
	    		}
	    		else {
	    			v.setValue(eachscenary.getValue().getName());
	    		}
	    		return v;
	    	});

		}	
	}
	
	@FXML
	public void filter_Characters(){
		if(!txt_filter.getText().matches("")) {
			ObservableList<Character> filter= FXCollections.observableArrayList();
			filter=CharacterDAO.getCharactersByName(txt_filter.getText());
			table_char.setItems(filter);
			setTableAndDetailsInfo();
		}
		else {
			table_char.setItems(charas);
			setTableAndDetailsInfo();
		}
	}
	
	@FXML
	public void filter_Attacks() {
		if(!txt_filter_2.getText().matches("")) {
			ObservableList<Attack> filter= FXCollections.observableArrayList();
			filter=AttackDAO.getAttacksByName(txt_filter_2.getText());
			table_attack.setItems(filter);
			setTableAndDetailsInfo();
		}
		else {
			table_attack.setItems(attacks);
			setTableAndDetailsInfo();
		}
	}
	
	public void filter_Scenary() {
		if(!txt_filter_3.getText().matches("")) {
			ObservableList<Scenary> filter= FXCollections.observableArrayList();
			filter=ScenaryDAO.getScenariesByName(txt_filter_3.getText());
			table_scenaries.setItems(filter);
			setTableAndDetailsInfo();
		}
		else {
			table_scenaries.setItems(scenaries);
			setTableAndDetailsInfo();
		}
	}
	
	@FXML
	public void updateFightersInfo() {
		if(com_fighter1.getSelectionModel().getSelectedItem()==null&&charas.size()>0) {
			com_fighter1.setValue(charas.get(0));
		}
		
		if(com_fighter2.getSelectionModel().getSelectedItem()==null&&charas.size()>0) {
			com_fighter2.setValue(charas.get(0));
		}
		
		//fighter 1
		if(com_fighter1.getSelectionModel().getSelectedItem()!=null) {//setea los valores del fighter1
			if(!random_p1) {
				File f=new File("file:"+com_fighter1.getSelectionModel().getSelectedItem().getPhoto_face());
				Image img=new Image(f.getPath());
				img_view_fighter1.setImage(img);
			}
			else {
				File f=new File("file:src/main/resources/images/symbolrandom.png");
				Image img=new Image(f.getPath());
				img_view_fighter1.setImage(img);
			}
			
		}
		
		else {// no hay seleccion --> todo a ""
			img_view_fighter1.setImage(null);
		}
		
		//fighter 2
		if(com_fighter2.getSelectionModel().getSelectedItem()!=null) {//setea los valores del fighter2
			if(!random_p2) {
				File f=new File("file:"+com_fighter2.getSelectionModel().getSelectedItem().getPhoto_face());
				Image img=new Image(f.getPath());
				img_view_fighter2.setImage(img);
			}
			else {
				File f=new File("file:src/main/resources/images/symbolrandom.png");
				Image img=new Image(f.getPath());
				img_view_fighter2.setImage(img);
			}
		}
		else {// no hay seleccion --> todo a ""
			img_view_fighter2.setImage(null);
		}
	}
	
	@FXML
	public void updateScenaryInfo() {
		if(com_scenary.getSelectionModel().getSelectedItem()==null&&scenaries.size()>0) {
			com_scenary.setValue(scenaries.get(0));
		}
		
		//fighter 2
		if(com_scenary.getSelectionModel().getSelectedItem()!=null) {//setea los valores del escenario
			File f=new File("file:"+com_scenary.getSelectionModel().getSelectedItem().getResource());
			Image img=new Image(f.getPath());
			img_view_scenary_battle.setImage(img);
		}
		else {// no hay seleccion --> todo a ""
			File f=new File("file:"+scenaries.get(0).getResource());
			Image img=new Image(f.getPath());
			img_view_scenary_battle.setImage(img);
		}
	}
	
	public void sendSession() {
		System.out.println("entro a sendSession?");
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					try {
						Timestamp ts=new Timestamp(System.currentTimeMillis());
						ss.setTime(ts);
						SesionDAO.guardar(ss);
						Thread.sleep(1000);
						System.out.println("hola");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}
}
