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

public class PrijaviIspitController implements Initializable{
	@FXML
	private TextArea text;
	@FXML
	private TextField textInput;
	@FXML
	private Button prijavi;
	
	@FXML
	private void search(ActionEvent event) throws IOException{
		ArrayList<Exam> exams = App.getExams();
		Student logedInStudent = CheckLogIn.getStudentLogedIn();
		ArrayList<PassedSubject> passedSubjects = logedInStudent.getPassedSubjects();
		Date date = new Date();
		@SuppressWarnings("deprecation")
		Date dateTemp = new Date(date.getYear(), date.getMonth(), date.getDate()+1);
		@SuppressWarnings("deprecation")
		Date datePayment = new Date(date.getYear(), date.getMonth(), date.getDate());
		int i = 0;
		for (Exam e : exams){ 
			boolean ind = false;
			if (e.getSubject().getSchoolYear() == logedInStudent.getSchoolYear() &&
					e.getSubject().getDepartment().equals(logedInStudent.getDepartment()) &&
					e.getDate().after(dateTemp)){
				for (PassedSubject ps : passedSubjects) {
					if (ps == null)
						continue;
					if(ps.getSubject().equals(e.getSubject()))
						ind = true;
				}
				for (Exam e2 : logedInStudent.getExamEntry()) {
					if (e2 == null)
						continue;
					if(e2.getSubject().equals(e.getSubject()))
						ind = true;
				}
				if(ind)
					continue;
				i++;
			}
		}
		if (i == 0){
			return;
		}

		int examOption = FactoryUtilsFX.checkInputInt(textInput.getText().trim());
		if(examOption <1 || examOption >i){
			textInput.clear();
			showErrorPrijavi();
			return;
		}
		i = 0;
		for (Exam e1 : exams){
			boolean ind = false;
			if (e1.getSubject().getSchoolYear() == logedInStudent.getSchoolYear() &&
					e1.getSubject().getDepartment().equals(logedInStudent.getDepartment()) &&
					e1.getDate().after(dateTemp)){
				for (PassedSubject ps : passedSubjects) {
					if (ps == null)
						continue;
					if(ps.getSubject().equals(e1.getSubject()))
						ind = true;
				}
				for (Exam e2 : logedInStudent.getExamEntry()) {
					if (e2 == null)
						continue;
					if(e2.getSubject().equals(e1.getSubject()))
						ind = true;
				}
				if(ind)
					continue;
				i++;
				if(i == examOption){
					Exam selectedExam = e1;
					if (selectedExam.getStatus().equals(Status.REGULAR)){
						if(logedInStudent.getAccount() == 200 || logedInStudent.getAccount() > 200){
							logedInStudent.setAccount(logedInStudent.getAccount() - 200);
							logedInStudent.getExamEntry().add(selectedExam);
							ArrayList<Student> students = App.getStudents();
							FileUtils.writeToFile(students, "students");
							ArrayList<Payment> payments = App.getPayments();
							payments.add(new Payment(200, logedInStudent.getIndex(), datePayment, selectedExam.getSubject()));
							FileUtils.writeToFile(payments, "payments");
							showMessage();
							showStudentMenu(event);
						}
						else {
							showMessageError();
						}
					}
					else {
						if(logedInStudent.getAccount() == 400 || logedInStudent.getAccount() > 400){
							logedInStudent.setAccount(logedInStudent.getAccount() - 400);
							logedInStudent.getExamEntry().add(selectedExam);
							ArrayList<Student> students = App.getStudents();
							FileUtils.writeToFile(students, "students");
							ArrayList<Payment> payments = App.getPayments();
							payments.add(new Payment(400, logedInStudent.getIndex(), datePayment, selectedExam.getSubject()));
							FileUtils.writeToFile(payments, "payments");
							showMessage();
							showStudentMenu(event);
						}
						else {
							showMessageError();
						}
					}
				}
			}
		}
	}
	
	private void showErrorPrijavi() throws IOException{
		final Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);

		window.setTitle("Obavještenje");
		window.setMinWidth(250);
		window.setMinHeight(100);
		
		window.setScene(new Scene(FXMLLoader.load(getClass().getResource("MessageErrorPrijavi.fxml"))));
		window.showAndWait();
	}
	
	private void showMessage() throws IOException{
		final Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);

		window.setTitle("Obavještenje");
		window.setMinWidth(250);
		window.setMinHeight(100);
		
		window.setScene(new Scene(FXMLLoader.load(getClass().getResource("MessagePrijaviIspit.fxml"))));
		window.showAndWait();
	}
	
	private void showMessageError() throws IOException{
		final Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);

		window.setTitle("Obavještenje");
		window.setMinWidth(250);
		window.setMinHeight(100);
		
		window.setScene(new Scene(FXMLLoader.load(getClass().getResource("MessagePrijaviIspitGreska.fxml"))));
		window.showAndWait();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Exam> exams = App.getExams();
		Student logedInStudent = CheckLogIn.getStudentLogedIn();
		ArrayList<PassedSubject> passedSubjects = logedInStudent.getPassedSubjects();
		Date date = new Date();
		@SuppressWarnings("deprecation")
		Date dateTemp = new Date(date.getYear(), date.getMonth(), date.getDate()+1);
		int i = 0;
		for (Exam e : exams){ 
			boolean ind = false;
			if (e.getSubject().getSchoolYear() == logedInStudent.getSchoolYear() &&
					e.getSubject().getDepartment().equals(logedInStudent.getDepartment()) &&
					e.getDate().after(dateTemp)){
				for (PassedSubject ps : passedSubjects) {
					if (ps == null)
						continue;
					if(ps.getSubject().equals(e.getSubject()))
						ind = true;
				}
				for (Exam e2 : logedInStudent.getExamEntry()) {
					if (e2 == null)
						continue;
					if(e2.getSubject().equals(e.getSubject()))
						ind = true;
				}
				if(ind)
					continue;
				i++;
				text.appendText(Integer.toString(i) + ". " + e.getSubject().getName() + "\n");
			}
		}
		if (i == 0){
			text.setText("Nema ispita za prijavljivanje.");
			textInput.setDisable(true);
			prijavi.setDisable(true);
		}
	}
	
	@FXML
	private void showStudentMenu(ActionEvent event) throws IOException{
		Parent studentMenu = FXMLLoader.load(getClass().getResource("StudentMenu.fxml"));
		Scene scene = new Scene(studentMenu);
		Stage studentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		studentStage.setScene(scene);
		studentStage.show();
	}
}
