package controller;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Leaderboard;
import model.MainModel;
import model.QuinzicalTuple;
import utilities.SceneManager;

/**
 * ResetController acts as the controller for the ResetView. ResetController provides 
 * the options 'yes' or 'no' on whether the user confirms to reseting all game data
 */
public class ResetController {

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
	 * Exits the ResetView pop-up window by selecting the 'no' option
	 * @param e
	 */
	@FXML
	private void onClickButtonNo(Event e) {
		Stage stage = (Stage) buttonNo.getScene().getWindow();
		stage.close();
	}

	/**
	 * Resets all game data from selecting 'yes' option 
	 * @param e
	 */
	@FXML
	private void onClickButtonYes(Event e) {
		this.model.setName(null);
		this.model.setGameWinnings(0);
		this.model.setPracticeWinnings(0);
		this.model.setCurrentQuestion(null);
		this.model.setGameQuestions(new ArrayList<QuinzicalTuple>());
		this.model.setPracticeQuestions(new ArrayList<QuinzicalTuple>());
		this.model.setGameQuestions(new ArrayList<QuinzicalTuple>());
		this.model.setLeaderboard(new Leaderboard(new HashMap<String, Integer>()));
		this.model.setAllCompletedGame(false);
		this.model.setAllCompletedPractice(false);
		this.model.setAddedToLeaderboard(false);
		ArrayList<QuinzicalTuple> questionList = this.model.getQuestions();
		for (QuinzicalTuple question : questionList) {
			question.setCompleted(false);
		}

		SceneManager.changeScene(getClass().getResource("/view/ResetConfirmView.fxml"), e);
	}
}
