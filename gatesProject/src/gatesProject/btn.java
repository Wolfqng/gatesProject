package gatesProject;

import java.awt.*;

public class btn
{
    private int x;
    private int y;
    private Color c;
    private int fontSize;
    private String text;
    private int width;
    private int height;
    private boolean hovered;
    
    public btn(int x, int y, int width, int height, int fontSize, String text, Color c)
    {
        this.x = x;
        this.y = y;
        this.width = width; 
        this.height = height;
        this.fontSize = fontSize;
        this.text = text;
        this.c = c;
    }

    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public Color getColor() {
        return this.c;
    }
    
    public int getFontSize() {
        return this.fontSize;
    }
    
    public String getText() {
        return this.text;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public boolean isHovered() {
        return this.hovered;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public void setColor(Color c) {
        this.c = c;
    }
    
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }   
}

