package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import model.Student;

public class SortStudentsController implements Initializable{
	@FXML
	private TextArea text;
	
	@FXML
	private void showAdminMenu(ActionEvent event) throws IOException{
		Parent adminMenu = FXMLLoader.load(getClass().getResource("AdminMenu.fxml"));
		Scene scene = new Scene(adminMenu);
		Stage adminStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		adminStage.setScene(scene);
		adminStage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 ArrayList<Student> students = App.getStudents();
		 int ind = 0;
		 Collections.sort(students, new Comparator<Student>() {
		        public int compare(Student student2, Student student1)
		        {
		            return  student2.getLastName().toUpperCase().compareTo(student1.getLastName().toUpperCase());
		        }
		    });
		 for (Student s : students){
			 if (s == null)
				 continue;
			 text.appendText(s.toString() + "\n");
			 ind++;
		 }
		 if (ind == 0) {
			text.setText("Nema studenata.");
		}
	}
}
