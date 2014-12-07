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
public class Task{
    private String name;
    private String info;
    private int priority;
    private Category category;
    private String complete;
    private Boolean isComplete = false;
    
    public Task(String n, String i, int p, Category c,String d){
        name = n;
        info = i;
        priority = p;
        category = c;
        complete = d;
    }
    
    public int getPriority(){
        return priority;
    }
    
    public void setPriority(int p){
         priority = p;
    }
    
    public void setIsComplete(boolean c){
         isComplete = c;
    }
    
    public boolean isComplete(){
         return isComplete;
    }
    
    public String getComplete(){
        return complete;
    }
    
    public void setComplete(String s){
         complete = s;
    }
    
    public String getPriorityAsString(){
        if( priority == 1){
            return "!";
        }else if (priority == 2){
            return "! !";
        }else if (priority == 3){
            return "! ! !";
        }
        return "";
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String s){
        name = s;
    }    
    public String getInfo(){
        return info;
    }
    
    public void setInfo(String s){
        info = s;
    } 
    
    public Color getCategoryColor(){
        return category.getCategoryColor();
    }
    
    
    public Category getCategory(){
       return category;
    }
    
    public void setCategory(Category s){
       category = s ;
    }
    
    public Color getSecondaryColor(){
        return category.getSecondaryColor();
    }
    
    public void update (String n, String i, int p, Category c, String com){
        setName(n);
        setPriority(p);
        setInfo(i);
        setComplete(com);
        setCategory(c);
    }
}
