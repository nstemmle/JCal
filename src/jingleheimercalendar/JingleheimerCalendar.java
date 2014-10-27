/*
Mockup layout for Calendar application
If you hate this we don't have to use any of it - just trying to get the ball rolling
*/

package jingleheimercalendar;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;

public class JingleheimerCalendar extends JFrame {
    //Constants for preferred/target screen width and height
    public static final int PREFERRED_WIDTH = 1280;
    public static final int PREFERRED_HEIGHT = 720;

    //Constants for preventing user resizing screen too small
    // these will have to adjusted/tested
    public static final int MINIMUM_WIDTH = 800;
    public static final int MINIMUM_HEIGHT = 450;

    //Constant for time-delay of mTimer in miliseconds
    //Want 1 minute = 60 seconds * 1000 miliseconds/second = 60,000 ms
    private static final int DELAY_INTERVAL = 60000;

    // Class variables
    private Timer mTimer;
    private Calendar mCalendar;
    private NavigationPanel mNavigationPanel;
    private CategoryPanel mCategoryPanel;
    private MonthPanel monthPanel;
    private JLayeredPane layeredPane;
    private JLabel topLabel, bottomLabel;
    private static Font customFont;
    private Font sans;
    SpringLayout springLayout, springLayeredPane;

    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext","true");
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private static void createAndShowGUI() {
        try {
            UIManager.getLookAndFeelDefaults().put("defaultFont", new Font("Tahoma", Font.BOLD, 28));
            System.out.println("Success - but it's actually not");
        } catch (Exception e) {
            e.printStackTrace();
        }
        new JingleheimerCalendar();
        // Forgive my terrible names
            //@nstemmle - Renamed and forgiven
    }

    public JingleheimerCalendar() {
        //@nstemmle
        //Get optimal initial bounds of window while accommodating available screen real estate where
        // initialWidth preferred is 1280 px (target)
        // initialHeight preferred is 720 px (target)
        // Feel free to adjust these parameters
        // This will currently set the initial size of the window to be the full workable screen real estate
        // e.g. If screen is 1280x720 it will be set to 1280x~680 or so
        // this means the application is essentially full screen without actually being maximized

        //Taken from the following stackoverflow
        //http://stackoverflow.com/questions/1936566/how-do-you-get-the-screen-width-in-java/8101318#8101318

        this.setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
        pack();

        Insets windowInsets = getScreenInsets(null);
        Rectangle workableBounds = getScreenBounds(null);
        int prefHeight = PREFERRED_HEIGHT + windowInsets.top + windowInsets.bottom;
        int prefWidth = PREFERRED_WIDTH + windowInsets.left + windowInsets.right;

        int minHeight = MINIMUM_HEIGHT + windowInsets.top + windowInsets.bottom;
        int minWidth = MINIMUM_WIDTH + windowInsets.left + windowInsets.right;

        int initialHeight, initialWidth, panelWidth, panelHeight;
        boolean maxWidth = false;
        if (workableBounds.width <= prefWidth) {
            maxWidth = true;
            initialWidth = minWidth;
            panelWidth = MINIMUM_WIDTH;
        } else
            initialWidth = prefWidth;
            panelWidth = PREFERRED_WIDTH;
        boolean maxHeight = false;
        if (workableBounds.height <= prefHeight) {
            maxHeight = true;
            panelHeight = MINIMUM_HEIGHT;
            initialHeight = minHeight;
        } else
            panelHeight = PREFERRED_HEIGHT;
            initialHeight = prefHeight;

        this.setPreferredSize(new Dimension(initialWidth, initialHeight));
        pack();
        springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);

        //Set default location to center of default screen device
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Jingleheimer Dingleheimer");

        mNavigationPanel = new NavigationPanel(panelWidth);
        mCategoryPanel = new CategoryPanel(panelWidth);

        //Pass the size of the monthPanel to be constructed to its constructor
        monthPanel = new MonthPanel(panelWidth, panelHeight - CategoryPanel.MINIMUM_HEIGHT - NavigationPanel.MINIMUM_HEIGHT,0);
        topLabel    = new JLabel("Top navigation area (custom font)");
        bottomLabel = new JLabel("Bottom navigation area (Arial Bold 20pt)");

        springLayeredPane = new SpringLayout();
        layeredPane = new JLayeredPane();
        layeredPane.setLayout(springLayeredPane);
        layeredPane.add(monthPanel);
        layeredPane.setOpaque(false);
        layeredPane.moveToFront(monthPanel);



        
        sans = new Font("Arial", Font.BOLD,20);

        topLabel.setFont(customFont);
        bottomLabel.setFont(sans);

        mNavigationPanel.add(topLabel);
        mCategoryPanel.add(bottomLabel);


        add(mNavigationPanel);
        springLayout.putConstraint(SpringLayout.NORTH, mNavigationPanel, 0, SpringLayout.NORTH, getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, mNavigationPanel, 0, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, mNavigationPanel, 0, SpringLayout.EAST, getContentPane());

        add(mCategoryPanel);
        springLayout.putConstraint(SpringLayout.SOUTH, mCategoryPanel, 0, SpringLayout.SOUTH, getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, mCategoryPanel, 0, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, mCategoryPanel, 0, SpringLayout.EAST, getContentPane());

        add(layeredPane);
        springLayout.putConstraint(SpringLayout.NORTH, layeredPane, 0, SpringLayout.SOUTH, mNavigationPanel);
        springLayout.putConstraint(SpringLayout.WEST, layeredPane, 0, SpringLayout.WEST, getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, layeredPane, 0, SpringLayout.EAST, getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, layeredPane, 0, SpringLayout.NORTH, mCategoryPanel);

        springLayeredPane.putConstraint(SpringLayout.NORTH, monthPanel, 0, SpringLayout.NORTH, layeredPane);
        springLayeredPane.putConstraint(SpringLayout.WEST, monthPanel, 0, SpringLayout.WEST, layeredPane);
        springLayeredPane.putConstraint(SpringLayout.EAST, monthPanel, 0, SpringLayout.EAST, layeredPane);
        springLayeredPane.putConstraint(SpringLayout.SOUTH, monthPanel, 0, SpringLayout.SOUTH, layeredPane);

        /*ActionListener mTimerListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };
        mTimer = new Timer(DELAY_INTERVAL,mTimerListener);*/

        pack();
        this.setVisible(true);
        this.validate();
        //If the application window size is set to the maximum workable bounds, then maximize the application
        //Take from the following stackoverflow
        //http://stackoverflow.com/questions/479523/java-swing-maximize-window
        if (maxWidth && maxHeight) {
            this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        }
        this.setLocationRelativeTo(null);
    }
    
    private Font loadFont() throws FontFormatException, IOException  {
        URL fontUrl = getClass().getResource("/font/Nexa_Bold.ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
        font = font.deriveFont(Font.PLAIN,60);
        return font;
    }

    static Rectangle getScreenBounds(Window currentWindow) {
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

    static Insets getScreenInsets(Window currentWindow) {
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
