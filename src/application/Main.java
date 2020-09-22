package application;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
			primaryStage.setScene(new Scene(root, 800, 800));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		dep();
		launch(args);
	}

	public static void dep() {
		JSONObject obj = new JSONObject();

		obj.put("name", "Pankaj Kumar");
		obj.put("age", new Integer(32));

		JSONArray cities = new JSONArray();
		cities.add("New York");
		cities.add("Bangalore");
		cities.add("San Francisco");

		obj.put("cities", cities);

		try {

			FileWriter file = new FileWriter("data.json");
			file.write(obj.toJSONString());
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.print(obj.toJSONString());
	}
}