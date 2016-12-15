package gc01coursework.shared_functionality;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import gc01coursework.admin_functionality.EditTheMenu;
import gc01coursework.users_and_login.Supervisor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import com.sun.org.apache.xml.internal.serialize.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.io.File;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class TakingAnOrder implements Initializable {
	private String tableClicked;
	private Boolean isOrderForTable = false;
	private int startersTotalPrice;
	private int mainsTotalPrice;
	private int dessertsTotalPrice;
	private int drinksTotalPrice;
	private ArrayList<String> finalStarters;
	private ArrayList<String> finalMains;
	private ArrayList<String> finalDesserts;
	private ArrayList<String> finalDrinks;
	List<String> currentStarters; 
	List<String> currentMains; 
	List<String> currentDesserts; 
	List<String> currentDrinks; 
	private int currentCost;

	@FXML
	private GridPane orderGridPane;
	@FXML
	private Label tableNumber;
	@FXML
	private Label theDate;
	@FXML
	private Label totalCost;
	@FXML 
	private TextField specialRequests;
	@FXML 
	private TextField comments;
	@FXML
	private Button saveOrderButton;
	@FXML
	private Button updateOrderButton;
	@FXML
	private Button deleteOrderButton;

	//Inside Items Panel:
	@FXML
	private GridPane menuGridPane;
	@FXML
	private Button clickForQuantitiesButton;

	@FXML
	private ListView<String> starterList;
	@FXML
	private ListView<String> selectedStartersList;
	@FXML
	private ObservableList<String> selectedStarters;
	@FXML
	private ListView<String> starterQuantitiesList;
	@FXML
	private Button selectStarter;
	@FXML
	private Button removeStarter;

	private ListView<String> mainList;
	@FXML
	private ListView<String> selectedMainsList;
	@FXML
	private ListView<String> mainQuantitiesList;
	@FXML
	private Button selectMain;
	@FXML
	private Button removeMain;

	private ListView<String> dessertList;
	@FXML
	private ListView<String> selectedDessertsList;
	@FXML
	private ListView<String> dessertQuantitiesList;
	@FXML
	private Button selectDessert;
	@FXML
	private Button removeDessert;

	private ListView<String> drinkList;
	@FXML
	private ListView<String> selectedDrinksList;
	@FXML
	private ListView<String> drinkQuantitiesList;
	@FXML
	private Button selectDrink;
	@FXML
	private Button removeDrink;


	@FXML
	private Button hiddenMainsQuantityButton;
	@FXML
	private Button hiddenDessertsQuantityButton;
	@FXML
	private Button hiddenDrinksQuantityButton;


	public TakingAnOrder(String tableNum) {
	}

	public void providingData(String theTable, Boolean isOrder) {
		setTableClicked(theTable);
		setIsOrderForTable(isOrder);
	}

	private Boolean getIsOrderForTable() {
		return isOrderForTable;
	}

	private void setIsOrderForTable(Boolean isOrderForTable) {
		this.isOrderForTable = isOrderForTable;
	}

	private String getTableClicked() {
		return tableClicked;
	}

	private void setTableClicked(String tableClicked) {
		this.tableClicked = tableClicked;
	}

	private int getStartersTotalPrice() {
		return startersTotalPrice;
	}

	private int getMainsTotalPrice() {
		return mainsTotalPrice;
	}

	private int getDessertsTotalPrice() {
		return dessertsTotalPrice;
	}

	private int getDrinksTotalPrice() {
		return drinksTotalPrice;
	}

	private void setStartersTotalPrice(int startersTotalPrice) {
		this.startersTotalPrice = startersTotalPrice;
	}

	private void setMainsTotalPrice(int mainsTotalPrice) {
		this.mainsTotalPrice = mainsTotalPrice;
	}

	private void setDessertsTotalPrice(int dessertsTotalPrice) {
		this.dessertsTotalPrice = dessertsTotalPrice;
	}

	private void setDrinksTotalPrice(int drinksTotalPrice) {
		this.drinksTotalPrice = drinksTotalPrice;
	}

	private void displayFinalPrice() {
		int finalPrice = getStartersTotalPrice() + getMainsTotalPrice() + getDessertsTotalPrice() + getDrinksTotalPrice() + getCurrentCost();
		totalCost.setText("£" + finalPrice);
	}

	private ArrayList<String> getFinalStarters() {
		return finalStarters;
	}

	private ArrayList<String> getFinalMains() {
		return finalMains;
	}

	private ArrayList<String> getFinalDesserts() {
		return finalDesserts;
	}

	private ArrayList<String> getFinalDrinks() {
		return finalDrinks;
	}

	private void setFinalStarters(ArrayList<String> finalStarters) {
		this.finalStarters = finalStarters;
	}

	private void setFinalMains(ArrayList<String> finalMains) {
		this.finalMains = finalMains;
	}

	private void setFinalDesserts(ArrayList<String> finalDesserts) {
		this.finalDesserts = finalDesserts;
	}

	private void setFinalDrinks(ArrayList<String> finalDrinks) {
		this.finalDrinks = finalDrinks;
	}
	
	
	private List<String> getCurrentStarters() {
		return currentStarters;
	}

	private void setCurrentStarters(List<String> currentStarters) {
		this.currentStarters = currentStarters;
	}
	
	

	private List<String> getCurrentMains() {
		return currentMains;
	}

	private List<String> getCurrentDesserts() {
		return currentDesserts;
	}

	private List<String> getCurrentDrinks() {
		return currentDrinks;
	}

	private void setCurrentMains(List<String> currentMains) {
		this.currentMains = currentMains;
	}

	private void setCurrentDesserts(List<String> currentDesserts) {
		this.currentDesserts = currentDesserts;
	}

	private void setCurrentDrinks(List<String> currentDrinks) {
		this.currentDrinks = currentDrinks;
	}
	
	
	private int getCurrentCost() {
		return currentCost;
	}

	private void setCurrentCost(int currentCost) {
		this.currentCost = currentCost;
	}

	/**
	 * Initializing Components on Order Form!
	 *
	 * 
	 * 
	 */

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tableNumber.setText(getTableClicked());

		//If there is already an order for the selected table:

		if(getIsOrderForTable()) {
			
		    saveOrderButton.setDisable(true);

			File file = new File("allOrders.xml");
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = null;
			try {
				documentBuilder = documentBuilderFactory.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			Document doc = null;
			try {
				doc = documentBuilder.parse(file);
			} catch (SAXException | IOException e) {
				e.printStackTrace();
			}

			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("order");

			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					String updatedDate = (dateFormat.format(date)).toString();
					theDate.setText(updatedDate);

					String existingCost = eElement.getElementsByTagName("totalcost").item(0).getTextContent();
					totalCost.setText(existingCost);
					int currentTotalOrder = Integer.parseInt(existingCost.substring(1));
					setCurrentCost(currentTotalOrder);

					String existingRequests = eElement.getElementsByTagName("specialrequests").item(0).getTextContent();
					specialRequests.setText(existingRequests);

					String existingComments = eElement.getElementsByTagName("comments").item(0).getTextContent();
					comments.setText(existingComments);
				}
			}
			
			Node starterNode = nList.item(0);
			if (starterNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) starterNode;
				String existingStarters = eElement.getElementsByTagName("starters").item(0).getTextContent();
				String[] allStarters = existingStarters.split("(?=\\d+)");
				setCurrentStarters(Arrays.asList(allStarters));
				

				ObservableList<String> starterQuantities = FXCollections.observableArrayList(allStarters);

				ListView<String> starterQuantitiesList = new ListView<>(starterQuantities);
				menuGridPane.add(starterQuantitiesList, 4, 0);

			}
			
			Node mainsNode = nList.item(0);
			if (mainsNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) mainsNode;
				String existingMains = eElement.getElementsByTagName("mains").item(0).getTextContent();
				String[] allMains = existingMains.split("(?=\\d+)");
				
				setCurrentMains(Arrays.asList(allMains));

				ObservableList<String> mainQuantities = FXCollections.observableArrayList(allMains);

				ListView<String> mainQuantitiesList = new ListView<>(mainQuantities);
				menuGridPane.add(mainQuantitiesList, 4, 1);
			}
			
			Node dessertNode = nList.item(0);
			if (dessertNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) dessertNode;
				String existingDesserts = eElement.getElementsByTagName("desserts").item(0).getTextContent();
				String[] allDesserts = existingDesserts.split("(?=\\d+)");
				setCurrentDesserts(Arrays.asList(allDesserts));
				
				ObservableList<String> dessertQuantities = FXCollections.observableArrayList(allDesserts);

				ListView<String> dessertQuantitiesList = new ListView<>(dessertQuantities);
				menuGridPane.add(dessertQuantitiesList, 4, 2);
			}
			
			Node drinkNode = nList.item(0);
			if (drinkNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) drinkNode;
				String existingDrinks = eElement.getElementsByTagName("drinks").item(0).getTextContent();
				String[] allDrinks = existingDrinks.split("(?=\\d+)");
				setCurrentDrinks(Arrays.asList(allDrinks));
				
				ObservableList<String> drinkQuantities = FXCollections.observableArrayList(allDrinks);

				ListView<String> drinkQuantitiesList = new ListView<>(drinkQuantities);
				menuGridPane.add(drinkQuantitiesList, 4, 3);
			}

			try {
				getStarters();
				getMains();
				getDesserts();
				getDrinks();
			} catch (ParserConfigurationException | SAXException | IOException e) {
				e.printStackTrace();
			}

		} else { 	//If there is NOT an order for the selected table.
		    updateOrderButton.setDisable(true);

			try {
				getStarters();
				getMains();
				getDesserts();
				getDrinks();
			} catch (ParserConfigurationException | SAXException | IOException e) {
				e.printStackTrace();
			}

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			String now = (dateFormat.format(date)).toString();
			theDate.setText(now);

		}
	}

	/**
	 * Pulling Starters from XML and populating Order Form!
	 *
	 * 
	 * 
	 */

	@FXML
	private void getStarters() throws ParserConfigurationException, SAXException, IOException {
		ArrayList<String> allStarters = new ArrayList<String>();

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
				String fullStarter = starterName + " - £" + starterPrice;
				allStarters.add(fullStarter);
			}

			final ObservableList<String> starterItems = FXCollections.observableArrayList(allStarters);
			ListView<String> starterList = new ListView<String>(starterItems);

			menuGridPane.add(starterList, 1, 0);

			ObservableList<String> selectedStarters = FXCollections.observableArrayList();
			ListView<String> selectedStartersList = new ListView<>(selectedStarters);
			menuGridPane.add(selectedStartersList, 3, 0);

			selectStarter.setOnAction((ActionEvent event) -> {
				String potential = starterList.getSelectionModel().getSelectedItem();
				if (potential != null) {
					starterList.getSelectionModel().clearSelection();
					selectedStarters.add(potential);
				}
			});

			removeStarter.setOnAction((ActionEvent event) -> {
				String undo = selectedStartersList.getSelectionModel().getSelectedItem();
				if (undo != null) {
					selectedStartersList.getSelectionModel().clearSelection();
					selectedStarters.remove(undo);

					if(!starterItems.contains(undo)) {
						starterItems.add(undo);
					}
				}
			});

			ArrayList<String> finalStarterNames = new ArrayList<String>();
			ArrayList<Integer> finalStarterQuantities = new ArrayList<Integer>();

			clickForQuantitiesButton.setOnAction((ActionEvent event) -> {
				ArrayList<String> quantities = new ArrayList<String>();
				int startersTotal = 0;

				Set<String> unique = new HashSet<String>(selectedStarters);
				for (String key : unique) {
					String item = key;
					String[] parts = item.split(" - ");
					String name = parts[0]; 


					String price = parts[1]; 
					int quantity = Collections.frequency(selectedStarters, key);

					finalStarterNames.add(name);
					finalStarterQuantities.add(quantity);

					String[] priceParts = price.split("£");
					String priceWithoutSymbol = priceParts[1];
					int priceAsNum = (int) Integer.parseInt(priceWithoutSymbol);
					int eachItemPriceSum = (quantity * priceAsNum);
					String nameAndQuantity = quantity + " x " + name;
					quantities.add(nameAndQuantity);

					startersTotal += eachItemPriceSum;


					ArrayList<String> finalStarters = new ArrayList<String>();

					for(int k=0; k<quantities.size(); k++) {
						String eachSelected = quantities.get(k);
						finalStarters.add(eachSelected);
					}
					
					if(getCurrentStarters() != null) {
						for(int m=0; m<getCurrentStarters().size(); m++) {
							finalStarters.add(getCurrentStarters().get(m));
						}
					}
					setFinalStarters(finalStarters);		    		
				}
				
				if(getCurrentStarters() != null) {
					for(int m=0; m<getCurrentStarters().size(); m++) {
						quantities.add(getCurrentStarters().get(m));
					}
				}
				
				ObservableList<String> starterQuantities = FXCollections.observableArrayList(quantities);

				ListView<String> starterQuantitiesList = new ListView<>(starterQuantities);
				menuGridPane.add(starterQuantitiesList, 4, 0);

				setStartersTotalPrice(startersTotal);

				hiddenMainsQuantityButton.fire();
				hiddenDessertsQuantityButton.fire();
				hiddenDrinksQuantityButton.fire();

				displayFinalPrice();
			});
		}
	}

	/**
	 * Pulling Mains from XML and populating Order Form!
	 *
	 * 
	 * 
	 */

	@FXML
	private void getMains() throws ParserConfigurationException, SAXException, IOException {
		ArrayList<String> allMains = new ArrayList<String>();

		File file = new File("mains.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document doc = documentBuilder.parse(file);

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("main");

		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String mainName = eElement.getElementsByTagName("mainName").item(0).getTextContent();
				String mainPrice = eElement.getElementsByTagName("mainPrice").item(0).getTextContent();
				String fullMain = mainName + " - £" + mainPrice;
				allMains.add(fullMain);
			}

			ObservableList<String> mainItems = FXCollections.observableArrayList(allMains);
			System.out.println(mainItems);
			ListView<String> mainList = new ListView<String>(mainItems);

			menuGridPane.add(mainList, 1, 1);

			ObservableList<String> selectedMains = FXCollections.observableArrayList();
			ListView<String> selectedMainsList = new ListView<>(selectedMains);
			menuGridPane.add(selectedMainsList, 3, 1);

			selectMain.setOnAction((ActionEvent event) -> {
				String potential = mainList.getSelectionModel().getSelectedItem();
				if (potential != null) {
					mainList.getSelectionModel().clearSelection();
					selectedMains.add(potential);
				}
			});

			removeMain.setOnAction((ActionEvent event) -> {
				String undo = selectedMainsList.getSelectionModel().getSelectedItem();
				if (undo != null) {
					selectedMainsList.getSelectionModel().clearSelection();
					selectedMains.remove(undo);

					if(!mainItems.contains(undo)) {
						mainItems.add(undo);
					}
				}
			});

			hiddenMainsQuantityButton.setOnAction((ActionEvent event) -> {
				ArrayList<String> quantities = new ArrayList<String>();
				int mainsTotal = 0;

				Set<String> unique = new HashSet<String>(selectedMains);
				for (String key : unique) {
					String item = key;
					String[] parts = item.split(" - ");
					String name = parts[0]; 
					String price = parts[1]; 
					int quantity = Collections.frequency(selectedMains, key);
					System.out.println(name + " " + price + " " + quantity);

					String nameAndQuantity = quantity + " x " + name;
					
					String[] priceParts = price.split("£");
					String priceWithoutSymbol = priceParts[1];
					int priceAsNum = (int) Integer.parseInt(priceWithoutSymbol);
					int eachItemPriceSum = (quantity * priceAsNum);
					String nameAndQuantityMains = quantity + " x " + name;

					quantities.add(nameAndQuantityMains);

					mainsTotal += eachItemPriceSum;

					ArrayList<String> finalMains = new ArrayList<String>();
					for(int k=0; k<quantities.size(); k++) {
						String eachSelected = quantities.get(k);
						finalMains.add(eachSelected);
					}
					setFinalMains(finalMains);
				}
				
				if(getCurrentMains() != null) {
					for(int m=0; m<getCurrentMains().size(); m++) {
						quantities.add(getCurrentMains().get(m));
					}
				}
				
				ObservableList<String> mainQuantities = FXCollections.observableArrayList(quantities);

				ListView<String> mainQuantitiesList = new ListView<>(mainQuantities);
				menuGridPane.add(mainQuantitiesList, 4, 1);

				setMainsTotalPrice(mainsTotal);
			});
		}
	}

	/**
	 * Pulling Desserts from XML and populating Order Form!
	 *
	 * 
	 * 
	 */

	@FXML
	private void getDesserts() throws ParserConfigurationException, SAXException, IOException {
		ArrayList<String> allDesserts = new ArrayList<String>();

		File file = new File("desserts.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document doc = documentBuilder.parse(file);

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("dessert");

		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String dessertName = eElement.getElementsByTagName("dessertName").item(0).getTextContent();
				String dessertPrice = eElement.getElementsByTagName("dessertPrice").item(0).getTextContent();
				String fullDessert = dessertName + " - £" + dessertPrice;
				allDesserts.add(fullDessert);
			}

			ObservableList<String> dessertItems = FXCollections.observableArrayList(allDesserts);
			System.out.println(dessertItems);
			ListView<String> dessertList = new ListView<String>(dessertItems);

			menuGridPane.add(dessertList, 1, 2);

			ObservableList<String> selectedDesserts = FXCollections.observableArrayList();
			ListView<String> selectedDessertsList = new ListView<>(selectedDesserts);
			menuGridPane.add(selectedDessertsList, 3, 2);

			selectDessert.setOnAction((ActionEvent event) -> {
				String potential = dessertList.getSelectionModel().getSelectedItem();
				if (potential != null) {
					dessertList.getSelectionModel().clearSelection();
					selectedDesserts.add(potential);
				}
			});

			removeDessert.setOnAction((ActionEvent event) -> {
				String undo = selectedDessertsList.getSelectionModel().getSelectedItem();
				if (undo != null) {
					selectedDessertsList.getSelectionModel().clearSelection();
					selectedDesserts.remove(undo);

					if(!dessertItems.contains(undo)) {
						dessertItems.add(undo);
					}
				}
			});

			hiddenDessertsQuantityButton.setOnAction((ActionEvent event) -> {
				ArrayList<String> quantities = new ArrayList<String>();
				int dessertsTotal = 0;

				Set<String> unique = new HashSet<String>(selectedDesserts);
				for (String key : unique) {
					String item = key;
					String[] parts = item.split(" - ");
					String name = parts[0]; 
					String price = parts[1]; 
					int quantity = Collections.frequency(selectedDesserts, key);
					System.out.println(name + " " + price + " " + quantity);

					String nameAndQuantity = quantity + " x " + name;

					String[] priceParts = price.split("£");
					String priceWithoutSymbol = priceParts[1];
					int priceAsNum = (int) Integer.parseInt(priceWithoutSymbol);
					int eachItemPriceSum = (quantity * priceAsNum);
					String nameAndQuantityDesserts = quantity + " x " + name;

					quantities.add(nameAndQuantityDesserts);

					dessertsTotal += eachItemPriceSum;

					ArrayList<String> finalDesserts = new ArrayList<String>();
					for(int k=0; k<quantities.size(); k++) {
						String eachSelected = quantities.get(k);
						finalDesserts.add(eachSelected);
					}
					setFinalDesserts(finalDesserts);
				}

				if(getCurrentDesserts() != null) {
					for(int m=0; m<getCurrentDesserts().size(); m++) {
						quantities.add(getCurrentDesserts().get(m));
					}
				}
				
				ObservableList<String> dessertQuantities = FXCollections.observableArrayList(quantities);

				ListView<String> dessertQuantitiesList = new ListView<>(dessertQuantities);
				menuGridPane.add(dessertQuantitiesList, 4, 2);

				setDessertsTotalPrice(dessertsTotal);
			});
		}
	}

	/**
	 * Pulling Drinks from XML and populating Order Form!
	 *
	 * 
	 * 
	 */

	@FXML
	private void getDrinks() throws ParserConfigurationException, SAXException, IOException {
		ArrayList<String> allDrinks = new ArrayList<String>();

		File file = new File("drinks.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document doc = documentBuilder.parse(file);

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("drink");

		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String drinkName = eElement.getElementsByTagName("drinkName").item(0).getTextContent();
				String drinkPrice = eElement.getElementsByTagName("drinkPrice").item(0).getTextContent();
				String fullDrink = drinkName + " - £" + drinkPrice;
				allDrinks.add(fullDrink);
			}

			ObservableList<String> drinkItems = FXCollections.observableArrayList(allDrinks);
			System.out.println(drinkItems);
			ListView<String> drinkList = new ListView<String>(drinkItems);

			menuGridPane.add(drinkList, 1, 3);

			ObservableList<String> selectedDrinks = FXCollections.observableArrayList();
			ListView<String> selectedDrinksList = new ListView<>(selectedDrinks);
			menuGridPane.add(selectedDrinksList, 3, 3);

			selectDrink.setOnAction((ActionEvent event) -> {
				String potential = drinkList.getSelectionModel().getSelectedItem();
				if (potential != null) {
					drinkList.getSelectionModel().clearSelection();
					selectedDrinks.add(potential);
				}
			});

			removeDrink.setOnAction((ActionEvent event) -> {
				String undo = selectedDrinksList.getSelectionModel().getSelectedItem();
				if (undo != null) {
					selectedDrinksList.getSelectionModel().clearSelection();
					selectedDrinks.remove(undo);

					if(!drinkItems.contains(undo)) {
						drinkItems.add(undo);
					}
				}
			});

			hiddenDrinksQuantityButton.setOnAction((ActionEvent event) -> {
				ArrayList<String> quantities = new ArrayList<String>();
				int drinksTotal = 0;

				Set<String> unique = new HashSet<String>(selectedDrinks);
				for (String key : unique) {
					String item = key;
					String[] parts = item.split(" - ");
					String name = parts[0]; 
					String price = parts[1]; 
					int quantity = Collections.frequency(selectedDrinks, key);
					System.out.println(name + " " + price + " " + quantity);

					String nameAndQuantity = quantity + " x " + name;

					String[] priceParts = price.split("£");
					String priceWithoutSymbol = priceParts[1];
					int priceAsNum = (int) Integer.parseInt(priceWithoutSymbol);
					int eachItemPriceSum = (quantity * priceAsNum);
					String nameAndQuantityDrinks = quantity + " x " + name;

					quantities.add(nameAndQuantityDrinks);

					drinksTotal += eachItemPriceSum;

					ArrayList<String> finalDrinks = new ArrayList<String>();

					for(int k=0; k<quantities.size(); k++) {
						String eachSelected = quantities.get(k);
						finalDrinks.add(eachSelected);
					}
					setFinalDrinks(finalDrinks);
				}
				
				if(getCurrentDrinks() != null) {
					for(int m=0; m<getCurrentDrinks().size(); m++) {
						quantities.add(getCurrentDrinks().get(m));
					}
				}
			
				ObservableList<String> drinkQuantities = FXCollections.observableArrayList(quantities);

				ListView<String> drinkQuantitiesList = new ListView<>(drinkQuantities);
				menuGridPane.add(drinkQuantitiesList, 4, 3);

				setDrinksTotalPrice(drinksTotal);
			});
		}
	}


	/**
	 * Generating Order XML!
	 * @throws SAXException 
	 *
	 * 
	 * 
	 */

	@FXML
	private void saveOrder() throws ParserConfigurationException, IOException, TransformerException, SAXException {
		clickForQuantitiesButton.fire();	//In case the user has not updated the quantities & prices before trying to save. 

		String tableNumberToSave = getTableClicked();
		String dateToSave = theDate.getText();
		String commentsToSave = comments.getText();
		String specialRequestsToSave = specialRequests.getText();
		String totalCostToSave = totalCost.getText();

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document xmlDoc = docBuilder.parse("allOrders.xml");	

		Element root = xmlDoc.getDocumentElement();
				
		Element order = xmlDoc.createElement("order");

		Text tableNumberText = xmlDoc.createTextNode(tableNumberToSave);
		Element tableNumber = xmlDoc.createElement("tablenumber");		 
		Text dateText = xmlDoc.createTextNode(dateToSave);
		Element date = xmlDoc.createElement("date");	
		Text totalCostText = xmlDoc.createTextNode(totalCostToSave);
		Element totalCost = xmlDoc.createElement("totalcost");
		Text commentsText = xmlDoc.createTextNode(commentsToSave);
		Element comments = xmlDoc.createElement("comments");
		Text specialRequestsText = xmlDoc.createTextNode(specialRequestsToSave);
		Element specialRequests = xmlDoc.createElement("specialrequests");
		tableNumber.appendChild(tableNumberText);
		date.appendChild(dateText);
		totalCost.appendChild(totalCostText);
		comments.appendChild(commentsText);
		specialRequests.appendChild(specialRequestsText);

		Element orderedItems = xmlDoc.createElement("orderedItems");

		Element starters = xmlDoc.createElement("starters");
		ArrayList<String> startersForSaving = getFinalStarters();

		if(startersForSaving != null) {
			for (String eachStarter : startersForSaving) {
				Element starter = xmlDoc.createElement("starter");
				starter.appendChild(xmlDoc.createTextNode(eachStarter));
				starters.appendChild(starter);
			}
		}

		Element mains = xmlDoc.createElement("mains");
		ArrayList<String> mainsForSaving = getFinalMains();

		if(mainsForSaving != null) {
			for (String eachMain : mainsForSaving) {
				Element main = xmlDoc.createElement("main");
				main.appendChild(xmlDoc.createTextNode(eachMain));
				mains.appendChild(main);
			}
		}

		Element desserts = xmlDoc.createElement("desserts");

		ArrayList<String> dessertsForSaving = getFinalDesserts();

		if(dessertsForSaving != null) {
			for (String eachDessert : dessertsForSaving) {
				Element dessert = xmlDoc.createElement("dessert");
				dessert.appendChild(xmlDoc.createTextNode(eachDessert));
				desserts.appendChild(dessert);
			}
		}

		Element drinks = xmlDoc.createElement("drinks");	
		ArrayList<String> drinksForSaving = getFinalDrinks();

		if(drinksForSaving != null) {
			for (String eachDrink : drinksForSaving) {
				Element drink = xmlDoc.createElement("drink");
				drink.appendChild(xmlDoc.createTextNode(eachDrink));
				drinks.appendChild(drink);
			}
		}

		orderedItems.appendChild(starters);
		orderedItems.appendChild(mains);
		orderedItems.appendChild(desserts);
		orderedItems.appendChild(drinks);

		order.appendChild(tableNumber);
		order.appendChild(date);
		order.appendChild(orderedItems);
		order.appendChild(totalCost);
		order.appendChild(comments);
		order.appendChild(specialRequests);
		order.appendChild(orderedItems);

		root.appendChild(order);

		DOMSource source = new DOMSource(xmlDoc);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		StreamResult result = new StreamResult("allOrders.xml");
		transformer.transform(source, result);
	}

	@FXML
	private void updateOrder() throws ParserConfigurationException, IOException, TransformerException, SAXException {
		saveOrder();
	}
	
	@FXML 
	private void deleteOrder() throws SAXException, IOException, ParserConfigurationException, XPathExpressionException, TransformerException {
		String tableOrderToDelete = getTableClicked();

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document doc = documentBuilder.parse("allOrders.xml");

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("order");
		
		for (int i = 0; i < nList.getLength(); i++) {
			Node order = nList.item(i);
		
			if (order.getFirstChild().getFirstChild().getNodeValue().equals(tableOrderToDelete)) {

//				NodeList again = order.getChildNodes();
//				System.out.println(again + " these should be the child nodes");

				while (order.hasChildNodes())
			       order.removeChild(order.getFirstChild());
				
				order.getParentNode().removeChild(order);
				
//				for(int y=0; y<again.getLength(); y++) {
//					Node thisone = (Node) again.item(y);
//					
//					System.out.println(thisone + "hahaah an element");
//					
//					Element q = (Element) again.item(y);
//					
//					System.out.println(q +  " hehe the elemtns");
//					
//					
//	                 thisone.getParentNode().removeChild(thisone);
//
//	                if(thisone.getNodeName().equals("specialrequest")) {
//	                	System.out.println(" in special request");
//						thisone.removeChild(thisone.getFirstChild());
//					}
//	                 
//	                if(thisone.getNodeName().equals("date")) {
//	                	System.out.println(" need a date");
//		                 thisone.getParentNode().removeChild(thisone);
//					}
//				}
			}
		}
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("allOrders.xml"));
		t.transform(source, result);
	}
}



















