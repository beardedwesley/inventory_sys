package ims.Model;

public class Outsourced extends Part {
    private String companyName;

    public Outsourced(String oName, double oPrice, int oInStock, int oMin, int oMax, String oCompanyName) {
        super(oName, oPrice, oInStock, oMin, oMax);
        this.companyName = oCompanyName;
    }
    
    public void setCompanyName(String newCompanyName){
        this.companyName = newCompanyName;
    }
    public String getCompanyName(){
        return this.companyName;
    }
}