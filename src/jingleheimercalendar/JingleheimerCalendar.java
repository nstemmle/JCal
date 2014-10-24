/*
Mockup layout for Calendar application
If you hate this we don't have to use any of it - just trying to get the ball rolling
*/

package jingleheimercalendar;

import java.awt.*;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;

public class JingleheimerCalendar extends JFrame {
    //Constants for preferred/target screen width and height
    public static final int PREFERRED_WIDTH = 1280;
    public static final int PREFERRED_HEIGHT = 720;

    //Constants for preventing user resizing screen too small
    // these will have to adjusted/tested
    public static final int MINIMUM_WIDTH = 800;
    public static final int MINIMUM_HEIGHT = 450;

    //These might need to be accessed from other methods so moving them outside constructor scope
    //Can re-insert into constructor scope if they don't need to be accessed again
    private Rectangle workableBounds;
    private boolean maxWidth = false;
    private boolean maxHeight = false;

    // Class variables
    private NavigationPanel mNavigationPanel;
    private CategoryPanel mCategoryPanel;
    private PlaceholderView mPlaceHolderView;
    private JLabel topLabel, bottomLabel, viewLabel;
    private Font sans, customFont;

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
        //@nstemmle
        //Get optimal initial bounds of window while accomadating available screen real estate where
        // initialWidth preferred is 1280 px (target)
        // initialHeight preferred is 720 px (target)
        // Feel free to adjust these parameters
        // This will currently set the initial size of the window to be the full workable screen real estate
        // e.g. If screen is 1280x720 it will be set to 1280x~680 or so
        // this means the application is essentially full screen without actually being maximized

        //Taken from the following stackoverflow
        //http://stackoverflow.com/questions/1936566/how-do-you-get-the-screen-width-in-java/8101318#8101318

        this.setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
        this.setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));

        workableBounds = getScreenBounds(null);
        int initialHeight, initialWidth;
        if (workableBounds.width <= PREFERRED_WIDTH) {
            maxWidth = true;
            initialWidth = workableBounds.width;
        } else
            initialWidth = PREFERRED_WIDTH;
        if (workableBounds.height <= PREFERRED_HEIGHT) {
            maxHeight = true;
            initialHeight = workableBounds.height;
        } else
            initialHeight = PREFERRED_HEIGHT;

        this.setSize(initialWidth, initialHeight);
        //Output for testing
        //System.out.println("workable bounds x: " + workableBounds.width + "\nworkable bounds y: " + workableBounds.height);
        //System.out.println("init width: " + initialWidth + "\ninit height: " + initialHeight);

        //Set default location to center of default screen device
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Jingleheimer Dingleheimer");

        mNavigationPanel = new NavigationPanel();
        mNavigationPanel.setMinimumSize(new Dimension(JingleheimerCalendar.MINIMUM_WIDTH, NavigationPanel.MINIMUM_HEIGHT));
        mCategoryPanel = new CategoryPanel();
        mCategoryPanel.setMinimumSize(new Dimension(JingleheimerCalendar.MINIMUM_WIDTH, CategoryPanel.MINIMUM_HEIGHT));
        mPlaceHolderView = new PlaceholderView();

        mPlaceHolderView.setLayout(new GridBagLayout());
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
        
        mNavigationPanel.add(topLabel);
        mCategoryPanel.add(bottomLabel);
        mPlaceHolderView.add(viewLabel);

        add(mNavigationPanel);
        add(mCategoryPanel);
        add(mPlaceHolderView);


        pack();
        this.setVisible(true);
        this.validate();
        //If the application window size is set to the maximum workable bounds, then maximize the application
        //Take from the following stackoverflow
        //http://stackoverflow.com/questions/479523/java-swing-maximize-window
        if (maxWidth && maxHeight) {
            this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        }

        //Debugging/testing output
        /*
        System.out.println("JFrame width: " + getWidth() + "\nJFrame height: " + getHeight());
        System.out.println("Cat Panel x: " + mCategoryPanel.getX() + "\nCat Panel width: " + mCategoryPanel.getWidth());
        System.out.println("Cat Panel y: " + mCategoryPanel.getY() + "\nCat Panel height: " + mCategoryPanel.getHeight());
        System.out.println("Nav Panel x: " + mNavigationPanel.getX() + "\nNav Panel width: " + mNavigationPanel.getWidth());
        System.out.println("Nav Panel y: " + mNavigationPanel.getY() + "\nNav Panel height: " + mNavigationPanel.getHeight());
        System.out.println("\n\n***Initial Settings\n\n");
        */
    }
    
    private Font loadFont() throws FontFormatException, IOException  {
        URL fontUrl = getClass().getResource("/font/Nexa_Bold.ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
        font = font.deriveFont(Font.PLAIN,60);
        return font;
    }

    //This allows for dynamic resizing of components in window
    @Override
    public void validate() {
        super.validate();
        if (mCategoryPanel != null) {
            mCategoryPanel.setSize(getContentPane().getWidth(), CategoryPanel.MINIMUM_HEIGHT);
            mCategoryPanel.setLocation(0, getContentPane().getHeight() - CategoryPanel.MINIMUM_HEIGHT);
            //Debugging/testing output
            //System.out.println("Cat Panel x: " + mCategoryPanel.getX() + "\nCat Panel width: " + mCategoryPanel.getWidth());
            //System.out.println("Cat Panel y: " + mCategoryPanel.getY() + "\nCat Panel height: " + mCategoryPanel.getHeight());
        }

        if (mNavigationPanel != null) {
            mNavigationPanel.setSize(getContentPane().getWidth(), NavigationPanel.MINIMUM_HEIGHT);
            //Debugging/testing output
            //System.out.println("Nav Panel x: " + mNavigationPanel.getX() + "\nNav Panel width: " + mNavigationPanel.getWidth());
            //System.out.println("Nav Panel y: " + mNavigationPanel.getY() + "\nNav Panel height: " + mNavigationPanel.getHeight());
        }
        if (mPlaceHolderView != null) {
            mPlaceHolderView.setSize(getContentPane().getWidth(), getContentPane().getHeight() - NavigationPanel.MINIMUM_HEIGHT - CategoryPanel.MINIMUM_HEIGHT);
            mPlaceHolderView.setLocation(0, NavigationPanel.MINIMUM_HEIGHT);
        }
    }

    class PlaceholderView extends JPanel {
        public PlaceholderView() {
            this.setBackground(Color.WHITE);
            this.setPreferredSize(new Dimension(1280, 650));
            this.setMinimumSize(new Dimension(JingleheimerCalendar.MINIMUM_WIDTH, JingleheimerCalendar.MINIMUM_HEIGHT - NavigationPanel.MINIMUM_HEIGHT - CategoryPanel.MINIMUM_HEIGHT));
        }
    }

    public static Rectangle getScreenBounds(Window currentWindow) {
        Rectangle screenBounds;

        //Get the operating system insets (dock, taskbar, etc.)
        Insets screenInsets = getScreenInsets(currentWindow);

        //If our window has been instantiated
        if (currentWindow != null) {
            screenBounds = currentWindow.getGraphicsConfiguration().getBounds();
        } else {
            screenBounds = GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration()
                    .getBounds();
        }

        //Take into account operating system insets (dock, taskbar, etc.) in usable bounds
        screenBounds.x += screenInsets.left;
        screenBounds.y += screenInsets.top;
        screenBounds.width -= screenInsets.right;
        screenBounds.height -= screenInsets.bottom;
        return screenBounds;
    }

    public static Insets getScreenInsets(Window currentWindow) {
        Insets screenInsets;

        if (currentWindow != null) {
            screenInsets = currentWindow.getToolkit().getScreenInsets(currentWindow.getGraphicsConfiguration());
        } else { //Pull system defaults
            screenInsets = Toolkit.getDefaultToolkit()
                    .getScreenInsets(GraphicsEnvironment
                            .getLocalGraphicsEnvironment()
                            .getDefaultScreenDevice()
                            .getDefaultConfiguration());
        }
        return screenInsets;
    }
}
