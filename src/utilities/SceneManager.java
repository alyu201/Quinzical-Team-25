package utilities;

import java.io.IOException;
import java.net.URL;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SceneManager {

	public static void changeScene(URL resource, Event event) {
		try {
			FXMLLoader loader = new FXMLLoader(resource);
			Parent parent = loader.load();
			Scene scene = new Scene(parent, 1000, 800);
			scene.getStylesheets().add(SceneManager.class.getResource("/resources/stylesheet/style.css").toExternalForm());
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(scene);
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void changeSceneCustomController(URL resource, Object controller, Event event) {
		try {
			FXMLLoader loader = new FXMLLoader(resource);
			loader.setController(controller);
			Parent parent = loader.load();
			Scene scene = new Scene(parent, 1000, 800);
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(scene);
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addStage(URL resource, Event event) {
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		try {
			Parent root = FXMLLoader.load(resource);
			root.getStylesheets().add(SceneManager.class.getResource("/resources/stylesheet/style.css").toExternalForm());
			Stage infoDialog = new Stage();
			infoDialog.setScene(new Scene(root));
			infoDialog.initOwner(window);
			infoDialog.initStyle(StageStyle.TRANSPARENT);
			infoDialog.showAndWait();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
