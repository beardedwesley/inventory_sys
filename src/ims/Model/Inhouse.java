package ims.Model;

public class Inhouse extends Part {
    private int machineID;

    public Inhouse(String oName, double oPrice, int oInStock, int oMin, int oMax, int oMachineID) {
        super(oName, oPrice, oInStock, oMin, oMax);
        this.machineID = oMachineID;
    }
    
    public void setMachineID(int newMachineID){
        this.machineID = newMachineID;
    }
    public int getMachineID(){
        return this.machineID;
    }
}