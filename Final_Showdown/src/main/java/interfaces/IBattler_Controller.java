package interfaces;

import java.io.File;

import P_Game.Scenary;
import models.P_Attack.Attack;
import models.P_Character.Fighter;

public interface IBattler_Controller {
	
	public void setController(Fighter fighter1, Fighter fighter2, Scenary scenary, boolean multiplayer);
	public void startBattle();
	public void turn();
	public void useAttack1();
	public void useAttack2();
	public void useAttack3();
	public void useBlock();
	public void useEvade();
	public int IAChoose();
	public void action(int actionP2);
	public void p1Attack();
	public void p2Attack();
	public int calculateCriticPercent(Fighter f);
	public void playEffect(File f);
	public void playAnimation(String animation);
	public void showInfoP1();
	public void showInfoP2();
	public void showInfoA1();
	public void showInfoA2();
	public void showInfoA3();
	public void showInfoBlock();
	public void showInfoEvade();
	public void closeInfos();
	public void calculateExtra(Fighter atacante, Fighter defensor, Attack awe);
	public void finishBattle();
}
