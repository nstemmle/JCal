/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jingleheimercalendar;


import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author Brandon
 */
public class CustomAddNewCategoryPanel extends JPanel{
  private int width = 275 + 80;
  private int height = 300;
  
  
  public CustomAddNewCategoryPanel(int x) {
            newCategory e = new jingleheimercalendar.newCategory();
            e.setBackground(new java.awt.Color(0,0,0,0));
            this.add(e);
            this.setBounds(x - 275 , 720-300, width, 300);
            this.setBackground(new Color(0,0,0,0));
            
  } 
        
@Override
public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    g2.setColor(new Color(102, 102, 102));
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    g2.fillRoundRect(0, 0, width, height, 25, 25);
    g2.setColor(new Color(238, 238, 238));
    g2.fillRect(0, 35, width, height);
}
        
    
}
