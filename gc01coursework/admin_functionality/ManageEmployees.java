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

public class ManageEmployees implements Initializable {
	
	private ObservableList<String> employeeNames = FXCollections.observableArrayList(); 
	private ObservableList<String> employeeLogins = FXCollections.observableArrayList(); 

	@FXML
	public ComboBox<String> employeeListComboBox;
	@FXML
	private Button deleteSelectedEmployeeButton;
	@FXML
	private GridPane activityLogGridPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Populating ComboBox:
		File file = new File("staff.xml");
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
					String junk = formatted[0];
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
		
		//Populating Activity Log ListView:
		ListView<String> employees = new ListView<String>(employeeNames);
		activityLogGridPane.add(employees, 0, 1);
		
		//Populating Activity Log ListView:
		ListView<String> logins = new ListView<String>(employeeLogins);
		activityLogGridPane.add(logins, 2, 1);

	}
	
	@FXML
	private void deleteSelected() throws TransformerException, ParserConfigurationException, SAXException, IOException {
		String employeeName = employeeListComboBox.getSelectionModel().getSelectedItem();
		System.out.println(employeeName + "ahhh");
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Warning!");
		alert.setHeaderText("You are about to delete this employee!");
		alert.setContentText("Would you like to proceed?");

		Optional<ButtonType> continueDelete = alert.showAndWait();
		if (continueDelete.get() == ButtonType.OK){
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document doc = documentBuilder.parse("staff.xml");

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
		StreamResult result = new StreamResult(new File("staff.xml"));
		t.transform(source, result);
		} else {
			System.out.println("Employee deletion cancelled.");
		}
		Stage stage = (Stage) deleteSelectedEmployeeButton.getScene().getWindow();
		stage.close();
	}
}
