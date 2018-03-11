package application;

import java.io.IOException;
import java.util.ArrayList;

import dataUtil.FactoryUtilsFX;
import dataUtil.FileUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Professor;
import model.Subject;
import utils.SearchUtils;

public class NewSubjectController {
	@FXML
	private TextField nameSubject;
	@FXML
	private TextField departmentSubject;
	@FXML
	private TextField weeklySubject;
	@FXML
	private TextField professorID;
	@FXML
	private TextField schoolYearSubject;
	
	@FXML
	private void saveNewProfessor(ActionEvent event) throws IOException{
		String name = null;
		int weekly;
		Professor professor = null;
		String department = null;
		int schoolYear;
		
		name = FactoryUtilsFX.checkInputString(nameSubject.getText().trim());
		if(name == null) nameSubject.clear();
		
		department = FactoryUtilsFX.checkInput(departmentSubject.getText().trim());
		if(department == null) departmentSubject.clear();
		
		weekly = FactoryUtilsFX.checkInputInt(weeklySubject.getText().trim());
		if(weekly == 0) weeklySubject.clear();
		
		int professorIDInt = FactoryUtilsFX.checkInputInt(professorID.getText().trim());
		professor = SearchUtils.findProfessorById(professorIDInt);
		if(professor == null) professorID.clear();
		
		schoolYear = FactoryUtilsFX.checkInputInt(schoolYearSubject.getText().trim());
		if(schoolYear == 0) schoolYearSubject.clear();
		
		if(name == null || department == null || weekly == 0 || professor == null
				|| schoolYear == 0){
			showError();
			return;
		}
		ArrayList<Subject> subjects = App.getSubjects();
		subjects.add(new Subject(name, department, weekly, professor, schoolYear));
		FileUtils.writeToFile(subjects, "subjects");
		showMessageNewSubject();
		showAdminMenu(event);
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
	
	private void showMessageNewSubject() throws IOException{
		final Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);

		window.setTitle("Obavještenje");
		window.setMinWidth(250);
		window.setMinHeight(100);
		
		window.setScene(new Scene(FXMLLoader.load(getClass().getResource("MessageNewSubject.fxml"))));
		window.showAndWait();
	}
	
	@FXML
	private void showAdminMenu(ActionEvent event) throws IOException{
		Parent adminMenu = FXMLLoader.load(getClass().getResource("AdminMenu.fxml"));
		Scene scene = new Scene(adminMenu);
		Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		adminStage.setScene(scene);
		adminStage.show();
	}
}
