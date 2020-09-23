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
	private Slider sliderSpeed;

	@FXML
	private Text textSpeed;

	@FXML
	public void initialize() {
		this.model = model.getMainModel();

		textVolume.setText("" + model.getSettings().getVolume());
		textSpeed.setText("" + model.getSettings().getSpeed());
		sliderVolume.setValue(new Double(model.getSettings().getVolume()));
		sliderSpeed.setValue(new Double(model.getSettings().getSpeed()));
		sliderVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
			textVolume.setText("" + newValue.intValue());
			this.model.getSettings().setVolume(newValue.intValue());
			System.out.println("" + this.model.getSettings().getVolume());
		});

		sliderSpeed.valueProperty().addListener((observable, oldValue, newValue) -> {
			textSpeed.setText("" + newValue.intValue());
			this.model.getSettings().setSpeed(newValue.intValue());
			System.out.println("" + this.model.getSettings().getSpeed());
		});

	}

	@FXML
	private void onClickButtonBack(Event e) {
		model.getSettings().toJSONFile();
		SceneManager.changeScene(getClass().getResource("/view/MainMenuView.fxml"), e);
	}

}
