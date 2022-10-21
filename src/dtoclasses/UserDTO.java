package dtoclasses;

public class UserDTO {
	private String username;
	private String pass;
	
	public UserDTO(String username, String pass){
		this.username = username;
		this.pass = pass;
	}
	
	public String getPass() {
		return pass;
	}
	public String getUsername() {
		return username;
	}
	
	public String toString() {
		return "[username="+username+", pass="+pass+"]";
	}
}
