/**
 * <h2>This is the 'ModifyMenu' class which allow for the Supervisor to modify or delete any existing menu item.</h2>
 * 
 * @author Rachel Slater
 * @since December 2016
 * 
 */

package gc01coursework.admin_functionality;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The Class 'ModifyMenu'.
 */
public class ModifyMenu implements Initializable {

	private String whichMenuCategory;
	private ArrayList<String> allExistingItems;
	private ArrayList<String> allExistingPrices;
	private String fileToUpdate;
	private String elementNameXML;

	@FXML private GridPane modifyMenuGridPane;
	@FXML private ObservableList<String> existing;
	@FXML private ObservableList<String> selected = FXCollections.observableArrayList();
	@FXML private ListView<String> existingList;
	@FXML private Button select, remove, updateItemButton, deleteItemButton, saveUpdatesButton, saveUpdatedItem;
	@FXML private ListView<String> selectedList = new ListView<>(selected);
	@FXML private TextField updateName = new TextField();
	@FXML private TextField updatePrice = new TextField();

	/**
	 * Instantiates the class..
	 * @param menuCategory which menu category (starter, main, dessert or drink).
	 */
	public ModifyMenu(String menuCategory) {
		setWhichMenuCategory(menuCategory);
	}

	/**
	 * Gets the menu category.
	 * @return the menu category
	 */
	private String getWhichMenuCategory() {
		return whichMenuCategory;
	}

	/**
	 * Sets the which menu category.
	 * @param whichMenuCategory provides which menu category
	 */
	private void setWhichMenuCategory(String whichMenuCategory) {
		this.whichMenuCategory = whichMenuCategory;
	}

	/**
	 * Sets the all existing items.
	 * @param allExistingItems all existing items.
	 */
	private void setAllExistingItems(ArrayList<String> allExistingItems) {
		this.allExistingItems = allExistingItems;
	}
	
	/**
	 * Sets the all existing prices.
	 * @param allExistingPrices the existing prices.
	 */
	private void setAllExistingPrices(ArrayList<String> allExistingPrices) {
		this.allExistingPrices = allExistingPrices;
	}

	/**
	 * Gets the file to update.
	 * @return the file to update
	 */
	private String getFileToUpdate() {
		return fileToUpdate;
	}

	/**
	 * Sets the file to update.
	 * @param fileToUpdate the new file to update
	 */
	private void setFileToUpdate(String fileToUpdate) {
		this.fileToUpdate = fileToUpdate;
	}

	/**
	 * Gets the element name for XML.
	 * @return the element name.
	 */
	private String getElementNameXML() {
		return elementNameXML;
	}

	/**
	 * Sets the element name for XML.
	 * @param elementNameXML the new element.
	 */
	private void setElementNameXML(String elementNameXML) {
		this.elementNameXML = elementNameXML;
	}

	/**
	 * Instantiates a new modify menu.
	 */
	public ModifyMenu() {
		super();
	}

	/** 
	 * This method run when the class is instantiated. It determines which menu category method to trigger depending on the button clicked. 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(getWhichMenuCategory() != null) {
			String category = getWhichMenuCategory();

			switch(category) {
			case "modifyStarters":
				try {
					existingStarters();
					break;
				} catch (ParserConfigurationException | SAXException | IOException e) {
					e.printStackTrace();
				}
			case "modifyMains":
				try {
					existingMains();
					break;
				} catch (ParserConfigurationException | SAXException | IOException e) {
					e.printStackTrace();
				}
			case "modifyDesserts":
				try {
					existingDesserts();
					break;
				} catch (ParserConfigurationException | SAXException | IOException e) {
					e.printStackTrace();
				}
			case "modifyDrinks":
				try {
					existingDrinks();
					break;
				} catch (ParserConfigurationException | SAXException | IOException e) {
					e.printStackTrace();
				}
			default:
			}
			modifyMenuGridPane.add(selectedList, 2, 1);
		}
		
		if(selected.size()==0) {
			updateItemButton.setDisable(true);
			deleteItemButton.setDisable(true);
		}
	}

	/**
	 * Existing starters.
	 * Retrieves all existing starters (parent nodes, child nodes, and node values). 
	 * 
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the SAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void existingStarters() throws ParserConfigurationException, SAXException, IOException {
		allExistingItems = new ArrayList<String>();
		allExistingPrices = new ArrayList<String>();
		File file = new File("src/gc01coursework/xml_data/starters.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document doc = documentBuilder.parse(file);

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("starter");

		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String starterName = eElement.getElementsByTagName("starterName").item(0).getTextContent();
				String starterPrice = eElement.getElementsByTagName("starterPrice").item(0).getTextContent();
				allExistingItems.add(starterName);
				allExistingPrices.add(starterPrice);
			}
		}
		setAllExistingItems(allExistingItems);
		setAllExistingPrices(allExistingPrices);
		setFileToUpdate("src/gc01coursework/xml_data/starters.xml");
		setElementNameXML("starter");
		existing = FXCollections.observableArrayList(allExistingItems);
		existingList = new ListView<>(existing);
		modifyMenuGridPane.add(existingList, 0, 1);
	}

	/**
	 * Existing mains.
	 * Retrieves all existing mains (parent nodes, child nodes, and node values). 

	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the SAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void existingMains() throws ParserConfigurationException, SAXException, IOException {
		allExistingItems = new ArrayList<String>();
		allExistingPrices = new ArrayList<String>();
		File fileMains = new File("src/gc01coursework/xml_data/mains.xml");
		DocumentBuilderFactory documentBuilderFactoryMains = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilderMains = documentBuilderFactoryMains.newDocumentBuilder();
		Document docMains = documentBuilderMains.parse(fileMains);

		docMains.getDocumentElement().normalize();
		NodeList nListMains = docMains.getElementsByTagName("main");

		for (int i = 0; i < nListMains.getLength(); i++) {
			Node nNode = nListMains.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String mainName = eElement.getElementsByTagName("mainName").item(0).getTextContent();
				String mainPrice = eElement.getElementsByTagName("mainPrice").item(0).getTextContent();
				allExistingItems.add(mainName);
				allExistingPrices.add(mainPrice);
			}
		}	

		setAllExistingItems(allExistingItems);
		setAllExistingPrices(allExistingPrices);
		setFileToUpdate("src/gc01coursework/xml_data/mains.xml");
		setElementNameXML("main");
		existing = FXCollections.observableArrayList(allExistingItems);
		existingList = new ListView<>(existing);
		modifyMenuGridPane.add(existingList, 0, 1);
	}

	/**
	 * Existing desserts.
	 * Retrieves all existing desserts (parent nodes, child nodes, and node values). 

	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the SAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void existingDesserts() throws ParserConfigurationException, SAXException, IOException {
		allExistingItems = new ArrayList<String>();
		allExistingPrices = new ArrayList<String>();
		File fileDesserts = new File("src/gc01coursework/xml_data/desserts.xml");
		DocumentBuilderFactory documentBuilderFactoryDesserts = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilderDesserts = documentBuilderFactoryDesserts.newDocumentBuilder();
		Document docDesserts = documentBuilderDesserts.parse(fileDesserts);

		docDesserts.getDocumentElement().normalize();
		NodeList nListDesserts = docDesserts.getElementsByTagName("dessert");

		for (int i = 0; i < nListDesserts.getLength(); i++) {
			Node nNode = nListDesserts.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String dessertName = eElement.getElementsByTagName("dessertName").item(0).getTextContent();
				String dessertPrice = eElement.getElementsByTagName("dessertPrice").item(0).getTextContent();
				allExistingItems.add(dessertName);
				allExistingPrices.add(dessertPrice);
			}
		}

		setAllExistingItems(allExistingItems);
		setAllExistingPrices(allExistingPrices);
		setFileToUpdate("src/gc01coursework/xml_data/desserts.xml");
		setElementNameXML("dessert");
		existing = FXCollections.observableArrayList(allExistingItems);
		existingList = new ListView<>(existing);
		modifyMenuGridPane.add(existingList, 0, 1);
	}

	/**
	 * Existing drinks.
	 * Retrieves all existing drinks (parent nodes, child nodes, and node values). 

	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the SAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void existingDrinks() throws ParserConfigurationException, SAXException, IOException {
		allExistingItems = new ArrayList<String>();
		allExistingPrices = new ArrayList<String>();
		File fileDrinks = new File("src/gc01coursework/xml_data/drinks.xml");
		DocumentBuilderFactory documentBuilderFactoryDrinks = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilderDrinks = documentBuilderFactoryDrinks.newDocumentBuilder();
		Document docDrinks = documentBuilderDrinks.parse(fileDrinks);

		docDrinks.getDocumentElement().normalize();
		NodeList nListDrinks = docDrinks.getElementsByTagName("drink");

		for (int i = 0; i < nListDrinks.getLength(); i++) {
			Node nNode = nListDrinks.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String drinkName = eElement.getElementsByTagName("drinkName").item(0).getTextContent();
				String drinkPrice = eElement.getElementsByTagName("drinkPrice").item(0).getTextContent();
				allExistingItems.add(drinkName);
				allExistingPrices.add(drinkPrice);
			}
		}
		setAllExistingItems(allExistingItems);
		setAllExistingPrices(allExistingPrices);
		setFileToUpdate("src/gc01coursework/xml_data/drinks.xml");
		setElementNameXML("drink");
		existing = FXCollections.observableArrayList(allExistingItems);
		existingList = new ListView<>(existing);
		modifyMenuGridPane.add(existingList, 0, 1);
	}

	/**
	 * Select item.
	 * This method retrieves the item selected by the Supervisor. 
	 * It then matches it with the corresponding price. 
	 * It also handles when a user may try to select more than one item, and display a warning. 
	 * @param event the 'select' button is clicked. 
	 */
	@FXML 
	public void selectItem(ActionEvent event) {
		String potential = existingList.getSelectionModel().getSelectedItem();
		if (potential != null) {
			existingList.getSelectionModel().clearSelection();
			if(selected.size() == 1) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Warning!");
				alert.setHeaderText("Please select one item only!");
				Optional<ButtonType> continueDelete = alert.showAndWait();
			} else {
				selected.add(potential);
				existing.remove(potential);	

				int getIndexForPrice = 0;
				for(int j=0; j<allExistingItems.size(); j++) {
					if(allExistingItems.get(j).equals(potential)) {
						getIndexForPrice = j;
					}
				}
				String priceForEdit = allExistingPrices.get(getIndexForPrice);

				updateName.setPromptText(potential);
				updatePrice.setPromptText(priceForEdit);
				
				if(selected.size()==1) {
					updateItemButton.setDisable(false);
					deleteItemButton.setDisable(false);
				}
			}
		}
	}

	/**
	 * Removes the item.
	 * This removes a highlighted item from the selected list.
	 * @param event the 'remove' button is clicked.
	 */
	@FXML
	private void removeItem(ActionEvent event) {
		String undo = selectedList.getSelectionModel().getSelectedItem();
		if (undo != null) {
			selectedList.getSelectionModel().clearSelection();
			selected.remove(undo);

			if(!existing.contains(undo)) {
				existing.add(undo);
			}
		}
	}

	/**
	 * Delete item.
	 * The item is removed from the appropriate XML file. 
	 * @param event the 'deleteItemButton' is clicked. 
	 * 
	 * @throws TransformerException the transformer exception
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the SAX exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	private void deleteItem(ActionEvent event) throws TransformerException, ParserConfigurationException, SAXException, IOException {
		
		//Deciding whether or not to display confirmation. (Yes for 'delete' and no for 'update').
		if(!((javafx.scene.Node) event.getSource()).getId().equals("updateItemButton")) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete Warning!");
			alert.setHeaderText("You are about to delete this item!");
			alert.setContentText("Would you like to proceed?");
			Optional<ButtonType> continueDelete = alert.showAndWait();
			if (continueDelete.get() == ButtonType.OK){
		} else if (((javafx.scene.Node) event.getSource()).getId().equals("updateItemButton")){
			System.out.println("Continue");
		}
			String file = getFileToUpdate();
			String element = getElementNameXML();
			String selectedItem = selected.get(0);

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document doc = documentBuilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName(element);

			for (int i = 0; i < nList.getLength(); i++) {
				Node each = nList.item(i);

				if (each.getFirstChild().getFirstChild().getNodeValue().equals(selectedItem)) {
					while (each.hasChildNodes())
						each.removeChild(each.getFirstChild());
					each.getParentNode().removeChild(each);
				}
			}
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer t = tf.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(file));
			t.transform(source, result);
		} else {
			System.out.println("Item deletion cancelled.");
		}
		Stage stage = (Stage) deleteItemButton.getScene().getWindow();
		stage.close();
	}


	/**
	 * Update item.
	 * The methods in 'Menu.java' are being utilized here.
	 * The item is updated in the appropriate XML file. 
	 * @param event the 'updateItemButton' is clicked.
	 *  
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the SAX exception
	 * @throws TransformerException the transformer exception
	 */
	@FXML 
	private void updateItem(ActionEvent event) throws IOException, ParserConfigurationException, SAXException, TransformerException {
		deleteItem(event);
		String newName = updateName.getText();
		String newPrice = updatePrice.getText();
		String[] newItem = {newName, newPrice};

		Menu update = new Menu();

		if(getWhichMenuCategory() != null) {
			String category = getWhichMenuCategory();

			switch(category) {
			case "modifyStarters":
				try {
					update.addMenuStarters(newItem);
					break;
				} catch (ParserConfigurationException | SAXException | IOException e) {
					e.printStackTrace();
				}
			case "modifyMains":
				try {
					update.addMenuMains(newItem);
					break;
				} catch (ParserConfigurationException | SAXException | IOException e) {
					e.printStackTrace();
				}
			case "modifyDesserts":
				try {
					update.addMenuDesserts(newItem);
					break;
				} catch (ParserConfigurationException | SAXException | IOException e) {
					e.printStackTrace();
				}
			case "modifyDrinks":
				try {
					update.addMenuDrinks(newItem);
					break;
				} catch (ParserConfigurationException | SAXException | IOException e) {
					e.printStackTrace();
				}
			default:
				System.out.println("No button clicked.");
			}
		}
	}
}
	
