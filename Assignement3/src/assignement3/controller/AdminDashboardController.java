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

import assignement3.communication.Customer;
import assignement3.communication.Employee;
import assignement3.communication.Order;
import assignement3.communication.People;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * 
 * The class {@code AdminDashboardController}  is used to manage
 * actions of AdminDashboardController.fxml.
 *
 */

public class AdminDashboardController {
	
	/**
	 * Class fields.
	 * 
	 * client - It is the socket used by client to communicate with the server.
	 * os - It is the object output stream used by client to sends messages to the server.
	 * is - It is the object input stream used by client to receives messages from the server.
	 * 
	 * usernameLabel - It is the name of the administrator logged in.
	 * 
	 * adminTabPane - It is the tab container of administrator dashboard.
	 * 
	 * wineTab - It is the tab of wine stored into the system.
	 * 
	 * orderTab - It is the tab of orders stored into the system.
	 * 
	 * employeeTab - it is the tab of employees stored into the system.
	 * 
	 * customerTab - It is the tab of customers stored into the system.
	 * 
	 * customerTableView - It is the table that will contains customers.
	 * 
	 * custId - It is the column that will contains customers id.
	 * custName - It is the column that will contains customers name.
	 * custSurname - It is the column that will contains customers surname.
	 * custEmail - It is the column that will contains customers email.
	 * custPass - It is the column that will contains customers password.
	 * custAddr - It is the column that will contains customer address.
	 * 
	 * newCustName - It is the new name of customers inserted by administrator.
	 * newCustSurname - It is the new surname of customers inserted by administrator.
	 * newCustEmail - It is the new email of customers inserted by administrator.
	 * newCustPass - It is the new password of customers inserted by administrator.
	 * newCustAddr - It is the new address of customers inserted by administrator.
	 * 
	 * deleteCustomerButton - It is a button used by administrator to delete customers.
	 * updateCustomerButton - It is a button used by administrator to update customers data.
	 * 
	 * invalidNameC - It is a label that appears if administrator doesn't insert customer name.
	 * invalidSurnameC - It is a label that appears if administrator doesn't insert customer surname.
	 * invalidEmailC - It is a label that appears if administrator doesn't insert customer email.
	 * invalidPasswordC - It is a label that appears if administrator doesn't insert customer password.
	 * invalidAddressC - It is a label that appears if administrator doesn't insert customer address.
	 * 
	 * employeeTableView - It is the table that will contains employees.
	 *  
	 * empId - It is the column that will contains employees id.
	 * empName - It is the column that will contains employees name.
	 * empSurname - It is the column that will contains employees surname.
	 * empEmail - It is the column that will contains employees email.
	 * empPass - It is the column that will contains employees password.
	 * 
	 * newEmpName - It is the new name of employees inserted by administrator.
	 * newEmpSurname - It is the new surname of employees inserted by administrator.
	 * newEmpEmail - It is the new email of employees inserted by administrator.
	 * newEmpPass - It is the new password of employees inserted by administrator.
	 * 
	 * deleteEmployeeButton - It is a button used by administrator to delete employees.
	 * updateEmployeeButton - It is a button used by administrator to update employees data / assign credentials.
	 * 
	 * invalidNameE - It is a label that appears if administrator doesn't insert employee name. 
	 * invalidSurnameE - It is a label that appears if administrator doesn't insert employee name. 
	 * invalidEmailE - It is a label that appears if administrator doesn't insert employee name. 
	 * invalidPasswordE - It is a label that appears if administrator doesn't insert employee name. 
	 * invalidAddressE - It is a label that appears if administrator doesn't insert employee name. 
	 * 
	 * orderTableView - It is the table that will contains orders.
	 * 
	 * ordID - It is the column that will contains order id.
	 * emailC - It is the column that will contains customer email.
	 * ordWine - It is the column that will contains wines name.
	 * ordWProducer - It is the column that will contains wines producer.
	 * ordWYear - It is the column that will contains wines year.
	 * ordBottles - It is the column that  will contains wines number of bottles.
	 * ordCompleted - It is the column that will contains a flag that indicates if the order is completed.
	 * ordDate - It is the column that will contains orders date.
	 * ordTot - It is the column that will contains the orders total price.
	 * 
	 * customerID - It is the id of customer.
	 * 
	 * dateOrder - It is the date of order selected by data picker.
	 * 
	 * wineTableView - It is the table that will contains wines.
	 * 
	 * wineID - It is the column that will contains wines id.
	 * wineName - It is the column that will contains wines name.
	 * wineProducer - It is the column that will contains wines producer.
	 * wineYear - It is the column that will contains wines year.
	 * wineTn - It is the column that will contains wines technical notes.
	 * wineVines - It is the column that will contains wines vine.
	 * wineBottle - It is the column that will contains wines number of bottles.
	 * winePrice - It is the column that will contains wines price.
	 * 
	 * newWineName - It is the new name of wines inserted by administrator.
     * newWineProducer - It is the new producer of wines inserted by administrator.
     * newWineYear - It is the new year of wines inserted by administrator.
     * newWineTN - It is the new technical notes of wines inserted by administrator.
 	 * newWineVines - It is the new vines of wines inserted by administrator.
 	 * newWineNB - It is the new number of bottles of wines inserted by administrator.
 	 * newWinePrice - It is the new price of wines inserted by administrator.
	 * 
	 * VALID_PASSWORD_REGEX - It is a regular expression used to validate password format.
	 * VALID_EMAIL_REGEX - It is a regular expression used to validate email format.
	 * VALID_PRICE_REGEX - It is a regular expression used to validate wine price format.
	 * VALID_BOTTLES_REGEX - It is a regular expression used to validate number of wine bottles format.
	 * VALID YEAR REGEX - It is a regular expression used to validate year format.
	 */
	
	private Socket client;
	private ObjectOutputStream os;
	private ObjectInputStream is;
	
	@FXML private Label usernameLabel;
	
	@FXML private TabPane adminTabPane;
	@FXML private Tab wineTab;
	@FXML private Tab orderTab;
	@FXML private Tab employeeTab;
	@FXML private Tab customerTab;
	
	@FXML private TableView<People> customerTableView;
	
	@FXML private TableColumn<People, Integer> custId;
	@FXML private TableColumn<People, String> custName;
	@FXML private TableColumn<People, String> custSurname;
	@FXML private TableColumn<People, String> custEmail;
	@FXML private TableColumn<People, String> custPass;
	@FXML private TableColumn<People, String> custAddr;
	
	@FXML private TextField newCustName;
	@FXML private TextField newCustSurname;
	@FXML private TextField newCustEmail;
	@FXML private TextField newCustPass;
	@FXML private TextField newCustAddr;
	
	@FXML private Button deleteCustomerButton;
	@FXML private Button updateCustomerButton;
	
	@FXML private Label invalidNameC;
	@FXML private Label invalidSurnameC;
	@FXML private Label invalidEmailC;
	@FXML private Label invalidPasswordC;
	@FXML private Label invalidAddressC;
	
	@FXML private TableView<People> employeeTableView;
	
	@FXML private TableColumn<People, Integer> empId;
	@FXML private TableColumn<People, String> empName;
	@FXML private TableColumn<People, String> empSurname;
	@FXML private TableColumn<People, String> empEmail;
	@FXML private TableColumn<People, String> empPass;
	
	@FXML private TextField newEmpName;
	@FXML private TextField newEmpSurname;
	@FXML private TextField newEmpEmail;
	@FXML private TextField newEmpPass;
	
	@FXML private Button deleteEmployeeButton;
	@FXML private Button updateEmployeeButton;
	
	@FXML private Label invalidNameE;
	@FXML private Label invalidSurnameE;
	@FXML private Label invalidEmailE;
	@FXML private Label invalidPasswordE;
	
	@FXML private TableView<Order> orderTableView;
	
	@FXML private TableColumn<Order, Integer> ordID;
	@FXML private TableColumn<Order, String> emailC;
	@FXML private TableColumn<Order, String> ordWine;
	@FXML private TableColumn<Order, String> ordWProducer;
	@FXML private TableColumn<Order, Date> ordWYear;
	@FXML private TableColumn<Order, Integer> ordBottles;
	@FXML private TableColumn<Order, Boolean> ordCompleted;
	@FXML private TableColumn<Order, Date> ordDate;
	@FXML private TableColumn<Order, Float> ordTot;
	
	@FXML private TextField customerID;
	
	@FXML private DatePicker dateOrder;	
	
	@FXML private TableView<Wine> wineTableView;
	
	@FXML private TableColumn<Wine, Integer> wineID;
	@FXML private TableColumn<Wine, String> wineName;
	@FXML private TableColumn<Wine, String> wineProducer;
	@FXML private TableColumn<Wine, Date> wineYear;
	@FXML private TableColumn<Wine, String> wineTn;
	@FXML private TableColumn<Wine, String> wineVines;
	@FXML private TableColumn<Wine, Integer> wineBottle;
	@FXML private TableColumn<Wine, Float> winePrice;
	
	@FXML private TextField newWineName;
	@FXML private TextField newWineProducer;
	@FXML private TextField newWineYear;
	@FXML private TextArea newWineTN;
	@FXML private TextField newWineVines;
	@FXML private TextField newWineNB;
	@FXML private TextField newWinePrice;

	
	private static final String VALID_PASSWORD_REGEX = "(?=^.{8,}$)(?=(.*[^A-Za-z]){2,})^.*";
	private static final String VALID_EMAIL_REGEX = "^(.+)@(.+)$";
	private static final String VALID_PRICE_REGEX = "^\s*(?=.*[1-9])[0-9]*(?:[.][0-9]{1,2})?\s*$";
	private static final String VALID_BOTTLES_REGEX = "^[1-9][0-9]*$";
	private static final String VALID_YEAR_REGEX = "(19[6789][0-9]|20[01][0-9]|2020|2021)"; //dal 1960 al 2021
	
	
	/**
	 * This is a service method used to validate the password inserted by client.
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
     * This is a service method used to validate the email inserted by client.
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
	 * This is a service method used to validate the price inserted by client.
	 * 
	 * @param price It is the price inserted by the client into the field.
	 * @return It returns a boolean value (true = valid price | false = not valid price).
	 */

    private static boolean validatePrice(String price) {
       
    	String pattern = VALID_PRICE_REGEX;

        if (price.matches(pattern)) {
            return true;
        } 
        
        else {
            return false;
        }
    }
    
    
    /**
	 * This is a service method used to validate the bottles inserted by client.
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
	 * This is a service method used to validate the year inserted by client.
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
	 * to AdministratorDashboardController.
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
	 * This method is used to initialize the administrator dashboard.
	 * It makes appear the user name into the dashboard.
	 * 
	 * @param n - It is the administrator user name.
	 */
	
	public void initialize(final String n) {
		
		usernameLabel.setText(n.split("@")[0]); // setting the user name near "Welcome" text
	}
		
	
	/**
	 * 
	 * This is a service method used to handles date picker.  	
	 *
	 */
	
	@FXML  
	private Timestamp handleDatePicker() {
		
		if(dateOrder.getValue() != null) {
			LocalDate date = dateOrder.getValue();
			LocalTime time = LocalTime.now();
			LocalDateTime datetime = LocalDateTime.of(date, time);
			return Timestamp.valueOf(datetime);
		}
		
		return null;
	}
	
	
	/**
	 * This is a service method used to show - load results into orders table view.
	 * 
	 * @param rs It is the server response.
	 */
	
	private void handleViewOrders(final Response rs) {
		
		ObservableList<Order> obList = FXCollections.observableArrayList();
		
		for(Order v: rs.getRespListO()) { // loading orders into ObservableList
			
			if(v.getCompleted() == 0) v.setStringCompleted("not shipped");
			else v.setStringCompleted("shipped");
			
			obList.add(v);
		}
		
		ordID.setCellValueFactory(new PropertyValueFactory<>("idO"));
		emailC.setCellValueFactory(new PropertyValueFactory<>("emailC"));
		ordWine.setCellValueFactory(new PropertyValueFactory<>("nameW"));
		ordWProducer.setCellValueFactory(new PropertyValueFactory<>("producerW"));
		ordWYear.setCellValueFactory(new PropertyValueFactory<>("yearW"));
		ordBottles.setCellValueFactory(new PropertyValueFactory<>("numBottles"));
		ordCompleted.setCellValueFactory(new PropertyValueFactory<>("stringCompleted"));
		ordDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		ordTot.setCellValueFactory(new PropertyValueFactory<>("tot"));
		
		orderTableView.setItems(obList);
		
		customerID.clear(); // clearing the id text field
		
		dateOrder.getEditor().clear(); // clearing the date picker
		
		dateOrder.setValue(null); 
	}
	
	
	/**
	 * 
	 * This method is used to handles the view by search orders history (administrator) button.
	 * When administrator clicks on this button, it will be show results of all orders history
	 * of all customers.
	 * 
	 */
	
	
	@FXML
	public void handleViewBySearchButton() {

		int custSelected = -1;
		
		Timestamp dateNot = this.handleDatePicker();
		
		String custSelectedStr = customerID.getText();
		
		if(!customerID.getText().isBlank() && customerID.getText().matches("^[1-9][0-9]*$")) { // this checks avoid parsing (string to integer) error
			
			custSelected = Integer.parseInt(custSelectedStr);
		}
			
		try { // the client tries to connect to the server

			Order rq = new Order();
			rq.setDate(dateNot);
			rq.setIdC(custSelected);
			rq.setRequest("adminViewBySearch"); // setting the type of request
			
			this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
			
			this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
			
			Object o = this.is.readObject(); // client waits for a server response
			
			if (o instanceof Response && ((Response) o).getResponse().equals("adminEmployeeViewBySearch")) {			
				
				Response rs = (Response) o;
				
				this.handleViewOrders(rs); // loading results into table view
			}
			
			else if ((o instanceof Response && ((Response) o).getResponse().equals("failAdminEmployeeViewBySearch"))) { // the server does not find any results
								
				orderTableView.setPlaceholder(new Label("No result"));
				
				customerID.clear();
				dateOrder.getEditor().clear();
				dateOrder.setValue(null);
				
				for ( int i = 0; i < orderTableView.getItems().size(); i++) { // cleaning the table view
					
					orderTableView.getItems().clear();
				}
			        
		        Alert alert = new Alert(Alert.AlertType.INFORMATION, "No results on request for delivery!"); // pop up
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
	 * This method is used to handles the customers tab.
	 * When administrator select this tab, it will be show all customers
	 * and their data.
	 *
	 */
	
	
	@FXML
	public void handleCustomerTab() {
		
		invalidNameC.setVisible(false);
		invalidSurnameC.setVisible(false);
		invalidEmailC.setVisible(false);
		invalidPasswordC.setVisible(false);
		invalidAddressC.setVisible(false);
		
		try { // the client tries to connect to the server
			
			Customer rq = new Customer(); // creation of a view customer request
			rq.setRequest("adminViewCustomer");
			
			this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
			
			this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
			
			Object o = this.is.readObject(); // client waits for a server response
			
			if (o instanceof Response && ((Response) o).getResponse().equals("viewPeople") ) { // if the server finds the customers the user is looking for
				
				// loading results into table view
		
				ObservableList<People> obList = FXCollections.observableArrayList();
				
				Response rs = (Response) o;
				
				for(People v: rs.getRespListP()) {
					obList.add(v);
				}
								
				custId.setCellValueFactory(new PropertyValueFactory<>("id"));
				custName.setCellValueFactory(new PropertyValueFactory<>("name"));
				custSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
				custEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
				custPass.setCellValueFactory(new PropertyValueFactory<>("password"));
				custAddr.setCellValueFactory(new PropertyValueFactory<>("address"));
				
				customerTableView.setItems(obList);
			}
			
			else if (o instanceof Response && ((Response) o).getResponse().equals("failViewPeople")) { // the server does not find any results
				customerTableView.setPlaceholder(new Label("No customers to show"));
			}
		} 
        
		catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * This method is used to handles the employee tab.
	 * When administrator select this tab, it will be show all employees
	 * and their data.
	 * 
	 */
	
	
	@FXML
	public void handleEmployeeTab() {
		
		invalidNameE.setVisible(false);
		invalidSurnameE.setVisible(false);
		invalidEmailE.setVisible(false);
		invalidPasswordE.setVisible(false);
		
		try { // the client tries to connect to the server
			
			Employee rq = new Employee(); // creation of a view employees request
			rq.setRequest("adminViewEmployee");
			
			this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
			
			this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
			
			Object o = this.is.readObject(); // client waits for a server response
			
			if (o instanceof Response && ((Response) o).getResponse().equals("viewPeople") ) { // if the server finds the employees the user is looking for
				
				// loading results into table view
		
				ObservableList<People> obList = FXCollections.observableArrayList();
				
				Response rs = (Response) o;
				
				for(People v: rs.getRespListP()) {
					obList.add(v);
				}
								
				empId.setCellValueFactory(new PropertyValueFactory<>("id"));
				empName.setCellValueFactory(new PropertyValueFactory<>("name"));
				empSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
				empEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
				empPass.setCellValueFactory(new PropertyValueFactory<>("password"));
				
				employeeTableView.setItems(obList);
			}
			
			else if (o instanceof Response && ((Response) o).getResponse().equals("failViewPeople")) { // the server does not find any results
				
				employeeTableView.setPlaceholder(new Label("No employees to show"));
			}
		} 
        
		catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * This method is used to handles the add (administrator) buttons.
	 * It is used to add employee or customer into the system.
	 * It is used to assign credential to the employees.
	 * When administrator clicks on this button, it will be insert
	 * the person into the system database.
	 * 
	 */
	
	
	@FXML
	public void handleAddPeopleButton() {
		
		String name = "";
		String surname = "";
		String email = "";
		String password = "";
		String address = "";
		
		Label invalidName = null;
		Label invalidSurname = null;
		Label invalidEmail = null;
		Label invalidPassword = null;
		Label invalidAddress = null;
		
		if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Customers")) { // if the tab selected is customers 
			
			// gets entered data from text fields
			
			name = newCustName.getText();
			surname = newCustSurname.getText();
			email = newCustEmail.getText();
			password = newCustPass.getText();
			address = newCustAddr.getText();
			
			invalidName = invalidNameC;
			invalidSurname = invalidSurnameC;
			invalidEmail = invalidEmailC;
			invalidPassword = invalidPasswordC;
			invalidAddress = invalidAddressC;
		}
		 
		else if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Employees")) { // if the tab selected is employees 
			
			// gets entered data from text fields
			
			name = newEmpName.getText();
			surname = newEmpSurname.getText();
			email = newEmpEmail.getText();
			password = newEmpPass.getText();
			
			invalidName = invalidNameE;
			invalidSurname = invalidSurnameE;
			invalidEmail = invalidEmailE;
			invalidPassword = invalidPasswordE;
		}		
		 
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
		
		if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Customers")) {
			
			if(address.isBlank()) {
				invalidAddress.setVisible(true);
			}
			
			else {
				invalidAddress.setVisible(false);
			}
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
		
		else { // if password is not empty it has to be validate
			
			if (!validatePassword(password)) {
				
				invalidPassword.setText("Invalid Password");
				invalidPassword.setVisible(true);
				Alert alert = new Alert(Alert.AlertType.ERROR, 
						"Password must contains at least 2 non-alphabetic characters\n"
								+ "Password must contains a number of characters between a minimum of 8 and a maximum of 15"); // it is a pop-up to warn the user that has entered an invalid password 
				
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
				
				alert.showAndWait(); // alert waits for a user input
			} 
			
			else {
				invalidPassword.setVisible(false);
			}
		}
		
		boolean conditionPeople; // this check is inserted because employees and customers have different attributes, so the conditions to be met are different
		
		if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Customers")) {
			conditionPeople = !name.isBlank() && !surname.isBlank() && validateEmail(email) && validatePassword(password) && !address.isBlank();
		}
		
		else {
			conditionPeople = !name.isBlank() && !surname.isBlank() && validateEmail(email) && validatePassword(password);
		}
		
		// client tries to connects to the server iff ( the user has entered data in all fields AND email is valid AND password is valid)

		if(conditionPeople) { 
			
			try { // The client tries to connect to the server and it sends a request for registration
					
				People rq = null;
				
				if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Customers")) {
					rq = new People(name, surname, email, password, address); // creation of a registration request
					rq.setRequest("adminAddCustomer");
				}
				
				else if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Employees")) {
					rq = new People(name, surname, email, password, null); // creation of a registration request
					rq.setRequest("adminAddEmployee");
				}
				
				this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
				
				this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
				
				Object o = this.is.readObject(); // client waits for a server response
				
				if (o instanceof Response && ((Response) o).getResponse().equals("adminAddPeople")) { 

					Response rs = (Response) o; 
					
					if(rs.getResp() > 0) {
						
						Alert alert = new Alert(Alert.AlertType.INFORMATION, "Insert has been successfully completed!"); // pop up
				        alert.setHeaderText("Insert completed");
				        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
				        alert.showAndWait();
						
						if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Customers")) {
							
							// clearing text fields
							
							newCustName.clear();
							newCustSurname.clear();
							newCustEmail.clear();
							newCustPass.clear();
							newCustAddr.clear();
							
							this.handleCustomerTab();
						}
						 
						else if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Employees")) {
							
							// clearing text fields
							
							newEmpName.clear();
							newEmpSurname.clear();
							newEmpEmail.clear();
							newEmpPass.clear();
							
							this.handleEmployeeTab();
						}
					}
					
					else {
						
						Alert alertFail = new Alert(Alert.AlertType.ERROR, "The item with this email is already registered!"); // pop-up
						alertFail.setHeaderText("Registration failed");
						Stage stage = (Stage) alertFail.getDialogPane().getScene().getWindow();
				        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
						alertFail.showAndWait();
					}
				}
			} 
	        
			catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 
	 * This method is used to handles the delete (administrator) buttons.
	 * When administrator clicks on this button, will be
	 * removed the person selected and its data from the system database.
	 * 
	 */
	
	
	@FXML
	public void handleDeletePeopleButton() {
		
		People rq = null;
		
		if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Customers")) {
			rq = customerTableView.getSelectionModel().getSelectedItem(); // it is the selected customer by administrator inside table
		}
		 
		else if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Employees")) {
			rq = employeeTableView.getSelectionModel().getSelectedItem(); // it is the selected employee by administrator inside table
		}
		
		if(rq == null) { // if customer doesn't select any person
			
			Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a row of the table!"); // pop up
	        alert.setHeaderText("Selection failed");
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
	        alert.showAndWait(); // it waits for a customer input
		}
		
		if(rq != null) {
			
			// client tries to connects to the server iff ( the user has selected a row into the table)
			
			try { // The client tries to connect to the server and it sends a removal request
				
				if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Customers")) {
					rq.setRequest("adminDeleteCustomer");
				}
				
				else if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Employees")) {
					rq.setRequest("adminDeleteEmployee");
				}
				
				this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
				
				this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
				
				Object o = this.is.readObject(); // client waits for a server response
				
				if (o instanceof Response && ((Response) o).getResponse().equals("adminDeletePeople")) { 

					Response rs = (Response) o;
					
					if(rs.getResp() > 0) {
						
						Alert alert = new Alert(Alert.AlertType.INFORMATION, "Deletion has been successfully completed!"); // pop up
				        alert.setHeaderText("Delete completed");
				        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
				        alert.showAndWait();
				        
				        if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Customers")) {
				        	
				        	// showing the remaining customers into the table
							
				        	this.handleCustomerTab();
						}
						 
						else if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Employees")) {
							
							// showing the remaining employees into the table
							
							this.handleEmployeeTab();
						}
					}
					
					else {
						
						if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Customers")) {	
			        		
				        	customerTableView.setPlaceholder(new Label("No customers to show"));
						}
						 
						else if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Employees")) {
						
							employeeTableView.setPlaceholder(new Label("No employees to show"));
						}
					}
				}
			} 
	        
			catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 
	 * This method is used to handles the update person (administrator) button.
	 * When administrator clicks on this button, it will be updated
	 * the field/fields of the person selected.
	 * 
	 */
	
	@FXML
	public void handleUpdatePeopleButton() {
		
		People rq = null; 
		
		if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Customers")) {
			rq = customerTableView.getSelectionModel().getSelectedItem(); // it is the selected customer by administrator inside table
		}
		 
		else if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Employees")) {
			rq = employeeTableView.getSelectionModel().getSelectedItem(); // it is the selected employee by administrator inside table
		}
		
		if(rq == null) { // if administrator doesn't select any person
			
			Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a row of the table!"); // pop up
	        alert.setHeaderText("Selection failed");
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
	        alert.showAndWait(); // it waits for a administrator input
		}
		
		if(rq != null) {
		
			String name = null;
			String surname = null;
			String email = null;
			String password = null;
			String address = null;
			
			if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Customers")) {
				
				// getting results from text fields
				
				name = newCustName.getText();
				surname = newCustSurname.getText();
				email = newCustEmail.getText();
				password = newCustPass.getText();	
				address = newCustAddr.getText();
			}
			 
			else if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Employees")) {
				
				// getting results from text fields
				
				name = newEmpName.getText();
				surname = newEmpSurname.getText();
				email = newEmpEmail.getText();
				password = newEmpPass.getText();
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
			
			boolean conditionPeople; // this check is inserted because employees and customers have different attributes, so the conditions to be met are different
			
			if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Customers")) {
				conditionPeople = name.isBlank() && surname.isBlank() && email.isBlank() && password.isBlank() && address.isBlank();
			}
			
			else {
				conditionPeople = name.isBlank() && surname.isBlank() && email.isBlank() && password.isBlank();
			}
			
			if(conditionPeople) {
				
				Alert alert = new Alert(Alert.AlertType.ERROR, "Update failed, you must inser at least one field!"); // pop up
		        alert.setHeaderText("Update failed");
		        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
		        alert.showAndWait();
			}
			
			boolean conditionPeopleOr; // this check is inserted because employees and customers have different attributes, so the conditions to be met are different
			
			if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Customers")) {
				conditionPeopleOr = !name.isBlank() || !surname.isBlank() || !address.isBlank() || validateEmail(email) || validatePassword(password);
			}
			
			else {
				conditionPeopleOr = !name.isBlank() || !surname.isBlank() || validateEmail(email) || validatePassword(password);
			}
			
			// client tries to connects to the server iff (the user has entered data at least in a one of fields AND email is valid AND password is valid)
	
			if(conditionPeopleOr) { // if client entered email and policy is selected
				
				try { // The client tries to connect to the server and it sends a request for update
					
					People rq_tmp = null;
					
					if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Customers")) {
						rq_tmp = new People(rq.getId(), name, surname, email, password, address);
						rq_tmp.setRequest("adminUpdateCustomer");
					}
					
					else if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Employees")) {
						rq_tmp = new People(rq.getId(), name, surname, email, password, null);
						rq_tmp.setRequest("adminUpdateEmployee");
					}
					
					this.os.writeObject(rq_tmp); // writing object into the object output stream and sending request to the server
					
					this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
					
					Object o = this.is.readObject(); // client waits for a server response
					
					if (o instanceof Response && ((Response) o).getResponse().equals("adminUpdatePeople")) { 
	
						Response rs = (Response) o; 
						
						if(rs.getResp() > 0) {
							
							Alert alert = new Alert(Alert.AlertType.INFORMATION, "Update has been successfully completed!"); // pop up
					        alert.setHeaderText("Update completed");
					        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
					        alert.showAndWait();
					       				        
					        if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Customers")) {
								
					        	// clearing text fields
					        	
					        	newCustName.clear();
								newCustSurname.clear();
								newCustEmail.clear();
								newCustPass.clear();
								newCustAddr.clear();
								
								this.handleCustomerTab(); //showing result of the customer to administrator
							}
							 
							else if(adminTabPane.getSelectionModel().getSelectedItem().getText().equals("Employees")) {
								
								// clearing text fields
								
								newEmpName.clear();
								newEmpSurname.clear();
								newEmpEmail.clear();
								newEmpPass.clear();
								
								this.handleEmployeeTab(); //showing result of the employee to administrator
							}
						}
						
						else {
							
							Alert alert = new Alert(Alert.AlertType.ERROR, "The item with this email is already registered!"); // pop-up
					        alert.setHeaderText("Update failed");
					        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
					        alert.showAndWait();
						}
					}
				} 
		        
				catch (IOException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	

	/**
	 *
	 * This method is used to handles the wine tab.
	 * When administrator selects this tab it will be 
	 * loaded wines and their data into wine table view.
	 *
	 */
	
	
	@FXML
	public void handleWineTab(){

		try { // the client tries to connect to the server
			
			Wine rq = new Wine(); // creation of a view wine request
			rq.setRequest("adminViewWine");
			
			this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
			
			this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
			
			Object o = this.is.readObject(); // client waits for a server response
			
			if (o instanceof Response && ((Response) o).getResponse().equals("search") ) { // if the server finds the wine the user is looking for
		
				ObservableList<Wine> obList = FXCollections.observableArrayList();
				
				Response rs = (Response) o;
				
				// loading results into wine table view
				
				for(Wine v: rs.getRespListS()) {
					obList.add(v);
				}
				
				wineID.setCellValueFactory(new PropertyValueFactory<>("id"));
				wineName.setCellValueFactory(new PropertyValueFactory<>("name"));
				wineProducer.setCellValueFactory(new PropertyValueFactory<>("producer"));
				wineYear.setCellValueFactory(new PropertyValueFactory<>("year"));
				wineTn.setCellValueFactory(new PropertyValueFactory<>("technicalNotes"));
				wineVines.setCellValueFactory(new PropertyValueFactory<>("vines"));
				wineBottle.setCellValueFactory(new PropertyValueFactory<>("numBottles"));
				winePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
				
				wineTableView.setItems(obList);
			}
			
			else if (o instanceof Response && ((Response) o).getResponse().equals("searchFail")) { // the server does not find any results
				
				wineTableView.setPlaceholder(new Label("No wine to show"));
			}
		} 
        
		catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * This method is used to handles the add wine (administrator) button.
	 * When administrator clicks on this button, it will be added
	 * the wine and its data into system database.
	 * 
	 */
	
	
	@FXML
	public void handleAddWineButton() {
		
		// getting data from text fields
		
		String newNameW = newWineName.getText();
		String newProducerW = newWineProducer.getText();
		String newYearW = newWineYear.getText();
		String newTnW = newWineTN.getText();
		String newVinesW = newWineVines.getText();
		String newNbW = newWineNB.getText();
		String newPriceW = newWinePrice.getText();	
		
		// it checks that all fields has been compiled

		if(newNameW.isBlank() || newProducerW.isBlank() || newYearW.isBlank() || newTnW.isBlank() || newVinesW.isBlank() || newProducerW.isBlank() || newNbW.isBlank() || newPriceW.isBlank()) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR, "You have to enter data into all fields!"); // pop up
	        alert.setHeaderText("Insert failed");
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
	        alert.showAndWait();
		}
		
		// it checks that valid parameters has been entered in numeric fields
		
		if(!newNbW.isBlank() && !newPriceW.isBlank() && !newYearW.isBlank()) {
																		
			if(!validateBottles(newNbW)|| !validatePrice(newPriceW) || !validateYear(newYearW)) {  // sistemare anno solo 4 cifre ???
				
				Alert alert = new Alert(Alert.AlertType.ERROR, "You have to enter valid data into numeric fields!"); // pop up
		        alert.setHeaderText("Insert failed");
		        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
		        alert.showAndWait();
			}
		}
		
		// client tries to connects to the server iff (the user has entered data in all fields AND year is valid AND number of bottles is valid AND price is valid)

		if(!newNameW.isBlank() && !newProducerW.isBlank() && !newYearW.isBlank() && !newTnW.isBlank() && !newVinesW.isBlank() && !newProducerW.isBlank() && !newNbW.isBlank() && !newPriceW.isBlank() && validateBottles(newNbW) && validatePrice(newPriceW) && validateYear(newYearW)) { 
			
			try { // The client tries to connect to the server and it sends a request for add 
								
				Wine rq = new Wine(0, newNameW, newProducerW, newYearW, newTnW, newVinesW, Integer.parseInt(newNbW), Float.parseFloat(newPriceW)); // creation of a registration request
				rq.setRequest("adminAddWine");
				
				this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
				
				this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
				
				Object o = this.is.readObject(); // client waits for a server response
				
				if (o instanceof Response && ((Response) o).getResponse().equals("adminAddWine")) { 

					Response rs = (Response) o; 
					
					if(rs.getResp() > 0) {
					
						Alert alert = new Alert(Alert.AlertType.INFORMATION, "Insert has been successfully completed!"); // pop up
				        alert.setHeaderText("Insert completed");
				        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
				        alert.showAndWait();
					
						newWineName.clear();
						newWineProducer.clear();
						newWineYear.clear();
						newWineTN.clear();
						newWineVines.clear();
						newWineNB.clear();
						newWinePrice.clear();
			        	
						this.handleWineTab();
					}
					
					else {
						
						Alert alert = new Alert(Alert.AlertType.ERROR, "The item with parameters: name, producer, year\nis already inserted!"); // pop-up
				        alert.setHeaderText("Insert failed");
				        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
				        alert.showAndWait();
					}
				}
			} 
	        
			catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	
	
	/**
	 * 
	 * This method is used to handles the delete (administrator) button.
	 * When administrator clicks on this button, it will be deleted
	 * the selected wine from the database system.
	 * 
	 */
	
	
	@FXML
	public void handleDeleteWineButton() {
		
		Wine rq = wineTableView.getSelectionModel().getSelectedItem(); // it is the selected wine by administrator inside table
		
		if(rq == null) { // if customer doesn't select any wine
			
			Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a row of the table!"); // pop up
	        alert.setHeaderText("Selection failed");
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
	        alert.showAndWait(); // it waits for a customer input
		}
		
		if(rq != null) {
			
			// client tries to connects to the server iff (the user has selected a row into the table)
			
			try { // The client tries to connect to the server and it sends a removal request
				
				rq.setRequest("adminDeleteWine");
				
				this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
				
				this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
				
				Object o = this.is.readObject(); // client waits for a server response
				
				if (o instanceof Response && ((Response) o).getResponse().equals("adminDeleteWine")) { 

					Response rs = (Response) o; 
					
					if(rs.getResp() > 0) {
						
						Alert alert = new Alert(Alert.AlertType.INFORMATION, "Deletion has been successfully completed!"); // pop up
				        alert.setHeaderText("Delete completed");
				        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
				        alert.showAndWait();
				        
				        this.handleWineTab(); // loading results into wine table view
					}
					
					else {
						
						Alert alert = new Alert(Alert.AlertType.ERROR, "You can't delete this wine because there are \nforeign key constraint associated with him!"); // pop-up
				        alert.setHeaderText("Deletion failed");
				        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
				        alert.showAndWait();
					}
				}
			} 
			catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	/**
	 * 
	 * This method is used to handles the update wine button.
	 * When administrator clicks on this button, will be 
	 * updated the field/fields of the selected wine.
	 * 
	 */
	
	
	@FXML
	public void handleUpdateWineButton() {
		
		int nBW_i = 0;
		float priceW_f = 0;
		
		Wine rq = wineTableView.getSelectionModel().getSelectedItem(); // it is the selected wine by administrator inside table
		 
		if(rq == null) { // if customer doesn't select any wine
			
			Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a row of the table!"); // pop up
	        alert.setHeaderText("Selection failed");
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
	        alert.showAndWait(); // it waits for a customer input
		}
		
		if(rq != null) {
		
			String nameW = newWineName.getText();
			String producerW = newWineProducer.getText();
			String yearW = newWineYear.getText();
			String tNW = newWineTN.getText();
			String vinesW = newWineVines.getText();
			String nBW = newWineNB.getText();
			String priceW = newWinePrice.getText();
			
			if((!yearW.isBlank() && !validateYear(yearW))) { // if year is not blank it have to be validate
				
				Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid year.\nYou have to insert a date between 1960 - 2021"); // pop-up
		        alert.setHeaderText("Update failed");
		        alert.showAndWait();
			}
			
			if((!nBW.isBlank() && !validateBottles(nBW))) { // if number of bottles is not blank it have to be validate
				
				Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid number of bottles."); // pop-up
		        alert.setHeaderText("Update failed");
		        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
		        alert.showAndWait();
			}
			
			// client tries to connects to the server iff (the user has entered data at least in a one of fields)
	
			if(!nameW.isBlank() || !producerW.isBlank() || (!yearW.isBlank() && validateYear(yearW)) || !tNW.isBlank() || !vinesW.isBlank() || (!nBW.isBlank() && validateBottles(nBW)) || (!priceW.isBlank() && validatePrice(priceW))) { // if client entered email and policy is selected
				
				try { // The client tries to connect to the server and it sends a search wine request
					
					if(!nBW.isBlank()) nBW_i = Integer.parseInt(nBW); // this checks is used to avoid parsing (string to integer) errors
					
					else nBW_i = -1; // default value. It stands for null value
					
					if(!priceW.isBlank()) priceW_f = Float.parseFloat(priceW);
					
					else priceW_f = -1;
					
					Wine rq_tmp = new Wine(rq.getId(), nameW, producerW, yearW, tNW, vinesW, nBW_i, priceW_f);
					rq_tmp.setRequest("adminUpdateWine");
					
					this.os.writeObject(rq_tmp); // writing object into the object output stream and sending request to the server
					
					this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
					
					Object o = this.is.readObject(); // client waits for a server response
					
					if (o instanceof Response && ((Response) o).getResponse().equals("adminUpdateWine")) { 
	
						Response rs = (Response) o; 
						
						if(rs.getResp() > 0) {
							
							Alert alert = new Alert(Alert.AlertType.INFORMATION, "Update has been successfully completed!"); // pop up
					        alert.setHeaderText("Update completed");
					        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
					        alert.showAndWait();
					        
					        newWineName.clear();
							newWineProducer.clear();
							newWineYear.clear();
							newWineTN.clear();
							newWineVines.clear();
							newWineNB.clear();
							newWinePrice.clear();
					        
					        this.handleWineTab(); // loading results into wine table view
						}
						
						else {
							
							Alert alert = new Alert(Alert.AlertType.ERROR, "The item with this name, producer, year\nalready exist. Duplicate entry!"); // pop-up
					        alert.setHeaderText("Update failed");
					        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
					        alert.showAndWait();
						}
				    }
				} 
		        
				catch (IOException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
			
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Update failed, you must inser at least one field!"); // pop up
		        alert.setHeaderText("Update failed");
		        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
		        alert.showAndWait();
			}
		}
	}
	
	
	
	
	/**
	 * This method is used to handles the logout button event.
	 * When administrator clicks on logout he tries to send a request 
     * for closing connection to the server.
	 * 
	 * @param event The event.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	
	
	@FXML
	public void handleHomeButton(ActionEvent event) throws IOException {
		
		// Client sends a request for closing socket to server thread
	    
	    try { // the client tries to connect to the server
			
			RequestClose rq = new RequestClose(true); // creation of a close socket request
			
			this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
			
			this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
						
			Object o = this.is.readObject(); // client waits for a server response
			
			if (o instanceof Response && ((Response) o).getResponse().equals("close")) {
			
				Response rs = (Response) o;
				
				if(rs.getResp() > 0) { // server sent ok for close
					this.client.close();
				}
			}
		} 
        
		catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	    
	    // changing scene
				
		FXMLLoader loader= new FXMLLoader(getClass().getResource("../FXMLs/Login.fxml")); // load FXML file that contains login form
		
		Parent root = loader.load();
		
		LoginController controller = loader.getController(); // loading controller to pass information

		controller.transferRole("administrator"); // this instruction is used to transfer the role to the next (previous) window

		// creating login window
	    
	    Scene loginScene = new Scene(root);
	    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    window.setScene(loginScene);
	    window.setTitle("Login");
	    window.show();
	}

	
	
	
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
}
