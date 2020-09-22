package controller;

import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import utilities.SceneManager;

public class SettingsController {

	@FXML
	private Slider sliderVolume;

	@FXML
	private Text textVolume;

	@FXML
	public void initialize() {
		textVolume.textProperty().bind(Bindings.format("%.0f", sliderVolume.valueProperty()));
	}
	
	@FXML
	private void onClickButtonBack(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

}
