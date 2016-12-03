package gc01coursework.users_and_login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import gc01coursework.users_and_login.Supervisor;
import javafx.scene.control.TextField;

public class Login {

	private static Boolean isStaffMember;
	private static Boolean accessGranted;

	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private Button loginButton;
	@FXML
	private Label loginStatus;

	public Boolean verifyLoginDetails(ActionEvent event) throws IOException {
		Supervisor supervisor = new Supervisor();

		if(username.getText().equals(supervisor.getUsername()) && password.getText().equals(supervisor.getPassword())) {
			accessGranted = true;
			
			Stage primaryStage = new Stage();
			Parent supervisorDashboard = FXMLLoader.load(getClass().getResource("../dashboard/SupervisorDashboard.fxml"));
			Scene scene = new Scene(supervisorDashboard);
			scene.getStylesheets().add(getClass().getResource("../style/Dashboard.css").toExternalForm());
			primaryStage.setTitle("Supervisor Dashboard!");
			primaryStage.setScene(scene);
			primaryStage.show();

			Stage stage = (Stage) loginButton.getScene().getWindow();
			stage.close();
		} else if(checkIfStaffMember(username.getText(), password.getText(), "./restaurant_staff_logins.txt")) {
			System.out.println("CHECKING");
			accessGranted = true;
			
			Stage primaryStage = new Stage();
			Parent supervisorDashboard = FXMLLoader.load(getClass().getResource("../dashboard/EmployeeDashboard.fxml"));
			Scene scene = new Scene(supervisorDashboard);
			scene.getStylesheets().add(getClass().getResource("../style/Dashboard.css").toExternalForm());
			primaryStage.setTitle("Employee Dashboard!");
			primaryStage.setScene(scene);
			primaryStage.show();

			Stage stage = (Stage) loginButton.getScene().getWindow();
			stage.close();
			
		} else {
			accessGranted = false;
		
			loginStatus.setText("Login failed! Please try again.");
		}
		
		return accessGranted;
	}


	public Boolean checkIfStaffMember(String username, String password, String filename) throws IOException {
		isStaffMember = false;

		BufferedReader br = new BufferedReader(new FileReader(filename));

		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();

				String correctDetails = "Username: " + username + ", Password: " + password;

				if((line = br.readLine()) != null && line.contains(correctDetails)) {
					isStaffMember = true;
				} 
			}
		} catch (Exception e) {
			  e.printStackTrace();
		} finally {
			br.close();
		}
		return isStaffMember;
	}
}
