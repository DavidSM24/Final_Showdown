package models.P_Character;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CharacterDAO {
	private ObservableList<Character> characters;
	private static CharacterDAO instance_C;
	
	private CharacterDAO() {
		super();
		this.characters = FXCollections.observableArrayList(); //crea lista vacía
	}

	private CharacterDAO(ObservableList<Character> characters) {
		super();
		this.characters = characters;
	}
	
	

	public static CharacterDAO getInstance () {
		if(instance_C==null) {
			instance_C=new CharacterDAO();
		}
		return instance_C;
	}
	
	public static CharacterDAO getInstance (ObservableList<Character> lista) { //carga directamente unos datos en el DAO
		if(instance_C==null) {
			instance_C=new CharacterDAO(lista);
		}
		return instance_C;
	}
	
	public ObservableList<Character> getAllCharacters(){
		return characters;
	}
}
