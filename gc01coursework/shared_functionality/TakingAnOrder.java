package gc01coursework.shared_functionality;

import java.io.IOException;

import gc01coursework.users_and_login.Supervisor;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TakingAnOrder {
	private String tableClicked;
	
	@FXML
	private GridPane orderGridPane;
	
	@FXML
	private Button tableNumber;
	
	@FXML 
	private Label test;
	
	@FXML
	private Label hello;
	
//	public TakingAnOrder(String tableClicked) {
//		super();
//		this.tableClicked = tableClicked;
//	}

	public void setTableClicked(String tableClicked) {
		this.tableClicked = tableClicked;
	}

	@FXML
	public void initializeOrder() {
		System.out.println(orderGridPane);
		System.out.println(tableClicked + "HELP");
		tableNumber = new Button();
		tableNumber.getId();
//		tableNumber.setText("YO");
//		System.out.println(test.getText());
//		System.out.println(hello.getText());
//		tableNumber.setText("PLEASE");
//		System.out.println(tableNumber.getId());
	}
	
//	public StringProperty initializeOrder() { 
//	    return tableNumber.textProperty();
//	}
	

}
