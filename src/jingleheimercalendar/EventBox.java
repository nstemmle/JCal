/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jingleheimercalendar;

import java.awt.*;
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
            
            double hourpx = hour24px/24;
            double minpix = hourpx/60;
                
            String startTime = dateFormat.format(dateFormatO.parse(e.getStartTime()));
            String[] times = startTime.split(":");
            String hours = times[0];
            String minutes = times[1];
            int currentHour = Integer.parseInt(hours);
            int currentMinutes = Integer.parseInt(minutes);
            int totalMinutes = (currentHour *60) + currentMinutes;
            double holder = currentHour*hourpx + currentMinutes*minpix;
            y = (int)holder;
            
            String endTime = dateFormat.format(dateFormatO.parse(e.getEndTime()));
            String[] times2 = endTime.split(":");
            hours = times2[0];
            minutes = times2[1];
            currentHour = Integer.parseInt(hours);
            currentMinutes = Integer.parseInt(minutes);
            totalMinutes = (currentHour *60) + currentMinutes;
            holder = currentHour * hourpx + currentMinutes * minpix - y;
            height = (int)holder;
            setBounds(0,y,width,height);
        } catch (ParseException ex) {
            Logger.getLogger(EventBox.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    CustomInfoEventWindow c = new CustomInfoEventWindow(event);
                }
            });

        if(height >= 25){
            JLabel evName = new JLabel(event.getName());
            evName.setFont(JingleheimerCalendar.defaultFont.deriveFont(Font.BOLD,12f));

            if(event.getCategory().name.equals("No Category")){
                evName.setForeground(Color.white);
                add(evName);
            }else{
                evName.setForeground(new Color(69,69,69));
                add(evName);
            }
        }
    }
    
    public void paintComponent(Graphics g){
    super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(event.getCategoryColor());
        g2.fillRoundRect(0, 0, width, height, 25, 25);

        if(event.getCategory().name.equals("No Category")){
            g2.setColor(new Color(123,123,123));
        }else{
            g2.setColor(event.getSecondaryColor());
        }

        g2.fillRoundRect(4,4, width - 8, height - 8, 23, 23);

    }
    
    
          
}
