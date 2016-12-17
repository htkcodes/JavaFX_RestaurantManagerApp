package gc01coursework.shared_functionality;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

public class OrderDataModel {
	
	 private final SimpleStringProperty tableNumber;
	 private final SimpleStringProperty date;
	 private final SimpleStringProperty totalCost;
	 private final SimpleStringProperty comments;
	 private final SimpleStringProperty specialRequests;
	 
	public OrderDataModel(String tableNumber, String date, String totalCost, String comments, String specialRequests) {
		super();
		this.tableNumber = new SimpleStringProperty(tableNumber);
		this.date = new SimpleStringProperty(date);
		this.totalCost = new SimpleStringProperty(totalCost);
		this.comments = new SimpleStringProperty(comments);
		this.specialRequests = new SimpleStringProperty(specialRequests);
	}

	public String getTableNumber() {
		return tableNumber.get();
	}

	public String getDate() {
		return date.get();
	}

	public String getTotalCost() {
		return totalCost.get();
	}

	public String getComments() {
		return comments.get();
	}

	public String getSpecialRequests() {
		return specialRequests.get();
	} 
}