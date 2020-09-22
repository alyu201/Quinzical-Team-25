package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import utilities.SceneManager;

public class LeaderboardController {

	public void initialize() {
		System.out.println("init");
	}
	
	@FXML
	public void onClickButtonBack(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}
}
