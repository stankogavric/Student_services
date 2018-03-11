package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.Exam;
import model.PassedSubject;
import model.Student;
import utils.CheckLogIn;

public class ExamsEntryController implements Initializable{
	@SuppressWarnings("deprecation")
	public static Date pocetakIspitnogRoka = new Date(117, 05, 01); //117 je 2017. godina, a mjesec je ustvari +1
	@SuppressWarnings("deprecation")
	public static Date krajIspitnogRoka = new Date(117, 05, 30); //117 je 2017. godina, a mjesec je ustvari +1
	
	@FXML
	private TextArea text;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Student logedInStudent = CheckLogIn.getStudentLogedIn();
		ArrayList<Exam> examEntry = logedInStudent.getExamEntry();
		ArrayList<PassedSubject> passedSubjects = logedInStudent.getPassedSubjects();
		int i = 0;
		Date dateNow = new Date();
		for (Exam e : examEntry){
			boolean ind = true;
			boolean x = false;
			if(e == null)
				continue;
			if((dateNow.after(krajIspitnogRoka) && dateNow.after(e.getDate())) ||
					(dateNow.before(pocetakIspitnogRoka) && dateNow.before(e.getDate())))
				x = true;
			if(x)
				continue;
			i++;
			for (PassedSubject ps : passedSubjects){
				if (ps == null)
					continue;
				if(ps.getSubject().equals(e.getSubject())){
					text.appendText(Integer.toString(i) + ". " + e.getSubject().getName() + " (Ocjena: " + Integer.toString(ps.getGrade()) + ")\n");
					ind = false;
					break;
				}
			}
			if(ind)
				text.appendText(Integer.toString(i) + ". " + e.getSubject().getName() + "\n");
		}
		if (i == 0){
			text.setText("Nema prijavljenih ispita.");
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
