package application;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.GameMode.GameType;
import model.MainModel;
import model.QuinzicalTuple;

public class Main extends Application {

	private static Stage primary;
	private static Object controller;
	private static MainModel model = MainModel.getMainModel();
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenuView.fxml"));
			Scene scene = new Scene(root, 1000, 800);
			// load in the css stylesheet for the button highlights for the current scene
			scene.getStylesheets().add(getClass().getResource("/resources/stylesheet/style.css").toExternalForm());
			this.primary = primaryStage;
			primaryStage.setScene(scene);
			primaryStage.setOnCloseRequest(e -> {
				// save and quit
				this.model.toJSONFile();
				primaryStage.close();
				System.exit(0);
			});
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static Stage getPrimaryStage() {
		return primary;
	}
}
