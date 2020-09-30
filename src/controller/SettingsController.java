package controller;

import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import model.MainModel;
import utilities.SceneManager;

public class SettingsController {

	private MainModel model;

	@FXML
	private Button buttoninfo;
	
	@FXML
	private Slider sliderVolume;

	@FXML
	private Text textVolume;

	@FXML
	private Slider sliderSpeed;

	@FXML
	private Text textSpeed;

	@FXML
	private Label labelWinnings;

	@FXML
	private Label labelName;

	@FXML
	public void initialize() {
		this.model = model.getMainModel();
		
		// Display name and winnings
//		labelWinnings.setText("$" + this.model.getWinnings());
//		labelName.setText(this.model.getName());
		this.labelName.textProperty().bind(this.model.getName());
		this.labelWinnings.textProperty().bind(this.model.getWinnings().asString());

		// Display position of slider and state of labels
		textVolume.setText("" + model.getSettings().getVolume());
		textSpeed.setText("" + model.getSettings().getSpeed());
		sliderVolume.setValue(new Double(model.getSettings().getVolume()));
		sliderSpeed.setValue(new Double(model.getSettings().getSpeed()));

		// Save state of voice speed
		sliderVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
			textVolume.setText("" + newValue.intValue());
			this.model.getSettings().setVolume(newValue.intValue());
		});

		// Save state of voice speed
		sliderSpeed.valueProperty().addListener((observable, oldValue, newValue) -> {
			textSpeed.setText("" + newValue.intValue());
			this.model.getSettings().setSpeed(newValue.intValue());
		});
	}

	@FXML
	private void onClickButtonBack(Event e) {
		model.toJSONFile();
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}
	
	@FXML
	private void onClickButtonInfo(Event e) {
		SceneManager.addStage(getClass().getResource("/view/InfoView.fxml"), e);
	}

}
