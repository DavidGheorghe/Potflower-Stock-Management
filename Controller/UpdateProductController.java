package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import DBConnection.connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class UpdateProductController implements Initializable{

    @FXML
    private JFXTextField updateNameValue;

    @FXML
    private JFXTextField updateHeightValue;

    @FXML
    private JFXTextField updateWidthValue;

    @FXML
    private JFXTextField updateWeightValue;

    @FXML
    private JFXTextField updatePcsValue;

    @FXML
    private JFXTextField updatePriceValue;

    @FXML
    private JFXButton goBackButton;

    @FXML
    private JFXButton saveButton;
    
    @FXML
    private Label initialNameValue;

    @FXML
    private Label initialHeightValue;

    @FXML
    private Label initialWidthValue;

    @FXML
    private Label initialWeightValue;

    @FXML
    private Label initialPcsValue;

    @FXML
    private Label initialPriceValue;
    
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
    void saveButtonEvent(ActionEvent event) throws SQLException, ClassNotFoundException {
    	
			con = conClass.getConnection();
			
			String updateQuery = "update product.potflower set name = ? , height = ? , width = ? , weight = ? , pcs = ? , price = ? where name = ? and height = ? and width = ? and weight = ? and pcs = ? and price = ?";
			
			PreparedStatement pst = con.prepareStatement(updateQuery);
			
			Alert emptyTextFieldAlert = new Alert(AlertType.INFORMATION);
	    	Alert notNumberAlert = new Alert(AlertType.INFORMATION);
	    	
	    	boolean valuesOK = true;
	    	
	    	if(updateNameValue.getText().trim().isEmpty())
	    	{
	    		valuesOK = false;
	    		emptyTextFieldAlert.setHeaderText("You forgot to add the name.");
	    		emptyTextFieldAlert.showAndWait();
	    	}
	    	else if(updateHeightValue.getText().trim().isEmpty()) 
	    	{
	    		valuesOK = false;
	    		emptyTextFieldAlert.setHeaderText("You forgot to add the height.");
	    		emptyTextFieldAlert.showAndWait();
	    	}
	    	else if(updateWidthValue.getText().trim().isEmpty())
	    	{
	    		valuesOK = false;
	    		emptyTextFieldAlert.setHeaderText("You forgot to add the width.");
	    		emptyTextFieldAlert.showAndWait();
	    	}
	    	else if(updateWeightValue.getText().trim().isEmpty())
	    	{
	    		valuesOK = false;
	    		emptyTextFieldAlert.setHeaderText("You forgot to add the weight.");
	    		emptyTextFieldAlert.showAndWait();
	    	}
	    	else if(updatePcsValue.getText().trim().isEmpty())
	    	{
	    		valuesOK = false;
	    		emptyTextFieldAlert.setHeaderText("You forgot to add the number of pieces.");
	    		emptyTextFieldAlert.showAndWait();
	    	}
	    	else if(updatePriceValue.getText().trim().isEmpty())
	    	{
	    		valuesOK = false;
	    		emptyTextFieldAlert.setHeaderText("You forgot to add the price.");
	    		emptyTextFieldAlert.showAndWait();
	    	}
			
	    	if(valuesOK == true)
	    	{
	    		try
	    		{
	    			pst.setString(1, updateNameValue.getText());
	    			pst.setString(2, updateHeightValue.getText());
	    			pst.setString(3, updateWidthValue.getText());
	    			pst.setString(4, updateWeightValue.getText());
	    			pst.setString(5, updatePcsValue.getText());
	    			pst.setString(6, updatePriceValue.getText());
	    			pst.setString(7, initialNameValue.getText());
	    			pst.setString(8, initialHeightValue.getText());
	    			pst.setString(9, initialWidthValue.getText());
	    			pst.setString(10, initialWeightValue.getText());
	    			pst.setString(11, initialPcsValue.getText());
	    			pst.setString(12, initialPriceValue.getText());
	    			
	    			pst.executeUpdate();
	    		}
	    		catch(SQLException e)
	    		{
	    			if(updateHeightValue.getText().matches("[0-9]+") == false)
	        		{
	        			notNumberAlert.setHeaderText("Height must be a number.");
	        			notNumberAlert.showAndWait();
	        		}
	    			else if(updateWidthValue.getText().matches("[0-9]+") == false)
	        		{
	        			notNumberAlert.setHeaderText("Width must be a number.");
	        			notNumberAlert.showAndWait();
	        		}
	    			else if(updateWeightValue.getText().matches("[0-9]+") == false)
	        		{
	        			notNumberAlert.setHeaderText("Weight must be a number.");
	        			notNumberAlert.showAndWait();
	        		}
	    			else if(updatePcsValue.getText().matches("[0-9]+") == false)
	        		{
	        			notNumberAlert.setHeaderText("Number of pieces must be a number.");
	        			notNumberAlert.showAndWait();
	        		}
	    			else if(updatePriceValue.getText().matches("[0-9]+") == false)
	        		{
	        			notNumberAlert.setHeaderText("Price must be a number.");
	        			notNumberAlert.showAndWait();
	        		}
	    		}
	    	}

			

		
    	
}

	@Override
	public void initialize(URL location, ResourceBundle rb) {
		
	}
	
	
	public void setTextToUpdate(String name, double height, double width, double weight,int pcs, double price)
	{
		this.updateNameValue.setText(name);
		this.updateHeightValue.setText(String.valueOf(height));
		this.updateWidthValue.setText(String.valueOf(width));
		this.updateWeightValue.setText(String.valueOf(weight));
		this.updatePcsValue.setText(String.valueOf(pcs));
		this.updatePriceValue.setText(String.valueOf(price));
	}

	public void setTextSelectedRow(String name, double height, double width, double weight,int pcs, double price)
	{
		this.initialNameValue.setText(name);
		this.initialHeightValue.setText(String.valueOf(height));
		this.initialWidthValue.setText(String.valueOf(width));
		this.initialWeightValue.setText(String.valueOf(weight));
		this.initialPcsValue.setText(String.valueOf(pcs));
		this.initialPriceValue.setText(String.valueOf(price));
	}

}
