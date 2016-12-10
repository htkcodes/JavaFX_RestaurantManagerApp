package gc01coursework.shared_functionality;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.ComboBoxListCell;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


public class ViewTheMenu {
	private ObservableList<String> starterItems;

	@FXML
	private ListView<String> starterList;

	public ViewTheMenu() throws ParserConfigurationException, SAXException, IOException {
		initialize(null, null);
	}
	
	public void initialize(URL location, ResourceBundle resources) throws ParserConfigurationException, SAXException, IOException {
		fetchStarters(); 
	}

	@FXML
	public void fetchStarters() throws ParserConfigurationException, SAXException, IOException {
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
				System.out.println(fullStarter);
			}
			ObservableList<String> starterItems = FXCollections.observableArrayList(allStarters);
			System.out.println(starterItems);
			ListView<String> starterList = new ListView<String>(starterItems);
		}
	}
}



