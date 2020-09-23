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

		textVolume.setText("" + model.getSettings().getVolume());
		sliderVolume.setValue(new Double(model.getSettings().getVolume()));
		sliderVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
			textVolume.setText("" + newValue.intValue());
			this.model.getSettings().setVolume(newValue.intValue());
			System.out.println("" + this.model.getSettings().getVolume());
		});

	}

	@FXML
	private void onClickButtonBack(Event e) {
		model.getSettings().toJSONFile();
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

}
