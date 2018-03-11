package application;

import java.io.IOException;
import java.util.ArrayList;

import dataUtil.FactoryUtilsFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.PassedSubject;
import model.Subject;
import utils.SearchUtils;

public class PassedExamsController {
	private static ArrayList<PassedSubject> passedSubjectsTemp = new ArrayList<PassedSubject>();
	
	@FXML
	private TextField name;
	@FXML
	private TextField gradeInput;
	
	@FXML
	private void save(ActionEvent event) throws IOException{
		Subject subject = SearchUtils.findSubjectByName(FactoryUtilsFX.checkInputString(name.getText().trim()));
		if(subject == null){
			name.clear();
		}
		int grade = FactoryUtilsFX.checkInputInt(gradeInput.getText().trim());
		if(grade == 0 || grade < 6 || grade > 10){
			gradeInput.clear();
		}
		
		if(subject == null || grade == 0 || grade < 6 || grade > 10){
			showError();
			return;
		}
		
		PassedSubject passedSubject = new PassedSubject(grade, subject);
		passedSubjectsTemp.add(passedSubject);
		
		Stage stage = (Stage) name.getScene().getWindow();
	    stage.close();
		
	}
	
	private void showError() throws IOException{
		final Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);

		window.setTitle("Obavještenje");
		window.setMinWidth(250);
		window.setMinHeight(100);
		
		window.setScene(new Scene(FXMLLoader.load(getClass().getResource("MessageError.fxml"))));
		window.showAndWait();
	}

	public static ArrayList<PassedSubject> getPassedSubjectsTemp() {
		return passedSubjectsTemp;
	}

	public static void setPassedSubjectsTemp(ArrayList<PassedSubject> passedSubjectsTemp) {
		PassedExamsController.passedSubjectsTemp = passedSubjectsTemp;
	}

	
}
