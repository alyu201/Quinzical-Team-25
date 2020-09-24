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
public class Leaderboard implements JSONString<Leaderboard>, JSONFile<Leaderboard> {
	private static final File LEADERBOARD_FILE = new File("./leaderboard.json");
	private HashMap<String, Integer> leaderboard;

	public Leaderboard() {
		this.fromJSONFile();
	}

	public Leaderboard(HashMap<String, Integer> map) {
		leaderboard = map;
	}

	public HashMap<String, Integer> getLeaderboard() {
		return leaderboard;
	}

	public void setLeaderboard(HashMap<String, Integer> leaderboard) {
		this.leaderboard = leaderboard;
	}

	@Override
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

	@Override
	public void toJSONFile() {
		try (FileWriter file = new FileWriter(LEADERBOARD_FILE)) {
			file.write(this.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
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
	@Override
	public String toJSONString() {
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject();
		leaderboard.forEach((key, value) -> {
			obj.put(key, value);
		});
		return obj.toJSONString();
	}

}
