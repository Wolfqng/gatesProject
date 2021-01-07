package gatesProject;

import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.MouseListener;
    
public class boardMouse implements MouseListener, MouseMotionListener {
    ArrayList<pwrNode> pwrNodes = board.pwrNodes;
    ArrayList<pwrNode> outNodes = board.outNodes;
    ArrayList<chipDTO> chipDTOS = board.chipDTOS;
    ArrayList<cable> cables = board.cables;
    ArrayList<chip> chips = board.chips;
    JFrame f = board.f;
    btn createButton = board.createButton;
    txtInput txt = board.txt;
    lineDTO invisionLine = board.invisionLine;
    int offGrabX = board.offGrabX;
    int offGrabY = board.offGrabY;
    chip selectedC = board.selectedC;
    boolean mouseM = board.mouseM;
    int myOff = 32; //Needed because of a weird glitch, prob something to do with adding mouse listener based on f
    int mxOff = 8;
    public static colorPicker cp = new colorPicker(0, 0, 200, 200);
    public static ArrayList<btn> buttons = new ArrayList<>();
    
    public boardMouse() {
        f.addMouseListener(this);
        f.addMouseMotionListener(this);
    }
    
    public void updateAll() {
       pwrNodes = board.pwrNodes;
       outNodes = board.outNodes;
       chipDTOS = board.chipDTOS;
       cables = board.cables;
       chips = board.chips;
       f = board.f;
       createButton = board.createButton;
       txt = board.txt;
       buttons = board.buttons;
       cp = board.cp;
    }
    
    public void updateAllAfter() {
       board.setPwrNodes(pwrNodes);
       board.setOutNodes(outNodes);
       board.setChipDTOS(chipDTOS);
       board.setCables(cables);
       board.setChips(chips);
       board.setF(f);
       board.setCreateButton(createButton);
       board.setTxt(txt);
       board.setCp(cp);
    }
    
    public void mouseClicked(MouseEvent e) { 
       updateAll();
       int mouseX = e.getX() - mxOff;
       int mouseY = e.getY() - myOff;
       
       for(int i = 0; i < 4; i++)
            if(inRect(mouseX, mouseY, buttons.get(i).getX(), buttons.get(i).getY(), buttons.get(i).getWidth(), buttons.get(i).getHeight())) { 
                if(i == 0) boardActions.addInput();
                else if(i == 1) boardActions.removeInput();
                else if(i == 2) boardActions.addOutput();
                else if(i == 3) boardActions.removeOutput();
            }
       for(pwrNode node : pwrNodes) {
           if(inRect(mouseX, mouseY, node.getX(), node.getY(), node.getSize(), node.getSize()))
               node.changePowered();      
       }  
        
       for(chipDTO cd : chipDTOS) {
           btn chipBtn = cd.getBtn();
           if(inRect(mouseX, mouseY, chipBtn.getX(), chipBtn.getY(), chipBtn.getWidth(), chipBtn.getHeight()))
               chips.add(cd.clicked().setX(f.getWidth() / 2).setY(f.getHeight() / 2));
       }
        
       pwrNode n = boardActions.clickInNode(mouseX, mouseY);
       if(n != null) {
           ArrayList<cable> ctbRemoved = new ArrayList<>();
           for(cable c : n.getCables()) {
               ctbRemoved.add(c);
           }
           for(cable c : ctbRemoved) {
               c.getInp().removeCable(c);
               c.getOut().removeCable(c);
               cables.remove(c);
           }
       }

       if(inRect(mouseX , mouseY, createButton.getX(), createButton.getY(), createButton.getWidth(), createButton.getHeight())){  
            ArrayList<String> outs = boardActions.simulate();
            updateAll();
            btn newBtn = new btn(0, 0, 50, 50, 25, txt.getText(), cp.getColor());
            chipDTO newChip = new chipDTO(pwrNodes.size(), outs.get(0).length(), outs, cp.getColor(), txt.getText(), newBtn);
            chipDTOS.add(newChip);
            cp.randomizeColor();
            boardActions.clearScreen();
            updateAll();
           }
       boardActions.updatePower();
       
       updateAllAfter();
       board.reP();
    } 
    
    public void clearScreen() {
        updateAll();
        
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
        
    public void mouseDragged(MouseEvent e) {
        updateAll();
        int mouseX = e.getX() - mxOff;
        int mouseY = e.getY() - myOff;
        
        if(invisionLine.getInpNode() != null && selectedC == null) {
           invisionLine.setX2(mouseX);
           invisionLine.setY2(mouseY);
        }else invisionLine.setInp(null);
           
        
        for(chip c : chips) 
            if(selectedC == c && mouseM && invisionLine.getInpNode() == null){
                c.setX(mouseX + offGrabX);   
                c.setY(mouseY + offGrabY);
            }
        
       
        boardActions.updatePower();
        
        updateAllAfter();
    }
        
    public void mousePressed(MouseEvent e) { 
        updateAll();
        mouseM = false;
        int mouseX = e.getX() - mxOff;
        int mouseY = e.getY() - myOff;
        for(chip c : chips) {
            if(inRect(mouseX, mouseY, c.getX(), c.getY(), c.getWidth(), c.getHeight())){
                selectedC = c;
                offGrabX = c.getX() - mouseX;
                offGrabY = c.getY() - mouseY;
                mouseM = true;
            }
        }
        
        pwrNode checkNode = boardActions.clickInNode(mouseX, mouseY);
        if(checkNode != null && invisionLine.getInpNode() == null) {
           int co1 = checkNode.getConnectionOffset();
           int cs1 = checkNode.getConnectionSize(); 
           invisionLine.setInp(checkNode);
           invisionLine.setX1(checkNode.getX() + (checkNode.getSize() / 2) + (co1 / 2) + (cs1 / 2));
           invisionLine.setY1(checkNode.getY() + (checkNode.getSize() / 2));
           invisionLine.setX2(mouseX);
           invisionLine.setY2(mouseY);
        }else invisionLine.setInp(null);
        
        boardActions.updatePower();
        
        updateAllAfter();
    } 
        
    public void mouseReleased(MouseEvent e) { 
        updateAll();
        mouseM = false;
        int mouseX = e.getX() - mxOff;
        int mouseY = e.getY() - myOff;
        selectedC = null;
        
        pwrNode checkNode = boardActions.clickInNode(mouseX, mouseY);
        if(checkNode != null && invisionLine.getInpNode() != null) {
           cable ca = new cable(invisionLine.getInpNode(), checkNode);
           cables.add(ca);
           for(chip chi : chips)
                chi.setOutputNodes();
        }
        invisionLine.setInp(null);
        invisionLine.setX1(0);
        invisionLine.setY1(0);
        invisionLine.setX2(0);
        invisionLine.setY2(0);
        
        boardActions.updatePower();
        boardActions.removeChipsOOB();
        
        updateAllAfter();
    }  
    
    public void mouseMoved(MouseEvent e) {
        updateAll();
        int mouseX = e.getX() - mxOff;
        int mouseY = e.getY() - myOff;
        
        for(chip c : chips) {
            for(pwrNode n : c.getInpNodes())
                n.setHovered(false);     
            for(pwrNode n : c.getOutNodes())
                n.setHovered(false);
            if(inRect(mouseX, mouseY, c.getX(), c.getY(),c.getWidth(), c.getHeight()))
                c.setHovered(true);
            else
                c.setHovered(false);
        }
        for(pwrNode n : outNodes)
            n.setHovered(false);
        for(pwrNode n : pwrNodes)
            n.setHovered(false);
        if(boardActions.clickInNode(mouseX, mouseY) != null) boardActions.clickInNode(mouseX, mouseY).setHovered(true);
        
        for(chipDTO cd : chipDTOS) {
           btn chipBtn = cd.getBtn();
           if(inRect(mouseX, mouseY, chipBtn.getX(), chipBtn.getY(), chipBtn.getWidth(), chipBtn.getHeight()))
               chipBtn.setHovered(true);
           else
               chipBtn.setHovered(false);
        }
        
        for(int i = 0; i < 4; i++)
            if(inRect(mouseX, mouseY, buttons.get(i).getX(), buttons.get(i).getY(), buttons.get(i).getWidth(), buttons.get(i).getHeight()))     
                buttons.get(i).setHovered(true);
            else
                buttons.get(i).setHovered(false);
                
        boardActions.updatePower();
        updateAllAfter();
    }
    
    public void mouseEntered(MouseEvent e) {  
    }  
    public void mouseExited(MouseEvent e) {  
    }  
    
    public static boolean inRect(int mx, int my, int x, int y, int width, int height) {
        if(mx > x && mx < x + width && my > y && my < y + height) 
            return true;
        else
            return false;
    }
}

