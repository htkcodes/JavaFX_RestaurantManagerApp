package gc01coursework.admin_functionality;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

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
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import gc01coursework.shared_functionality.SearchOrders;
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
	private static String theMenuItemTypeToBeModified;

	@FXML
	private Button addStarterButton;
	@FXML
	private Button addMainButton;
	@FXML
	private Button addDessertButton;
	@FXML
	private Button addDrinkButton;
	@FXML 
	private Button modifyStarters;
	@FXML 
	private Button modifyMains;
	@FXML 
	private Button modifyDessert;
	@FXML 
	private Button modifyDrinks;
	@FXML
	private TextField menuItemNameTextField;
	@FXML
	private TextField menuItemPriceTextField;
	@FXML
	private Button addMenuItemButton;
	@FXML
	private Button cancelMenuItemButton;
	@FXML
	private Button editOrDeleteMenuItems;

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
	private void addMenuItem() throws ParserConfigurationException, IOException, TransformerException, SAXException {
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

	/**
	 * Starter XML!
	 *
	 * 
	 * 
	 */
	
	@FXML
	protected void addMenuStarters(String[] theNewItem) throws ParserConfigurationException, IOException, TransformerException, SAXException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		System.out.println(theNewItem + "!!!");
		Document xmlDoc = docBuilder.parse("starters.xml");	

		Element root = xmlDoc.getDocumentElement();

		Element starter = xmlDoc.createElement("starter");

		Text starterText = xmlDoc.createTextNode(theNewItem[0]);
		Element starterName = xmlDoc.createElement("starterName");	
		Element starterPrice = xmlDoc.createElement("starterPrice");	
		Text starterPriceText = xmlDoc.createTextNode(theNewItem[1]);

		starterName.appendChild(starterText);
		starterPrice.appendChild(starterPriceText);

		starter.appendChild(starterName);
		starter.appendChild(starterPrice);

		root.appendChild(starter);

		DOMSource source = new DOMSource(xmlDoc);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		StreamResult result = new StreamResult("starters.xml");
		transformer.transform(source, result);

	}

	/**
	 * Mains XML!
	 * @throws SAXException 
	 *
	 * 
	 * 
	 */

	@FXML
	protected void addMenuMains(String[] theNewItem) throws ParserConfigurationException, IOException, TransformerException, SAXException {	

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document xmlDoc = docBuilder.parse("mains.xml");	

		Element root = xmlDoc.getDocumentElement();

		Element main = xmlDoc.createElement("main");

		Text mainText = xmlDoc.createTextNode(theNewItem[0]);
		Element mainName = xmlDoc.createElement("mainName");	
		Element mainPrice = xmlDoc.createElement("mainPrice");	
		Text mainPriceText = xmlDoc.createTextNode(theNewItem[1]);

		mainName.appendChild(mainText);
		mainPrice.appendChild(mainPriceText);

		main.appendChild(mainName);
		main.appendChild(mainPrice);

		root.appendChild(main);

		DOMSource source = new DOMSource(xmlDoc);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		StreamResult result = new StreamResult("mains.xml");
		transformer.transform(source, result);
	}

	/**
	 * Dessert XML!
	 * @throws SAXException 
	 *
	 * 
	 * 
	 */

	@FXML
	protected void addMenuDesserts(String[] theNewItem) throws ParserConfigurationException, IOException, TransformerException, SAXException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document xmlDoc = docBuilder.parse("desserts.xml");	

		Element root = xmlDoc.getDocumentElement();

		Element dessert = xmlDoc.createElement("dessert");

		Text dessertText = xmlDoc.createTextNode(theNewItem[0]);
		Element dessertName = xmlDoc.createElement("dessertName");	
		Element dessertPrice = xmlDoc.createElement("dessertPrice");	
		Text dessertPriceText = xmlDoc.createTextNode(theNewItem[1]);

		dessertName.appendChild(dessertText);
		dessertPrice.appendChild(dessertPriceText);

		dessert.appendChild(dessertName);
		dessert.appendChild(dessertPrice);

		root.appendChild(dessert);

		DOMSource source = new DOMSource(xmlDoc);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		StreamResult result = new StreamResult("desserts.xml");
		transformer.transform(source, result);
	}

	/**
	 * Drinks XML!
	 * @throws SAXException 
	 *
	 * 
	 * 
	 */

	@FXML
	protected void addMenuDrinks(String[] theNewItem) throws ParserConfigurationException, IOException, TransformerException, SAXException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document xmlDoc = docBuilder.parse("drinks.xml");	

		Element root = xmlDoc.getDocumentElement();

		Element drink = xmlDoc.createElement("drink");

		Text drinkText = xmlDoc.createTextNode(theNewItem[0]);
		Element drinkName = xmlDoc.createElement("drinkName");	
		Element drinkPrice = xmlDoc.createElement("drinkPrice");	
		Text drinkPriceText = xmlDoc.createTextNode(theNewItem[1]);

		drinkName.appendChild(drinkText);
		drinkPrice.appendChild(drinkPriceText);

		drink.appendChild(drinkName);
		drink.appendChild(drinkPrice);

		root.appendChild(drink);

		DOMSource source = new DOMSource(xmlDoc);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		StreamResult result = new StreamResult("drinks.xml");
		transformer.transform(source, result);
	}
	
	/**
	 * Modify Menu XML!
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 *  
	 *
	 * 
	 * 
	 */
	
	@FXML
	private void modifyMenuItems(ActionEvent event) throws IOException, ParserConfigurationException, SAXException {
		theMenuItemTypeToBeModified = ((Node) event.getSource()).getId();
		
		Stage modifyMenu = new Stage();
		ModifyMenu modify = new ModifyMenu(theMenuItemTypeToBeModified);
		
		FXMLLoader modifyScreen = new FXMLLoader();
		modifyScreen.setLocation(getClass().getResource("../admin_functionality/modifyMenu.fxml"));
		modifyScreen.setController(modify);
		Parent imports = (Parent)modifyScreen.load();
		Scene scene = new Scene(imports);
		modifyMenu.setTitle("Select Orders For Exporting!");
		modifyMenu.initModality(Modality.APPLICATION_MODAL);
		modifyMenu.setScene(scene);
		
		modifyMenu.showAndWait();
	}
}




















