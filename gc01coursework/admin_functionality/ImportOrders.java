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
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class ImportOrders implements Initializable{
	
	@FXML
	private Button chooseFileButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	private void chooseFile() {
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
		        checkValidityOfChosenFile(file1.getPath());
		    } catch (IOException ex) {
		   }
		}
	}
	
	private Boolean checkValidityOfChosenFile(String fileImported) {
		
		try {
			System.out.println("TRYING TO VALIDATE");
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File("src/gc01coursework/admin_functionality/ordersFormat.xsd"));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(fileImported)));
        } catch (IOException | SAXException e) {
        	System.out.println("AHHHHH");
            System.out.println("Exception: "+ e.getMessage());
            return false;
        }
		System.out.println("WOOOOOOOO");
        return true;
    }
		

}
