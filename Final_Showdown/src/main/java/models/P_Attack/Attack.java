package models.P_Attack;

import java.io.Serializable;

public class Attack{

	private int id;
	private String name;
	private int power;
	private int cost;
	private int hit_rate;
	private Extra extra;
	private String photo;
	private String animation;
	private String media;
	private int id_user;

	public Attack(int id,String name, int power, int cost, int hit, Extra extra,String photo, String animation, String media, int id_user) {
		super();
		this.id=id;
		this.name = name;
		this.power = power;
		this.cost = cost;
		this.hit_rate=hit;
		this.extra=extra;
		this.photo=photo;
		this.animation=animation;
		this.media=media;
		this.id_user=id_user;
	}

	public Attack() {
		this(0,"",0,0,0,null,"","","",-1);
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
	
	public Extra getExtra() {
		return extra;
	}

	public void setExtra(Extra extra) {
		this.extra = extra;
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

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	@Override
	public String toString() {
		return this.name;
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
