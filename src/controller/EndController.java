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
import utilities.SceneManager;

/**
 * Endcontroller acts as the controller for EndView. EndController handles the
 * case in which the user has answered all the questions in their question set.
 * It displays a total winnings of the user, adds the user's score to the
 * leaderboard and gives the user the option of either 'starting over' which
 * preserves their name but resets their score and question set, or continuing
 * in their current state. If the user decides to continue in their current
 * state they will be softlocked out of the game mode until they either 'start
 * over' or 'reset'.
 */
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

	/**
	 * Initialize the controller and populate the name, winnings and functions of
	 * user details within the menu. Does not add the score to the leaderboard if
	 * the user is in practice mode.
	 */
	public void initialize() {
		this.model = model.getMainModel();

		// Set biline
		if ((this.model.isAddedToLeaderboardGame() && this.model.getCurrentGameType().equals(GameType.GAMESMODULE))
				|| (this.model.isAddedToLeaderboardInternational()
						&& this.model.getCurrentGameType().equals(GameType.INTERNATIONALMODULE))) {
			this.labelBiline
					.setText("SCORE HAS ALREADY BEEN ADDED TO LEADERBOARD, YOU CAN PRESS \'START OVER\' TO PLAY AGAIN");
		}

		// Add to leaderboard
		if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
			this.model.getLeaderboard().addToLeaderboard(this.model.getName().get(),
					this.model.getGameWinnings().get());
			this.model.setAddedToLeaderboardGame(true);
		} else if (this.model.getCurrentGameType().equals(GameType.INTERNATIONALMODULE)) {
			this.model.getLeaderboard().addToLeaderboard(this.model.getName().get(),
					this.model.getInternationalWinnings().get());
			this.model.setAddedToLeaderboardInternational(true);
		} else {
			this.labelBiline.setText("DUE TO PRACTICE MODE SCORE HAS NOT BEEN ADDED TO THE LEADERBOARD");
		}

		// Display winnings
		if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
			this.labelWinnings.textProperty().bind(this.model.getGameWinnings().asString());
			this.labelWinningsBig.textProperty().bind(this.model.getGameWinnings().asString());
		} else if (this.model.getCurrentGameType().equals(GameType.INTERNATIONALMODULE)) {
			this.labelWinnings.textProperty().bind(this.model.getInternationalWinnings().asString());
			this.labelWinningsBig.textProperty().bind(this.model.getInternationalWinnings().asString());
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

	/**
	 * Continue to MainModelView. Does not 'start over' the users state
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonContinue(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

	/**
	 * Continue to MainModelView. Gives the user score and question set a reset. The
	 * rest of the model is preserved.
	 * 
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonStartOver(Event e) {
		// Restart
		if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
			this.model.setGameWinnings(0);
			this.model.setGameQuestions(new ArrayList<QuinzicalTuple>());
			this.model.setAllCompletedGame(false);
			this.model.setAddedToLeaderboardGame(false);
		} else if (this.model.getCurrentGameType().equals(GameType.INTERNATIONALMODULE)) {
			this.model.setInternationalWinnings(0);
			this.model.setInternationalQuestions(new ArrayList<QuinzicalTuple>());
			this.model.setAllCompletedInternational(false);
			this.model.setAddedToLeaderboardInternational(false);
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
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}
}
