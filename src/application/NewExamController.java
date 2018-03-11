package application;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import dataUtil.FactoryUtilsFX;
import dataUtil.FileUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Exam;
import model.Exam.ExamType;
import model.Exam.Status;
import model.Subject;
import utils.SearchUtils;

public class NewExamController implements Initializable {
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
	ObservableList<String> statusExamList = FXCollections.observableArrayList("Redovan", "Vanredni");
	ObservableList<String> examTypeList = FXCollections.observableArrayList("Usmeno", "Pismeno", "Praktièno");
	
	@FXML
	private TextField classroomExam;
	@FXML
	private TextField subjectExam;
	@FXML
	private TextField dateExam;
	@FXML 
	private ComboBox<String> typeExam;
	@FXML 
	private ComboBox<String> statusExam;
	
	@FXML
	private void saveNewExam(ActionEvent event) throws IOException{
		ExamType type = null;
		Status status = null;
		String classroom;
		Subject subject = null;
		Date date = null;
		
		int id = App.getExams().size() + 1;
		
		String typeOption = typeExam.getValue();
		if (typeOption == "Usmeno") {
			type = ExamType.ORAL_EXAM;
		}
		else if (typeOption == "Pismeno") {
			type = ExamType.TEST;
		}
		else {
			type = ExamType.PRACTISE;
		}
		
		String statusOption = statusExam.getValue();
		if (statusOption == "Redovan") {
			status = Status.REGULAR;
		}
		else {
			status = Status.NOT_REGULAR;
		}
		
		classroom = FactoryUtilsFX.checkInputString(classroomExam.getText().trim());
		if(classroom == null) classroomExam.clear();
		
		String subjectString = FactoryUtilsFX.checkInputString(subjectExam.getText().trim());
		if(subjectString != null)
			subject = SearchUtils.findSubjectByName(subjectString);
		if(subject == null) subjectExam.clear();
		
		String dateString = FactoryUtilsFX.checkInputString(dateExam.getText().trim());
		if(dateString != null){
			try {
				date = sdf.parse(dateString);
			} catch (ParseException e) {
				dateExam.clear();
			}
		}
		
		if(type == null || status == null || date == null || subject == null
				|| classroom == null){
			showError();
			return;
		}
		
		ArrayList<Exam> exams = App.getExams();
		exams.add(new Exam(id, type, status, date, classroom, subject));
		FileUtils.writeToFile(exams, "exams");
		showMessageNewExam();
		showProfessorMenu(event);
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

	private void showMessageNewExam() throws IOException{
		final Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);

		window.setTitle("Obavještenje");
		window.setMinWidth(250);
		window.setMinHeight(100);
		
		window.setScene(new Scene(FXMLLoader.load(getClass().getResource("MessageNewExam.fxml"))));
		window.showAndWait();
	}
	
	@FXML
	private void showProfessorMenu(ActionEvent event) throws IOException{
		Parent professorMenu = FXMLLoader.load(getClass().getResource("ProfessorMenu.fxml"));
		Scene scene = new Scene(professorMenu);
		Stage professorStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		professorStage.setScene(scene);
		professorStage.show();
	} 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		typeExam.setValue("Usmeno");
		typeExam.setItems(examTypeList);
		statusExam.setValue("Redovan");
		statusExam.setItems(statusExamList);
	}
	
}
