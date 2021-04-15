package models.P_Character;

import models.P_Attack.Attack;

public class Character {
	private int id;
	private String name;
	private int hp;
	private int atk;
	private int def;
	private int spe;
	private Attack a1;
	private Attack a2;
	private Attack a3;
	private Rol rol;
	
	private Character(int id, String name, int hp, int atk, int def, int spe, Attack a1, Attack a2, Attack a3,
			Rol rol) {
		super();
		this.id = id;
		this.name = name;
		this.hp = hp;
		this.atk = atk;
		this.def = def;
		this.spe = spe;
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
		this.rol = rol;
	}

	private Character() {
		this(0,"",0,0,0,0,null,null,null,null);
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

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
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

	@Override
	public String toString() {
		return "Character [id=" + id + ", name=" + name + ", hp=" + hp + ", atk=" + atk + ", def=" + def + ", spe="
				+ spe + ", a1=" + a1 + ", a2=" + a2 + ", a3=" + a3 + ", rol=" + rol + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
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
