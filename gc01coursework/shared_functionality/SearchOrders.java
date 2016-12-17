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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SearchOrders implements Initializable{
	private ArrayList<String> tablesWithOrders;
	private ArrayList<String> dateStampForOrders;
	private ArrayList<String> costsForOrders;
	private ArrayList<String> commentsForOrders;
	private ArrayList<String> specialRequestsForOrders;
	private ArrayList<String> startersForOrders;

	@FXML
	public ObservableList<OrderDataModel> data;
	@FXML
	private GridPane searchResultsGridPane;
	@FXML
	private ComboBox<String> tableNumberComboBox;
	@FXML
	private ComboBox<String> datesComboBox;
	@FXML
	private ObservableList<String> tableNumberOptions;
	@FXML
	private ObservableList<String> dateOptions;
	@FXML
	private TextField totalCostInput;
	@FXML
	private TextField commentsInput;
	@FXML
	private TextField specialRequestsInput;
	@FXML
	private Button searchButton;
	@FXML
	private TableView<OrderDataModel> table;
	@FXML
	private TableColumn<OrderDataModel, String> tableNumberColumn;
	@FXML
	private TableColumn<OrderDataModel, String> dateColumn;
	@FXML
	private TableColumn<OrderDataModel, String> costColumn;
	@FXML
	private TableColumn<OrderDataModel, String> commentsColumn;
	@FXML
	private TableColumn<OrderDataModel, String> specialRequestsColumn;
	@FXML
	private TableColumn<OrderDataModel, String> startersColumn;
	@FXML
	private TableColumn<OrderDataModel, String> mainsColumn;
	@FXML
	private TableColumn<OrderDataModel, String> dessertsColumn;
	@FXML
	private TableColumn<OrderDataModel, String> drinksColumn;
	@FXML
	private TextField tableNumberFilteredSearch;
	@FXML
	private TextField dateFilteredSearch;
	@FXML
	private TextField costFilteredSearch;
	@FXML
	private TextField commentsFilteredSearch;
	@FXML
	private TextField specialRequestsFilteredSearch;
	@FXML
	private TextField startersFilteredSearch;
	@FXML
	private TextField mainsFilteredSearch;
	@FXML
	private TextField dessertsFilteredSearch;
	@FXML
	private TextField drinksFilteredSearch;
	
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
		
//		 Getting starters of the existing orders:
		startersForOrders = new ArrayList<String>();
		NodeList startersList = doc.getElementsByTagName("starters");
		for (int k = 0; k < startersList.getLength(); k++) {
			
			Node parent = doc.getDocumentElement().getChildNodes().item(k);
			String eachOrdersDateStamp = parent.getFirstChild().getNextSibling().getFirstChild().getNodeValue();
			
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
		
		System.out.println(startersForOrders + " <3 <3");
		
			
			
			
			
			
		data = FXCollections.observableArrayList();

		for(int y=0; y<tablesWithOrders.size(); y++) {
			String tableNum = tablesWithOrders.get(y);
			String date = dateStampForOrders.get(y);
			String cost = costsForOrders.get(y);
			String comment = commentsForOrders.get(y);
			String specialRequest = specialRequestsForOrders.get(y);

			OrderDataModel eachOrder = new OrderDataModel(tableNum, date, cost, comment, specialRequest);
			data.add(eachOrder);

		}

		tableNumberColumn.setCellValueFactory(new PropertyValueFactory<OrderDataModel,String>("tableNumber"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<OrderDataModel,String>("date"));
		costColumn.setCellValueFactory(new PropertyValueFactory<OrderDataModel,String>("totalCost"));
		commentsColumn.setCellValueFactory(new PropertyValueFactory<OrderDataModel,String>("comments"));
		specialRequestsColumn.setCellValueFactory(new PropertyValueFactory<OrderDataModel,String>("specialRequests"));

		table.setItems(data);
		table.getColumns();

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
		SortedList<OrderDataModel> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(table.comparatorProperty());
		table.setItems(sortedData);
	}

}
