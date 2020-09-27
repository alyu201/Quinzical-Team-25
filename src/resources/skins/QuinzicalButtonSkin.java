package resources.skins;

import javafx.scene.control.Button;
import javafx.scene.control.skin.ButtonSkin;

public class QuinzicalButtonSkin extends ButtonSkin {

    public QuinzicalButtonSkin(Button button) {
        super(button);

    	String defaultStyle = button.getStyle();
        button.setOnMouseEntered(e -> {
        	button.setStyle("-fx-background-radius: 30; -fx-background-color: #0e6f78;"
        			+ "-fx-text-fill: #f2fff3;");
        });

        button.setOnMouseExited(e -> {
        	button.setStyle(defaultStyle);
        });
    }
}
