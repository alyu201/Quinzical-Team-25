package controller;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.GameMode.GameType;
import model.MainModel;
import model.GameMode.GameType;
import utilities.SceneManager;

public class MainMenuController {

	private MainModel model;
	@FXML
	private Button buttonInfo;

	@FXML
	private Button buttonPlay;

	@FXML
	private Button buttonStartOver;

	@FXML
	private Button buttonPractice;

	@FXML
	private Button buttonLeaderboard;

	@FXML
	private Button buttonCreateQuestions;

	@FXML
	private Button buttonSettings;

	@FXML
	private Button buttonReset;

	@FXML
	private Button buttonQuit;

	@FXML
	private Label labelName;

	@FXML
	private Label labelWinnings;

	@FXML
	private HBox userDetails;

	public void initialize() {
		this.model = model.getMainModel();
		if (this.model.getName().getValue() != null) {
			this.labelName.textProperty().bind(this.model.getName());
			this.labelWinnings.textProperty().bind(this.model.getWinnings().asString());
			this.userDetails.setVisible(true);
		} else {
			this.userDetails.setVisible(false);
		}

		// User has practice question set
//		if(this.model.getPracticeQuestions().size() != 0) {
//			this.buttonPractice.setText("CONTINUE PRACTICING");
//		}

		// user has saved game
//		if(this.model.getGameQuestions().size() != 0) {
//			this.buttonPlay.setText("CONTINUE PLAYING");
//		}
	}

	@FXML
	private void onClickButtonInfo(Event e) {
		SceneManager.addStage(getClass().getResource("/view/InfoView.fxml"), e);
	}

	@FXML
	private void onClickButtonPlay(Event e) {
		this.model.setCurrentGameType(GameType.GAMESMODULE);
		if (this.model.getPracticeQuestions().size() != 0) {
			// name is empty and needs to be set
			if (this.model.getName().getValue() == null) {
				SceneManager.changeScene(getClass().getResource("/view/NameView.fxml"), e);
			} else {
				SceneManager.changeScene(getClass().getResource("/view/PointsPlayView.fxml"), e);
			}
		}
	}

	@FXML
	private void onClickButtonStartOver(Event e) {
		SceneManager.addStage(getClass().getResource("/view/StartOverView.fxml"), e);
	}

	@FXML
	private void onClickButtonPractice(Event e) {
		this.model.setCurrentGameType(GameType.PRACTICEMODULE);
		if (this.model.getPracticeQuestions().size() != 0) {
			SceneManager.changeScene(getClass().getResource("/view/PointsPracticeView.fxml"), e);
		} else {
			SceneManager.changeScene(getClass().getResource("/view/CategoryView.fxml"), e);
		}
	}

	@FXML
	private void onClickButtonLeaderboard(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/LeaderboardView.fxml"), e);
	}

	@FXML
	private void onClickButtonCreateQuestions(Event e) {
		System.out.println("create questions");
	}

	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	@FXML
	private void onClickButtonReset(Event e) {
		SceneManager.addStage(getClass().getResource("/view/ResetView.fxml"), e);
	}

	@FXML
	private void onClickButtonQuit(Event e) {
		// save and quit
		this.model.toJSONFile();
		Stage stage = (Stage) buttonQuit.getScene().getWindow();
		stage.close();
		System.exit(0);
	}

	@FXML
	private void onClickLabelName(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/NameView.fxml"), e);
	}
}
