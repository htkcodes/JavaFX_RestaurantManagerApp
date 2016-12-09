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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditTheMenu {
	private static String theMenuItemTypeToBeAdded;
	
	@FXML
	private Button addStarterButton;
	@FXML
	private Button addMainButton;
	@FXML
	private Button addDessertButton;
	@FXML
	private Button addDrinkButton;
	@FXML
	private TextField menuItemNameTextField;
	@FXML
	private TextField menuItemPriceTextField;
	@FXML
	private Button addMenuItemButton;
	@FXML
	private Button cancelMenuItemButton;
	
	/**
	 * Starter XML!
	 * @throws IOException 
	 * @throws TransformerException 
	 * @throws ParserConfigurationException 
	 *
	 * 
	 * 
	 */
	
	@FXML
	private void addToMenu(ActionEvent event) throws IOException, ParserConfigurationException, TransformerException {
		Stage primaryStage = new Stage();
		Parent addMenuItemPopUp = FXMLLoader.load(getClass().getResource("../admin_functionality/addToMenu.fxml"));
		Scene scene = new Scene(addMenuItemPopUp);
		primaryStage.setTitle("Add A Menu Item");
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.initOwner(addMenuItemPopUp.getScene().getWindow());
		primaryStage.setScene(scene);
		
		theMenuItemTypeToBeAdded = ((Node) event.getSource()).getId();
		primaryStage.showAndWait();
	}
	
	@FXML
	private void addMenuItem() throws ParserConfigurationException, IOException, TransformerException {
		String newItemName = menuItemNameTextField.getText();
		String newItemPrice = menuItemPriceTextField.getText();
		System.out.println(newItemName + " " + newItemPrice);
		String[] newItem = new String[2];
		newItem[0] = newItemName;
		newItem[1] = newItemPrice;
		
		String whichItemType = theMenuItemTypeToBeAdded;

		if(whichItemType.equals("addStarterButton")) {
			addMenuStarters(newItem);
		} else if(whichItemType.equals("addMainButton")) {
			addMenuMains(newItem);
		} else if (whichItemType.equals("addDessertButton")) {
			addMenuDesserts(newItem);
		} else if(whichItemType.equals("addDrinkButton")) {
			addMenuDrinks(newItem);
		}
		
		Stage stage = (Stage) addMenuItemButton.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void cancelAddingMenuItem(ActionEvent event) throws IOException {
		Stage stage = (Stage) cancelMenuItemButton.getScene().getWindow();
		stage.close();
	} 

	@FXML
	private void addMenuStarters(String[] theNewItem) throws ParserConfigurationException, IOException, TransformerException {
		String name = theNewItem[0];
		String price = theNewItem[1];
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document xmlDoc = docBuilder.newDocument();	

		Element mainElement = xmlDoc.createElement("starter");

		Text starterText = xmlDoc.createTextNode(name);
		Element starter = xmlDoc.createElement("starter");	
		Element starterPrice = xmlDoc.createElement("starterPrice");	
		Text starterPriceText = xmlDoc.createTextNode(price);

		starter.appendChild(starterText);
		starterPrice.appendChild(starterPriceText);

		mainElement.appendChild(starter);
		mainElement.appendChild(starterPrice);
		xmlDoc.appendChild(mainElement);

		OutputFormat outFormat = new OutputFormat(xmlDoc);
		outFormat.setIndenting(true);
		File xmlFile = new File("./src/menu.xml");
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
	private void addMenuMains(String[] theNewItem) throws ParserConfigurationException, IOException, TransformerException {	
		String name = theNewItem[0];
		String price = theNewItem[1];
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document xmlDoc = docBuilder.newDocument();	

		Element mainElement = xmlDoc.createElement("main");

		Text mainText = xmlDoc.createTextNode(name);
		Element main = xmlDoc.createElement("main");	
		Element mainPrice = xmlDoc.createElement("mainPrice");	
		Text mainPriceText = xmlDoc.createTextNode(price);

		main.appendChild(mainText);
		mainPrice.appendChild(mainPriceText);

		mainElement.appendChild(main);
		mainElement.appendChild(mainPrice);
		xmlDoc.appendChild(mainElement);

		OutputFormat outFormat = new OutputFormat(xmlDoc);
		outFormat.setIndenting(true);
		File xmlFile = new File("./src/menu.xml");
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
	 * Dessert XML!
	 *
	 * 
	 * 
	 */

	@FXML
	private void addMenuDesserts(String[] theNewItem) throws ParserConfigurationException, IOException, TransformerException {
		String name = theNewItem[0];
		String price = theNewItem[1];
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document xmlDoc = docBuilder.newDocument();	

		Element mainElement = xmlDoc.createElement("dessert");

		Text dessertText = xmlDoc.createTextNode(name);
		Element dessert = xmlDoc.createElement("dessert");
		Element dessertPrice = xmlDoc.createElement("dessertPrice");
		Text dessertPriceText = xmlDoc.createTextNode(price);

		dessert.appendChild(dessertText);
		dessertPrice.appendChild(dessertPriceText);

		mainElement.appendChild(dessert);
		mainElement.appendChild(dessertPrice);

		xmlDoc.appendChild(mainElement);

		OutputFormat outFormat = new OutputFormat(xmlDoc);
		outFormat.setIndenting(true);
		File xmlFile = new File("./src/menu.xml");
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
	private void addMenuDrinks(String[] theNewItem) throws ParserConfigurationException, IOException, TransformerException {
		String name = theNewItem[0];
		String price = theNewItem[1];
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document xmlDoc = docBuilder.newDocument();	

		Element mainElement = xmlDoc.createElement("drink");

		Text drinkText = xmlDoc.createTextNode(name);
		Element drink = xmlDoc.createElement("drink");
		Element drinkPrice = xmlDoc.createElement("drinkPrice");
		Text drinkPriceText = xmlDoc.createTextNode(price);

		drink.appendChild(drinkText);
		drinkPrice.appendChild(drinkPriceText);

		mainElement.appendChild(drink);
		mainElement.appendChild(drinkPrice);

		xmlDoc.appendChild(mainElement);

		OutputFormat outFormat = new OutputFormat(xmlDoc);
		outFormat.setIndenting(true);
		File xmlFile = new File("./src/menu.xml");
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
