package ims.Model;

public abstract class Part {
    private static int partIDCounter = 0;
    
    protected int partId, inStock, min, max;
    protected double price;
    protected String name;
    
    Part(String oName, double oPrice, int oInStock, int oMin, int oMax){
        ++partIDCounter;
        this.partId = partIDCounter;
        this.name = oName;
        this.price = oPrice;
        this.inStock = oInStock;
        this.min = oMin;
        this.max = oMax;
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
    
    public void setPartId(int newPartId){
        this.partId = newPartId;
    }
    public int getPartId(){
        return this.partId;
    }
    
    public boolean equals(Part checker) {
        if (this.name.equals(checker.getName()))
            if (this.price == checker.getPrice())
                if (this.inStock == checker.getInStock())
                    if (this.max == checker.getMax())
                        if (this.min == checker.getMin())
                            if (this.partId == checker.getPartId())
                                return true;
        
        
        return false;
    }
}