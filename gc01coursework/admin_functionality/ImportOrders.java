package gc01coursework.admin_functionality;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
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

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class ImportOrders implements Initializable{
	private String filePath;
	
	@FXML
	private Button chooseFileButton;
	@FXML
	private Label validityConfirmation;
	@FXML
	private Button addToExistingOrdersButton;
	
	
	private String getFilePath() {
		return filePath;
	}

	private void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	private void chooseFile() throws ParserConfigurationException, SAXException, TransformerException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select your XML file:");
		Window stage = null;
		File file = fileChooser.showOpenDialog(stage);
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.xml", "*.*"));
		String filePath = file.getAbsolutePath();
		
		FileChooser fileChooser1 = new FileChooser();
		fileChooser1.setInitialFileName(file.getName());
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
            return false;
        }
		System.out.println("Valid!!");
		validityConfirmation.setText("Valid! Continue to Step 3.");
        return true;
    }
	
	@FXML
	private void addToExistingOrders() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		String theImportedFile = getFilePath();
		System.out.println("add to existing orders");
		
		//Parsing the file to which we're going to add the imported orders.
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse("allOrders.xml");
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
		
//		OutputFormat outFormat = new OutputFormat(doc);
//		outFormat.setIndenting(true);
//		FileOutputStream outStream = new FileOutputStream(doc, true);
//		XMLSerializer serializer = new XMLSerializer(outStream, outFormat);
//		serializer.serialize(doc);
//		Transformer transformer = TransformerFactory.newInstance().newTransformer();
//		transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
//		DOMSource source = new DOMSource(doc);
//		StreamResult console = new StreamResult(System.out);
//		transformer.transform(source, console);
//		System.out.println("\nXML DOM Created Successfully..");
//		
//		
		DOMSource source = new DOMSource(doc);
		TransformerFactory tFactory = TransformerFactory.newInstance(); 
		Transformer transformer = tFactory.newTransformer();
		StreamResult result = new StreamResult("allOrders.xml");
		transformer.transform(source, result); 
	}
		

}
