package ims.Controller;

import static ims.Controller.MainLayoutController.selectedPartIndex;
import ims.IMS;
import ims.Model.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class ModifyPartLayoutController implements Initializable {

    private Stage myStage;
    private Part currPart;
    
    @FXML
    private ToggleGroup partType;
    @FXML
    private RadioButton inhouseRbtn;
    @FXML
    private RadioButton outsourceRbtn;
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
    private Label companyLbl;
    @FXML
    private TextField companyTxt;
    
    
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myStage = IMS.currStage;
        currPart = MainLayoutController.selectedPart;
        
        if (currPart.getClass().equals(Inhouse.class)) {
            outsourceRbtn.setSelected(false);
            inhouseRbtn.setSelected(true);
            companyTxt.setText("" + ((Inhouse) currPart).getMachineID());
            companyLbl.setText("Machine ID:");
            //change company label
            
        } else {
            companyTxt.setText(((Outsourced) currPart).getCompanyName());
            
        }
        idTxt.setText("" + currPart.getPartId());
        nameTxt.setText(currPart.getName());
        inventoryTxt.setText("" + currPart.getInStock());
        priceTxt.setText("" + currPart.getPrice());
        maxTxt.setText("" + currPart.getMax());
        minTxt.setText("" + currPart.getMin());
    }

    @FXML
    private void saveMod(ActionEvent event) {
        //Check that fields are complete
        if ((nameTxt.getText()).isEmpty() || (priceTxt.getText()).isEmpty() || (inventoryTxt.getText()).isEmpty() || (minTxt.getText()).isEmpty() || (maxTxt.getText()).isEmpty() || (companyTxt.getText()).isEmpty()) {
            Alert incomplete = new Alert(Alert.AlertType.ERROR, "Unable to save Part without all fields complete.");
            incomplete.initModality(Modality.WINDOW_MODAL);
            incomplete.showAndWait();
            return;
        }
        
        //Collected entered info into variables
        String name = nameTxt.getText();
        double price = Double.parseDouble(priceTxt.getText());
        int inventory = Integer.parseInt(inventoryTxt.getText());
        int min = Integer.parseInt(minTxt.getText());
        int max = Integer.parseInt(maxTxt.getText());
        
        //Checking for exceptions
        if (max < min) {
            Alert wrong = new Alert(Alert.AlertType.ERROR, "Maximum must be greater than or equal to Minimum.");
            wrong.initModality(Modality.WINDOW_MODAL);
            wrong.showAndWait();
            return;
        }
        if (inventory < min) {
            Alert wrong = new Alert(Alert.AlertType.ERROR, "Inventory levels cannot be lower than Minimum.");
            wrong.initModality(Modality.WINDOW_MODAL);
            wrong.showAndWait();
            return;
        }
        if (max < inventory) {
            Alert wrong = new Alert(Alert.AlertType.ERROR, "Inventory levels cannot exceed the Maximum.");
            wrong.initModality(Modality.WINDOW_MODAL);
            wrong.showAndWait();
            return;
        }        
        
        //Check and add based on part type
        if (inhouseRbtn.isSelected()){
            int machId = Integer.parseInt(companyTxt.getText());
            ims.Model.Inhouse newPart = new ims.Model.Inhouse(name, price, inventory, min, max, machId);
            newPart.setPartId(currPart.getPartId());
            IMS.inSys.updatePart(selectedPartIndex, newPart);
        }
        else {
            String compName = companyTxt.getText();
            ims.Model.Outsourced newPart = new ims.Model.Outsourced(name, price, inventory, min, max, compName);
            newPart.setPartId(currPart.getPartId());
            IMS.inSys.updatePart(selectedPartIndex, newPart);
        }
                        
        myStage.close();
    }

    @FXML
    private void cancelMod(ActionEvent event) {
        myStage.close();
    }
    
    @FXML
    private void handleInhouseSelected(ActionEvent event) {
        companyLbl.setText("Machine ID:");
    }

    @FXML
    private void handleOutsourcedSelected(ActionEvent event) {
        companyLbl.setText("Company Name:");
    }
    
}
