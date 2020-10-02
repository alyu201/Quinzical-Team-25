package controller;

import java.util.ArrayList;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.GameMode.GameType;
import model.MainModel;
import model.QuinzicalTuple;
import model.GameMode.GameType;
import utilities.SceneManager;

public class EndController {

	private MainModel model;

	@FXML
	private Button buttonInfo;

	@FXML
	private Label labelName;

	@FXML
	private Label labelWinnings;

	@FXML
	private HBox userDetails;

	@FXML
	private Label labelCorrect;

	@FXML
	private Label labelWinningsBig;

	@FXML
	private Label labelBiline;

	@FXML
	private Label labelQuestionWinnings;

	@FXML
	private Button buttonContinue;

	public void initialize() {
		this.model = model.getMainModel();

		// Add to leaderboard
		if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
		}
		else {
			this.labelBiline.setText("DUE TO PRACTICE MODE SCORE HAS NOT BEEN ADDED TO THE LEADERBOARD");

		}
		
		if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
			this.labelWinnings.textProperty().bind(this.model.getGameWinnings().asString());
			this.labelWinningsBig.textProperty().bind(this.model.getGameWinnings().asString());
		} else {
			this.labelWinnings.textProperty().bind(this.model.getPracticeWinnings().asString());
			this.labelWinningsBig.textProperty().bind(this.model.getPracticeWinnings().asString());
		}
	}

	@FXML
	private void onClickButtonInfo(Event e) {
		SceneManager.addStage(getClass().getResource("/view/InfoView.fxml"), e);
	}

	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	@FXML
	private void onClickLabelName(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/NameView.fxml"), e);
	}

	@FXML
	private void onClickButtonContinue(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

	@FXML
	private void onClickButtonStartOver(Event e) {
		if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
			this.model.setGameWinnings(0);
			this.model.setGameQuestions(new ArrayList<QuinzicalTuple>());
			this.model.setAllCompletedGame(false);
		} else {
			this.model.setPracticeWinnings(0);
			this.model.setPracticeQuestions(new ArrayList<QuinzicalTuple>());
			this.model.setAllCompletedPractice(false);
		}
		this.model.setCurrentQuestion(null);
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}
}
