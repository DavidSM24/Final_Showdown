package P_Game;

import java.sql.Timestamp;

public class Sesion {
	private int id;
	private int id_user;
	private Timestamp time;
	
	public Sesion(int id, int id_user, Timestamp time) {
		super();
		this.id = id;
		this.id_user = id_user;
		this.time = time;
	}

	public Sesion() {
		this(-1,-1, null);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp timestamp) {
		this.time = timestamp;
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", id_user=" + id_user + ", time=" + time + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Sesion))
			return false;
		Sesion other = (Sesion) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
