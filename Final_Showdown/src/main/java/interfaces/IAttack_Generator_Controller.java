package interfaces;

import Final_Showdown.Attack_Generator_Controller;
import Final_Showdown.Character_Creation_Controller;
import models.P_Attack.Attack;

public interface IAttack_Generator_Controller {

	public void setController(Character_Creation_Controller dad, Attack_Generator_Controller me, Attack a);
	public void save();
	public void cancel();
	public void set_Attack_Image();
	public void updateExtraDescription();
}
