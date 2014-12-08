/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jingleheimercalendar;

import java.awt.Color;

/**
 *
 * @author Brandon
 */
public class Category {
    public String name;
    public Color primaryColor;
    public Color secondaryColor;
    
    public Category(String n, Color p){
        name = n;
        primaryColor = p;
    }
    
    public String getName(){
        return name;
    }
    
    public Color getCategoryColor(){
        return primaryColor;
    }
    
    public Color getSecondaryColor(){
        float[] hsbValues = new float[3];
            Color.RGBtoHSB(primaryColor.getRed(), primaryColor.getGreen(), primaryColor.getBlue(), hsbValues);
            hsbValues[1] = hsbValues[1]*.3f;
        return Color.getHSBColor(hsbValues[0],hsbValues[1],hsbValues[2]); 
    }
}
