package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import dataUtil.FactoryUtilsFX;
import dataUtil.FileUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Exam;
import model.Exam.Status;
import model.PassedSubject;
import model.Payment;
import model.Student;
import utils.CheckLogIn;

public class OdjaviIspitController implements Initializable{
	@FXML
	private TextArea text;
	@FXML
	private TextField textInput;
	@FXML
	private Button odjavi;
	
	private void showMessage() throws IOException{
		final Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);

		window.setTitle("Obavještenje");
		window.setMinWidth(250);
		window.setMinHeight(100);
		
		window.setScene(new Scene(FXMLLoader.load(getClass().getResource("MessageOdjaviIspit.fxml"))));
		window.showAndWait();
	}
	
	@FXML
	private void showStudentMenu(ActionEvent event) throws IOException{
		Parent studentMenu = FXMLLoader.load(getClass().getResource("StudentMenu.fxml"));
		Scene scene = new Scene(studentMenu);
		Stage studentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		studentStage.setScene(scene);
		studentStage.show();
	}
	
	@FXML
	private void search(ActionEvent event) throws IOException{
		Student logedInStudent = CheckLogIn.getStudentLogedIn();
		ArrayList<Exam> exams = logedInStudent.getExamEntry();
		ArrayList<PassedSubject> passedSubjects = logedInStudent.getPassedSubjects();
		ArrayList<Payment> payments = App.getPayments();
		Date date = new Date();
		@SuppressWarnings("deprecation")
		Date dateTemp = new Date(date.getYear(), date.getMonth(), date.getDate()+1);
		int i = 0;
		for (Exam e : exams){ 
			boolean x = false;
			if(e == null)
				continue;
			for (PassedSubject ps : passedSubjects) {
				if (ps == null)
					continue;
				if(ps.getSubject().equals(e.getSubject()))
					x = true;
			}
			if(x)
				continue;
			if(e.getDate().after(dateTemp)){
				i++;
			}
		}
		if (i == 0){
			return;
		}
		int examOption = FactoryUtilsFX.checkInputInt(textInput.getText().trim());
		if(examOption <1 || examOption >i){
			textInput.clear();
			showErrorOdjavi();
			return;
		}
		i = 0;
		for (Exam e1 : exams){
			if(e1 == null)
				continue;
			i++;
			if(i == examOption){
				Exam selectedExam = e1;
				if(selectedExam.getStatus().equals(Status.REGULAR))
					logedInStudent.setAccount(logedInStudent.getAccount() + 200);
				if(selectedExam.getStatus().equals(Status.NOT_REGULAR))
					logedInStudent.setAccount(logedInStudent.getAccount() + 400);
				logedInStudent.getExamEntry().remove(selectedExam);
				for(Payment p : payments){
					if(p == null)
						continue;
					if(p.getSubject().getName().equals(selectedExam.getSubject().getName()) && p.getIndex() == logedInStudent.getIndex()){
						payments.remove(p);
						FileUtils.writeToFile(payments, "payments");
						break;
					}
				}
				
				ArrayList<Student> students = App.getStudents();
				FileUtils.writeToFile(students, "students");
				showMessage();
				showStudentMenu(event);
				break;
			}
		}
	}
	
	private void showErrorOdjavi() throws IOException{
		final Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);

		window.setTitle("Obavještenje");
		window.setMinWidth(250);
		window.setMinHeight(100);
		
		window.setScene(new Scene(FXMLLoader.load(getClass().getResource("MessageErrorPrijavi.fxml"))));
		window.showAndWait();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Student logedInStudent = CheckLogIn.getStudentLogedIn();
		ArrayList<Exam> exams = logedInStudent.getExamEntry();
		ArrayList<PassedSubject> passedSubjects = logedInStudent.getPassedSubjects();
		Date date = new Date();
		@SuppressWarnings("deprecation")
		Date dateTemp = new Date(date.getYear(), date.getMonth(), date.getDate()+1);
		int i = 0;
		for (Exam e : exams){ 
			boolean x = false;
			if(e == null)
				continue;
			for (PassedSubject ps : passedSubjects) {
				if (ps == null)
					continue;
				if(ps.getSubject().equals(e.getSubject()))
					x = true;
			}
			if(x)
				continue;
			if(e.getDate().after(dateTemp)){
				i++;
				text.appendText(Integer.toString(i) + ". " + e.getSubject().getName() + "\n");
			}
		}
		if (i == 0){
			text.setText("Nema ispita za odjavljivanje.");
			textInput.setDisable(true);
			odjavi.setDisable(true);
		}
	}
	
}
