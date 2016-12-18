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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import gc01coursework.admin_functionality.EditTheMenu;
import gc01coursework.admin_functionality.ExportOrders;
import gc01coursework.admin_functionality.ImportOrders;
import gc01coursework.admin_functionality.ManageEmployees;
import gc01coursework.admin_functionality.ModifyMenu;
import gc01coursework.shared_functionality.ChoosingAnOrder;
import gc01coursework.shared_functionality.SearchOrders;
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
	private static Boolean isStaff;
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
	@FXML
	private Button searchOrdersButton;
	
	public Supervisor() {
		super(username, password, lastLogin);
	}

	protected static Boolean getIsStaff() {
		return isStaff;
	}

	protected static void setIsStaff(Boolean isStaff) {
		Supervisor.isStaff = isStaff;
		System.out.println("SETTING" + isStaff);
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
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if(getIsStaff()) {
			addEmployeeButton.setDisable(true);
			deleteEmployeeButton.setDisable(true);
			editMenuButton.setDisable(true);
			exportOrdersButton.setDisable(true);
			importOrdersButton.setDisable(true);
		}
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
	public void saveEmployee(ActionEvent event) throws IOException, ParserConfigurationException, SAXException, TransformerException {
		//Getting the date so we can write the score to our file.
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String dateAdded = dateFormat.format(date);
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		Document xmlDoc = docBuilder.parse("staff.xml");	

		Element root = xmlDoc.getDocumentElement();

		Element staff = xmlDoc.createElement("staff");

		Text staffText = xmlDoc.createTextNode(employeeUsername.getText());
		Element staffName = xmlDoc.createElement("staffName");	
		Element staffPassword = xmlDoc.createElement("staffPassword");	
		Text staffPasswordText = xmlDoc.createTextNode(employeePassword.getText());
		Element staffAdded = xmlDoc.createElement("dateAdded");	
		Text staffAddedText = xmlDoc.createTextNode(dateAdded);
		Element staffLogins = xmlDoc.createElement("loginActivity");
		Text staffLoginText = xmlDoc.createTextNode("firstcreated");

		staffName.appendChild(staffText);
		staffPassword.appendChild(staffPasswordText);
		staffAdded.appendChild(staffAddedText);
		staffLogins.appendChild(staffLoginText);
		
		staff.appendChild(staffName);
		staff.appendChild(staffPassword);
		staff.appendChild(staffAdded);
		staff.appendChild(staffLogins);

		root.appendChild(staff);

		DOMSource source = new DOMSource(xmlDoc);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		StreamResult result = new StreamResult("staff.xml");
		transformer.transform(source, result);
	}

	@FXML
	public void cancelAddingEmployee(ActionEvent event) throws IOException {
		Stage stage = (Stage) saveNewEmployee.getScene().getWindow();
		stage.close();
	} 



	/**
	 * Deleting an Employee!
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 *
	 * 
	 * 
	 */
	
	private void updateComboBoxOfNames() throws ParserConfigurationException, SAXException, IOException {

	}
	
	@FXML
	public void deleteEmployee(ActionEvent event) throws IOException, ParserConfigurationException, SAXException {
		
		Stage manageEmployees = new Stage();
		ManageEmployees modify = new ManageEmployees();
		
		FXMLLoader manageScreen = new FXMLLoader();
		manageScreen.setLocation(getClass().getResource("../admin_functionality/deleteEmployeePopUp.fxml"));
		manageScreen.setController(modify);
		Parent imports = (Parent)manageScreen.load();
		Scene scene = new Scene(imports);
		manageEmployees.setTitle("Manage Employees");
		manageEmployees.initModality(Modality.APPLICATION_MODAL);
		manageEmployees.setScene(scene);
		
		manageEmployees.showAndWait();
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
	
	/**
	 * Search Orders!
	 * @throws IOException 
	 *
	 * 
	 * 
	 */
	
	@FXML
	private void searchOrders() throws IOException {
		
		Stage orderSearch = new Stage();
		SearchOrders search = new SearchOrders();
		
		FXMLLoader searchScreen = new FXMLLoader();
		searchScreen.setLocation(getClass().getResource("../shared_functionality/searchOrders.fxml"));
		searchScreen.setController(search);
		Parent imports = (Parent)searchScreen.load();
		Scene scene = new Scene(imports);
		orderSearch.setTitle("Select Orders For Exporting!");
		orderSearch.initModality(Modality.APPLICATION_MODAL);
		orderSearch.initOwner(searchOrdersButton.getScene().getWindow());
		orderSearch.setScene(scene);

		orderSearch.showAndWait();
	}
}


	


