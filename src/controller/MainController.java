package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

	public static MainModel model;

	@FXML
	private Button buttonReset;

	// TODO: Add as TextFlow and add coloured text as green
	@FXML
	private Text labelWinnings;

	@FXML
	private GridPane gridQuestions;

	public void initialize() {
		this.labelWinnings.setText("Winnings: $" + model.getWinnings());
		this.buttonReset.setOnAction(event -> {
			model.resetState();
			this.gridQuestions.getChildren().forEach(x -> {
				if (x.isDisabled()) {
					x.setDisable(false);
				}
			});

		});

		ArrayList<String> uniqueCategories = new ArrayList<String>();
		model.getQuestions().forEach(q -> {
			if (!uniqueCategories.contains(q.category)) {
				uniqueCategories.add(q.category);
			}
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
					model.setCompleted(question);
					model.putState();
					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/QuestionView.fxml"));
						model.setCurrentQuestion(question);
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

		// this.gridQuestions.addEventHandler(arg0, arg1);
		System.out.println("changed!");

	}

	public MainController() {
		model = MainModel.getMainModel();
	}
}
