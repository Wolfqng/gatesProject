package gatesProject;

import java.util.*;
import java.awt.*;
import javax.swing.*;

import gatesProject.txtInput;

public class board extends JPanel {
    public static JFrame f = new JFrame("screen");
    public static JPanel jp = new JPanel();
    public static ArrayList<pwrNode> pwrNodes = new ArrayList<>();
    public static ArrayList<pwrNode> outNodes = new ArrayList<>();
    public static ArrayList<cable> cables = new ArrayList<>();
    public static ArrayList<chip> chips = new ArrayList<>();
    public static ArrayList<chipDTO> chipDTOS = new ArrayList<>();
    public static btn createButton;
    public static boolean mouseM = false;
    public static int offGrabX = 0;
    public static int offGrabY = 0;
    public static chip selectedC = null;
    public static pwrNode selectedN = null;
    public static lineDTO invisionLine = new lineDTO(0, 0, 0, 0);
    public static txtInput txt = new txtInput(0, 0, 100, 100, new Color(70, 70, 70), Color.BLACK);
    public static colorPicker cp = new colorPicker(0, 0, 200, 200);
    public static ArrayList<btn> buttons = new ArrayList<>();
    
    public board() {
        f.addKeyListener(txt);
    }
    
    //Figure out why the hell you did this
    public static void reP() {
        f.repaint();
    }
    
    public void paintComponent(Graphics g){
        for(chip c : chips){
            for(pwrNode node : c.getInpNodes()) {
                 node.outPowered();
            }
            c.setOutputNodes();
        }
        for(cable c : cables) {
             c.isPowered();
        }
        for(pwrNode node : outNodes)
            node.outPowered();
        
        super.paintComponent(g);
        this.setBackground(new Color(70, 70, 70));
        drawBoard.draw(g, f, pwrNodes, outNodes, chipDTOS, createButton, chips, invisionLine, cables, cp, buttons);
        
        g.setFont(new Font("TimesRoman", Font.BOLD, txt.getFontSize()));
        g.setColor(txt.getColor());
        txt.setWidth(g.getFontMetrics().stringWidth(txt.getText()) + 20);
        g.fillRect(txt.getX(), txt.getY(), txt.getWidth(), txt.getHeight());
        g.setColor(txt.getTextColor());
        g.drawString(txt.getText(), txt.getX(), txt.getY() + (txt.getHeight() / 2) + (txt.getFontSize() / 2));
        //g.fillRect(txt.getX() + 10 + g.getFontMetrics().stringWidth(txt.getText()), txt.getY() + (txt.getFontSize() / 2), 5, txt.getFontSize() / 2);
    }
        
    public static void main(String[] args){
        ImageIcon img = new ImageIcon("./hiffinLogo.png");
        f.setIconImage(img.getImage());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        board b = new board();
        boardMouse bm = new boardMouse();
        f.add(b);
        f.setSize(1920, 1080);
        f.setVisible(true);
        //f.add(jp);
        
        
        cp.randomizeColor();
        txt.setX((int)(f.bounds().width * 0.05));
        txt.setY((int)(f.bounds().height * 0.2) - txt.getHeight());
        createButton = new btn(0, 0, 50, 50, 25, "CREATE", new Color(214, 214, 214));
        btn andButton = new btn(0, 0, 50, 50, 25, "AND", new Color(252, 186, 3));
        btn notButton = new btn(0, 0, 50, 50, 25, "NOT", new Color(252, 3, 65));
        chipDTO andChip = new chipDTO(2, 1, new ArrayList<String>(Arrays.asList("0", "0", "0", "1")), new Color(252, 186, 3), "AND", andButton);
        chipDTOS.add(andChip);
        chipDTO notChip = new chipDTO(1, 1, new ArrayList<String>(Arrays.asList("1", "0")), new Color(252, 3, 65), "NOT", notButton);
        chipDTOS.add(notChip);
        
        //node buttons font size
        int nbfs  = 90;
        buttons.add(new btn((int)(f.bounds().width * 0.05) - 50,(int)(f.bounds().height * 0.2), 50, 50, nbfs, "+", Color.GREEN));
        buttons.add(new btn((int)(f.bounds().width * 0.05) - 50,(int)(f.bounds().height * 0.2) + (int)(f.bounds().height * 0.6) - 50 + 10, 50, 50, nbfs, "-", Color.RED));
        buttons.add(new btn((int)(f.bounds().width * 0.05) - 10 + (int)(f.bounds().width * 0.9),(int)(f.bounds().height * 0.2), 50, 50, nbfs, "+", Color.GREEN));
        buttons.add(new btn((int)(f.bounds().width * 0.05) - 10 + (int)(f.bounds().width * 0.9),(int)(f.bounds().height * 0.2) + (int)(f.bounds().height * 0.6) - 50 + 10, 50, 50, nbfs, "-", Color.RED));
    
        
        for(int i = 0; i < 4; i++) {
            boardActions.newInput();
        }
        
        for(int i = 0; i < 2; i++) {
            boardActions.newOutput();
        }
    }
    
    public static JFrame setF(JFrame pf) {
        return f = pf;
    }
    
    public static JPanel setJp(JPanel pjp) {
        return jp = pjp;
    }
    
    public static ArrayList<pwrNode> setPwrNodes(ArrayList<pwrNode> ppwrNodes) {
        return pwrNodes = ppwrNodes;
    }
    
    public static ArrayList<pwrNode> setOutNodes(ArrayList<pwrNode> poutNodes) {
        return outNodes = poutNodes;
    }
    
    public static ArrayList<cable> setCables(ArrayList<cable> pcables) {
        return cables = pcables;
    }
    
    public static ArrayList<chip> setChips(ArrayList<chip> pchips) {
        return chips = pchips;
    }
    
    public static ArrayList<chipDTO> setChipDTOS(ArrayList<chipDTO> pchipDTOS) {
        return chipDTOS = pchipDTOS;
    }
    
    public static btn setCreateButton(btn pcreateButton) {
        return createButton = pcreateButton;
    }
    
    public static boolean setMouseM(boolean pmouseM) {
        return mouseM = pmouseM;
    }
    
    public static int setOffGrabX(int poffGrabX) {
        return offGrabX = poffGrabX;
    }
    
    public static int setOffGrabY(int poffGrabY) {
        return offGrabY = poffGrabY;
    }
    
    public static chip setSelectedC(chip pselectedC) {
        return selectedC = pselectedC;
    }
    
    public static pwrNode setSelectedN(pwrNode pselectedN) {
        return selectedN = pselectedN;
    }
    
    public static lineDTO setInvisionLine(lineDTO pinvisionLine) {
        return invisionLine = pinvisionLine;
    }
    
    public static txtInput setTxt(txtInput ptxt) {
        return txt = ptxt;
    }
    
    public static colorPicker setCp(colorPicker pcp) {
        return cp = pcp;
    }
    
    public static ArrayList<btn> setButtons(ArrayList<btn> pbuttons) {
        return buttons = pbuttons;
    }
}

