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
		System.out.println("play");
		new Thread() {

			@Override
			public void run() {
				try {
					String command = "festival";
					ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
					Process process = pb.start();
					BufferedWriter stdin = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
					BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
					BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

					stdin.write("(SayText \"hello there\")");
					stdin.flush();

					int exitCode = process.waitFor();
					if (exitCode == 0) {
						System.out.println("passed");
					} else {
						System.out.println("failed");
					}
					stdin.close();
					stdout.close();
					stderr.close();
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
