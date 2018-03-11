package application;

import java.io.IOException;

import dataUtil.FactoryUtilsFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Student;
import utils.SearchUtils;

public class FindStudentByIndexController {
	@FXML 
	private TextField indexStudent;
	@FXML
	private Text text;
	
	@FXML
	private void find(ActionEvent event) throws IOException{
		int index = FactoryUtilsFX.checkInputInt(indexStudent.getText().trim());
		Student s = SearchUtils.findStudentByIndex(index);
		if (s == null) {
			text.setText("Ne postoji student s navedenim brojem indeksa.");
			text.setFill(Color.RED);
		}
		else {
			text.setText(SearchUtils.findStudentByIndex(index).toString());
			text.setFill(Color.BLACK);
		}
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
