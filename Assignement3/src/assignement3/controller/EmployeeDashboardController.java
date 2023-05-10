package assignement3.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import assignement3.communication.Order;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * 
 * The class {@code EmployeeDashboardController} is used to manage
 * actions of EmployeeDashboardController.fxml.
 *
 */

public class EmployeeDashboardController {
	
	/**
	 * Class fields.
	 * 
	 * client - It is the socket used by client to communicate with the server.
	 * os - It is the object output stream used by client to sends messages to the server.
	 * is - It is the object input stream used by client to receives messages from the server.
	 * 
	 * employeeTabPane - It is the container of tabs of employee dashboard.
	 * 
	 * buyFinishedWineTab - It is the tab that will contains finished wines.
	 * 
	 * usernameLabel - It is the name of the administrator logged in.
	 * 
	 * deliveryTableView - It is the table that will contains wines to ship.
	 * 
	 * ordID - It is the column that will contains id orders.
	 * ordCustomer - It is the column that will contains customer id of orders.
	 * ordCAddress - It is the column that will contains customer address of orders.
	 * ordWine - It is the column that will contains wine id of orders.
	 * ordWProducer - It is the column that will contains  wine producer of orders.
	 * ordWYear - It is the column that will contains wine year of orders.
	 * ordBottles - It is the column that will contains wine number of bottles of orders.
	 * 
	 * customerID - It is the id to insert to view by search.
	 * 
	 * dateOrder - It is the date picker to view by search.
	 * 
	 * sendAll - It is the button used to ship all orders.
	 * 
	 * buyFinishedWineButton - It is the button used to buy a wine when it's finished.
	 * 
	 * wineNB - It is the number of bottles to insert when employee buys a finished wine.
	 * 
	 * buyFinishedWineTableView - It is the table that will contains finished wines.
	 * 
	 * wineFID - It is the column that will contains finished wine id.
	 * wineFName - It is the column that will contains finished wine name.
	 * wineFProducer - It is the column that will contains finished wine producer.
	 * wineFYear - It is the column that will contains finished wine year.
	 * wineFTn - It is the column that will contains finished wine technical notes.
	 * wineFVines - It is the column that will contains finished wine vines.
	 * wineFBottle - It is the column that will contains finished wine number of bottles (zero).
	 * wineFPrice - It is the column that will contains finished wine price.
	 * 
	 * buyNewWineTableView - It is the table that will contains the wine that are stored into the wine store system.
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
	 * newWineName - It is the new name of wines inserted by employee.
     * newWineProducer - It is the new producer of wines inserted by employee.
     * newWineYear - It is the new year of wines inserted by employee.
     * newWineTN - It is the new technical notes of wines inserted by employee.
 	 * newWineVines - It is the new vines of wines inserted by employee.
 	 * newWineNB - It is the new number of bottles of wines inserted by employee.
 	 * newWinePrice - It is the new price of wines inserted by employee.
	 * 
	 * updateWineTableView - It is the table that will contains the wine that are stored into the wine store system (ordered wine table by employee).
	 * 
	 * wineUID - It is the column that will contains wines id.
	 * wineUName - It is the column that will contains wines name.
	 * wineUProducer - It is the column that will contains wines producer.
	 * wineUYear - It is the column that will contains wines year.
	 * wineUTn - It is the column that will contains wines technical notes.
	 * wineUVines - It is the column that will contains wines vine.
	 * wineUBottle - It is the column that will contains wines number of bottles.
	 * wineUPrice - It is the column that will contains wines price.
	 * 
	 * uploadButton - It is the button used to upload order.
	 * 
	 * VALID_PRICE_REGEX - It is a regular expression used to validate wine price format.
	 * VALID_BOTTLES_REGEX - It is a regular expression used to validate number of wine bottles format.
	 * VALID YEAR REGEX - It is a regular expression used to validate year format.
	 */
	
	private Socket client;
	private ObjectOutputStream os;
	private ObjectInputStream is;
	
	@FXML private TabPane employeeTabPane;
	
	@FXML private Tab buyFinishedWineTab;
	
	@FXML private Label usernameLabel;
	
	@FXML private TableView<Order> deliveryTableView;
	
	@FXML private TableColumn<Order, Integer> ordID;
	@FXML private TableColumn<Order, Integer> ordCustomer;
	@FXML private TableColumn<Order, String> ordCAddress;
	@FXML private TableColumn<Order, String> ordWine;
	@FXML private TableColumn<Order, String> ordWProducer;
	@FXML private TableColumn<Order, String> ordWYear;
	@FXML private TableColumn<Order, Integer> ordBottles;
	
	@FXML private TextField customerID;
	
	@FXML private DatePicker dateOrder;	
	
	@FXML private Button sendAll;
	
	@FXML private Button buyFinishedWineButton;
	
	@FXML private TableView<Wine> buyFinishedWineTableView;
	
	@FXML private TextField wineNB;
	
	@FXML private TableColumn<Wine, Integer> wineFID;
	@FXML private TableColumn<Wine, String> wineFName;
	@FXML private TableColumn<Wine, String> wineFProducer;
	@FXML private TableColumn<Wine, String> wineFYear;
	@FXML private TableColumn<Wine, String> wineFTn;
	@FXML private TableColumn<Wine, String> wineFVines;
	@FXML private TableColumn<Wine, Integer> wineFBottle;
	@FXML private TableColumn<Wine, Float> wineFPrice;
	
	@FXML private TableView<Wine> buyNewWineTableView;
	
	@FXML private TableColumn<Wine, Integer> wineID;
	@FXML private TableColumn<Wine, String> wineName;
	@FXML private TableColumn<Wine, String> wineProducer;
	@FXML private TableColumn<Wine, String> wineYear;
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
	
	@FXML private TableView<Wine> updateWineTableView;
	
	@FXML private TableColumn<Wine, Integer> wineUID;
	@FXML private TableColumn<Wine, String> wineUName;
	@FXML private TableColumn<Wine, String> wineUProducer;
	@FXML private TableColumn<Wine, String> wineUYear;
	@FXML private TableColumn<Wine, String> wineUTn;
	@FXML private TableColumn<Wine, String> wineUVines;
	@FXML private TableColumn<Wine, Integer> wineUBottle;
	@FXML private TableColumn<Wine, Float> wineUPrice;
	
	@FXML private Button uploadButton;
	
	private static final String VALID_BOTTLES_REGEX = "^[1-9][0-9]*$";
	private static final String VALID_PRICE_REGEX = "^\s*(?=.*[1-9])[0-9]*(?:[.][0-9]{1,2})?\s*$";
	private static final String VALID_YEAR_REGEX = "(19[6789][0-9]|20[01][0-9]|2020|2021)"; //dal 1960 al 2021
	
	
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
  	 * This is a service methods used to validate the price inserted by client.
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
	 * to EmployeeDashboardController.
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
	 * This method is used to initialize the employee dashboard.
	 * 
	 * @param n It is the name of employee.
	 */
	
	public void initialize(final String n) {
		
		usernameLabel.setText(n.split("@")[0]); // setting the user name near "Welcome" text	
		
		sendAll.setDisable(true); // disable send all orders button
		
		this.handleViewAllFinished(); // it shows to the employee if there are finished wine after login
	}
		
	
	/**
	 * 
	 * This method is used to handles date picker.  	
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
	 * 
	 * This is a service method used to show - load results into orders table view.
	 * 
	 */
	
	private void handleViewOrderToShip(final Response rs) {
		
		ObservableList<Order> obList = FXCollections.observableArrayList();
		
		for(Order v: rs.getRespListO()){
			obList.add(v);
		}
						
		ordID.setCellValueFactory(new PropertyValueFactory<>("idO"));
		ordCustomer.setCellValueFactory(new PropertyValueFactory<>("nameC"));
		ordCAddress.setCellValueFactory(new PropertyValueFactory<>("addressC"));
		ordWine.setCellValueFactory(new PropertyValueFactory<>("nameW"));
		ordWProducer.setCellValueFactory(new PropertyValueFactory<>("producerW"));
		ordWYear.setCellValueFactory(new PropertyValueFactory<>("yearW"));
		ordBottles.setCellValueFactory(new PropertyValueFactory<>("numBottles"));
		
		deliveryTableView.setItems(obList);
		
		customerID.clear(); // clearing the customer id field
		dateOrder.getEditor().clear(); // clearing the date picker
		
		sendAll.setDisable(false); // enable send all orders button

		dateOrder.setValue(null);
	}
	
	
	/**
	 * 
	 * This method is used to handles the view all / view by search orders to ship button.
	 * When employee clicks on this button, it will be shown all undelivered orders.
	 * 
	 */
	
	@FXML
	public void handleViewBySearchButton() {
		
		int custSelected = -1; // default value. It stands for null value
		
		Timestamp dateNot = this.handleDatePicker(); // date entered by user
		
		String custSelectedStr = customerID.getText(); // id
		
		if(!customerID.getText().isBlank() && customerID.getText().matches("^[1-9][0-9]*$")) {
			
			custSelected = Integer.parseInt(custSelectedStr); // this avoid parsing (string to integer) errors
		}
		
		
		try { // the client tries to connect to the server

			Order rq = new Order(); // creation of a view unshipped orders request
			rq.setDate(dateNot);
			rq.setIdC(custSelected);
			rq.setRequest("employeeViewDeliveryBySearch"); // setting the type of request
			
			this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
			
			this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
			
			Object o = this.is.readObject(); // client waits for a server response
			
			if (o instanceof Response && ((Response) o).getResponse().equals("adminEmployeeViewBySearch")) {			
				
				Response rs = (Response) o;
				
				this.handleViewOrderToShip(rs); // loading results into orders table view
			}
			
			else if (o instanceof Response && ((Response) o).getResponse().equals("failAdminEmployeeViewBySearch")) { // the server does not find any results
								
				deliveryTableView.setPlaceholder(new Label("No result"));
				
				customerID.clear();
				dateOrder.getEditor().clear();
				dateOrder.setValue(null);
				
				sendAll.setDisable(true);
				
				for ( int i = 0; i < deliveryTableView.getItems().size(); i++) { // cleaning the table view
					
					deliveryTableView.getItems().clear();
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
	 * This method is used to handles the delivery all (employee) button.
	 * When employee clicks on this button, it will be shipped all undelivered
	 * orders.
	 * 
	 */
	
	@FXML
	public void handleShipAllButton() {
		
		try { // the client tries to connect to the server
			
			Order rq = new Order(); // creation of a delivery wine request
			rq.setRequest("shipAll");
			
			this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
			
			this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
			
			Object o = this.is.readObject(); // client waits for a server response
			
			if (o instanceof Response && ((Response) o).getResponse().equals("shipAllOK")) { 
				
				Response rs = (Response) o;
					
				String result = rs.getResponseMDB();
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
				alert.setHeaderText("Shipping summary");
				alert.setContentText("All orders has been successfully shipped!");
				
				// Message containing the shipping results

				Label label = new Label("Details:");

				TextArea textArea = new TextArea(result);
				textArea.setEditable(false);
				textArea.setWrapText(true);

				textArea.setMaxWidth(Double.MAX_VALUE);
				textArea.setMaxHeight(Double.MAX_VALUE);
				GridPane.setVgrow(textArea, Priority.ALWAYS);
				GridPane.setHgrow(textArea, Priority.ALWAYS);

				GridPane expContent = new GridPane();
				expContent.setMaxWidth(Double.MAX_VALUE);
				expContent.add(label, 0, 0);
				expContent.add(textArea, 0, 1);

				alert.getDialogPane().setExpandableContent(expContent);

				alert.showAndWait();
				
				for ( int i = 0; i < deliveryTableView.getItems().size(); i++) { // cleaning the table view
					 deliveryTableView.getItems().clear();
				}
				
				sendAll.setDisable(true); // disable send all button
				 
				deliveryTableView.setPlaceholder(new Label("No orders to delivery."));
			}
			
			else if(o instanceof Response && ((Response) o).getResponse().equals("failshipAll")) {
				
				deliveryTableView.setPlaceholder(new Label("No orders to delivery."));
			}
		} 
        
		catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * This method is used to handles delivery select button.
	 * When employee clicks on this button, it will be shipped
	 * the selected order.
	 * 
	 */
	
	@FXML
	public void handleShipSelect() {
		
		Order rq = deliveryTableView.getSelectionModel().getSelectedItem(); // it is the selected order by employee inside table
		
		if(rq == null) { // if employee doesn't select any order
			
			Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a row of the table!"); // pop up
	        alert.setHeaderText("Selection failed");
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
	        alert.showAndWait(); // it waits for a employee input
		}
		
		if(rq != null) {
			
			// client tries to connects to the server iff (the user has selected a row into the table)
			
			try { // The client tries to connect to the server and it sends a shipping request
				
				rq.setRequest("shipSelected");
				
				this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
				
				this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
				
				Object o = this.is.readObject(); // client waits for a server response
				
				if (o instanceof Response && ((Response) o).getResponse().equals("shipAllOK")) { 

					Response rs = (Response) o; 
					
					Alert alert = new Alert(Alert.AlertType.INFORMATION, rs.getResponseMDB() + "\n"); // pop up
			        alert.setHeaderText("Delivery completed");
			        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
			        alert.showAndWait();
			        
			        sendAll.setDisable(true);
			
			        for ( int i = 0; i < deliveryTableView.getItems().size(); i++) { // cleaning the table view
			        	deliveryTableView.getItems().clear();
					}
				}
				
				else if(o instanceof Response && ((Response) o).getResponse().equals("failShipAll")) {
	
			        deliveryTableView.setPlaceholder(new Label("No orders to delivery."));
				}
			} 
	        
			catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}		
	}
	
	
	/**
	 * 
	 * This method is used to load - show results into table finished wine view.
	 * When employee selects this tab, it will be shown the finished wines.
	 * 
	 */
	
	@FXML
	public void handleViewAllFinished() {
		
		try { // The client tries to connect to the server and it sends a request to view finished wine
							
			Wine rq = new Wine(); // creation of a view finished wine request
			rq.setRequest("employeeViewBuyFinishedWine");
			
			this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
			
			this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
			
			Object o = this.is.readObject(); // client waits for a server response
			
			if (o instanceof Response && ((Response) o).getResponse().equals("search")) { 

				Response rs = (Response) o; 
				
				// loading results into finished wines table view
		        
		        ObservableList<Wine> obList = FXCollections.observableArrayList();
				
				for(Wine v: rs.getRespListS()) {				
					obList.add(v);
				}
		        	
				wineFID.setCellValueFactory(new PropertyValueFactory<>("id"));
				wineFName.setCellValueFactory(new PropertyValueFactory<>("name"));
				wineFProducer.setCellValueFactory(new PropertyValueFactory<>("producer"));
				wineFYear.setCellValueFactory(new PropertyValueFactory<>("year"));
				wineFTn.setCellValueFactory(new PropertyValueFactory<>("technicalNotes"));
				wineFVines.setCellValueFactory(new PropertyValueFactory<>("vines"));
				wineFBottle.setCellValueFactory(new PropertyValueFactory<>("numBottles"));
				wineFPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
				
				buyFinishedWineTableView.setItems(obList);
				
				buyFinishedWineButton.setDisable(false);
				
				// it switch into this tab if only the current tab isn't buyFinishedWineTab
				
				if(!employeeTabPane.getSelectionModel().getSelectedItem().getText().equals("Buy finished wine") ) {
				
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Notification");
					Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
					alert.setContentText("New notification of zero bottles!\nDo you want to see it?");
					Optional<ButtonType> result = alert.showAndWait();
				
					if (result.get() == ButtonType.OK) employeeTabPane.getSelectionModel().select(buyFinishedWineTab);
				}
			}
			
			else if(o instanceof Response && ((Response) o).getResponse().equals("searchFail")) {
				
				buyFinishedWineTableView.setPlaceholder(new Label("No finished bottles"));
				
				buyFinishedWineButton.setDisable(true);
			}

		} 
        
		catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * This method is used to handles the buy finished wine button.
	 * When employee clicks on this button, he buys the finished wine.
	 * The selected wine will be inserted into orderedwineemployee table.
	 * 
	 */
	
	@FXML
	public void handleBuyFinishedWineButton() {
		
		int nB = 0;
	
		Wine rq = buyFinishedWineTableView.getSelectionModel().getSelectedItem(); // selected finished wine
		
		if(rq == null) { // if employee doesn't select any wine
			
			Alert alert = new Alert(AlertType.ERROR, "You must select a row of the table!"); // pop up
	        alert.setHeaderText("Selection failed");
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
	        alert.showAndWait(); // it waits for a employee input
		}
		
		if(!wineNB.getText().isBlank() && validateBottles(wineNB.getText())) {
			
			nB = Integer.parseInt(wineNB.getText());
		}
		
		else {
			
			Alert alert = new Alert(AlertType.ERROR, "Invalid number of bottles!"); // pop up
	        alert.setHeaderText("Invalid input");
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
	        alert.showAndWait(); // it waits for a employee input
		}
		
		if(rq != null && !wineNB.getText().isBlank() && validateBottles(wineNB.getText())) {
			
			try { // the client tries to connect to the server and it sends a buy finished wine request
				
				rq.setNumBottles(nB);
				rq.setRequest("employeeUpdateFinishedWine");
						
				this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
				
				this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
				
				Object o = this.is.readObject(); // client waits for a server response
				
				if (o instanceof Response && ((Response) o).getResponse().equals("employeeUpdateFinishedWine")) { 

					Response rs = (Response) o; 
					
					Alert alert = new Alert(Alert.AlertType.INFORMATION, "Purchase has been successfully completed!"); // pop up
			        alert.setHeaderText("Purchase completed");
			        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
			        alert.showAndWait();
			        
			        // loading results (remaining finished wines) into finished wines table view.
			        
			        ObservableList<Wine> obList = FXCollections.observableArrayList();
			        
					for(Wine v: rs.getRespListS()) {		
						obList.add(v);
					}
					
					wineFID.setCellValueFactory(new PropertyValueFactory<>("id"));
					wineFName.setCellValueFactory(new PropertyValueFactory<>("name"));
					wineFProducer.setCellValueFactory(new PropertyValueFactory<>("producer"));
					wineFYear.setCellValueFactory(new PropertyValueFactory<>("year"));
					wineFTn.setCellValueFactory(new PropertyValueFactory<>("technicalNotes"));
					wineFVines.setCellValueFactory(new PropertyValueFactory<>("vines"));
					wineFBottle.setCellValueFactory(new PropertyValueFactory<>("numBottles"));
					wineFPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
					
					buyFinishedWineTableView.setItems(obList);
					
					wineNB.clear(); // clearing the number of bottles text field
			    }
				
				else if(o instanceof Response && ((Response) o).getResponse().equals("failEmployeeUpdateFinishedWine")) {
					
					for ( int i = 0; i < buyFinishedWineTableView.getItems().size(); i++) { // cleaning the table view
						buyFinishedWineTableView.getItems().clear();
					}
					
					buyFinishedWineTableView.setPlaceholder(new Label("No orders to delivery."));
					
					buyFinishedWineButton.setDisable(true);
					
					wineNB.clear();
				}
			} 
	        
			catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 
	 * This method is used to handles the buy new wine table view.
	 * If you select a row into the table, then other fields will be disabled
	 * except number of bottles field.
	 * 
	 */
	
	@FXML 
	public void handleBuyNewWineTableView() {
		
		Wine rq_tmp = buyNewWineTableView.getSelectionModel().getSelectedItem(); // if you select a wine then it will be disabled other fields except number of bottles 
		
		// because you can buy a wine that doesn't exist into wine store system
			// or you can buy an existing wine
		
		if(rq_tmp != null) {
			
			newWineName.setDisable(true);
			newWineProducer.setDisable(true);
			newWineYear.setDisable(true);
			newWineTN.setDisable(true);
			newWineVines.setDisable(true);
			newWinePrice.setDisable(true);
		}
	}
	
	
	/**
	 * This method is used to deselect a row from the table
	 * by pressing ESC key.
	 * 
	 * @param e The event.
	 */
	
	@FXML
	private void handleBuyNewWineTabPaneDeselectRow(KeyEvent e) {
		
		if ((e.getCode() == KeyCode.ESCAPE)) {
			
			buyNewWineTableView.getSelectionModel().select(null);
			
			newWineName.setDisable(false);
			newWineProducer.setDisable(false);
			newWineYear.setDisable(false);
			newWineTN.setDisable(false);
			newWineVines.setDisable(false);
			newWinePrice.setDisable(false);
		}
	}
	
	
	/**
	 * 
	 * This method is used to handles the buy new wine tab.
	 * In this tab there's a table in which employee can view 
	 * wine store wines.
	 * 
	 */
	
	@FXML
	public void handleBuyNewWineTab() {
		
		try { // the client tries to connect to the server
			
			Wine rq = new Wine(); // creation of a view wine request			
			rq.setRequest("adminViewWine");
			
			this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
			
			this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
			
			Object o = this.is.readObject(); // client waits for a server response
			
			if (o instanceof Response && ((Response) o).getResponse().equals("search") ) { // if the server finds the wine the user is looking for
				
				// loading results into table view
		
				ObservableList<Wine> obList = FXCollections.observableArrayList();
				
				Response rs = (Response) o;
				
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
				
				buyNewWineTableView.setItems(obList);
			}
			
			else if (o instanceof Response && ((Response) o).getResponse().equals("searchFail")) { // the server does not find any results
				
				buyNewWineTableView.setPlaceholder(new Label("No wines to show"));
			}
		} 
        
		catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * This method is used to handles the buy new wine button.
	 * When employee clicks on this button, the employee buys an existing 
	 * wine (by selecting a row into the table) or a new wine (entering data
	 * into the fields).
	 * 
	 */
	
	@FXML
	public void handleBuyNewWineButton() {
	
		String newNameW = newWineName.getText();
		String newProducerW = newWineProducer.getText();
		String newYearW = newWineYear.getText();
		String newTnW = newWineTN.getText();
		String newVinesW = newWineVines.getText();
		String newNbW = newWineNB.getText();
		String newPriceW = newWinePrice.getText();
		
		if(!newWineName.isDisable()) { // if employee wants to buy a new wine
		
			// it checks that all fields has been compiled
	
			if(newNameW.isBlank() || newProducerW.isBlank() || newYearW.isBlank() || newTnW.isBlank() || newVinesW.isBlank() || newProducerW.isBlank() || newNbW.isBlank() || newPriceW.isBlank()) {
				
				Alert alert = new Alert(Alert.AlertType.ERROR, "You have to enter data into all fields\nor select a row!"); // pop up
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
				
				try { // The client tries to connect to the server and it sends a buy new wine request
									
					Wine rq = new Wine(0, newNameW, newProducerW, newYearW, newTnW, newVinesW, Integer.parseInt(newNbW), Float.parseFloat(newPriceW)); // creation of a buy new wine request
					rq.setRequest("employeeBuyNewWine");
					
					this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
					
					this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
					
					Object o = this.is.readObject(); // client waits for a server response
					
					if (o instanceof Response && ((Response) o).getResponse().equals("employeeBuyNewWine")) { 
						
						Response rs = (Response) o;
						
						if(rs.getResp() > 0) {
						
							Alert alert = new Alert(Alert.AlertType.INFORMATION, "Insert has been successfully completed!"); // pop up
					        alert.setHeaderText("Insert completed");
					        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
					        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
					        alert.showAndWait();
					        
					        // cleaning text fields
							
							newWineName.clear();
							newWineProducer.clear();
							newWineYear.clear();
							newWineTN.clear();
							newWineVines.clear();
							newWineNB.clear();
							newWinePrice.clear();
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
		
		else {	// if employee wants to buy an existing wine	
			
			int nB = 0;
			
			Wine rq = buyNewWineTableView.getSelectionModel().getSelectedItem(); // he selects a row into the table
			
			if(!newNbW.isBlank() && validateBottles(newNbW)) { 
				nB = Integer.parseInt(newNbW);
			}
						
			else {
				Alert alert = new Alert(AlertType.ERROR, "Invalid number of bottles!"); // pop up
		        alert.setHeaderText("Invalid input");
		        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
		        alert.showAndWait(); // it waits for a customer input
			}
			
			if(rq != null && !newNbW.isBlank() && validateBottles(newNbW)) {
				
				try { // the client tries to connect to the server and it sends a request for buy an existing wine
					
					rq.setNumBottles(nB);
					rq.setRequest("employeeUpdateAlreadyExistWine");
							
					this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
					
					this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
					
					Object o = this.is.readObject(); // client waits for a server response
					
					if(o instanceof Response && ((Response) o).getResponse().equals("failEmployeeUpdateAlreadyExistWine")) {
						
						Alert alert = new Alert(AlertType.INFORMATION, "Order has been successfully completed!"); // pop up
				        alert.setHeaderText("Order success");
				        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
				        alert.showAndWait(); // it waits for a customer input
						
				        newWineNB.clear();
				        
				        newWineName.setDisable(false);
						newWineProducer.setDisable(false);
						newWineYear.setDisable(false);
						newWineTN.setDisable(false);
						newWineVines.setDisable(false);
						newWinePrice.setDisable(false);
						
						buyNewWineTableView.getSelectionModel().select(null);
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
	 * This is a service method used to show - load results into update wine table view.
	 * 
	 */
	
	private void handleViewUpdateWine(final Response rs) {
		
		ObservableList<Wine> obList = FXCollections.observableArrayList();
		
		for(Wine v: rs.getRespListS()) {
			obList.add(v);
		}
		
		wineUID.setCellValueFactory(new PropertyValueFactory<>("id"));
		wineUName.setCellValueFactory(new PropertyValueFactory<>("name"));
		wineUProducer.setCellValueFactory(new PropertyValueFactory<>("producer"));
		wineUYear.setCellValueFactory(new PropertyValueFactory<>("year"));
		wineUTn.setCellValueFactory(new PropertyValueFactory<>("technicalNotes"));
		wineUVines.setCellValueFactory(new PropertyValueFactory<>("vines"));
		wineUBottle.setCellValueFactory(new PropertyValueFactory<>("numBottles"));
		wineUPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		updateWineTableView.setItems(obList);
		
		uploadButton.setDisable(false);
	}
	
	
	/**
	 * 
	 * This method is used to handles the update table view.
	 * When an employee selects this tab, it will be
	 * shown wines he bought.
	 * 
	 */
	
	public void handleUpdateWineTab() {
		
		try { // the client tries to connect to the server
			
			Wine rq = new Wine(); // creation of a view wine request
			rq.setRequest("employeeViewOrderedWine");
			
			this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
			
			this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
			
			Object o = this.is.readObject(); // client waits for a server response
			
			if (o instanceof Response && ((Response) o).getResponse().equals("search") ) { // if the server finds the wine the user is looking for
				
				Response rs = (Response) o;
				
				this.handleViewUpdateWine(rs); // loading results into table view
			}
			
			else if (o instanceof Response && ((Response) o).getResponse().equals("searchFail")) { // the server does not find any results
				
				updateWineTableView.setPlaceholder(new Label("No wine to show"));
			}
		} 
        
		catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}		
	}
	
	
	/**
	 * 
	 * This method is used to handles the update (employee) button to reload bottles.
	 * When employee clicks on this button, it will be reloaded bottles of wines he bought
	 * and it will be sent availability notification to users who subscribed for that wine.
	 * 
	 */
	
	@FXML
	public void handleUpdateWineButton() {
		
		Wine rq = updateWineTableView.getSelectionModel().getSelectedItem(); // it is the selected wine by employee inside table
		
		if(rq == null) { // if employee doesn't select any wine
			
			Alert alert = new Alert(Alert.AlertType.ERROR, "You must select a row of the table!"); // pop up
	        alert.setHeaderText("Selection failed");
	        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
	        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
	        alert.showAndWait(); // it waits for an employee input
		}
		
		if(rq != null) {
			
			// client tries to connects to the server iff (the user has selected a row into the table)
			
			try { // The client tries to connect to the server and it sends an update request
				
				rq.setRequest("employeeUpdateWine");
				
				this.os.writeObject(rq); // writing object into the object output stream and sending request to the server
				
				this.os.flush(); // this method is used to make sure that all buffered data has been written into the object output stream
				
				Object o = this.is.readObject(); // client waits for a server response
				
				if (o instanceof Response && ((Response) o).getResponse().equals("employeeUpdateWine")) { 

					Response rs = (Response) o; 
					
					Alert alert = new Alert(Alert.AlertType.INFORMATION, "Update has been successfully completed!"); // pop up
			        alert.setHeaderText("Update completed");
			        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
			        alert.showAndWait();
			        
					this.handleViewUpdateWine(rs); // loading results into table view
				}
				
				else if(o instanceof Response && ((Response) o).getResponse().equals("failEmployeeUpdateWine")) {
					
					Alert alert = new Alert(Alert.AlertType.INFORMATION, "All wines has been uploaded!"); // pop-up
			        alert.setHeaderText("Uploaded completed");
			        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
			        stage.getIcons().add(new Image("assignement3/pics/logo.png"));
			        alert.showAndWait();
			        
			        for ( int i = 0; i < updateWineTableView.getItems().size(); i++) { // cleaning the table view
			        	updateWineTableView.getItems().clear();
					}
			        
			        uploadButton.setDisable(true);
			        
			        updateWineTableView.setPlaceholder(new Label("No orders to delivery."));
				}
			} 
	        
			catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	

	/**
	 * This method is used to handles the logout button event.
	 * When an employee clicks on logout he tries to send a request 
     * for closing connection to the server.
	 * 
	 * @param event The event.
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	
	@FXML
	public void handleHomeButton(ActionEvent event) throws IOException {
		
		// Client sends a request for closing socket to server thread
	    
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
	
		FXMLLoader loader= new FXMLLoader(getClass().getResource("../FXMLs/Login.fxml")); // load FXML file that contains login form
		Parent root = loader.load();
		LoginController controller = loader.getController(); // loading controller to pass information
		controller.transferRole("employee"); // this instruction is used to transfer the role to the next window
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

	public Socket getClient() {	return client; }
	
	
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

	public ObjectInputStream getIs() { return is; }
	
	
	/**
	 * This method is used to set the client object input stream.
	 * 
	 * @param is It is the object input stream to set.
	 */

	private void setIs(ObjectInputStream is) { this.is = is; }
}
