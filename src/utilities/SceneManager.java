package utilities;

import java.io.IOException;
import java.net.URL;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

	public static void changeScene(URL resource, Event event) {
		try {
			FXMLLoader loader = new FXMLLoader(resource);
			Parent parent = loader.load();
			Scene scene = new Scene(parent, 800, 800);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(scene);
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
