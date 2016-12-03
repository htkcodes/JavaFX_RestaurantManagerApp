package gc01coursework.users_and_login;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Supervisor extends StaffMember {
	private static final String ADMINNAME = "ss";
	private static final String ADMINPASSWORD = "ss";
	private static final Boolean isSupervisor = true;

	@FXML
	private Button addEmployeeButton;

	@FXML 
	private TextField employeeUsername;

	@FXML 
	private TextField employeePassword;

	@FXML
	private Button saveNewEmployee;

	@FXML
	private Button cancelAddingEmployee;

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

	@FXML
	public void addEmployee(ActionEvent event) throws IOException {

		//Creating the Pop-Up Modal:
		Stage primaryStage = new Stage();
		Parent addEmployeePopUp = FXMLLoader.load(getClass().getResource("../admin_functionality/addEmployeePopUp.fxml"));
		Scene scene = new Scene(addEmployeePopUp);
		primaryStage.setTitle("Add A New Employee");
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.initOwner(addEmployeeButton.getScene().getWindow());
		primaryStage.setScene(scene);
		primaryStage.showAndWait();
	}
	
	
	@FXML
	public void saveEmployee(ActionEvent event) throws IOException {
		Employee newEmployee = new Employee(employeeUsername.getText(), employeePassword.getText(), lastLogin);

		try{
			File file = new File("./restaurant_staff_logins.txt");

			if(!file.exists()){
				file.createNewFile();	//If file doesn't exist, create it.
				System.out.println("File doesnt exist");
			}

			//Getting the date so we can write the score to our file.
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();

			String contentForFile = "\nUsername: " + employeeUsername.getText() + ", Password:" + employeePassword.getText() + ", Date Added: " + dateFormat.format(date);
			FileWriter fileWritter = new FileWriter(file.getName(),true);	//The second argument 'true' means don't overwrite existing content.
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(contentForFile);
			bufferWritter.close();
			System.out.println("New employee details added. _____________");

			//Going back to parent window. 
			Stage stage = (Stage) saveNewEmployee.getScene().getWindow();
		    stage.close();
		    
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	@FXML
	public void cancelAddingEmployee(ActionEvent event) throws IOException {
		Stage stage = (Stage) saveNewEmployee.getScene().getWindow();
	    stage.close();
	} 
}

