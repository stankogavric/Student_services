package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.PassedSubject;
import model.Student;
import utils.CheckLogIn;

public class PassedSubjectsController implements Initializable{
	@FXML
	private TextArea text;
	@FXML
	private Text textGpa;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Student logedInStudent = CheckLogIn.getStudentLogedIn();
		ArrayList<PassedSubject> passedSubjects = logedInStudent.getPassedSubjects();
		
		int i = 0;
		double gpa = 0;
		double sum = 0;
		for (PassedSubject ps : passedSubjects){
			if(ps == null)
				continue;
			i++;
			text.appendText(Integer.toString(i) + ". " + ps.toString() + "\n");
			sum += ps.getGrade();
		}
		if (i == 0){
			text.setText("Nema položenih ispita.");
			textGpa.setText(Double.toString(gpa));
		}
		else {
			gpa = sum / passedSubjects.size();
			textGpa.setText(Double.toString(gpa));
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
