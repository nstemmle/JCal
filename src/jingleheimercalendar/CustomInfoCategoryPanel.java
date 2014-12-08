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
public class CustomInfoCategoryPanel extends JPanel{
  private int width = 275;
  private int height = 300;
  private Category cat;
  
  public CustomInfoCategoryPanel(int x, Category c) {
            cat = c;
            
            this.add(new MoreCategoryInfoPanel(cat));
            
            this.setBounds(x, 720-300, 275, 300);
            //this.setBackground(new Color(0,0,0,0));
      //setBackground(Color.BLUE);
      setOpaque(false);
  } 
        
@Override
public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    g2.setColor(cat.getCategoryColor());
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
    g2.fillRoundRect(0, 0, width, height, 25, 25);
    g2.setColor(cat.getSecondaryColor());
    g2.fillRect(0, 35, width, height);
}
        
    
}
