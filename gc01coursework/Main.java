/**
 * <h2>This is the 'launcher class', and contains the main method.</h2>
 * 
 * @author Rachel Slater
 * @since December 2016
 * 
 * <p> It has two primary purposes: a) To display the landing page (requiring user login), b) To create the 6 x required XML files for data storage. </p>
 */

package gc01coursework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Class 'Main'.
 * This launches the application & creates the required XML files for data storage.
 */
public class Main extends Application {
	
	/** 
	 * Inside a try/catch block, the landing page screen is displayed, and the 6 x standard XMl files are generated. 
	 * @param primaryStage the FXML stage window.
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	
	@Override
	public void start(Stage primaryStage) throws ParserConfigurationException, FileNotFoundException, IOException {
		try {
			Parent landingPageLogin = FXMLLoader.load(getClass().getResource("./landingPage.fxml"));
			Scene scene = new Scene(landingPageLogin);
			scene.getStylesheets().add(getClass().getResource("style/Main.css").toExternalForm());
			primaryStage.setTitle("Rachel's Restaurant Manager!");
			primaryStage.setScene(scene);

			generateXMLFiles("starters.xml", "starters");
			generateXMLFiles("mains.xml", "mains");
			generateXMLFiles("desserts.xml", "desserts");
			generateXMLFiles("drinks.xml", "drinks");
			generateXMLFiles("allOrders.xml", "orders");
			generateXMLFiles("staff.xml", "allStaff");

			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The standard Java main method.
	 *
	 * @param args the required 'main method' arguments.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Generating XML files for the Project.
	 * They are required to store the restaurant data - 1. Starters, 2. Mains, 3. Desserts, 4. Drinks.
	 *
	 * @param fileName the new XML file name.
	 * @param parentElement the root element of the newly created XML file.
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws TransformerFactoryConfigurationError the transformer factory configuration error
	 * @throws TransformerException the transformer exception
	 * @throws ParserConfigurationException the parser configuration exception
	 * 
	 * @return It is expected that 6 x XML files have been created in the project workspace. 
	 */
	private void generateXMLFiles(String fileName, String parentElement) throws IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException {

		File xmlFile = new File(fileName);

		if(!xmlFile.createNewFile()) {
		} else {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document xmlDoc = docBuilder.newDocument();	
			Element element = xmlDoc.createElement(parentElement);
			xmlDoc.appendChild(element);

			OutputFormat outFormat = new OutputFormat(xmlDoc);
			outFormat.setIndenting(true);
			FileOutputStream outStream = new FileOutputStream(xmlFile, true);
			XMLSerializer serializer = new XMLSerializer(outStream, outFormat);
			serializer.serialize(xmlDoc);
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			DOMSource source = new DOMSource(xmlDoc);
			StreamResult console = new StreamResult(System.out);
			transformer.transform(source, console);
			System.out.println("\nXML DOM Created Successfully..");
		}
	}
}
