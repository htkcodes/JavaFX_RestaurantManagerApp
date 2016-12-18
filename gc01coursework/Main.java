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


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws ParserConfigurationException, FileNotFoundException, IOException {
		try {
			Parent landingPageLogin = FXMLLoader.load(getClass().getResource("./LandingPageLogin.fxml"));
			Scene scene = new Scene(landingPageLogin);
			scene.getStylesheets().add(getClass().getResource("style/Main.css").toExternalForm());
			primaryStage.setTitle("Rachel's Restaurant Manager!");
			primaryStage.setScene(scene);

			generateStarterXML();
			generateMainXML();
			generateDessertXML();
			generateDrinkXML();
			generateOrdersXML();
			generateStaffXML();

			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Generating Starter XML file for Menu!
	 *
	 * 
	 * 
	 */
	private void generateStarterXML() throws IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException {

		File xmlFile = new File("starters.xml");

		if(!xmlFile.createNewFile()) {
			System.out.println("Menu has already been generated.");
		} else {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document xmlDoc = docBuilder.newDocument();	
			Element starterElement = xmlDoc.createElement("starters");
			xmlDoc.appendChild(starterElement);

			finishXMLCreation(xmlDoc, xmlFile);
		}
	}

	/**
	 * Generating Main XML file for Menu!
	 *
	 * 
	 * 
	 */
	private void generateMainXML() throws IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException {

		File xmlFile = new File("mains.xml");

		if(!xmlFile.createNewFile()) {
			System.out.println("Menu has already been generated.");
		} else {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document xmlDoc = docBuilder.newDocument();	
			Element mainElement = xmlDoc.createElement("mains");
			xmlDoc.appendChild(mainElement);

			finishXMLCreation(xmlDoc, xmlFile);
		}
	}

	/**
	 * Generating Dessert XML file for Menu!
	 *
	 * 
	 * 
	 */
	private void generateDessertXML() throws IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException {

		File xmlFile = new File("desserts.xml");

		if(!xmlFile.createNewFile()) {
			System.out.println("Menu has already been generated.");
		} else {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document xmlDoc = docBuilder.newDocument();	
			Element dessertElement = xmlDoc.createElement("desserts");
			xmlDoc.appendChild(dessertElement);

			finishXMLCreation(xmlDoc, xmlFile);
		}
	}

	/**
	 * Generating Drink XML file for Menu!
	 *
	 * 
	 * 
	 */
	private void generateDrinkXML() throws IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException {

		File xmlFile = new File("drinks.xml");

		if(!xmlFile.createNewFile()) {
			System.out.println("Menu has already been generated.");
		} else {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document xmlDoc = docBuilder.newDocument();	
			Element drinkElement = xmlDoc.createElement("drinks");
			xmlDoc.appendChild(drinkElement);

			finishXMLCreation(xmlDoc, xmlFile);
		}
	}
	
	/**
	 * Generating Order XML file for Menu!
	 *
	 * 
	 * 
	 */
	private void generateOrdersXML() throws IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException {

		File xmlFile = new File("allOrders.xml");

		if(!xmlFile.createNewFile()) {
			System.out.println("Orders XML is ready to add orders to.");
		} else {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document xmlDoc = docBuilder.newDocument();	
			Element orderElement = xmlDoc.createElement("orders");
			xmlDoc.appendChild(orderElement);

			finishXMLCreation(xmlDoc, xmlFile);
		}
	}
	
	/**
	 * Generating Staff XML file for Login!
	 *
	 * 
	 * 
	 */
	private void generateStaffXML() throws IOException, TransformerFactoryConfigurationError, TransformerException, ParserConfigurationException {

		File xmlFile = new File("staff.xml");

		if(!xmlFile.createNewFile()) {
			System.out.println("Staff XML is ready to add orders to.");
		} else {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document xmlDoc = docBuilder.newDocument();	
			Element orderElement = xmlDoc.createElement("allStaff");
			xmlDoc.appendChild(orderElement);

			finishXMLCreation(xmlDoc, xmlFile);
		}
	}
	
	private void finishXMLCreation(Document xmlDoc, File xmlFile) throws IOException, TransformerFactoryConfigurationError, TransformerException {
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
