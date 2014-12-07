/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jingleheimercalendar;

import java.awt.Color;
import java.util.Date;

/**
 *
 * @author Brandon
 */
public class Event {
    private Date date;
    private String startTime;
    private String endTime;
    private String name;
    private String info;
    private Category category;
    
    public Event(String n, String i, String s, String e, Category c, Date d){
        date = d;
        name = n;
        info = i;
        startTime = s;
        endTime = e;
        category = c;
    }
    
    public void setStartTime(String s){
        startTime = s;
    }
    
    public void setEndTime(String s){
        endTime = s;
    }
    
    public void setName(String s){
        name = s;
    }
    
    public void setDate(Date s){
        date = s;
    }
    
    public void setInfo(String s){
        info = s;
    }
    
    public void setCategory(Category s){
        category = s;
    }
    
    public String getStartTime(){
        return startTime;
    }
    
    public String getEndTime(){
        return endTime;
    }
    
    public String getName(){
        return name;
    }
    
    public String getInfo(){
        return info;
    }
    
    public Category getCategory(){
        return category;
    }
    
    public Color getCategoryColor(){
        return category.getCategoryColor();
    }
    public Date getDate(){
        return date;
    }
    
    public Color getSecondaryColor(){
        return category.getSecondaryColor();
    }
    
    public void update (String n, String i, String s, String e, Category c,Date d){
        setName(n);
        setStartTime(s);
        setEndTime(e);
        setInfo(i);
        setCategory(c);
        setDate(d);
    }
}
