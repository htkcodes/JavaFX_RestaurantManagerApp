package gc01coursework.shared_functionality;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

public class OrderDataModel {
	
	 private final SimpleStringProperty tableNumber;
	 private final SimpleStringProperty date;
	 private final SimpleStringProperty totalCost;
	 private final SimpleStringProperty comments;
	 private final SimpleStringProperty specialRequests;
	 private final SimpleStringProperty starters;
	 private final SimpleStringProperty mains;
	 private final SimpleStringProperty desserts;
	 private final SimpleStringProperty drinks;

	public OrderDataModel(String tableNumber, String date, String totalCost, String comments, String specialRequests, String starters, String mains, String desserts, String drinks) {
		super();
		this.tableNumber = new SimpleStringProperty(tableNumber);
		this.date = new SimpleStringProperty(date);
		this.totalCost = new SimpleStringProperty(totalCost);
		this.comments = new SimpleStringProperty(comments);
		this.specialRequests = new SimpleStringProperty(specialRequests);
		this.starters = new SimpleStringProperty(starters);
		this.mains = new SimpleStringProperty(mains);
		this.desserts = new SimpleStringProperty(desserts);
		this.drinks = new SimpleStringProperty(drinks);
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

	public String getStarters() {
		return starters.get();
	}

	public String getMains() {
		return mains.get();
	}

	public String getDesserts() {
		return desserts.get();
	}

	public String getDrinks() {
		return drinks.get();
	} 
		
}