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
		if(this.model.isAddedToLeaderboard()) {
			this.labelBiline.setText("SCORE HAS ALREADY BEEN ADDED TO LEADERBOARD, YOU CAN PRESS \'START OVER\' TO PLAY AGAIN");
		}
		else if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
			this.model.getLeaderboard().addToLeaderboard(this.model.getName().get(), this.model.getGameWinnings().get());
			this.model.setAddedToLeaderboard(true);
		} else {
			this.labelBiline.setText("DUE TO PRACTICE MODE SCORE HAS NOT BEEN ADDED TO THE LEADERBOARD");
		}

		if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
			this.labelWinnings.textProperty().bind(this.model.getGameWinnings().asString());
			this.labelWinningsBig.textProperty().bind(this.model.getGameWinnings().asString());
		} else {
			this.labelWinnings.textProperty().bind(this.model.getPracticeWinnings().asString());
			this.labelWinningsBig.textProperty().bind(this.model.getPracticeWinnings().asString());
		}
		this.labelName.textProperty().bind(this.model.getName());
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

		ArrayList<QuinzicalTuple> questionList = this.model.getQuestions();
		for (QuinzicalTuple question : questionList) {
			question.setCompleted(false);
		}

		this.model.setCurrentQuestion(null);
		this.model.setAddedToLeaderboard(false);
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}
}
