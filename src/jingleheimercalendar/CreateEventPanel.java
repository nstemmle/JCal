/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jingleheimercalendar;

import javax.swing.SwingUtilities;
import java.awt.Color;
import java.util.Calendar;

/**
 *
 * @author Brandon
 */
public class CreateEventPanel extends javax.swing.JPanel{
       javax.swing.JPanel parentContainer;
       javax.swing.JLabel allDayEventText;
       DayPanel dayPanelRef;
       Calendar cal;
    /**
     * Creates new form NewEventPanel
     */
    public CreateEventPanel(javax.swing.JPanel addHere, javax.swing.JLabel allDay,DayPanel day, Calendar c) {
        dayPanelRef=day;
        parentContainer = addHere;
        cal = c;
        allDayEventText = allDay;
        initComponents();
    }

     /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        eventName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        eventNote = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        scheduleEvent = new javax.swing.JButton();
        allDayEvent = new javax.swing.JCheckBox();
        categoryBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        startTimeLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        sTimeHour = new javax.swing.JComboBox();
        sTimeMin = new javax.swing.JComboBox();
        sTimeAP = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        eTimeAP = new javax.swing.JComboBox();
        eTimeMin = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        eTimeHour = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jLabel6.setText("jLabel6");

        jLabel7.setText("jLabel7");

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(600, 225));

        eventName.setFont(JingleheimerCalendar.defaultFont.deriveFont(13f));
        eventName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventNameActionPerformed(evt);
            }
        });

        eventNote.setColumns(20);
        eventNote.setFont(JingleheimerCalendar.defaultFont.deriveFont(13f));
        eventNote.setRows(5);
        eventNote.setText("(Optional)");
        jScrollPane1.setViewportView(eventNote);

        jLabel1.setFont(JingleheimerCalendar.defaultFont.deriveFont(13f));
        jLabel1.setText("Event Name:");

        jLabel2.setFont(JingleheimerCalendar.defaultFont.deriveFont(13f));
        jLabel2.setText("Note:");

        scheduleEvent.setFont(JingleheimerCalendar.defaultFont.deriveFont(13f));
        scheduleEvent.setText("Schedule My Event");
        scheduleEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scheduleEventActionPerformed(evt);
            }
        });

        allDayEvent.setFont(JingleheimerCalendar.defaultFont.deriveFont(13f));
        allDayEvent.setText("All Day Event");
        allDayEvent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allDayEventActionPerformed(evt);
            }
        });

        categoryBox.setFont(JingleheimerCalendar.defaultFont.deriveFont(13f));
        JingleheimerCalendar f = (JingleheimerCalendar) SwingUtilities.getWindowAncestor(parentContainer);
        String[] categoriesArray = new String[UserCalendar.getInstance().getCategories().size()];
        for(int i = 0;i< UserCalendar.getInstance().getCategories().size();i++){
           categoriesArray[i] = UserCalendar.getInstance().getCategories().get(i).getName();
        }
        categoryBox.setModel(new javax.swing.DefaultComboBoxModel(categoriesArray));
        categoryBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryBoxActionPerformed(evt);
            }
        });

        jLabel3.setFont(JingleheimerCalendar.defaultFont.deriveFont(13f));
        jLabel3.setText("Category:");

        startTimeLabel.setBackground(new java.awt.Color(204, 204, 204));
        startTimeLabel.setFont(JingleheimerCalendar.defaultFont.deriveFont(13f));
        startTimeLabel.setText("Start Time:");

        jLabel5.setBackground(new java.awt.Color(204, 204, 204));
        jLabel5.setFont(JingleheimerCalendar.defaultFont.deriveFont(13f));
        jLabel5.setText("End Time:");

        sTimeHour.setFont(JingleheimerCalendar.defaultFont.deriveFont(13f));
        sTimeHour.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        sTimeMin.setFont(JingleheimerCalendar.defaultFont.deriveFont(13f));
        sTimeMin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));
        sTimeMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sTimeMinActionPerformed(evt);
            }
        });

        sTimeAP.setFont(JingleheimerCalendar.defaultFont.deriveFont(13f));
        sTimeAP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "am", "pm"}));

        jLabel8.setText(":");

        eTimeAP.setFont(JingleheimerCalendar.defaultFont.deriveFont(13f));
        eTimeAP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "am", "pm" }));

        eTimeMin.setFont(JingleheimerCalendar.defaultFont.deriveFont(13f));
        eTimeMin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));

        jLabel10.setText(":");

        eTimeHour.setFont(JingleheimerCalendar.defaultFont.deriveFont(13f));
        eTimeHour.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 4, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(69, 69, 69));

        jLabel4.setFont(JingleheimerCalendar.defaultFont.deriveFont(13f));
        jLabel4.setText("Create A New Event ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jButton1.setText("Cancel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(44, 44, 44)
                                                .addComponent(jLabel2)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane1)
                                        .addComponent(eventName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(sTimeHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, 0)
                                                                .addComponent(jLabel8)
                                                                .addGap(0, 0, 0)
                                                                .addComponent(sTimeMin, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(sTimeAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(startTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(allDayEvent)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel3)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(categoryBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(scheduleEvent)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(eTimeHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, 0)
                                                                .addComponent(jLabel10)
                                                                .addGap(0, 0, 0)
                                                                .addComponent(eTimeMin, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(eTimeAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(0, 202, Short.MAX_VALUE))
                                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(startTimeLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(sTimeHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(sTimeMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(sTimeAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel8))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel5)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(eTimeHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(eTimeMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(eTimeAP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel10))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(allDayEvent)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(categoryBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(eventName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel1))
                                                .addGap(10, 10, 10)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel2))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGap(20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(scheduleEvent)
                                        .addComponent(jButton1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>                        
                 

    private void scheduleEventActionPerformed(java.awt.event.ActionEvent evt) {                                              
        
        String startTime = "";
        String endTime="";
        String name="";
        String note="";
        Category category;
        Color categoryColor = new Color(153,153,153);
        category = UserCalendar.getInstance().getCategories().get(categoryBox.getSelectedIndex());
        
        categoryColor = category.getCategoryColor();
           
            name = eventName.getText();
            note = eventNote.getText();
        
        if(allDayEvent.isSelected()){
            startTime = "All Day";
            endTime = ""; 
        }else{
            startTime = sTimeHour.getSelectedItem().toString()+":"+sTimeMin.getSelectedItem().toString()+" "+sTimeAP.getSelectedItem().toString();
            endTime = eTimeHour.getSelectedItem().toString()+":"+eTimeMin.getSelectedItem().toString()+" "+eTimeAP.getSelectedItem().toString();
        }
        if(note.equals("(Optional)")){
           note = "No Note Available For This Event";
        }
        cal.set(Calendar.HOUR_OF_DAY, 0);
        Event newEvent = new Event(name,note,startTime,endTime,category,cal.getTime());
        UserCalendar.getInstance().addEventsByDate(newEvent, cal.getTime());
        if(allDayEvent.isSelected()){
            DayPanel.updateAllDayText(cal.getTime());
        }
        dayPanelRef.refreshEventPanel();
        parentContainer.remove(this);
        parentContainer.validate();
    }   
    
    

    private void allDayEventActionPerformed(java.awt.event.ActionEvent evt) {                                            
        eTimeHour.setEnabled(! eTimeHour.isEnabled());
        eTimeMin.setEnabled(! eTimeMin.isEnabled());
        eTimeAP.setEnabled(! eTimeAP.isEnabled());
        sTimeHour.setEnabled(! sTimeHour.isEnabled());
        sTimeMin.setEnabled(! sTimeMin.isEnabled());
        sTimeAP.setEnabled(! sTimeAP.isEnabled());
    }                                           
   
    private void eventNameActionPerformed(java.awt.event.ActionEvent evt) {                                          
        
    }                                         

    private void categoryBoxActionPerformed(java.awt.event.ActionEvent evt) {   
        jPanel2.setBackground(UserCalendar.getInstance().getCategories().get(categoryBox.getSelectedIndex()).getCategoryColor());
    }                                           

    private void sTimeMinActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }  
    
    private void  jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        //AKA CANCEL
        /*if(events.isEmpty()){
            JPanel centerPanel = new JPanel(); 
            centerPanel.setLayout(new BorderLayout());
            centerPanel.setBackground(Color.WHITE);
            javax.swing.JLabel noEventMessage = new javax.swing.JLabel("No Scheduled Events.");
            noEventMessage.setFont(new java.awt.Font("Helvetica Neue", 0, 12));
            noEventMessage.setHorizontalAlignment(SwingConstants.CENTER);
            centerPanel.add(noEventMessage, BorderLayout.CENTER); 

            parentContainer.add(centerPanel);
        }*/
        dayPanelRef.refreshEventPanel();
        parentContainer.remove(this);
        parentContainer.validate(); 
    }

    

    // Variables declaration - do not modify                     
    private javax.swing.JCheckBox allDayEvent;
    private javax.swing.JComboBox categoryBox;
    private javax.swing.JComboBox eTimeAP;
    private javax.swing.JComboBox eTimeHour;
    private javax.swing.JComboBox eTimeMin;
    private javax.swing.JTextField eventName;
    private javax.swing.JTextArea eventNote;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox sTimeAP;
    private javax.swing.JComboBox sTimeHour;
    private javax.swing.JComboBox sTimeMin;
    private javax.swing.JButton scheduleEvent;
    private javax.swing.JLabel startTimeLabel;
    // End of variables declaration          
}
