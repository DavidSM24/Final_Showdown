package models.P_Character;

import models.P_Attack.Attack;

public class Character {
	private int id;
	private String name;
	private String universe;
	private String description;
	private String band;
	private int hp;
	private int energy_ini;
	private int energy_recover;
	private int atk;
	private int def;
	private int spe;
	private Attack a1;
	private Attack a2;
	private Attack a3;
	private Rol rol;
	private String photo_face;
	private String photo_card;
	private String ost;
	private int id_user;
	
	public Character(int id, String universe, String name, String description, String band, int hp, int energy_ini, int energy_recover, int atk, int def, int spe, Attack a1, Attack a2, Attack a3,
			Rol rol, String photo_face, String photo_card, String ost, int id_user) {
		super();
		this.id = id;
		this.name = name;
		this.universe=universe;
		this.description=description;
		this.band=band;
		this.hp = hp;
		this.energy_ini=energy_ini;
		this.energy_recover=energy_recover;
		this.atk = atk;
		this.def = def;
		this.spe = spe;
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
		this.rol = rol;
		this.photo_face=photo_face;
		this.photo_card=photo_card;
		this.ost=ost;
		this.id_user=id_user;
	}

	public Character() {
		this(0,"","","","",0,0,0,0,0,0,null,null,null,null,"","","",-1);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getUniverse() {
		return universe;
	}

	public void setUniverse(String universe) {
		this.universe = universe;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBand() {
		return band;
	}

	public void setBand(String band) {
		this.band = band;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
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
	
	public String getPhoto_face() {
		return photo_face;
	}

	public void setPhoto_face(String photo_face) {
		this.photo_face = photo_face;
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

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	@Override
	public String toString() {
		return name+" ("+universe+")";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Character))
			return false;
		Character other = (Character) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
	
	
}
