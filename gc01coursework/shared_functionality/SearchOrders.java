/**
 * <h2>This is the 'SearchOrders' class parses the "allOrders.xml" and builds a TableView and FilteredLists.</h2>
 * 
 * @author Rachel Slater
 * @since December 2016
 * 
 * <p>This class implements a real-time search table with filters on every column (representing fields within each order). 
 * As the user types, the table is filtered.</p>
 * 
 */

package gc01coursework.shared_functionality;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * The Class SearchOrders.
 */
public class SearchOrders implements Initializable{
	
	private ArrayList<String> tablesWithOrders, dateStampForOrders, costsForOrders, commentsForOrders, specialRequestsForOrders, startersForOrders, mainsForOrders, dessertsForOrders, drinksForOrders;

	@FXML public ObservableList<OrderDataModel> data, tableNumberOptions, dateOptions;
	@FXML private GridPane searchResultsGridPane;
	@FXML private ComboBox<String> tableNumberComboBox, datesComboBox;
	@FXML private TextField totalCostInput, commentsInput, specialRequestsInput;
	@FXML private Button searchButton;
	@FXML private TableView<OrderDataModel> table;
	@FXML private TableColumn<OrderDataModel, String> tableNumberColumn;
	@FXML private TableColumn<OrderDataModel, String> dateColumn;	
	@FXML private TableColumn<OrderDataModel, String> costColumn;
	@FXML private TableColumn<OrderDataModel, String> commentsColumn;
	@FXML private TableColumn<OrderDataModel, String> specialRequestsColumn;
	@FXML private TableColumn<OrderDataModel, String> startersColumn;
	@FXML private TableColumn<OrderDataModel, String> mainsColumn;
	@FXML private TableColumn<OrderDataModel, String> dessertsColumn;
	@FXML private TableColumn<OrderDataModel, String> drinksColumn;
	@FXML private TextField tableNumberFilteredSearch, dateFilteredSearch, costFilteredSearch, commentsFilteredSearch, specialRequestsFilteredSearch, startersFilteredSearch, mainsFilteredSearch, dessertsFilteredSearch, drinksFilteredSearch;
	
	/**
	 * This single method is run when the class is instantiated. 
	 * It builds ArrayLists for each component of every individual order (so the TableView can be populated). 
	 * The table view is created, the OrderDataModel is called and utilized to build a data object.
	 * The table columns are seeded with this data. 
	 * Using FilteredLists and FilteredSearch, the search fields and filters are built for each table column.
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Document doc = null;

		try {
			doc = documentBuilder.parse("allOrders.xml");
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("order");

		// Getting table numbers:
		tablesWithOrders = new ArrayList<String>();

		for (int i = 0; i < nList.getLength(); i++) {
			Node order = nList.item(i);
			String table = order.getFirstChild().getFirstChild().getNodeValue();
			tablesWithOrders.add(table);
		}

		// Getting date stamps of the existing orders:
		dateStampForOrders = new ArrayList<String>();
		NodeList list = doc.getElementsByTagName("date");

		for (int j = 0; j < tablesWithOrders.size(); j++) {
			if (list != null && list.getLength() > 0) {
				NodeList subList = list.item(j).getChildNodes();

				if (subList != null && subList.getLength() > 0) {
					String dateStamp = subList.item(0).getNodeValue();
					dateStampForOrders.add(dateStamp);
				}
			}
		}

		// Getting total costs of the existing orders:
		costsForOrders = new ArrayList<String>();
		NodeList costList = doc.getElementsByTagName("totalcost");

		for (int k = 0; k < tablesWithOrders.size(); k++) {
			if (costList != null && costList.getLength() > 0) {
				NodeList costSubList = costList.item(k).getChildNodes();

				if (costSubList != null && costSubList.getLength() > 0) {
					String cost = costSubList.item(0).getNodeValue();
					costsForOrders.add(cost);
				} else {
					costsForOrders.add("---");
				}
			}
		}

		// Getting comments of the existing orders:
		commentsForOrders = new ArrayList<String>();
		NodeList commentList = doc.getElementsByTagName("comments");

		for (int k = 0; k < tablesWithOrders.size(); k++) {
			if (commentList != null && commentList.getLength() > 0) {
				NodeList commentSubList = commentList.item(k).getChildNodes();

				if (commentSubList != null && commentSubList.getLength() > 0) {
					String comment = commentSubList.item(0).getNodeValue();
					commentsForOrders.add(comment);
				} else {
					commentsForOrders.add("---");
				}
			}
		}

		// Getting special requests of the existing orders:
		specialRequestsForOrders = new ArrayList<String>();
		NodeList specialRequestsList = doc.getElementsByTagName("specialrequests");

		for (int k = 0; k < tablesWithOrders.size(); k++) {
			if (specialRequestsList != null && specialRequestsList.getLength() > 0) {
				NodeList specialRequestsSubList = specialRequestsList.item(k).getChildNodes();

				if (specialRequestsSubList != null && specialRequestsSubList.getLength() > 0) {
					String specialRequest = specialRequestsSubList.item(0).getNodeValue();
					specialRequestsForOrders.add(specialRequest);
				} else {
					specialRequestsForOrders.add("---");
				}
			}
		}
		
		// Getting starters of the existing orders:
		startersForOrders = new ArrayList<String>();
		NodeList startersList = doc.getElementsByTagName("starters");
		for (int k = 0; k < startersList.getLength(); k++) {
			
			Node parent = doc.getDocumentElement().getChildNodes().item(k);
			
			if (startersList != null && startersList.getLength() > 0) {
				Node eachOrdersStarters = parent.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild();
				
				NodeList startersSubList = eachOrdersStarters.getChildNodes();

				if (startersSubList != null && startersSubList.getLength() > 0) {	
					
					String startersTogether = "";
					for(int r=0; r<startersSubList.getLength(); r++) {
						String starter = startersSubList.item(r).getFirstChild().getNodeValue();
						startersTogether += " and " + starter;						
					}
					String formatted = startersTogether.substring(5);
					startersForOrders.add(formatted);
				} else {
					startersForOrders.add("---");
				}
			}
		}
		
		// Getting mains of the existing orders:
		mainsForOrders = new ArrayList<String>();
		NodeList mainsList = doc.getElementsByTagName("mains");
		for (int k = 0; k < mainsList.getLength(); k++) {
			
			Node parent = doc.getDocumentElement().getChildNodes().item(k);
			
			if (mainsList != null && mainsList.getLength() > 0) {
				Node eachOrdersMains = parent.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNextSibling();
				
				NodeList mainsSubList = eachOrdersMains.getChildNodes();

				if (mainsSubList != null && mainsSubList.getLength() > 0) {	
					
					String mainsTogether = "";
					for(int r=0; r<mainsSubList.getLength(); r++) {
						String main = mainsSubList.item(r).getFirstChild().getNodeValue();
						mainsTogether += " and " + main;						
					}
					String formatted = mainsTogether.substring(5);
					mainsForOrders.add(formatted);
				} else {
					mainsForOrders.add("---");
				}
			}
		}
		
		// Getting desserts of the existing orders:
		dessertsForOrders = new ArrayList<String>();
		NodeList dessertsList = doc.getElementsByTagName("desserts");
		for (int k = 0; k < dessertsList.getLength(); k++) {
			
			Node parent = doc.getDocumentElement().getChildNodes().item(k);
			
			if (dessertsList != null && dessertsList.getLength() > 0) {
				Node eachOrdersDesserts = parent.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNextSibling().getNextSibling();
				
				NodeList dessertsSubList = eachOrdersDesserts.getChildNodes();

				if (dessertsSubList != null && dessertsSubList.getLength() > 0) {	
					
					String dessertsTogether = "";
					for(int r=0; r<dessertsSubList.getLength(); r++) {
						String dessert = dessertsSubList.item(r).getFirstChild().getNodeValue();
						dessertsTogether += " and " + dessert;						
					}
					String formatted = dessertsTogether.substring(5);
					dessertsForOrders.add(formatted);
				} else {
					dessertsForOrders.add("---");
				}
			}
		}
		
		// Getting drinks of the existing orders:
		drinksForOrders = new ArrayList<String>();
		NodeList drinksList = doc.getElementsByTagName("drinks");
		for (int k = 0; k < drinksList.getLength(); k++) {
			
			Node parent = doc.getDocumentElement().getChildNodes().item(k);
			
			if (drinksList != null && drinksList.getLength() > 0) {
				Node eachOrdersDrinks = parent.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getNextSibling().getNextSibling().getNextSibling();
				
				NodeList drinksSubList = eachOrdersDrinks.getChildNodes();

				if (drinksSubList != null && drinksSubList.getLength() > 0) {	
					
					String drinksTogether = "";
					for(int r=0; r<drinksSubList.getLength(); r++) {
						String drink = drinksSubList.item(r).getFirstChild().getNodeValue();
						drinksTogether += " and " + drink;						
					}
					String formatted = drinksTogether.substring(5);
					drinksForOrders.add(formatted);
				} else {
					drinksForOrders.add("---");
				}
			}
		}				
			
		data = FXCollections.observableArrayList();

		for(int y=0; y<tablesWithOrders.size(); y++) {
			String tableNum = tablesWithOrders.get(y);
			String date = dateStampForOrders.get(y);
			String cost = costsForOrders.get(y);
			String comment = commentsForOrders.get(y);
			String specialRequest = specialRequestsForOrders.get(y);
			String starter = startersForOrders.get(y);
			String main = mainsForOrders.get(y);
			String dessert = dessertsForOrders.get(y);
			String drink = drinksForOrders.get(y);

			OrderDataModel eachOrder = new OrderDataModel(tableNum, date, cost, comment, specialRequest, starter, main, dessert, drink);
			data.add(eachOrder);
		}

		tableNumberColumn.setCellValueFactory(new PropertyValueFactory<OrderDataModel,String>("tableNumber"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<OrderDataModel,String>("date"));
		costColumn.setCellValueFactory(new PropertyValueFactory<OrderDataModel,String>("totalCost"));
		commentsColumn.setCellValueFactory(new PropertyValueFactory<OrderDataModel,String>("comments"));
		specialRequestsColumn.setCellValueFactory(new PropertyValueFactory<OrderDataModel,String>("specialRequests"));
		startersColumn.setCellValueFactory(new PropertyValueFactory<OrderDataModel,String>("starters"));
		mainsColumn.setCellValueFactory(new PropertyValueFactory<OrderDataModel,String>("mains"));
		dessertsColumn.setCellValueFactory(new PropertyValueFactory<OrderDataModel,String>("desserts"));
		drinksColumn.setCellValueFactory(new PropertyValueFactory<OrderDataModel,String>("drinks"));

		table.setItems(data);
		table.getColumns();
		table.setPlaceholder(new Label("There are no orders matching your criteria!"));


		// 1. Wrap the ObservableList in a FilteredList (initially display all data). http://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
		FilteredList<OrderDataModel> filteredData = new FilteredList<>(data, p -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		tableNumberFilteredSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(order -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (order.getTableNumber().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches first name.
				}  else { 
					return false; // Does not match.
				}	
			});
		});
		
		dateFilteredSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(order -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (order.getDate().toLowerCase().contains(lowerCaseFilter)) {
					return true; 
				}  else {
					return false; 
				}
			});
		});
		
		costFilteredSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(order -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (order.getTotalCost().toLowerCase().contains(lowerCaseFilter)) {
					return true; 
				}  else {
					return false; 
				}
			});
		});
		
		commentsFilteredSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(order -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (order.getComments().toLowerCase().contains(lowerCaseFilter)) {
					return true; 
				}  else {
					return false; 
				}
			});
		});
		
		specialRequestsFilteredSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(order -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (order.getSpecialRequests().toLowerCase().contains(lowerCaseFilter)) {
					return true; 
				}  else {
					return false; 
				}
			});
		});
		
		startersFilteredSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(order -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (order.getStarters().toLowerCase().contains(lowerCaseFilter)) {
					return true; 
				}  else {
					return false; 
				}
			});
		});
		
		mainsFilteredSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(order -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (order.getMains().toLowerCase().contains(lowerCaseFilter)) {
					return true; 
				}  else {
					return false; 
				}
			});
		});
		
		dessertsFilteredSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(order -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (order.getDesserts().toLowerCase().contains(lowerCaseFilter)) {
					return true; 
				}  else {
					return false; 
				}
			});
		});
		
		drinksFilteredSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(order -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (order.getDrinks().toLowerCase().contains(lowerCaseFilter)) {
					return true; 
				}  else {
					return false; 
				}
			});
		});
		
		SortedList<OrderDataModel> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(table.comparatorProperty());
		table.setItems(sortedData);
	}

}
