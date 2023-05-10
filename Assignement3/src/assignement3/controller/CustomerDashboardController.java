package assignement3.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import assignement3.communication.Order;
import assignement3.communication.People;
import assignement3.communication.RequestClose;
import assignement3.communication.Response;
import assignement3.communication.Wine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * 
 * The class {@code CustomerDashboardController}  is used to manage
 * actions of CustomerDashboardController.fxml.
 *
 */

public class CustomerDashboardController  {
	
	
	/**
	 * Class fields.
	 * 
	 * client - It is the socket used by client to communicate with the server.
	 * os - It is the object output stream used by client to sends messages to the server.
	 * is - It is the object input stream used by client to receives messages from the server.
	 * 
	 * usernameLabel - It is the name of the customer logged in.
	 * 
	 * customerTabPane - It represents the customer container for tabs.
	 * 
	 * historyTab - It is the tab of customer orders history.
	 *  
	 * shopTableView - It is the table that will contains wines searched by customers.
	 *  
	 * wineName - It is the column that will contains wines name.
	 * wineProducer - It is the column that will contains wines producer.
	 * wineYear - It is the column that will contains wines year.
	 * wineTn - It is the column that will contains wines technical notes.
	 * wineVines - It is the column that will contains wines vine.
	 * wineBottle - It is the column that will contains wines number of bottles.
	 * winePrice - It is the column that will contains wines price.
	 * 
	 * productName - It is the text field used by customer to search wines by name.
	 * productProducer - It is the text field used by customer to search wines by producer.
	 * productYear - It is the text field used by customer to search wines by year.
	 * productNBottles - It is the text field used by customer to insert the number of bottles desired.
	 * 
	 * invalidName - It is a label that appears if user doesn't insert wine name.
	 * invalidProducer - It is a label that appears if user doesn't insert wine producer.
	 * invalidYear - It is a label that appears if user doesn't insert wine year.
	 * invalidNBottles - It is a label that appears if user doesn't insert a valid number of bottles.
	 * 
	 * buyButton - It is the buy button.
	 *  
	 * historyTableView - It is the table that will contains customers orders history.
	 * 
	 * ordID - It is the column that will contains order id.
	 * ordWine - It is the column that will contains wines name.
	 * ordWProducer - It is the column that will contains wines producer.
	 * ordWYear - It is the column that will contains wines year.
	 * ordBottles - It is the column that  will contains wines number of bottles.
	 * ordCompleted - It is the column that will contains a flag that indicates if the order is completed.
	 * ordDate - It is the column that will contains orders date.
	 * ordTot - It is the column that will contains the the orders total price.
	 * 
	 * wineNameHistoryField - It is the field used to search wine in orders history by name.
	 * dateOrder - It is the the field used to search orders by date.
	 * 
	 * notifyTableView - It is the table that will contains notification sent by system to the customers when a wine is available.
	 * 
	 * notifyID - It is the column that will contains notification id.
	 * notifyWine - It is the column that will contains wines name.
	 * notifyWProducer - It is the column that will contains wines producer.
	 * notifyWYear - It is the column that will contains wines year.
	 * notifyBottles - It is the column that will contains wines number of bottles.
	 * notifyDate - It is the column that will contains date of the notification.
	 * 
	 * wineNameNotifyField - It is the field used to search wine in notification list by name.
	 * dateNotify - It is the the field used to search notifications by date.
	 * 
	 * newName - It is the name to insert to modify customer data.
	 * newSurname - It is the surname to insert to modify customer data.
	 * newEmal - It is the email to insert to modify customer data.
	 * newPass - It is the password to insert to modify customer data.
	 * oldPass - It is the password to insert to confirm data modification.
	 * newAddress - It is the address to insert to modify customer data.
	 * 
	 * accountTableView - It is the table that will contains customer data.
	 * 
	 * custName - It is the column that will contains customer name.
	 * custSurname - It is the column that will contains customer surname.
	 * custEmail - It is the column that will contains notification customer email.
	 * custAddress - It is the column that will contains notification customer address.
	 * 
	 * customer - It is the customer id.
	 * oldPassword - It is the current customer password.
	 * 
	 * VALID_PASSWORD_REGEX - It is a regular expression used to validate password format.
	 * VALID_EMAIL_REGEX - It is a regular expression used to validate email format.
	 * VALID_BOTTLES_REGEX - It is a regular expression used to validate number of bottles format.
	 * VALID_YEAR_REGEX - It is a regular expression used to validate year format.
	 */

	private Socket client;
	private ObjectOutputStream os;
	private ObjectInputStream is;
	
	@FXML private Label usernameLabel;

	@FXML private TabPane customerTabPane;
	@FXML private Tab historyTab;
	
	@FXML private TableView<Wine> shopTableView;
	
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
	@FXML private TextField productNBottles;
	
	@FXML private Label invalidName;
	@FXML private Label invalidProducer;
	@FXML private Label invalidYear;
	@FXML private Label invalidNBottles;
	
	@FXML private Button buyButton;
	
	@FXML private TableView<Order> historyTableView;
	
	@FXML private TableColumn<Order, Integer> ordID;
	@FXML private TableColumn<Order, String> ordWine;
	@FXML private TableColumn<Order, String> ordWProducer;
	@FXML private TableColumn<Order, String> ordWYear;
	@FXML private TableColumn<Order, Integer> ordBottles;
	@FXML private TableColumn<Order, Boolean> ordCompleted;
	@FXML private TableColumn<Order, Date> ordDate;
	@FXML private TableColumn<Order, Float> ordTot;
	
	@FXML private TextField wineNameHistoryField;
	@FXML private DatePicker dateOrder;

	@FXML private TableView<Order> notifyTableView;
	
	@FXML private Tab notifyTab;
	
	@FXML private TableColumn<Order, Integer> notifyID;
	@FXML private TableColumn<Order, String> notifyWine;
	@FXML private TableColumn<Order, String> notifyWProducer;
	@FXML private TableColumn<Order, String> notifyWYear;
	@FXML private TableColumn<Order, Integer> notifyBottles;
	@FXML private TableColumn<Order, Date> notifyDate;
		
	@FXML private TextField wineNameNotifyField;
	@FXML private DatePicker dateNotify;
	
	@FXML private TextField newName;
	@FXML private TextField newSurname;
	@FXML private TextField newEmail;
	@FXML private TextField newAddress;
	@FXML private PasswordField newPass;
	@FXML private PasswordField oldPass;

	@FXML private TableView<People> accountTableView;
	
	@FXML private TableColumn<People, String> custName;
	@FXML private TableColumn<People, String> custSurname;
	@FXML private TableColumn<People, String> custEmail;
	@FXML private TableColumn<People, String> custAddress;
	
	private int customer;
	private String oldPassword;
	
	private static final String VALID_PASSWORD_REGEX = "(?=^.{8,}$)(?=(.*[^A-Za-z]){2,})^.*";
	private static final String VALID_EMAIL_REGEX = "^(.+)@(.+)$";
	private static final String VALID_BOTTLES_REGEX = "^[1-9][0-9]*$";
	private static final String VALID_YEAR_REGEX = "(19[6789][0-9]|20[01][0-9]|2020|2021)"; //dal 1960 al 2021
	
	
	/**
	 * This method is used to validate the password entered by client.
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
     * This method is used to validate the email entered by client.
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
	 * This method is used to validate the number of desired bottles entered by client.
	 * 
	 * @param bottles It is the bottles inserted by the client into the field.
	 * @return It returns a boolean value (true = valid bottles | false = not valid bottles).
	 */
	    
	private static boolean validateBottles(String bottles) {
        
		String pattern = VALID_BOTTLES_REGEX;

        if (bottles.matches(pattern)) {
            return true;
        } 
        
        else {
            return false;
        }
    }
	    
	    
    /**
	 * This method is used to validate the year inserted by client.
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
	 * This method is used to pass information from LoginController 
	 * to CustomerDashboardController.
	 * It is used to pass the socket, Object output stream, Object input stream
	 * for client - server communication.
	 * 
	 * @param c It is the socket used by client to communicate with the server.
	 * @param o It is the object output stream used by client to sends messages (objects) to the server.
	 * @param i It is the object input stream used by client to receives messages (objects) form the server.
	 * @throws FileNotFoundException signals that an attempt to open the file denoted by a specified pathname has failed.
	 */
		
	public void transferSocketIO(final Socket c, final ObjectOutputStream o, final ObjectInputStream i) throws FileNotFoundException { // method used to pass  
		this.setClient(c);
	    this.setOs(o);
	    this.setIs(i);
	}
	
	
	/**
	 * 
	 * This method is used to handles date picker. 	
	 *
	 */
	
	@FXML  
	private Timestamp handleDatePicker() {
		
		DatePicker dateBox = null;
		
		// in relation to the selected tab there are different date pickers
		
		if(customerTabPane.getSelectionModel().getSelectedItem().getText().equals("History")) dateBox = dateOrder;
		else if (customerTabPane.getSelectionModel().getSelectedItem().getText().equals("Notification")) dateBox = dateNotify;
		
		if(dateBox.getValue() != null) {
			LocalDate date = dateBox.getValue();
			LocalTime time = LocalTime.now();
			LocalDateTime datetime = LocalDateTime.of(date, time);
			return Timestamp.valueOf(datetime);
		}
		
		return null;
	}
	
	
	/**
	 * This method is used to initialize the customer dashboard.
	 * It makes appear the user name into the dashboard.
	 * 
	 * @param n It is the customer user name.
	 * @param id It is the customer id.
	 */

	public void initialize(final String n, final int id) { //final int id

		usernameLabel.setText(n.split("@")[0]);
		
		// after logging in, the first thing you do is check if the system has sent a notification 
			// of availability of a given wine to the customer		
		
		try { // the client tries to connect to the server
					
			Order rq = new Order(id); // creation of a view notification of availability request
			rq.setNameW("");
			rq.setRequest("customerViewNotificationBySearch");
			
			this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
			
			this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
			
			Object o = this.is.readObject(); // client waits for a server response
			
			if (o instanceof Response && ((Response) o).getResponse().equals("customerNotifyView")) { 
		
				Response rs = (Response) o;
				
				this.handleViewNotify(rs);
				
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Notification");
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
				alert.setContentText("New notification!\nDo you want to see it?");
				@SuppressWarnings("unused")
				ButtonType okButton = new ButtonType("Yes", ButtonData.YES);
				@SuppressWarnings("unused")
				ButtonType noButton = new ButtonType("No", ButtonData.CANCEL_CLOSE);
				Optional<ButtonType> result = alert.showAndWait();
				
				// if table is not empty then the first tab will be notification subscribed by customer that has been satisfied
				
				if (result.get() == ButtonType.OK) customerTabPane.getSelectionModel().select(notifyTab); 
				
			}
				
			else if (o instanceof Response && ((Response) o).getResponse().equals("notifyFail")) { // the server does not find any results
				
				notifyTableView.setPlaceholder(new Label("No result"));
			}
		} 
        
		catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * This is a service method used to show - load wines into search table view.
	 * 
	 */
	
	private void handleViewWines(final Response rs) {
		
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
		
		shopTableView.setItems(obList);
		
		// after showing the results, the fields for searching wines are emptied
		
		productName.clear();
		productProducer.clear();
		productYear.clear();
		productNBottles.clear();
		
		// after showing the results, it will be shows button to make purchase

		buyButton.setDisable(false);
		productNBottles.setDisable(false);
	}
	
	
	/**
	 * 
	 * This method is used to handles the view all (customer) button.
	 * When customer clicks on this button, it will be shown searched wines.
	 * 
	 */
	
	@FXML
	public void handleSearchViewAllButton() {
		
		try { // the client tries to connect to the server
			
			Wine rq = new Wine(); // creation of a view wine request
			rq.setRequest("adminViewWine");
			
			this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
			
			this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
			
			Object o = this.is.readObject(); // client waits for a server response
			
			if (o instanceof Response && ((Response) o).getResponse().equals("search") ) { // if the server finds the wine the user is looking for
				
		        Response rs = (Response) o;
		        
				this.handleViewWines(rs); // loading results into table view			
			}
			
			else if (o instanceof Response && ((Response) o).getResponse().equals("searchFail")) { // the server does not find any results
				
				shopTableView.setPlaceholder(new Label("No wine to show"));
			}
		} 
        
		catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * This method is used to handles the search button.
	 * When customer clicks on this button, it will be shown 
	 * the searched wines.
	 * 
	 */
	
	@FXML
	public void handleSearchProductButton() { // actions take when client clicks on search button	
		
		if(productName.getText().isBlank() && productProducer.getText().isBlank() && productYear.getText().isBlank()) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR, "You must enter data in at least one field!"); // pop up
	        alert.setHeaderText("Search failed");
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
	        alert.showAndWait(); // it waits for a customer input
		}
		
		if (!productYear.getText().isBlank() && !validateYear(productYear.getText())) { // if year is not blank it has to be validated
			invalidYear.setVisible(true);
		} 
		
		else {
			invalidYear.setVisible(false);
		}
		
		// client tries to connects to the server iff the user has entered at least data in one field
		
		if (!this.productName.getText().isBlank() || !this.productProducer.getText().isBlank() || (!this.productYear.getText().isBlank() && validateYear(productYear.getText()))) {
			
			try { // the client tries to connect to the server
				
				Wine rq = new Wine(productName.getText(), productProducer.getText(), productYear.getText()); // creation of a search wine request
				rq.setRequest("search");
				
				this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
				
				this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
				
				Object o = this.is.readObject(); // client waits for a server response
				
				if (o instanceof Response && ((Response) o).getResponse().equals("search")) { // if the server finds the wine the user is looking for
					
					Response rs = (Response) o;
					
					this.handleViewWines(rs); // loading results into table view
				}
				
				else if (o instanceof Response && ((Response) o).getResponse().equals("searchFail")) { // the server does not found any results
					
					for ( int i = 0; i < shopTableView.getItems().size(); i++) { // cleaning the table view
						shopTableView.getItems().clear();
					}
					
					shopTableView.setPlaceholder(new Label("No result"));
					
					// it will be disabled button to make purchase

					productNBottles.clear();
					buyButton.setDisable(true);
					productNBottles.setDisable(true);
				}
			}	 
			
			catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 
	 * This method is used to handles the buy button and subscribe notification.
	 *
	 */
		
	@FXML
	public void handleBuyProductButton() {
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis()); // it takes the current date e time of the order
		
		Wine wineselected = shopTableView.getSelectionModel().getSelectedItem(); // it is the selected wine by customer inside table
		
		if(wineselected == null) { // if customer doesn't select any wine
			
			Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a row of the table to make a buy!"); // pop up
	        alert.setHeaderText("Buy failed");
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
	        alert.showAndWait(); // it waits for a customer input
		}
		
		if(productNBottles.getText().isBlank() || !validateBottles(productNBottles.getText()) || Integer.parseInt(this.productNBottles.getText()) <= 0) { // if user entered an invalid number of bottles
			invalidNBottles.setVisible(true);
		}
		
		// it checks that the wine has been selected, the number of bottles has been entered and that it is valid
		
		if(wineselected != null && !this.productNBottles.getText().isBlank() && validateBottles(productNBottles.getText()) && Integer.parseInt(this.productNBottles.getText()) > 0) {
		
			// it checks that the number of bottles inserted isn't greater than those available 
			
			if (Integer.parseInt(productNBottles.getText()) > wineselected.getNumBottles()) {
				
				// user can subscribe notification
				
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Notification");
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
				alert.setContentText("Number of bottle requested are more than bottles in the shop.\n" + "Do you want to subribe a notification?");
				@SuppressWarnings("unused")
				ButtonType okButton = new ButtonType("Yes", ButtonData.YES);
				@SuppressWarnings("unused")
				ButtonType noButton = new ButtonType("No", ButtonData.CANCEL_CLOSE);
				Optional<ButtonType> result = alert.showAndWait();
				
				// if user select subscribe notification
				
				if (result.get() == ButtonType.OK) {
				   
					try { // the client tries to connect to the server
						
						Order rq = new Order(this.getCustomer(), wineselected.getId(), Integer.parseInt(this.productNBottles.getText())); // creation of a subscribe notification request
						rq.setRequest("customerSubscribeNotification");
						
						this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
						
						this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
						
						Object o = this.is.readObject(); // client waits for a server response
						
						if (o instanceof Response && ((Response) o).getResponse().equals("notification")) { 
							
							Response rs = (Response) o;
							
							if(rs.getResp() > 0) { // server sent ok
								
								Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Your notification has been successfully completed!"); // pop up
						        alert2.setHeaderText("Notification completed");
						        Stage stage2 = (Stage) alert2.getDialogPane().getScene().getWindow();
						        stage2.getIcons().add(new Image("assignement3/pics/logo.png"));
						        alert2.showAndWait();
						        
						        for ( int i = 0; i < shopTableView.getItems().size(); i++) { // cleaning the table view
									shopTableView.getItems().clear();
								}
						        
						        // cleaning text fields
						        
						        productNBottles.clear();
						        productNBottles.setDisable(true);
						        buyButton.setDisable(true);
							}
						}
					} 
			        
					catch (IOException | ClassNotFoundException e1) {
						e1.printStackTrace();
					}
				}
				
				else { // if customer doesn't want to subscribe notification
					
					invalidNBottles.setVisible(false);
					productNBottles.clear();
				}
			}
			
			// it checks that the number of bottles inserted is less than or equal than those available 
			
			else if (Integer.parseInt(productNBottles.getText()) <= wineselected.getNumBottles()) { // if the client enters all the parameters necessary to search for wine 
				
				invalidNBottles.setVisible(false);		
				
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Credit card details");
				dialog.setHeaderText("Insert your credit card details");
				dialog.setContentText("Please enter your credit card number:");

				Optional<String> result = dialog.showAndWait();
				
				if(result.isPresent() && !result.get().isBlank()) { // if user entered credit card number
				
					try { // the client tries to connect to the server
			
						float tot = wineselected.getPrice() * Integer.parseInt(this.productNBottles.getText()); // calculating the amount of money that customer have to pays
							
						Order rq = new Order(this.getCustomer(), wineselected.getId(), Integer.parseInt(this.productNBottles.getText()), timestamp, tot); // creation of a buy wine request
						rq.setRequest("customerBuy");
						
						this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
						
						this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
						
						Object o = this.is.readObject(); // client waits for a server response
						
						if (o instanceof Response && ((Response) o).getResponse().equals("buy")) { 
							
							Response rs = (Response) o;
							
							if(rs.getResp() > 0) { // server sent ok
								
								Alert alert = new Alert(Alert.AlertType.INFORMATION, "Your order has been successfully completed!"); // pop up
						        alert.setHeaderText("Purchase completed");
						        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
						        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
						        alert.showAndWait();
	
						        for ( int i = 0; i < shopTableView.getItems().size(); i++) { // cleaning the table view
									shopTableView.getItems().clear();
								}
						        
						        // cleaning text fields.
						        
						        productNBottles.clear();
						        productNBottles.setDisable(true);
						        buyButton.setDisable(true);
							}
						}
					} 
			        
					catch (IOException | ClassNotFoundException e1) {
						e1.printStackTrace();
					}
				}
				
				else { // customer doesn't want to make purchase
					
					productNBottles.clear();

					Alert alert = new Alert(Alert.AlertType.WARNING, "Your order has been successfully canceled!"); // pop up
			        alert.setHeaderText("Purchase canceled");
			        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
			        alert.showAndWait();
				}
			}
			
		}	
	}
	
	
	/**
	 * 
	 * This is a service method used to show - load results into orders history table view.
	 * 
	 */
	
	private void hanldeViewHistory(final Response rs) {
		
		ObservableList<Order> obList = FXCollections.observableArrayList();
		
		for(Order v: rs.getRespListO()) {
			if(v.getCompleted() == 0) v.setStringCompleted("not shipped");
			else v.setStringCompleted("shipped");
			obList.add(v);
		}
						
		ordID.setCellValueFactory(new PropertyValueFactory<>("idO"));
		ordWine.setCellValueFactory(new PropertyValueFactory<>("nameW"));
		ordWProducer.setCellValueFactory(new PropertyValueFactory<>("producerW"));
		ordWYear.setCellValueFactory(new PropertyValueFactory<>("yearW"));
		ordBottles.setCellValueFactory(new PropertyValueFactory<>("numBottles"));
		ordCompleted.setCellValueFactory(new PropertyValueFactory<>("stringCompleted"));
		ordDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		ordTot.setCellValueFactory(new PropertyValueFactory<>("tot"));
		
		historyTableView.setItems(obList);
		
		wineNameHistoryField.clear();
		dateOrder.getEditor().clear();
		
		dateOrder.setValue(null);
	}
	

	/**
	 * 
	 * This method is used to handles the view by search (orders history) button.
	 * When customer clicks on this button, it will be shown orders history into 
	 * table view.
	 * 
	 */
		
	@FXML
	public void handleHistoryViewBySearchButton() {

		String wineName = "";
		
		Timestamp  dateHistory = this.handleDatePicker(); // extracting date entered by customer 
		
		wineName = wineNameHistoryField.getText(); // wine name entered by user
		
		try { // the client tries to connect to the server

			Order rq = new Order(this.getCustomer()); // creation of a view history request.
			rq.setDate(dateHistory);
			rq.setNameW(wineName);
			rq.setRequest("customerViewBySearch");
			
			this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
			
			this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
			
			Object o = this.is.readObject(); // client waits for a server response
			
			if (o instanceof Response && ((Response) o).getResponse().equals("historyOrder")) {			
				
				Response rs = (Response) o;
				
				this.hanldeViewHistory(rs);			
			}
			
			else if (o instanceof Response && ((Response) o).getResponse().equals("historyFail")) { // the server does not find any results
								
				historyTableView.setPlaceholder(new Label("No result"));
				
				wineNameHistoryField.clear();
				dateOrder.getEditor().clear();
				dateOrder.setValue(null);
				
				for ( int i = 0; i < historyTableView.getItems().size(); i++) { // cleaning the table view
					historyTableView.getItems().clear();
				}
			        
		        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Search of order not found!"); // pop up
		        alert.setHeaderText("Search failed");
		        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
		        alert.showAndWait();
			}
		} 
        
		catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * This is a service method used to show - load results into notification table view.
	 * 
	 * @param rs It is the server response.
	 */
	
	private void handleViewNotify(final Response rs) {
		
		ObservableList<Order> obList = FXCollections.observableArrayList();
		
		for(Order v: rs.getRespListO()){
			obList.add(v);
		}
						
		notifyID.setCellValueFactory(new PropertyValueFactory<>("idO"));
		notifyWine.setCellValueFactory(new PropertyValueFactory<>("nameW"));
		notifyWProducer.setCellValueFactory(new PropertyValueFactory<>("producerW"));
		notifyWYear.setCellValueFactory(new PropertyValueFactory<>("yearW"));
		notifyBottles.setCellValueFactory(new PropertyValueFactory<>("numBottles"));
		notifyDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		
		notifyTableView.setItems(obList);
		
		wineNameNotifyField.clear();
		dateNotify.getEditor().clear();
		
		dateNotify.setValue(null);
	}
	
	
	/**
	 * 
	 * This method is used to handles the view by search 
	 * (satisfied notification subscribed by customer) button.
	 * When customer clicks on this button, it will be shown
	 * availability notification.
	 * 
	 */
	
	@FXML
	public void handleNotifyViewBySearchButton() {
		
		String wineName = "";
		
		Timestamp dateNot = this.handleDatePicker();
		
		wineName = wineNameNotifyField.getText();
		
		try { // the client tries to connect to the server
			
			Order rq = new Order(this.getCustomer()); // creation of view notification of availability request
			rq.setDate(dateNot);
			rq.setNameW(wineName);
			rq.setRequest("customerViewNotificationBySearch");
			
			this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
			
			this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
			
			Object o = this.is.readObject(); // client waits for a server response
			
			if (o instanceof Response && ((Response) o).getResponse().equals("customerNotifyView")) {
				
				Response rs = (Response) o;
				
				this.handleViewNotify(rs);	
			}
			
			else if (o instanceof Response && ((Response) o).getResponse().equals("notifyFail")) { // the server does not find any results
								
				notifyTableView.setPlaceholder(new Label("No result"));
				
				wineNameNotifyField.clear();
				dateNotify.getEditor().clear();
				dateNotify.setValue(null);
				
				for ( int i = 0; i < notifyTableView.getItems().size(); i++) { // cleaning the table view
					notifyTableView.getItems().clear();
				}
			        
		        Alert alert = new Alert(Alert.AlertType.INFORMATION, "No results on request for notification!"); // pop up
		        alert.setHeaderText("Search failed");
		        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
		        alert.showAndWait();
			}
		} 
        
		catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * This method is used to handles the delete (satisfied notification subscribed by customer) button.
	 * When customer clicks on this button it will be deleted the selected notification.
	 * 
	 */
	
	@FXML
	public void handleDeleteNotifyButton() {
		
		Order rq = notifyTableView.getSelectionModel().getSelectedItem(); // selected item from the table (row).
				
		if(rq == null) { // if customer doesn't select any notification
			
			Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a row of the table!"); // pop up
	        alert.setHeaderText("Selection failed");
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
	        alert.showAndWait(); // it waits for a customer input
		}
		
		if(rq != null) {
			
			// client tries to connects to the server iff (the user has selected a row into the table)
			
			try { // the client tries to connect to the server and it sends a removal notification
				
				rq.setIdC(customer);
				rq.setRequest("customerDeleteNotification");
				
				this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
				
				this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
				
				Object o = this.is.readObject(); // client waits for a server response
				
				if (o instanceof Response && ((Response) o).getResponse().equals("customerDeleteNotification")) { 

					Response rs = (Response) o; 
					
					Alert alert = new Alert(Alert.AlertType.INFORMATION, "Deletion has been successfully completed!"); // pop up
			        alert.setHeaderText("Delete completed");
			        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
			        alert.showAndWait();
			        
			        this.handleViewNotify(rs);
				}
				
				else if(o instanceof Response && ((Response) o).getResponse().equals("failCustomerDeleteNotification")) {
					
					// the last deletion shows that all notifications has been successfully completed
					
					Alert alert = new Alert(Alert.AlertType.INFORMATION, "Deletion has been successfully completed!"); // pop up
			        alert.setHeaderText("Delete completed");
			        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
			        alert.showAndWait();
					
					for ( int i = 0; i < notifyTableView.getItems().size(); i++) { // cleaning the table view
						notifyTableView.getItems().clear();
					}
					
					notifyTableView.setPlaceholder(new Label("No result"));
				}
	
			} 
	        
			catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 
	 * This method is used to handles the account (customer) tab.
	 * When customer selects this tab it will be shown its information
	 * into a table view.
	 * 
	 */
	
	@FXML
	public void handleAccountTab() {
		
		try { // the client tries to connect to the server
			
			People rq = new People(); // creation of a view customer data request
			rq.setId(this.customer);
			rq.setRequest("customerViewData");
			
			this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
			
			this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
						
			Object o = this.is.readObject(); // client waits for a server response
			
			if (o instanceof Response && ((Response) o).getResponse().equals("customerViewData")) {
			
				Response rs = (Response) o;
				
				ObservableList<People> obList = FXCollections.observableArrayList();
				
				for(People v: rs.getRespListP()) {		
					obList.add(v);
					this.setOldPass(v.getPassword());
				}
				
				// showing results into table
				
				custName.setCellValueFactory(new PropertyValueFactory<>("name"));
				custSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
				custEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
				custAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
				
				accountTableView.setItems(obList);
			}
		} 
        
		catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	

	/**
	 * 
	 * This method is used to handles the update (customer data stored into database) button.
	 * When customer clicks on this button it will be updated fields that has been inserted
	 * by confirming with the current password.
	 * 
	 */
	
	@FXML
	public void handleCustomerUpdateButton() {
		
		String name = "";
		String surname = "";
		String email = "";
		String address = "";
		String password = "";
		String oldPassword = "";
		
		name = newName.getText();
		surname = newSurname.getText();
		email = newEmail.getText();
		password = newPass.getText();	
		oldPassword = oldPass.getText();
		address = newAddress.getText();
		
		// you can't contacts the server without entering at least one field.
		
		if(name.isBlank() && surname.isBlank() && address.isBlank() && password.isBlank()) { 
			
			Alert alert = new Alert(Alert.AlertType.ERROR, "Enter at least data in one field!"); // pop up
	        alert.setHeaderText("Update failed");
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
	        alert.showAndWait(); // it waits for a customer input
		}
		
		if (!email.isBlank()) { // if email is not empty it has to be validate
			
			if (!validateEmail(email)) {
				
				Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong format of the email"); // it is a pop-up to warn the user that has entered an invalid password 
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
				alert.showAndWait();
			} 
		}
			
		if (!password.isBlank())  { // if email is not empty it has to be validate
			
			if (!validatePassword(password)) {
				Alert alert = new Alert(Alert.AlertType.ERROR, 
						"Password must contains at least 2 non-alphabetic characters\n"
								+ "Password must contains a number of characters between a minimum of 8 and a maximum of 15"); // it is a pop-up to warn the user that has entered an invalid password 
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
				alert.showAndWait(); // alert waits for a user input
			} 
		}
		
		if(oldPassword.isBlank()) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR, "You have to insert password to confirm changes!"); // pop up
	        alert.setHeaderText("Update failed");
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
	        alert.showAndWait(); // it waits for a customer input
		}
			
		// client tries to connects to the server iff (the user has entered data at least in a one of fields AND email is valid AND password is valid)

		if((!name.isBlank() || !surname.isBlank() || !address.isBlank() || validateEmail(email) || validatePassword(password)) && this.getOldPass().equals(oldPassword)) { // if client entered email and policy is selected
			
			try { // The client tries to connect to the server and it sends a request for registration
				
				People rq =  new People(customer, name, surname, email, password, address);
				rq.setRequest("customerUpdateCustomer");
				
				this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
				
				this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
				
				Object o = this.is.readObject(); // client waits for a server response
				
				if (o instanceof Response && ((Response) o).getResponse().equals("customerUpdateCustomer")) { 
					
					Response rs = (Response) o;
					
					if(rs.getResp() > 0) { // server sent ok
						
						newName.clear();
						newSurname.clear();
						newEmail.clear();  
						newAddress.clear();
						newPass.clear();
						oldPass.clear();

						Alert alert = new Alert(Alert.AlertType.INFORMATION, "Update completed!"); // pop up
				        alert.setHeaderText("Update completed");
				        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
				        alert.showAndWait();
						
						this.handleAccountTab();	
					}
				}
			} 
	        
			catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	/**
	 * This method is used to handles the logout button event.
	 * When a client clicks on logout he tries to send a request 
     * for closing connection to the server.
	 * 
	 * @param event The event.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	
	
	@FXML
	public void handleHomeButton(ActionEvent event) throws IOException { // logout
		
		// client sends a request for closing socket to server thread
	    
	    try { // the client tries to connect to the server
			
			RequestClose rq = new RequestClose(true); 
			
			this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
			
			this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
						
			Object o = this.is.readObject(); // client waits for a server response
			
			if (o instanceof Response && ((Response) o).getResponse().equals("close")) { // if the server finds the wine the user is looking for
			
				Response rs = (Response) o;
				
				if(rs.getResp() > 0) {
					this.client.close();
				}
			}
		} 
        
		catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	    
	    // changing the scene
		
		FXMLLoader loader= new FXMLLoader(getClass().getResource("../FXMLs/Login.fxml")); // fxml file charging
		
		Parent root = loader.load(); // charging fxml file into the Parent
		
		LoginController controller = loader.getController(); // loading controller to pass information

		controller.transferRole("customer"); // this instruction is used to transfer the role to the next window

		// creating login window
	    
	    Scene loginScene = new Scene(root); // creates a Scene for a specific root Node
	    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow(); // replaces the current scene with another scene in the same window
	    window.setScene(loginScene); // specify the scene to be used on this stage
	    window.setTitle("Login"); // set the title of the window
	    window.show();
	}
	
	
	/**
	 * This method is used to transfer informations from LoginController
	 * to CustomerDashboardController.
	 * 
	 * @param c - It is the customer id.
	 */
	
	public void transferCustomer(final int c) { this.setCustomer(c); }
	
	
	/**
	 * This method is used to get the customer id.
	 * 
	 * @return It returns the customer user name.
	 */
	
	private int getCustomer() { return customer; }
	
	
	/**
	 * This method is used to set the customer user name.
	 * 
	 * @param c - It is the id to set.
	 */

	private void setCustomer(int c) { this.customer = c; }
	
	
	/**
	 * This method is used to get the client socket.
	 * 
	 * @return It returns the client socket.
	 */

	public Socket getClient() { return client; }
	
	
	/**
	 * This method is used to set the client socket.
	 * 
	 * @param client It is the socket to set.
	 */

	private void setClient(Socket client) { this.client = client; }
	
	
	/**
	 * This method is used to get the client object output stream.
	 * 
	 * @return It returns the client object output stream.
	 */

	public ObjectOutputStream getOs() { return os; }
	
	
	/**
	 * This method is used to set the client object output stream.
	 * 
	 * @param os It is the object output stream to set.
	 */

	private void setOs(ObjectOutputStream os) { this.os = os; }
	
	
	/**
	 * This method is used to get the client object input stream.
	 * 
	 * @return It returns the client object input stream.
	 */

	public ObjectInputStream getIs() { return this.is; }
	
	
	/**
	 * This method is used to set the client object input stream.
	 * 
	 * @param is It is the object input stream to set.
	 */

	private void setIs(ObjectInputStream is) { this.is = is; }
	
	
	/**
	 * This method is used to get the current password.
	 * 
	 * @return It returns the current password.
	 */
	
	private String getOldPass() { return this.oldPassword; }
	
	
	/**
	 * This method is used to set the current password.
	 * 
	 * @param pass It is the current password.
	 */
	
	private void setOldPass(final String pass) { this.oldPassword = pass; }
}
