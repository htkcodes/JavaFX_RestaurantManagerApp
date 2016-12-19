/**
 * <h2>This Login class handles attempts to login on the landing page.</h2>
 * 
 * @author Rachel Slater
 * @since December 2016
 * 
 * <p> It distinguished between a failed login, a Supervisor login, or a normal Staff Member login. </p> 
 */

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

import gc01coursework.users_and_login.User;
import javafx.scene.control.TextField;

/**
 * The Class 'Login'.
 * This handles logging into the application.
 * The username and password are validated, and if correct, the user is directed to the appropriate dashboard depending on their user access rights. 
 */
public class Login {
	
	/**
	 * Boolean isStaffMember - false indicates they are a Supervisor (all functionality), true indicates they are a normal Staff Member (limited functionality).
	 * Boolean accessGranted - false means login details are incorrect, true means login details are valid.
	 */
	private static Boolean isStaffMember;			
	private static Boolean accessGranted;			
	@FXML private TextField username, password;
	@FXML private Button loginButton;
	@FXML private Label loginStatus;

	/**
	 * Verify Login Details.
	 * This method is triggered when the 'login' button is clicked.
	 * It checks the login details entered by the user.
	 * 
	 * @param event the 'loginButton' click.
	 * @return Boolean 'accessGranted' - true means the user is verified.
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the SAX exception
	 * @throws TransformerException the transformer exception
	 */
	
	public Boolean verifyLoginDetails(ActionEvent event) throws IOException, ParserConfigurationException, SAXException, TransformerException {
		User user = new User();

		if(username.getText().equals(user.getUsername()) && password.getText().equals(user.getPassword())) {
			accessGranted = true;
			isStaffMember = false;
			user.setIsStaff(isStaffMember);
			
			Stage primaryStage = new Stage();
			Parent dashboard = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
			Scene scene = new Scene(dashboard);
			scene.getStylesheets().add(getClass().getResource("../style/Dashboard.css").toExternalForm());
			primaryStage.setTitle("Dashboard!");
			primaryStage.setScene(scene);
//	        primaryStage.setFullScreen(true);
			primaryStage.show();
			
			Stage stage = (Stage) loginButton.getScene().getWindow();
			stage.close();
		} else if(checkIfStaffMember(username.getText(), password.getText())) {
			accessGranted = true;
			isStaffMember = true;
			user.setIsStaff(isStaffMember);

			Stage primaryStage = new Stage();
			Parent dashboard = FXMLLoader.load(getClass().getResource("./dashboard.fxml"));
			Scene scene = new Scene(dashboard);
			scene.getStylesheets().add(getClass().getResource("../style/Dashboard.css").toExternalForm());
			primaryStage.setTitle("Dashboard!");
			primaryStage.setScene(scene);
//	        primaryStage.setFullScreen(true);
			primaryStage.show();

			Stage stage = (Stage) loginButton.getScene().getWindow();
			stage.close();
		} else {
			accessGranted = false;
			loginStatus.setText("Login failed! Please try again.");
		}
		return accessGranted;
	}


	/**
	 * Check if the user is a standard staff member.
	 * If the login details entered do not match the Supervisor, the 'staff.xml' is parsed to check if they match any found here. 
	 * If a match is found, this is when the new login date is added to the staff member's XML record - so the Supervisor has an up-to-date activity log for staff. 
	 * @param username the username entered
	 * @param password the password entered
	 * @return Boolean checkIfStaffMember is true if a match is found
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the SAX exception
	 * @throws TransformerException the transformer exception
	 */
	public Boolean checkIfStaffMember(String username, String password) throws IOException, ParserConfigurationException, SAXException, TransformerException {
		isStaffMember = false;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String newLoginDate = dateFormat.format(date);
		
		File file = new File("src/gc01coursework/xml_data/staff.xml");
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
				
				//Checking if the login details are a match & if so, updating the XML file with the new datestamp for this login action. 
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
					StreamResult result = new StreamResult("src/gc01coursework/xml_data/staff.xml");
					transformer.transform(source, result);
				}
			}
		}
		return isStaffMember;
	}
}
