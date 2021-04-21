package models.P_Attack;

import java.io.Serializable;

public class Attack{

	private int id;
	private String name;
	private int power;
	private int cost;
	private int hit_rate;
	private int id_extra;
	private String photo;
	private String animation;

	public Attack(int id,String name, int power, int cost, int hit, int id_extra,String photo, String animation) {
		super();
		this.id=id;
		this.name = name;
		this.power = power;
		this.cost = cost;
		this.hit_rate=hit;
		this.id_extra=id_extra;
		this.photo=photo;
		this.animation=animation;
	}

	public Attack() {
		this(0,"",0,0,0,0,"","");
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

	public int getCost() {
		return this.cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getHit_rate() {
		return this.hit_rate;
	}

	public void setHit_rate(int hit_rate) {
		this.hit_rate=hit_rate;
	}
	
	public int getId_extra() {
		return id_extra;
	}

	public void setId_extra(int id_extra) {
		this.id_extra = id_extra;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getAnimation() {
		return animation;
	}

	public void setAnimation(String animation) {
		this.animation = animation;
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
