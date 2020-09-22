package controller;

import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import model.MainModel;
import utilities.SceneManager;

public class SettingsController {

	private MainModel model;

	@FXML
	private Slider sliderVolume;

	@FXML
	private Text textVolume;

	@FXML
	public void initialize() {
		this.model = model.getMainModel();

		textVolume.setText("" + model.getVolume());
		sliderVolume.setValue(new Double(model.getVolume()));
		sliderVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
			textVolume.setText("" + newValue.intValue());
			this.model.setVolume(newValue.intValue());
			System.out.println("" + this.model.getVolume());
		});

	}

	@FXML
	private void onClickButtonBack(Event e) {
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

}
