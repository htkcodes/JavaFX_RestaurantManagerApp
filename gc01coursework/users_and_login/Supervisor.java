package gc01coursework.users_and_login;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Supervisor extends StaffMember {
	private static final String ADMINNAME = "ss";
	private static final String ADMINPASSWORD = "ss";
	private static final Boolean isSupervisor = true;
	
	public Supervisor() {
		super(username, password, lastLogin);
	}
	
	@Override
	protected String getUsername() {
		return ADMINNAME;
	}
	
	@Override
	protected String getPassword() {
		return ADMINPASSWORD;
	}
	
	@Override
	protected void setUsername(String username) {
		System.out.println("Admin name cannot be changed.");
	}
	
	@Override
	protected void setPassword(String password) {
		System.out.println("Admin password cannot be changed.");
	}

	
	
	public void addEmployee(String username, String password, String lastLogin) {
		
		Employee newEmployee = new Employee(username, password, lastLogin);
		
		try{
			File file = new File("restaurant_staff_logins.txt");

			if(!file.exists()){
				file.createNewFile();	//If file doesn't exist, create it.
			}

			String contentForFile = "\nUsername: " + username + " , Password:" + password;
			FileWriter fileWritter = new FileWriter(file.getName(),true);	//The second argument 'true' means don't overwrite existing content.
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(contentForFile);
			bufferWritter.close();
			System.out.println("User details added. _____________");

		} catch(IOException e){
			e.printStackTrace();
		}
	}

}
