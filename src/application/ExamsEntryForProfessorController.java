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
import model.Payment;
import model.Professor;
import utils.CheckLogIn;
import utils.SearchUtils;

public class ExamsEntryForProfessorController implements Initializable{
	@SuppressWarnings("deprecation")
	public static Date pocetakIspitnogRoka = new Date(117, 05, 01); //117 je 2017. godina, a mjesec je ustvari +1
	@SuppressWarnings("deprecation")
	public static Date krajIspitnogRoka = new Date(117, 05, 30); //117 je 2017. godina, a mjesec je ustvari +1
	
	@FXML
	private TextArea text;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Professor logedInProfessor = CheckLogIn.getProfessorLogedIn();
		ArrayList<Payment> payments = App.getPayments();
		int i = 0;
		Date dateNow = new Date();
		for (Payment p : payments){
			boolean x = false;
			if (p == null)
				continue;
			for (Exam e : SearchUtils.findStudentByIndex(p.getIndex()).getExamEntry()){
				if(e == null)
					continue;
				if((dateNow.after(krajIspitnogRoka) && dateNow.after(e.getDate())) ||
						(dateNow.before(pocetakIspitnogRoka) && dateNow.before(e.getDate())))
					x = true;
			}
			if(x)
				continue;
			if (p.getSubject().getProfessor().getJmbg().equals(logedInProfessor.getJmbg())  &&
					SearchUtils.findStudentByIndex(p.getIndex()).getExamEntry().contains(SearchUtils.findExamBySubject(p.getSubject().getName()))){
				i++;
				text.appendText(Integer.toString(i) + ". " + p.getSubject().getName() + " (" + 
				(SearchUtils.findStudentByIndex(p.getIndex())).getFirstName() + " " + 
						(SearchUtils.findStudentByIndex(p.getIndex())).getLastName() + " " + Integer.toString(p.getIndex()) + ")\n");
			}
		}
		if(i == 0)
			text.setText("Nema prijavljenih ispita.");
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
