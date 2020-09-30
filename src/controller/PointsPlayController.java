package controller;

import java.util.ArrayList;
import java.util.Random;

import javafx.beans.binding.Bindings;
import model.QuinzicalTuple;
import model.GameMode.GameType;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.MainModel;
import utilities.SceneManager;

public class PointsPlayController {

	private MainModel model;

	@FXML
	private GridPane gridPanePoints;

	@FXML
	private Button buttonInfo;
	
	@FXML
	private Label labelName;

	@FXML
	private Label labelWinnings;
	
	@FXML
	private HBox userDetails;

	@FXML
	private Label labelCategory;

	@FXML
	public void initialize() {
		this.model = model.getMainModel();
		this.labelName.textProperty().bind(this.model.getName());
		this.labelWinnings.textProperty().bind(this.model.getWinnings().asString());
	}

	@FXML
	private void onClickButtonBack(Event e) {
		model.toJSONFile();
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

	@FXML
	private void onClickLabelName(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/NameView.fxml"), e);
	}

	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	@FXML
	private void onClickButtonInfo(Event e) {
		SceneManager.addStage(getClass().getResource("/view/InfoView.fxml"), e);
	}
}
