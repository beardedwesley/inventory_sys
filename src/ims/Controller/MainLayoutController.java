package ims.Controller;

import ims.IMS;
import ims.Model.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;

public class MainLayoutController implements Initializable{
    
    @FXML
    private TableView<Part> partTbl;
     
    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;
    
    @FXML
    private TableColumn<Part, Integer> partInventoryCol;
    
    @FXML
    private TableColumn<Part, Double> partPriceCol;

    
    
    @FXML
    private TableView<Product> productTbl;
    
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    
    @FXML
    private TableColumn<Product, String> productNameCol;
    
    @FXML
    private TableColumn<Product, Integer> productInventoryCol;
    
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    
    
    

    @FXML
    private Button productDelBtn;

    @FXML
    private Button productSearchBtn;

    @FXML
    private TextField partSearchTxt;

    @FXML
    private Button productModBtn;

    @FXML
    private Button partAddBtn;

    @FXML
    private TextField productSearchTxt;

    @FXML
    private Button partModBtn;

    @FXML
    private Button partDelBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button productAddBtn;

    @FXML
    private Button partSearchBtn;

    private TableView.TableViewSelectionModel<Part> partSelectModel;
    private TableView.TableViewSelectionModel<Product> productSelectModel;
    protected static Part selectedPart;
    protected static int selectedPartIndex;
    protected static Product selectedProduct;
    protected static int selectedProductIndex;
    
    
    @FXML
    private void handlePartSearch(ActionEvent event) {
        //Check that there is something to search for
        if (partSearchTxt.getText().isEmpty()){
            Alert emptySearch = new Alert(Alert.AlertType.ERROR, "Please type something in search field");
            emptySearch.initModality(Modality.WINDOW_MODAL);
            emptySearch.showAndWait();
            return;            
        }
        
        //Check if the search is for numbers only
        boolean flagNumOnly = true;
        for (char tester : partSearchTxt.getText().toCharArray()){
            if (!(Character.isDigit(tester)))
                flagNumOnly = false;
        }
        
        //If numbers only, check for a matching ID first and return if found
        Part searchedID = null;
        if (flagNumOnly) {
            searchedID = IMS.inSys.lookupPart(Integer.parseInt(partSearchTxt.getText()));
        }
        if (searchedID != null){
            partSelectModel.clearAndSelect(findPartIndex(searchedID));
            return;
        }
        
        //Check for any parts whose name contains the searched text
        Part searchedName = null;
        searchedName = IMS.inSys.lookupPart(partSearchTxt.getText());
        if (searchedName != null) {
            partSelectModel.clearAndSelect(findPartIndex(searchedName));
            return;
        }
        
        //No matching parts found
        Alert notFound = new Alert(Alert.AlertType.ERROR, "No matches found.");
        notFound.initModality(Modality.WINDOW_MODAL);
        notFound.showAndWait();
    }
    
    @FXML
    private void handleProductSearch(ActionEvent event) {
        //Check that there is something to search for
        if (productSearchTxt.getText().isEmpty()){
            Alert emptySearch = new Alert(Alert.AlertType.ERROR, "Please type something in search field");
            emptySearch.initModality(Modality.WINDOW_MODAL);
            emptySearch.showAndWait();
            return;            
        }
        
        //Check if the search is for numbers only
        boolean flagNumOnly = true;
        for (char tester : productSearchTxt.getText().toCharArray()){
            if (!(Character.isDigit(tester)))
                flagNumOnly = false;
        }
        
        //If numbers only, check for a matching ID first and return if found
        Product searchedID = null;
        if (flagNumOnly) {
            searchedID = IMS.inSys.lookupProduct(Integer.parseInt(productSearchTxt.getText()));
        }
        if(searchedID != null){
            productSelectModel.clearAndSelect(findProductIndex(searchedID));
            return;
        }
        
        //Check for any products whose name contains the searched text
        Product searchedName = null;
        searchedName = IMS.inSys.lookupProduct(productSearchTxt.getText());
        if (searchedName != null) {
            productSelectModel.clearAndSelect(findProductIndex(searchedName));
            return;
        }

        //No matching products found
        Alert notFound = new Alert(Alert.AlertType.ERROR, "No matches found.");
        notFound.initModality(Modality.WINDOW_MODAL);
        notFound.showAndWait();
    }
    
    @FXML
    private void handlePartAdd(ActionEvent event) throws IOException {
        Stage addPart = new Stage();
        IMS.currStage = addPart;
        addPart.initModality(Modality.APPLICATION_MODAL);
        Parent addPartScene = FXMLLoader.load(getClass().getResource("/ims/View/AddPartLayout.fxml"));
        addPart.setScene(new Scene(addPartScene));
        addPart.showAndWait();
    }
    
    @FXML
    private void handleProductAdd(ActionEvent event) throws IOException{
        if (IMS.inSys.getAllParts().isEmpty())
        {
            Alert incomplete = new Alert(Alert.AlertType.ERROR, "There must be at least one Part available to associate with a new Product.");
            incomplete.initModality(Modality.WINDOW_MODAL);
            incomplete.showAndWait();
            return;
        }
        
        Stage addProduct = new Stage();
        IMS.currStage = addProduct;
        addProduct.initModality(Modality.APPLICATION_MODAL);
        Parent addProdScene = FXMLLoader.load(getClass().getResource("/ims/View/AddProductLayout.fxml"));
        addProduct.setScene(new Scene(addProdScene));
        addProduct.showAndWait();
    }
    
    @FXML
    private void handlePartMod(ActionEvent event) throws IOException{
        if (partSelectModel.isEmpty())
            return;
        
        selectedPart = partSelectModel.getSelectedItem();
        selectedPartIndex = partSelectModel.getSelectedIndex();
        
        Stage modPart = new Stage();
        IMS.currStage = modPart;
        modPart.initModality(Modality.APPLICATION_MODAL);
        Parent modPartScene = FXMLLoader.load(getClass().getResource("/ims/View/ModifyPartLayout.fxml"));
        modPart.setScene(new Scene(modPartScene));
        modPart.showAndWait();
    }
    
    @FXML
    private void handleProductMod(ActionEvent event) throws IOException{
        if (productSelectModel.isEmpty())
            return;
        
        selectedProduct = productSelectModel.getSelectedItem();
        selectedProductIndex = productSelectModel.getSelectedIndex();
        
        Stage modProduct = new Stage();
        IMS.currStage = modProduct;
        modProduct.initModality(Modality.APPLICATION_MODAL);
        Parent modProdScene = FXMLLoader.load(getClass().getResource("/ims/View/ModifyProductLayout.fxml"));
        modProduct.setScene(new Scene(modProdScene));
        modProduct.showAndWait();
    }
    
    @FXML
    private void handlePartDelete(ActionEvent event) {
        IMS.inSys.deletePart(partSelectModel.getSelectedItem());
    }
    
    @FXML
    private void handleProductDelete(ActionEvent event) {
        IMS.inSys.deleteProduct(productSelectModel.getSelectedItem());
    }
    
    @FXML
    private void handleQuit(ActionEvent event) {
        System.exit(0);
    }
    
    private int findPartIndex(Part part){
        ObservableList<Part> allParts = IMS.inSys.getAllParts();
        for (int i = 0; i < allParts.size(); i = i + 1){
            if (allParts.get(i).equals(part)) {
                return i;
            }
        }
        return -1;
    }
    
    private int findProductIndex(Product product){
        ObservableList<Product> allProducts = IMS.inSys.getAllProducts();
        for (int i = 0; i < allProducts.size(); i = i + 1){
            if (allProducts.get(i).equals(product)) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        partTbl.setItems(IMS.inSys.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partSelectModel = partTbl.selectionModelProperty().get();
        
        productTbl.setItems(IMS.inSys.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productSelectModel = productTbl.selectionModelProperty().get();
                
    }
    
}