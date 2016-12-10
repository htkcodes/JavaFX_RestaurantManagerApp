package gc01coursework.shared_functionality;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

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

	@FXML
	private GridPane orderGridPane;
	@FXML
	private Label tableNumber;
	@FXML
	private Label theDate;
	@FXML 
	private TextField specialRequests;
	@FXML 
	private TextField comments;
	@FXML
	private Button saveOrderButton;
	
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
	private Button selectMain;
	@FXML
	private Button removeMain;
	
	private ListView<String> dessertList;
	@FXML
	private ListView<String> selectedDessertsList;
	@FXML
	private Button selectDessert;
	@FXML
	private Button removeDessert;
	
	private ListView<String> drinkList;
	@FXML
	private ListView<String> selectedDrinksList;
	@FXML
	private Button selectDrink;
	@FXML
	private Button removeDrink;
	
	public TakingAnOrder(String tableNum) {
	}

	public void providingData(String theTable) {
		setTableClicked(theTable);
	}

	private String getTableClicked() {
		return tableClicked;
	}

	private void setTableClicked(String tableClicked) {
		this.tableClicked = tableClicked;
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
			System.out.println(starterItems);
			ListView<String> starterList = new ListView<String>(starterItems);
			
			menuGridPane.add(starterList, 1, 1);
			
			ObservableList<String> selectedStarters = FXCollections.observableArrayList();
		    ListView<String> selectedStartersList = new ListView<>(selectedStarters);
		    menuGridPane.add(selectedStartersList, 3, 1);
		    
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
		    
		    clickForQuantitiesButton.setOnAction((ActionEvent event) -> {
		    	ArrayList<String> quantities = new ArrayList<String>();
		    	
		    	Set<String> unique = new HashSet<String>(selectedStarters);
		    	for (String key : unique) {
		    		String item = key;
		    		String[] parts = item.split(" - ");
		    		String name = parts[0]; 
		    		String price = parts[1]; 
		    		int quantity = Collections.frequency(selectedStarters, key);
		    		System.out.println(name + " " + price + " " + quantity);
		    		
		    		String nameAndQuantity = quantity + " x " + name;
		    		quantities.add(nameAndQuantity);
		    	}
		    	
		    	ObservableList<String> starterQuantities = FXCollections.observableArrayList(quantities);
		    	
		    	ListView<String> starterQuantitiesList = new ListView<>(starterQuantities);
		    	menuGridPane.add(starterQuantitiesList, 4, 1);
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
			
			menuGridPane.add(mainList, 1, 2);
			
			ObservableList<String> selectedMains = FXCollections.observableArrayList();
		    ListView<String> selectedMainsList = new ListView<>(selectedMains);
		    menuGridPane.add(selectedMainsList, 3, 2);
		    
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
		}
	}
	
	/**
	 * Pulling Mains from XML and populating Order Form!
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
			
			menuGridPane.add(dessertList, 1, 3);
			
			ObservableList<String> selectedDesserts = FXCollections.observableArrayList();
		    ListView<String> selectedDessertsList = new ListView<>(selectedDesserts);
		    menuGridPane.add(selectedDessertsList, 3, 3);
		    
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
			
			menuGridPane.add(drinkList, 1, 4);
			
			ObservableList<String> selectedDrinks = FXCollections.observableArrayList();
		    ListView<String> selectedDrinksList = new ListView<>(selectedDrinks);
		    menuGridPane.add(selectedDrinksList, 3, 4);
		    
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
		}
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

	/**
	 * Generating Order XML!
	 *
	 * 
	 * 
	 */

	@FXML
	private void saveOrder() throws ParserConfigurationException, IOException, TransformerException {
		String tableNumberToSave = getTableClicked();
		String dateToSave = theDate.getText();
		String commentsToSave = comments.getText();
		String specialRequestsToSave = specialRequests.getText();
		System.out.println(tableNumberToSave + " " + dateToSave + " " + specialRequestsToSave + " " + commentsToSave);


		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document xmlDoc = docBuilder.newDocument();	//Now we've created the document, we're ready to build the XML.
		
		Element mainElement = xmlDoc.createElement("order");

		Text tableNumberText = xmlDoc.createTextNode(tableNumberToSave);
		Element tableNumber = xmlDoc.createElement("tablenumber");		 
		Text dateText = xmlDoc.createTextNode(dateToSave);
		Element date = xmlDoc.createElement("date");	
		Text itemsText = xmlDoc.createTextNode("Items");
		Element items = xmlDoc.createElement("items");
		Text totalCostText = xmlDoc.createTextNode("Total Cost");
		Element totalCost = xmlDoc.createElement("totalcost");
		Text commentsText = xmlDoc.createTextNode(commentsToSave);
		Element comments = xmlDoc.createElement("comments");
		Text specialRequestsText = xmlDoc.createTextNode(specialRequestsToSave);
		Element specialRequests = xmlDoc.createElement("specialrequest");

		//Now we just append all the children.

		tableNumber.appendChild(tableNumberText);
		date.appendChild(dateText);
		items.appendChild(itemsText);
		totalCost.appendChild(totalCostText);
		comments.appendChild(commentsText);
		specialRequests.appendChild(specialRequestsText);

		mainElement.appendChild(tableNumber);
		mainElement.appendChild(date);
		mainElement.appendChild(items);
		mainElement.appendChild(totalCost);
		mainElement.appendChild(comments);
		mainElement.appendChild(specialRequests);

		xmlDoc.appendChild(mainElement);

		//Now we need to set the output format. 
		OutputFormat outFormat = new OutputFormat(xmlDoc);
		outFormat.setIndenting(true);

		//Declare the file:
		File xmlFile = new File("./src/orders.xml");
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



















