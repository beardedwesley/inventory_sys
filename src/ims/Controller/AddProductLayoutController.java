package ims.Controller;

import ims.IMS;
import ims.Model.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;

public class AddProductLayoutController implements Initializable {
    
    private Stage myStage;
    private ObservableList<Part> tempPartList = FXCollections.observableArrayList();
    private TableView.TableViewSelectionModel<Part> partSelectModel;
    private TableView.TableViewSelectionModel<Part> currPartSelectModel;

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
    private TableView<Part> currPartTbl;
    @FXML
    private TableColumn<Part, Integer> currIdCol;
    @FXML
    private TableColumn<Part, String> currNameCol;
    @FXML
    private TableColumn<Part, Integer> currInventoryCol;
    @FXML
    private TableColumn<Part, Double> currPriceCol;
    
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
    private Button addPartBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.myStage = IMS.currStage;
        
        partListTbl.setItems(IMS.inSys.getAllParts());
        idCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partSelectModel = partListTbl.selectionModelProperty().get();
        
        currPartTbl.setItems(tempPartList);
        currIdCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        currNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        currInventoryCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        currPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        currPartSelectModel = currPartTbl.selectionModelProperty().get();
    }    

    @FXML
    private void searchPartTable(ActionEvent event) {
        if (searchPartTxt.getText().isEmpty()){
            Alert emptySearch = new Alert(Alert.AlertType.ERROR, "Please type something in search field");
            emptySearch.initModality(Modality.WINDOW_MODAL);
            emptySearch.showAndWait();
            return;            
        }
        Part searchedID, searchedName;
        searchedID = IMS.inSys.lookupPart(Integer.parseInt(searchPartTxt.getText()));
        searchedName = IMS.inSys.lookupPart(searchPartTxt.getText());
        
        if (searchedID != null) {
            {
                partSelectModel.clearAndSelect(findPartIndex(searchedID));
                return;
            }
        } else if (searchedName != null) {
                partSelectModel.clearAndSelect(findPartIndex(searchedName));
                return;
        } else {
            Alert notFound = new Alert(Alert.AlertType.ERROR, "No matches found.");
            notFound.initModality(Modality.WINDOW_MODAL);
            notFound.showAndWait();
        }
    }

    @FXML
    private void addPartToProduct(ActionEvent event) {
        if(partSelectModel.isEmpty()) 
            return;
        else {
            tempPartList.add(partSelectModel.getSelectedItem());
        }
    }

    @FXML
    private void removeCurrPart(ActionEvent event) {
        if(!(currPartSelectModel.isEmpty())){
            tempPartList.remove(currPartSelectModel.getSelectedItem());
        }
    }

    @FXML
    private void saveNewProduct(ActionEvent event) {
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
        IMS.inSys.addProduct(newProduct);
        myStage.close();
    }

    @FXML
    private void cancelProductAdd(ActionEvent event) {
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
