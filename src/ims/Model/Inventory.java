package ims.Model;

import javafx.collections.*;

public class Inventory {
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    
    //Part methods
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    public static boolean deletePart(Part partToRemove){
        return allParts.remove(partToRemove);
    }
    public static Part lookupPart(int partId){
        for (Part searching : allParts) {
            if (searching.getPartId() == partId)
                return searching;
        }
        return null;
    }
    public static Part lookupPart(String name){
        Part found = null;
        for(Part searching : allParts) {
            if (searching.getName().contains(name)) {
                return searching;
            }
        }
        return null;
    }
    public static void updatePart(int partIndex, Part updatedPart){
        allParts.set(partIndex, updatedPart);
    }
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    //Product methods
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    public static boolean deleteProduct(Product productToRemove){
        return allProducts.remove(productToRemove);
    }
    public static Product lookupProduct(int productID){
        for (Product searching : allProducts){
            if (searching.getProductId() == productID)
                return searching;
        }
        return null;
    }
    public static Product lookupProduct(String name){
        for(Product searching : allProducts) {
            if (searching.getName().contains(name))
                return searching;
        }
        return null;
    }
    public static void updateProduct(int productIndex, Product updatedProduct){
        allProducts.set(productIndex, updatedProduct);
    }
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}