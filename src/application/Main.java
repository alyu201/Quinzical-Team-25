package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.MainModel;
import model.QuestionTypeEnum.QuestionType;

public class Main extends Application {

	private static Stage primary;
	private static MainModel model = MainModel.getMainModel();

	/**
	 * Entry point for Quinzical.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.getIcons()
					.add(new Image(Main.class.getResourceAsStream("/resources/images/thumb-icon.png")));
			Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenuView.fxml"));
			Scene scene = new Scene(root, 1300, 800);
			// Load in the CSS stylesheet for the button highlights for the current scene
			scene.getStylesheets().add(getClass().getResource("/resources/stylesheet/style.css").toExternalForm());
			primary = primaryStage;
			primaryStage.setMinWidth(1000);
			primaryStage.setMinHeight(837);
			primaryStage.setScene(scene);

			// Save on quit
			primaryStage.setOnCloseRequest(e -> {
				model.toJSONFile();
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

	public static void setPrimary(Stage primary) {
		Main.primary = primary;
	}
	
	
}
