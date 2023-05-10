package assignement3.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import assignement3.communication.People;
import assignement3.communication.RequestClose;
import assignement3.communication.Response;

/**
 * 
 * The class {@code LoginController} represents the controller that manages 
 * the actions that must be taken when a client triggers an action.
 *
 */

public class LoginController {
	
	/**
	 * Class fields.
	 * 
	 * SHOST - It is the host on which the server resides.
	 * SPORT - It is the port where the server listens.
	 * 
	 * role - It is the client role.
	 * 
	 * client - It is the socket used by client to communicate with the server.
	 * os - It is the object output stream used by client to sends messages to the server.
	 * is - It is the object input stream used by client to receives messages from the server.
	 * 
	 * userName - It is the customer email.
	 * pass - It is the customer password.
	 * 
	 * invalidLabel - It is a label that appears if customer insert wrong credential.
	 * loginIcon - It is an image that appears during the login phase.
	 * 
	 * imageNearLogin - It is an image.
	 * 
	 * register - It is a button register customer.
	 * search - It is a button to search wine.
	 * 
	 * registerLabel - It is a text into the window.
	 * loginLabel - It is the main title.
	 */
	
	private final String SHOST = "localhost"; // host on which the server resides
	private final int SPORT = 4444; // server port
	private String role;
	
	private Socket client;
	private ObjectOutputStream os;
	private ObjectInputStream is;

    @FXML private TextField userName; // content of user name field (login)
    @FXML private TextField pass; // content of of password field (login)
   
    @FXML private Label invalidLabel; 
    @FXML private ImageView loginIcon; // image
	
	@FXML private ImageView imageNearLogin; // image in login form
	
	@FXML private Button register; // button to register a user
	@FXML private Button search; // button to search a wine
	
	@FXML private Label registerLabel; // text 
	@FXML private Label loginLabel; // text 
	
	
	/**
	 * 
	 * This method is used to handles the contact us button.
	 * 
	 */
	
	@FXML 
    public void handleContactUsLink() { // action taken when a client click on contact us
		
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Nome - Lorenzo Di Palma\nMatricola - 299636\nNome - Vincenzo Fraello\nMatricola - 299647"); // pop up
        alert.setHeaderText("Contact Us");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
        alert.showAndWait();
    }
	
    
    /**
     * This method is used to handles the press of "Enter" to make login.
     * 
     * @param event The event.
     * @throws IOException Signals that an I/O exception has occurred.
     */
   
    @FXML 
    private void onEnter(ActionEvent event) throws IOException { // action taken when a user press "Enter"
        handleLoginButton(event);
    }
    
       
   /**
    * This method is used to change the scene when user clicks on a button 
    * (search/login/register).
    * 
    * @param event The event.
    * @param fxml It is the fxml file.
    * @param title It is the title to put into the new window.
    * @param id It is the customer id inside database.
    * @param os It is the Object Output Stream used to communicate with the server.
    * @param is It is the Object Input Stream used to communicate with the server.
    * @param client It is the socket used to communicate with the server.
    * @throws IOException Signals that an I/O exception has occurred.
    */
    
    public void changeScene(final ActionEvent event, final String fxml, final String title, final int id, final ObjectOutputStream os, final ObjectInputStream is, final Socket client) throws IOException {
				
		FXMLLoader loader= new FXMLLoader(getClass().getResource(fxml)); // fxml file charging
		Parent root = loader.load(); // charging fxml file into the Parent
		
		// actions taken if the scene is some dashboard - passed parameters between windows are used for communication between the client and the server
		
		if(fxml.equals("../FXMLs/CustomerDashboard.fxml")) { 
			
			CustomerDashboardController controller = loader.getController(); // loading controller to pass information
			controller.transferSocketIO(this.client, this.os, this.is); // this instruction is used to transfer socket, object output stream and object input stream to the next window
			controller.initialize(this.userName.getText(),id); // this instruction is used to pass the user name info to the next window.
			controller.transferCustomer(id); // this instruction is used to pass the customer id inside database --> the id is used to make other operations such as search ecc.ecc
		}
		
		else if(fxml.equals("../FXMLs/AdminDashboard.fxml")) {
			
			AdminDashboardController controller = loader.getController();
			controller.transferSocketIO(this.client, this.os, this.is);
			controller.initialize(this.userName.getText());
		}
		
		else if(fxml.equals("../FXMLs/EmployeeDashboard.fxml")) {
			
			EmployeeDashboardController controller = loader.getController(); 
			controller.transferSocketIO(this.client, this.os, this.is); 
			controller.initialize(this.userName.getText());
		}
				
		// creating login window
	    
		Scene dashboardScene = new Scene(root); // creates a Scene for a specific root Node
	    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow(); // replaces the current scene with another scene in the same window
	    window.setScene(dashboardScene); // specify the scene to be used on this stage
	    window.setTitle(title); // set the title of the window
	    window.show();
	    
	    // gestione evento utente clicca sulla X della window senza aver eseguito il logout e relativa chiusura della socket
	    
	    // if user closes the window and if the user does not log out and does not close the socket properly, then the program will do it for him
	    	// because the logout button closes the socket.
	    
	    window.setOnCloseRequest(new EventHandler<WindowEvent>() {

			public void handle(WindowEvent we) {
				
				// occorre imporre la condizione che la socket sia non nulla perchè se l'utente clicca su register 
					// e poi clicca sulla X della finestra, si tenta di chiudere una socket che non è mai 
						// stata aperta poichè nel register la scoket viene creata quando si clicca sul bottone registrami
				
				// occorre imporre la condizione che la socket non sia già stata chiusa perchè se l'utente esegue il logout 
					// nella finestra della dashboard allora la socket viene chiusa.
				 		// quando l'utente chiuderà la window con la X il programma non tenta di richiudere la socket
							// poichè è stata già chiusa.

				if(client != null && !client.isClosed()) { // if socket has been created and if it isn't already closed (because also logout button closes the socket)
					
					try { // the client tries to connect to the server
						
						RequestClose rqs = new RequestClose(true); // creation of a close socket request
						
						os.writeObject(rqs); // writing object into the object output stream and sending request to the server
						
						os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
						
						Object c = is.readObject(); // client waits for a server response
						
						if (c instanceof Response && ((Response) c).getResponse().equals("close")) { 
							
							Response rs_ = (Response) c;
							
							if(rs_.getResp() > 0) { // if server sends ok
								
								client.close(); // client closes the socket
							}
						}
					} 
			        
					catch (IOException | ClassNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}
				
		});
	}
	
	
	/**
	 * This method is used to handles the register button.
	 * 
	 * @param event The event.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	
	@FXML 	
	public void handleRegisterButton(ActionEvent event)throws IOException { // action taken when a user clicks on register button
		
		changeScene(event,"../FXMLs/Register.fxml","Register", 0, null, null, null);
    }
	
	
	/**
	 * This method is used to handles the search button.
	 * 
	 * @param event the event.
	 * @throws IOException signals that an I/O exception has occurred.
	 */
	
	@FXML 	
	public void handleSearchButton(ActionEvent event)throws IOException { // action taken when a user clicks on search button
		
		changeScene(event, "../FXMLs/Search.fxml", "Search - unregistered users", 0, null, null, null);
    }
	
	
	/**
	 * This method is used to handles the login button.
	 * 
	 * @param event The event.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */

	@FXML 
	public void handleLoginButton(ActionEvent event) throws IOException { // action taken when users clicks on log in button
		
		loginIcon.setVisible(true); // when user clicks on login button an image is loaded
	
	    String userName = this.userName.getText(); // email inserted by user in text field
	    String pass = this.pass.getText(); // password inserted by user in text field
	    String role = this.getRole(); // role passed as message from RoleController
	    
	    if(userName.isBlank() || pass.isBlank()) { // you can't make server request without inserting data
	    	
			Alert alert = new Alert(Alert.AlertType.ERROR, "Blank fields!"); // pop up
	        alert.setHeaderText("Login failed");
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
	        alert.showAndWait();
	        loginIcon.setVisible(false);
	    }
	    
	    else {
	    	
	    	try { // The client tries to connect to the server
				
				this.client = new Socket(SHOST, SPORT); // socket creation
				this.os = new ObjectOutputStream(client.getOutputStream()); // creation of an object output stream from a stream (client.getOutputStream()) to write an object into the object output stream (it is used to send object over the network)
				this.is = null; // definition of object input stream to receive object (response) from the server

				People rq = new People(userName, pass, role); // creation of a log in request
				rq.setRequest("login"); // type of request: login

				os.writeObject(rq); // writing object into the object output stream and sending request to the server
				
				os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
				
				if (this.is == null) { // if object input stream has been never used
					
					this.is = new ObjectInputStream(new BufferedInputStream(client.getInputStream())); // creation of object input stream
				}
				
				Object o = this.is.readObject(); // client waits for a server response
				
				if (o instanceof Response && ((Response) o).getResponse().equals("login")) { 
					
					Response rs = (Response) o; 
				
					if(rs.getResp() > 0) { // server sent true (customer id that is positive) to the client
						
						switch (role) { // changing the scene
						
							case "administrator": {
								changeScene(event, "../FXMLs/AdminDashboard.fxml", "Administrator Dashboard", rs.getResp(), this.os, this.is, this.client);
								break;
							}
		
							case "customer": {
								changeScene(event, "../FXMLs/CustomerDashboard.fxml", "Customer Dashboard", rs.getResp(), this.os, this.is, this.client);
								break;
							}
		
							case "employee": {
								changeScene(event, "../FXMLs/EmployeeDashboard.fxml", "Employee Dashboard", rs.getResp(), this.os, this.is, this.client);
								break;
							}
						}
					}
					
					else { // server sent false to the client (wrong credential or user is not registered)
						
						invalidLabel.setVisible(true);
						Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong credentials or you are not registered!"); // pop up
				        alert.setHeaderText("Login failed");
				        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
				        alert.showAndWait();
				        loginIcon.setVisible(false);
				        
				        this.client.close(); // if wrong login client closes the socket.
					}
				}
				
			} 
	        
			catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
	    }
	}
	
	
	/**
	 * This method is used to pass the role to the next window.
	 * 
	 * @param message The role.
	 * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed.
	 */
	
	// occorre trasferire il ruolo da una finestra ad un'altra perchè se il client esegue il logout e
		// successivamente vuole rieseguire il login, il programma non si ricorda che tipo di utente è.
	
	protected void transferRole(String message) throws FileNotFoundException { // method used to pass the role to the next window 
        
		if(!message.equals("customer")) { // if the user selected a radio button different from Customer then layout changes
			
			// different layouts for employee and administrator
			
			if(message.equals("administrator")) {
				
				File file = new File("src/assignement3/pics/login-admin.png");
				Image img = new Image(file.toURI().toString());
				loginLabel.setText("Welcome administrator");
				imageNearLogin.setImage(img);
			}
			
			else {
				
				File file = new File("src/assignement3/pics/login-employee.png");
				Image img = new Image(file.toURI().toString());
				loginLabel.setText("Welcome employees");
				imageNearLogin.setImage(img);
			}
						
			register.setVisible(false); // button register is disabled
			search.setVisible(false); // button search is disabled
			registerLabel.setVisible(false); // text that invite a user to register disabled
		}
		
		this.setRole(message); // setting the role inside login controller
    }
	
	/**
     * This method is used to get the role.
     * 
     * @return It returns the role.
     */
    
    private String getRole() { return this.role; }
	
	/**
	 * This method is used to set the role.
	 * 
	 * @param mss It is the role.
	 */
    
    private void setRole(final String mss) { this.role = mss; }
}
