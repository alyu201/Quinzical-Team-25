package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import model.MainModel;

/**
 * TTSQuestionThread is a specialized thread to say the currently selected
 * question asyncronously
 */
public class TTSQuestionThread extends Thread {

	private MainModel model;
	private Process process;

	public TTSQuestionThread() {
		super();
		this.model = MainModel.getMainModel();
	}

	/**
	 * Use the program festival to speak the currently selected question. Voice is
	 * adjusted to the users settings.
	 */
	@Override
	public void run() {
		try {
			String command = "festival";
			ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
			this.process = pb.start();
			BufferedWriter stdin = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
			BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			// TODO: Voice volume
			stdin.write("(Parameter.set 'Duration_Stretch " + (1.0 - (new Double(model.getSettings().getSpeed()) / 100))
					+ ")");
			stdin.write("(SayText \"" + model.getCurrentQuestion().getQuestion() + "\")");
			stdin.flush();

			stdin.close();
			stdout.close();
			stderr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Kill saying a question mid sentence
	 */
	public void killVoice() {
		if (this.process != null) {
			process.destroy();
		}
	}
}
