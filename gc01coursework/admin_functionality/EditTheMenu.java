package gc01coursework.admin_functionality;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class EditTheMenu {
	
	@FXML
	private Button testButton;
	@FXML
	private Button addStarterButton;
	@FXML
	private Button addMainButton;
	@FXML
	private Button addDessertButton;
	@FXML
	private Button addDrinkButton;
	
	@FXML 
	public void updateMenu() throws ParserConfigurationException, IOException, TransformerException {
		addMenuStarters();
		addMenuMains();
		addMenuDesserts();
		addMenuDrinks();
	}
	
	/**
	 * Starter XML!
	 *
	 * 
	 * 
	 */
	
	@FXML
	private void addMenuStarters() throws ParserConfigurationException, IOException, TransformerException {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document xmlDoc = docBuilder.newDocument();	//Now we've created the document, we're ready to build the XML.

		Element mainElement = xmlDoc.createElement("starter");

		Text starterText = xmlDoc.createTextNode("Starter");
		Element starter = xmlDoc.createElement("starter");	
		Element starterPrice = xmlDoc.createElement("starterPrice");	
		Text starterPriceText = xmlDoc.createTextNode(" Price = ");

		starter.appendChild(starterText);
		starterPrice.appendChild(starterPriceText);
		
		mainElement.appendChild(starter);
		mainElement.appendChild(starterPrice);
		xmlDoc.appendChild(mainElement);

		OutputFormat outFormat = new OutputFormat(xmlDoc);
		outFormat.setIndenting(true);
		File xmlFile = new File("./starters.xml");
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
	
	/**
	 * Mains XML!
	 *
	 * 
	 * 
	 */
	
	@FXML
	private void addMenuMains() throws ParserConfigurationException, IOException, TransformerException {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document xmlDoc = docBuilder.newDocument();	//Now we've created the document, we're ready to build the XML.

		Element mainElement = xmlDoc.createElement("main");

		Text mainText = xmlDoc.createTextNode("Mains");
		Element main = xmlDoc.createElement("main");	
		Element mainPrice = xmlDoc.createElement("mainPrice");	
		Text mainPriceText = xmlDoc.createTextNode(" Price = ");

		main.appendChild(mainText);
		mainPrice.appendChild(mainPriceText);

		mainElement.appendChild(main);
		mainElement.appendChild(mainPrice);
		xmlDoc.appendChild(mainElement);

		OutputFormat outFormat = new OutputFormat(xmlDoc);
		outFormat.setIndenting(true);
		File xmlFile = new File("./mains.xml");
		FileOutputStream outStream = new FileOutputStream(xmlFile, true);

		XMLSerializer serializer = new XMLSerializer(outStream, outFormat);
		serializer.serialize(xmlDoc);
		
		// output DOM XML to console 
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
        DOMSource source = new DOMSource(xmlDoc);
        StreamResult console = new StreamResult(System.out);
        transformer.transform(source, console);

        System.out.println("\nXML DOM Created Successfully..");
	}
	
	/**
	 * Dessert XML!
	 *
	 * 
	 * 
	 */
	
	@FXML
	private void addMenuDesserts() throws ParserConfigurationException, IOException, TransformerException {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document xmlDoc = docBuilder.newDocument();	//Now we've created the document, we're ready to build the XML.

		Element mainElement = xmlDoc.createElement("dessert");

		Text dessertText = xmlDoc.createTextNode("Desserts");
		Element dessert = xmlDoc.createElement("dessert");
		Element dessertPrice = xmlDoc.createElement("dessertPrice");
		Text dessertPriceText = xmlDoc.createTextNode(" Price = ");

		dessert.appendChild(dessertText);
		dessertPrice.appendChild(dessertPriceText);

		mainElement.appendChild(dessert);
		mainElement.appendChild(dessertPrice);

		xmlDoc.appendChild(mainElement);

		OutputFormat outFormat = new OutputFormat(xmlDoc);
		outFormat.setIndenting(true);
		File xmlFile = new File("./desserts.xml");
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
	
	/**
	 * Drinks XML!
	 *
	 * 
	 * 
	 */
	
	@FXML
	private void addMenuDrinks() throws ParserConfigurationException, IOException, TransformerException {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document xmlDoc = docBuilder.newDocument();	//Now we've created the document, we're ready to build the XML.

		Element mainElement = xmlDoc.createElement("drink");

		Text drinkText = xmlDoc.createTextNode("Drinks");
		Element drink = xmlDoc.createElement("drink");
		Element drinkPrice = xmlDoc.createElement("drinkPrice");
		Text drinkPriceText = xmlDoc.createTextNode(" Price = ");

		drink.appendChild(drinkText);
		drinkPrice.appendChild(drinkPriceText);

		mainElement.appendChild(drink);
		mainElement.appendChild(drinkPrice);

		xmlDoc.appendChild(mainElement);

		OutputFormat outFormat = new OutputFormat(xmlDoc);
		outFormat.setIndenting(true);
		File xmlFile = new File("./drinks.xml");
		FileOutputStream outStream = new FileOutputStream(xmlFile, true);

		//Serialize XML data with the specified OutputStream:
		XMLSerializer serializer = new XMLSerializer(outStream, outFormat);
		serializer.serialize(xmlDoc);
		
		// output DOM XML to console 
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
        DOMSource source = new DOMSource(xmlDoc);
        StreamResult console = new StreamResult(System.out);
        transformer.transform(source, console);

        System.out.println("\nXML DOM Created Successfully..");
	}
	
}
