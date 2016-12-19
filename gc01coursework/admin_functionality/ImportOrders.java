/**
 * <h2>This is the 'Imports' class which allows a user to import an XML file, verify it against custom XSD, and if valid add it to existing orders. </h2>
 * 
 * @author Rachel Slater
 * @since December 2016
 * 
 * <p>This feature has a major 'verification' component as imported data cannot be added to 'allOrders.xml' carelessly.
 * This custom XSD was created to act as the validation tool. If an imported file returns 'true' from this check, it can undergo
 * the final step of being added to the existing orders. </p>
 * 
 */

package gc01coursework.admin_functionality;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * The Class ImportOrders.
 * This class allows .xml imports file to be validated and added to existing orders data.
 */
public class ImportOrders {
	
	private String filePath;
	@FXML private Button chooseFileButton, cancelButton, addToExistingOrdersButton;
	@FXML private Label validityConfirmation;
	
	/**
	 * Gets the file path.
	 * @return the file path string.
	 */
	private String getFilePath() {
		return filePath;
	}

	/**
	 * Sets the file path.
	 * @param filePath string
	 */
	private void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * Choose file.
	 * Using FileChooser, the user can browse their home directories, and select an XML file to import.
	 * 
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the SAX exception
	 * @throws TransformerException the transformer exception
	 */
	@FXML
	private void chooseFile() throws ParserConfigurationException, SAXException, TransformerException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select your XML file:");
		Window stage = null;
		File file = fileChooser.showOpenDialog(stage);
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.xml", "*.*"));
		
		FileChooser fileChooser1 = new FileChooser();
		fileChooser1.setInitialDirectory(new File(System.getProperty("user.dir")));                 
		File file1 = fileChooser1.showSaveDialog(stage);
		if (file1 != null) {
		    try {
		        Files.copy(file.toPath(), file1.toPath());
		        if(checkValidityOfChosenFile(file1.getPath())) {
		        	setFilePath(file1.getPath());
		        }
		    } catch (IOException ex) {
		   }
		}
	}
	
	/**
	 * Check validity of chosen file.
	 * The selected file is verified to be valid against the custom orders XSD created for this project. ('ordersFormat.xsd').
	 * If it is deemed valid, the user is notified, and can proceed to step 3. Otherwise they are asked to select a different file.
	 * 
	 * @param fileImported the file imported
	 * @return the boolean indicating valid Orders XML.
	 */
	private Boolean checkValidityOfChosenFile(String fileImported) {
		
		try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File("src/gc01coursework/admin_functionality/ordersFormat.xsd"));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(fileImported)));
        } catch (IOException | SAXException e) {
        	System.out.println("Not valid Orders XML!! Compare to XSD and try again.");
            System.out.println("Exception: "+ e.getMessage());
    		validityConfirmation.setText("Not Valid! Please return to Step 1.");
    		addToExistingOrdersButton.setDisable(true);
            return false;
        }
		addToExistingOrdersButton.setDisable(false);
		validityConfirmation.setText("Valid! Continue to Step 3.");
        return true;
    }
	
	/**
	 * Adds the to existing orders.
	 * This is the final importing step - the user can add the imported file to the existing orders. 
	 * The situation of having multiple orders per table is handled. (See ChoosingAnOrder.java).
	 * 
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the SAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws TransformerException the transformer exception
	 */
	@FXML
	private void addToExistingOrders() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		String theImportedFile = getFilePath();
		System.out.println("add to existing orders");
		
		//Parsing the file to which we're going to add the imported orders.
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse("src/gc01coursework/xml_data/allOrders.xml");
		doc.getDocumentElement().normalize();
				
		//Parsing the imported file:
		DocumentBuilderFactory importingFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder importingBuilder = importingFactory.newDocumentBuilder();
		Document theImport = importingBuilder.parse(theImportedFile);
		
		NodeList orderList = theImport.getElementsByTagName("order");

		for (int j = 0; j < orderList.getLength(); j++) {
			Node order = orderList.item(j);
			
			Element root = doc.getDocumentElement();
		    Node eachImportedOrder = doc.importNode(order, true);
			root.appendChild(eachImportedOrder);
			
		}
		
		DOMSource source = new DOMSource(doc);
		TransformerFactory tFactory = TransformerFactory.newInstance(); 
		Transformer transformer = tFactory.newTransformer();
		StreamResult result = new StreamResult("src/gc01coursework/xml_data/allOrders.xml");
		transformer.transform(source, result); 
		
		Stage stage = (Stage) addToExistingOrdersButton.getScene().getWindow();
		stage.close();
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Successfully Imported!");
		alert.setHeaderText("Your imported orders have been added to the existing orders!");
		alert.setContentText("success");
		Optional<ButtonType> okay = alert.showAndWait();
	}
		
	
	/**
	 * Cancel.
	 * @param event the 'cancelButton' is clicked. 
	 */
	@FXML
	public void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
}
