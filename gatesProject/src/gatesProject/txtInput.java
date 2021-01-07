package gatesProject;


import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class txtInput extends KeyAdapter
{
    private int x, y, w, h, fontSize;
    private Color c, tc;
    private String text;
    //CHANGE THIS
    private boolean selected = true;;
    
    public txtInput(int x, int y, int w, int fontSize, Color c, Color tc) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = fontSize + 10;
        this.fontSize = fontSize;
        this.c = c;
        this.tc = tc;
        this.text = "";
    }
    
    @Override
    public void keyPressed(KeyEvent event) {
        if(selected) {
            char ch = event.getKeyChar();
            if((int)ch >= 48 && (int)ch <= 122 && !((int)ch >= 58 && (int)ch <= 64) && !((int)ch >= 91 && (int)ch <= 96))
                this.text += Character.toUpperCase(ch);
            if(event.getKeyCode() == KeyEvent.VK_BACK_SPACE && this.text.length() > 0) 
                this.text = this.text.substring(0, this.text.length() - 1);
            board.reP();
        }       
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getWidth() {
        return this.w;
    }
    
    public int getHeight() {
        return this.h;
    }
    
    public int getFontSize() {
        return this.fontSize;
    }
    
    public Color getColor() {
        return this.c;
    }
    
    public Color getTextColor() {
        return this.tc;
    }
    
    public String getText() {
        return this.text;
    }
    
    public boolean isSelected() {
        return this.selected;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public void setWidth(int w) {
        this.w = w;
    }
    
    public void setHeight(int h) {
        this.h = h;
    }
    
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
    
    public void setColor(Color c) {
        this.c = c;
    }
    
    public void setTextColor(Color tc) {
        this.tc = tc;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

