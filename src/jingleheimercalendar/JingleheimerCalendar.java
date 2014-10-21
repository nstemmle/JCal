/*
Mockup layout for Calendar application
If you hate this we don't have to use any of it - just trying to get the ball rolling
*/

package jingleheimercalendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;

public class JingleheimerCalendar extends JFrame {
    
    // Class variables
    JLabel topLabel, bottomLabel, viewLabel;
    Font sans, customFont;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private static void createAndShowGUI() {
        new JingleheimerCalendar();
        // Forgive my terrible names
            //@nstemmle - Renamed and forgiven
    }
    
    public JingleheimerCalendar() {
        
        this.setSize(1280,720);
        // JFrame dimensions include the operating system's borders.
        // The actual size of the JFrame on windows is 1296x758, but this is left out for now because
        // I'm sure it's different on mac and the frame stretches to fit the components with pack() anyway
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Awesome Calendar");
        
        TopNavigation top = new TopNavigation();
        PlaceholderView view = new PlaceholderView();
        BottomNavigation bottom = new BottomNavigation();
        // Separate Jpanel classes for top and bottom because they have different borders.
        
        view.setLayout(new GridBagLayout());
        // default GridBag just for centering the label.
        
        topLabel    = new JLabel("Top navigation area (Swing default font)");
        viewLabel   = new JLabel("Placeholder view area (Nexa Bold 60pt)");
        bottomLabel = new JLabel("Bottom navigation area (Arial Bold 20pt)");
        
        try {
            customFont = loadFont();
            // I'm not sure the following two lines are completely necessary.
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (FontFormatException|IOException e) {
            // If you see times at all, something is wrong.
            System.err.println("Error loading custom font. Using Times.");
            customFont = new Font("Times New Roman", Font.BOLD,60);
        }
        
        sans = new Font("Arial", Font.BOLD,20);
        // test reference font for the bottom panel
        
        //topLabel is Swing default just for reference, so I don't change it
        viewLabel.setFont(customFont);
        bottomLabel.setFont(sans);
        
        top.add(topLabel);
        view.add(viewLabel);
        bottom.add(bottomLabel);
        
        BasePanel base = new BasePanel();
        base.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
                
        base.add(top);
        base.add(view);
        base.add(bottom);
        
        this.add(base);
        pack();
        this.setVisible(true);
    }
    
    private Font loadFont() throws FontFormatException, IOException  {
        URL fontUrl = getClass().getResource("/font/Nexa_Bold.ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
        font = font.deriveFont(Font.PLAIN,60);
        return font;
    }
    
    class TopNavigation extends JPanel {
        public TopNavigation() {
            setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.BLACK));
            // MatteBorder takes arguments for the width of each border's side.
            this.setBackground(Color.WHITE);
        }
        public Dimension getPreferredSize() {
            return new Dimension(1280,35);
        }
    }
    class BottomNavigation extends JPanel {
        public BottomNavigation() {
            setBorder(BorderFactory.createMatteBorder(2,0,0,0,Color.BLACK));
            // MatteBorder takes arguments for the width of each border's side.
            this.setBackground(Color.WHITE);
        }
        public Dimension getPreferredSize() {
            return new Dimension(1280,35);
        }
    }
    class PlaceholderView extends JPanel {
        public PlaceholderView() {
            this.setBackground(Color.WHITE);
        }
        public Dimension getPreferredSize() {
            return new Dimension(1280,650);
        }
    }
    class BasePanel extends JPanel {
        public BasePanel(){
            this.setBackground(Color.WHITE);
        }
        public Dimension getPreferredSize() {
            return new Dimension(1280,720);
        }
    }
}
