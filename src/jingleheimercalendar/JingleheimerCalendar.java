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
    public static final int MINIMUM_VIEW_HEIGHT = MINIMUM_HEIGHT - NavigationPanel.MINIMUM_HEIGHT - CategoryPanel.MINIMUM_HEIGHT;

    //Constant for time-delay of mTimer in miliseconds
    //Want 1 minute = 60 seconds * 1000 miliseconds/second = 60,000 ms
    private static final int DELAY_INTERVAL = 60000;

    //
    public static final String PATH_FONT_KALINGA = "/font/kalinga.ttf";
    public static final String PATH_FONT_NEXA = "/font/Nexa_Bold.ttf";

    // Class variables
    private Timer mTimer;
    private Calendar mCalendar;
    private NavigationPanel mNavigationPanel;
    private CategoryPanel mCategoryPanel;

    //Structure used for holding and accessing the different content views
    private static ViewPanel[] views;

    public static final String DAY_VIEW = "dayView";
    public static final String WEEK_VIEW = "dayView";
    public static final String MONTH_VIEW = "dayView";
    public static final String YEAR_VIEW = "dayView";

    //Constants relating to managing the current view
    public static final int NUM_VIEWS = 4;
    public static final int INDEX_DAY_VIEW = 0;
    public static final int INDEX_WEEK_VIEW = 1;
    public static final int INDEX_MONTH_VIEW = 2;
    public static final int INDEX_YEAR_VIEW = 3;
    public static int indexDisplayedview;

    private static ViewPanel viewPanel;

    private static Font customFont;
    SpringLayout springLayoutRoot, springLayeredPane;
    private static CardLayout cardViewLayout;

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
            UIManager.getLookAndFeelDefaults().put("defaultFont", new Font("Arial", Font.BOLD, 28));
            //System.out.println("Success - but it's actually not");
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

        Insets windowInsets = getInsets();
        Rectangle workableBounds = getScreenBounds(null);
        int prefHeight = PREFERRED_HEIGHT + windowInsets.top + windowInsets.bottom;
        int prefWidth = PREFERRED_WIDTH + windowInsets.left + windowInsets.right;

        int minHeight = MINIMUM_HEIGHT + windowInsets.top + windowInsets.bottom;
        int minWidth = MINIMUM_WIDTH + windowInsets.left + windowInsets.right;

        int initialHeight, initialWidth, viewWidth, viewHeight;
        boolean maxWidth = false;
        boolean maxHeight = false;
        if (workableBounds.width <= prefWidth) {
            maxWidth = true;
            viewWidth = MINIMUM_WIDTH;
            initialWidth = minWidth;
        } else {
            viewWidth = PREFERRED_WIDTH;
            initialWidth = prefWidth;
        }
        if (workableBounds.height <= prefHeight) {
            maxHeight = true;
            viewHeight = MINIMUM_HEIGHT;
            initialHeight = minHeight;
        } else {
            viewHeight = PREFERRED_HEIGHT - CategoryPanel.MINIMUM_HEIGHT - NavigationPanel.MINIMUM_HEIGHT;
            initialHeight = prefHeight;
        }


        this.setPreferredSize(new Dimension(initialWidth, initialHeight));
        pack();

        springLayoutRoot = new SpringLayout();
        getContentPane().setLayout(springLayoutRoot);

        //Set default location to center of default screen device
        //this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Jingleheimer-Schmidt Calendar");

        //Initialize Navigation and Category Panels
        mNavigationPanel = new NavigationPanel(viewWidth);
        mCategoryPanel = new CategoryPanel(viewWidth);

        //Initialize LayeredPane
        springLayeredPane = new SpringLayout();
        cardViewLayout = new CardLayout();
        viewPanel = new ViewPanel(cardViewLayout);
        //getContentPane().setLayout(springLayeredPane);
        viewPanel.setOpaque(false);

        //Initialize views array
        initializeViews(viewWidth, viewHeight);

        //Start with Day View as default
        displayView(INDEX_DAY_VIEW);

        //Set constraints for frame components
        initializeViewSprings();

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

    private void initializeViews(int viewWidth, int viewHeight) {
        //Initialize array of views
        views = new ViewPanel[NUM_VIEWS];
        for (int i = 0; i < NUM_VIEWS; i++) {
            switch (i) {
                case INDEX_DAY_VIEW:
                    views[i] = new DayView(viewWidth, viewHeight);
                    break;
                case INDEX_WEEK_VIEW:
                    views[i] = new WeekView(viewWidth, viewHeight, viewPanel);
                    break;
                case INDEX_MONTH_VIEW:
                    views[i] = new MonthView(viewWidth, viewHeight);
                    break;
                case INDEX_YEAR_VIEW:
                    views[i] = new YearView(viewWidth, viewHeight);
                    break;
            }
            viewPanel.add(views[i], views[i].getStringValue());
        }

    }

    public static void displayView(int viewIndex) {
        cardViewLayout.show(viewPanel,views[viewIndex].getStringValue());
        setIndexDisplayedview(viewIndex);
    }

    private void initializeViewSprings() {
        add(mNavigationPanel);
        springLayoutRoot.putConstraint(SpringLayout.NORTH, mNavigationPanel, 0, SpringLayout.NORTH, getContentPane());
        springLayoutRoot.putConstraint(SpringLayout.WEST, mNavigationPanel, 0, SpringLayout.WEST, getContentPane());
        springLayoutRoot.putConstraint(SpringLayout.EAST, mNavigationPanel, 0, SpringLayout.EAST, getContentPane());

        add(mCategoryPanel);
        springLayoutRoot.putConstraint(SpringLayout.SOUTH, mCategoryPanel, 0, SpringLayout.SOUTH, getContentPane());
        springLayoutRoot.putConstraint(SpringLayout.WEST, mCategoryPanel, 0, SpringLayout.WEST, getContentPane());
        springLayoutRoot.putConstraint(SpringLayout.EAST, mCategoryPanel, 0, SpringLayout.EAST, getContentPane());

        add(viewPanel);
        springLayoutRoot.putConstraint(SpringLayout.NORTH, viewPanel, 0, SpringLayout.SOUTH, mNavigationPanel);
        springLayoutRoot.putConstraint(SpringLayout.WEST, viewPanel, 0, SpringLayout.WEST, getContentPane());
        springLayoutRoot.putConstraint(SpringLayout.EAST, viewPanel, 0, SpringLayout.EAST, getContentPane());
        springLayoutRoot.putConstraint(SpringLayout.SOUTH, viewPanel, 0, SpringLayout.NORTH, mCategoryPanel);

        for (ViewPanel view : views) {
            springLayoutRoot.putConstraint(SpringLayout.NORTH, view, 0, SpringLayout.SOUTH, mNavigationPanel);
            springLayoutRoot.putConstraint(SpringLayout.WEST, view, 0, SpringLayout.WEST, getContentPane());
            springLayoutRoot.putConstraint(SpringLayout.EAST, view, 0, SpringLayout.EAST, getContentPane());
            springLayoutRoot.putConstraint(SpringLayout.SOUTH, view, 0, SpringLayout.NORTH, mCategoryPanel);
        }
    }

    private static void setIndexDisplayedview(int index) {
       indexDisplayedview = index;
    }
}
