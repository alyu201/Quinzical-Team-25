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
 * RewardController acts as a controller for the RewardView. RewardController 
 * provides the option 'continue' to return to either the PointsPlayView and 
 * PointsPracticeView depending on the current game type
 */
public class RewardController {

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
	private Label labelAnswer;

	@FXML
	private Label labelQuestionWinnings;

	@FXML
	private Button buttonContinue;

	@FXML
	private Button buttonSettings;

	/**
	 * Initializes the controller and populate the name, winnings, question worth and 
	 * answer on screen.
	 */
	public void initialize() {
		this.model = model.getMainModel();
		if (!(this.model.getName().getValue() == null)) {
			this.labelName.textProperty().bind(this.model.getName());
			if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
				this.labelWinnings.textProperty().bind(this.model.getGameWinnings().asString());
			} else {
				this.labelWinnings.textProperty().bind(this.model.getPracticeWinnings().asString());
			}
			userDetails.setVisible(true);
		} else {
			userDetails.setVisible(false);
		}
		this.labelAnswer.setText(this.model.getCurrentQuestion().getAnswers().get(0));
		this.labelQuestionWinnings.setText("" + this.model.getCurrentQuestion().getWorth());
		if (!this.model.getCurrentQuestion().getCorrectlyAnswered()) {
			this.labelAnswer
					.setText("The correct answer was \"" + this.model.getCurrentQuestion().getAnswers().get(0) + "\"");
			this.labelCorrect.setText("INCORRECT");
		}

	}

	@FXML
	private void onClickButtonInfo(Event e) {
		SceneManager.addStage(getClass().getResource("/view/InfoView.fxml"), e);
	}

	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.addStage(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	@FXML
	private void onClickLabelName(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/NameView.fxml"), e);
	}

	/**
	 * Checks whether all questions available are completed and changes screen to the corresponding 
	 * PointsPlayView, PointsPracticeView or EndView depending on game type after
	 * selecting 'continue' option
	 * @param e
	 */
	@FXML
	private void onClickButtonContinue(Event e) {

		boolean completeFlag = true;
		ArrayList<QuinzicalTuple> toIterate = null;
		if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
			toIterate = this.model.getGameQuestions();
		} else {
			toIterate = this.model.getPracticeQuestions();
		}

		for (QuinzicalTuple question : toIterate) {
			if (question.getCompleted().equals(false)) {
				completeFlag = false;
			}
		}

		if (completeFlag) {
			if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
				this.model.setAllCompletedGame(true);
			} else {
				this.model.setAllCompletedPractice(true);
				;
			}

			SceneManager.changeScene(getClass().getResource("/view/EndView.fxml"), e);
		} else {
			if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
				SceneManager.changeScene(getClass().getResource("/view/PointsPlayView.fxml"), e);
			} else {
				SceneManager.changeScene(getClass().getResource("/view/PointsPracticeView.fxml"), e);
			}
		}

	}
}
