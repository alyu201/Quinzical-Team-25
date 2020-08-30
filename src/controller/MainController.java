package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.JepordayTuple;
import model.MainModel;

public class MainController {

	private MainModel model;

	@FXML
	private Button buttonReset;

	@FXML
	private Text labelWinnings;

	@FXML
	private GridPane gridQuestions;

	public void initialize() {
		this.buttonReset.setOnAction(event -> {
			this.model.resetState();
			this.gridQuestions.getChildren().forEach(x -> {
				if (x.isDisabled()) {
					x.setDisable(false);
				}
			});
			
			this.labelWinnings.setText("Winnings: $0");
			//this.labelWinnings.setTextFill(Color.web("#00FF00", 0.8));
		});

		ArrayList<String> uniqueCategories = new ArrayList<String>();
		this.model.getQuestions().forEach(q -> {
			if (!uniqueCategories.contains(q.category))
				uniqueCategories.add(q.category);
		});

		int col = -1;
		int row = 1;
		for (String category : uniqueCategories) {
			row = 1;
			++col;
			String uppercaseCategory = category.substring(0, 1).toUpperCase() + category.substring(1);
			Label categoryLabel = new Label(uppercaseCategory);
			categoryLabel.setStyle("-fx-font: 30 arial;");
			categoryLabel.setPrefSize(200, 100);
			this.gridQuestions.add(categoryLabel, col, 0);
			ArrayList<JepordayTuple> filteredQuestions = new ArrayList<JepordayTuple>();
			for (JepordayTuple question : this.model.getQuestions()) {
				if (question.category.equals(category)) {
					filteredQuestions.add(question);
				}
			}
			for (JepordayTuple question : filteredQuestions) {
				Button buttonQuestion = new Button(question.worth);
				buttonQuestion.setPrefSize(300, 30);
				buttonQuestion.setAlignment(Pos.CENTER);
				System.out.println(question.toString());
				if (question.completed == true) {
					buttonQuestion.setDisable(true);
				}
				buttonQuestion.setOnAction(event -> {

					buttonQuestion.setDisable(true);
					this.model.setCompleted(question);
					this.model.putState();
					try {
						Stage primaryStage = (Stage) buttonReset.getScene().getWindow();
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuestionView.fxml"));
						QuestionController controller = new QuestionController(question);
						loader.setController(controller);
						Parent root = loader.load();
						primaryStage.getScene().setRoot(root);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				this.gridQuestions.add(buttonQuestion, col, row);
				++row;
			}

		}

	}

	public MainController() {
		this.model = new MainModel();
	}

	// switch to question scene

}
