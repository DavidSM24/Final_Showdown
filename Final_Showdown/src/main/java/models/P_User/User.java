package models.P_User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class User {

	
	int id;
	String mail;
	String username;
	String password;
	String usercode;
	boolean confirmed;
	
	public User(int id, String mail, String username, String password, String usercode, boolean confirmed) {
		super();
		this.mail=mail;
		this.id = id;
		this.username = username;
		this.password = password;
		this.usercode = usercode;
		this.confirmed = confirmed;
	}

	public User() {
		this(0, "","","","",false);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	
	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	@Override
	public String toString() {
		return this.username;
	}
	
	
}
