package models.P_Character;

import models.P_Attack.Attack;

public class Fighter extends Character{

	private String name;
	private int priority;
	private int hp_total;
	private int hp_current;
	private double hp_percentage;
	private int energy;
	private int energy_ini;
	private int energy_recover;
	private int atk;
	private int def;
	private int spe;
	private Attack a1;
	private Attack a2;
	private Attack a3;
	private Rol rol;
	private String photo_card;
	private String ost;
	private int action;
	private boolean evade;
	private boolean block;
	int AtkState;
	int DefState;
	int SpeState;
	
	public Fighter (Character c) {
		this.priority=0;
		this.name=c.getName();
		this.hp_total=c.getHp();
		this.hp_current=c.getHp();
		this.hp_percentage=1.0;
		this.energy=0;
		this.energy_ini=c.getEnergy_ini();
		System.out.println(c.getEnergy_ini());
		this.energy_recover=c.getEnergy_recover();
		this.atk=c.getAtk();
		this.def=c.getDef();
		this.spe=c.getSpe();
		this.a1=c.getA1();
		this.a2=c.getA2();
		this.a3=c.getA3();
		this.rol=c.getRol();
		this.photo_card=c.getPhoto_card();
		this.ost=c.getOst();
		this.AtkState=0;
		this.DefState=0;
		this.SpeState=0;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getHp_total() {
		return hp_total;
	}

	public void setHp_total(int hp_total) {
		this.hp_total = hp_total;
	}

	public int getHp_current() {
		return hp_current;
	}

	public void setHp_current(int hp_current) {
		this.hp_current = hp_current;
	}
	
	public double getHp_percentage() {
		return hp_percentage;
	}

	public void setHp_percentage(double hp_percentage) {
		this.hp_percentage = hp_percentage;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getEnergy_ini() {
		return energy_ini;
	}

	public void setEnergy_ini(int energy_ini) {
		this.energy_ini = energy_ini;
	}

	public int getEnergy_recover() {
		return energy_recover;
	}

	public void setEnergy_recover(int energy_recover) {
		this.energy_recover = energy_recover;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public int getSpe() {
		return spe;
	}

	public void setSpe(int spe) {
		this.spe = spe;
	}

	public Attack getA1() {
		return a1;
	}

	public void setA1(Attack a1) {
		this.a1 = a1;
	}

	public Attack getA2() {
		return a2;
	}

	public void setA2(Attack a2) {
		this.a2 = a2;
	}

	public Attack getA3() {
		return a3;
	}

	public void setA3(Attack a3) {
		this.a3 = a3;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getPhoto_card() {
		return photo_card;
	}

	public void setPhoto_card(String photo_card) {
		this.photo_card = photo_card;
	}

	public String getOst() {
		return ost;
	}

	public void setOst(String ost) {
		this.ost = ost;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public boolean isEvade() {
		return evade;
	}

	public void setEvade(boolean evade) {
		this.evade = evade;
	}

	public boolean isBlock() {
		return block;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}

	public int getAtkState() {
		return AtkState;
	}

	public void setAtkState(int atkState) {
		AtkState = atkState;
	}

	public int getDefState() {
		return DefState;
	}

	public void setDefState(int defState) {
		DefState = defState;
	}

	public int getSpeState() {
		return SpeState;
	}

	public void setSpeState(int speState) {
		SpeState = speState;
	}
}
