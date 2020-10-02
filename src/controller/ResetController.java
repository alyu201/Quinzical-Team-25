package controller;

import java.util.ArrayList;
import java.util.HashMap;

import application.Main;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Leaderboard;
import model.MainModel;
import model.QuinzicalTuple;
import utilities.SceneManager;

public class ResetController {
	
	private MainModel model;
	@FXML
	private Button buttonYes;
	
	@FXML
	private Button buttonNo;
	
	public void initialize() {
		this.model = model.getMainModel();
	}
	
	@FXML
	private void onClickButtonNo(Event e) {
		Stage stage = (Stage) buttonNo.getScene().getWindow();
		stage.close();
	}
	
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
		ArrayList<QuinzicalTuple> questionList = this.model.getQuestions();
		for (QuinzicalTuple question : questionList) {
			question.setCompleted(false);
		}
		
		SceneManager.changeScene(getClass().getResource("/view/ResetConfirmView.fxml"), e);
	}
}
