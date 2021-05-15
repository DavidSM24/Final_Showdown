package interfaces;

import Final_Showdown.PrimaryController;

public interface IPrimaryController {
	public void setController (PrimaryController me);
	public void battle();
	public void changeRandom_1();
	public void changeRandom_2();
	public void play();
	public void stop();
	public void createCharacter();
	public void editCharacter();
	public void deleteCharacter();
	public void createAttack();
	public void editAttack();
	public void deleteAttack();
	public void select_Character();
	public void select_Attack();
	public void setTableAndDetailsInfo();
	public void filter_Characters();
	public void filter_Attacks();
	public void updateFightersInfo();
}
