package application;

import java.io.IOException;

import dataUtil.FactoryUtilsFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.SearchUtils;

public class FindStudentsByLastNameController {
	@FXML
	private TextField lastNameStudent;
	@FXML
	private TextArea text;
	
	@FXML
	private void search(){
		String lastName = FactoryUtilsFX.checkInput(lastNameStudent.getText().trim());
		String s = null;
		if(lastName != null)
			s = SearchUtils.findStudentByLastName(lastName);
		if (s == null) {
			text.setText("Ne postoji student s navedenim prezimenom.");
		}
		else {
			text.setText(SearchUtils.findStudentByLastName(lastName));
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
