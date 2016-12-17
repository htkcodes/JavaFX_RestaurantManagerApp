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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
				}
			}
		}
		
		tableNumberOptions = FXCollections.observableArrayList(tablesWithOrders);
		tableNumberComboBox.getItems().removeAll(tableNumberComboBox.getItems());
		tableNumberComboBox.setItems(tableNumberOptions);
		tableNumberComboBox.getSelectionModel().select(tableNumberOptions.get(0));

		dateOptions = FXCollections.observableArrayList(dateStampForOrders);
		datesComboBox.getItems().removeAll(datesComboBox.getItems());
		datesComboBox.setItems(dateOptions);
		datesComboBox.getSelectionModel().select(dateOptions.get(0));
		
		System.out.println(costsForOrders + " all the costs.");
		System.out.println(commentsForOrders + " all the comments.");
		System.out.println(specialRequestsForOrders + " all the special requests.");
		
		
		searchButton.setOnAction((ActionEvent event) -> {
			String table = tableNumberComboBox.getSelectionModel().getSelectedItem();
			String date = datesComboBox.getSelectionModel().getSelectedItem();
			String cost = totalCostInput.getText();
			String comments = commentsInput.getText();
			String specialRequests = specialRequestsInput.getText();
			
			System.out.println(table + " " + date + " " + cost + " " + comments + " " +  specialRequests);
		});
	}

}
