package ims;

import ims.Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

public class IMS extends Application {
    
    public static Inventory inSys;
    
    public static Stage mainStage;
    public static Stage currStage;
    public static Part currPart;
    public static Product currProd;
    
    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        currStage = mainStage;
        Parent mainLayout = FXMLLoader.load(getClass().getResource("View/MainLayout.fxml"));
        Scene scene = new Scene(mainLayout);
        mainStage.setScene(scene);
       
        mainStage.setOnCloseRequest(e -> System.exit(0));
        
        mainStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}