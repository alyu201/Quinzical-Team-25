package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.MainModel;

/**
 * Controller for MainView. Displays a title, grid of buttons containing each
 * category question and the users total winnings.
 */
public class NameController {

	public static MainModel model;

	@FXML
	private Label labelTitleName;

	@FXML
	private TextField textFieldName;

	public void initialize() {
		this.model = model.getMainModel();
	}



}
