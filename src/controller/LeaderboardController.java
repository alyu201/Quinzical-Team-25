package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import model.MainModel;
import model.GameMode.GameType;
import utilities.SceneManager;

/**
 * LeaderboardController is the controller for LeaderboardView.
 * LeaderboardController handles and orders a set of leaderboard values for
 * completed games. LeaderboardController orders the values in the leaderboard
 * by maximum score first.
 * 
 */
public class LeaderboardController {

	private MainModel model;

	// Rank of score
	@FXML
	ListView<String> rankList;

	// Name of score holder
	@FXML
	ListView<String> nameList;

	// Score a name has achieved
	@FXML
	ListView<String> scoreList;

	@FXML
	private Label labelName;

	@FXML
	private Label labelWinnings;

	@FXML
	private HBox userDetails;

	@FXML
	private Button buttonInfo;

	@FXML
	private Button buttonSettings;

	/**
	 * Initialize the controller and populate the name, winnings and functions of
	 * user details within the menu.
	 */
	public void initialize() {
		this.model = model.getMainModel();
		if (this.model.getName().getValue() != null) {
			this.labelName.textProperty().bind(this.model.getName());
			if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
				this.labelWinnings.textProperty().bind(this.model.getGameWinnings().asString());
			} else {
				this.labelWinnings.textProperty().bind(this.model.getPracticeWinnings().asString());
			}
			this.userDetails.setVisible(true);
		} else {
			this.userDetails.setVisible(false);
		}

		// Populate listView
		ObservableList<String> userRank = FXCollections.observableArrayList();
		ObservableList<String> userName = FXCollections.observableArrayList();
		ObservableList<String> userScore = FXCollections.observableArrayList();
		HashMap<String, Integer> map = this.model.getLeaderboard().getMap();
		List<String> keys = new ArrayList<String>(map.keySet());
		for (int i = 0; i < keys.size(); i++) {
			userRank.add(Integer.toString(i + 1));
			userName.add(keys.get(i));
			userScore.add("$" + map.get(keys.get(i)).toString());
		}
		rankList.setItems(userRank);
		nameList.setItems(userName);
		scoreList.setItems(userScore);
	}

	@FXML
	public void onClickButtonReturn(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

	@FXML
	private void onClickButtonInfo(Event e) {
		SceneManager.addStage(getClass().getResource("/view/InfoView.fxml"), e);
	}

	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.addStage(getClass().getResource("/view/SettingsView.fxml"), e);
	}
}
