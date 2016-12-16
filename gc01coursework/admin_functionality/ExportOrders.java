package gc01coursework.admin_functionality;

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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

public class ExportOrders implements Initializable {
	private ArrayList<String> tablesWithOrders;
	private ArrayList<String> dateStampForOrders;
	private ArrayList<String> selectableOrders;
	private ArrayList<String> selectedExports;
	
	@FXML
	private GridPane exportOrdersGridPane;
	@FXML
	private Button selectExport;
	@FXML 
	private Button removeExport;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Checking if which tables have orders!

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

		//Getting table numbers:
		tablesWithOrders = new ArrayList<String>();

		for (int i = 0; i < nList.getLength(); i++) {
			Node order = nList.item(i);
			String table = order.getFirstChild().getFirstChild().getNodeValue();
			tablesWithOrders.add(table);
		}

		//Getting date stamps of the existing orders:
		dateStampForOrders = new ArrayList<String>();
		NodeList list = doc.getElementsByTagName("date");

		for(int j=0; j<tablesWithOrders.size(); j++) {
			if (list != null && list.getLength() > 0) {
				NodeList subList = list.item(j).getChildNodes();

				if (subList != null && subList.getLength() > 0) {
					String dateStamp = subList.item(0).getNodeValue();
					dateStampForOrders.add(dateStamp);
				}
			}
		}
		populateExistingOrders(tablesWithOrders, dateStampForOrders);
	}

	private void populateExistingOrders(ArrayList<String> listOfTablesWithOrders, ArrayList<String> listOfDateStampsForOrders) {
		//Populating orders for selection column:
		selectableOrders = new ArrayList<String>();
		String eachOrder;
		
		for(int k=0; k<listOfTablesWithOrders.size(); k++) {
			eachOrder = "Order @ Table " + listOfTablesWithOrders.get(k) + " (created on " + listOfDateStampsForOrders.get(k) + ")";
			selectableOrders.add(eachOrder);
		}
		
		final ObservableList<String> orders = FXCollections.observableArrayList(selectableOrders);
		ListView<String> ordersForSelection = new ListView<String>(orders);
		
		exportOrdersGridPane.add(ordersForSelection, 0, 1);
		
		
		//Handling selection of orders for export:
		
//		selectedExports = new ArrayList<String>();
		ObservableList<String> selectedExports = FXCollections.observableArrayList();
		
		selectExport.setOnAction((ActionEvent event) -> {
			System.out.println("SELECT ACTION");
			String potential = ordersForSelection.getSelectionModel().getSelectedItem();
			if (potential != null) {
				ordersForSelection.getSelectionModel().clearSelection();
				selectedExports.add(potential);
			}
		});
		
		removeExport.setOnAction((ActionEvent event) -> {
			System.out.println("REMOVE ACTION");
			String undo = ordersForSelection.getSelectionModel().getSelectedItem();
			if (undo != null) {
				ordersForSelection.getSelectionModel().clearSelection();
				selectedExports.remove(undo);
			}
		});
				
		ListView<String> selections = new ListView<>(selectedExports);
		exportOrdersGridPane.add(selections, 2, 1);

	}


}
