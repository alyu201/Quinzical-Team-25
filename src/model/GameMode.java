package model;

/**
 * GameMode contains the types of mode the game can have and allows the game 
 * to keep track of the state it is in and change game flow depending on the 
 * game state.
 */
public class GameMode {
	public enum GameType {
		GAMESMODULE,
		PRACTICEMODULE;
	}
}
