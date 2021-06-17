package P_Game;

public class Scenary {
	private int id;
	private String name;
	private String resource;
	private int id_user;
	
	public Scenary(int id, String name, String resource, int id_user) {
		super();
		this.id = id;
		this.name = name;
		this.resource = resource;
		this.id_user=id_user;
	}
	
	public Scenary() {
		this(0,"","",0);
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
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
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
		if (!(obj instanceof Scenary))
			return false;
		Scenary other = (Scenary) obj;
		if (id != other.id)
			return false;
		return true;
	}	
}
