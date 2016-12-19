/**
 * <h2>This is the 'User' class, and controls all functionality on the dashboard (which appears following successful login)</h2>
 * 
 * @author Rachel Slater
 * @since December 2016
 * 
 * <p> This class essentially controls the restaurant dashboard, and directs traffic around the application when buttons are clicked. </p>
 * 
 */

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
import java.util.Optional;
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

import gc01coursework.Main;
import gc01coursework.admin_functionality.Menu;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The Class 'User'.
 * It inherits from the 'StaffMember' class.
 * It implements the 'Initializable' which is a controller initialization interface.
 */

public class User implements Initializable {
	
	private static final String ADMINNAME = "ss";		//The Supervisor's login details.
	private static final String ADMINPASSWORD = "ss";
	private static Boolean isStaff;
	public ObservableList<String> employeeNames = FXCollections.observableArrayList(); 
	public String tableClicked;
	public Boolean orderExists = false;
	private Stage dashBoardStage;

	@FXML private Button addEmployeeButton, saveNewEmployee, cancelAddingEmployee, manageEmployeeButton, table, manageMenuButton, exportOrdersButton, importOrdersButton, searchOrdersButton, logoutButton;
	@FXML private TextField employeeUsername, employeePassword;
	
	/**
	 * Constructor - instantiates a new user.
	 */
	public User() {
		super();
	}
	
	/**
	 * Getter for checking access rights.
	 * @return Boolean isStaff - if true, some functionality is disabled. If false, all functionality is enabled.
	 */
	protected static Boolean getIsStaff() {
		return isStaff;
	}

	/**
	 * Setter for access rights.
	 * This method is called in 'Login.java' when the user logs in.
	 * @param isStaff this Boolean is set when the user logs in, depending on their login credentials.
	 */
	protected static void setIsStaff(Boolean isStaff) {
		User.isStaff = isStaff;
	}

	protected String getUsername() {
		return ADMINNAME;
	}

	protected String getPassword() {
		return ADMINPASSWORD;
	}
	
	protected void setUsername(String username) {
		System.out.println("Admin name cannot be changed.");
	}
	
	protected void setPassword(String password) {
		System.out.println("Admin password cannot be changed.");
	}
	
	/**
	 * Gets the table clicked.
	 * @return the table clicked
	 */
	public String getTableClicked() {
		return tableClicked;
	}

	/**
	 * Gets the dashboard stage.
	 * @return the dashboard stage
	 */
	protected Stage getDashBoardStage() {
		return dashBoardStage;
	}

	/**
	 * Sets the dashboard stage.
	 *
	 * @param dashBoardStage the new dashboard stage
	 */
	protected void setDashBoardStage(Stage dashBoardStage) {
		this.dashBoardStage = dashBoardStage;
	}

	/**
	 * The required method for the 'Initializable' interface.
	 * This method is used to enable/disable features depending on whether the user is the Supervisor or a normal staff member.
	 * @param location 
	 * @param resources 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(getIsStaff()) {
			addEmployeeButton.setDisable(true);
			manageEmployeeButton.setDisable(true);
			manageMenuButton.setDisable(true);
			exportOrdersButton.setDisable(true);
			importOrdersButton.setDisable(true);
		}
	}

	/**
	 * Adds the employee.
	 * This method opens a new window.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	public void addEmployee(ActionEvent event) throws IOException {
		Stage primaryStage = new Stage();
		Parent addEmployeePopUp = FXMLLoader.load(getClass().getResource("../admin_functionality/addEmployee.fxml"));
		Scene scene = new Scene(addEmployeePopUp);
		primaryStage.setTitle("Add A New Employee");
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.initOwner(addEmployeeButton.getScene().getWindow());
		primaryStage.setScene(scene);
		primaryStage.showAndWait();
	}

	/**
	 * Save the new employee.
	 * This method updates the 'staff.xml' file with the details added by the Supervisor.
	 *
	 * @param event the saveNewEmployee button is clicked.
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the SAX exception
	 * @throws TransformerException the transformer exception
	 */
	
	@FXML
	public void saveEmployee(ActionEvent event) throws IOException, ParserConfigurationException, SAXException, TransformerException {
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
		
		Stage stage = (Stage) saveNewEmployee.getScene().getWindow();
		stage.close();
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Saving Successful!");
		alert.setHeaderText("The new staff member has been added!");

		Optional<ButtonType> okay = alert.showAndWait();
	}

	/**
	 * Cancel adding employee.
	 * This method closes the window for adding a new employee.
	 *
	 * @param event the cancelAddingEmployeeButton is clicked.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	public void cancelAddingEmployee(ActionEvent event) throws IOException {
		Stage stage = (Stage) saveNewEmployee.getScene().getWindow();
		stage.close();
	} 


	/**
	 * Managing Employees!
	 * This method opens a new window.

	 * @param event the deleteEmployeeButton is clicked.
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the SAX exception
	 */
	
	@FXML
	public void manageEmployee(ActionEvent event) throws IOException, ParserConfigurationException, SAXException {
		Stage manageEmployees = new Stage();
		ManageEmployees manage = new ManageEmployees();
		FXMLLoader manageScreen = new FXMLLoader();
		manageScreen.setLocation(getClass().getResource("../admin_functionality/manageEmployeeAccounts.fxml"));
		manageScreen.setController(manage);
		Parent imports = (Parent)manageScreen.load();
		Scene scene = new Scene(imports);
		manageEmployees.setTitle("Manage Employees");
		manageEmployees.initModality(Modality.APPLICATION_MODAL);
		manageEmployees.setScene(scene);
		manageEmployees.showAndWait();
	}
	

	/**
	 * This method initializes the process of taking an order.
	 * It parses the 'allOrders.xml' file for current orders for the table number selected, in order to decide what to do next.
	 * If an existing order is found, it sets the Boolean 'orderExists' to true.
	 * If there is more than one existing order, it presents a new window, asking the user to decide which order they'd like to view.
	 * 
	 * @param event one of the tables on the dashboard is clicked.
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SAXException the SAX exception
	 * @throws ParserConfigurationException the parser configuration exception
	 */
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

		if(tablePossibilities.size() > 1) {		//If there is more than one order for this table, the user chooses which to view.
			ChoosingAnOrder multiple = new ChoosingAnOrder();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../shared_functionality/chooseAnOrder.fxml"));
			loader.setController(multiple);
			Parent choose = (Parent)loader.load();
			Scene scene = new Scene(choose);
			primaryStage.setTitle("Please choose which order you'd like to see!");
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.initOwner(table.getScene().getWindow());
			primaryStage.setScene(scene);
			multiple.setTheTable(tableClicked);
			multiple.initializing(datePossibilities);
			primaryStage.showAndWait();
		} else {								//Otherwise, they are taken directly to the ordersheet.
			goToOrder(tableClicked, null);
		}
	}
	
	/**
	 * Go to order.
	 * This method calls the constructor of the 'TakeAnOrder' class, and it displays the OrderSheet for the table clicked.
	 *
	 * @param tableClicked the table clicked
	 * @param dateSelection the date selection
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the SAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void goToOrder(String tableClicked, String dateSelection) throws ParserConfigurationException, SAXException, IOException {
		TakingAnOrder newOrder = new TakingAnOrder(tableClicked);
		newOrder.providingData(tableClicked, dateSelection);
		Stage orderSheet = new Stage();
		FXMLLoader loaderOrder = new FXMLLoader();
		loaderOrder.setLocation(getClass().getResource("../shared_functionality/takeAnOrder.fxml"));
		loaderOrder.setController(newOrder);
		Parent takeAnOrder = (Parent)loaderOrder.load();
		Scene scene = new Scene(takeAnOrder);
//		scene.getStylesheets().add(getClass().getResource("../style/Order.css").toExternalForm());
		orderSheet.setTitle("Order Sheet!");
		orderSheet.initModality(Modality.APPLICATION_MODAL);
		orderSheet.setScene(scene);
		newOrder.reinitialize();
		orderSheet.showAndWait();
	}
	

	/**
	 * Managing The Menu.
	 * This method opens a new window, and calls the 'EditTheMenu' class.
	 * @param event the editMenuButton is clicked.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */

	@FXML
	public void manageMenu(ActionEvent event) throws IOException {
		Menu menu = new Menu();
		Stage menuManage = new Stage();
		FXMLLoader loaderMenu = new FXMLLoader();
		loaderMenu .setLocation(getClass().getResource("../admin_functionality/manageMenu.fxml"));
		loaderMenu .setController(menu);
		Parent editTheMenu = (Parent)loaderMenu.load();
		Scene scene = new Scene(editTheMenu);
		menuManage.setTitle("Manage The Menu");
		menuManage.initModality(Modality.APPLICATION_MODAL);
		menuManage.initOwner(manageMenuButton.getScene().getWindow());
		menuManage.setScene(scene);
		menuManage.showAndWait();
	}	
	
	/**
	 * Exporting Orders!
	 * This method opens a new window, and calls the 'ExportOrders' class.
	 * @param event the exportOrdersButton is clicked.
	 * @throws IOException Signals that an I/O exception has occurred.
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
	 * Importing Orders!.
	 * This method opens a new window, and calls the 'ImportOrders' class.
	 * @param event the importOrdersButton is clicked.
	 * @throws IOException Signals that an I/O exception has occurred.
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
	 * Search Orders.
	 * This method opens a new window, and calls the 'SearchOrders' class.
	 * @throws IOException Signals that an I/O exception has occurred.
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
	
	/**
	 * Logout.
	 * This method closes the dashboard & re-opens the login window.
	 * @throws FileNotFoundException the file not found exception
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	private void logout() throws FileNotFoundException, ParserConfigurationException, IOException {
		Stage currentStage = (Stage) logoutButton.getScene().getWindow();
		currentStage.close();
		
		Main newLogin = new Main();
		Stage primaryStage = new Stage();
		newLogin.start(primaryStage);
	}
}


	


