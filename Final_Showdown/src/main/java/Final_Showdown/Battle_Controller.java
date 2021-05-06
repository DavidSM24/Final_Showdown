package Final_Showdown;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.P_Character.Fighter;

public class Battle_Controller {
	//variables
	private boolean firstTurn=true;
	private Fighter fighter1;
	private Fighter fighter2;
	private String terminal="";
	private int fighter1_priority;
	private int fighter2_priority;
	
	//buttons
	@FXML
	public Button prueba;
	@FXML
	public Button btn_P1_atk1;
	@FXML
	public Button btn_P1_atk2;
	@FXML
	public Button btn_P1_atk3;
	@FXML
	public Button btn_P1_evade;
	@FXML
	public Button btn_P1_block;
	
	//texts
	@FXML
	public Label lab_P1_name;
	@FXML
	public Label lab_P2_name;
	@FXML
	public Label lab_P1_hp;
	@FXML
	public Label lab_P2_hp;
	@FXML
	public Label lab_P1_ene;
	@FXML
	public Label lab_P2_ene;
	@FXML 
	public TextArea are_terminal;

	//others
	@FXML
	public GridPane gri_action_buttons;
	@FXML
	public ProgressBar pb1;
	@FXML
	public ProgressBar pb2;
	@FXML
	public ImageView img_card_P1;
	@FXML
	public ImageView img_card_P2;
	@FXML
	public ImageView img_animation_P1;
	@FXML
	public ImageView img_animation_P2;
	@FXML
	public ImageView img_attack_photo;
	
	//methods
	
	@FXML
	public void useAttack1() {
		fighter1.setAction(1);
		action(IAChoose());
	}
	
	

	@FXML
	public void useAttack2() {
		fighter1.setAction(2);
		action(IAChoose());
	}
	
	@FXML
	public void useAttack3() {
		fighter1.setAction(3);
		action(IAChoose());
	}
	
	@FXML
	public void useBlock() {
		fighter1.setAction(4);
		action(IAChoose());
	}
	
	@FXML
	public void useEvade() {
		fighter1.setAction(5);
		action(IAChoose());
	}
	
	private int IAChoose() { //IA elige un movimiento y se procede al combate...
		return 0;
	}
	
	private void action(int actionP2) { //se procede al combate
		switch(fighter1.getAction()) {
		case 4: fighter1.setBlock(true);
			
		case 5: fighter1.setEvade(true);
		}
		switch(fighter2.getAction()) {
		case 4: fighter2.setBlock(true);
		
		case 5: fighter2.setEvade(true);
		}
		
		double min_pri1=fighter1.getSpe()*0.85;
		double max_pri1=fighter1.getSpe()*1.15;
		fighter1_priority=(int) Math.floor(Math.random()*(max_pri1-min_pri1+1)+min_pri1);
		
		double min_pri2=fighter2.getSpe()*0.85;
		double max_pri2=fighter2.getSpe()*1.15;
		fighter2_priority=(int) Math.floor(Math.random()*(max_pri2-min_pri2+1)+min_pri2);
		System.out.println(fighter1_priority+" 1 vs, "+fighter2_priority+" 2");
	}
	
	@FXML
	public void setProgress() {
		this.lab_P1_hp.setText("100 %");
		this.lab_P2_hp.setText("100 %");
		pb1.setProgress(1.0);
		pb2.setProgress(1.0);
		pb1.setStyle("-fx-accent: green;");
		pb2.setStyle("-fx-accent: green;");;
		
	}
	
	protected void startBattle() {
		if(fighter1!=null&&fighter2!=null) {
			terminal+="¡El combate entre "+fighter1.getName()+" y "+fighter2.getName()+" ha comenzado!";
			are_terminal.setText(terminal);
			turn();
		}
		else {
			Alert alert4=new Alert(AlertType.ERROR);
    		alert4.setHeaderText(null);
    		alert4.setTitle("Error");
    		alert4.setContentText("¡No hay luchadores para combatir!");
    		alert4.showAndWait();	
		}
	}
	
	private void turn() { //se reinician los turnos...

		if(fighter1.getHp_current()>0&&fighter2.getHp_current()>0) { //seguir jugando
			if(firstTurn) {
				terminal+="\nLos contrincantes están listos para luchar..."
						+ "\nElige una opción.";
				are_terminal.setText(terminal);
				gri_action_buttons.setDisable(false);
				gri_action_buttons.setVisible(true);
				firstTurn=false;
			}
			else {
				fighter1.setEnergy(fighter1.getEnergy()+fighter1.getEnergy_recover());
				fighter2.setEnergy(fighter2.getEnergy()+fighter2.getEnergy_recover());

				terminal+="\nLos luchadores han recuperado algo de energía..."
						+ "\nElige una opción.";
				are_terminal.setText(terminal);
				gri_action_buttons.setDisable(false);
				gri_action_buttons.setVisible(true);
			}
			
			
		}
		else { //fin de la partida
			
		}
	}

	@FXML
	public void changeProgressPB1() {
		lab_P1_hp.setText("");
		Timer timer= new Timer();
		TimerTask tarea= new TimerTask() {
			@Override
			public void run() {
				if(pb1.getProgress()>=0.75) {	
					pb1.setStyle("-fx-accent: green;");
				}
				
				else if(pb1.getProgress()>=0.5) {
					pb1.setStyle("-fx-accent: yellow;");
				}
				
				else if(pb1.getProgress()>=0.25) {
					pb1.setStyle("-fx-accent: orange;");
				}
				
				else {
					pb1.setStyle("-fx-accent: red;");
				}
				
				pb1.setProgress(pb1.getProgress()-0.0005);
				
				if(pb1.getProgress()<=0.15) {	
					timer.cancel();
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							prueba();
							
						}
					});
				}
			}
		};	
		timer.schedule(tarea, 0, 1);
	}
	
	public void setController(Fighter fighter1, Fighter fighter2) {
		this.fighter1=fighter1;
		this.fighter2=fighter2;
		
		this.lab_P1_name.setText(fighter1.getName());
		this.lab_P2_name.setText(fighter2.getName());
		this.lab_P1_ene.setText(fighter1.getEnergy()+"");
		this.lab_P2_ene.setText(fighter2.getEnergy()+"");
		
		File f1=new File("file:"+fighter1.getPhoto_card());
		Image img1=new Image(f1.getPath());
		img_card_P1.setImage(img1);
		
		File f2=new File("file:"+fighter2.getPhoto_card());
		Image img2=new Image(f2.getPath());
		img_card_P2.setImage(img2);
		
		this.lab_P1_hp.setText("100 %");
		this.lab_P2_hp.setText("100 %");
		pb1.setProgress(1.0);
		pb2.setProgress(1.0);
		pb1.setStyle("-fx-accent: green;");
		pb2.setStyle("-fx-accent: green;");
		
		btn_P1_atk1.setText(fighter1.getA1().getName());
		btn_P1_atk2.setText(fighter1.getA2().getName());
		btn_P1_atk3.setText(fighter1.getA3().getName());
		
		gri_action_buttons.setDisable(true);
		gri_action_buttons.setVisible(false);
		
		fighter1.setEnergy(fighter1.getEnergy_ini());
		fighter2.setEnergy(fighter2.getEnergy_ini());
	}
	void prueba(){
		lab_P1_hp.setText(15+ " %");
	}
		
}
