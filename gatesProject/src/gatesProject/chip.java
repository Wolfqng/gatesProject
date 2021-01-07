package gatesProject;

import java.awt.*;
import java.util.ArrayList;

public class chip
{
    private int inSize;
    private int outSize;
    private int x;
    private int y;
    private ArrayList<String> out = new ArrayList<>();
    private String name;
    private ArrayList<pwrNode> inpNodes = new ArrayList<>();
    private ArrayList<pwrNode> outNodes = new ArrayList<>();
    private int width;
    private int height = 50;
    private int nodeSize = 15;
    private Color c;
    private boolean hovered;
       
    public chip(int x, int y, int inSize, int outSize, ArrayList out, String name, Color c) {
        this.x = x;
        this.y = y;
        this.inSize = inSize;
        this.outSize = outSize;
        this.out = out;
        this.name = name;
        this.c = c;
        setNodes();
    }
    
    private void setNodes() {
        for(int i = 0; i < this.inSize; i++) {
            pwrNode node = new pwrNode(false, nodeSize, this.x - (nodeSize / 2), this.y + (this.height / 2) - (nodeSize / 2) + ((nodeSize + 2) * i) - (((nodeSize + 2) * (this.inSize - 1))) / 2, false);
            node.setConnectionSize(15);
            this.inpNodes.add(node);
        }
        
        for(int i = 0; i < this.outSize; i++) {
            pwrNode node = new pwrNode(false, nodeSize, this.x - (nodeSize / 2) + width, this.y + (this.height / 2) - (nodeSize / 2) + ((nodeSize + 2) * i) - (((nodeSize + 2) * (this.outSize - 1))) / 2, true);
            node.setConnectionSize(15);
            this.outNodes.add(node);
        }
    }
    
    public void updateNodeCords() {
       for(int i = 0; i < this.inSize; i++) {
            inpNodes.get(i).setX(this.x - (nodeSize / 2));
            inpNodes.get(i).setY(this.y + (this.height / 2) - (nodeSize / 2) + ((nodeSize + 2) * i) - (((nodeSize + 2) * (this.inSize - 1))) / 2);
        }
        
        for(int i = 0; i < this.outSize; i++) {
            outNodes.get(i).setX(this.x - (nodeSize / 2) + width);
            outNodes.get(i).setY(this.y + (this.height / 2) - (nodeSize / 2) + ((nodeSize + 2) * i) - (((nodeSize + 2) * (this.outSize - 1))) / 2);
        } 
    }
    
    //Get all inputs
    public static ArrayList<String> genInp(int x) {
        ArrayList<String> inp = new ArrayList<>();
        for(int i = 0; i < Math.pow(2, x); i++)
            inp.add(fillEnds(toBinary(i), '0', x));
        return inp;
    }
    
    //Convert the number to binary
    private static String toBinary(int n) {
        String sb = "";
        while (n > 0) {
        sb += (n % 2);
            n /= 2;
        }
        sb = toReverse(sb);   
        return sb.toString();
    }
      
    //Reverse the String
    private static String toReverse(String s) {
        String n = "";
        for(int i = s.length() - 1; i >= 0; i--)
            n += s.charAt(i);  
        return n;
    }
    
    //Fill the string with the given char
    private static String fillEnds(String s, char c, int t) {
        if(s.length() == t) return s;
        while(s.length() < t)
            s = c + s;
        return s;
    }
    
    //Get corresponding output (Possible to not have to loop through
    //genInp if you have all the possible conbinations in a array)
    public String getOutput(String inp) {
        return out.get(genInp(inSize).indexOf(inp));
    }
    
    public void setOutputNodes() {
        String inp = "";
        for(pwrNode node : inpNodes)
            if(node.isPowered()) inp += "1"; else inp += "0";
            
        String out = getOutput(inp);
        for(int i = 0; i < outNodes.size(); i++)
            if(out.charAt(i) == '1') outNodes.get(i).setPowered(true); else outNodes.get(i).setPowered(false);
        
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public ArrayList<pwrNode> getInpNodes() {
        return this.inpNodes;
    }
    
    public ArrayList<pwrNode> getOutNodes() {
        return this.outNodes;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public Color getColor() {
        return this.c;
    }
    
    public boolean isHovered() {
        return this.hovered;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public chip setX(int x) {
        this.x = x;
        updateNodeCords();
        return this;
    }
    
    public chip setY(int y) {
        this.y = y;
        updateNodeCords();
        return this;
    }
    
    public void setColor(Color c) {
        this.c = c;
    }
    
    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }
}

