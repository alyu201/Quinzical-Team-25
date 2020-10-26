package controller;

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
	
	@FXML
	private Button buttonReturnToMain;

	/**
	 * Initialize the controller and populate the name, winnings and functions of
	 * user details within the menu.
	 */
	public void initialize() {
		this.model = MainModel.getMainModel();
		if (this.model.getName().getValue() != null) {
			this.labelName.textProperty().bind(this.model.getName());
			if (this.model.getCurrentGameType().equals(GameType.GAMESMODULE)) {
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

	/**
	 * Save the current name. Return to MainMenuView.
	 * @param e Event that triggered this function
	 */
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

	/**
	 * Save the current name. Return to MainMenuView.
	 * @param e Event that triggered this function
	 */
	@FXML
	private void onClickButtonEnter(Event e) {
		this.model.setName(this.textFieldName.getText().trim());
		this.onClickButtonContinue(e);
	}

	/**
	 * Update the name every time the user types
	 * @param ke Event that triggered this function
	 */
	@FXML
	private void onInputTextFieldName(KeyEvent ke) {
		this.model.setName(this.textFieldName.getText().trim());
		if (ke.getCode().equals(KeyCode.ENTER)) {
			this.onClickButtonContinue(ke);
		}
	}
	
	/**
	 * Return to the main menu screen.
	 */
	@FXML
	private void onClickButtonReturnToMain(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}
}
