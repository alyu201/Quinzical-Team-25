package resources.skins;

import javafx.scene.control.Button;
import javafx.scene.control.skin.ButtonSkin;

public class QuinzicalButtonSkin extends ButtonSkin {

    public QuinzicalButtonSkin(Button button) {
        super(button);

    	String defaultButtonStyle = button.getStyle();
        button.setOnMouseEntered(e -> {
        	String defaultTextStyle = "-fx-font-size: " + button.getFont().getSize() + "px;";
        	button.setStyle("-fx-background-radius: 30; -fx-background-color: #0e6f78;"
        			+ " -fx-text-fill: #f2fff3; -fx-font-weight: bold; " + defaultTextStyle);
        });

        button.setOnMouseExited(e -> {
        	button.setStyle(defaultButtonStyle);
        });
    }
}
