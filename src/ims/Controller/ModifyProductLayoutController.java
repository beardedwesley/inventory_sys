package ims.Controller;

import static ims.Controller.MainLayoutController.selectedProductIndex;
import ims.IMS;
import ims.Model.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;

public class ModifyProductLayoutController implements Initializable {

    private Stage myStage;
    private Product currProd;
    private ObservableList<Part> tempPartList;
    private TableView.TableViewSelectionModel<Part> partSelectModel;
    private TableView.TableViewSelectionModel<Part> currPartSelectModel;
    
    @FXML
    private TextField idTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField inventoryTxt;
    @FXML
    private TextField priceTxt;
    @FXML
    private TextField maxTxt;
    @FXML
    private TextField minTxt;
    @FXML
    private Button searchPartBtn;
    @FXML
    private TextField searchPartTxt;
    @FXML
    private TableView<Part> partListTbl;
    @FXML
    private TableColumn<Part, Integer> idCol;
    @FXML
    private TableColumn<Part, String> nameCol;
    @FXML
    private TableColumn<Part, Integer> inventoryCol;
    @FXML
    private TableColumn<Part, Double> priceCol;
    @FXML
    private Button addPartBtn;
    @FXML
    private TableView<Part> currPartListTbl;
    @FXML
    private TableColumn<Part, Integer> currIdCol;
    @FXML
    private TableColumn<Part, String> currNameCol;
    @FXML
    private TableColumn<Part, Integer> currInventoryCol;
    @FXML
    private TableColumn<Part, Double> currPriceCol;
    @FXML
    private Button delPartBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.myStage = IMS.currStage;
        currProd = MainLayoutController.selectedProduct;
        tempPartList = currProd.getAssociatedParts();
        
        idTxt.setText("" + currProd.getProductId());
        nameTxt.setText(currProd.getName());
        inventoryTxt.setText("" + currProd.getInStock());
        priceTxt.setText("" + currProd.getPrice());
        maxTxt.setText("" + currProd.getMax());
        minTxt.setText("" + currProd.getMin());
        
        partListTbl.setItems(IMS.inSys.getAllParts());
        idCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partSelectModel = partListTbl.selectionModelProperty().get();
        
        currPartListTbl.setItems(tempPartList);
        currIdCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        currNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        currInventoryCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        currPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        currPartSelectModel = currPartListTbl.selectionModelProperty().get();
    }    

    @FXML
    private void searchPartList(ActionEvent event) {
        //Check that there is something to search for
        if (searchPartTxt.getText().isEmpty()){
            Alert emptySearch = new Alert(Alert.AlertType.ERROR, "Please type something in search field");
            emptySearch.initModality(Modality.WINDOW_MODAL);
            emptySearch.showAndWait();
            return;            
        }
        
        //Check if the search is for numbers only
        boolean flagNumOnly = true;
        for (char tester : searchPartTxt.getText().toCharArray()){
            if (!(Character.isDigit(tester)))
                flagNumOnly = false;
        }
        
        //If numbers only, check for a matching ID first and return if found
        Part searchedID = null;
        if (flagNumOnly) {
            searchedID = IMS.inSys.lookupPart(Integer.parseInt(searchPartTxt.getText()));
        }
        if (searchedID != null){
            partSelectModel.clearAndSelect(findPartIndex(searchedID));
            return;
        }
        
        //Check for any parts whose name contains the searched text
        Part searchedName = null;
        searchedName = IMS.inSys.lookupPart(searchPartTxt.getText());
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
    private void addNewPart(ActionEvent event) {
        if(!(partSelectModel.isEmpty())){
            tempPartList.add(partSelectModel.getSelectedItem());
        }
    }

    @FXML
    private void delCurrPart(ActionEvent event) {
        if(!(currPartSelectModel.isEmpty())){
            tempPartList.remove(currPartSelectModel.getSelectedItem());
        }
    }

    @FXML
    private void saveProdMod(ActionEvent event) {
        //Check that fields are complete
        if ((nameTxt.getText()).isEmpty() || (priceTxt.getText()).isEmpty() || (inventoryTxt.getText()).isEmpty() || (minTxt.getText()).isEmpty() || (maxTxt.getText()).isEmpty()) {
            Alert incomplete = new Alert(Alert.AlertType.ERROR, "Unable to save Product without all fields complete.");
            incomplete.initModality(Modality.WINDOW_MODAL);
            incomplete.showAndWait();
            return;
        }
        if (tempPartList.isEmpty()) {
            Alert incomplete = new Alert(Alert.AlertType.ERROR, "At least one Part must be associated with this Product");
            incomplete.initModality(Modality.WINDOW_MODAL);
            incomplete.showAndWait();
            return;
        }
        
        //Collected entered info into variables
        ims.Model.Product newProduct;
        String name = nameTxt.getText();
        double price = Double.parseDouble(priceTxt.getText());
        int inventory = Integer.parseInt(inventoryTxt.getText());
        int min = Integer.parseInt(minTxt.getText());
        int max = Integer.parseInt(maxTxt.getText());
        
        //Check that product price is higher than sum of associated part's prices
        double partSum = 0.0;
        for (Part sum : tempPartList) {
            partSum += sum.getPrice();
        }
        if (partSum > price) {
            Alert tooCheap = new Alert(Alert.AlertType.ERROR, "This Product's price cannot be less than the cost of it's associated parts.");
            tooCheap.initModality(Modality.WINDOW_MODAL);
            tooCheap.showAndWait();
            return;
        }
        
        //Add product to the Inventory
        newProduct = new Product(name, price, inventory, min, max, tempPartList);
        newProduct.setProductId(currProd.getProductId());
        IMS.inSys.updateProduct(selectedProductIndex, newProduct);
        
        myStage.close();
    }

    @FXML
    private void cancelProdMod(ActionEvent event) {
        myStage.close();
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
    
}
