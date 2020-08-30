package controller;

import model.JepordayTuple;

public class QuestionController {

	JepordayTuple model;
	
	@FXML
	public void initialize() {
		
	}

	QuestionController(JepordayTuple model) {
		this.model = model;
	}
	
	public void setQuestion(JepordayTuple question) {
		this.model = question;
	}
}
