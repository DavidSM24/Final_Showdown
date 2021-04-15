package models.P_Attack;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class AttackDAO {
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
	
	public ObservableList<Attack> getAllAttacks(){
		return attacks;
	}
	
	
}
