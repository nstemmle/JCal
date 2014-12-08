/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jingleheimercalendar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Brandon
 */
public class EventBox extends JPanel{
    Event event;
    int y;
    int height;
    int width = 160;
    public EventBox(Event e,int hour24px){
        event = e;
        setBackground(new Color(0,0,0,0));
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            SimpleDateFormat dateFormatO = new SimpleDateFormat("hh:mm a");
            
            int hourpx = hour24px/24;
                int minpix = hourpx/60;
                
            String startTime = dateFormat.format(dateFormatO.parse(e.getStartTime()));
            String[] times = startTime.split(":");
            String hours = times[0];
            String minutes = times[1];
            int currentHour = Integer.parseInt(hours);
            int currentMinutes = Integer.parseInt(minutes);
            int totalMinutes = (currentHour *60) + currentMinutes;
            y = currentHour * hourpx + currentMinutes * minpix;
            
            String endTime = dateFormat.format(dateFormatO.parse(e.getEndTime()));
            String[] times2 = endTime.split(":");
            hours = times2[0];
            minutes = times2[1];
            currentHour = Integer.parseInt(hours);
            currentMinutes = Integer.parseInt(minutes);
            totalMinutes = (currentHour *60) + currentMinutes;
            height = currentHour * hourpx + currentMinutes * minpix;
            setBounds(0,y,width,height-y);
        } catch (ParseException ex) {
            Logger.getLogger(EventBox.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    CustomInfoEventWindow c = new CustomInfoEventWindow(event);
                }
            });
        
        JLabel evName = new JLabel(event.getName());
        if(event.getCategory().name.equals("No Category")){
                evName.setForeground(Color.white);
                add(evName);
        }else{
                evName.setForeground(event.getCategoryColor());
                add(evName);
        }
    }
    
    public void paintComponent(Graphics g){
    super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(event.getSecondaryColor());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.fillRoundRect(0, 0, width, height-y, 25, 25); 
    }
    
    
          
}
