/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jingleheimercalendar;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Brandon
 */
public class CustomInfoTaskWindow extends JDialog{
  
  Task task;
  Color color;
  Color secondaryColor;
  public CustomInfoTaskWindow(Task e) {
    task = e;
    color = task.getCategoryColor();
    secondaryColor=task.getSecondaryColor();
    setUndecorated(true);
    add(new MyPanel2(250,300,this));
    pack();
    setBounds(300, 300, 250, 300);
    setVisible(true);
    setBackground(new Color(0,0,0,0));
    addMouseMotionListener(new MouseMotionListener() {
    private int mx, my;

    @Override
    public void mouseMoved(MouseEvent evt) {
        mx = evt.getXOnScreen();
        my = evt.getYOnScreen();
    }

    @Override
    public void mouseDragged(MouseEvent evt) {
        Point p = getLocation();
        p.x += evt.getXOnScreen() - mx;
        p.y += evt.getYOnScreen() - my;
        mx = evt.getXOnScreen();
        my = evt.getYOnScreen();
        setLocation(p);
    }
    });
  }
  
  public void updateCategoryColor(Color c){
    color= c;
}
  public void updateSecondaryCategoryColor(Color c){
    secondaryColor = c;
}
  
  public class MyPanel2 extends JPanel{
        private int width;
        private int height;
        JDialog parent;
        public MyPanel2(int x,int y, JDialog p){
            parent = p;
            width = x;
            height = y;
            MoreTaskInfoPanel e = new MoreTaskInfoPanel(task,parent);
            this.add(e);
            e.setBounds(0, 0, 250, 300);
            this.setSize(x, y);
            //this.setBackground(new Color(0,0,0,0));
            this.setOpaque(false);
        }
        
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.setColor(color);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
            g2.fillRoundRect(0, 0, width, height, 25, 25);
            g2.setColor(secondaryColor);
            g2.fillRect(0, 35, width, height - 30*2);
            g2.fillRoundRect(0,height - 25*2, width, 25*2, 25, 25);
            //g2.dispose();
        }
        
    }
}
