package model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Settings contains all current settings values of the game such as volume, 
 * voice type, and voice speed and allows these values to be changed and obtained
 */
public class Settings {
	private String voiceType;
	private int speed;
	private int volume;

	public Settings(String voiceType, int speed, int volume) {
		this.voiceType = voiceType;
		this.speed = speed;
		this.volume = volume;
	}

	public String getVoiceType() {
		return voiceType;
	}

	public void setVoiceType(String voiceType) {
		this.voiceType = voiceType;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}
}