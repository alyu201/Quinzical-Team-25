package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import application.Main;
import model.QuinzicalTuple;
import model.GameMode.GameType;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import model.MainModel;
import utilities.SceneManager;

public class PointsPlayController {

	private MainModel model;

	@FXML
	private GridPane gridPanePoints;

	@FXML
	private Button buttonInfo;

	@FXML
	private Label labelName;

	@FXML
	private Label labelWinnings;

	@FXML
	private HBox userDetails;

	@FXML
	private Label labelCategory;

	@FXML
	private Button buttonSettings;
	
	@FXML
	public void initialize() {
		this.model = model.getMainModel();
		this.labelName.textProperty().bind(this.model.getName());
		this.labelWinnings.textProperty().bind(this.model.getWinnings().asString());

		// lists whether game questions are completed
		List<Boolean> allCompleted = new ArrayList<Boolean>();
		for (QuinzicalTuple question : this.model.getGameQuestions()) {
			allCompleted.add(question.getCompleted());
		}
		if (!allCompleted.contains(false)) {
			this.model.setAllCompleted(true);
		} else {
			this.model.setAllCompleted(false);
		}
		
		// New questions (no game questions or game questions all completed)
		if (this.model.getGameQuestions().size() == 0 || !allCompleted.contains(false)) {
			ArrayList<String> categoriesSet = new ArrayList<String>();
			Random rand = new Random();

			while (categoriesSet.size() < 5) {
				int nextRandBounded = Math.abs(rand.nextInt() % (this.model.getCategories().size() - 1));
				String currentCategory = this.model.getCategories().get(nextRandBounded);
				if (!categoriesSet.contains(currentCategory)) {
					categoriesSet.add(currentCategory);
				}
			}

			ArrayList<QuinzicalTuple> questionSet = new ArrayList<QuinzicalTuple>();
			for (String category : categoriesSet) {
				ArrayList<QuinzicalTuple> questionSetCurrentCategory = new ArrayList<QuinzicalTuple>();
				ArrayList<QuinzicalTuple> filteredQuestions = new ArrayList<QuinzicalTuple>();

				for (QuinzicalTuple question : this.model.getQuestions()) {
					if (question.getCategory().equals(category)) {
						filteredQuestions.add(question);
					}
				}

				int i = 0;
				while (i < 5) {
					int nextRandBounded = Math.abs(rand.nextInt() % (filteredQuestions.size() - 1));
					QuinzicalTuple currentQuestion = filteredQuestions.get(nextRandBounded);
					if (!questionSetCurrentCategory.contains(currentQuestion)) {
						questionSetCurrentCategory.add(currentQuestion);
						++i;
					}
					questionSet.addAll(questionSetCurrentCategory);
				}

				// Finally add those questions
				this.model.setGameQuestions(questionSet);
			}
		}

		ArrayList<String> questionCategories = new ArrayList<String>();
		for (QuinzicalTuple question : this.model.getGameQuestions()) {
			if (!questionCategories.contains(question.getCategory())) {
				questionCategories.add(question.getCategory());
			}
		}

		this.gridPanePoints.setAlignment(Pos.CENTER);
		int r = 0;
		int c = 0;
		for (String category : questionCategories) {
			boolean flag = false;
			Label label = new Label(category);
			label.setWrapText(true);
			label.setPrefWidth(200);
			label.setStyle("-fx-font-weight: bold; -fx-text-fill: #f2fff3; -fx-font-size: 18px;");
			label.setAlignment(Pos.CENTER);
			label.setTextAlignment(TextAlignment.CENTER);
			this.gridPanePoints.add(label, c, r);
			r++;
			ArrayList<QuinzicalTuple> filteredQuestions = new ArrayList<QuinzicalTuple>();
			for (QuinzicalTuple question : this.model.getGameQuestions()) {
				if (!filteredQuestions.contains(question) && category.equals(question.getCategory())) {
					filteredQuestions.add(question);
				}
			}

			// TODO: if possible make the question not completed at the top or make gameQuestions only those that are not completed
			// sort questions by worth lowest worth first
			Collections.sort(filteredQuestions, ((x, y) -> {
				return Integer.compare(((QuinzicalTuple) x).getWorth(), ((QuinzicalTuple) y).getWorth());
			}));

			for (QuinzicalTuple question : filteredQuestions) {
				Button button = new Button("$" + question.getWorth());
				button.setStyle("-fx-background-color: #00c3b1; -fx-background-radius: 30; -fx-text-fill: #f2fff3;"
						+ " -fx-font-weight: bold; -fx-font-size: 16px;");
				if (question.getCompleted() == true || flag == true) {
					button.setStyle("-fx-background-color: #0b2247; -fx-background-radius: 30; -fx-font-size: 16px;"
							+ " -fx-text-fill: #f2fff3; -fx-font-weight: bold;");
					button.setDisable(true);
				}


				button.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent me) {
						model.setCompleted(GameType.GAMESMODULE, question);
						model.setCurrentQuestion(question);
						model.toJSONFile();
						SceneManager.changeScene(getClass().getResource("/view/QuestionView.fxml"), me);
					}
				});
				button.setPrefSize(200, 100);
				this.gridPanePoints.add(button, c, r);

				if(question.getCompleted() == false) {
					flag = true;
				}

				++r;
			}
			++c;
			r = 0;
		}

	}

	@FXML
	private void onClickButtonBack(Event e) {
		model.toJSONFile();
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

	@FXML
	private void onClickLabelName(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/NameView.fxml"), e);
	}

	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.addStage(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	@FXML
	private void onClickButtonInfo(Event e) {
		SceneManager.addStage(getClass().getResource("/view/InfoView.fxml"), e);
	}
}
