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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

	public Boolean verifyLoginDetails(ActionEvent event) throws IOException, ParserConfigurationException, SAXException, TransformerException {
		Supervisor supervisor = new Supervisor();

		if(username.getText().equals(supervisor.getUsername()) && password.getText().equals(supervisor.getPassword())) {
			accessGranted = true;
			isStaffMember = false;
			supervisor.setIsStaff(isStaffMember);
			
			Stage primaryStage = new Stage();
			Parent supervisorDashboard = FXMLLoader.load(getClass().getResource("../dashboard/SupervisorDashboard.fxml"));
			Scene scene = new Scene(supervisorDashboard);
			scene.getStylesheets().add(getClass().getResource("../style/Dashboard.css").toExternalForm());
			primaryStage.setTitle("Supervisor Dashboard!");
			primaryStage.setScene(scene);
			
			supervisor.setDashBoardStage(primaryStage);
			primaryStage.show();

			Stage stage = (Stage) loginButton.getScene().getWindow();
			stage.close();
		} else if(checkIfStaffMember(username.getText(), password.getText(), "restaurant_staff_logins.txt")) {
			accessGranted = true;
			isStaffMember = true;
			supervisor.setIsStaff(isStaffMember);

			Stage primaryStage = new Stage();
			Parent supervisorDashboard = FXMLLoader.load(getClass().getResource("../dashboard/SupervisorDashboard.fxml"));
			Scene scene = new Scene(supervisorDashboard);
			scene.getStylesheets().add(getClass().getResource("../style/Dashboard.css").toExternalForm());
			primaryStage.setTitle("Supervisor Dashboard!");
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


	public Boolean checkIfStaffMember(String username, String password, String filename) throws IOException, ParserConfigurationException, SAXException, TransformerException {
		isStaffMember = false;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String newLoginDate = dateFormat.format(date);
		
		File file = new File("staff.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document doc = documentBuilder.parse(file);
		
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("staff");
		
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String staffName = eElement.getElementsByTagName("staffName").item(0).getTextContent();
				String staffPassword = eElement.getElementsByTagName("staffPassword").item(0).getTextContent();
				String currentLogins = eElement.getElementsByTagName("loginActivity").item(0).getTextContent();
				
				if(staffName.equals(username) && staffPassword.equals(password)) {
					isStaffMember = true;
					
					Node staff = nList.item(i);
					Element loginDates = (Element) staff.getFirstChild().getNextSibling().getNextSibling().getNextSibling();
					String currentLoginList = loginDates.getFirstChild().getNodeValue();
					String updatedLogin = currentLoginList + ", " + newLoginDate;
					loginDates.getFirstChild().setNodeValue(updatedLogin);
					
					DOMSource source = new DOMSource(doc);

					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					StreamResult result = new StreamResult("staff.xml");
					transformer.transform(source, result);
				}
			}
		}
		return isStaffMember;
	}
}
