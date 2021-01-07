package gatesProject;

import java.util.ArrayList;

public class pwrNode
{
    private boolean powered;
    private int size;
    private int x;
    private int y;
    private int connectionSize;
    private int connectionOffset;
    private boolean isInput, hovered;
    private ArrayList<cable> cc = new ArrayList<>(); //Connected Cables
    
    
    public pwrNode(boolean powered, int size, int x, int y, boolean isInput){
        this.powered = powered;
        this.size = size;
        this.x = x;
        this.y = y;
        this.connectionSize = size;
        this.isInput = isInput;
    }
    
    public boolean isPowered() {
        return this.powered;
    }
    
    public boolean isHovered() {
        return this.hovered;
    }
    
    public int getSize() {   
        return this.size;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public ArrayList<cable> getCables() {
        return this.cc;
    }
    
    //Function for output board 
    public boolean outPowered() {
        for(cable c : cc) {
            if(c.isPowered()) {
                this.powered = true;
                return true;
            }
        }
        
        this.powered = false;
        return false;
    }
    
    public void setPowered(boolean power) {
        this.powered = power;
    }
    
    public void setPowered(int power) {
        if(power == 1)
            this.powered = true;
        else if(power == 0)
            this.powered = false;
        else
            System.out.println(power + " is not a power on/off state");
    }
    
    public void setSize(int size) {
        this.size = size;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }
    
    public void setCables(ArrayList<cable> cc) {
        this.cc = cc;
    }
    
    public void setConnectionSize(int s) {
        this.connectionSize = s;
    }
    
    public void setConnectionOffset(int x) {
        this.connectionOffset = x;
    }
    
    public void setIsInput(boolean isInput) {
        this.isInput = isInput;
    }
    
    public void addCable(cable c) {
        if(this.cc.contains(c)) 
            return;
        else
            this.cc.add(c);
    }
    
    public boolean removeCable(cable c) {
        if(this.cc.contains(c)) {
            this.cc.remove(c);
            return true;
        }
        else
            return false;
    }
    
    public void changePowered() {
       if(this.powered) this.powered = false;
       else if(!this.powered) this.powered = true;
    }
    
    public int getConnectionSize(){
        return this.connectionSize;
    }
    
    public int getConnectionOffset(){
        return this.connectionOffset;
    }
    
    public boolean isInput() {
        return isInput;
    }
}

