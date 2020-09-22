package controller;

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
		System.out.println("play");
		new Thread() {

			@Override
			public void run() {
				try {
					String command = "echo " + "hello I am testing voices" + " | festival --tts";
					ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
					pb.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
