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
import model.PassedSubject;
import model.Payment;
import model.Professor;
import model.Student;
import utils.CheckLogIn;
import utils.SearchUtils;

public class MarksController implements Initializable{
	@SuppressWarnings("deprecation")
	public static Date pocetakIspitnogRoka = new Date(117, 05, 01); //117 je 2017. godina, a mjesec je ustvari +1
	@SuppressWarnings("deprecation")
	public static Date krajIspitnogRoka = new Date(117, 05, 30); //117 je 2017. godina, a mjesec je ustvari +1
	
	@FXML
	private TextArea text;
	@FXML
	private TextField textInput;
	@FXML
	private TextField markInput;
	@FXML
	private Button mark;
	
	@FXML
	private void mark(ActionEvent event) throws IOException{
		Professor logedInProfessor = CheckLogIn.getProfessorLogedIn();
		ArrayList<Payment> payments = App.getPayments();
		Date dateNow = new Date();
		int i = 0;
		for (Payment p : payments){
			if (p == null)
				continue;
			if (p.getSubject().getProfessor().getJmbg().equals(logedInProfessor.getJmbg()) &&
					SearchUtils.findStudentByIndex(p.getIndex()).getExamEntry().contains(SearchUtils.findExamBySubject(p.getSubject().getName()))){
				boolean x = false;
				for (PassedSubject ps : SearchUtils.findStudentByIndex(p.getIndex()).getPassedSubjects()){
					if(ps == null)
						continue;
					if(ps.getSubject().equals(p.getSubject()))
						x = true;
				}
				for (Exam e : SearchUtils.findStudentByIndex(p.getIndex()).getExamEntry()){
					if(e == null)
						continue;
					if((dateNow.after(krajIspitnogRoka) && dateNow.after(e.getDate())) ||
							(dateNow.before(pocetakIspitnogRoka) && dateNow.before(e.getDate())) ||
							dateNow.before(e.getDate()))
						x = true;
				}
				if(x)
					continue;
				i++;
			}
		}
		if(i == 0){
			return;
		}
		
		boolean ind = false;
		int examOption = FactoryUtilsFX.checkInputInt(textInput.getText().trim());
		if(examOption <1 || examOption >i){
			ind = true;
			textInput.clear();
		}
		int gradeInput = FactoryUtilsFX.checkInputInt(markInput.getText().trim());
		if(gradeInput < 6 || gradeInput > 10){
			ind = true;
			markInput.clear();
		}
		if(ind == true){
			showError();
			return;
		}
		i = 0;
		for (Payment p : payments){
			if (p == null)
				continue;
			if (p.getSubject().getProfessor().getJmbg().equals(logedInProfessor.getJmbg()) &&
					SearchUtils.findStudentByIndex(p.getIndex()).getExamEntry().contains(SearchUtils.findExamBySubject(p.getSubject().getName()))){
				boolean x = false;
				for (PassedSubject ps : SearchUtils.findStudentByIndex(p.getIndex()).getPassedSubjects()){
					if(ps == null)
						continue;
					if(ps.getSubject().equals(p.getSubject()))
						x = true;
				}
				for (Exam e : SearchUtils.findStudentByIndex(p.getIndex()).getExamEntry()){
					if(e == null)
						continue;
					if((dateNow.after(krajIspitnogRoka) && dateNow.after(e.getDate())) ||
							(dateNow.before(pocetakIspitnogRoka) && dateNow.before(e.getDate())) ||
							dateNow.before(e.getDate()))
						x = true;
				}
				if(x)
					continue;
				i++;
				if(i == examOption){
					int grade = FactoryUtilsFX.checkInputInt(markInput.getText().trim());
					Student s = SearchUtils.findStudentByIndex(p.getIndex());
					PassedSubject ps = new PassedSubject(grade, p.getSubject());
					s.getPassedSubjects().add(ps);
					double gpa = 0;
					double sum = 0;
					for (PassedSubject ps1 : s.getPassedSubjects()){
						if(ps1 == null)
							continue;
						sum += ps1.getGrade();
					}
					gpa = sum / (s.getPassedSubjects().size()-1);
					s.setGpa(gpa);
					ArrayList<Student> students = App.getStudents();
					FileUtils.writeToFile(students, "students");
					showMessage();
					showProfessorMenu(event);
				}
			}
		}
	}
	
	private void showError() throws IOException{
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
		
		window.setScene(new Scene(FXMLLoader.load(getClass().getResource("MessageMark.fxml"))));
		window.showAndWait();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Professor logedInProfessor = CheckLogIn.getProfessorLogedIn();
		ArrayList<Payment> payments = App.getPayments();
		Date dateNow = new Date();
		int i = 0;
		for (Payment p : payments){
			if (p == null)
				continue;
			if (p.getSubject().getProfessor().getJmbg().equals(logedInProfessor.getJmbg()) &&
					SearchUtils.findStudentByIndex(p.getIndex()).getExamEntry().contains(SearchUtils.findExamBySubject(p.getSubject().getName()))){
				boolean x = false;
				for (PassedSubject ps : SearchUtils.findStudentByIndex(p.getIndex()).getPassedSubjects()){
					if(ps == null)
						continue;
					if(ps.getSubject().equals(p.getSubject()))
						x = true;
				}
				for (Exam e : SearchUtils.findStudentByIndex(p.getIndex()).getExamEntry()){
					if(e == null)
						continue;
					if((dateNow.after(krajIspitnogRoka) && dateNow.after(e.getDate())) ||
							(dateNow.before(pocetakIspitnogRoka) && dateNow.before(e.getDate())) ||
							dateNow.before(e.getDate()))
						x = true;
				}
				if(x)
					continue;
				i++;
				text.appendText(Integer.toString(i) + ". " + p.getSubject().getName() + " (" + 
				(SearchUtils.findStudentByIndex(p.getIndex())).getFirstName() + " " + 
						(SearchUtils.findStudentByIndex(p.getIndex())).getLastName() + " " + Integer.toString(p.getIndex()) + ")\n");
			}
		}
		if(i == 0){
			text.setText("Nema prijavljenih ispita.");
			textInput.setDisable(true);
			mark.setDisable(true);
			markInput.setDisable(true);
		}
	}

	@FXML
	private void showProfessorMenu(ActionEvent event) throws IOException{
		Parent professorMenu = FXMLLoader.load(getClass().getResource("ProfessorMenu.fxml"));
		Scene scene = new Scene(professorMenu);
		Stage professorStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		professorStage.setScene(scene);
		professorStage.show();
	} 
}
