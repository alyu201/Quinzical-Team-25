package controller;

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
	}

	@FXML
	public void onClickButtonBack(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

}
