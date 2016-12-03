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
			primaryStage.setTitle("Rachel's Restaurant Manager!");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			Stage stage = (Stage) loginButton.getScene().getWindow();
		    stage.close();	
//		} else if(checkIfStaffMember(getUsername(), getPassword())) {
//			accessGranted = true;
		} else {
			accessGranted = false;
		}
		return accessGranted;
	}


	public Boolean checkIfStaffMember(String username, String password) {
		isStaffMember = false;

		///read the file!
		return isStaffMember;
	}
}
