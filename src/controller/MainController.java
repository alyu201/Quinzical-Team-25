package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.QuinzicalTuple;
import model.MainModel;

/**
 * Controller for MainView. Displays a title, grid of buttons containing each
 * category question and the users total winnings.
 */
public class MainController {

	public static MainModel model;

	@FXML
	private Button buttonReset;

	@FXML
	private Text labelWinnings;

	@FXML
	private GridPane gridQuestions;

	public void initialize() {

		// Setting winnings amount
		this.labelWinnings.setText("Winnings: $" + model.getWinnings());

		// Reset action
		this.buttonReset.setOnAction(event -> {
			this.gridQuestions.getChildren().forEach(x -> {
				if (x.isDisabled()) {
					x.setDisable(false);
				}
			});
			// Winnings will be zero as state has been reset
			this.labelWinnings.setText("Winnings: $" + model.getWinnings());
		});

		// Filter an array for unique category names
		ArrayList<String> uniqueCategories = new ArrayList<String>();
		model.getQuestions().forEach(q -> {
			if (!uniqueCategories.contains(q.getCategory())) {
				uniqueCategories.add(q.getCategory());
			}
		});

		// Populate jeopardy board with category labels and question buttons
		int col = -1;
		int row = 1;
		for (String category : uniqueCategories) {
			row = 1;
			++col;

			// Add category label to grid
			String uppercaseCategory = category.substring(0, 1).toUpperCase() + category.substring(1);
			Label categoryLabel = new Label(uppercaseCategory);
			categoryLabel.setStyle("-fx-font-size: 30");
			categoryLabel.setPrefSize(200, 100);
			categoryLabel.setAlignment(Pos.CENTER);
			this.gridQuestions.add(categoryLabel, col, 0);

			// Filter questions by current category
			ArrayList<QuinzicalTuple> filteredQuestions = new ArrayList<QuinzicalTuple>();
			for (QuinzicalTuple question : model.getQuestions()) {
				if (question.getCategory().equals(category)) {
					filteredQuestions.add(question);
				}
			}
			for (QuinzicalTuple question : filteredQuestions) {
				// Add question button to grid
				Button buttonQuestion = new Button("0");
				buttonQuestion.setPrefSize(300, 50);
				buttonQuestion.setStyle("-fx-font-size:20");
				buttonQuestion.setAlignment(Pos.CENTER);

				// Disable button if question has been answered
				if (question.getCompleted() == true) {
					buttonQuestion.setDisable(true);
				}

				// Change to question scene for selected question
				buttonQuestion.setOnAction(event -> {
					// Set this question to be completed
					model.setCurrentQuestion(question);
					//model.setCompleted(model.getCurrentQuestion());

					// Change to question scene
					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuestionView.fxml"));
						QuestionController controller = new QuestionController();
						loader.setController(controller);
						model.setCurrentQuestion(question);
						Parent sParent = loader.load();
						Scene sScene = new Scene(sParent, 800, 800);
						Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
						window.setScene(sScene);
						window.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				this.gridQuestions.add(buttonQuestion, col, row);
				++row;
			}
		}

		// Check if jeopardy game is complete. If completed create a dialog finalizing
		// the result. This is done on another thread such that it does not halt the
		// MainMenu scene from drawing
		new Thread() {

			@Override
			public void run() {
				int completedQuestions = (int) model.getQuestions().stream()
						.filter(question -> question.getCompleted() == true).count();
				if (completedQuestions == model.getQuestions().size()) {
					Alert alert = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
					alert.setTitle("jeopardy");
					alert.setHeaderText("Congratulations!");
					alert.setContentText("You have completed your game of Jeopardy. Your total winnings are $"
							+ model.getWinnings() + ". Press the 'Reset' button if you would like to play again");
					alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label)
							.forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
					alert.show();
				}
			}
		}.run();
	}

	public MainController() {
		model = MainModel.getMainModel();
	}

}
