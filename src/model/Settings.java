package model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Settings implements JSONable {
	public static String SETTINGS_FILENAME = "./settings.json";
	private String voiceType;
	private int speed;
	private int volume;

	Settings() {
		this.fromJSONFile();
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

	@Override
	public void fromJSONFile() {
		JSONParser parser = new JSONParser();

		try (Reader reader = new FileReader(SETTINGS_FILENAME)) {

			JSONObject obj = (JSONObject) parser.parse(reader);
			this.setVoiceType((String) obj.get("voiceType"));
			this.setSpeed((Math.toIntExact(((Long) obj.get("speed")))));
			this.setVolume((Math.toIntExact(((Long) obj.get("volume")))));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	// Breaks type safety
	@SuppressWarnings("unchecked")
	@Override
	public void toJSONFile() {
		JSONObject obj = new JSONObject();
		obj.put("voiceType", this.getVoiceType());
		obj.put("speed", this.getSpeed());
		obj.put("volume", this.getVolume());
		try (FileWriter file = new FileWriter(SETTINGS_FILENAME)) {
			file.write(obj.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
