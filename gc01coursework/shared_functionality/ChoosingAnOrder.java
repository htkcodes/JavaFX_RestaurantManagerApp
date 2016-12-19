/**
 * <h2>This is the 'ChoosingAnOrder' class, which is triggered to be instantiated inside User.java (dashboard) 
 * if there is more than one order for the table number clicked.</h2>
 * 
 * @author Rachel Slater
 * @since December 2016
 * 
 * <p> 
 */

package gc01coursework.shared_functionality;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import gc01coursework.users_and_login.User;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The Class 'ChoosingAnOrder'.
 * It handles the case of there being multiple orders recorded for one table.
 * It allows the user to choose which order they'd like to view / update.
 */

public class ChoosingAnOrder {
	
	private String theTable;
	@FXML private Button selectThisTableOrder, removeThisTableOrder, goButton, okButton;
	@FXML private GridPane whichOrder;
	@FXML private ObservableList<String> selectedOrder;

	/**
	 * Gets the table number.
	 * @return the table number.
	 */
	private String getTheTable() {
		return theTable;
	}

	/**
	 * Sets the table number.
	 * @param theTable table number.
	 */
	public void setTheTable(String theTable) {
		this.theTable = theTable;
	}

	/**
	 * This 'initializing' method is called when the class is instantiated.
	 * The date differentiates between the orders created for the same table. 
	 * @param dates ArrayList of strings.
	 */
	public void initializing(ArrayList<String> dates) {

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
	
	/**
	 * Retrieve selected order.
	 * This method ensures the order from the list which the user has selected is the correct one they want to view/update.
	 *
	 * @param event the goButton is clicked.
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParserConfigurationException the parser configuration exception
	 * @throws SAXException the SAX exception
	 */
	@FXML
	private void retrieveSelectedOrder(ActionEvent event) throws IOException, ParserConfigurationException, SAXException {
		Stage currentStage = (Stage) goButton.getScene().getWindow();
		currentStage.close();
		
		if(selectedOrder.size() > 1) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning!");
			alert.setHeaderText("You can view one order at a time.");
			alert.setContentText("Please choose one order only!");

			Optional<ButtonType> continueDelete = alert.showAndWait();
		} else {
			User user = new User();
			String theDateSelected = selectedOrder.get(0);
			String theTableSelected = getTheTable();
			user.goToOrder(theTableSelected, theDateSelected);
		}
	}
	
	/**
	 * Okay will select one table.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	public void okayWillSelectOneTable(ActionEvent event) throws IOException {
	Stage stage = (Stage) okButton.getScene().getWindow();
	stage.close();
	}
	
}


