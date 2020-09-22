package controller;

import org.json.simple.JSONObject;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import model.MainModel;
import utilities.SceneManager;

public class LeaderboardController {

	private MainModel model;
	
	@FXML
	TableView<String> tableViewLeaderboard;

	public void initialize() {
		this.model = model.getMainModel();
		this.model.getLeaderboard().forEach((key, value) -> {
		});
		
	}

	@FXML
	public void onClickButtonBack(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}
}
