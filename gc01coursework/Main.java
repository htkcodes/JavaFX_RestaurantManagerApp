package gc01coursework;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws ParserConfigurationException, FileNotFoundException, IOException {
		try {
			Parent landingPageLogin = FXMLLoader.load(getClass().getResource("./LandingPageLogin.fxml"));
			Scene scene = new Scene(landingPageLogin);
			scene.getStylesheets().add(getClass().getResource("style/Main.css").toExternalForm());
			primaryStage.setTitle("Rachel's Restaurant Manager!");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
