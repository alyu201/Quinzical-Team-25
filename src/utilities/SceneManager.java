package utilities;

import java.io.IOException;
import java.net.URL;

import application.Main;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * SceneManager controls the game flow by changing the current scene of the stage or adding a new stage for pop-ups..
 */
public class SceneManager {

	/**
	 * Changes the scene of the current stage to the scene given. The current stage is obtained from the 
	 * source node that triggered this event.
	 * 
	 * @param resource URL of the new view to be changed to
	 * @param event Event that triggered this function
	 */
	public static void changeScene(URL resource, Event event) {
		try {
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			// Get the width and height of current scene so next scene scales to current
			// size
			Double width = ((Node) event.getSource()).getScene().getWidth();
			Double height = ((Node) event.getSource()).getScene().getHeight();
			FXMLLoader loader = new FXMLLoader(resource);
			Parent parent = loader.load();
			Scene scene = new Scene(parent, width, height);
			scene.getStylesheets()
					.add(SceneManager.class.getResource("/resources/stylesheet/style.css").toExternalForm());
			window.setScene(scene);
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Changes scene of the application stage to the scene given. This function does not require an event to trigger this.
	 * 
	 * @param resource URL of the new view to be changed to
	 */
	public static void changeScene(URL resource) {
		try {
			Stage window = Main.getPrimaryStage();
			// Get the width and height of current scene so next scene scales to current
			// size
			Double width = window.getScene().getWidth();
			Double height = window.getScene().getHeight();
			FXMLLoader loader = new FXMLLoader(resource);
			Parent parent = loader.load();
			Scene scene = new Scene(parent, width, height);
			scene.getStylesheets()
					.add(SceneManager.class.getResource("/resources/stylesheet/style.css").toExternalForm());
			window.setScene(scene);
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a stage as pop-up dialog windows with the scene set to the one given.
	 * 
	 * @param resource URL of the view the pop-up will have
	 * @param event Event that triggered this function
	 */
	public static void addStage(URL resource, Event event) {
		Stage window = Main.getPrimaryStage();
		window.getScene().getRoot().setEffect(new GaussianBlur());
		try {
			Parent root = FXMLLoader.load(resource);
			root.getStylesheets()
					.add(SceneManager.class.getResource("/resources/stylesheet/style.css").toExternalForm());
			Stage dialog = new Stage();
			dialog.setScene(new Scene(root));
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(window);
			dialog.initStyle(StageStyle.TRANSPARENT);
			dialog.getScene().setFill(Color.TRANSPARENT);
			// Calculate the center position of the parent Stage
			double centerXPosition = window.getX() + window.getWidth() / 2d;
			double centerYPosition = window.getY() + window.getHeight() / 2d;
			// Hide the pop-up stage before being shown and relocate pop-up
			dialog.setOnShowing(e -> dialog.hide());
			// Relocate the pop-up Stage
			dialog.setOnShown(e -> {
				dialog.setX(centerXPosition - dialog.getWidth() / 2d);
				dialog.setY(centerYPosition - dialog.getHeight() / 2d);
				dialog.show();
			});
			dialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
