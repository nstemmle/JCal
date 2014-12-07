/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jingleheimercalendar;

/**
 *
 * @author Brandon
 */

import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brandon
 */
public class MoreEventInfoPanel extends javax.swing.JPanel {
    private final Event event;
    /**
     * Creates new form moreEventPanel
     */
    public MoreEventInfoPanel(Event e) {
        event = e;
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

        jPanel5 = new javax.swing.JPanel();
        name = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        date = new javax.swing.JTextField();
        startTime = new javax.swing.JTextField();
        endTime = new javax.swing.JTextField();
        category = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        info = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setBackground(new Color(0,0,0,0));
        setPreferredSize(new java.awt.Dimension(250, 350));
        
        Color textColor = new Color(69,69,69);
        
        if(event.getStartTime().equals("All Day") && event.getCategory().getName().equals("No Category")){
           textColor = Color.WHITE;
        }
        jPanel5.setBackground(new Color(0,0,0,0));

        name.setFont(JingleheimerCalendar.defaultFont.deriveFont(Font.BOLD, 16f));
        name.setForeground(new java.awt.Color(255, 255, 255));
        name.setBackground(event.getCategoryColor());
        name.setBorder(null);
        name.setEnabled(false);
        name.setDisabledTextColor(Color.white);
        name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        name.setText(event.getName());

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close15.png"))); // NOI18N
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        
        
        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(name, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new Color(0,0,0,0));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new Color(0,0,0,0));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.setBackground(event.getSecondaryColor());

        jLabel2.setFont(JingleheimerCalendar.defaultFont.deriveFont(Font.BOLD, 13f));
        jLabel2.setForeground(textColor);
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Date");

        jLabel3.setFont(JingleheimerCalendar.defaultFont.deriveFont(Font.BOLD, 13f));
        jLabel3.setForeground(textColor);
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Start Time");

        jLabel4.setFont(JingleheimerCalendar.defaultFont.deriveFont(Font.BOLD, 13f));
        jLabel4.setForeground(textColor);
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("End Time");

        jLabel5.setFont(JingleheimerCalendar.defaultFont.deriveFont(Font.BOLD, 13f));
        jLabel5.setForeground(textColor);
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Category");

        date.setFont(JingleheimerCalendar.defaultFont.deriveFont(Font.PLAIN, 13f));
        date.setEnabled(false);
        date.setBackground(event.getSecondaryColor());
        date.setBorder(null);
        date.setForeground(textColor);
        date.setDisabledTextColor(textColor);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
        date.setText(sdf.format(event.getDate()));

        startTime.setFont(JingleheimerCalendar.defaultFont.deriveFont(Font.PLAIN, 13f));
        startTime.setText(event.getStartTime());
        startTime.setEnabled(false);
        startTime.setBackground(event.getSecondaryColor());
        startTime.setBorder(null);
        startTime.setForeground(textColor);
        startTime.setDisabledTextColor(textColor);
        
        endTime.setFont(JingleheimerCalendar.defaultFont.deriveFont(Font.PLAIN, 13f));
        endTime.setText(event.getEndTime());
        endTime.setEnabled(false);
        endTime.setBackground(event.getSecondaryColor());
        endTime.setBorder(null);
        endTime.setForeground(textColor);
        endTime.setDisabledTextColor(textColor);
        
        category.setFont(JingleheimerCalendar.defaultFont.deriveFont(Font.PLAIN, 13f));
        String[] categoriesArray = new String[UserCalendar.getInstance().getCategories().size()];
        for(int i = 0;i< UserCalendar.getInstance().getCategories().size();i++){
           categoriesArray[i] = UserCalendar.getInstance().getCategories().get(i).getName();
        }
        category.setModel(new javax.swing.DefaultComboBoxModel(categoriesArray));
        category.setEnabled(false);
        category.setSelectedIndex(UserCalendar.getInstance().getCategories().indexOf(event.getCategory()));
        category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeColor(evt);
            }
        });
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(startTime, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(endTime, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(startTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(endTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        jPanel2.setBackground(new Color(0,0,0,0));

        info.setColumns(20);
        info.setFont(JingleheimerCalendar.defaultFont.deriveFont(Font.PLAIN, 13f));
        info.setLineWrap(true);
        info.setRows(5);
        info.setEnabled(false);
        info.setBackground(event.getSecondaryColor());
        info.setBorder(null);
        info.setForeground(textColor);
        info.setDisabledTextColor(textColor);
        info.setText(event.getInfo());
        
        jScrollPane1.setViewportView(info);
        jScrollPane1.setBorder(null);
        
        jLabel6.setFont(JingleheimerCalendar.defaultFont.deriveFont(Font.BOLD, 13f));
        jLabel6.setForeground(textColor);
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Additional Information");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel6)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deleteButton20.png"))); // NOI18N
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteMouseClicked(evt);
            }
        });
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editButton20.png"))); // NOI18N
        jLabel7.setBackground(event.getSecondaryColor());
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editMouseClicked(evt);
            }
        });
        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setBackground(event.getSecondaryColor());
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                .addComponent(jLabel8))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>                       

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {                                     
        getParent().setVisible(false);
    } 
    
    private void editMouseClicked(java.awt.event.MouseEvent evt) {                                     
        if(!startTime.isEnabled()){
            date.setEnabled(true);
            startTime.setEnabled(true);
            endTime.setEnabled(true);
            date.setEnabled(true);
            category.setEnabled(true);
            name.setEnabled(true);
            info.setEnabled(true);
            jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/saveButton20.png"))); // SAVE
            jLabel7.setBackground(event.getSecondaryColor());
        }else{
            try {
                date.setEnabled(false);
                startTime.setEnabled(false);
                endTime.setEnabled(false);
                category.setEnabled(false);
                name.setEnabled(false);
                info.setEnabled(false);
                
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
                Date possibleNewDate;
                possibleNewDate = df.parse(date.getText());
                if(possibleNewDate.equals(event.getDate())){
                    event.update(name.getText(), info.getText(), startTime.getText(), endTime.getText(), UserCalendar.getInstance().getCategories().get(category.getSelectedIndex()),possibleNewDate);
                    UserCalendar.getInstance().sortEvents(event.getDate());
                    JingleheimerCalendar.refreshCurrentView();
                }else{
                    UserCalendar.getInstance().removeEvent(event);
                    Event newE = new Event(name.getText(), info.getText(), startTime.getText(), endTime.getText(), UserCalendar.getInstance().getCategories().get(category.getSelectedIndex()),possibleNewDate);
                    UserCalendar.getInstance().addEventsByDate(newE, possibleNewDate);
                    UserCalendar.getInstance().sortEvents(possibleNewDate);
                    JingleheimerCalendar.refreshCurrentView();
                }
                
                jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editButton20.png")));
            } catch (ParseException ex) {
                Logger.getLogger(MoreEventInfoPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
    
    private void deleteMouseClicked(java.awt.event.MouseEvent evt) { 
        UserCalendar.getInstance().removeEvent(event);
        JingleheimerCalendar.refreshCurrentView();
        this.getParent().setVisible(false);
    } 
    
    private void changeColor( java.awt.event.ActionEvent evt ){
        CustomInfoEventWindow w = (CustomInfoEventWindow) SwingUtilities.getWindowAncestor(this);
        w.updateCategoryColor(UserCalendar.getInstance().getCategories().get(category.getSelectedIndex()).getCategoryColor());
        w.updateSecondaryCategoryColor(UserCalendar.getInstance().getCategories().get(category.getSelectedIndex()).getSecondaryColor());
        date.setBackground(UserCalendar.getInstance().getCategories().get(category.getSelectedIndex()).getSecondaryColor());
        endTime.setBackground(UserCalendar.getInstance().getCategories().get(category.getSelectedIndex()).getSecondaryColor());
        startTime.setBackground(UserCalendar.getInstance().getCategories().get(category.getSelectedIndex()).getSecondaryColor());
        info.setBackground(UserCalendar.getInstance().getCategories().get(category.getSelectedIndex()).getSecondaryColor());
        name.setBackground(UserCalendar.getInstance().getCategories().get(category.getSelectedIndex()).getCategoryColor());
        jLabel7.setBackground(UserCalendar.getInstance().getCategories().get(category.getSelectedIndex()).getSecondaryColor());
        jPanel3.setBackground(UserCalendar.getInstance().getCategories().get(category.getSelectedIndex()).getSecondaryColor());
        jPanel1.setBackground(UserCalendar.getInstance().getCategories().get(category.getSelectedIndex()).getSecondaryColor());
        w.repaint();
    }

    // Variables declaration - do not modify                     
    private javax.swing.JComboBox category;
    private javax.swing.JTextField date;
    private javax.swing.JTextField endTime;
    private javax.swing.JTextField name;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea info;
    private javax.swing.JTextField startTime;
    // End of variables declaration                   
}