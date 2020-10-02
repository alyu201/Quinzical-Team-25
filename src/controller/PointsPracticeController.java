package controller;

import java.util.ArrayList;
import java.util.Random;

import application.Main;
import javafx.beans.binding.Bindings;
import model.QuinzicalTuple;
import model.GameMode.GameType;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.MainModel;
import utilities.SceneManager;

public class PointsPracticeController {

	private MainModel model;

	@FXML
	private GridPane gridPanePoints;

	@FXML
	private Button buttonInfo;

	@FXML
	private Button buttonEnter;

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

	private Event event;

	@FXML
	public void initialize() {
		this.model = model.getMainModel();
		if (this.model.getName().getValue() != null) {
			this.labelName.textProperty().bind(this.model.getName());
			if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
				this.labelWinnings.textProperty().bind(this.model.getGameWinnings().asString());
			} else {
				this.labelWinnings.textProperty().bind(this.model.getPracticeWinnings().asString());
			}
			this.labelCategory.setText(this.model.getCurrentCategory());
			userDetails.setVisible(true);
		} else {
			userDetails.setVisible(false);
		}

		// There is no existing practice question set yet
		if (!(this.model.getPracticeQuestions().size() == 0)) {

		} else {
			// Scope to tuples for currentCategory
			String currentCategory = this.model.getCurrentCategory();
			ArrayList<QuinzicalTuple> xs = new ArrayList<QuinzicalTuple>();
			for (QuinzicalTuple question : this.model.getQuestions()) {
				if (question.getCategory().equals(currentCategory)) {
					xs.add(question);
				}
			}

			// If a category has less than 5 questions default to that length
			ArrayList<QuinzicalTuple> questionSet = new ArrayList<QuinzicalTuple>();
			Random rand = new Random();
			int limit = (xs.size() < 5) ? xs.size() : 5;

			// FIXME: brute add random question to bank
			int i = 0;
			while (i < limit) {
				int nextRand = Math.abs((rand.nextInt() % (xs.size() - 1)));
				QuinzicalTuple current = xs.get(nextRand);
				if (!questionSet.contains(current)) {
					questionSet.add(current);
					++i;
				}
			}

			// Sort questions by lowest worth first
			questionSet.sort((x, y) -> {
				if (x.getWorth() < y.getWorth()) {
					return -1;
				} else if (x.getWorth() > y.getWorth()) {
					return 1;
				} else {
					return 0;
				}
			});

			// set practice questions set
			this.model.setPracticeQuestions(questionSet);

		}

		// Add questions to screen
		int col = 0;
		for (QuinzicalTuple question : this.model.getPracticeQuestions()) {

			Button button = new Button("$" + question.getWorth());
			button.setPrefWidth(150);
			button.setPrefHeight(150);
			button.setStyle("-fx-background-color: #00C3B1; -fx-background-radius: 30; -fx-font-size: 25px;"
					+ " -fx-text-fill: #f2fff3; -fx-font-weight: bold;");
			if (question.getCompleted() == true) {
				button.setStyle("-fx-background-color: #0b2247; -fx-background-radius: 30; -fx-font-size: 25px;"
						+ " -fx-text-fill: #f2fff3; -fx-font-weight: bold;");
				button.setDisable(true);
			}
			button.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent me) {
					model.setCompleted(GameType.PRACTICEMODULE, question);
					model.setCurrentQuestion(question);
					model.toJSONFile();
					SceneManager.changeScene(getClass().getResource("/view/QuestionView.fxml"), me);
				}
			});
			this.gridPanePoints.add(button, col, 0);
			col++;
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
