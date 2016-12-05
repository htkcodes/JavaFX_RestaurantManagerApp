package gc01coursework.shared_functionality;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.*;
import gc01coursework.users_and_login.Supervisor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TakingAnOrder implements Initializable {
	private String tableClicked;
	
	@FXML
	private GridPane orderGridPane;
	@FXML
	private Label tableNumber;
	@FXML
	private Label theDate;
	@FXML
	private Button saveOrderButton;

	
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableNumber.setText(getTableClicked());
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String now = (dateFormat.format(date)).toString();
		theDate.setText(now);
	}
	
	@FXML
	private void saveOrder() {
		
	}
	
}
