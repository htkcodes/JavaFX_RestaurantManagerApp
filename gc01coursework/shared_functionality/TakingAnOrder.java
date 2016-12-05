package gc01coursework.shared_functionality;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.*;
import gc01coursework.users_and_login.Supervisor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TakingAnOrder implements Initializable {
	private Label tableNumber;
	private String tableClicked;
	
	@FXML
	private GridPane orderGridPane;
	
	public TakingAnOrder(String tableClicked2) {
		// TODO Auto-generated constructor stub
	}


	public void providingData(String theTable) {
		setTableClicked(theTable);
		System.out.println("The table = " + tableClicked);
	}
	
	private String getTableClicked() {
		return tableClicked;
	}
	
	private void setTableClicked(String tableClicked) {
		this.tableClicked = tableClicked;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableNumber = new Label(getTableClicked());
		orderGridPane.add(tableNumber, 1, 0);

	}
}
