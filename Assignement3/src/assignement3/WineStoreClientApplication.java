package assignement3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The class {@code WineStoreClientApplication} represents clients
 * that tries to connect to the server.
 * 
 * @author Vincenzo Fraello (299647) - Lorenzo Di Palma (299636)
 * @version 1.0
 */

public class WineStoreClientApplication extends Application {

	/** {@inheritDoc} **/
	@Override
	
	public void start(Stage primaryStage) {
		
		try {
			
			System.out.println("----------------------------------------------------------------");
			System.out.println("|                    CLIENT APPLICATION                         |");
			System.out.println("----------------------------------------------------------------\n");
			
			System.out.println("Authors: Vincenzo Fraello (299647) - Lorenzo Di Palma (299636)\n");
			
			System.out.println("STRUTTURA DASHBOARDS & ISTRUZIONI:\n ");
			
			try {
			     
				File fileDir = new File("./README.txt");
			   
				BufferedReader in = new BufferedReader(new InputStreamReader(
			                       new FileInputStream(fileDir), "UTF8"));

				String str;
			         
				while ((str = in.readLine()) != null) {
					System.out.println(str);
				}
			                
				in.close();
			} 
			         
			catch (UnsupportedEncodingException e) {
				System.out.println(e.getMessage());
			} 
			 
			catch (IOException e) {
				System.out.println(e.getMessage());
			}
			 
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			// creating role window
			
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("FXMLs/Role.fxml"));
			
			Scene scene = new Scene(root,319,318);
			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Client generator");
			primaryStage.getIcons().add(new Image("assignement3/pics/logo.png"));
			primaryStage.show();
		} 		
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is used to execute the client application.
	 * 
	 * @param args the method does not requires arguments.
	 */
	
	public static void main(final String[] args) {
		launch(args);
	}
}
