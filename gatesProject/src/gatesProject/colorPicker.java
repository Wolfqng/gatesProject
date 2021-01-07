package gatesProject;

import java.awt.*;

public class colorPicker
{
    private Color c;
    private int[] bars = new int[3];
    private int x, y, w, h;
    
    public colorPicker(int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.bars[0] = 252;
        this.bars[1] = 186;
        this.bars[2] = 3;
        this.c = new Color(bars[0], bars[1], bars[2]);
    }
    
    public void randomizeColor() {
        for(int i = 0; i < 3; i++) {
            this.bars[i] = (int)(Math.random() * 255);
        }
        this.c = new Color(bars[0], bars[1], bars[2]);
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
    
    public int[] getRGB() {
        return this.bars;
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
    
    public int[] setRGB(int[] rgb) {
        this.c = new Color(rgb[0], rgb[1], rgb[2]);
        return this.bars = rgb;
    }
    
    public Color setColor(Color c) {
        return this.c = c;
    }
}

