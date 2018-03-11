package application;

import java.io.IOException;
import java.net.URL;
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
import model.Payment;

public class PaymentsController implements Initializable{
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
		int i = 0;
		for(Payment p : App.getPayments()){
			if (p == null)
				continue;
			i++;
			text.appendText(Integer.toString(i) + ". " + p.toFile() + "\n");
		}
		if(i == 0)
			text.setText("Nema uplata.");
	}
}
