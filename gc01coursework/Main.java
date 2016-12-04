package gc01coursework;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
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
