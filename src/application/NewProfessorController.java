package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import model.Professor;
import model.Professor.ProfessorStatus;

public class NewProfessorController implements Initializable {
	ObservableList<String> statusProfList = FXCollections.observableArrayList("Docent", "Vanredni profesor", "Redovni profesor");
	
	@FXML
	private void saveNewProfessor(ActionEvent event) throws IOException{
		String jmbg = null;
		String firstName = null;
		String lastName = null;
		String username = null;
		String password = null;
		String department = null;
		ProfessorStatus status = null;
		
		int id = App.getProfessors().size() + 1;
		
		jmbg = jmbgProf.getText().trim();
		if (!jmbg.matches("[0-9]{13}"))
			jmbg = null;
			jmbgProf.clear();
		
		firstName = FactoryUtilsFX.checkInput(firstNameProf.getText().trim());
		if(firstName == null) firstNameProf.clear();
		
		lastName = FactoryUtilsFX.checkInput(lastNameProf.getText().trim());
		if(lastName == null) lastNameProf.clear();
		
		username = FactoryUtilsFX.checkInputString(usernameProf.getText().trim());
		if(username == null) usernameProf.clear();
		
		password = FactoryUtilsFX.checkInputString(passwordProf.getText().trim());
		if(password == null) passwordProf.clear();
		
		department = FactoryUtilsFX.checkInput(departmentProf.getText().trim());
		if(department == null) departmentProf.clear();
		
		String typeOption = statusProf.getValue();
		if (typeOption == "Docent") {
			status = ProfessorStatus.DOCENT;
		}
		else if (typeOption == "Vanredni profesor") {
			status = ProfessorStatus.ASSOCIATE_PROFESSOR;
		}
		else {
			status = ProfessorStatus.FULL_PROFESSOR;
		}
		
		if(jmbg == null || firstName == null || lastName == null || username == null
				|| password == null || department == null){
			showError();
			return;
		}
		ArrayList<Professor> professors = App.getProfessors();
		professors.add(new Professor(id, jmbg, firstName, lastName, username, password, department, status));
		FileUtils.writeToFile(professors, "professors");
		showMessageNewProfessor();
		showAdminMenu(event);
	}
	
	private void showMessageNewProfessor() throws IOException{
		final Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);

		window.setTitle("Obavještenje");
		window.setMinWidth(250);
		window.setMinHeight(100);
		
		window.setScene(new Scene(FXMLLoader.load(getClass().getResource("MessageNewProfessor.fxml"))));
		window.showAndWait();
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
	
	@FXML
	private void showAdminMenu(ActionEvent event) throws IOException{
		Parent adminMenu = FXMLLoader.load(getClass().getResource("AdminMenu.fxml"));
		Scene scene = new Scene(adminMenu);
		Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		adminStage.setScene(scene);
		adminStage.show();
	}
	
	@FXML
	private TextField firstNameProf;
	@FXML
	private TextField lastNameProf;
	@FXML
	private TextField jmbgProf;
	@FXML
	private TextField usernameProf;
	@FXML
	private TextField passwordProf;
	@FXML
	private TextField departmentProf;
	@FXML 
	private ComboBox<String> statusProf;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		statusProf.setValue("Docent");
		statusProf.setItems(statusProfList);
	}

}
