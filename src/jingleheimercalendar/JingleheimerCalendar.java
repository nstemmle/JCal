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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Nathan
 */
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
        this.setResizable(false);

        int viewWidth = PREFERRED_WIDTH;
        int viewHeight = PREFERRED_HEIGHT - CategoryBar.MINIMUM_HEIGHT - NavigationPanel.MINIMUM_HEIGHT;

        springLayoutRoot = new SpringLayout();
        getContentPane().setLayout(springLayoutRoot);

        //Set default location to center of default screen device
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("JCal");

        defaultFont = null;
        try {
            defaultFont = loadFont(PATH_FONT_HELVETICA);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

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
            e.printStackTrace();
        }

        mNavigationPanel.setBackground(Color.BLUE);

        mCategoryPanel.setBackground(Color.WHITE);
        getContentPane().setBackground(Color.WHITE);
        viewPanel.setBackground(Color.WHITE);
        this.setBackground(Color.WHITE);

        pack();
        this.setVisible(true);
        this.validate();

        this.setLocationRelativeTo(null);
    }

    private Font loadFont(String path) throws FontFormatException, IOException  {
        URL fontUrl = getClass().getResource(path);
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
        font = font.deriveFont(Font.PLAIN, 24f);
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

        for (ViewPanel v : views) {
            springLayoutRoot.putConstraint(SpringLayout.NORTH, v, 0, SpringLayout.SOUTH, mNavigationPanel);
            springLayoutRoot.putConstraint(SpringLayout.WEST, v, 0, SpringLayout.WEST, getContentPane());
            springLayoutRoot.putConstraint(SpringLayout.EAST, v, 0, SpringLayout.EAST, getContentPane());
            springLayoutRoot.putConstraint(SpringLayout.SOUTH, v, 0, SpringLayout.NORTH, mCategoryPanel);
        }
    }

    public static void displayView(int viewIndex) {
        if (viewIndex == INDEX_TODAY_VIEW) {
            cardViewLayout.show(viewPanel, views[INDEX_DAY_VIEW].getStringValue());
            DayView.goToCurrentDay();
            views[INDEX_DAY_VIEW].refresh();
            setIndexDisplayedview(INDEX_DAY_VIEW);
        } else if (viewIndex == INDEX_YEAR_VIEW) {
            cardViewLayout.show(viewPanel, views[viewIndex].getStringValue());
            views[viewIndex].refresh();
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

    //Brandon from John's demo base
    private void demo(){
        try {
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
            UserCalendar uc = UserCalendar.getInstance();
            ArrayList<Category> categories = uc.getCategories();

            categories.add(new Category("US Holidays", new Color(196, 2, 51)));

            Category none     = categories.get(0);
            Category work     = categories.get(1);
            Category school   = categories.get(2);
            Category family   = categories.get(3);
            Category wellness = categories.get(4);
            Category holiday = categories.get(5);


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
            e = new Event("Hanukkah", "begins at sundown", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/16/2015");
            e = new Event("Hanukkah", "begins at sundown", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/24/2014");
            e = new Event("Christmas Eve", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/24/2015");
            e = new Event("Christmas Eve", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/25/2014");
            e = new Event("Christmas", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/25/2015");
            e = new Event("Christmas", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/26/2014");
            e = new Event("Boxing Day", "Canada", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/26/2015");
            e = new Event("Boxing Day", "Canada", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/31/2014");
            e = new Event("New Year's Eve", "", "All Day", "", holiday, d);
            uc.addEventsByDate(e,d);

            d = df.parse("12/31/2015");
            e = new Event("New Year's Eve", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("1/1/2014");
            e = new Event("New Year's Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("1/1/2015");
            e = new Event("New Year's Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("1/20/2015");
            e = new Event("Martin Luther King Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("1/20/2014");
            e = new Event("Martin Luther King Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("2/14/2015");
            e = new Event("Valentine's Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("2/14/2014");
            e = new Event("Valentine's Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("2/17/2015");
            e = new Event("Presidents' Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("2/17/2014");
            e = new Event("Presidents' Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("4/5/2015");
            e = new Event("Easter", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("4/20/2014");
            e = new Event("Easter", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("5/11/2015");
            e = new Event("Mothers' Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("5/11/2014");
            e = new Event("Mothers' Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("6/15/2015");
            e = new Event("Fathers'Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("6/15/2014");
            e = new Event("Fathers'Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("7/4/2015");
            e = new Event("Independence Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("7/4/2014");
            e = new Event("Independence Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("9/1/2015");
            e = new Event("Labor Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("9/1/2014");
            e = new Event("Labor Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("10/13/2015");
            e = new Event("Columbus Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("10/13/2014");
            e = new Event("Columbus Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);


            d = df.parse("10/31/2015");
            e = new Event("Columbus Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("10/31/2014");
            e = new Event("Columbus Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("11/11/2015");
            e = new Event("Veterans Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("11/11/2014");
            e = new Event("Veterans Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("11/27/2015");
            e = new Event("Thanksgiving Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("11/27/2014");
            e = new Event("Thanksgiving Day", "", "All Day", "",holiday,d);
            uc.addEventsByDate(e,d);

            d = df.parse("8/11/2014");
            e = new Event("Vacation - WDW", "", "All Day", "",family,d);
            uc.addEventsByDate(e,d);

            d = df.parse("8/12/2014");
            e = new Event("Vacation - WDW", "", "All Day", "",family,d);
            uc.addEventsByDate(e,d);

            d = df.parse("8/13/2014");
            e = new Event("Vacation - WDW", "", "All Day", "",family,d);
            uc.addEventsByDate(e,d);

            d = df.parse("8/14/2014");
            e = new Event("Vacation - WDW", "", "All Day", "",family,d);
            uc.addEventsByDate(e,d);

            d = df.parse("8/15/2014");
            e = new Event("Vacation - WDW", "", "All Day", "",family,d);
            uc.addEventsByDate(e,d);

            d = df.parse("3/14/2014");
            e = new Event("Conference", "", "All Day", "",work,d);
            uc.addEventsByDate(e,d);

            d = df.parse("3/15/2014");
            e = new Event("Conference", "", "All Day", "",work,d);
            uc.addEventsByDate(e,d);

            d = df.parse("3/16/2014");
            e = new Event("Conference", "", "All Day", "",work,d);
            uc.addEventsByDate(e,d);

            d = df.parse("6/16/2014");
            e = new Event("Regents", "", "All Day", "",school,d);
            uc.addEventsByDate(e,d);

            d = df.parse("6/17/2014");
            e = new Event("Regents", "", "All Day", "",school,d);
            uc.addEventsByDate(e,d);

            d = df.parse("6/18/2014");
            e = new Event("Regents", "", "All Day", "",school,d);
            uc.addEventsByDate(e,d);

            d = df.parse("6/19/2014");
            e = new Event("Regents", "", "All Day", "",school,d);
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