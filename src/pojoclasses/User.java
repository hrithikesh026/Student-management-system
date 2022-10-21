package pojoclasses;

import java.sql.Timestamp;

public class User {
	private String username;
	private String pass;
	private Timestamp createdTimestamp;
	
	public User(String username, String pass, Timestamp createdTimestamp){
		this.username = username;
		this.pass = pass;
		this.createdTimestamp = createdTimestamp;
	}
	
	public String getPass() {
		return pass;
	}
	public String getUsername() {
		return username;
	}
	public Timestamp getCreatedTimestamp() {
		return createdTimestamp;
	}
	
	public String toString() {
		return "[username="+username+", pass="+pass+", createdTimestamp="+createdTimestamp+"]";
	}
}
