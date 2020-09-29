package utilities;

import java.io.IOException;
import java.net.URL;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SceneManager {

	public static void changeScene(URL resource, Event event) {
		try {
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			// Get the width and height of current scene so next scene scales to current size
			Double width = ((Node) event.getSource()).getScene().getWidth();
			Double height = ((Node) event.getSource()).getScene().getHeight();
			FXMLLoader loader = new FXMLLoader(resource);
			Parent parent = loader.load();
			Scene scene = new Scene(parent, width, height);
			scene.getStylesheets().add(SceneManager.class.getResource("/resources/stylesheet/style.css").toExternalForm());
			window.setScene(scene);
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void changeSceneCustomController(URL resource, Object controller, Event event) {
		try {
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			// Get the width and height of current scene so next scene scales to current size
			Double width = ((Node) event.getSource()).getScene().getWidth();
			Double height = ((Node) event.getSource()).getScene().getHeight();
			FXMLLoader loader = new FXMLLoader(resource);
			loader.setController(controller);
			Parent parent = loader.load();
			Scene scene = new Scene(parent, width, height);
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
			infoDialog.initOwner(window);
			infoDialog.setScene(new Scene(root));
			infoDialog.initStyle(StageStyle.TRANSPARENT);
			// Calculate the center position of the parent Stage
            double centerXPosition = window.getX() + window.getWidth()/2d;
            double centerYPosition = window.getY() + window.getHeight()/2d;
            // Hide the pop-up stage before being shown and relocate pop-up
            infoDialog.setOnShowing(ev -> infoDialog.hide());
            // Relocate the pop-up Stage
            infoDialog.setOnShown(ev -> {
                infoDialog.setX(centerXPosition - infoDialog.getWidth()/2d);
                infoDialog.setY(centerYPosition - infoDialog.getHeight()/2d);
                infoDialog.show();
            });
			infoDialog.showAndWait();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
