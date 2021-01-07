package gatesProject;

import java.awt.*;

public class colorBar
{
    private int x, y, w, h, v;
    private Color c;
    
    public colorBar(int x, int y, int w, int h, int v)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.v = v;
        this.c = Color.BLACK;
    }
    
    public colorBar(int x, int y, int w, int h, int v, Color c)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.v = v;
        this.c = c;
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
    
    public int getValue() {
        return this.v;
    }
    
    public Color getColor() {
        return this.c;
    }
    
    public int setX(int x) {
        return this.x = x;
    }
    
    public int setY(int y) {
        return this.y = y;
    }
    
    public int setWidth(int w) {
        return this.w = w;
    }
    
    public int setHeight(int h) {
        return this.h = h;
    }
    
    public int setValue(int v) {
        return this.v = v;
    }
    
    public Color setColor(Color c) {
        return this.c = c;
    }
}
