package gatesProject;

import java.util.*;
import java.awt.*;

public class drawBoard
{    
    public static void draw(Graphics g, Frame f, ArrayList<pwrNode> pwrNodes, ArrayList<pwrNode> outNodes, ArrayList<chipDTO> chipDTOS, btn createButton, ArrayList<chip> chips, lineDTO invisionLine, ArrayList<cable> cables, colorPicker cp, ArrayList<btn> buttons) {
        drawRectangle(g, f); 
        drawOutputs(g, f, outNodes.size(), outNodes);
        drawInputs(g, f, pwrNodes.size(), pwrNodes);
        drawCables(g, cables.size(), cables);
        drawChips(g, chips.size(), chips);
        drawChipButtons(g, f, chipDTOS.size(), chipDTOS);
        drawCreateButton(g, f, createButton);
        drawColorPicker(g, f, cp);
        drawNodeButtons(g, f, buttons);
        
        //Draw invisionLine
        g.setColor(Color.BLACK);
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(5));
        g2.drawLine(invisionLine.getX1(), invisionLine.getY1(), invisionLine.getX2(), invisionLine.getY2());
    }
    
    public static void drawNodeButtons(Graphics g, Frame f, ArrayList<btn> buttons) {
        for(btn b : buttons) {
            g.setColor(new Color(70, 70, 70));
            g.fillRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
            g.setFont(new Font("TimesRoman", Font.BOLD, b.getFontSize()));
            g.setColor(Color.BLACK);
            if(b.isHovered()) g.setColor(new Color(100, 100, 100));
            int soff = (b.getWidth() - g.getFontMetrics().stringWidth(b.getText())) / 2;
            g.drawString(b.getText(), b.getX() + soff, b.getY() + b.getHeight() + 5);
        }
    }
    
    public static void drawColorPicker(Graphics g, Frame f, colorPicker cp) {
        int[] rgb = cp.getRGB();
        //System.out.println(rgb[1 - 1] / 255.0 * (cp.getWidth() * 0.8));
        int ss = 5; //Stroke size
        int bx = f.bounds().width - cp.getWidth() - (int)(f.bounds().width * 0.05) - (ss * 3);
        int by = ss;
        g.setColor(Color.BLACK);
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(ss));
        g2.drawRect(bx, by, cp.getWidth(), cp.getHeight());
        
        for(int i = 1; i < 4; i++) {
            g.fillRect(bx + (int)(cp.getWidth() * 0.1), by + (i * (int)(cp.getHeight() * 0.2)), (int)(cp.getWidth() * 0.8), 3);
            g.fillRect((int)(bx + (int)(cp.getWidth() * 0.1) + (rgb[i - 1] / 255.0 * (cp.getWidth() * 0.8))), by + (i * (int)(cp.getHeight() * 0.2)) - 10, 5, 20);
        }
    }
    
    public static void drawCreateButton(Graphics g, Frame f, btn createButton) {
        g.setFont(new Font("TimesRoman", Font.BOLD, createButton.getFontSize()));
        int width = g.getFontMetrics().stringWidth(createButton.getText()) + 20;
        createButton.setWidth(width);
        createButton.setY((int)(f.bounds().height * 0.6) + (int)(f.bounds().height * 0.2) + 60);
        createButton.setX((int)(f.bounds().width * 0.05));
        g.setColor(createButton.getColor());
        g.fillRect(createButton.getX(), createButton.getY(), createButton.getWidth(), createButton.getHeight());
        g.setColor(Color.BLACK);
        g.drawString(createButton.getText(), createButton.getX() + 10, createButton.getY() + 37);
    }
    
    public static void drawChipButtons(Graphics g, Frame f, int size, ArrayList<chipDTO> chipDTOS) {
        for(int i = 0; i < size; i++) {
            chipDTO cd = chipDTOS.get(i);
            btn chipBtn = cd.getBtn();
            g.setFont(new Font("TimesRoman", Font.BOLD, chipBtn.getFontSize())); 
            int width = g.getFontMetrics().stringWidth(chipBtn.getText()) + 20;
            chipBtn.setWidth(width);
            chipBtn.setY((int)(f.bounds().height * 0.6) + (int)(f.bounds().height * 0.2) + 10);
            if(i != 0) chipBtn.setX(chipDTOS.get(i - 1).getBtn().getX() + chipDTOS.get(i - 1).getBtn().getWidth()); else chipBtn.setX((int)(f.bounds().width * 0.05));
            int hovOff = 0;
            if(cd.getBtn().isHovered()) hovOff = 50; else hovOff = 0;
            int r = chipBtn.getColor().getRed() + hovOff;
            int gr = chipBtn.getColor().getGreen() + hovOff;
            int b = chipBtn.getColor().getBlue() + hovOff;
            if(r > 255) r = 255;
            if(gr > 255) gr = 255;
            if(b > 255) b = 255;
            g.setColor(new Color(r, gr, b));
            g.fillRect(chipBtn.getX(), chipBtn.getY(), chipBtn.getWidth(), chipBtn.getHeight());
            g.setColor(Color.BLACK);
            g.drawString(chipBtn.getText(), chipBtn.getX() + 10, chipBtn.getY() + 37);
        }
    }
    
    public static void drawChips(Graphics g, int size, ArrayList<chip> chips) {
        for(int i = 0; i < size; i++) {
            int xc = chips.get(i).getX();
            int yc = chips.get(i).getY();
            int fontSize = 25;
            String name = chips.get(i).getName();
            int nodeAmt = 0;
            int exHeight = 20;
            if(chips.get(i).getInpNodes().size() > 1 || chips.get(i).getOutNodes().size() > 1) exHeight = 5;
            if(chips.get(i).getInpNodes().size() > chips.get(i).getOutNodes().size()) nodeAmt = chips.get(i).getInpNodes().size(); else nodeAmt = chips.get(i).getOutNodes().size();
            chips.get(i).setHeight(nodeAmt * (chips.get(i).getInpNodes().get(0).getSize() + 2) + exHeight);
            int height = chips.get(i).getHeight();
            g.setFont(new Font("TimesRoman", Font.BOLD, fontSize)); 
            int width = g.getFontMetrics().stringWidth(name) + 20;
            chips.get(i).setWidth(width);
            chips.get(i).updateNodeCords();
            ArrayList<pwrNode> cInpNodes = chips.get(i).getInpNodes();
            ArrayList<pwrNode> cOutNodes = chips.get(i).getOutNodes();
            
            int hovOffC = 0;
            if(chips.get(i).isHovered()) hovOffC = 50; else hovOffC = 0;
            int r = chips.get(i).getColor().getRed() + hovOffC;
            int gr = chips.get(i).getColor().getGreen() + hovOffC;
            int b = chips.get(i).getColor().getBlue() + hovOffC;
            if(r > 255) r = 255;
            if(gr > 255) gr = 255;
            if(b > 255) b = 255;
            g.setColor(new Color(r, gr, b));
            g.fillRect(xc, yc, width, height);
            
            g.setColor(Color.BLACK);
            g.drawString(name, xc + 10, yc + (height / 2) + (fontSize / 2));
            for(pwrNode node : cInpNodes) {
                int hovOff = 0;
                if(node.isHovered()) hovOff = 100; else hovOff = 0; 
                if(node.outPowered()) g.setColor(new Color(255, hovOff, hovOff)); else g.setColor(new Color(hovOff, hovOff, hovOff));
                g.fillOval(node.getX(), node.getY(), node.getSize(), node.getSize());
            }
                
            for(pwrNode node : cOutNodes){
                int hovOff = 0;
                if(node.isHovered()) hovOff = 100; else hovOff = 0; 
                if(node.isPowered()) g.setColor(new Color(255, hovOff, hovOff)); else g.setColor(new Color(hovOff, hovOff, hovOff));
                g.fillOval(node.getX(), node.getY(), node.getSize(), node.getSize());
            }
            
        }
    }
    
    public static void drawCables(Graphics g, int size, ArrayList<cable> cables){
        for(int i = 0; i < size; i++) {
            if(cables.get(i).isPowered()) g.setColor(Color.RED); else g.setColor(Color.BLACK);
            pwrNode p1 = cables.get(i).getInp();
            pwrNode p2 = cables.get(i).getOut();
            Graphics2D g2 = (Graphics2D)g;
            g2.setStroke(new BasicStroke(5));
            int co1 = p1.getConnectionOffset();
            int cs1 = p1.getConnectionSize();
            int co2 = p2.getConnectionOffset();
            int cs2 = p2.getConnectionSize();
            g2.drawLine(p1.getX() + (p1.getSize() / 2) + (co1 / 2) + (cs1 / 2), p1.getY() + (p1.getSize() / 2), p2.getX() + (p2.getSize() / 2) + co2 - (cs2 / 2), p2.getY() + (p2.getSize() / 2));
        }
    }
    
    public static void drawInputs(Graphics g, Frame f, int size, ArrayList<pwrNode> pwrNodes){
        for(int i = 0; i < size; i++) {
            pwrNode pn = pwrNodes.get(i);
            int diam = pn.getSize();
            int x = (int)(f.bounds().width * 0.05) - ((diam - 10) / 2);
            int y = (int)(f.bounds().height / 2) + ((100 * i) / 2) - ((50 * size) / 2) + (diam / 4);
            pn.setX(x);
            pn.setY(y);
            if(pn.isPowered()) g.setColor(Color.RED); else g.setColor(Color.BLACK);
            g.fillOval(x, y, diam, diam);
            int hovOff = 0;
            if(pn.isHovered()) hovOff = 100; else hovOff = 0; 
            if(pn.isPowered()) g.setColor(new Color(255, hovOff, hovOff)); else g.setColor(new Color(hovOff, hovOff, hovOff));
            g.fillOval(x + pn.getConnectionOffset(), y + (pn.getSize() - pn.getConnectionSize()) / 2, pn.getConnectionSize(), pn.getConnectionSize());
        }
    }
    
    public static void drawOutputs(Graphics g, Frame f, int size, ArrayList<pwrNode> outNodes){
        for(int i = 0; i < size; i++) {
            pwrNode pn = outNodes.get(i);
            int diam = pn.getSize();
            int offsetRectX = (int)(f.bounds().width * 0.05);
            int x = (int)(f.bounds().width * 0.9) + offsetRectX - 20 - ((diam - 10) / 2);
            int y = (int)(f.bounds().height / 2) + ((100 * i) / 2) - ((50 * size) / 2) + (diam / 4);
            pn.setX(x);
            pn.setY(y);
            if(pn.outPowered()) g.setColor(Color.RED); else g.setColor(Color.BLACK);
            g.fillOval(x, y, diam, diam);
            int hovOff = 0;
            if(pn.isHovered()) hovOff = 100; else hovOff = 0; 
            if(pn.outPowered()) g.setColor(new Color(255, hovOff, hovOff)); else g.setColor(new Color(hovOff, hovOff, hovOff));
            g.fillOval(x + pn.getConnectionOffset(), y + (pn.getSize() - pn.getConnectionSize()) / 2, pn.getConnectionSize(), pn.getConnectionSize());
        }
    }
    
    public static void drawRectangle(Graphics g, Frame f){
        int offsetRectX = (int)(f.bounds().width * 0.05); 
        int offsetRectY = (int)(f.bounds().height * 0.2); 
        g.setColor(new Color(80, 80, 80));
        //TOP
        g.fillRect(offsetRectX, offsetRectY, (int)(f.bounds().width * 0.9) - 10, 10);
        //LEFT
        g.fillRect(offsetRectX, offsetRectY, 10, (int)(f.bounds().height * 0.6));
        //BOTTOM
        g.fillRect(offsetRectX, (int)(f.bounds().height * 0.6) + offsetRectY, (int)(f.bounds().width * 0.9) - 10, 10);
        //RIGHT
        g.fillRect((int)(f.bounds().width * 0.9) + offsetRectX - 20, offsetRectY, 10, (int)(f.bounds().height * 0.6));
    }

}
