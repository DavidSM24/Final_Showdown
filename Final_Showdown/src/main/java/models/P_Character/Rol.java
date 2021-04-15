package models.P_Character;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "rol")
@XmlAccessorType (XmlAccessType.FIELD)
public class Rol implements Serializable{
	
	private int id;
	private String name;
	private int hp_base;
	private int atk_base;
	private int def_base;
	private int spe_base;

	public Rol (int id, String name, int hp_base, int atk_base, int def_base, int spe_base) {
		super();
		this.id=id;
		this.name = name;
		this.hp_base = hp_base;
		this.atk_base = atk_base;
		this.def_base = def_base;
		this.spe_base = spe_base;
	}
	
	public Rol () {
		this(0,"",0,0,0,0);
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHp_base() {
		return this.hp_base;
	}

	public void setHp_base(int hp_base) {
		this.hp_base = hp_base;
	}

	public int getAtk_base() {
		return this.atk_base;
	}

	public void setAtk_base(int atk_base) {
		this.atk_base = atk_base;
	}

	public int getDef_base() {
		return this.def_base;
	}

	public void setDef_base(int def_base) {
		this.def_base = def_base;
	}

	public int getSpe_base() {
		return this.spe_base;
	}

	public void setSpe_base(int spe_base) {
		this.spe_base = spe_base;
	}

	@Override
	public String toString() {
		return this.getName();
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
		if (!(obj instanceof Rol))
			return false;
		Rol other = (Rol) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
	
	
}
