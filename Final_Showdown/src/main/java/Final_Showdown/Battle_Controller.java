package Final_Showdown;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import P_Game.Scenary;
import interfaces.IBattler_Controller;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import models.P_Attack.Attack;
import models.P_Character.Fighter;

public class Battle_Controller implements IBattler_Controller{
	//variables
	
	static long duration=0;
	
	static Timer timer=null;
	
	static MediaPlayer mediaPlayer=null;
	
	static Thread main=null;
	
	static int turn=0;
	
	boolean multiplayer=false;
	
	PrimaryController dad;
	private boolean firstTurn=true;
	private Fighter fighter1;
	private Fighter fighter2;
	private Attack a=null;
	private Attack a2=null;
	private int dmg=0;
	Random r=new Random();
	File effect=new File("src/main/resources/audio/effects/attack_effect.wav");
	File tear=new File("src/main/resources/audio/effects/tear.wav");
	File heal=new File("src/main/resources/audio/effects/heal.wav");
	File stun=new File("src/main/resources/audio/effects/stun.wav");
	File fail=new File("src/main/resources/audio/effects/fail.wav");
	File buff=new File("src/main/resources/audio/effects/buff.wav");
	File debuff=new File("src/main/resources/audio/effects/debuff.wav");
	File critic=new File("src/main/resources/audio/effects/critic.wav");
	File sound_animation;
	int frame_number=0;
	int limit_frame=0;
	double perToEdit=0.0;
	static Clip clip=null;
	
	//buttons
	@FXML
	protected Button btn_P1_atk1;
	@FXML
	protected Button btn_P1_atk2;
	@FXML
	protected Button btn_P1_atk3;
	@FXML
	protected Button btn_P1_evade;
	@FXML
	protected Button btn_P1_block;
	
	@FXML
	protected Button btn_P2_atk1;
	@FXML
	protected Button btn_P2_atk2;
	@FXML
	protected Button btn_P2_atk3;
	@FXML
	protected Button btn_P2_evade;
	@FXML
	protected Button btn_P2_block;
	
	@FXML
	protected Button btn_surrender;
	
	//texts
	@FXML
	protected Label lab_P1_name;
	@FXML
	protected Label lab_P2_name;
	@FXML
	protected Label lab_P1_hp;
	@FXML
	protected Label lab_P2_hp;
	@FXML
	protected Label lab_P1_ene;
	@FXML
	protected Label lab_P2_ene;
	@FXML 
	protected TextArea are_terminal;
	@FXML
	protected TextArea are_Info;
	@FXML
	protected TextArea are_info_p1;
	@FXML
	protected TextArea are_info_p2;


	//others
	@FXML
	public GridPane gri_action_buttons;
	@FXML
	public GridPane gri_action_P2_buttons;
	@FXML
	public ProgressBar pb1;
	@FXML
	public ProgressBar pb2;
	@FXML
	public ImageView img_face_P1;
	@FXML
	public ImageView img_face_P2;
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
	@FXML
	public ImageView img_scenary;
	@FXML
	public Pane pan_blacK_effect;
	@FXML
	Pane pan_serpentBack;
	@FXML 
	Pane pan_shadow_effect;
	@FXML
	Pane pan_info_P1;
	@FXML 
	Pane pan_info_P2;
	@FXML 
	Pane pan_video;
	@FXML
	MediaView mediaview = new MediaView(null);
	
	//methods
	
	public void setController(Fighter fighter1, Fighter fighter2, Scenary scenary, boolean multiplayer) {	
		
		this.multiplayer=multiplayer;		
		
		File f0=new File("file:"+scenary.getResource());
		Image img0=new Image(f0.getPath());
		img_scenary.setImage(img0);
		
		this.dad=dad;
		btn_surrender.setVisible(false);
		pan_blacK_effect.setVisible(false);
		pan_serpentBack.setVisible(false);
		pan_video.setVisible(false);
		this.fighter1=fighter1;
		this.fighter2=fighter2;
		
		this.lab_P1_name.setText(fighter1.getName());
		this.lab_P2_name.setText(fighter2.getName());
		this.lab_P1_ene.setText(fighter1.getEnergy_ini()+"");
		this.lab_P2_ene.setText(fighter2.getEnergy_ini()+"");
		
		File f1=new File("file:"+fighter1.getPhoto_card());
		Image img1=new Image(f1.getPath());
		img_card_P1.setImage(img1);
		
		File f2=new File("file:"+fighter2.getPhoto_card());
		Image img2=new Image(f2.getPath());
		img_card_P2.setImage(img2);
		
		File f3=new File("file:"+fighter1.getPhoto_face());
		Image img3=new Image(f3.getPath());
		img_face_P1.setImage(img3);
		System.out.println(f3.getPath());
		
		File f4=new File("file:"+fighter2.getPhoto_face());
		Image img4=new Image(f4.getPath());
		img_face_P2.setImage(img4);
		
		this.lab_P1_hp.setText("100 %");
		this.lab_P2_hp.setText("100 %");
		pb1.setProgress(1.0);
		pb2.setProgress(1.0);
		pb1.setStyle("-fx-accent: green;");
		pb2.setStyle("-fx-accent: green;");
		
		btn_P1_atk1.setText(fighter1.getA1().getName());
		btn_P1_atk2.setText(fighter1.getA2().getName());
		btn_P1_atk3.setText(fighter1.getA3().getName());
		
		if(multiplayer) {
			btn_P2_atk1.setText(fighter2.getA1().getName());
			btn_P2_atk2.setText(fighter2.getA2().getName());
			btn_P2_atk3.setText(fighter2.getA3().getName());
		}
		
		btn_P1_block.setTextFill(Color.WHITE);
		btn_P1_evade.setTextFill(Color.WHITE);
		btn_P1_atk1.setTextFill(Color.WHITE);
		btn_P1_atk2.setTextFill(Color.WHITE);
		btn_P1_atk3.setTextFill(Color.WHITE);
		
		are_terminal.setStyle("-fx-text-fill:red");
		
		gri_action_buttons.setDisable(true);
		gri_action_buttons.setVisible(false);
		
		gri_action_P2_buttons.setVisible(false);
		
		are_Info.setVisible(false);
		pan_info_P1.setVisible(false);
		pan_info_P2.setVisible(false);
		
		if(!fighter2.getOst().matches("no_resource")) {
			File ost=new File(fighter2.getOst());
			clip=null;
			if(ost!=null) {
				AudioInputStream audioInputStream = null;
				try {
					
					audioInputStream = AudioSystem.getAudioInputStream(ost);
					clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					
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
	}
	
	public void startBattle() {
		if(fighter1!=null&&fighter2!=null) {
			are_terminal.setText("¡El combate entre "+fighter1.getName()+" y "+fighter2.getName()+" ha comenzado!");
			are_terminal.end();
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
	
	public void turn() { //se reinician los turnos...;
		System.out.println(turn);
		turn++;
		
		if(fighter1.getHp_current()>0&&fighter2.getHp_current()>0) { //seguir jugando
			if(firstTurn) {
				fighter1.setEnergy(fighter1.getEnergy_ini());
				fighter2.setEnergy(fighter2.getEnergy_ini());
		
				are_terminal.setText(are_terminal.getText()+"\nLos contrincantes están listos para luchar..."
						+ "\n\n--------------------Elige una opción.---------------------");
				are_terminal.end();
				
				if(fighter1.getEnergy()>=fighter1.getA1().getCost()) {
					btn_P1_atk1.setDisable(false);
				}
				else {
					btn_P1_atk1.setDisable(true);
				}
				
				if(fighter1.getEnergy()>=fighter1.getA2().getCost()) {
					btn_P1_atk2.setDisable(false);
				}
				else {
					btn_P1_atk2.setDisable(true);
				}
				if(fighter1.getEnergy()>=fighter1.getA3().getCost()) {
					btn_P1_atk3.setDisable(false);
				}
				else {
					btn_P1_atk3.setDisable(true);
				}
				
				if(multiplayer) {
					gri_action_P2_buttons.setDisable(false);
					
					if(fighter2.getEnergy()>=fighter2.getA1().getCost()) {
						btn_P2_atk1.setDisable(false);
					}
					else {
						btn_P2_atk1.setDisable(true);
					}
					
					if(fighter2.getEnergy()>=fighter2.getA2().getCost()) {
						btn_P2_atk2.setDisable(false);
					}
					else {
						btn_P2_atk2.setDisable(true);
					}
					if(fighter2.getEnergy()>=fighter2.getA3().getCost()) {
						btn_P2_atk3.setDisable(false);
					}
					else {
						btn_P2_atk3.setDisable(true);
					}
				}
				gri_action_buttons.setDisable(false);
				gri_action_buttons.setVisible(true);
				btn_surrender.setVisible(true);
				firstTurn=false;
			}
			else { //2º o mas turno
				
				//recuperar energía
				fighter1.setBlock(false);
				fighter1.setEvade(false);
				fighter2.setBlock(false);
				fighter2.setEvade(false);
				
				fighter1.setEnergy(fighter1.getEnergy()+fighter1.getEnergy_recover());
				fighter2.setEnergy(fighter2.getEnergy()+fighter2.getEnergy_recover());
				lab_P1_ene.setText(fighter1.getEnergy()+"");
				lab_P2_ene.setText(fighter2.getEnergy()+"");
				are_terminal.setText(are_terminal.getText()+"\n\nLos luchadores han recuperado algo de energía..."
						+ "\n\n--------------------Elige una opción.---------------------");
				are_terminal.end();
				
				//reestablecer botones
				if(fighter1.getEnergy()>=fighter1.getA1().getCost()) {
					btn_P1_atk1.setDisable(false);
				}
				else {
					btn_P1_atk1.setDisable(true);
				}
				
				if(fighter1.getEnergy()>=fighter1.getA2().getCost()) {
					btn_P1_atk2.setDisable(false);
				}
				else {
					btn_P1_atk2.setDisable(true);
				}
				
				if(fighter1.getEnergy()>=fighter1.getA3().getCost()) {
					btn_P1_atk3.setDisable(false);
				}
				else {
					btn_P1_atk3.setDisable(true);
				}
				
				if(multiplayer) {
					
					if(fighter2.getEnergy()>=fighter2.getA1().getCost()) {
						btn_P2_atk1.setDisable(false);
					}
					else {
						btn_P2_atk1.setDisable(true);
					}
					
					if(fighter2.getEnergy()>=fighter2.getA2().getCost()) {
						btn_P2_atk2.setDisable(false);
					}
					else {
						btn_P2_atk2.setDisable(true);
					}
					if(fighter2.getEnergy()>=fighter2.getA3().getCost()) {
						btn_P2_atk3.setDisable(false);
					}
					else {
						btn_P2_atk3.setDisable(true);
					}
				}
				
				gri_action_buttons.setDisable(false);
				gri_action_buttons.setVisible(true);
				btn_surrender.setVisible(true);
			}
			
			
		}
		else { //fin de la partida
			
		}
	}
	
	@FXML
	public void useAttack1() {
		fighter1.setAction(1);
		a=fighter1.getA1();
		if(multiplayer) {
			gri_action_buttons.setVisible(false);
			
			gri_action_P2_buttons.setVisible(true);
		}
		else {
			action(IAChoose());
		}
		
	}
	
	@FXML
	public void useAttack2() {
		fighter1.setAction(2);
		a=fighter1.getA2();		
		if(multiplayer) {
			gri_action_buttons.setVisible(false);
			
			gri_action_P2_buttons.setVisible(true);
		}
		else {
			action(IAChoose());
		}
	}
	
	@FXML
	public void useAttack3() {
		fighter1.setAction(3);
		a=fighter1.getA3();		
		if(multiplayer) {
			gri_action_buttons.setVisible(false);
			
			gri_action_P2_buttons.setVisible(true);
		}
		else {
			action(IAChoose());
		}
	}
	
	@FXML
	public void useBlock() {
		fighter1.setAction(4);
		
		if(multiplayer) {
			gri_action_buttons.setVisible(false);
			
			gri_action_P2_buttons.setVisible(true);
		}
		else {
			action(IAChoose());
		}
	}
	
	@FXML
	public void useEvade() {
		fighter1.setAction(5);
		if(multiplayer) {
			gri_action_buttons.setVisible(false);
			
			gri_action_P2_buttons.setVisible(true);
		}
		else {
			action(IAChoose());
		}
	}
	
	@FXML
	public void P2_use_Attack1() {
		fighter2.setAction(1);
		a2=fighter2.getA1();
		
		action(1);
	}
	
	@FXML
	public void P2_use_Attack2() {
		fighter2.setAction(2);
		a2=fighter2.getA2();
		action(2);
	}
	
	@FXML
	public void P2_use_Attack3() {
		fighter2.setAction(3);
		a2=fighter2.getA3();
		action(3);
	}
	
	@FXML
	public void P2_use_Block() {
		fighter2.setAction(4);
		action(4);
	}

	@FXML
	public void P2_use_Evade() {
		fighter2.setAction(5);
		action(5);
	}
	
	public int IAChoose() { //IA elige un movimiento y se procede al combate...
		//quiere curarse...
		if(pb2.getProgress()<=0.35&&
				((fighter2.getA3().getExtra().getId()>0&&fighter2.getA3().getExtra().getId()<4)
				|(fighter2.getA2().getExtra().getId()>0&&fighter2.getA2().getExtra().getId()<4)
				|(fighter2.getA1().getExtra().getId()>0&&fighter2.getA1().getExtra().getId()<4)
				)) { 
			
			if(fighter2.getEnergy()>=fighter2.getA3().getCost() //usa el 3 con heal
					&&(fighter2.getA3().getExtra().getId()>0&&fighter2.getA3().getExtra().getId()<4)) {
				fighter2.setAction(3);
				a2=fighter2.getA3(); 
				return 3;
			}
			if(fighter2.getEnergy()>=fighter2.getA2().getCost() //usa el 2 con heal
					&&(fighter2.getA2().getExtra().getId()>0&&fighter2.getA2().getExtra().getId()<4)) {
				fighter2.setAction(2);
				a2=fighter2.getA2();
				return 2;
			}
			if(fighter2.getEnergy()>=fighter2.getA1().getCost() //usa el 1 con heal
					&&(fighter2.getA1().getExtra().getId()>0&&fighter2.getA1().getExtra().getId()<4)) {
				fighter2.setAction(1);
				a2=fighter2.getA1(); 
				return 1;
			}
				
		}
		//no puede curarse...
		
		if(fighter2.getEnergy()<fighter2.getA3().getCost()) { //prio ahorra energy
			int prob=(int)(Math.floor(Math.random()*100));
			if(prob<=50) { //usa block o evasion
				prob=(int)(Math.floor(Math.random()*100));
				if(prob<=50) { //usa block
					fighter2.setAction(4);
					a2=fighter2.getA1(); 
					return 4;

				}
				else { //usa evade
					fighter2.setAction(5);
					a2=fighter2.getA1(); 
					return 5;
				}
			}
			else {// usa un ataque
				prob=(int)(Math.floor(Math.random()*100));
				if(prob>=75&&fighter2.getEnergy()>=fighter2.getA3().getCost()) {
					fighter2.setAction(3);
					a2=fighter2.getA3(); 
					return 3;
				}
				else if(prob>=50&&fighter2.getEnergy()>=fighter2.getA2().getCost()) {
					fighter2.setAction(2);
					a2=fighter2.getA2(); 
					return 2;
				}
				else if(fighter2.getEnergy()>=fighter2.getA1().getCost()){
					fighter2.setAction(1);
					a2=fighter2.getA1(); 
					return 1;
				}
				else {
					prob=(int)(Math.floor(Math.random()*100));
					if(prob<=50) { //usa block
						fighter2.setAction(4);
						a2=fighter2.getA1(); 
						return 4;

					}
					else { //usa evade
						fighter2.setAction(5);
						a2=fighter2.getA1(); 
						return 5;
					}
				}
			}
		}
		
		else { //prio ofensivo gasta energy
			fighter2.setAction(3);
			a2=fighter2.getA3();
			return 3;
		}		
	}
	
	public void action(int actionP2) { //se procede al combate
		btn_surrender.setVisible(false);
		
		gri_action_buttons.setVisible(false);
		gri_action_P2_buttons.setVisible(false);
		
		closeInfos();
		
		switch(fighter1.getAction()) {
		case 4: 
			are_terminal.setText(are_terminal.getText()+"\n¡"+fighter1.getName()+" se prepara para bloquear el ataque!");
			are_terminal.end();
			fighter1.setBlock(true);
			break;
			
		case 5: 
			are_terminal.setText(are_terminal.getText()+"\n¡"+fighter1.getName()+" se prepara para evadir el ataque!");
			are_terminal.end();
			fighter1.setEvade(true);
			break;
		}
		switch(fighter2.getAction()) {
		case 4: 
			are_terminal.setText(are_terminal.getText()+"\n¡"+fighter2.getName()+" se prepara para bloquear el ataque!");
			are_terminal.end();
			fighter2.setBlock(true);
			break;
		
		case 5: 
			are_terminal.setText(are_terminal.getText()+"\n¡"+fighter2.getName()+" se prepara para evadir el ataque!");
			are_terminal.end();
			fighter2.setEvade(true);
			break;
		}
		
		//calcular % prioridades
		try {
			fighter1.setPriority((int) (r.nextInt((int) (fighter1.getSpe()*1.15-fighter1.getSpe()*0.85))+fighter1.getSpe()*0.85)); 
		} catch (Exception e) {
			fighter1.setPriority(1);
		}
		try {
			fighter2.setPriority((int) (r.nextInt((int) (fighter2.getSpe()*1.15-fighter2.getSpe()*0.85))+fighter2.getSpe()*0.85));
		} catch (Exception e) {
			fighter2.setPriority(1);
		}

		if(!(fighter1.getPriority()==fighter2.getPriority())) {
			if(fighter1.getSpe()>fighter2.getSpe()) {
				fighter1.setPriority(fighter1.getPriority()+1);
			}
			else {
				fighter2.setPriority(fighter2.getPriority()+1);
			}
		}
		else {
			int ran=(int)(Math.floor(Math.random())*100);
			if(ran>50) {
				fighter1.setPriority(fighter1.getPriority()+1);
			}
			else {
				fighter2.setPriority(fighter2.getPriority()+1);
			}
		}
		
		//turnos...
		if(fighter1.getPriority()>fighter2.getPriority()) { //f1 ataca antes
			p1Attack();
		}
		else { //f2 ataca antes
			p2Attack();
		}	
	}
		
	public void p1Attack() {
		dmg=0;
		if(fighter1.getAction()>0&&fighter1.getAction()<4) { //ataca
			
			//comentar y restar a.cost
			are_terminal.setText(are_terminal.getText()+"\n\n¡"+fighter1.getName()+" ha usado "+a.getName()+"!"+
			"\n¡"+fighter1.getName()+" ha perdido "+a.getCost()+" de energía!");
			are_terminal.end();
			fighter1.setEnergy(fighter1.getEnergy()-a.getCost());
			
			//calcular acierto f1 empieza
			boolean acierto=false;
			
			if(fighter2.isEvade()) { //f2 action en evade
				if(a.getHit_rate()-40>=(int)Math.floor(Math.random()*(100-0+0))){
					acierto=true;
				}	
			}
			else { //f2 action en otro
				if(a.getHit_rate()>=(int)Math.floor(Math.random()*(100-0+0))){
					acierto=true;
				}
			}
			
			
			if(acierto) { //acierta
			
				pan_blacK_effect.setVisible(true);
				pan_serpentBack.setVisible(true);
				
				playEffect(effect);
				File effect=new File("file:src/main/resources/audio/effects");
				//meter imagen
				File f2=new File("file:"+a.getPhoto());
				Image img2=new Image(f2.getPath());
				pan_shadow_effect.setVisible(true);
				img_attack_photo.setImage(img2);
				
				new Timer().schedule(new TimerTask() { //empieza el conteo de la imagen
				    //despues de esperar unos segundos se ejecuta lo de abajo
					
					@Override					
					public void run() {
				        //***Aquí agregamos el proceso a ejecutar.
				    	img_attack_photo.setImage(null);
				    	pan_shadow_effect.setVisible(false);
				    	pan_serpentBack.setVisible(false);
				    	pan_blacK_effect.setVisible(false);
				    	
				    	//aqui va lo del video... //////////////////////////////////////////////////////
				    	
				    	if(!a.getMedia().matches("no_resource")) {
				    		File filestring = new File(a.getMedia());
						    Media file = new Media(filestring.toURI().toString());
						    MediaPlayer mediaPlayer = new MediaPlayer(file);
						    	
					    	reproduceMedia(a.getMedia());
					    	try {
								main=Thread.currentThread();

					    		synchronized (main) {
									main.wait();
								}
					    		pan_video.setVisible(false);
							
					    	} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				    	}
				    	
				    	////////////////////////////////////////////////////////////////////////////
				    	
				    	playAnimation(a.getAnimation());
				    	int n=0;
				    	
				    	
				    	new Timer().schedule(new TimerTask() {//secuencia de milisegundos para animacion
				    		@Override
						    public void run() {
				    			String url="";
				    			switch(a.getAnimation()) {
				    			case "Base":
				    				url="file:src/main/resources/images/animations/base/frame"+frame_number+".png";
				    				limit_frame=6;
				    				break;
				    			case "Fuego":
				    				url="file:src/main/resources/images/animations/fire/frame"+frame_number+".png";
				    				limit_frame=14;
				    				break;
				    			case "Rayo":
				    				url="file:src/main/resources/images/animations/thunder/frame"+frame_number+".png";
				    				limit_frame=6;
				    			}
				    			File f=new File(url);
						    	Image img_a2=new Image(f.getPath());
						    	img_animation_P2.setImage(img_a2);
						    	
						    	frame_number++;
						    	if(frame_number>limit_frame) {
						    		img_animation_P2.setImage(null);
						    		cancel();
						    		frame_number=0;
						    		limit_frame=0;
						    		//calcular daño
									dmg=(int) Math.floor(Math.random()*(fighter1.getAtk()*1.10-fighter1.getAtk()*0.90+1)+fighter1.getAtk()*0.90)
											-(int) Math.floor(Math.random()*(fighter2.getDef()*1.15-fighter1.getAtk()*0.85+1)+fighter1.getAtk()*0.85);
									if(dmg<=0) {dmg=1;}
									
									//calcular crítico...
									if(calculateCriticPercent(fighter1)>=(int)Math.floor(Math.random()*(100-0+0))) { //meto crítico
										playEffect(critic);
										dmg*=1.5;
										are_terminal.setText(are_terminal.getText()+"\n¡"+fighter1.getName()+" ha acertado un golpe crítico!");
										are_terminal.end();
									}
									dmg+=a.getPower();
									//calcular daño recibido...
									if(fighter2.isBlock()) { // si block tankea 50% del daño recibido...
										fighter2.setHp_current(fighter2.getHp_current()-(dmg/2));
									}
									else {
										fighter2.setHp_current(fighter2.getHp_current()-dmg);
									}
									
									if(fighter2.getHp_current()<0) {
										fighter2.setHp_current(0);
									}
									
									are_terminal.setText(are_terminal.getText()+"\n¡"+fighter2.getName()+" ha recibido "+dmg+" puntos de daño!");
									are_terminal.end();
												
									fighter2.setHp_percentage((fighter2.getHp_current()*100)/fighter2.getHp_total());
									if(fighter2.getHp_percentage()<=0.0){
										fighter2.setHp_percentage(0.001);
									}
									
									//quitar hp:
					    			lab_P2_hp.setVisible(false);
									new Timer().schedule(new TimerTask() { //secuencia para hpbarrier
							    		@Override
									    public void run() {
							    			if(pb2.getProgress()>=0.75) {	
												pb2.setStyle("-fx-accent: green;");
											}
											
											else if(pb2.getProgress()>=0.5) {
												pb2.setStyle("-fx-accent: yellow;");
											}
											
											else if(pb2.getProgress()>=0.25) {
												pb2.setStyle("-fx-accent: orange;");
											}
											
											else {
												pb2.setStyle("-fx-accent: red;");
											}
											
							    			if(!(pb2.getProgress()<=0.0)) {
					    						try {
					    							pb2.setProgress(pb2.getProgress()-0.0005);
												} catch (Exception e) {
													
													finishBattle();
													
												}
					    						
					    					}
					    					else {
					    						finishBattle();
					    					}
											
											if(pb2.getProgress()<=fighter2.getHp_percentage()/100) {	
												cancel();
												Platform.runLater(new Runnable() {
													
													@Override
													public void run() {
														lab_P2_hp.setText((int)fighter2.getHp_percentage()+" %");
														lab_P2_hp.setVisible(true);
														
														//extra...
														if(a.getExtra().getId()!=0) {
															calculateExtra(fighter1, fighter2 ,a);
															
														}
														
														if(fighter2.getHp_current()<=0
				    											|fighter2.getHp_percentage()<=0.0
				    											|lab_P2_hp.getText().matches("0 %")
				    											|pb2.getProgress()<=0.0) {
															finishBattle();
														}
														//desgarro...
														else if((a.getExtra().getId()>12&&a.getExtra().getId()<16)) {
															//meter pausa y efecto sonoro...
															new Timer().schedule(new TimerTask() {
													    		@Override
															    public void run() {
													    			
													    			switch(a.getExtra().getId()) {
													    			case 13:
													    				perToEdit=0.9;
													    				break;
													    			case 14:
													    				perToEdit=0.8;
													    				break;
													    			case 15:
													    				perToEdit=0.7;
													    				break;
													    			}
													    			
													    			fighter2.setHp_current((int)(fighter2.getHp_current()*perToEdit));
													    			
													    			fighter2.setHp_percentage((fighter2.getHp_current()*100)/fighter2.getHp_total());
													    			if(fighter2.getHp_percentage()<=0.0){
													    				fighter2.setHp_percentage(0.001);
													    			}
													    			
													    			lab_P2_hp.setVisible(false);
													    			playEffect(tear);
													    			new Timer().schedule(new TimerTask() {
													    				public void run() {
													    					if(pb2.getProgress()>=0.75) {	
													    						pb2.setStyle("-fx-accent: green;");
													    					}
													    					
													    					else if(pb2.getProgress()>=0.5) {
													    						pb2.setStyle("-fx-accent: yellow;");
													    					}
													    					
													    					else if(pb2.getProgress()>=0.25) {
													    						pb2.setStyle("-fx-accent: orange;");
													    					}
													    					
													    					else {
													    						pb2.setStyle("-fx-accent: red;");
													    					}
													    					
													    					if(!(pb2.getProgress()<=0.0)) {
													    						try {
													    							pb2.setProgress(pb2.getProgress()-0.0005);
																				} catch (Exception e) {
																					
																					finishBattle();
																					
																				}
													    						
													    					}
													    					else {
													    						finishBattle();
													    					}
													    					
													    					
													    					if(pb2.getProgress()<=fighter2.getHp_percentage()/100) {
													    						cancel();
													    						Platform.runLater(new Runnable() {
													    							@Override
													    							public void run() {
													    								are_terminal.setText(are_terminal.getText()+
													    										"\n El ataque de "+fighter1.getName()+" ha desgarrado a "
													    										+fighter2.getName()+" y ha perdido "
													    												+ "\nun "+(int)(100-(perToEdit*100))+" % de HP!");
													    								are_terminal.end();
													    								
													    								lab_P2_hp.setText((int)fighter2.getHp_percentage()+" %");
													    								lab_P2_hp.setVisible(true);
													    								
													    								if(fighter1.getPriority()>fighter2.getPriority()) {
													    									if(fighter2.getHp_current()<=0
													    											|fighter2.getHp_percentage()<=0.0
													    											|lab_P2_hp.getText().matches("0 %")
													    											|pb2.getProgress()<=0.0) {
													    										finishBattle();
													    									}
													    									else {
													    										p2Attack();
													    									}
													    								}
													    								else {
													    									turn();
													    								}
													    							}
													    						});	
													    					}
													    				}
													    			},0,1);
													    		}
															},1000); //esperar 1 sec desgarro...
														}
														
														else if(((a.getExtra().getId()>0&&a.getExtra().getId()<4))&&fighter1.getHp_current()!=fighter1.getHp_total()) {

															new Timer().schedule(new TimerTask() {
													    		@Override
															    public void run() {
													    			switch(a.getExtra().getId()) {
													    			case 1:
													    				perToEdit=0.1;
													    				break;
													    			case 2:
													    				perToEdit=0.2;
													    				break;
													    			case 3:
													    				perToEdit=0.3;
													    				break;
													    			}
													    			fighter1.setHp_current((int)(fighter1.getHp_current()+(fighter1.getHp_total()*perToEdit)));
													    			if(fighter1.getHp_current()>fighter1.getHp_total()) {
													    				fighter1.setHp_current(fighter1.getHp_total());
													    				fighter1.setHp_percentage(100);
													    			}
													    			else {
													    				fighter1.setHp_percentage((fighter1.getHp_current()*100)/fighter1.getHp_total());
													    			}
													    			
													    			lab_P1_hp.setVisible(false);
													    			playEffect(heal);
													    			//meter animación hp
													    			
													    			new Timer().schedule(new TimerTask() {
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
													    					
													    					pb1.setProgress(pb1.getProgress()+0.0005);
													    					
													    					if(pb1.getProgress()>=fighter1.getHp_percentage()/100) {
													    						cancel();
													    						Platform.runLater(new Runnable() {
													    							@Override
													    							public void run() {
													    								are_terminal.setText(are_terminal.getText()+
													    								"\n"+fighter1.getName()+" ha recuperado un "+((int)perToEdit*100)+" % de HP!");
								
													    								are_terminal.end();
													    								
													    								lab_P1_hp.setText((int)fighter1.getHp_percentage()+" %");
													    								lab_P1_hp.setVisible(true);
													    								
													    								if(fighter1.getPriority()>fighter2.getPriority()) {
													    									if(fighter2.getHp_current()<=0
													    											|fighter2.getHp_percentage()<=0.0
													    											|lab_P2_hp.getText().matches("0 %")
													    											|pb2.getProgress()<=0.0) {
													    										finishBattle();
													    									}
													    									else {
													    										p2Attack();
													    									}
													    								}
													    								else {
													    									turn();
													    								}
													    							}
													    						});
													    					}
															    		}
													    			},0,1);
													    			
													    		}
															},1000); //esperar 1 sec
														}
														
														else if(a.getExtra().getId()==35) { //sacrifice
															//meter pausa y efecto sonoro...
															new Timer().schedule(new TimerTask() {
													    		@Override
															    public void run() {
													    			
													    			fighter1.setHp_current(1);
													    			
													    			fighter1.setHp_percentage((fighter1.getHp_current()*100)/fighter1.getHp_total());
													    			if(fighter1.getHp_percentage()<=0.0){
													    				fighter1.setHp_percentage(0.01);
													    			}
													    			
													    			lab_P1_hp.setVisible(false);
													    			playEffect(tear);
													    			new Timer().schedule(new TimerTask() {
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
													    					
													    					if(!(pb1.getProgress()<=0.0)) {
													    						try {
													    							pb1.setProgress(pb1.getProgress()-0.0005);
																				} catch (Exception e) {
																					
																					finishBattle(); //error
																					
																				}
													    						
													    					}
													    					else {
													    						finishBattle();
													    					}
													    					
													    					
													    					if(pb1.getProgress()<=0.01) {
													    						cancel();
													    						Platform.runLater(new Runnable() {
													    							@Override
													    							public void run() {
													    								are_terminal.setText(are_terminal.getText()+
													    										"\n¡"+fighter1.getName()+" ha sido gravemente herido!");
													    								are_terminal.end();
													    								
													    								lab_P1_hp.setText(1+" %");
													    								lab_P1_hp.setVisible(true);
													    								
													    								if(fighter1.getPriority()>fighter2.getPriority()) {
													    									if(fighter2.getHp_current()<=0
													    											|fighter2.getHp_percentage()<=0.0
													    											|lab_P2_hp.getText().matches("0 %")
													    											|pb2.getProgress()<=0.0) {
													    										finishBattle();
													    									}
													    									else {
													    										p2Attack();
													    									}
													    								}
													    								else {
													    									turn();
													    								}
													    							}
													    						});	
													    					}
													    				}
													    			},0,1);
													    		}
															},1000); //esperar 1 sec desgarro...
														}
														
														else {
															if(fighter1.getPriority()>fighter2.getPriority()) { //F1 GANA
																if(fighter2.getHp_current()<=0
																		|fighter2.getHp_percentage()<=0.0
																		|lab_P2_hp.getText().matches("0 %")
																		|pb2.getProgress()<=0.0) {
																	finishBattle();
																}
																else {
																	p2Attack();
																}	
															}
															else {
																turn();
															}
														}	
													}
												});
											}
							    		}
							    	},0,1); //speed hpbarrier
						    	}

						    }
				    	}, 0,65); //speed animation			    	
				    }
				}, 2000); //speed attack image;

			}
			else { //falla
				playEffect(fail);
				are_terminal.setText(are_terminal.getText()+"\n¡"+fighter2.getName()+" ha esquivado el ataque!");
				are_terminal.end();
				if(fighter1.getPriority()>fighter2.getPriority()) {
					p2Attack();
				}
				else {
					turn();
				}
			}
		}
		else { //f1 bloquea o evade, no ataca...
			if(fighter1.getPriority()>fighter2.getPriority()) {
				p2Attack();
			}
			else {
				turn();
			}
		}
	}
	
	public void p2Attack() {
		dmg=0;
		if(fighter2.getAction()>0&&fighter2.getAction()<4) { //ataca
			
			//comentar y restar a.cost
			are_terminal.setText(are_terminal.getText()+"\n\n¡"+fighter2.getName()+" ha usado "+a2.getName()+"!"+
			"\n¡"+fighter2.getName()+" ha perdido "+a2.getCost()+" de energía!");
			are_terminal.end();
			fighter2.setEnergy(fighter2.getEnergy()-a2.getCost());
			
			//calcular acierto f2 empieza
			boolean acierto=false;
			
			if(fighter1.isEvade()) { //f1 action en evade
				if(a2.getHit_rate()-40>=(int)Math.floor(Math.random()*(100-0+0))){
					acierto=true;
				}	
			}
			else { //f1 action en otro
				if(a2.getHit_rate()>=(int)Math.floor(Math.random()*(100-0+0))){
					acierto=true;
				}
			}
			
			
			if(acierto) { //acierta
				pan_blacK_effect.setVisible(true);
				pan_serpentBack.setVisible(true);
				
				playEffect(effect);
				File effect=new File("file:src/main/resources/audio/effects");
				//meter imagen
				File f2=new File("file:"+a2.getPhoto());
				Image img2=new Image(f2.getPath());
				pan_shadow_effect.setVisible(true);
				img_attack_photo.setImage(img2);
				
				new Timer().schedule(new TimerTask() {
				    @Override
				    public void run() {
				        //***Aquí agregamos el proceso a ejecutar.
				    	img_attack_photo.setImage(null);
				    	pan_shadow_effect.setVisible(false);
				    	pan_serpentBack.setVisible(false);
				    	pan_blacK_effect.setVisible(false);
				    	
				    	//aqui va lo de media////////////////////////////////////////////////////
				    	
				    	if(!a2.getMedia().matches("no_resource")) {
				    		File filestring = new File(a2.getMedia());
						    Media file = new Media(filestring.toURI().toString());
						    MediaPlayer mediaPlayer = new MediaPlayer(file);
						    	
					    	reproduceMedia(a2.getMedia());
					    	try {
								main=Thread.currentThread();

					    		synchronized (main) {
									main.wait();
								}
					    		pan_video.setVisible(false);
							
					    	} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				    	}
				    	
				    	/////////////////////////////////////////////////////////
				    	
				    	playAnimation(a2.getAnimation());
				    	int n=0;
				    	
				    	new Timer().schedule(new TimerTask() {
				    		@Override
						    public void run() {
				    			
				    			String url="";
				    			switch(a2.getAnimation()) {
				    			case "Base":
				    				url="file:src/main/resources/images/animations/base/frame"+frame_number+".png";
				    				limit_frame=6;
				    				break;
				    			case "Fuego":
				    				url="file:src/main/resources/images/animations/fire/frame"+frame_number+".png";
				    				limit_frame=14;
				    				break;
				    			case "Rayo":
				    				url="file:src/main/resources/images/animations/thunder/frame"+frame_number+".png";
				    				limit_frame=6;
				    			}
				    			
				    			File f=new File(url);
						    	Image img_a2=new Image(f.getPath());
						    	img_animation_P1.setImage(img_a2);
						    	frame_number++;
						    	
						    	if(frame_number>limit_frame) {
						    		img_animation_P1.setImage(null);
						    		cancel();
						    		frame_number=0;
						    		limit_frame=0;
						    		//calcular daño
									dmg=(int) Math.floor(Math.random()*(fighter2.getAtk()*1.10-fighter2.getAtk()*0.90+1)+fighter2.getAtk()*0.90)
											-(int) Math.floor(Math.random()*(fighter1.getDef()*1.15-fighter1.getDef()*0.85+1)+fighter1.getDef()*0.85);
									if(dmg<=0) {dmg=1;}
									
									//calcular crítico...
									if(calculateCriticPercent(fighter2)>=(int)Math.floor(Math.random()*(100-0+0))) { //meto crítico
										playEffect(critic);
										dmg*=1.5;
										are_terminal.setText(are_terminal.getText()+"\n¡"+fighter2.getName()+" ha acertado un golpe crítico!");
										are_terminal.end();
									}
									dmg+=a2.getPower();
									//calcular daño recibido...
									if(fighter1.isBlock()) { // si block tankea 50% del daño recibido...
										fighter1.setHp_current(fighter1.getHp_current()-(dmg/2));
									}
									else {
										fighter1.setHp_current(fighter1.getHp_current()-dmg);
									}
									
									if(fighter1.getHp_current()<0) {
										fighter1.setHp_current(0);
									}
									
									are_terminal.setText(are_terminal.getText()+"\n¡"+fighter1.getName()+" ha recibido "+dmg+" puntos de daño!");
									are_terminal.end();
												
									fighter1.setHp_percentage((fighter1.getHp_current()*100)/fighter1.getHp_total());
									
									if(fighter1.getHp_percentage()<=0.0){
										fighter1.setHp_percentage(0.001);
									}
									
									//quitar hp:
					    			lab_P1_hp.setVisible(false);
									new Timer().schedule(new TimerTask() {
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
											
							    			try {
							    				if(pb1.getProgress()!=0.0) {
								    				pb1.setProgress(pb1.getProgress()-0.0005);
								    			}
											} catch (Exception e) {
												finishBattle();
										
											}
							    				
											if(pb1.getProgress()<=fighter1.getHp_percentage()/100) {	
												cancel();
												Platform.runLater(new Runnable() {
													
													@Override
													public void run() {
														lab_P1_hp.setText((int)fighter1.getHp_percentage()+" %");
														lab_P1_hp.setVisible(true);
														
														//extra...
														if(a2.getExtra().getId()!=0) {
															calculateExtra(fighter2, fighter1,a2);
															
														}
														
														
														if(fighter1.getHp_current()<=0
				    											|fighter1.getHp_percentage()<=0.0
				    											|lab_P1_hp.getText().matches("0 %")
				    											|pb1.getProgress()<=0.0) {
															finishBattle();
														}
														//desgarro...
														else if((a2.getExtra().getId()>12&&a2.getExtra().getId()<16)) {
															//meter pausa y efecto sonoro...
															new Timer().schedule(new TimerTask() {
													    		@Override
															    public void run() {
													    			
													    			switch(a2.getExtra().getId()) {
													    			case 13:
													    				perToEdit=0.9;
													    				break;
													    			case 14:
													    				perToEdit=0.8;
													    				break;
													    			case 15:
													    				perToEdit=0.7;
													    				break;
													    			}
													    			
													    			fighter1.setHp_current((int)(fighter1.getHp_current()*perToEdit));
													    			
													    			fighter1.setHp_percentage((fighter1.getHp_current()*100)/fighter1.getHp_total());
													    			if(fighter1.getHp_percentage()<=0.0){
													    				fighter1.setHp_percentage(0.001);
													    			}
													    			
													    			lab_P1_hp.setVisible(false);
													    			playEffect(tear);
													    			new Timer().schedule(new TimerTask() {
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
													    					
													    					try {
															    				if(pb1.getProgress()!=0.0) {
																    				pb1.setProgress(pb1.getProgress()-0.0005);
																    			}
																			} catch (Exception e) {
																				finishBattle();
																			
																			}
													    					
													    					if(pb1.getProgress()<=fighter1.getHp_percentage()/100) {
													    						cancel();
													    						Platform.runLater(new Runnable() {
													    							@Override
													    							public void run() {
													    								are_terminal.setText(are_terminal.getText()+
													    										"\n El ataque de "+fighter1.getName()+" ha desgarrado a "
													    										+fighter2.getName()+" y ha perdido "
													    												+ "\nun "+(int)(100-(perToEdit*100))+" % de HP!");
													    								are_terminal.end();
													    								
													    								lab_P1_hp.setText((int)fighter1.getHp_percentage()+" %");
													    								lab_P1_hp.setVisible(true);
													    								
													    								if(fighter2.getPriority()>fighter1.getPriority()) {
													    									if(fighter1.getHp_current()<=0
													    											|fighter1.getHp_percentage()<=0.0
													    											|lab_P1_hp.getText().matches("0 %")
													    											|pb1.getProgress()<=0.0) {
													    										finishBattle();
													    									}
													    									else {
													    										p1Attack();
													    									}
													    								}
													    								else {
													    									turn();
													    								}
													    							}
													    						});	
													    					}
													    				}
													    			},0,1);
													    		}
															},1000); //esperar 1 sec desgarro...	
														}
														
														else if(a2.getExtra().getId()==35) { //sacrifice.../
															//meter pausa y efecto sonoro...
															new Timer().schedule(new TimerTask() {
													    		@Override
															    public void run() {
													    			
													    			fighter2.setHp_current(1);
													    			
													    			fighter2.setHp_percentage((fighter2.getHp_current()*100)/fighter2.getHp_total());
													    			if(fighter2.getHp_percentage()<=0.0){
													    				fighter2.setHp_percentage(0.01);
													    			}
													    			
													    			lab_P2_hp.setVisible(false);
													    			playEffect(tear);
													    			new Timer().schedule(new TimerTask() {
													    				public void run() {
													    					if(pb2.getProgress()>=0.75) {	
													    						pb2.setStyle("-fx-accent: green;");
													    					}
													    					
													    					else if(pb2.getProgress()>=0.5) {
													    						pb2.setStyle("-fx-accent: yellow;");
													    					}
													    					
													    					else if(pb2.getProgress()>=0.25) {
													    						pb2.setStyle("-fx-accent: orange;");
													    					}
													    					
													    					else {
													    						pb2.setStyle("-fx-accent: red;");
													    					}
													    					
													    					try {
															    				if(pb2.getProgress()!=0.0) {
																    				pb2.setProgress(pb2.getProgress()-0.0005);
																    			}
																			} catch (Exception e) {
																				finishBattle();
																			
																			}
													    					
													    					if(pb2.getProgress()<=0.01) {
													    						cancel();
													    						Platform.runLater(new Runnable() {
													    							@Override
													    							public void run() {
													    								are_terminal.setText(are_terminal.getText()+
													    										"\n¡"+fighter2.getName()+" ha sido gravemente herido!");
													    								are_terminal.end();
													    								
													    								lab_P2_hp.setText(1+" %");
													    								lab_P2_hp.setVisible(true);
													    								
													    								if(fighter2.getPriority()>fighter1.getPriority()) {
													    									if(fighter1.getHp_current()<=0
													    											|fighter1.getHp_percentage()<=0.0
													    											|lab_P1_hp.getText().matches("0 %")
													    											|pb1.getProgress()<=0.0) {
													    										finishBattle();
													    									}
													    									else {
													    										p1Attack();
													    									}
													    								}
													    								else {
													    									turn();
													    								}
													    							}
													    						});	
													    					}
													    				}
													    			},0,1);
													    		}
															},1000); //esperar 1 sec desgarro...	
														}
														
														else if(((a2.getExtra().getId()>0&&a2.getExtra().getId()<4))&&fighter2.getHp_current()!=fighter2.getHp_total()) {

															new Timer().schedule(new TimerTask() {
													    		@Override
															    public void run() {
													    			switch(a2.getExtra().getId()) {
													    			case 1:
													    				perToEdit=0.1;
													    				break;
													    			case 2:
													    				perToEdit=0.2;
													    				break;
													    			case 3:
													    				perToEdit=0.3;
													    				break;
													    			}
													    			fighter2.setHp_current((int)(fighter2.getHp_current()+(fighter2.getHp_total()*perToEdit)));
													    			if(fighter2.getHp_current()>fighter2.getHp_total()) {
													    				fighter2.setHp_current(fighter2.getHp_total());
													    				fighter2.setHp_percentage(100);
													    			}
													    			else {
													    				fighter2.setHp_percentage((fighter2.getHp_current()*100)/fighter2.getHp_total());
													    			}
													    			
													    			lab_P2_hp.setVisible(false);
													    			playEffect(heal);
													    			//meter animación hp
													    			
													    			new Timer().schedule(new TimerTask() {
															    		@Override
																	    public void run() {
															    			if(pb2.getProgress()>=0.75) {	
													    						pb2.setStyle("-fx-accent: green;");
													    					}
													    					
													    					else if(pb2.getProgress()>=0.5) {
													    						pb2.setStyle("-fx-accent: yellow;");
													    					}
													    					
													    					else if(pb2.getProgress()>=0.25) {
													    						pb2.setStyle("-fx-accent: orange;");
													    					}
													    					
													    					else {
													    						pb2.setStyle("-fx-accent: red;");
													    					}
													    					
													    					pb2.setProgress(pb2.getProgress()+0.0005);
													    					
													    					if(pb2.getProgress()>=fighter2.getHp_percentage()/100) {
													    						cancel();
													    						Platform.runLater(new Runnable() {
													    							@Override
													    							public void run() {
													    								are_terminal.setText(are_terminal.getText()+
													    								"\n"+fighter2.getName()+" ha recuperado un "+((int)perToEdit*100)+" % de HP!");
								
													    								are_terminal.end();
													    								
													    								lab_P2_hp.setText((int)fighter2.getHp_percentage()+" %");
													    								lab_P2_hp.setVisible(true);
													    								
													    								if(fighter2.getPriority()>fighter1.getPriority()) {
													    									if(fighter1.getHp_current()<=0
													    											|fighter1.getHp_percentage()<=0.0
													    											|lab_P1_hp.getText().matches("0 %")
													    											|pb1.getProgress()<=0.0) {
													    										finishBattle();
													    									}
													    									else {
													    										p1Attack();
													    									}
													    								}
													    								else {
													    									turn();
													    								}
													    							}
													    						});
													    					}
															    		}
													    			},0,1);
													    			
													    		}
															},1000); //esperar 1 sec
														}
														
														else {
															if(fighter2.getPriority()>fighter1.getPriority()) {
																if(fighter1.getHp_current()<=0
																		|fighter1.getHp_percentage()<=0.0
																		|lab_P1_hp.getText().matches("0 %")
																		|pb1.getProgress()<=0.0) {
																	finishBattle();
																}
																else {
																	p1Attack();
																}
															}
															else {
																turn();
															}
														}
														
															
													}
												});
											}
							    		}
							    	},0,1); //speed hpbarrier
						    	}

						    }
				    	}, 0,65); //speed animation
				    }
				}, 2000); //speed attack image;

			}
			else { //falla
				playEffect(fail);
				are_terminal.setText(are_terminal.getText()+"\n¡"+fighter1.getName()+" ha esquivado el ataque!");
				are_terminal.end();
				
				if(fighter2.getPriority()>fighter1.getPriority()) {
					p1Attack();
				}
				else {
					turn();
				}
			}
		}
		else { //f2 bloquea o evade, no ataca...
			if(fighter2.getPriority()>fighter1.getPriority()) {
				p1Attack();
			}
			else {
				turn();
			}
		}
	}
	
	
	public int calculateCriticPercent(Fighter f) {
		int result=(f.getSpe()/13)*3;
		return result;
	}
	
	public void playEffect(File f){
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(f);
			Clip clip= null;
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void playAnimation(String animation){
		switch(animation) {
		case "Base":
			sound_animation=new File("src/main/resources/audio/effects/base_animation.wav");
			break;
		case "Fuego":
			sound_animation=new File("src/main/resources/audio/effects/fire_animation.wav");
			break;
		case "Rayo":
			sound_animation=new File("src/main/resources/audio/effects/thunder_animation.wav");
			break;
			
		}
		
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(sound_animation);
			Clip clip= null;
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (UnsupportedAudioFileException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void showInfoP1() {
		try {
			are_info_p1.setText("Recuperación de Energía : "+fighter1.getEnergy_recover()
					+"\nAtaque : "+fighter1.getAtk()+" ("+fighter1.getAtkState()+")"
					+"\nDefensa : "+fighter1.getDef()+" ("+fighter1.getDefState()+")"
					+"\nVelocidad : "+fighter1.getSpe()+" ("+fighter1.getSpeState()+")"
					+"\nRol : "+fighter1.getRol().getName());
			pan_info_P1.setVisible(true);
		}
		catch (Exception e) {
			closeInfos();
		}
	}
	
	@FXML
	public void showInfoP2() {
		try {
			are_info_p2.setText("Recuperación de Energía : "+fighter2.getEnergy_recover()
					+"\nAtaque : "+fighter2.getAtk()+" ("+fighter2.getAtkState()+")"
					+"\nDefensa : "+fighter2.getDef()+" ("+fighter2.getDefState()+")"
					+"\nVelocidad: "+fighter2.getSpe()+" ("+fighter2.getSpeState()+")"
					+"\nRol : "+fighter2.getRol().getName());
			pan_info_P2.setVisible(true);
		}
		catch (Exception e) {
			closeInfos();
		}
	}
	
	@FXML
	public void showInfoA1() {
		try {
			String f="";
			if(fighter1.getA1().getExtra().getId()!=34) {
				f="Poder : "+fighter1.getA1().getPower()
						+"\nCoste : "+fighter1.getA1().getCost()
						+"\nAcierto : "+fighter1.getA1().getHit_rate()+" %"
						+"\n"+fighter1.getA1().getExtra().getDescription();
			}
			else {
				f="Poder : "+fighter1.getA1().getPower()
						+"\nCoste : "+fighter1.getA1().getCost()
						+"\nAcierto : "+fighter1.getA1().getHit_rate()+" %"
						+"\nJuego Final, puede pasar cualquier cosa...";
			}
			are_Info.setText(f);
			are_Info.setVisible(true);
		}
		catch (Exception e) {
			closeInfos();
		}
		
	}
	
	@FXML
	public void showInfoA2() {
		try {
			String f="";
			if(fighter1.getA2().getExtra().getId()!=34) {
				f="Poder : "+fighter1.getA2().getPower()
						+"\nCoste : "+fighter1.getA2().getCost()
						+"\nAcierto : "+fighter1.getA2().getHit_rate()+" %"
						+"\n"+fighter1.getA2().getExtra().getDescription();
			}
			else {
				f="Poder : "+fighter1.getA2().getPower()
						+"\nCoste : "+fighter1.getA2().getCost()
						+"\nAcierto : "+fighter1.getA2().getHit_rate()+" %"
						+"\nJuego Final, puede pasar cualquier cosa...";
			}
			are_Info.setText(f);
			are_Info.setVisible(true);
		}
		catch (Exception e) {
			closeInfos();
		}
	}
	
	@FXML
	public void showInfoA3() {
		try {
			String f="";
			if(fighter1.getA3().getExtra().getId()!=34) {
				f="Poder : "+fighter1.getA3().getPower()
						+"\nCoste : "+fighter1.getA3().getCost()
						+"\nAcierto : "+fighter1.getA3().getHit_rate()+" %"
						+"\n"+fighter1.getA3().getExtra().getDescription();
			}
			else {
				f="Poder : "+fighter1.getA3().getPower()
						+"\nCoste : "+fighter1.getA3().getCost()
						+"\nAcierto : "+fighter1.getA3().getHit_rate()+" %"
						+"\nJuego Final, puede pasar cualquier cosa...";
			}
			are_Info.setText(f);
			are_Info.setVisible(true);
		} catch (Exception e) {
			closeInfos();
		}
	}
	
	@FXML
	public void p2_showInfoA1() {
		try {
			String f="";
			if(fighter2.getA1().getExtra().getId()!=34) {
				f="Poder : "+fighter2.getA1().getPower()
						+"\nCoste : "+fighter2.getA1().getCost()
						+"\nAcierto : "+fighter2.getA1().getHit_rate()+" %"
						+"\n"+fighter2.getA1().getExtra().getDescription();
			}
			else {
				f="Poder : "+fighter2.getA1().getPower()
						+"\nCoste : "+fighter2.getA1().getCost()
						+"\nAcierto : "+fighter2.getA1().getHit_rate()+" %"
						+"\nJuego Final, puede pasar cualquier cosa...";
			}
			are_Info.setText(f);
			are_Info.setVisible(true);
		}
		catch (Exception e) {
			closeInfos();
		}
		
	}
	
	@FXML
	public void p2_showInfoA2() {
		try {
			String f="";
			if(fighter2.getA2().getExtra().getId()!=34) {
				f="Poder : "+fighter2.getA2().getPower()
						+"\nCoste : "+fighter2.getA2().getCost()
						+"\nAcierto : "+fighter2.getA2().getHit_rate()+" %"
						+"\n"+fighter2.getA2().getExtra().getDescription();
			}
			else {
				f="Poder : "+fighter2.getA2().getPower()
						+"\nCoste : "+fighter2.getA2().getCost()
						+"\nAcierto : "+fighter2.getA2().getHit_rate()+" %"
						+"\nJuego Final, puede pasar cualquier cosa...";
			}
			are_Info.setText(f);
			are_Info.setVisible(true);
		}
		catch (Exception e) {
			closeInfos();
		}
	}
	
	@FXML
	public void p2_showInfoA3() {
		try {
			String f="";
			if(fighter1.getA3().getExtra().getId()!=34) {
				f="Poder : "+fighter2.getA3().getPower()
						+"\nCoste : "+fighter2.getA3().getCost()
						+"\nAcierto : "+fighter2.getA3().getHit_rate()+" %"
						+"\n"+fighter2.getA3().getExtra().getDescription();
			}
			else {
				f="Poder : "+fighter2.getA3().getPower()
						+"\nCoste : "+fighter2.getA3().getCost()
						+"\nAcierto : "+fighter2.getA3().getHit_rate()+" %"
						+"\nJuego Final, puede pasar cualquier cosa...";
			}
			are_Info.setText(f);
			are_Info.setVisible(true);
		} catch (Exception e) {
			closeInfos();
		}
	}
	
	@FXML
	public void showInfoBlock() {
		try {
			are_Info.setText("Tomas posición defensiva y te preaparas para bloquear un ataque."
					+ "\nReduces un 50 % el daño recibido este turno."
					+ "\nNo cuesta energía.");
			are_Info.setVisible(true);
		} catch (Exception e) {
			closeInfos();
		}
	}
	
	@FXML
	public void showInfoEvade() {
		try {
			are_Info.setText("Tomas posición defensiva y te preaparas para evadir un ataque."
					+ "\nGanas un 40 % mas de posibilidades de evadir un golpe."
					+ "\nNo cuesta energía.");
			are_Info.setVisible(true);
		} catch (Exception e) {
			closeInfos();
		}
	}
	
	@FXML
	public void closeInfos() {
		try {
			are_Info.setVisible(false);
			pan_info_P1.setVisible(false);
			pan_info_P2.setVisible(false);
		} catch (Exception e) {
			
		}
	}

	public void calculateExtra(Fighter atacante, Fighter defensor, Attack awe) {
		switch(awe.getExtra().getId()) {

		case 4:
			playEffect(buff);
			atacante.setAtk((int)(atacante.getAtk()*1.1));
			atacante.setAtkState(atacante.getAtkState()+1);
			are_terminal.setText(are_terminal.getText()+"\n¡El ataque de "+atacante.getName()+" ha aumentado un 10 %!");
			are_terminal.end();
			break;
		case 5:
			playEffect(buff);
			atacante.setAtk((int)(atacante.getAtk()*1.2));
			atacante.setAtkState(atacante.getAtkState()+2);
			are_terminal.setText(are_terminal.getText()+"\n¡El ataque de "+atacante.getName()+" ha aumentado un 20 %!");
			are_terminal.end();
			break;
		case 6:
			playEffect(buff);
			atacante.setAtk((int)(atacante.getAtk()*1.3));
			atacante.setAtkState(atacante.getAtkState()+3);
			are_terminal.setText(are_terminal.getText()+"\n¡El ataque de "+atacante.getName()+" ha aumentado un 30 %!");
			are_terminal.end();
			break;
		case 7:
			playEffect(buff);
			atacante.setDef((int)(atacante.getDef()*1.1));
			atacante.setDefState(atacante.getDefState()+1);
			are_terminal.setText(are_terminal.getText()+"\n¡La defensa de "+atacante.getName()+" ha aumentado un 10 %!");
			are_terminal.end();
			break;
		case 8:
			playEffect(buff);
			atacante.setDef((int)(atacante.getDef()*1.2));
			atacante.setDefState(atacante.getDefState()+2);
			are_terminal.setText(are_terminal.getText()+"\n¡La defensa de "+atacante.getName()+" ha aumentado un 20 %!");
			are_terminal.end();
			break;
		case 9:
			playEffect(buff);
			atacante.setDef((int)(atacante.getDef()*1.3));
			atacante.setDefState(atacante.getDefState()+3);
			are_terminal.setText(are_terminal.getText()+"\n¡La defensa de "+atacante.getName()+" ha aumentado un 30 %!");
			are_terminal.end();
			break;
		case 10:
			playEffect(buff);
			atacante.setSpe((int)(atacante.getSpe()*1.1));
			atacante.setSpeState(atacante.getSpeState()+1);
			are_terminal.setText(are_terminal.getText()+"\n¡La velocidad de "+atacante.getName()+" ha aumentado un 10 %!");
			are_terminal.end();
			break;
		case 11:
			playEffect(buff);
			atacante.setSpe((int)(atacante.getSpe()*1.2));
			atacante.setSpeState(atacante.getSpeState()+2);
			are_terminal.setText(are_terminal.getText()+"\n¡La velocidad de "+atacante.getName()+" ha aumentado un 20 %!");
			are_terminal.end();
			break;
		case 12:
			playEffect(buff);
			atacante.setSpe((int)(atacante.getSpe()*1.3));
			atacante.setSpeState(atacante.getSpeState()+3);
			are_terminal.setText(are_terminal.getText()+"\n¡La velocidad de "+atacante.getName()+" ha aumentado un 30 %!");
			are_terminal.end();
			break;

		case 16:
			playEffect(debuff);
			if(defensor.getAtk()>=0){
				defensor.setAtk((int)(defensor.getAtk()*0.9));
				defensor.setAtkState(defensor.getAtkState()-1);
				are_terminal.setText(are_terminal.getText()+"\n¡El ataque de "+defensor.getName()+" ha disminuido un 10 %!");
				are_terminal.end();
			}
			if(defensor.getAtk()<=0) {
				defensor.setAtk(1);
			}
			
			break;
		case 17:
			playEffect(debuff);
			if(defensor.getAtk()>=0){
				defensor.setAtk((int)(defensor.getAtk()*0.8));
				defensor.setAtkState(defensor.getAtkState()-2);
				are_terminal.setText(are_terminal.getText()+"\n¡El ataque de "+defensor.getName()+" ha disminuido un 20 %!");
				are_terminal.end();
			}
			if(defensor.getAtk()<=0) {
				defensor.setAtk(1);
			}
			
			break;
		case 18:
			playEffect(debuff);
			if(defensor.getAtk()>=0){
				defensor.setAtk((int)(defensor.getAtk()*0.7));
				defensor.setAtkState(defensor.getAtkState()-3);
				are_terminal.setText(are_terminal.getText()+"\n¡El ataque de "+defensor.getName()+" ha disminuido un 30 %!");
				are_terminal.end();
			}
			if(defensor.getAtk()<=0) {
				defensor.setAtk(1);
			}
			break;
		case 19:
			playEffect(debuff);
			if(defensor.getDef()>=0) {
				defensor.setDef((int)(defensor.getDef()*0.9));
				defensor.setDefState(defensor.getDefState()-1);
				are_terminal.setText(are_terminal.getText()+"\n¡La defensa de "+defensor.getName()+" ha disminuido un 10 %!");
				are_terminal.end();
			}
			if(defensor.getDef()<=0) {
				defensor.setDef(1);
			}
			
			break;
		case 20:
			playEffect(debuff);
			if(defensor.getDef()>=0) {
				defensor.setDef((int)(defensor.getDef()*0.8));
				defensor.setDefState(defensor.getDefState()-2);
				are_terminal.setText(are_terminal.getText()+"\n¡La defensa de "+defensor.getName()+" ha disminuido un 20 %!");
				are_terminal.end();	
			}
			if(defensor.getDef()<=0) {
				defensor.setDef(1);
			}
			
			break;
		case 21:
			playEffect(debuff);
			if(defensor.getDef()>=0) {
				defensor.setDef((int)(defensor.getDef()*0.7));
				defensor.setDefState(defensor.getDefState()-3);
				are_terminal.setText(are_terminal.getText()+"\n¡La defensa de "+defensor.getName()+" ha disminuido un 30 %!");
				are_terminal.end();
			}
			if(defensor.getDef()<=0) {
				defensor.setDef(1);
			}
			break;
		case 22:
			playEffect(debuff);
			if(defensor.getSpe()>=0) {
				defensor.setSpe((int)(defensor.getSpe()*0.9));
				defensor.setSpeState(defensor.getSpeState()-1);
				are_terminal.setText(are_terminal.getText()+"\n¡La velocidad de "+defensor.getName()+" ha disminuido un 10 %!");
				are_terminal.end();
			}
			if(defensor.getSpe()<=0) {
				defensor.setSpe(1);
			}
			
			break;
		case 23:
			playEffect(debuff);
			if(defensor.getSpe()>=0) {
				defensor.setSpe((int)(defensor.getSpe()*0.8));
				defensor.setSpeState(defensor.getSpeState()-2);
				are_terminal.setText(are_terminal.getText()+"\n¡La velocidad de "+defensor.getName()+" ha disminuido un 20 %!");
				are_terminal.end();
			}
			if(defensor.getSpe()<=0) {
				defensor.setSpe(1);
			}
			
			break;
		case 24:
			playEffect(debuff);
			if(defensor.getSpe()>=0) {
				defensor.setSpe((int)(defensor.getSpe()*0.7));
				defensor.setSpeState(defensor.getSpeState()-3);
				are_terminal.setText(are_terminal.getText()+"\n¡La velocidad de "+defensor.getName()+" ha disminuido un 30 %!");
				are_terminal.end();
			}
			if(defensor.getSpe()<=0) {
				defensor.setSpe(1);
			}
			
			break;
		case 25:
			if(atacante.getPriority()>defensor.getPriority()) {
				int prob=(int)(Math.floor(Math.random()*100));
				if(prob<=33) {
					defensor.setPriority(atacante.getPriority()+1);
					playEffect(stun);
					are_terminal.setText(are_terminal.getText()+"\n¡"+defensor.getName()+" ha quedado aturdido!");
					are_terminal.end();
				}
			}
			break;
		case 26:
			if(atacante.getPriority()>defensor.getPriority()) {
				int prob=(int)(Math.floor(Math.random()*100));
				if(prob<=50) {
					defensor.setPriority(atacante.getPriority()+1);
					playEffect(stun);
					are_terminal.setText(are_terminal.getText()+"\n¡"+defensor.getName()+" ha quedado aturdido!");
					are_terminal.end();
				}
			}
			break;
		case 27:
			if(atacante.getPriority()>defensor.getPriority()) {
				defensor.setPriority(atacante.getPriority()+1);
				playEffect(stun);
				are_terminal.setText(are_terminal.getText()+"\n¡"+defensor.getName()+" ha quedado aturdido!");
				are_terminal.end();
			}
			break;
		case 28:
			if(defensor.getEnergy()>=1) {
				int prob=(int)(Math.floor(Math.random()*100));
				if(prob<=33) {
					atacante.setEnergy(atacante.getEnergy()+1);
					defensor.setEnergy(defensor.getEnergy()-1);
					are_terminal.setText(are_terminal.getText()+"\n¡"+atacante.getName()+" ha robado 1 de energía a "+defensor.getName()+"!");
					are_terminal.end();
					if(defensor.equals(fighter2)) { //atacante f1
						lab_P2_ene.setText(defensor.getEnergy()+"");
					}
					else {
						lab_P1_ene.setText(defensor.getEnergy()+"");
					}
				}
				
			}
			break;
		case 29:
			if(defensor.getEnergy()>=1) {
				int prob=(int)(Math.floor(Math.random()*100));
				if(prob<=50) {
					atacante.setEnergy(atacante.getEnergy()+1);
					defensor.setEnergy(defensor.getEnergy()-1);
					are_terminal.setText(are_terminal.getText()+"\n¡"+atacante.getName()+" ha robado 1 de energía a "+defensor.getName()+"!");
					are_terminal.end();
					if(defensor.equals(fighter2)) { //atacante f1
						lab_P2_ene.setText(defensor.getEnergy()+"");
					}
					else {
						lab_P1_ene.setText(defensor.getEnergy()+"");
					}
				}
				
			}
			break;
		case 30:
			if(defensor.getEnergy()>=1) {
				atacante.setEnergy(atacante.getEnergy()+1);
				defensor.setEnergy(defensor.getEnergy()-1);
				are_terminal.setText(are_terminal.getText()+"\n¡"+atacante.getName()+" ha robado 1 de energía a "+defensor.getName()+"!");
				are_terminal.end();
				if(defensor.equals(fighter2)) { //atacante f1
					lab_P2_ene.setText(defensor.getEnergy()+"");
				}
				else {
					lab_P1_ene.setText(defensor.getEnergy()+"");
				}
			}
			break;
		case 31:
			int ene=0;		
			if(defensor.getEnergy()!=0) {
				if(defensor.getEnergy()>=2) {
					ene=2;
				}
				else if(defensor.getEnergy()>=1) {
					ene=1;
				}
				
				int prob=(int)(Math.floor(Math.random()*100));
				if(prob<=33) {
					atacante.setEnergy(atacante.getEnergy()+ene);
					defensor.setEnergy(defensor.getEnergy()-ene);
					are_terminal.setText(are_terminal.getText()+"\n¡"+atacante.getName()+" ha robado "+ene+" de energía a "+defensor.getName()+"!");
					are_terminal.end();
					if(defensor.equals(fighter2)) { //atacante f1
						lab_P2_ene.setText(defensor.getEnergy()+"");
					}
					else {
						lab_P1_ene.setText(defensor.getEnergy()+"");
					}
				}
			}
			break;
		case 32:
			int ene2=0;		
			if(defensor.getEnergy()!=0) {
				if(defensor.getEnergy()>=2) {
					ene2=2;
				}
				else if(defensor.getEnergy()>=1) {
					ene2=1;
				}
				
				int prob=(int)(Math.floor(Math.random()*100));
				if(prob<=33) {
					atacante.setEnergy(atacante.getEnergy()+ene2);
					defensor.setEnergy(defensor.getEnergy()-ene2);
					are_terminal.setText(are_terminal.getText()+"\n¡"+atacante.getName()+" ha robado "+ene2+" de energía a "+defensor.getName()+"!");
					are_terminal.end();
					if(defensor.equals(fighter2)) { //atacante f1
						lab_P2_ene.setText(defensor.getEnergy()+"");
					}
					else {
						lab_P1_ene.setText(defensor.getEnergy()+"");
					}
				}
			}
			break;
		case 33:
			int ene3=0;		
			if(defensor.getEnergy()!=0) {
				if(defensor.getEnergy()>=2) {
					ene3=2;
				}
				else if(defensor.getEnergy()>=1) {
					ene3=1;
				}
				
				atacante.setEnergy(atacante.getEnergy()+ene3);
				defensor.setEnergy(defensor.getEnergy()-ene3);
				are_terminal.setText(are_terminal.getText()+"\n¡"+atacante.getName()+" ha robado "+ene3+" de energía a "+defensor.getName()+"!");
				are_terminal.end();
				if(defensor.equals(fighter2)) { //atacante f1
					lab_P2_ene.setText(defensor.getEnergy()+"");
				}
				else {
					lab_P1_ene.setText(defensor.getEnergy()+"");
				}
			}
			break;
		case 34:
			int prob=(int)(Math.floor(Math.random()*100));
			if(prob<=50) {
				playEffect(debuff);
				atacante.setEnergy(0);
				atacante.setAtk(1);
				atacante.setDef(1);
				atacante.setSpe(1);
				
				atacante.setAtkState(-100);
				atacante.setDefState(-100);
				atacante.setSpeState(-100);
				
				are_terminal.setText(are_terminal.getText()+"\n¡Las estadísticas de "+atacante.getName()
						+"\n han sido reducidas a 1!");
				are_terminal.end();
			}
			else {
				playEffect(buff);
				atacante.setEnergy(0);
				atacante.setAtk((int)(atacante.getAtk()*1.3));
				atacante.setDef((int)(atacante.getDef()*1.3));
				atacante.setSpe((int)(atacante.getDef()*1.3));
				
				atacante.setAtkState(atacante.getAtkState()+3);
				atacante.setDefState(atacante.getDefState()+3);
				atacante.setSpeState(atacante.getSpeState()+3);
				
				are_terminal.setText(are_terminal.getText()+"\n¡Las estadísticas de "+atacante.getName()
				+"\n han sido aumentadas en un 30 %!");
				are_terminal.end();
			}
			
			break;	
		case 36:
			int prob1=(int)(Math.floor(Math.random()*100));
			if(prob1<=33) {
				atacante.setEvade(true);
				are_terminal.setText(are_terminal.getText()+"\n¡"+atacante.getName()+" ha atacado y ha tomado"
						+ "\nposición evasiva!");
				are_terminal.end();
			}
			break;
		case 37:
			int prob2=(int)(Math.floor(Math.random()*100));
			if(prob2<=50) {
				atacante.setEvade(true);
				are_terminal.setText(are_terminal.getText()+"\n¡"+atacante.getName()+" ha atacado y ha tomado"
						+ "\nposición evasiva!");
				are_terminal.end();
			}
			break;
		case 38:
			atacante.setEvade(true);
			are_terminal.setText(are_terminal.getText()+"\n¡"+atacante.getName()+" ha atacado y ha tomado"
					+ "\nposición evasiva!");
			are_terminal.end();
			break;
		}	
	}
	
	@FXML
	public void finishBattle() {
		gri_action_buttons.setVisible(false);
		gri_action_buttons.setDisable(true);
		btn_surrender.setVisible(false);
		clip.stop();
		
	}
	
	public void reproduceMedia(String url) {
		
		
		File filestring = new File(url);
	    Media file = new Media(filestring.toURI().toString());  
	    mediaPlayer = new MediaPlayer(file);
	    mediaview.setMediaPlayer(mediaPlayer);
	    
	    mediaPlayer.setOnReady(new Runnable() {
	    	
	        @Override
	        public void run() {

	            
	            duration=(long) file.getDuration().toMillis();	
	            System.out.println("duracion en hilo de player= "+duration);
	        }
	    });
	    
	    mediaPlayer.play();
	    pan_video.setVisible(true);
	    
	    new Timer().schedule(new TimerTask() { //empieza el conteo de la imagen
		    //despues de esperar unos segundos se ejecuta lo de abajo
			
			@Override					
			public void run() {
				timer=new Timer();
				timer.schedule(new TimerTask() { //empieza el conteo de la imagen
				    //despues de esperar unos segundos se ejecuta lo de abajo
					
					
					@Override					
					public void run() {
						mediaPlayer.stop();
						
						synchronized (main) {
							main.notify();
						}
					}
				},duration);
			}
	    },500);  
	    	
	}
	
	@FXML
	public void skip() {
		mediaPlayer.stop();
		synchronized (main) {
			main.notify();
		}
		
		timer.cancel();
	}
}
