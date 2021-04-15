package models.P_Attack;

import java.io.Serializable;

public class Attack implements Serializable{

	private String name;
	private int power;
	private int cd;

	private int id;
	private int hit_rate;

	public Attack(int id,String name, int power, int cd, int hit) {
		super();
		this.id=id;
		this.name = name;
		this.power = power;
		this.cd = cd;
		this.hit_rate=hit;
	}

	public Attack() {
		this(0,"",0,0,0);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPower() {
		return this.power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getCd() {
		return this.cd;
	}

	public void setCd(int cd) {
		this.cd = cd;
	}

	public int getHit_rate() {
		return this.hit_rate;
	}

	public void setHit_rate(int hit_rate) {
		this.hit_rate=hit_rate;
	}

	@Override
	public String toString() {
		return this.name;
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
		if (!(obj instanceof Attack))
			return false;
		Attack other = (Attack) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
}
