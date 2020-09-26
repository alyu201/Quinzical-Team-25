package model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Wrapper type
 */
public class Leaderboard {
	private static final File LEADERBOARD_FILE = new File("./leaderboard.json");
	private HashMap<String, Integer> map;

	public Leaderboard() {
		this.fromJSONFile();
	}

	public Leaderboard(HashMap<String, Integer> map) {
		this.map = map;
	}

	public HashMap<String, Integer> getMap() {
		return map;
	}

	public void setLeaderboard(HashMap<String, Integer> leaderboard) {
		this.map= leaderboard;
	}

	public Leaderboard fromJSONFile() {
		JSONParser parser = new JSONParser();

		try (Reader reader = new FileReader(LEADERBOARD_FILE)) {

			JSONObject obj = (JSONObject) parser.parse(reader);
			return this.fromJSONString(obj.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void toJSONFile() {
		try (FileWriter file = new FileWriter(LEADERBOARD_FILE)) {
			file.write(this.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public Leaderboard fromJSONString(String xs) {
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(xs);

			HashMap<String, Integer> m = new HashMap<String, Integer>();
			obj.forEach((key, value) -> {
				m.put((String) key, Math.toIntExact((Long) value));
			});

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public String toJSONString() {
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject();
		map.forEach((key, value) -> {
			obj.put(key, value);
		});
		return obj.toJSONString();
	}

}
