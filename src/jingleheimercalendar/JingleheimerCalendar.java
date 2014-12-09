package jingleheimercalendar;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JingleheimerCalendar extends JFrame {
    //Constants for preferred/target screen width and height
    public static final int PREFERRED_WIDTH = 1280;
    public static final int PREFERRED_HEIGHT = 720;

    //Constants for preventing user resizing screen too small
    // these will have to adjusted/tested
    public static final int MINIMUM_WIDTH = 800;
    public static final int MINIMUM_HEIGHT = 450;
    public static final int MINIMUM_VIEW_HEIGHT = MINIMUM_HEIGHT - NavigationPanel.MINIMUM_HEIGHT - CategoryBar.MINIMUM_HEIGHT;

    public static final String PATH_FONT_HELVETICA = "/font/HelveticaNeue.ttf";

    // Class variables
    private Timer mTimer;
    private Calendar mCalendar;
    protected static NavigationPanel mNavigationPanel;
    private static CategoryBar mCategoryPanel;

    //Structure used for holding and accessing the different content views
    private static ViewPanel[] views;

    public static final String VIEW_TODAY = "TodayView";
    public static final String VIEW_DAY = "DayView";
    public static final String VIEW_WEEK = "WeekView";
    public static final String VIEW_MONTH = "MonthView";
    public static final String VIEW_YEAR = "YearView";

    //Constants relating to managing the current view
    public static final int NUM_VIEWS = 4;
    public static final int INDEX_TODAY_VIEW = 4;
    public static final int INDEX_DAY_VIEW = 0;
    public static final int INDEX_WEEK_VIEW = 1;
    public static final int INDEX_MONTH_VIEW = 2;
    public static final int INDEX_YEAR_VIEW = 3;
    public static int indexDisplayedview;

    private static ViewPanel viewPanel;

    public static Font defaultFont;
    SpringLayout springLayoutRoot;
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
        new JingleheimerCalendar();
        // Forgive my terrible names
        //@nstemmle - Renamed and forgiven
    }

    public JingleheimerCalendar() {
        demo();
        this.getContentPane().setMinimumSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        this.getContentPane().setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        pack();

        Insets windowInsets = getInsets();
        int prefHeight = PREFERRED_HEIGHT + windowInsets.top + windowInsets.bottom;
        int prefWidth = PREFERRED_WIDTH + windowInsets.left + windowInsets.right;

        this.setSize(prefWidth, prefHeight);
        this.setPreferredSize(new Dimension(prefWidth, prefHeight));
        //System.out.println(this.getBounds());
        //System.out.println(getContentPane().getBounds());
        this.setResizable(false);

        int viewWidth = PREFERRED_WIDTH;
        int viewHeight = PREFERRED_HEIGHT - CategoryBar.MINIMUM_HEIGHT - NavigationPanel.MINIMUM_HEIGHT;
        //System.out.println("viewWidth: " + viewWidth + ", viewHeight: " + viewHeight);

        springLayoutRoot = new SpringLayout();
        getContentPane().setLayout(springLayoutRoot);

        //Set default location to center of default screen device
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Jingleheimer-Schmidt Calendar");

        defaultFont = null;
        try {
            defaultFont = loadFont(PATH_FONT_HELVETICA);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        //if (defaultFont == null)
           // System.out.println("FUUUUUUUU!!!!");

        //Initialize Navigation and Category Panels
        Insets nullInsets = new Insets(0,0,0,0);
        mNavigationPanel = new NavigationPanel(viewWidth);
        mNavigationPanel.setBorder(null);
        mNavigationPanel.getInsets(nullInsets);

        mCategoryPanel = new CategoryBar();
        mCategoryPanel.setBorder(null);
        mCategoryPanel.getInsets(null);

        cardViewLayout = new CardLayout();
        viewPanel = new ViewPanel(cardViewLayout);
        //viewPanel.setOpaque(false);
        viewPanel.setOpaque(true); ///bcaruso
        viewPanel.setBorder(null);
        viewPanel.getInsets(nullInsets);

        //Initialize views array
        initializeViews(viewWidth, viewHeight);

        //Start with Day View as default
        displayView(INDEX_DAY_VIEW);

        //Set constraints for frame components
        initializeViewSprings();

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        mNavigationPanel.setBackground(Color.BLUE);

        mCategoryPanel.setBackground(Color.WHITE);
        //mCategoryPanel.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.PINK));
        getContentPane().setBackground(Color.WHITE);
        viewPanel.setBackground(Color.WHITE);
        this.setBackground(Color.WHITE);

        pack();
        this.setVisible(true);
        this.validate();

        /*System.out.println("NavigationPanel.bounds: " + mNavigationPanel.getBounds().toString());
        System.out.println("NavigationPanel.x: " + mNavigationPanel.getX());
        System.out.println("NavigationPanel.y: " + mNavigationPanel.getY());

        System.out.println("viewPanel.bounds: " + viewPanel.getBounds().toString());
        System.out.println("viewPanel.width: " + viewPanel.getWidth());
        System.out.println("viewPanel.height: " + viewPanel.getHeight());
        System.out.println("viewPanel.insets: " + viewPanel.getInsets());
        System.out.println("viewPanel.border: " + viewPanel.getBorder());
        System.out.println("viewPanel.x: " + viewPanel.getX());
        System.out.println("viewPanel.y: " + viewPanel.getY());

        System.out.println("CategoryPanel.bounds: " + mCategoryPanel.getBounds().toString());
        System.out.println("CategoryPanel.x: " + mCategoryPanel.getX());
        System.out.println("CategoryPanel.y: " + mCategoryPanel.getY());*/

        //if (maxWidth && maxHeight)
        //    this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
    }

    private Font loadFont(String path) throws FontFormatException, IOException  {
        URL fontUrl = getClass().getResource(path);
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
        font = font.deriveFont(Font.PLAIN, 24f);
        return font;
        //return Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream()).deriveFont(Font.PLAIN, 24f);
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
        views[INDEX_DAY_VIEW] = new DayView(viewWidth, viewHeight);
        views[INDEX_WEEK_VIEW] = new WeekView(viewWidth, viewHeight, viewPanel);
        views[INDEX_MONTH_VIEW] = new MonthView(viewWidth, viewHeight);
        views[INDEX_YEAR_VIEW] = new YearView(viewWidth, viewHeight);

        for (int i = 0; i < NUM_VIEWS; i++) {
            viewPanel.add(views[i], views[i].getStringValue());
        }
    }

    private void initializeViewSprings() {
        add(mNavigationPanel);
        /*SpringLayout.Constraints nav = springLayoutRoot.getConstraints(mNavigationPanel);
        nav.setX(Spring.constant(0));
        nav.setY(Spring.constant(0));
        nav.setWidth(Spring.constant(PREFERRED_WIDTH));
        nav.setHeight(Spring.constant(NavigationPanel.MINIMUM_HEIGHT));
        //nav.getConstraint(SpringLayout.WEST).setValue();*/

        springLayoutRoot.putConstraint(SpringLayout.NORTH, mNavigationPanel, 0, SpringLayout.NORTH, getContentPane());
        springLayoutRoot.putConstraint(SpringLayout.WEST, mNavigationPanel, 0, SpringLayout.WEST, getContentPane());
        springLayoutRoot.putConstraint(SpringLayout.EAST, mNavigationPanel, 0, SpringLayout.EAST, getContentPane());

        add(mCategoryPanel);
        /*SpringLayout.Constraints cat = springLayoutRoot.getConstraints(mCategoryPanel);
        cat.setX(Spring.constant(0));
        cat.setY(Spring.constant(PREFERRED_HEIGHT - NavigationPanel.MINIMUM_HEIGHT));
        cat.setWidth(Spring.constant(PREFERRED_WIDTH));
        cat.setHeight(Spring.constant(CategoryBar.MINIMUM_HEIGHT));*/
        springLayoutRoot.putConstraint(SpringLayout.SOUTH, mCategoryPanel, 0, SpringLayout.SOUTH, getContentPane());
        springLayoutRoot.putConstraint(SpringLayout.WEST, mCategoryPanel, 0, SpringLayout.WEST, getContentPane());
        springLayoutRoot.putConstraint(SpringLayout.EAST, mCategoryPanel, 0, SpringLayout.EAST, getContentPane());

        add(viewPanel);
        /*SpringLayout.Constraints view = springLayoutRoot.getConstraints(viewPanel);
        view.setX(Spring.constant(0));
        view.setY(Spring.constant(NavigationPanel.MINIMUM_HEIGHT));
        view.setWidth(Spring.constant(PREFERRED_WIDTH));
        view.setHeight(Spring.constant(PREFERRED_HEIGHT - NavigationPanel.MINIMUM_HEIGHT - CategoryBar.MINIMUM_HEIGHT));*/
        springLayoutRoot.putConstraint(SpringLayout.NORTH, viewPanel, 0, SpringLayout.SOUTH, mNavigationPanel);
        springLayoutRoot.putConstraint(SpringLayout.WEST, viewPanel, 0, SpringLayout.WEST, getContentPane());
        springLayoutRoot.putConstraint(SpringLayout.EAST, viewPanel, 0, SpringLayout.EAST, getContentPane());
        springLayoutRoot.putConstraint(SpringLayout.SOUTH, viewPanel, 0, SpringLayout.NORTH, mCategoryPanel);

        for (ViewPanel v : views) {
            springLayoutRoot.putConstraint(SpringLayout.NORTH, v, 0, SpringLayout.SOUTH, mNavigationPanel);
            springLayoutRoot.putConstraint(SpringLayout.WEST, v, 0, SpringLayout.WEST, getContentPane());
            springLayoutRoot.putConstraint(SpringLayout.EAST, v, 0, SpringLayout.EAST, getContentPane());
            springLayoutRoot.putConstraint(SpringLayout.SOUTH, v, 0, SpringLayout.NORTH, mCategoryPanel);
        }
    }

    public static void displayView(int viewIndex) {
        if (viewIndex == INDEX_TODAY_VIEW)  {
            cardViewLayout.show(viewPanel,views[INDEX_DAY_VIEW].getStringValue());
            DayView.goToCurrentDay();
            views[INDEX_DAY_VIEW].refresh();
            setIndexDisplayedview(INDEX_DAY_VIEW);
        } else {
            cardViewLayout.show(viewPanel,views[viewIndex].getStringValue());
            views[viewIndex].refresh();
            setIndexDisplayedview(viewIndex);
        }
        mNavigationPanel.viewChanged(getViewString(viewIndex));
    }

    private static void setIndexDisplayedview(int index) {
        indexDisplayedview = index;
    }

    private static ViewPanel getDisplayedView() {
        return views[indexDisplayedview];
    }

    public static void refreshCurrentView() {
        getDisplayedView().refresh();
    }

    public static String getViewString(int index) {
        if (index == INDEX_TODAY_VIEW)
            return VIEW_TODAY;
        return views[index].getStringValue();
    }

    public static int getViewIndex(String view) {
        switch (view) {
            case VIEW_TODAY:
                return INDEX_TODAY_VIEW;
            case VIEW_DAY:
                return INDEX_DAY_VIEW;
            case VIEW_WEEK:
                return INDEX_WEEK_VIEW;
            case VIEW_MONTH:
                return INDEX_MONTH_VIEW;
            case VIEW_YEAR:
                return INDEX_YEAR_VIEW;
            default: //Should throw an error
                return -1;
        }
    }
    private void demo(){
        try {
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
            UserCalendar uc = UserCalendar.getInstance();
            ArrayList<Category> categories = uc.getCategories();

            Category none     = categories.get(0);
            Category work     = categories.get(1);
            Category school   = categories.get(2);
            Category family   = categories.get(3);
            Category wellness = categories.get(4);

            Date d;
            Event e;

            // Events
            d = df.parse("12/10/2014");
            e = new Event("Dad's Birthday", "56 Years Old!", "All Day", "",family,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/10/2014");
            e = new Event("ELA State Test", "Don't let Timmy go to school without breakfast", "10:45 am", "1:30 pm",school,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/13/2014");
            e = new Event("Yoga", "Don't forget a mat this week!", "8:00 am", "9:30 am",wellness,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/11/2014");
            e = new Event("HR Audit", "The auditor will be here!", "9:00 am", "11:00 am",work,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/09/2014");
            e = new Event("Andy's practice", "My turn to pick the boys up", "3:00 pm", "4:00 pm",school,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/10/2014");
            e = new Event("Andy's soccer game", "Home game", "6:00 pm", "7:30 pm",school,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/11/2014");
            e = new Event("Jason's practice", "My turn to pick the boys up", "3:00 pm", "4:00 pm",family,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/12/2014");
            e = new Event("Movie night", "With Jack, bring the kids?", "7:00 pm", "10:00 pm",family,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/13/2014");
            e = new Event("Shopping!", "Pick up Justine and Kate", "All Day", "",none,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/24/2014");
            e = new Event("Christmas Eve Party", "At Justine's, bring chicken wing dip.", "7:00 pm", "10:00 pm",none,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/25/2014");
            e = new Event("Christmas Dinner", "Mom and Dad's", "5:00 pm", "7:00 pm",family,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/31/2014");
            e = new Event("New Years' Party", "Our place", "9:00 pm", "2:00 am",family,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/16/2014");
            e = new Event("Hanukkah", "begins at sundown", "All Day", "",none,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/24/2014");
            e = new Event("Christmas Eve", "", "All Day", "",none,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/25/2014");
            e = new Event("Christmas", "", "All Day", "",none,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/26/2014");
            e = new Event("Boxing Day", "Canada", "All Day", "",none,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/31/2014");
            e = new Event("New Year's Eve", "", "All Day", "",none,d);
            uc.addEventsByDate(e,d);

            d = df.parse("1/1/2015");
            e = new Event("New Year's Day", "", "All Day", "",none,d);
            uc.addEventsByDate(e,d);

            //TODO: remove
            d = df.parse("01/01/2014");
            e = new Event("New Year's Day", "", "All Day", "", none, d);
            uc.addEventsByDate(e,d);

            // Tasks
            uc.addTasks(new Task("Cake and Ice Cream","Order from Yummy Cakes",3,school,"3:00 pm"));
            uc.addTasks(new Task("Finish quilt","Justine's new baby!",2,none,""));
            uc.addTasks(new Task("Call the caterer","New Years' Party",1,none,""));
        } catch (ParseException ex) {
            Logger.getLogger(JingleheimerCalendar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void changeDayViewDay(int date, int month, int year) {
        ((DayView)views[INDEX_DAY_VIEW]).changeDay(date, month, year);
    }

    public static void changeMonthViewMonth(int month, int year) {
        ((MonthView)views[INDEX_MONTH_VIEW]).setMonth(month, year);
    }

    public static void repaintDisplayedCategoryWindow() {
        mCategoryPanel.repaintCategoryBar();
    }

    public static void refreshCategoryBar() {
        mCategoryPanel.refresh();
    }
}