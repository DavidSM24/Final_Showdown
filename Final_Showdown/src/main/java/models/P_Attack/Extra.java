package models.P_Attack;

import java.io.Serializable;

public class Extra implements Serializable{
	private int id;
	private String name;
	private String description;
	
	public Extra(int id, String name,String description) {
		super();
		this.id = id;
		this.name=name;
		this.description = description;
	}

	public Extra() {
		this(0,"?","?");
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		if (!(obj instanceof Extra))
			return false;
		Extra other = (Extra) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	
	
	
}
