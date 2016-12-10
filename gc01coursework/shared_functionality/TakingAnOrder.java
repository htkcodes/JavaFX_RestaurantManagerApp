package gc01coursework.shared_functionality;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import gc01coursework.admin_functionality.EditTheMenu;
import gc01coursework.users_and_login.Supervisor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import com.sun.org.apache.xml.internal.serialize.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import java.io.File;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class TakingAnOrder implements Initializable {
	private String tableClicked;
	private ObservableList<String> starterItems;

	@FXML
	private GridPane orderGridPane;
	@FXML
	private Label tableNumber;
	@FXML
	private Label theDate;
	@FXML 
	private TextField specialRequests;
	@FXML 
	private TextField comments;
	@FXML
	private Button saveOrderButton;
	
	//Inside Items Panel:
	@FXML
	private GridPane menuGridPane;
	@FXML
	private Label theItems;
	@FXML
	private ListView<String> starterList;
	@FXML
	private Button selectStarter;
	@FXML
	private Button removeStarter;
	
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
	
	@FXML
	private void getStarters() throws ParserConfigurationException, SAXException, IOException {
		ArrayList<String> allStarters = new ArrayList<String>();

		File file = new File("starters.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document doc = documentBuilder.parse(file);

		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("starter");

		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String starterName = eElement.getElementsByTagName("starterName").item(0).getTextContent();
				String starterPrice = eElement.getElementsByTagName("starterPrice").item(0).getTextContent();
				String fullStarter = starterName + " - £" + starterPrice;
				allStarters.add(fullStarter);
			}
			
			ObservableList<String> starterItems = FXCollections.observableArrayList(allStarters);
			System.out.println(starterItems);
			ListView<String> starterList = new ListView<String>(starterItems);
			
			menuGridPane.add(starterList, 1, 0);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableNumber.setText(getTableClicked());
		
		try {
			getStarters();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String now = (dateFormat.format(date)).toString();
		theDate.setText(now);
	}

	/**
	 * Generating Order XML!
	 *
	 * 
	 * 
	 */

	@FXML
	private void saveOrder() throws ParserConfigurationException, IOException, TransformerException {
		String tableNumberToSave = getTableClicked();
		String dateToSave = theDate.getText();
		String commentsToSave = comments.getText();
		String specialRequestsToSave = specialRequests.getText();
		System.out.println(tableNumberToSave + " " + dateToSave + " " + specialRequestsToSave + " " + commentsToSave);


		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document xmlDoc = docBuilder.newDocument();	//Now we've created the document, we're ready to build the XML.
		
		Element mainElement = xmlDoc.createElement("order");

		Text tableNumberText = xmlDoc.createTextNode(tableNumberToSave);
		Element tableNumber = xmlDoc.createElement("tablenumber");		 
		Text dateText = xmlDoc.createTextNode(dateToSave);
		Element date = xmlDoc.createElement("date");	
		Text itemsText = xmlDoc.createTextNode("Items");
		Element items = xmlDoc.createElement("items");
		Text totalCostText = xmlDoc.createTextNode("Total Cost");
		Element totalCost = xmlDoc.createElement("totalcost");
		Text commentsText = xmlDoc.createTextNode(commentsToSave);
		Element comments = xmlDoc.createElement("comments");
		Text specialRequestsText = xmlDoc.createTextNode(specialRequestsToSave);
		Element specialRequests = xmlDoc.createElement("specialrequest");

		//Now we just append all the children.

		tableNumber.appendChild(tableNumberText);
		date.appendChild(dateText);
		items.appendChild(itemsText);
		totalCost.appendChild(totalCostText);
		comments.appendChild(commentsText);
		specialRequests.appendChild(specialRequestsText);

		mainElement.appendChild(tableNumber);
		mainElement.appendChild(date);
		mainElement.appendChild(items);
		mainElement.appendChild(totalCost);
		mainElement.appendChild(comments);
		mainElement.appendChild(specialRequests);

		xmlDoc.appendChild(mainElement);

		//Now we need to set the output format. 
		OutputFormat outFormat = new OutputFormat(xmlDoc);
		outFormat.setIndenting(true);

		//Declare the file:
		File xmlFile = new File("./src/orders.xml");
		FileOutputStream outStream = new FileOutputStream(xmlFile, true);

		//Serialize XML data with the specified OutputStream:
		XMLSerializer serializer = new XMLSerializer(outStream, outFormat);
		serializer.serialize(xmlDoc);

		// output DOM XML to console 
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
		DOMSource source = new DOMSource(xmlDoc);
		StreamResult console = new StreamResult(System.out);
		transformer.transform(source, console);

		System.out.println("\nXML DOM Created Successfully..");
	}

}



















