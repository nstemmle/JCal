/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jingleheimercalendar;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brandon
 */
public class UserCalendar {
    private ArrayList<Category> categories = new ArrayList<Category>();
    private HashMap<Date,ArrayList<Event>> events = new HashMap<Date, ArrayList<Event>>();
    private ArrayList<Task> tasks = new ArrayList<Task>();
    private String name = "UserCal";
    
    private UserCalendar() {
        categories.add(new Category("No Category", new Color(69,69,69)));
        categories.add(new Category("Work", new Color(122,202,68)));
        categories.add(new Category("School", new Color(63,170,246)));
        categories.add(new Category("Family", new Color(245,147,48)));
        categories.add(new Category("Wellness", new Color(103,48,146)));
    }
    
    
    public ArrayList<Category> getCategories(){
        return categories;
    }
    
    public void addNewCategory(Category c){
       categories.add(c);
    }
    
    public ArrayList<Event> getEventsByDate(Date c){
       if(events.containsKey(c)){
            return events.get(c);
        }else{
            ArrayList<Event> ev = new ArrayList<Event>();
            events.put(c, ev);
            return events.get(c);
        }
    }
    
    public void addEventsByDate(Event e, Date c){
        if(events.containsKey(c)){
            events.get(c).add(e);
        }else{
            ArrayList<Event> ev = new ArrayList<Event>();
            ev.add(e);
            events.put(c, ev);
        }
       sortEvents(c);
    }
    
     public void moveEvent(Event e, Date d1, Date d2){
        Event temp = e;
        removeEvent(temp);
        addEventsByDate(temp,d2);
    }
     
    public void removeCategory(Category c){
        changeCategory(getEventsByCategory(c),categories.get(0));
        categories.remove(c);
        JingleheimerCalendar.refreshCurrentView();
    }
    
    public ArrayList<Event> getAllDayEventsByDate( Date d){
        ArrayList<Event> event = new ArrayList<Event>();
        Iterator it = events.entrySet().iterator();
        while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry)it.next();
        ArrayList<Event> e = (ArrayList<Event>)pairs.getValue();
                for (int i = 0; i <  e.size(); i++) {
                if(e.get(i).getStartTime().equals("All Day") && e.get(i).getDate().equals(d))
                     event.add(e.get(i));
        }
    
     } 
       
        return event;
    }
    
    public ArrayList<Event> getEventsByCategory( Category c){
        System.out.println("here");
        ArrayList<Event> event = new ArrayList<Event>();
        Iterator it = events.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry)it.next();
        ArrayList<Event> e = (ArrayList<Event>)pairs.getValue();
                for (int i = 0; i <  e.size(); i++) {
                if(e.get(i).getCategory().getName().equals(c.getName()))
                     event.add(e.get(i));
            }
    
    }     
        return sortEventsByDate(event);
    }
    
    public ArrayList<Event> getEventsByCategoryForInfo( Category c, Date d){
        System.out.println("here");
        ArrayList<Event> event = new ArrayList<Event>();
        Iterator it = events.entrySet().iterator();
    while (it.hasNext()) {
        Map.Entry pairs = (Map.Entry)it.next();
        ArrayList<Event> e = (ArrayList<Event>)pairs.getValue();
                for (int i = 0; i <  e.size(); i++) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(d);
                    cal.add(Calendar.DATE, 7);
                if(e.get(i).getCategory().getName().equals(c.getName()) 
                        && e.get(i).getDate().compareTo(d)>=0 
                        && e.get(i).getDate().compareTo(cal.getTime())<=0 )
                     event.add(e.get(i));
            }
    
    }     
        return sortEventsByDate(event);
    }
    
    public void changeCategory(ArrayList<Event> e, Category c){
        for( int i = 0 ; i<e.size(); i++ )
            e.get(i).setCategory(c);
    }
    
    public ArrayList<Task> getTasks(){
       return tasks;
    }
    
    public void addTasks(Task t){
        tasks.add(t);
        sortTasks();
    }
    
    public void sortTasks(){
        tasks.sort(new Comparator<Task>() {
            @Override
            public int compare(Task  t1, Task  t2){
                if(t1.getPriority()>t2.getPriority()){
                    return -1;
                }else if(t1.getPriority()<t2.getPriority()){
                    return 1;
                }else{
                    return 0;
                }
            }
            
        });
    }
    
    public void sortEvents(Date d){
        events.get(d).sort(new Comparator<Event>() {
            @Override
            public int compare(Event  e1, Event  e2){
                
                return compareByTime(e1.getStartTime(),e2.getStartTime());
                
                
            }
            
        });
    }
    
     public ArrayList<Event> sortEventsByDate(ArrayList<Event> e){
        e.sort(new Comparator<Event>() {
            @Override
            public int compare(Event  e1, Event  e2){
               return e1.getDate().compareTo(e2.getDate());
            }
            
        });
        return e;
    }
    
    private int compareByTime(String s1, String s2){
        if(s1.equals("All Day")){
               return -1;
        }
        if(s2.equals("All Day")){
            return 1;
        }
        try {
            
            SimpleDateFormat convert = new SimpleDateFormat("HH:mm");
            SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
            Date d1 = convert.parse(convert.format(df.parse(s1)));
            Date d2 = convert.parse(convert.format(df.parse(s2)));
            
            return d1.compareTo(d2);
        } catch (ParseException ex) {
            Logger.getLogger(UserCalendar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public void removeTasks(Task t){
        tasks.remove(t);
    }
    public void removeEvent(Event e){
        events.get(e.getDate()).remove(e);
    }
    
    
    public static UserCalendar getInstance() {
        return UserCalendarHolder.INSTANCE;
    }
    
    private static class UserCalendarHolder {

        private static final UserCalendar INSTANCE = new UserCalendar();
    }
}