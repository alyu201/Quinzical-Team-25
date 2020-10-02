package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import model.MainModel;
import model.GameMode.GameType;
import utilities.SceneManager;

/**
 * Controller for MainView. Displays a title, grid of buttons containing each
 * category question and the users total winnings.
 */
public class NameController {

	public MainModel model;

	@FXML
	private Label labelTitleName;

	@FXML
	private TextField textFieldName;

	@FXML
	private Label labelName;

	@FXML
	private Label labelWinnings;
	
	@FXML
	private HBox userDetails;
	
	@FXML
	private Label labelWarning;

	@FXML
	private Button buttonContinue;
	
	@FXML
	private Button buttonInfo;
	
	@FXML
	private Button buttonEnter;
	
	@FXML
	private Button buttonSettings;

	public void initialize() {
		this.model = model.getMainModel();
		if(this.model.getName().getValue() != null) {
			this.labelName.textProperty().bind(this.model.getName());
			if(this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
				this.labelWinnings.textProperty().bind(this.model.getGameWinnings().asString());
			} else {
				this.labelWinnings.textProperty().bind(this.model.getPracticeWinnings().asString());
			}
			this.userDetails.setVisible(true);
		} else {
			this.userDetails.setVisible(false);
		}
	}

	@FXML
	private void onClickButtonInfo(Event e) {
		SceneManager.addStage(getClass().getResource("/view/InfoView.fxml"), e);
	}

	@FXML
	private void onClickLabelName(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/NameView.fxml"), e);
	}

	@FXML
	private void onClickButtonSettings(Event e) {
		SceneManager.addStage(getClass().getResource("/view/SettingsView.fxml"), e);
	}

	@FXML
	private void onClickButtonContinue(Event e) {
		this.model.toJSONFile();
		// checks for whitespace names
		if (this.model.getName().getValue().length() != 0) {
			SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
		} else {
			this.labelWarning.setVisible(true);
		}
	}
	
	@FXML
	private void onClickButtonEnter(Event e) {
		this.model.setName(this.textFieldName.getText().trim());
		this.onClickButtonContinue(e);
	}

	@FXML
	private void onInputTextFieldName(KeyEvent ke) {
		this.model.setName(this.textFieldName.getText().trim());
		if(ke.getCode().equals(KeyCode.ENTER)) {
			this.onClickButtonContinue(ke);
		}
	}
}
