package gatesProject;

import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

public class boardActions
{
    static ArrayList<pwrNode> pwrNodes = board.pwrNodes;
    static ArrayList<pwrNode> outNodes = board.outNodes;
    static ArrayList<chipDTO> chipDTOS = board.chipDTOS;
    static ArrayList<cable> cables = board.cables;
    static ArrayList<chip> chips = board.chips;
    static JFrame f = board.f;
    static btn createButton = board.createButton;
    static txtInput txt = board.txt;
    static lineDTO invisionLine = board.invisionLine;
    static int offGrabX = board.offGrabX;
    static int offGrabY = board.offGrabY;
    static chip selectedC = board.selectedC;
    static boolean mouseM = board.mouseM;
    
    public static void updateAllB() {
       pwrNodes = board.pwrNodes;
       outNodes = board.outNodes;
       chipDTOS = board.chipDTOS;
       cables = board.cables;
       chips = board.chips;
       f = board.f;
       createButton = board.createButton;
       txt = board.txt;
    }
    
    public static void updateAllAfter() {
       board.setPwrNodes(pwrNodes);
       board.setOutNodes(outNodes);
       board.setChipDTOS(chipDTOS);
       board.setCables(cables);
       board.setChips(chips);
       board.setF(f);
       board.setCreateButton(createButton);
       board.setTxt(txt);
    }
    
    public static void removeChipsOOB() {
        updateAllB();
        
        int offsetRectX = (int)(f.bounds().width * 0.05); 
        int offsetRectY = (int)(f.bounds().height * 0.2); 
        for(int i = chips.size() - 1; i >= 0; i--) {
            chip c = chips.get(i);
            if(c.getX() < offsetRectX || c.getX() > (int)(f.bounds().width * 0.9) + offsetRectX - c.getWidth() - 15 || c.getY() < offsetRectY || c.getY() > (int)(f.bounds().height * 0.6) + offsetRectY - c.getHeight() + 10){
                ArrayList<cable> ctbRemoved = new ArrayList<>();
                for(pwrNode n : c.getInpNodes())
                        for(cable cab : n.getCables()) 
                            ctbRemoved.add(cab);
                for(pwrNode n : c.getOutNodes())
                        for(cable cab : n.getCables()) 
                            ctbRemoved.add(cab);
                for(cable cab : ctbRemoved) {
                            cab.getInp().removeCable(cab);
                            cab.getOut().removeCable(cab);
                            cables.remove(cab);
                }
                chips.remove(c);
            }
        }
        
        updateAllAfter();
    }
    
    public static void clearScreen() {
        updateAllB();
        
        txt.setText("");
        for(int i = 0; i < outNodes.size(); i++)
            outNodes.get(i).setCables(new ArrayList<>());
        for(int i = 0; i < pwrNodes.size(); i++) {
            pwrNodes.get(i).setCables(new ArrayList<>());
            pwrNodes.get(i).setPowered(false);
        }
        cables = new ArrayList<>();
        chips = new ArrayList<>();
        
        updateAllAfter();
    }
    
    public static void updatePower() {
        updateAllB();
        
        for(chip i : chips){
            for(cable c : cables) {
                 c.isPowered();
            }
            for(chip c : chips){
                for(pwrNode node : c.getInpNodes()) {
                     node.outPowered();
                }
                c.setOutputNodes();
                for(pwrNode node : c.getOutNodes())
                    node.isPowered();
            }
            for(cable c : cables) {
                 c.isPowered();
            }
            for(pwrNode node : outNodes)
                node.outPowered();
        }
        board.reP();
        
        updateAllAfter();
    }
    
    public static ArrayList<String> simulate() {
        updateAllB();
        
        ArrayList<String> output = new ArrayList<>();
        ArrayList<String> inps = chip.genInp(pwrNodes.size());
        boardActions.updatePower();
        for(int s = 0; s < inps.size(); s++) {
            //f.repaint();
            String st = inps.get(s);
            //System.out.println(st);
            for(int i = 0; i < st.length(); i++) {
                pwrNodes.get(i).setPowered(Character.getNumericValue(st.charAt(i)));
    
                boardActions.updatePower();
            }
            boardActions.updatePower();
            String outDone = "";
            for(pwrNode node : outNodes) 
                if(node.isPowered()) 
                    outDone += "1";
                else 
                    outDone += "0";
                    
            output.add(outDone);
            
            //try{Thread.sleep(250);}catch(InterruptedException ex){Thread.currentThread().interrupt();}
        }
        
        updateAllAfter();
        return output;
    }
    
    public static pwrNode clickInNode(int x, int y) {
        updateAllB();
        
        for(chip c : chips) {
            for(pwrNode n : c.getInpNodes())
                if(inNode(n, x, y))
                    return n;
                    
            for(pwrNode n : c.getOutNodes())
                if(inNode(n, x, y))
                    return n;
        }
        
        for(pwrNode n : outNodes)
            if(inNode(n, x, y))
                return n;
        
        for(pwrNode n : pwrNodes)
            if(inNode(n, x, y))
                return n;
        
        updateAllAfter();         
        return null;
    }
        
    public static boolean inNode(pwrNode node, int x, int y) {
        updateAllB();
        
        int co = node.getConnectionOffset();
        int cs = node.getConnectionSize();
        int offY = (node.getSize() - cs) / 2; 
        if(x > node.getX() + co && x < node.getX() + cs + co && y > node.getY() + offY && y < node.getY() + cs + offY)
            return true;
      
        updateAllAfter();    
        return false;
    }  
    
    public static boolean addInput() {
        updateAllB();
        
        newInput();
        updateAllAfter(); 
        board.reP();
        return true;
    }
    
    public static boolean removeInput() {
        updateAllB();
        
        if(pwrNodes.size() < 2) return false;
        if(pwrNodes.get(pwrNodes.size() - 1).getCables().size() > 0)
            for(int c = pwrNodes.get(pwrNodes.size() - 1).getCables().size() - 1; c >= 0; c--) {
                cable ca = pwrNodes.get(pwrNodes.size() - 1).getCables().get(c); 
                ca.getInp().removeCable(ca);
                ca.getOut().removeCable(ca);
                cables.remove(ca);
            }
        
        pwrNodes.remove(pwrNodes.get(pwrNodes.size() - 1));
        updateAllAfter();
        return true;
    }
    
    public static boolean addOutput() {
       updateAllB();
       
       newOutput();
       updateAllAfter(); 
       return true; 
    }
    
    public static boolean removeOutput() {
       updateAllB();
       
       if(outNodes.size() < 2) return false;  
       if(outNodes.get(outNodes.size() - 1).getCables().size() > 0)
            for(int c = outNodes.get(outNodes.size() - 1).getCables().size() - 1; c >= 0; c--) {
                cable ca = outNodes.get(outNodes.size() - 1).getCables().get(c); 
                ca.getInp().removeCable(ca);
                ca.getOut().removeCable(ca);
                cables.remove(ca);
            }
       
       outNodes.remove(outNodes.get(outNodes.size() - 1));
       updateAllAfter(); 
       return true;
    }
    
    public static void newInput() {
        updateAllB();
        
        pwrNode pn = new pwrNode(true, 40, 0, 0, true);
        pn.setPowered(false);
        pn.setConnectionOffset(39);
        pn.setConnectionSize(20);
        pwrNodes.add(pn);
        
        updateAllAfter(); 
    }
    
    public static void newOutput() {
       updateAllB();
       
       pwrNode pn = new pwrNode(true, 40, 0, 0, false);
       pn.setConnectionOffset(-18);
       pn.setConnectionSize(20);
       outNodes.add(pn);
       
       updateAllAfter(); 
    }
}

