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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import gc01coursework.admin_functionality.EditTheMenu;
import gc01coursework.admin_functionality.ExportOrders;
import gc01coursework.admin_functionality.ImportOrders;
import gc01coursework.shared_functionality.ChoosingAnOrder;
import gc01coursework.shared_functionality.TakingAnOrder;
import javafx.beans.property.*;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Supervisor extends StaffMember implements Initializable {
	private static final String ADMINNAME = "ss";
	private static final String ADMINPASSWORD = "ss";
	private static final Boolean isSupervisor = true;
	public ObservableList<String> employeeNames = FXCollections.observableArrayList(); 
	public String tableClicked;
	public Boolean orderExists = false;

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
	@FXML
	private Button editMenuButton;
	@FXML
	private Button exportOrdersButton;
	@FXML
	private Button importOrdersButton;
	
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
		employeeListComboBox = new ComboBox<String>();
		//employeeListComboBox.getItems().clear();
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

	@FXML
	public void takeAnOrder(ActionEvent event) throws IOException, SAXException, ParserConfigurationException {
		
		tableClicked = ((Labeled) event.getSource()).getText();			
		File file = new File("allOrders.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document doc = documentBuilder.parse(file);

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("order");
		ArrayList<String> tablePossibilities = new ArrayList<String>();
		ArrayList<String> datePossibilities = new ArrayList<String>();

		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String tableNumber = eElement.getElementsByTagName("tablenumber").item(0).getTextContent();
				String date = eElement.getElementsByTagName("date").item(0).getTextContent();

				if(tableNumber.equals(tableClicked)) {
					tablePossibilities.add(tableNumber);
					datePossibilities.add(date);
					orderExists = true;
				}
			}
		}

		if(tablePossibilities.size() > 1) {
			
			ChoosingAnOrder multiple = new ChoosingAnOrder();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../shared_functionality/chooseAnOrderPopUp.fxml"));
			loader.setController(multiple);
			Parent choose = (Parent)loader.load();
			Scene scene = new Scene(choose);
			primaryStage.setTitle("Please choose which order you'd like to see!");
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.initOwner(table.getScene().getWindow());
			primaryStage.setScene(scene);
			
			multiple.setTheTable(tableClicked);
			multiple.initial(datePossibilities);
			primaryStage.showAndWait();
			
		} else {
			goToOrder(tableClicked, null);
		}
	}
	
	public void goToOrder(String tableClicked, String dateSelection) throws ParserConfigurationException, SAXException, IOException {
		
		TakingAnOrder newOrder = new TakingAnOrder(tableClicked);
		newOrder.providingData(tableClicked, dateSelection);
		
		Stage orderSheet = new Stage();
		FXMLLoader loaderOrder = new FXMLLoader();
		loaderOrder.setLocation(getClass().getResource("../shared_functionality/takeAnOrder.fxml"));
		loaderOrder.setController(newOrder);
		Parent takeAnOrder = (Parent)loaderOrder.load();
		Scene scene = new Scene(takeAnOrder);
		orderSheet.setTitle("Order Sheet!");
		orderSheet.initModality(Modality.APPLICATION_MODAL);
		orderSheet.setScene(scene);
		
		newOrder.reinitialize();
		orderSheet.showAndWait();
	}
	

	/**
	 * Editting The Menu!
	 *
	 * 
	 * 
	 */

	@FXML
	public void editMenu(ActionEvent event) throws IOException {
		
		EditTheMenu menuEditter = new EditTheMenu();
		Stage menuEdit = new Stage();
		FXMLLoader loaderMenu = new FXMLLoader();
		loaderMenu .setLocation(getClass().getResource("../admin_functionality/editTheMenu.fxml"));
		loaderMenu .setController(menuEditter);
		Parent editTheMenu = (Parent)loaderMenu.load();
		Scene scene = new Scene(editTheMenu);
		menuEdit.setTitle("Restaurant Menu");
		menuEdit.initModality(Modality.APPLICATION_MODAL);
		menuEdit.initOwner(editMenuButton.getScene().getWindow());
		menuEdit.setScene(scene);
		
		menuEdit.showAndWait();
	}	
	
	/**
	 * Exporting Orders!
	 *
	 * 
	 * 
	 */
	@FXML
	public void exportOrders(ActionEvent event) throws IOException {
		
		ExportOrders exporter = new ExportOrders();
		Stage orderExporting = new Stage();
		FXMLLoader exportScreen = new FXMLLoader();
		exportScreen.setLocation(getClass().getResource("../admin_functionality/exportOrders.fxml"));
		exportScreen.setController(exporter);
		Parent export = (Parent)exportScreen.load();
		Scene scene = new Scene(export);
		orderExporting.setTitle("Select Orders For Exporting!");
		orderExporting.initModality(Modality.APPLICATION_MODAL);
		orderExporting.initOwner(exportOrdersButton.getScene().getWindow());
		orderExporting.setScene(scene);
		
		orderExporting.showAndWait();
	}
	
	/**
	 * Importing Orders!
	 *
	 * 
	 * 
	 */
	
	@FXML
	public void importOrders(ActionEvent event) throws IOException {
		
		ImportOrders importer = new ImportOrders();
		Stage orderImporting = new Stage();
		FXMLLoader importScreen = new FXMLLoader();
		importScreen.setLocation(getClass().getResource("../admin_functionality/importOrders.fxml"));
		importScreen.setController(importer);
		Parent imports = (Parent)importScreen.load();
		Scene scene = new Scene(imports);
		orderImporting.setTitle("Select Orders For Exporting!");
		orderImporting.initModality(Modality.APPLICATION_MODAL);
		orderImporting.initOwner(importOrdersButton.getScene().getWindow());
		orderImporting.setScene(scene);
		
		orderImporting.showAndWait();
	}
}


	


