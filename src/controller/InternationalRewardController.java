package controller;

import application.Main;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InternationalRewardController {

    @FXML
    private Button buttonOk;

    @FXML
    private Label header;

    @FXML
    void onClickButtonOk(Event e) {
    	Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
    	stage.close();
    	Main.getPrimaryStage().getScene().getRoot().setEffect(null);
    }

}
