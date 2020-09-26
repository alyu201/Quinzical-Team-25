package model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Settings {
	public static String SETTINGS_FILENAME = "./settings.json";
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

	public Settings fromJSONString(String xs) {
			try {
				JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject) parser.parse(xs);
				return new Settings(
					(String) obj.get("voiceType"),
					Math.toIntExact(((Long) obj.get("speed"))),
					Math.toIntExact(((Long) obj.get("volume")))
					);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
	}

	public String toJSONString() {
		JSONObject obj = new JSONObject();
		obj.put("voiceType", this.getVoiceType());
		obj.put("speed", this.getSpeed());
		obj.put("volume", this.getVolume());
		return obj.toJSONString();
	}

	public Settings fromJSONFile() {
		JSONParser parser = new JSONParser();
		try (Reader reader = new FileReader(SETTINGS_FILENAME)) {
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
		try (FileWriter file = new FileWriter(SETTINGS_FILENAME)) {
			file.write(this.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
