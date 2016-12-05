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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import com.sun.org.apache.xml.internal.serialize.*;

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
		try {
			this.saveOrder();
		} catch(ParserConfigurationException parse) {
			
		} catch(FileNotFoundException fileNotFound) {
			
		} catch (IOException ioexception) {
			
		}
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
	private void saveOrder() throws ParserConfigurationException, IOException {
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document xmlDoc = docBuilder.newDocument();	//Now we've created the document, we're ready to build the XML.
		
		//XML Elements and Text Nodes:
		// 	<orders>
		//		<order>
		//			<tablenumber>Table Number</tablenumber>
		//			<date>Date</date>
		//			<items>Ordered Items</items>
		//			<totalcost>Total Cost</totalcost>
		//			<comments>Comments</comments>
		//			<specialrequest>Special Requests</specialrequests>
		//		<order>
		//</orders>
		
		Element rootElement = xmlDoc.createElement("orders");
		Element mainElement = xmlDoc.createElement("order");
		Text tableNumberText = xmlDoc.createTextNode("Table Number");
		Element tableNumber = xmlDoc.createElement("tablenumber");		 
		Text dateText = xmlDoc.createTextNode("Date");
		Element date = xmlDoc.createElement("date");	
		
		//Now we just append all the children.
		
		tableNumber.appendChild(tableNumberText);
		date.appendChild(dateText);
		
		mainElement.appendChild(tableNumber);
		mainElement.appendChild(date);
		
		rootElement.appendChild(mainElement);
		
		xmlDoc.appendChild(rootElement);
		
		//Now we need to set the output format. 
		OutputFormat outFormat = new OutputFormat(xmlDoc);
		outFormat.setIndenting(true);
		
		//Declare the file:
		File xmlFile = new File("./orders.xml");
		FileOutputStream outStream = new FileOutputStream(xmlFile);
		
		//Serialize XML data with the specified OutputStream:
		XMLSerializer serializer = new XMLSerializer(outStream, outFormat);
		serializer.serialize(xmlDoc);
	}
	
}


















