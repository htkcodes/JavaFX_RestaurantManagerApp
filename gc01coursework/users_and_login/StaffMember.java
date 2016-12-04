package gc01coursework.users_and_login;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.stage.Stage;

public class StaffMember {
	protected static String username;
	protected static String password;
	protected static String lastLogin;
	
	public StaffMember(String username, String password, String lastLogin) {
		this.username = username;
		this.password = password;
		this.lastLogin = lastLogin;
	}

	protected String getUsername() {
		return username;
	}

	protected String getPassword() {
		return password;
	}

	protected String getLastLogin() {
		return lastLogin;
	}

	protected void setUsername(String username) {
		this.username = username;
	}

	protected void setPassword(String password) {
		this.password = password;
	}

	protected void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public void initialize() {	
		System.out.println("Implemented here so it could be used in Supervisor.");
	}
}
