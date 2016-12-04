package gc01coursework.users_and_login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import gc01coursework.admin_functionality.Orders;
import gc01coursework.shared_functionality.TakingAnOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Supervisor extends StaffMember implements Initializable {
	private static final String ADMINNAME = "ss";
	private static final String ADMINPASSWORD = "ss";
	private static final Boolean isSupervisor = true;
	public ObservableList<String> employeeNames = FXCollections.observableArrayList(); 
	protected String tableClicked;

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
	@FXML
	private Button deleteEmployeeButton;
	@FXML
	private ComboBox<String> employeeListComboBox;
	@FXML
	private Button table;
	@FXML
	private Button takeAnOrderButton;
	@FXML
	private Button okayToSelectTableButton;
	
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
	
	
	public String getTableClicked() {
		return tableClicked;
	}

	private void setTableClicked(String tableClicked) {
		this.tableClicked = tableClicked;
	}

	@Override
	protected void setUsername(String username) {
		System.out.println("Admin name cannot be changed.");
	}

	@Override
	protected void setPassword(String password) {
		System.out.println("Admin password cannot be changed.");
	}

	/**
	 * Adding a New Employee!
	 *
	 * 
	 * 
	 */

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

			String contentForFile = "\nUsername: " + employeeUsername.getText() + ", Password: " + employeePassword.getText() + ", Date Added: " + dateFormat.format(date);
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

	
	
	/**
	 * Deleting an Employee!
	 *
	 * 
	 * 
	 */
	@FXML
	public void deleteEmployee(ActionEvent event) throws IOException {
		//Creating the Pop-Up Modal:
		Stage primaryStage = new Stage();
		Parent addEmployeePopUp = FXMLLoader.load(getClass().getResource("../admin_functionality/deleteEmployeePopUp.fxml"));

		Scene scene = new Scene(addEmployeePopUp);
		primaryStage.setTitle("Delete an Employee");
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.initOwner(addEmployeeButton.getScene().getWindow());
		primaryStage.setScene(scene);
		primaryStage.show();


	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("./restaurant_staff_logins.txt"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());

				if((line = br.readLine()) != null) {
					String eachEmployee = line.substring(line.indexOf("Username:") + 10, line.indexOf(","));
					employeeNames.add(eachEmployee);
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(employeeNames);
		employeeListComboBox = new ComboBox<String>(employeeNames);
		//employeeListComboBox.setItems(employeeNames);		
	}
	
	/**
	 * Taking An Order!
	 *
	 * 
	 * 
	 */

	@FXML
	public void takeAnOrderChooseATable(ActionEvent event) throws IOException {

		//Creating the Pop-Up Modal:
		Stage primaryStage = new Stage();
		Parent chooseATablePopUp = FXMLLoader.load(getClass().getResource("../admin_functionality/chooseATablePopUp.fxml"));
		Scene scene = new Scene(chooseATablePopUp);
		primaryStage.setTitle("Please click on a Table");
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.initOwner(takeAnOrderButton.getScene().getWindow());
		primaryStage.setScene(scene);
		primaryStage.showAndWait();
	}


	@FXML
	public void okayToSelectTable(ActionEvent event) throws IOException {
		Stage stage = (Stage) okayToSelectTableButton.getScene().getWindow();
		stage.close();
	}
	
	public void takeAnOrder(ActionEvent event) throws IOException {
		
		tableClicked = ((Labeled) event.getSource()).getText();
		System.out.println(tableClicked + "YOOOOOOOO___________");

		Stage orderSheet = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../shared_functionality/takeAnOrder.fxml"));
		Parent takeAnOrder = (Parent)loader.load();
//		Parent takeAnOrder = FXMLLoader.load(getClass().getResource("../shared_functionality/takeAnOrder.fxml"));
		Scene scene = new Scene(takeAnOrder);
		
		TakingAnOrder gagag = (TakingAnOrder) loader.getController();

//		loader.setLocation(JfxUtils.class.getResource(fxml));

		orderSheet.setTitle("Order Sheet!");
		orderSheet.setScene(scene);
		orderSheet.show();
		
//		TakingAnOrder newOrder = new TakingAnOrder();
		gagag.initializeOrder(tableClicked);
	}
 	
}


	


