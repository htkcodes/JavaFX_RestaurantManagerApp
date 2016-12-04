package gc01coursework.shared_functionality;

import java.io.IOException;

import gc01coursework.users_and_login.Supervisor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class TakingAnOrder {
	
	@FXML
	private Label tableNumber;
	@FXML 
	private Label test;
	@FXML
	private Button button;
	
	public void initializeOrder(String theTable) {
		
		Supervisor accessingData = new Supervisor();
		System.out.println(accessingData.getTableClicked());
		System.out.println(theTable + "YEHAHAHAHH");
//		System.out.println(tableNumber.getId());
//		System.out.println(table);
//		tableNumber.setText(theTable.toString());
//		System.out.println(tableNumber.getText();
	}
	
	public void buttonTest(ActionEvent event) throws IOException {
		System.out.println(test.getText());
		test.setText("SUP");
	}
}
