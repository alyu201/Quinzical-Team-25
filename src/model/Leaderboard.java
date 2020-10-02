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
	private HashMap<String, Integer> map;

	public Leaderboard(HashMap<String, Integer> map) {
		this.map = map;
	}

	public HashMap<String, Integer> getMap() {
		return map;
	}

	public void setLeaderboard(HashMap<String, Integer> leaderboard) {
		this.map = leaderboard;
	}

	public void addToLeaderboard(String name, int winnings) {
		this.map.put(name, winnings);
	}

}
