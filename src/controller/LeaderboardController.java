package controller;

import org.json.simple.JSONObject;

import javafx.event.Event;
import javafx.fxml.FXML;
import utilities.SceneManager;

public class LeaderboardController {

	private MainModel model;

	public void initialize() {
		System.out.println("init");
	}
	
	@FXML
	public void onClickButtonBack(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}
}
