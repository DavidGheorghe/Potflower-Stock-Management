package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.function.Predicate;

import javax.print.attribute.standard.PrinterMessageFromOperator;
import javax.swing.JOptionPane;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import DBConnection.connection;
import impl.org.controlsfx.skin.AutoCompletePopup;
import javafx.scene.control.*;

public class Controller implements Initializable{

    @FXML
    private AnchorPane anchorPane;
    
	@FXML
    private JFXButton addProductButton;

    @FXML
    private JFXButton deleteProductButton;

    @FXML
    private JFXButton updateProductButton;
    
    @FXML
    private TableView<Potflower> tableView;
    
    @FXML
    private TableColumn<Potflower, Integer> idCol;

    @FXML
    private TableColumn<Potflower, String> nameCol;

    @FXML
    private TableColumn<Potflower, Double> heightCol;

    @FXML
    private TableColumn<Potflower, Double> widthCol;

    @FXML
    private TableColumn<Potflower, Double> weightCol;

    @FXML
    private TableColumn<Potflower, Integer> pcsCol;

    @FXML
    private TableColumn<Potflower, Double> priceCol;

    @FXML
    private TextField searchBar;
        
    ObservableList<Potflower> ob = FXCollections.observableArrayList();
    
    Connection con;
    
    connection conClass = new connection();
    
    
    @FXML
    void deselectRow(MouseEvent event) {
    	tableView.getSelectionModel().clearSelection();
    }
    
    @FXML
    void eventAddProductButton(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	con = conClass.getConnection();    	
    	
    	addProductButton.getScene().getWindow().hide();
    	
    	Stage addProductStage = new Stage();
    	
    	Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/AddProductPage.fxml"));
    	
    	Scene addProductScene = new Scene(root);
    	
    	addProductStage.setScene(addProductScene);
    	
    	addProductStage.show();
    	
    }

    @FXML
    void eventDeleteProductButton(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	con = conClass.getConnection();
    
    	ArrayList<Potflower> ind = new ArrayList<>(tableView.getSelectionModel().getSelectedItems());
    	
    	ObservableList<Potflower> allProducts, selectedProducts;
    	allProducts = tableView.getItems();
    	selectedProducts = tableView.getSelectionModel().getSelectedItems();
    	
       	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setHeaderText("Are you sure you want to delete the selected row(s) ?");
    	Optional<javafx.scene.control.ButtonType> result = alert.showAndWait();
    	
    	if(ind.size() > 0)
    	{
	    	if(result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK)
	    	{
	        		String deleteQuery = "delete from product.potflower where id between ? and ?";

	    			PreparedStatement ps = con.prepareStatement(deleteQuery);
	    			
		            ps.setInt(1, ind.get(0).getID());
		        	ps.setInt(2, ind.get(ind.size()-1).getID());
		        	
		        	ps.execute();
		        	
		        	for(int i = 0 ; i < ind.size(); i++)
		        	selectedProducts.forEach(allProducts :: remove);
		        	
		        	tableView.getSelectionModel().clearSelection();
		        	//refreshTable();
		    }
    	}
    	else if (ind.size() == 0)
    	{
    		Alert noRowSelected = new Alert(AlertType.INFORMATION);
    		noRowSelected.setHeaderText("No row was selected.");
    		noRowSelected.showAndWait();
    	}

    	
    }

    @FXML
    void eventUpdateProductButton(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
    	con = conClass.getConnection();
    	
    	//getting the values of the selected row
    	Potflower selectedRow = tableView.getSelectionModel().getSelectedItem();
    	if(selectedRow == null)
    	{
    		Alert noSelectedRow = new Alert(AlertType.INFORMATION);
    		noSelectedRow.setHeaderText("No row was selected.");
    		noSelectedRow.showAndWait();
    	}
    	else
    	{
    	String selectedRowName = selectedRow.getName();
    	double selectedRowHeight = selectedRow.getHeight();
    	double selectedRowWidth = selectedRow.getWidth();
    	double selectedRowWeight = selectedRow.getWeight();
    	int selectedRowPcs = selectedRow.getPcs();
    	double selectedRowPrice = selectedRow.getPrice();
    	
    	//setting the scene to update a product 
    	updateProductButton.getScene().getWindow().hide();
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Files/UpdateProductPage.fxml"));
    	Parent root = (Parent) loader.load();
    	
    	UpdateProductController displayText = loader.getController();
    	
    	displayText.setTextToUpdate(selectedRowName, selectedRowHeight, selectedRowWidth, selectedRowWeight, selectedRowPcs, selectedRowPrice);
    	displayText.setTextSelectedRow(selectedRowName, selectedRowHeight, selectedRowWidth, selectedRowWeight, selectedRowPcs, selectedRowPrice);
    	Stage updateProductStage = new Stage();
    	
    	//Parent root = FXMLLoader.load(getClass().getResource("/FXML_Files/UpdateProductPage.fxml"));
    	
    	Scene updateProductScene = new Scene(root);
    	
    	updateProductStage.setScene(updateProductScene);
    	
    	updateProductStage.show();
    	}
    	
    }
    
    @FXML
    void searchBarEvent(KeyEvent event) {
    	FilteredList<Potflower> filteredData = new FilteredList<>(ob, e -> true);
    	searchBar.setOnKeyReleased(e ->{
    		searchBar.textProperty().addListener((observableValue, oldValue, newValue) -> {
				filteredData.setPredicate((Predicate<? super Potflower>) ptflwr ->{
					if(newValue == null || newValue.isEmpty())
						return true;
					
					if(ptflwr.getName().toLowerCase().contains(newValue.toLowerCase()))
						return true;
					
					return false;
				});
    		});
    		SortedList<Potflower> sortedData = new SortedList<>(filteredData);
    		sortedData.comparatorProperty().bind(tableView.comparatorProperty());
    		tableView.setItems(sortedData);
    	});
    	
    }
    
   
   	public void initialize(URL location, ResourceBundle rb) {
		
		try {
			con = conClass.getConnection();
			
			String selectAllQuery = "select * from product.potflower";

			ResultSet rs = con.createStatement().executeQuery(selectAllQuery);
			
			while(rs.next())
			{
				//int id = rs.getGeneratedKeys();
				ob.add(new Potflower( rs.getInt("id"), rs.getString("name"), rs.getDouble("height"),
						rs.getDouble("width"), rs.getDouble("weight"), rs.getInt("pcs"), rs.getDouble("price") ) );
			}
			

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		idCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Potflower,Integer>, ObservableValue<Integer>>() {

			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Potflower, Integer> p) {
				// TODO Auto-generated method stub
				System.out.println(tableView.getItems().indexOf(p.getValue()));
				return new ReadOnlyObjectWrapper(tableView.getItems().indexOf(p.getValue()) + 1);
			}
		});
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		heightCol.setCellValueFactory(new PropertyValueFactory<>("height"));
		widthCol.setCellValueFactory(new PropertyValueFactory<>("width"));
		weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
		pcsCol.setCellValueFactory(new PropertyValueFactory<>("pcs"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		tableView.setItems(ob);
		
		
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


	}
    

    


}
