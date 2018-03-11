package application;

import java.io.IOException;
import java.util.Date;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.CheckLogIn;

public class Controller {
	@SuppressWarnings("deprecation")
	public static Date pocetakIspitnogRoka = new Date(117, 05, 05); //117 je 2017. godina, a mjesec je ustvari +1
	@SuppressWarnings("deprecation")
	public static Date krajIspitnogRoka = new Date(117, 05, 30); //117 je 2017. godina, a mjesec je ustvari +1
	
	@FXML
	private void checkLogIn(ActionEvent event) throws IOException{
		String korisnickoIme;
		String lozinka;
		
		korisnickoIme = usernameLogIn.getText().trim();
		lozinka = passwordLogIn.getText().trim();
		
		if (CheckLogIn.checkLogIn(korisnickoIme, lozinka)){
			usernameLogIn.clear();
			passwordLogIn.clear();
			warning.setVisible(true);
			return;
		}
		
		if (CheckLogIn.getAdminLogedIn() != null){
			showAdminMenu(event);
		}
		
		if (CheckLogIn.getStudentLogedIn() != null){
			showStudentMenu(event);
		}
		
		if (CheckLogIn.getProfessorLogedIn() != null){
			showProfessorMenu(event);
		}
	}
	
	@FXML
	private void showAdminMenu(ActionEvent event) throws IOException{
		Parent adminMenu = FXMLLoader.load(getClass().getResource("AdminMenu.fxml"));
		Scene scene = new Scene(adminMenu);
		Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		adminStage.setScene(scene);
		adminStage.setTitle("Dobro došli, " + CheckLogIn.getAdminLogedIn().getFirstName().toUpperCase() + " " +
				CheckLogIn.getAdminLogedIn().getLastName().toUpperCase());
		adminStage.show();
	}
	
	@FXML
	private void showStudentMenu(ActionEvent event) throws IOException{
		Parent studentMenu = FXMLLoader.load(getClass().getResource("StudentMenu.fxml"));
		Scene scene = new Scene(studentMenu);
		Stage studentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		studentStage.setScene(scene);
		studentStage.setTitle("Dobro došli, " + CheckLogIn.getStudentLogedIn().getFirstName().toUpperCase() + " " +
				CheckLogIn.getStudentLogedIn().getLastName().toUpperCase());
		studentStage.show();
	}
	
	@FXML
	private void showProfessorMenu(ActionEvent event) throws IOException{
		Parent professorMenu = FXMLLoader.load(getClass().getResource("ProfessorMenu.fxml"));
		Scene scene = new Scene(professorMenu);
		Stage professorStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		professorStage.setScene(scene);
		professorStage.setTitle("Dobro došli, " + CheckLogIn.getProfessorLogedIn().getFirstName().toUpperCase() + " " +
				CheckLogIn.getProfessorLogedIn().getLastName().toUpperCase());
		professorStage.show();
	} 
	
	@FXML
	private void exit(){
		Platform.exit();
	}
	
	@FXML 
	private TextField usernameLogIn;
	@FXML
	private TextField passwordLogIn;
	@FXML 
	private Text warning;
	
	@FXML 
	private void newProfessor(ActionEvent event) throws IOException{
		Parent newProfessor = FXMLLoader.load(getClass().getResource("NewProfessor.fxml"));
		Scene scene = new Scene(newProfessor);
		Stage newProfessorStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		newProfessorStage.setScene(scene);
		newProfessorStage.show();
	}
	
	@FXML
	private void newSubject(ActionEvent event) throws IOException{
		if(App.getProfessors().isEmpty()){
			showMessage();
			showAdminMenu(event);
			return;
		}
		Parent newSubject = FXMLLoader.load(getClass().getResource("NewSubject.fxml"));
		Scene scene = new Scene(newSubject);
		Stage newSubjectStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		newSubjectStage.setScene(scene);
		newSubjectStage.show();
	}
	
	@FXML 
	private void newStudent(ActionEvent event) throws IOException{
		Parent newStudent = FXMLLoader.load(getClass().getResource("NewStudent.fxml"));
		Scene scene = new Scene(newStudent);
		Stage newStudentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		newStudentStage.setScene(scene);
		newStudentStage.show();
	}
	
	@FXML 
	private void payments(ActionEvent event) throws IOException{
		Parent p = FXMLLoader.load(getClass().getResource("Payments.fxml"));
		Scene scene = new Scene(p);
		Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
		s.setScene(scene);
		s.show();
	}
	
	@FXML 
	private void sortStudents(ActionEvent event) throws IOException{
		Parent p = FXMLLoader.load(getClass().getResource("SortStudents.fxml"));
		Scene scene = new Scene(p);
		Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
		s.setScene(scene);
		s.show();
	}
	
	@FXML 
	private void examsEntryForProfessor(ActionEvent event) throws IOException{
		Parent p = FXMLLoader.load(getClass().getResource("ExamsEntryForProfessor.fxml"));
		Scene scene = new Scene(p);
		Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
		s.setScene(scene);
		s.show();
	}
	
	@FXML 
	private void findStudentByIndex(ActionEvent event) throws IOException{
		Parent p = FXMLLoader.load(getClass().getResource("FindStudentByIndex.fxml"));
		Scene scene = new Scene(p);
		Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
		s.setScene(scene);
		s.show();
	}
	
	@FXML 
	private void odjaviIspit(ActionEvent event) throws IOException{
		Parent p = FXMLLoader.load(getClass().getResource("OdjaviIspit.fxml"));
		Scene scene = new Scene(p);
		Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
		s.setScene(scene);
		s.show();
	}
	
	@FXML 
	private void mark(ActionEvent event) throws IOException{
		Parent p = FXMLLoader.load(getClass().getResource("Marks.fxml"));
		Scene scene = new Scene(p);
		Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
		s.setScene(scene);
		s.show();
	}
	
	@FXML 
	private void findStudentsByLastName(ActionEvent event) throws IOException{
		Parent p = FXMLLoader.load(getClass().getResource("FindStudentsByLastName.fxml"));
		Scene scene = new Scene(p);
		Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
		s.setScene(scene);
		s.show();
	}
	
	@FXML 
	private void prijaviIspit(ActionEvent event) throws IOException{
		Parent p = FXMLLoader.load(getClass().getResource("PrijaviIspit.fxml"));
		Scene scene = new Scene(p);
		Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
		s.setScene(scene);
		s.show();
	}
	
	@FXML 
	private void examsEntry(ActionEvent event) throws IOException{
		Parent p = FXMLLoader.load(getClass().getResource("ExamsEntry.fxml"));
		Scene scene = new Scene(p);
		Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
		s.setScene(scene);
		s.show();
	}
	
	@FXML 
	private void passedSubjects(ActionEvent event) throws IOException{
		Parent p = FXMLLoader.load(getClass().getResource("PassedSubjects.fxml"));
		Scene scene = new Scene(p);
		Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
		s.setScene(scene);
		s.show();
	}
	
	@FXML 
	private void newExam(ActionEvent event) throws IOException{
		Date dateNow = new Date();
		if(dateNow.before(pocetakIspitnogRoka) || dateNow.after(krajIspitnogRoka)){
			showMessageExam();
			showAdminMenu(event);
			return;
		}
		Parent newExam = FXMLLoader.load(getClass().getResource("NewExam.fxml"));
		Scene scene = new Scene(newExam);
		Stage newExamStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		newExamStage.setScene(scene);
		newExamStage.show();
	}
	
	private void showMessage() throws IOException{
		final Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);

		window.setTitle("Obavještenje");
		window.setMinWidth(250);
		window.setMinHeight(100);
		
		window.setScene(new Scene(FXMLLoader.load(getClass().getResource("MessageNewSubjectError.fxml"))));
		window.showAndWait();
	}
	
	private void showMessageExam() throws IOException{
		final Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);

		window.setTitle("Obavještenje");
		window.setMinWidth(250);
		window.setMinHeight(100);
		
		window.setScene(new Scene(FXMLLoader.load(getClass().getResource("MessageNewExamError.fxml"))));
		window.showAndWait();
	}
}
