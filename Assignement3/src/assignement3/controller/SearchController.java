package assignement3.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import assignement3.communication.RequestClose;
import assignement3.communication.Response;
import assignement3.communication.Wine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * 
 * The class {@code SearchController} is used to manages
 * actions of Search.fxml.
 *
 */

public class SearchController {
	
	/**
	 * Class fields.
	 * 
	 * SHOST - It is the host on which the server resides.
	 * SPORT - It is the port where the server listens.
	 * 
	 * client - It is the socket used by client to communicate with the server.
	 * os - It is the object output stream used by client to sends messages to the server.
	 * is - It is the object input stream used by client to receives messages from the server.
	 * 
	 * searchTableView - It is the table that will contains wines searched by unregistered users.
	 * 
	 * wineName - It is the column that will contains wines name.
	 * wineProducer - It is the column that will contains wines producer.
	 * wineYear - It is the column that will contains wines year.
	 * wineTn - It is the column that will contains wines technical notes.
	 * wineVines - It is the column that will contains wines vine.
	 * wineBottle - It is the column that will contains wines number of bottles.
	 * winePrice - It is the column that will contains wines price.
	 * 
	 * productName - It is the text field used by unregistered user to search wines by name.
	 * productProducer - It is the text field used by unregistered user to search wines by producer.
	 * productYear - It is the text field used by unregistered user to search wines by year.
	 * 
	 * invalidName - It is a label that appears if user doesn't insert wine name.
	 * invalidProducer - It is a label that appears if user doesn't insert wine producer.
	 * invalidYear - It is a label that appears if user doesn't insert wine year.
	 * 
	 * VALID_YEAR_REGEX - It is a regular expression used to validate year entered by unregistered user.
	 */
	
	private final String SHOST = "localhost"; // host on which the server resides
	private final int SPORT = 4444; // server port
	
	private Socket client;	
	private ObjectOutputStream os;
	private ObjectInputStream is;
	
	@FXML private TableView<Wine> searchTableView;
	
	@FXML private TableColumn<Wine, String> wineName;
	@FXML private TableColumn<Wine, String> wineProducer;
	@FXML private TableColumn<Wine, String> wineYear;
	@FXML private TableColumn<Wine, String> wineTn;
	@FXML private TableColumn<Wine, String> wineVines;
	@FXML private TableColumn<Wine, Integer> wineBottle;
	@FXML private TableColumn<Wine, Float> winePrice;
	
	@FXML private TextField productName;
	@FXML private TextField productProducer;
	@FXML private TextField productYear;
	
	@FXML private Label invalidName;
	@FXML private Label invalidProducer;
	@FXML private Label invalidYear;
	
	private static final String VALID_YEAR_REGEX = "(19[6789][0-9]|20[01][0-9]|2020|2021)"; //dal 1960 al 2021
	
	
	/**
	 * This method is used to validate the year entered by client.
	 * 
	 * @param year It is the year inserted by the client into the field.
	 * @return It returns a boolean value (true = valid year | false = not valid year).
	 */

    private static boolean validateYear(String year) {
        String pattern = VALID_YEAR_REGEX;

        if (year.matches(pattern)) {
            return true;
        } 
        
        else {
            return false;
        }
    }
    
    
    /**
     * 
     * This is a service method used to close the socket.
     * 
     */
    
    private void closeSocket() {
    	
    	try { // the client tries to connect to the server
			
			RequestClose rqs = new RequestClose(true); // creation of a close socket request
			this.os.writeObject(rqs); 
			this.os.flush(); 								
			
			if (is == null) {
				this.is = new ObjectInputStream(new BufferedInputStream(this.client.getInputStream()));
			}
			
			Object c = this.is.readObject();
			
			if (c instanceof Response && ((Response) c).getResponse().equals("close")) { 					
				Response rs_ = (Response) c;
				
				if(rs_.getResp() > 0) {
					this.client.close();
				}
			}
		} 
        
		catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
    }
    
    
    /**
     * This is a service method use to show - load results into wine table view.
     * 
     * @param rs It is the server response.
     */
    
    public void handleViewWine(final Response rs) {
    	
    	// operations executed to show results inside table view
    	
    	ObservableList<Wine> obList = FXCollections.observableArrayList();
		
		for(Wine v: rs.getRespListS()){
			obList.add(v);
		}
						
		wineName.setCellValueFactory(new PropertyValueFactory<>("name"));
		wineProducer.setCellValueFactory(new PropertyValueFactory<>("producer"));
		wineYear.setCellValueFactory(new PropertyValueFactory<>("year"));
		wineTn.setCellValueFactory(new PropertyValueFactory<>("technicalNotes"));
		wineVines.setCellValueFactory(new PropertyValueFactory<>("vines"));
		wineBottle.setCellValueFactory(new PropertyValueFactory<>("numBottles"));
		winePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		searchTableView.setItems(obList);
		
		// clearing data fields
		
		productName.clear();
		productProducer.clear();
		productYear.clear();
    }
    
    /**
	 * 
	 * This method is used to handles search button.
	 * When user clicks on this button, it will be shown
	 * searched wines.
	 * 
	 */
	
	@FXML
	public void handleSearchProductButton() {
		
		if(productName.getText().isBlank() && productProducer.getText().isBlank() && productYear.getText().isBlank()) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR, "You must enter data in at least one field!"); // pop up
	        alert.setHeaderText("Search failed");
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
	        alert.showAndWait(); // it waits for a customer input
		}
		
		 // if year is not empty it has to be validate
			
		if(!productYear.getText().isBlank() && !validateYear(productYear.getText())) {
			invalidYear.setVisible(true);
		} 
		
		else {
			invalidYear.setVisible(false);
		}
		
		// client tries to connects to the server iff the user has entered data in all fields

		if (!this.productName.getText().isBlank() || !this.productProducer.getText().isBlank() || (!this.productYear.getText().isBlank() && validateYear(productYear.getText()))) {
			
			try { // client tries to connect to the server
							
				this.client = new Socket(SHOST, SPORT); // socket creation	
				this.os = new ObjectOutputStream(this.client.getOutputStream()); // creation of an object output stream from a stream (client.getOutputStream()) to write an object into the object output stream (it is used to send object over the network)
				this.is = null;
				
				Wine rq = new Wine(productName.getText(), productProducer.getText(), productYear.getText()); // creation of a search wine request
				rq.setRequest("search");
				
				this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
				
				this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
				
				if (is == null) { // if object input stream has been never used
					
					this.is = new ObjectInputStream(new BufferedInputStream(this.client.getInputStream()));
				}
				
				Object o = this.is.readObject(); // client waits for a server response
				
				if (o instanceof Response && ((Response) o).getResponse().equals("search")) { 
					
					Response rs = (Response) o;
			        
					this.handleViewWine(rs);
				}
				
				else if (o instanceof Response && ((Response) o).getResponse().equals("searchFail")) {
					
					for (int i = 0; i < searchTableView.getItems().size(); i++) {
						searchTableView.getItems().clear();
					}
					
					searchTableView.setPlaceholder(new Label("No result"));
				}
				
				this.closeSocket();
			} 
	        
			catch (IOException | ClassNotFoundException e2) {
				e2.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 
	 * This method is used to handles the view all (employee) button.
	 * When user clicks on this button it will be shown all wines.
	 * 
	 */
	
	@FXML
	public void handleSearchViewAllButton() {

		try { // the client tries to connect to the server
			
			this.client = new Socket(SHOST, SPORT); // socket creation	
			this.os = new ObjectOutputStream(this.client.getOutputStream()); // creation of an object output stream from a stream (client.getOutputStream()) to write an object into the object output stream (it is used to send object over the network)
			this.is = null;
			
			Wine rq = new Wine(); // creation of a view wine request
			rq.setRequest("adminViewWine");
			
			this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
			
			this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
			
			if (is == null) { // if object input stream has been never used
				
				this.is = new ObjectInputStream(new BufferedInputStream(this.client.getInputStream()));
			}
			
			Object o = this.is.readObject(); // client waits for a server response
			
			if (o instanceof Response && ((Response) o).getResponse().equals("search") ) { // if the server finds the wine the user is looking for
				
				Response rs = (Response) o;
				
				this.handleViewWine(rs); // loading results into table view
			}
			
			else if (o instanceof Response && ((Response) o).getResponse().equals("searchFail")) { // the server does not find any results
				
				searchTableView.setPlaceholder(new Label("No wine to show"));
			}
			
			this.closeSocket();
		} 
        
		catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
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

		controller.transferRole("customer"); // this instruction is used to transfer the role to the next (previous) window

		// creating login window
	    
	    Scene loginScene = new Scene(root);
	    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    window.setScene(loginScene);
	    window.setTitle("Login");
	    window.show();
	}
}
