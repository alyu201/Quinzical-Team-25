package controller;

import java.util.ArrayList;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.MainModel;
import model.QuinzicalTuple;

/**
 * StartOverController acts as the controller for the StartOverView. StartOverController 
 * provides the options 'yes' for resetting all game data except for user name and 'no' 
 * for exiting the popup window StartOverView.
 */
public class StartOverController {

	private MainModel model;

	@FXML
	private Button buttonYes;

	@FXML
	private Button buttonNo;

	/**
	 * Initialize the controller and get the main model of the game
	 */
	public void initialize() {
		this.model = MainModel.getMainModel();

	}

	/**
	 * Exits the StartOverView pop-up window by selecting the 'no' option
	 * @param e
	 */
	@FXML
	private void onClickButtonNo(Event e) {
		Stage stage = (Stage) buttonNo.getScene().getWindow();
		stage.close();
	}

	/**
	 * Resets all game data except for user name from selecting 'yes' option 
	 * @param e
	 */
	@FXML
	private void onClickButtonYes(Event e) {
		this.model.setGameWinnings(0);
		this.model.setInternationalWinnings(0);
		this.model.setPracticeWinnings(0);
		this.model.setCurrentQuestion(null);
		this.model.setGameQuestions(new ArrayList<QuinzicalTuple>());
		this.model.setPracticeQuestions(new ArrayList<QuinzicalTuple>());
		this.model.setInternationalQuestions(new ArrayList<QuinzicalTuple>());
		this.model.setAllCompletedGame(false);
		this.model.setAllCompletedPractice(false);
		this.model.setAddedToLeaderboardGame(false);
		this.model.setAddedToLeaderboardInternational(false);
		this.model.setInternationalUnlocked(false);
		ArrayList<QuinzicalTuple> questionList = this.model.getQuestions();
		for (QuinzicalTuple question : questionList) {
			question.setCompleted(false);
		}

		Stage stage = (Stage) buttonYes.getScene().getWindow();
		stage.close();
	}
}
