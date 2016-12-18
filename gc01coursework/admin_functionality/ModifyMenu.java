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

public class ModifyMenu implements Initializable {
	private String whichMenuCategory;
	private ArrayList<String> allExistingItems;
	private ArrayList<String> allExistingPrices;
	private String fileToUpdate;
	private String elementNameXML;
	private Boolean isUpdating = false;
	
	@FXML
	private GridPane modifyMenuGridPane;
	@FXML
	private ObservableList<String> existing;
	@FXML
	private ListView<String> existingList;
	@FXML
	private Button select;
	@FXML
	private Button remove;
	@FXML
	private ObservableList<String> selected = FXCollections.observableArrayList();
	@FXML
	private ListView<String> selectedList = new ListView<>(selected);
	@FXML
	private Button updateItemButton;
	@FXML
	private Button deleteItemButton;
	@FXML 
	private TextField updateName = new TextField();
	@FXML
	private TextField updatePrice = new TextField();
	@FXML
	private Button saveUpdatesButton;
	@FXML
	private Button saveUpdatedItem;
	
	
	public ModifyMenu(String menuCategory) {
		setWhichMenuCategory(menuCategory);
	}

	private String getWhichMenuCategory() {
		return whichMenuCategory;
	}

	private void setWhichMenuCategory(String whichMenuCategory) {
		this.whichMenuCategory = whichMenuCategory;
	}
	
	private ArrayList<String> getAllExistingItems() {
		return allExistingItems;
	}

	private void setAllExistingItems(ArrayList<String> allExistingItems) {
		this.allExistingItems = allExistingItems;
	}
	
	
	private ArrayList<String> getAllExistingPrices() {
		return allExistingPrices;
	}

	private void setAllExistingPrices(ArrayList<String> allExistingPrices) {
		this.allExistingPrices = allExistingPrices;
	}

	private String getFileToUpdate() {
		return fileToUpdate;
	}

	private void setFileToUpdate(String fileToUpdate) {
		this.fileToUpdate = fileToUpdate;
	}
	
	
	private String getElementNameXML() {
		return elementNameXML;
	}

	private void setElementNameXML(String elementNameXML) {
		this.elementNameXML = elementNameXML;
	}

	public ModifyMenu() {
		super();
	}

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
			System.out.println("No button clicked.");
		}
		modifyMenuGridPane.add(selectedList, 2, 1);
		}
	}
	
	private void existingStarters() throws ParserConfigurationException, SAXException, IOException {
		allExistingItems = new ArrayList<String>();
		allExistingPrices = new ArrayList<String>();
		File file = new File("starters.xml");
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
		setFileToUpdate("starters.xml");
		setElementNameXML("starter");
		existing = FXCollections.observableArrayList(allExistingItems);
		existingList = new ListView<>(existing);
		modifyMenuGridPane.add(existingList, 0, 1);
	}
	
	private void existingMains() throws ParserConfigurationException, SAXException, IOException {
		allExistingItems = new ArrayList<String>();
		allExistingPrices = new ArrayList<String>();
		File fileMains = new File("mains.xml");
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
		setFileToUpdate("mains.xml");
		setElementNameXML("main");
		existing = FXCollections.observableArrayList(allExistingItems);
		existingList = new ListView<>(existing);
		modifyMenuGridPane.add(existingList, 0, 1);
	}
	
	private void existingDesserts() throws ParserConfigurationException, SAXException, IOException {
		allExistingItems = new ArrayList<String>();
		allExistingPrices = new ArrayList<String>();
		File fileDesserts = new File("desserts.xml");
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
		setFileToUpdate("desserts.xml");
		setElementNameXML("dessert");
		existing = FXCollections.observableArrayList(allExistingItems);
		existingList = new ListView<>(existing);
		modifyMenuGridPane.add(existingList, 0, 1);
	}
	
	private void existingDrinks() throws ParserConfigurationException, SAXException, IOException {
		allExistingItems = new ArrayList<String>();
		allExistingPrices = new ArrayList<String>();
		File fileDrinks = new File("drinks.xml");
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
		setFileToUpdate("drinks.xml");
		setElementNameXML("drink");
		existing = FXCollections.observableArrayList(allExistingItems);
		existingList = new ListView<>(existing);
		modifyMenuGridPane.add(existingList, 0, 1);
	}
	
	@FXML 
	public void selectItem(ActionEvent event) {
		String potential = existingList.getSelectionModel().getSelectedItem();
		if (potential != null) {
			existingList.getSelectionModel().clearSelection();
			if(selected.size() == 1) {
				System.out.println("Only choose one please!");
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
			}
		}
	}
	
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
	
	@FXML
	private void deleteItem(ActionEvent event) throws TransformerException, ParserConfigurationException, SAXException, IOException {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Warning!");
		alert.setHeaderText("You are about to delete this item!");
		alert.setContentText("Would you like to proceed?");

		Optional<ButtonType> continueDelete = alert.showAndWait();
		if (continueDelete.get() == ButtonType.OK){
		
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
		StreamResult result = new StreamResult(new File("starters.xml"));
		t.transform(source, result);
		} else {
			System.out.println("Item deletion cancelled.");
		}
	}

	
	@FXML 
	private void updateItem(ActionEvent event) throws IOException, ParserConfigurationException, SAXException, TransformerException {
		deleteItem(event);
		isUpdating = true;		
		String newName = updateName.getText();
		String newPrice = updatePrice.getText();
		
		String[] newItem = {newName, newPrice};
		
		EditTheMenu update = new EditTheMenu();

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
	
