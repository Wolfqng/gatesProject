package gatesProject;

import java.awt.*;
import java.util.ArrayList;

public class chipDTO
{
    private btn btn;
    private Color c;
    private int inSize;
    private int outSize;
    private ArrayList<String> out = new ArrayList<>();
    private String name;
    
    public chipDTO(int inSize, int outSize, ArrayList<String> out, Color c, String name, btn btn) 
    {
        this.inSize = inSize;
        this.outSize = outSize;
        this.name = name;
        this.c = c;
        this.out = out;
        this.btn = btn;
    }
    
    public btn getBtn() {
        return this.btn;
    }
    
    public chip clicked() {
        return new chip(400, 400, inSize, outSize, out, name, c);
    }
}

