package assignement3.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import assignement3.communication.Customer;
//import assignement3.communication.RequestRegister;
import assignement3.communication.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * 
 * The class {@code RegisterController} is used to manages actions
 * of file Register.fxml.
 *
 */

public class RegisterController {
	
	/**
	 * Class fields.
	 * 
	 * SHOST - It is the host on which the server resides.
	 * SPORT - It is the port where the server listens.
	 * 
	 * name - It is the name inserted by client into the field.
	 * surname - It is the surname inserted by client into the field.
	 * address - It is the address inserted by client into the field.
	 * email - It is the email inserted by client into the field.
	 * password - It is the password inserted by client into the field.
	 * verifyPassword - It is the password inserted by client into the field (again).
	 * policy - It is the a checkbox that have to be selected to proceed with registration.
	 * 
	 * invalidName - It is a label that appears if you insert an invalid name.
	 * invalidSurname - It is a label that appears if you insert an invalid surname.
	 * invalidEmail - It is a label that appears if you insert an invalid email.
	 * invalidPassword - It is a label that appears if you insert an invalid password.
	 * invalidAddress - It is a label that appears if you insert an invalid address.
	 * invalidMatch - It is a label that appears if you insert a password different from verifyPassword.
	 * 
	 * VALID_PASSWORD_REGEX - It is a regular expression used to validate the password format.
	 * VALID_EMAIL_REGEX - It is a regular expression used to validate the email format.
	 */
	
	private final String SHOST = "localhost"; // host on which the server resides
	private final int SPORT = 4444; // server port

	@FXML private TextField name;
	@FXML private TextField surname;
	@FXML private TextField email;
	@FXML private PasswordField password;
	@FXML private TextArea address;
	@FXML private PasswordField verifyPassword;
	@FXML private CheckBox policy;
	
	@FXML private Label invalidName;
	@FXML private Label invalidSurname;
	@FXML private Label invalidEmail;
	@FXML private Label invalidPassword;
	@FXML private Label invalidAddress;
	@FXML private Label invalidMatch;
	
	private static final String VALID_PASSWORD_REGEX = "(?=^.{8,}$)(?=(.*[^A-Za-z]){2,})^.*";
	private static final String VALID_EMAIL_REGEX = "^(.+)@(.+)$";
	
	
	/**
	 * This method is used to validate the password inserted by client.
	 * 
	 * @param password It is the password inserted by the client into the field.
	 * @return It returns a boolean value (true = valid password | false = not valid password).
	 */

    private static boolean validatePassword(String password) {
        
    	String pattern = VALID_PASSWORD_REGEX;

        if (password.matches(pattern)) {
            return true;
        } 
        
        else {
            return false;
        }
    }
    
    
    /**
     * This method is used to validate the email inserted by client.
     * 
     * @param email It is the email inserted by the client into the field.
     * @return It returns a boolean value (true = valid email | false = not valid email).
     */
    
    private static boolean validateEmail(String email) {
        
    	String pattern = VALID_EMAIL_REGEX;

        if (email.matches(pattern)) {
            return true;
        } 
        
        else {
        	  return false;
        }
    }
    
    
    /**
     * This method is used to handles the register button event.
     * When a client clicks on register he tries to send a request 
     * for registration to the server.
     * 
     * @param event The event.
     */
    
	public void handleAddCustomerButton(ActionEvent event) {
		
		String name = this.name.getText(); // name inserted by client in text field
		String surname = this.surname.getText(); // surname inserted by client in text field
		String email = this.email.getText(); // email inserted by client in text field
		String password = this.password.getText(); // password inserted by client in text field
		String address = this.address.getText(); // address inserted by client in text field
		
		if (name.isBlank()) { // it checks if user inserted name in text field
			
			invalidName.setVisible(true); // it is used to brings up the error label into the GUI
		} 
		
		else { 
			invalidName.setVisible(false); // it is used to hide the error label
		}
		
		if (surname.isBlank()) {
			invalidSurname.setVisible(true);
		} 
		
		else {
			invalidSurname.setVisible(false);
		}
		
		if (address.isBlank()) {
			invalidAddress.setVisible(true);
		} 
		
		else {
			invalidAddress.setVisible(false);
		}

		if (email.isBlank()) {
			invalidEmail.setVisible(true);
		} 
		
		else { // if email is not empty it has to be validate
			
			if (!validateEmail(email)) {
				
				invalidEmail.setText("Invalid Email Address");
				invalidEmail.setVisible(true);
			} 
			
			else {
				invalidEmail.setVisible(false);
			}
		}
		
		if (password.isBlank()) {
			invalidPassword.setVisible(true);
		} 
		
		else { // if email is not empty it has to be validate
			
			if (!validatePassword(password)) {
				
				invalidPassword.setText("Invalid Password");
				invalidPassword.setVisible(true);
				Alert alert = new Alert(Alert.AlertType.ERROR, 
						"Password must contains at least 2 non-alphabetic characters\n"
								+ "Password must contains a number of characters between\n"
								+ "a minimum of 8 and a maximum of 15"); // it is a pop-up to warn the user that has entered an invalid password 
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
				alert.showAndWait(); // alert waits for a user input
			} 
			
			else {
				invalidPassword.setVisible(false);
			}
			
			if (!password.equals(verifyPassword.getText())) { // error if password inserted in password field differs from veryfyPassword  
				invalidMatch.setVisible(true);
			} 
			
			else {
				
				invalidMatch.setVisible(false);
				
				if (!policy.isSelected()) { // error if policy checkbox is not selected
					
					Alert alert = new Alert(Alert.AlertType.WARNING, "Agree to our terms and conditions to continue");
					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
					alert.showAndWait();
				} 
				
				else {
					
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
					alert.setHeaderText("Thank you");
				}
			}
		}
		
		// client tries to connects to the server iff ( the user has entered data in all fields AND email is valid AND password is valid AND policy button is selected)

		if(!name.isBlank() && !surname.isBlank() && !address.isBlank() && validateEmail(email) && validatePassword(password) && policy.isSelected() && password.equals(verifyPassword.getText())) { // if client entered email and policy is selected
			
			try { // The client tries to connect to the server and it sends a request for registration
								
				Socket client = new Socket(SHOST, SPORT); // socket creation to communicate with the server
				ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream()); // creation of an object output stream from a stream (client.getOutputStream()) to write an object into the object output stream (it is used to send object over the network)
				ObjectInputStream is = null; // definition of an object input stream to receive object (response) from the server
	
				Customer rq = new Customer(0, name, surname, email, password, address); // creation of a registration request
				rq.setRequest("customerRegistration");
				
				os.writeObject(rq); // writing object into the object output stream and sending request to the server
				
				os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
				
				// server response
				
				if (is == null) { // if object input stream has been never used
					
					is = new ObjectInputStream(new BufferedInputStream(client.getInputStream())); // creation of an object input stream to receive object (response) from the server
				}
				
				Object o = is.readObject(); // client waits for a server response
				
				if (o instanceof Response && ((Response) o).getResponse().equals("registration")) { 
					
					Response rs = (Response) o; 
				
					if(rs.getResp() > 0) { // if server response == true then Registration has been successfully completed
						
						Alert alert = new Alert(Alert.AlertType.INFORMATION, "Registration has been successfully completed!"); // pop up
				        alert.setHeaderText("Registration completed");
				        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
				        alert.showAndWait();
					}
					
					else { // registration has not been successfully completed
						
						Alert alert = new Alert(Alert.AlertType.ERROR, "You are already registered!"); // pop-up
				        alert.setHeaderText("Registration failed");
				        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
				        alert.showAndWait();
					}
					
					// scene changing
					
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXMLs/Login.fxml")); // fxml file charging
					Parent root = loader.load(); // charging fxml file into the Parent
					
					LoginController controller = loader.getController(); // loading controller to pass information

					controller.transferRole("customer"); // transferring information (role)
					
					Scene loginScene = new Scene(root); // creates a Scene for a specific root Node
				    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();  // replaces the current scene with another scene in the same window
				    window.setScene(loginScene); // specify the scene to be used on this stage
				    window.setTitle("Login"); // set the title of the window
				    window.show();
				}
	
				client.close(); // client closes the socket 
			} 
	        
			catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	/**
	 * This method is used to handles the exit button.
	 * 
	 * @param event The event.
	 * @throws IOException Signals that an I/O exception of some sort has occurred.
	 */
	
	@FXML
	public void handleHomeButton(ActionEvent event) throws IOException {	

		FXMLLoader loader= new FXMLLoader(getClass().getResource("../FXMLs/Login.fxml")); // load FXML file that contains login form
		
		Parent root = loader.load();
		
		LoginController controller = loader.getController(); // loading controller to pass information

		controller.transferRole("customer"); // this instruction is used to transfer the role to the next window

		// creating login window
	    
	    Scene loginScene = new Scene(root);
	    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    window.setScene(loginScene);
	    window.setTitle("Login");
	    window.show();
	}
}
