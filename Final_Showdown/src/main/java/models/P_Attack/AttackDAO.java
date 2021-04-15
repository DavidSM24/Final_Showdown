package models.P_Attack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import models.P_Character.Rol;

public class AttackDAO implements Serializable{
	private ObservableList<Attack> attacks;
	private static AttackDAO instance_A;
	
	private AttackDAO(ObservableList<Attack> attacks) {
		super();
		this.attacks = attacks;
	}
	
	private AttackDAO() {
		super();
		this.attacks = FXCollections.observableArrayList(); //crea lista vacía
	}
	
	public static AttackDAO getInstance () {
		if(instance_A==null) {
			instance_A=new AttackDAO();
		}
		return instance_A;
	}
	
	public static AttackDAO getInstance (ObservableList<Attack> lista) { //carga directamente unos datos en el DAO
		if(instance_A==null) {
			instance_A=new AttackDAO(lista);
		}
		return instance_A;
	}
	
	public void addNewAttack(Attack newA) {
		if (newA != null && attacks != null)
			this.attacks.add(newA);
	}
	
	public ObservableList<Attack> getAllAttacks(){
		return attacks;
	}
	
	public List<Attack> getAllAta(){
		List<Attack> result=null;
		if(attacks!=null&&attacks.size()>0) {
			result=new ArrayList<Attack>();
			for(Attack a:attacks) {
				result.add(a);
			}
			
		}
		return result;
	}
	
	public void saveFile() {
		try {
			FileOutputStream fos = new FileOutputStream(new File("Ataques.dat"));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.getAllAta());
			oos.flush();
			oos.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadFile(){

		try {
			FileInputStream fi=new FileInputStream("Ataques.dat");
			ObjectInputStream oi=new ObjectInputStream(fi);
			List<Attack> lista=new ArrayList<Attack>();
			ObservableList<Attack>aux=FXCollections.observableArrayList();
			lista=(List<Attack>) oi.readObject();
			if(lista!=null&&lista.size()>0) {
				for(Attack a:lista) {
					aux.add(a);
				}
				attacks=aux;
			}
			oi.close();
		} catch (FileNotFoundException e) {
			System.out.println("\n No se ha cargado ningún ataque. No hay ataques que cargar.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
