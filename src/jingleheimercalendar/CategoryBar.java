/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jingleheimercalendar;


import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Font;

/**
 *
 * @author Brandon
 */
public class CategoryBar extends javax.swing.JPanel {
    UserCalendar cal ;
    Category cat1;
    Category cat2;
    Category cat3;
    Category cat4;
    
    
    /**
     * Creates new form NewJPanel
     */
    public CategoryBar() {       
        cat1 = UserCalendar.getInstance().getCategories().get(1);
        cat2 = UserCalendar.getInstance().getCategories().get(2);
        cat3 = UserCalendar.getInstance().getCategories().get(3);
        cat4 = UserCalendar.getInstance().getCategories().get(4);
        initComponents();
    }

    public void refresh(){
        cat1 = UserCalendar.getInstance().getCategories().get(1);
        cat2 = UserCalendar.getInstance().getCategories().get(2);
        cat3 = UserCalendar.getInstance().getCategories().get(3);
        cat4 = UserCalendar.getInstance().getCategories().get(4);
        
        category1.setBackground(cat1.getSecondaryColor());
        topBar1.setBackground(cat1.getCategoryColor());
        catName1.setText(cat1.getName());

        category2.setBackground(cat2.getSecondaryColor());
        topBar2.setBackground(cat2.getCategoryColor());
        catName2.setText(cat2.getName());

        category3.setBackground(cat3.getSecondaryColor());
        topBar3.setBackground(cat3.getCategoryColor());
        catName3.setText(cat3.getName());

        category4.setBackground(cat4.getSecondaryColor());
        topBar4.setBackground(cat4.getCategoryColor());
        catName4.setText(cat4.getName());
        validate();
    }
    
   /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        
        jPanel1 = new javax.swing.JPanel();
        left = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        right = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        addNew = new javax.swing.JLabel();
        topBar = new javax.swing.JPanel();
        category1 = new javax.swing.JPanel();
        catName1 = new javax.swing.JLabel();
        topBar1 = new javax.swing.JPanel();
        category2 = new javax.swing.JPanel();
        catName2 = new javax.swing.JLabel();
        topBar2 = new javax.swing.JPanel();
        category3 = new javax.swing.JPanel();
        catName3 = new javax.swing.JLabel();
        topBar3 = new javax.swing.JPanel();
        category4 = new javax.swing.JPanel();
        catName4 = new javax.swing.JLabel();
        topBar4 = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(1280, 50));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(50, 50));

        
        left.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        left.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        left.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/leftArrow32.png"))); // NOI18N
        left.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                goLeft(evt);
            }
        });
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(left, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(left, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(50, 50));
        jPanel2.setSize(new java.awt.Dimension(50, 50));

        right.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        right.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rightArrow32.png"))); // NOI18N
        right.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                goRight(evt);
            }
        });
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(right, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(right, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        jPanel3.setPreferredSize(new java.awt.Dimension(80, 50));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showAddNew(evt);
            }
        });
        addNew.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addIcon15x15.png"))); // NOI18N
        
        topBar.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout topBarLayout = new javax.swing.GroupLayout(topBar);
        topBar.setLayout(topBarLayout);
        topBarLayout.setHorizontalGroup(
            topBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        topBarLayout.setVerticalGroup(
            topBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(addNew, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(topBar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(topBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(addNew, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        category1.setPreferredSize(new java.awt.Dimension(275, 50));
        category1.setBackground(cat1.getSecondaryColor());
        category1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showCategory1Info(evt);
            }
        });

        catName1.setFont(JingleheimerCalendar.defaultFont.deriveFont(Font.BOLD, 24f)); // NOI18N
        catName1.setForeground(new java.awt.Color(69, 69, 69));
        catName1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        catName1.setText(cat1.getName());
        
        topBar1.setPreferredSize(new java.awt.Dimension(100, 10));
        topBar1.setBackground(cat1.getCategoryColor());
        
        javax.swing.GroupLayout topBar1Layout = new javax.swing.GroupLayout(topBar1);
        topBar1.setLayout(topBar1Layout);
        topBar1Layout.setHorizontalGroup(
            topBar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        topBar1Layout.setVerticalGroup(
            topBar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout category1Layout = new javax.swing.GroupLayout(category1);
        category1.setLayout(category1Layout);
        category1Layout.setHorizontalGroup(
            category1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(catName1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
            .addComponent(topBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
        );
        category1Layout.setVerticalGroup(
            category1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, category1Layout.createSequentialGroup()
                .addComponent(topBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(catName1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        category2.setPreferredSize(new java.awt.Dimension(275, 50));
        category2.setBackground(cat2.getSecondaryColor());
        category2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showCategory2Info(evt);
            }
        });
        catName2.setFont(JingleheimerCalendar.defaultFont.deriveFont(Font.BOLD, 24f));
        catName2.setForeground(new java.awt.Color(69,69,69));
        catName2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        catName2.setText(cat2.getName());
        
        topBar2.setPreferredSize(new java.awt.Dimension(100, 10));
        topBar2.setBackground(cat2.getCategoryColor());

        javax.swing.GroupLayout topBar2Layout = new javax.swing.GroupLayout(topBar2);
        topBar2.setLayout(topBar2Layout);
        topBar2Layout.setHorizontalGroup(
            topBar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        topBar2Layout.setVerticalGroup(
            topBar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout category2Layout = new javax.swing.GroupLayout(category2);
        category2.setLayout(category2Layout);
        category2Layout.setHorizontalGroup(
            category2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(catName2, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
            .addComponent(topBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
        );
        category2Layout.setVerticalGroup(
            category2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, category2Layout.createSequentialGroup()
                .addComponent(topBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(catName2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        category3.setPreferredSize(new java.awt.Dimension(275, 50));
        category3.setBackground(cat3.getSecondaryColor());
        category3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showCategory3Info(evt);
            }
        });
        catName3.setFont(JingleheimerCalendar.defaultFont.deriveFont(Font.BOLD, 24f));
        catName3.setForeground(new java.awt.Color(69, 69, 69));
        catName3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        catName3.setText(cat3.getName());
        
        topBar3.setPreferredSize(new java.awt.Dimension(100, 10));
        topBar3.setBackground(cat3.getCategoryColor());

        javax.swing.GroupLayout topBar3Layout = new javax.swing.GroupLayout(topBar3);
        topBar3.setLayout(topBar3Layout);
        topBar3Layout.setHorizontalGroup(
            topBar3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        topBar3Layout.setVerticalGroup(
            topBar3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout category3Layout = new javax.swing.GroupLayout(category3);
        category3.setLayout(category3Layout);
        category3Layout.setHorizontalGroup(
            category3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(catName3, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
            .addComponent(topBar3, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
        );
        category3Layout.setVerticalGroup(
            category3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, category3Layout.createSequentialGroup()
                .addComponent(topBar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(catName3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        category4.setPreferredSize(new java.awt.Dimension(275, 50));
        category4.setBackground(cat4.getSecondaryColor());
        category4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showCategory4Info(evt);
            }
        });
        catName4.setFont(JingleheimerCalendar.defaultFont.deriveFont(Font.BOLD, 24f));
        catName4.setForeground(new java.awt.Color(69, 69, 69));
        catName4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        catName4.setText(cat4.getName());
       
        topBar4.setPreferredSize(new java.awt.Dimension(100, 10));
        topBar4.setBackground(cat4.getCategoryColor());

        javax.swing.GroupLayout topBar4Layout = new javax.swing.GroupLayout(topBar4);
        topBar4.setLayout(topBar4Layout);
        topBar4Layout.setHorizontalGroup(
            topBar4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        topBar4Layout.setVerticalGroup(
            topBar4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout category4Layout = new javax.swing.GroupLayout(category4);
        category4.setLayout(category4Layout);
        category4Layout.setHorizontalGroup(
            category4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(catName4, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
            .addComponent(topBar4, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
        );
        category4Layout.setVerticalGroup(
            category4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, category4Layout.createSequentialGroup()
                .addComponent(topBar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(catName4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(category1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(category2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(category3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(category4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(category1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(category2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(category3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(category4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>                        

    private void showCategory1Info(java.awt.event.MouseEvent evt){
        JingleheimerCalendar f = (JingleheimerCalendar) SwingUtilities.getWindowAncestor(this);
        CustomInfoCategoryPanel c = new CustomInfoCategoryPanel(category1.getX(),cat1);
        JPanel glass = new JPanel();
        glass.setLayout(null); 
        glass.setOpaque(false); 
        glass.add(c);
        f.setGlassPane(glass); 
        glass.setVisible(true);
        glass.revalidate();
    }
    
    private void showCategory2Info(java.awt.event.MouseEvent evt){
        JingleheimerCalendar f = (JingleheimerCalendar) SwingUtilities.getWindowAncestor(this);
        CustomInfoCategoryPanel c = new CustomInfoCategoryPanel(category2.getX(),cat2);
        JPanel glass = new JPanel();
        glass.setLayout(null); 
        glass.setOpaque(false); 
        glass.add(c);
        f.setGlassPane(glass); 
        glass.setVisible(true);
        glass.revalidate();
    }
    
    private void showCategory3Info(java.awt.event.MouseEvent evt){
        
        JingleheimerCalendar f = (JingleheimerCalendar) SwingUtilities.getWindowAncestor(this);
        CustomInfoCategoryPanel c = new CustomInfoCategoryPanel(category3.getX(),cat3);
        JPanel glass = new JPanel();
        glass.setLayout(null); 
        glass.setOpaque(false); 
        glass.add(c);
        f.setGlassPane(glass); 
        glass.setVisible(true);
        glass.revalidate();
    }
    
    private void showCategory4Info(java.awt.event.MouseEvent evt){
        JingleheimerCalendar f = (JingleheimerCalendar) SwingUtilities.getWindowAncestor(this);
        CustomInfoCategoryPanel c = new CustomInfoCategoryPanel(category4.getX(),cat4);
        JPanel glass = new JPanel();
        glass.setLayout(null); 
        glass.setOpaque(false); 
        glass.add(c);
        f.setGlassPane(glass); 
        glass.setVisible(true);
        glass.revalidate();
        
    }
    
    private void showAddNew(java.awt.event.MouseEvent evt){
       JingleheimerCalendar f = (JingleheimerCalendar) SwingUtilities.getWindowAncestor(this);
        CustomAddNewCategoryPanel c = new CustomAddNewCategoryPanel(jPanel3.getX());
        JPanel glass = new JPanel();
        glass.setLayout(null); 
        glass.setOpaque(false); 
        glass.add(c);
        f.setGlassPane(glass); 
        glass.setVisible(true);
        glass.revalidate();
    }
    
    private void goRight(java.awt.event.MouseEvent evt){
       
        JingleheimerCalendar f = (JingleheimerCalendar) SwingUtilities.getWindowAncestor(this);
        f.getGlassPane().setVisible(false);
      
        
       int next;
       
        if(UserCalendar.getInstance().getCategories().indexOf(cat4) != UserCalendar.getInstance().getCategories().size() - 1){
            next = UserCalendar.getInstance().getCategories().indexOf(cat4) + 1;
        }else{
            next = 1;
        }
            cat1 = cat2;
            cat2 = cat3;
            cat3 = cat4;
            cat4 = UserCalendar.getInstance().getCategories().get(next);
            
            category1.setBackground(cat1.getSecondaryColor());
            topBar1.setBackground(cat1.getCategoryColor());
            catName1.setText(cat1.getName());
            
            category2.setBackground(cat2.getSecondaryColor());
            topBar2.setBackground(cat2.getCategoryColor());
            catName2.setText(cat2.getName());
            
            category3.setBackground(cat3.getSecondaryColor());
            topBar3.setBackground(cat3.getCategoryColor());
            catName3.setText(cat3.getName());
            
            category4.setBackground(cat4.getSecondaryColor());
            topBar4.setBackground(cat4.getCategoryColor());
            catName4.setText(cat4.getName());
        
       
       
    }
    private void goLeft(java.awt.event.MouseEvent evt){
       JingleheimerCalendar f = (JingleheimerCalendar) SwingUtilities.getWindowAncestor(this);
        f.getGlassPane().setVisible(false);
        
       int next;
       
        if(UserCalendar.getInstance().getCategories().indexOf(cat1) != 1){
            next = UserCalendar.getInstance().getCategories().indexOf(cat1) - 1;
        }else{
            next = UserCalendar.getInstance().getCategories().size() - 1 ;
        }
            cat4 = cat3;
            cat3 = cat2;
            cat2 = cat1;
            cat1 = UserCalendar.getInstance().getCategories().get(next);
            
            category1.setBackground(cat1.getSecondaryColor());
            topBar1.setBackground(cat1.getCategoryColor());
            catName1.setText(cat1.getName());
            
            category2.setBackground(cat2.getSecondaryColor());
            topBar2.setBackground(cat2.getCategoryColor());
            catName2.setText(cat2.getName());
            
            category3.setBackground(cat3.getSecondaryColor());
            topBar3.setBackground(cat3.getCategoryColor());
            catName3.setText(cat3.getName());
            
            category4.setBackground(cat4.getSecondaryColor());
            topBar4.setBackground(cat4.getCategoryColor());
            catName4.setText(cat4.getName());
        
       
       
    }
    
    // Variables declaration - do not modify                     
    private javax.swing.JLabel addNew;
    private javax.swing.JLabel catName1;
    private javax.swing.JLabel catName2;
    private javax.swing.JLabel catName3;
    private javax.swing.JLabel catName4;
    private javax.swing.JPanel category1;
    private javax.swing.JPanel category2;
    private javax.swing.JPanel category3;
    private javax.swing.JPanel category4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel left;
    private javax.swing.JLabel right;
    private javax.swing.JPanel topBar;
    private javax.swing.JPanel topBar1;
    private javax.swing.JPanel topBar2;
    private javax.swing.JPanel topBar3;
    private javax.swing.JPanel topBar4;
    // End of variables declaration                   
}