/**
 * <h2>This is the 'Manage Employees' class - it provides a display of the staff activity log within the system, and allows the Supervisor to 
 * delete staff accounts.</h2>
 * 
 * @author Rachel Slater
 * @since December 2016
 * 
 * <p> When this class is instantiated, it presents a ListView of staff logins dates/times. If the delete button is clicked, 
 * the second method is called in order to update the XML data.</p>
 * 
 */

package gc01coursework.admin_functionality;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import org.xml.sax.SAXException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * The Class ManageEmployees.
 * It runs the 'initialize' when a new instance of the class is created. 
 */
public class ManageEmployees implements Initializable {
	
	private ObservableList<String> employeeNames = FXCollections.observableArrayList(); 
	private ObservableList<String> employeeLogins = FXCollections.observableArrayList(); 
	@FXML public ComboBox<String> employeeListComboBox;
	@FXML private Button deleteSelectedEmployeeButton, closeWindowButton;
	@FXML private GridPane activityLogGridPane;
	
	/** 
	 * This method populates the ListViews and the ComboBox when the window is displayed. 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 * The try/catch block will catch exceptions if there is a problem parsing the file.  
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Populating ComboBox:
		File file = new File("src/gc01coursework/xml_data/staff.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Document doc = null;
		try {
			doc = documentBuilder.parse(file);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("staff");
		
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String staffName = eElement.getElementsByTagName("staffName").item(0).getTextContent();
				employeeNames.add(staffName);
				
				String staffLogins = eElement.getElementsByTagName("loginActivity").item(0).getTextContent();
				if(!staffLogins.equals("firstcreated")) {
					String[] formatted = staffLogins.split("firstcreated, ");
					String logins = formatted[1];
					employeeLogins.add(logins);
				} else {
					employeeLogins.add("---");
				}
			}
		}
		
		employeeListComboBox.getItems().removeAll(employeeListComboBox.getItems());
		employeeListComboBox.setItems(employeeNames);
		employeeListComboBox.getSelectionModel().select(employeeNames.get(0));
		
		//Populating Activity Log ListView (employee names):
		ListView<String> employees = new ListView<String>(employeeNames);
		activityLogGridPane.add(employees, 0, 2);
		
		//Populating Activity Log ListView (employee login timestamps):
		ListView<String> logins = new ListView<String>(employeeLogins);
		activityLogGridPane.add(logins, 1, 2);

	}
	
	/**
	 * Delete Selected Employee.
	 * If an employee is selected from the ComboBox list, and the delete button is clicked, the 
	 * 'staff.xml" file is updated.
	 * 
	 * A confirmation alert allows the Supervisor the confirm the deletion.
	 * 
	 * @throws TransformerException the transformer exception
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the SAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	private void deleteSelected() throws TransformerException, ParserConfigurationException, SAXException, IOException {
		String employeeName = employeeListComboBox.getSelectionModel().getSelectedItem();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Warning!");
		alert.setHeaderText("You are about to delete this employee!");
		alert.setContentText("Would you like to proceed?");

		Optional<ButtonType> continueDelete = alert.showAndWait();
		if (continueDelete.get() == ButtonType.OK){
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document doc = documentBuilder.parse("src/gc01coursework/xml_data/staff.xml");

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("staff");
		
		for (int i = 0; i < nList.getLength(); i++) {
			Node each = nList.item(i);
			
			if (each.getFirstChild().getFirstChild().getNodeValue().equals(employeeName)) {
				while (each.hasChildNodes())
			       each.removeChild(each.getFirstChild());
			       each.getParentNode().removeChild(each);
			}
		}
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("src/gc01coursework/xml_data/staff.xml"));
		t.transform(source, result);
		} else {
			System.out.println("Employee deletion cancelled.");
		}
		Stage stage = (Stage) deleteSelectedEmployeeButton.getScene().getWindow();
		stage.close();
	}
	
	/**
	 * Close window.
	 * @param event the closeWindowButton is clicked.
	 */
	@FXML
	public void closeWindow(ActionEvent event) {
		Stage stage = (Stage) closeWindowButton.getScene().getWindow();
		stage.close();
	}
}
