package model;

import java.util.HashMap;

/**
 * Leaderboard contains the user name (key) and user score (value) which will be 
 * displayed in the LeaderboardView after the user had completed the games module
 */
public class Leaderboard {
	private HashMap<String, Integer> map;

	public Leaderboard(HashMap<String, Integer> map) {
		this.map = map;
	}

	/**
	 * Returns the hash map containing the user name and user score (key and value)
	 * 
	 * @return map The hash map of the user scores
	 */
	public HashMap<String, Integer> getMap() {
		return map;
	}

	/**
	 * Allows the leaderboard hash map to be set to another
	 * 
	 * @param leaderboard The new leaderboard hash map to be set to
	 */
	public void setLeaderboard(HashMap<String, Integer> leaderboard) {
		this.map = leaderboard;
	}

	/**
	 * Add new user name and user score to leaderboard hashmap
	 * 
	 * @param name The name given by the user
	 * @param winnings The total winnings won by the user
	 */
	public void addToLeaderboard(String name, int winnings) {
		this.map.put(name, winnings);
	}

}
