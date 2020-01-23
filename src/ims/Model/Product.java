package ims.Model;

import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedParts;
    private int productId, inStock, min, max;
    private String name;
    private double price;
    
    private static int productIDCounter = 0;
    
    public Product(String oName, double oPrice, int oInventory, int oMin, int oMax, ObservableList<Part> oPartList){
        ++productIDCounter;
        this.productId = productIDCounter;
        this.name = oName;
        this.price = oPrice;
        this.inStock = oInventory;
        this.min = oMin;
        this.max = oMax;
        this.associatedParts = oPartList;
    }
    
    public void setName(String newName){
        this.name = newName;
    }
    public String getName(){
        return this.name;
    }
    
    public void setPrice(double newPrice){
        this.price = newPrice;
    }
    public double getPrice(){
        return this.price;
    }
    
    public void setInStock(int newInStock){
        this.inStock = newInStock;
    }
    public int getInStock(){
        return this.inStock;
    }
    
    public void setMin(int newMin){
        this.min = newMin;
    }
    public int getMin(){
        return this.min;
    }
    
    public void setMax(int newMax){
        this.max = newMax;
    }
    public int getMax(){
        return this.max;
    }
    
    public void addAssociatePart(Part newPart){
        associatedParts.add(newPart);
    }
    public boolean removeAssociatedPart(int partIndex){
        return associatedParts.remove(associatedParts.get(partIndex));
    }
    public ObservableList<Part> getAssociatedParts(){
        return associatedParts;
    }
    public Part lookupAssociatedPart(int partIndex){
        return associatedParts.get(partIndex);
    }
    
    public void setProductId(int newProductID){
        this.productId = newProductID;
    }
    public int getProductId(){
        return this.productId;
    }
    
    public boolean equals(Product checker){
        if (this.name.equals(checker.getName()))
            if (this.price == checker.getPrice())
                if (this.associatedParts.equals(checker.getAssociatedParts()))
                    if (this.inStock == checker.getInStock())
                        if (this.max == checker.getMax())
                            if (this.min == checker.getMin())
                                if (this.productId == checker.getProductId())
                                    return true;
        
        return false;
    }
}
