package Controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import DBConnection.connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddProductController implements Initializable{

    @FXML
    private JFXTextField addIDValue;

    @FXML
    private JFXTextField addNameValue;

    @FXML
    private JFXTextField addHeightValue;

    @FXML
    private JFXTextField addWidthValue;

    @FXML
    private JFXTextField addWeightValue;

    @FXML
    private JFXTextField addPcsValue;

    @FXML
    private JFXTextField addPriceValue;

    @FXML
    private JFXButton goBackButton;
    
    @FXML
    private JFXButton clearButton;
    
    @FXML
    private JFXButton saveButton;
    
    @FXML
    private JFXTextArea confirmationMessage;
    
    Connection con;
    connection conClass = new connection();
    
    @FXML
    void goBackButtonEvent(ActionEvent event) throws IOException {
    	
    	goBackButton.getScene().getWindow().hide();
    	
    	Stage mainMenuStage = new Stage();
    	
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/MainMenu.fxml"));
    	
    	Scene mainMenuScene = new Scene(root);
    	
    	mainMenuStage.setScene(mainMenuScene);
    	mainMenuStage.show();

    }

 
    @FXML
    void saveButtonEvent(ActionEvent event) throws ClassNotFoundException, SQLException, InvocationTargetException {
    	con = conClass.getConnection();
    	
    	String insertQuery = "insert into product.potflower(name, height, width, weight, pcs, price)" + "values (?, ?, ?, ?, ?, ?)";
    	PreparedStatement pst = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
    	
    	Alert emptyTextFieldAlert = new Alert(AlertType.INFORMATION);
    	Alert notNumberAlert = new Alert(AlertType.INFORMATION);
    	
    	boolean valuesOK = true;
    	
    	if(addNameValue.getText().trim().isEmpty())
    	{
    		valuesOK = false;
    		emptyTextFieldAlert.setHeaderText("You forgot to add the name.");
    		emptyTextFieldAlert.showAndWait();
    	}
    	else if(addHeightValue.getText().trim().isEmpty()) 
    	{
    		valuesOK = false;
    		emptyTextFieldAlert.setHeaderText("You forgot to add the height.");
    		emptyTextFieldAlert.showAndWait();
    	}
    	else if(addWidthValue.getText().trim().isEmpty())
    	{
    		valuesOK = false;
    		emptyTextFieldAlert.setHeaderText("You forgot to add the width.");
    		emptyTextFieldAlert.showAndWait();
    	}
    	else if(addWeightValue.getText().trim().isEmpty())
    	{
    		valuesOK = false;
    		emptyTextFieldAlert.setHeaderText("You forgot to add the weight.");
    		emptyTextFieldAlert.showAndWait();
    	}
    	else if(addPcsValue.getText().trim().isEmpty())
    	{
    		valuesOK = false;
    		emptyTextFieldAlert.setHeaderText("You forgot to add the number of pieces.");
    		emptyTextFieldAlert.showAndWait();
    	}
    	else if(addPriceValue.getText().trim().isEmpty())
    	{
    		valuesOK = false;
    		emptyTextFieldAlert.setHeaderText("You forgot to add the price.");
    		emptyTextFieldAlert.showAndWait();
    	}
    	
    	if(valuesOK == true)
    	{
    		try
    		{
        		pst.setString(1, addNameValue.getText());
    			pst.setString(2, addHeightValue.getText());
    			pst.setString(3, addWidthValue.getText());
    			pst.setString(4, addWeightValue.getText());
    			pst.setString(5, addPcsValue.getText());
    			pst.setString(6, addPriceValue.getText());
    			
    			pst.executeUpdate();
    		}
    		catch(SQLException e)
    		{
    			if(addHeightValue.getText().matches("[0-9]+") == false)
        		{
        			notNumberAlert.setHeaderText("Height must be a number.");
        			notNumberAlert.showAndWait();
        		}
    			else if(addWidthValue.getText().matches("[0-9]+") == false)
        		{
        			notNumberAlert.setHeaderText("Width must be a number.");
        			notNumberAlert.showAndWait();
        		}
    			else if(addWeightValue.getText().matches("[0-9]+") == false)
        		{
        			notNumberAlert.setHeaderText("Weight must be a number.");
        			notNumberAlert.showAndWait();
        		}
    			else if(addPcsValue.getText().matches("[0-9]+") == false)
        		{
        			notNumberAlert.setHeaderText("Number of pieces must be a number.");
        			notNumberAlert.showAndWait();
        		}
    			else if(addPriceValue.getText().matches("[0-9]+") == false)
        		{
        			notNumberAlert.setHeaderText("Price must be a number.");
        			notNumberAlert.showAndWait();
        		}
    		}	
    	}
    	//ResultSet rs = pst.getGeneratedKeys();
    	  	
    }
    
    @FXML
    void clearButtonEvent(ActionEvent event) {
    	addNameValue.clear();
    	addHeightValue.clear();
    	addWeightValue.clear();
    	addWidthValue.clear();
    	addPcsValue.clear();
    	addPriceValue.clear();
    }
    
	@Override
	public void initialize(URL location, ResourceBundle rb) {
		
		
	}

}
