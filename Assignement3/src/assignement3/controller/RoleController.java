package assignement3.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * 
 * The class {@code RoleController} represents the first window of the application.
 * It is a client generator.
 * This class pass the value that user selected to the next window.
 *
 */

public class RoleController  { 
	
	
	/**
	 * Class fields.
	 * 
	 * toogleGroupValue It is the string that represents the client role.
	 * group - It is the component of role window.
	 * selectBtn - It is the button to select the role.
	 */
	
	private String toogleGroupValue; // string that represents the role selected

	@FXML private ToggleGroup group; // component of role window to manage radio button in an integrated way
	@FXML private Button selectBtn; // component of role window
	
	
	/**
	 * This method is used to handles select button.
	 * 	
	 * @throws IOException Signals that an I/O exception of some sort has occurred.
	 */

	public void handleLogButton() throws IOException { // action taken when users click on select button
		
		@SuppressWarnings("unused")
		String loginform = "";
		
		RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle(); // this instruction is used to extract value from selected radio button

		this.toogleGroupValue = selectedRadioButton.getText();
		
		FXMLLoader loader= new FXMLLoader(getClass().getResource("../FXMLs/Login.fxml")); // load FXML file that contains login form
		
		Parent root = loader.load();
		
		LoginController controller = loader.getController(); // loading controller to pass information

		controller.transferRole(this.toogleGroupValue); // this instruction is used to transfer the role to the next window

		// creating login window
		
		Scene loginScene = new Scene(root); 
		Stage window = new Stage();
		window.setScene(loginScene);
		window.setTitle("Login");
		window.setResizable(false);
		window.getIcons().add(new Image("assignement3/pics/logo.png"));
		window.show();
	}
}
