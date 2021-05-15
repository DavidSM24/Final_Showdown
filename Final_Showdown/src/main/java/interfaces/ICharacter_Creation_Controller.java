package interfaces;

import Final_Showdown.Character_Creation_Controller;
import Final_Showdown.PrimaryController;
import models.P_Character.Character;

public interface ICharacter_Creation_Controller {

	public void setController(PrimaryController dad, Character_Creation_Controller me, Character chara);
	public void add();
	public void cancel();
	public void set_Presentation();
	public void set_Card();
	public void set_ost();
	public void updateRolStats();
	public void updateTextSHP();
	public void updateTextSATK();
	public void updateTextSDEF();
	public void updateTextSSPE();
	public void generateCharacter();
}
