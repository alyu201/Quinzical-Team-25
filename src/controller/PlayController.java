package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.MainModel;
import utilities.SceneManager;

public class PlayController {

	private MainModel model;

	@FXML
	private Button buttonPlay;

	@FXML
	public void initialize() {
		this.model = model.getMainModel();
	}

	@FXML
	private void onClickButtonBack(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

	@FXML
	private void onClickButtonPlay(Event e) {

	}
}
