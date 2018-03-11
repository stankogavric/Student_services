package application;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import model.Address;
import model.Exam;
import model.PassedSubject;
import model.Student;

public class NewStudentController {
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
	
	@FXML
	private TextField firstNameStudent;
	@FXML
	private TextField lastNameStudent;
	@FXML
	private TextField jmbgStudent;
	@FXML
	private TextField usernameStudent;
	@FXML
	private TextField passwordStudent;
	@FXML
	private TextField departmentStudent;
	@FXML
	private TextField schoolYearStudent;
	@FXML
	private TextField parentStudent;
	@FXML
	private TextField emailStudent;
	@FXML
	private TextField dateStudent;
	@FXML
	private TextField telephoneStudent;
	@FXML
	private TextField placeStudent;
	@FXML
	private TextField streetStudent;
	@FXML
	private TextField streetNoStudent;
	@FXML
	private TextField indexStudent;
	@FXML
	private TextField gpaStudent;
	
	@FXML
	private void saveNewStudent(ActionEvent event) throws IOException{
		String jmbg = null;
		String firstName = null;
		String lastName = null;
		String username = null;
		String password = null;
		int index;
		double gpa = 0;
		int schoolYear;
		String parent;
		String email;
		String place;
		String street;
		int streetNo;
		Address address;
		double account = 0;
		ArrayList<Exam> examEntry = new ArrayList<Exam>();
		ArrayList<PassedSubject> passedSubjects = new ArrayList<PassedSubject>();
		String department;
		Date date = null;
		String telephone;
		
		int id = App.getStudents().size() + 1;
		
		jmbg = jmbgStudent.getText().trim();
		if (!jmbg.matches("[0-9]{13}")){
			jmbg = null;
			jmbgStudent.clear();
		}
		
		firstName = FactoryUtilsFX.checkInput(firstNameStudent.getText().trim());
		if(firstName == null) firstNameStudent.clear();
		
		lastName = FactoryUtilsFX.checkInput(lastNameStudent.getText().trim());
		if(lastName == null) lastNameStudent.clear();
		
		username = FactoryUtilsFX.checkInputString(usernameStudent.getText().trim());
		if(username == null) usernameStudent.clear();
		
		password = FactoryUtilsFX.checkInputString(passwordStudent.getText().trim());
		if(password == null) passwordStudent.clear();
		
		department = FactoryUtilsFX.checkInput(departmentStudent.getText().trim());
		if(department == null) departmentStudent.clear();
		
		index = FactoryUtilsFX.checkInputInt(indexStudent.getText().trim());
		if(index == 0) indexStudent.clear();
		
		schoolYear = FactoryUtilsFX.checkInputInt(schoolYearStudent.getText().trim());
		if(schoolYear == 0) schoolYearStudent.clear();
		
		parent = FactoryUtilsFX.checkInput(parentStudent.getText().trim());
		if(parent == null) parentStudent.clear();
		
		email = FactoryUtilsFX.checkInputString(emailStudent.getText().trim());
		if(email == null) emailStudent.clear();
		
		place = FactoryUtilsFX.checkInput(placeStudent.getText().trim());
		if(place == null) placeStudent.clear();
		
		street = FactoryUtilsFX.checkInput(streetStudent.getText().trim());
		if(street == null) streetStudent.clear();
		
		streetNo = FactoryUtilsFX.checkInputInt(streetNoStudent.getText().trim());
		if(streetNo == 0) streetNoStudent.clear();
		
		address = new Address(place, street, streetNo);
		
		String dateString = FactoryUtilsFX.checkInputString(dateStudent.getText().trim());
		if(dateString != null){
			try {
				date = sdf.parse(dateString);
			} catch (ParseException e) {
				dateStudent.clear();
			}
		}
		telephone = FactoryUtilsFX.checkInputString(telephoneStudent.getText().trim());
		if(telephone == null) telephoneStudent.clear();
		
		gpa = FactoryUtilsFX.checkInputDouble(gpaStudent.getText().trim());
		if(gpa == -1 || gpa < 0 || gpa > 10){ gpaStudent.clear(); gpa = -1;}
		
		if(!PassedExamsController.getPassedSubjectsTemp().isEmpty())
			passedSubjects = PassedExamsController.getPassedSubjectsTemp();
		
		if(jmbg == null || firstName == null || lastName == null || username == null
				|| password == null || department == null  || index == 0 || schoolYear == 0
				|| parent  == null || place == null || street == null || streetNo == 0 || telephone == null
				|| date == null || email == null || gpa == -1){
			showError();
			return;
		}
		ArrayList<Student> students = App.getStudents();
		students.add(new Student(id, jmbg, firstName, lastName, username, password, index, gpa,
				schoolYear, parent, email, address, account, examEntry, passedSubjects, 
				department, date, telephone));
		FileUtils.writeToFile(students, "students");
		showMessageNewStudent();
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
	
	private void showMessageNewStudent() throws IOException{
		final Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);

		window.setTitle("Obavještenje");
		window.setMinWidth(250);
		window.setMinHeight(100);
		
		window.setScene(new Scene(FXMLLoader.load(getClass().getResource("MessageNewStudent.fxml"))));
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
	private void passedExams() throws IOException{
		final Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);

		window.setTitle("Obavještenje");
		window.setMinWidth(250);
		window.setMinHeight(100);
		
		window.setScene(new Scene(FXMLLoader.load(getClass().getResource("PassedExams.fxml"))));
		window.showAndWait();
	}
}
