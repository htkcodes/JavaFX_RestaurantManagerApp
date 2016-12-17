package gc01coursework.shared_functionality;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import gc01coursework.users_and_login.Supervisor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChoosingAnOrder {
	private String theTable;
	
	@FXML
	private Button selectThisTableOrder;
	@FXML
	private Button removeThisTableOrder;
	@FXML 
	private Button goButton;
	@FXML 
	private GridPane whichOrder;
	@FXML
	private ObservableList<String> selectedOrder;
	@FXML
	private Button okButton;
	

	private String getTheTable() {
		return theTable;
	}

	public void setTheTable(String theTable) {
		this.theTable = theTable;
	}


	public void initial(ArrayList<String> dates) {

		final ObservableList<String> whichDate = FXCollections.observableArrayList(dates);
		ListView<String> dateList = new ListView<String>(whichDate);
		whichOrder.add(dateList, 0, 1);

		selectedOrder = FXCollections.observableArrayList();
		ListView<String> selection = new ListView<>(selectedOrder);
		whichOrder.add(selection, 2, 1);

		selectThisTableOrder.setOnAction((ActionEvent event) -> {
			String potential = dateList.getSelectionModel().getSelectedItem();
			if (potential != null) {
				dateList.getSelectionModel().clearSelection();
				selectedOrder.add(potential);
				whichDate.remove(potential);
			}
		});

		removeThisTableOrder.setOnAction((ActionEvent event) -> {
			String undo = selection.getSelectionModel().getSelectedItem();
			if (undo != null) {
				dateList.getSelectionModel().clearSelection();
				selectedOrder.remove(undo);

				if (!whichDate.contains(undo)) {
					whichDate.add(undo);
				}
			}
		});
		
	}
	
	@FXML
	private void retrieveSelectedOrder(ActionEvent event) throws IOException, ParserConfigurationException, SAXException {		
		if(selectedOrder.size() > 1) {
			Stage primaryStage = new Stage();
			Parent chooseATablePopUp = FXMLLoader.load(getClass().getResource("../shared_functionality/multipleOrdersWarning.fxml"));
			Scene scene = new Scene(chooseATablePopUp);
			primaryStage.setTitle("Please click on a Table");
			primaryStage.initModality(Modality.APPLICATION_MODAL);
			primaryStage.initOwner(goButton.getScene().getWindow());
			primaryStage.setScene(scene);
			primaryStage.showAndWait();
		} else {
			Supervisor user = new Supervisor();
			String theDateSelected = selectedOrder.get(0);
			String theTableSelected = getTheTable();
			user.goToOrder(theTableSelected, theDateSelected);
		}
	}
	
	@FXML
	public void okayWillSelectOneTable(ActionEvent event) throws IOException {
	Stage stage = (Stage) okButton.getScene().getWindow();
	stage.close();
	}
	
}


