package jingleheimercalendar;

/**
 * Created by Nathan on 10/21/2014.
 */

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;

class MonthPanel extends JPanel {
    public static final int DAYS_IN_WEEK = 7;
    public static final int NUM_ROWS = 6;
    public static final int NUM_COLUMNS = 7;
    public static final int NUM_DAYS_DISPLAYED = NUM_ROWS  * NUM_COLUMNS;

    public static final int CONTEXT_DAY = 1;
    public static final int CONTEXT_MONTH = 2;
    public static final int CONTEXT_YEAR = 3;

    private static final int COLUMN_PANE_HEIGHT = 50; //50 px

    public static final Color BLUE_SELECTED = new Color(76, 200, 237, 64);
    public static final Color BLUE_SELECTED_A25 = new Color(76, 200, 237, 64);
    public static final Color BLUE_SELECTED_DARK = new Color(0,100,128);
    public static final Color BLUE_SELECTED_MEDIUM = new Color(0,150,191);
    public static final Color DEFAULT_PANEL_BACKGROUND = Color.WHITE;
    public static final Color GRAY_CURRENT_DAY_BACKGROUND = Color.GRAY;
    public static final Color TEXT_BLACK = Color.BLACK;
    public static final Color TEXT_GRAY = Color.LIGHT_GRAY;

    public static enum DayLabels {
        SUNDAY ("SUN"),
        MONDAY ("MON"),
        TUESDAY ("TUE"),
        WEDNESDAY ("WED"),
        THURSDAY ("THU"),
        FRIDAY ("FRI"),
        SATURDAY ("SAT");

        private final String label;
        DayLabels(String label) {
            this.label = label;
        }
        private String getLabel() { return label; }
    }

    private int gridPaddingHorizontal = 0;
    private int gridPaddingVertical = 0;

    private Calendar monthCalendar;

    //Layout Managers
    //GridLayout for managing putting the GridPanes in rows/columns
    private GridLayout gridDayPanes;
    //GridLayout for managing putting the columnLabels into a row
    private GridLayout gridColumnLabels;
    //SpringLayout for manging the size of the columnPane and dayPane with respect to this container
    private SpringLayout springMonthPanel;

    private JLabel[] columnLabels;
    private JPanel columnPane;

    private JPanel dayPane;
    private DayPane[] dayPanes;
    private JLabel[] dayOrdinalLabels;

    //Date values from the current system date

    //This will be 1 (Calendar.SUNDAY) in America but would be 2 (Calendar.MONDAY) in France
    static int firstDayOfWeek;

    private int numDaysPreviousMonthDisplayed;

    private int currentWeekday;
    private int currentDate;
    //This value is 0 based; i.e. Calendar.JANUARY = 0 & Calendar.DECEMBER = 11
    private int currentMonth;
    private int currentYear;
    private int numDaysCurrentMonth;
    private int numDaysInPreviousMonth;

    private String fontPathHeaders = JingleheimerCalendar.PATH_FONT_KALINGA;
    private Font fontHeaders;
    private int fontSizeHeaders = 48;

    private Font fontOrdinals;
    private int fontSizeOrdinals = 48;
    private String fontPathOrdinals = JingleheimerCalendar.PATH_FONT_KALINGA;

    private DayPane selectedPanel;
    private DayPane currentDayPanel;

    private int viewContext;

    MonthPanel(int width, int height, int monthDelta, int context){
        setPreferredSize(new Dimension(width, height));

        viewContext = context;
        //Begin creating components
        columnPane = new JPanel();
        this.add(columnPane);
        columnPane.setLayout(gridColumnLabels);
        columnPane.setPreferredSize(new Dimension(width,COLUMN_PANE_HEIGHT));
        columnPane.setBackground(DEFAULT_PANEL_BACKGROUND);

        dayPane = new JPanel();
        this.add(dayPane);
        dayPane.setLayout(gridDayPanes);
        dayPane.setPreferredSize(new Dimension(width, height - COLUMN_PANE_HEIGHT));
        dayPane.setOpaque(false);

        createColumnHeaders(width, height);

        createDayOrdinals(width, height);

        //Get a Calendar object with today's date
        monthCalendar = Calendar.getInstance();

        //Get the value representing the first weekday of the calendar for the given locale (e.g. Sunday in USA)
        firstDayOfWeek = monthCalendar.getFirstDayOfWeek();

        //If monthDelta paramater is non-zero, apply that to this calendar first
        changeMonthBy(monthDelta);

        //Start by getting current system date
        updateDateFields();

        initializeDayPaneDates();

        springMonthPanel = new SpringLayout();
        this.setLayout(springMonthPanel);

        //Initialize the layout managers
        initializeLayoutManagers();

        //Set custom font
       try {
            fontHeaders = loadFont(fontSizeHeaders,fontPathHeaders);
            fontOrdinals = loadFont(fontSizeOrdinals,fontPathOrdinals);
        } catch (FontFormatException |IOException e) {
            System.err.println("Error loading custom font. Using Times.");
            fontHeaders = fontOrdinals = new Font("Times New Roman", Font.BOLD,60);
        }

        //Set the column headers text
        updateDayColumnHeaders();

        //Set the day ordinal labels text
        initializeDayOrdinals();

        //Initialize the currentDayPanel to the current selectedPanel
        if (viewContext == CONTEXT_MONTH) {
            currentDayPanel = selectedPanel;
            currentDayPanel.setIsCurrentDay(true);
        }
    }

    private void initializeDayPaneDates() {
        for (DayPane dayPane1 : dayPanes) {
            dayPane1.initializeDate();
        }
    }

    private class DayPane extends JPanel {
        private int month;
        private int year;
        private boolean isCurrentDay;

        DayPane() {
            super();
            //Default date context to current values
            initializeDate();
        }

        void initializeDate() {
            this.month = currentMonth;
            this.year = currentYear;
        }

        void setMonth(int monthDelta) {
            if (monthDelta == 0)
                return;
            switch ((month + monthDelta) % 12) {
                case Calendar.JANUARY:
                    month = Calendar.JANUARY;
                    break;
                case Calendar.FEBRUARY:
                    month = Calendar.FEBRUARY;
                    break;
                case Calendar.MARCH:
                    month = Calendar.MARCH;
                    break;
                case Calendar.APRIL:
                    month = Calendar.APRIL;
                    break;
                case Calendar.MAY:
                    month = Calendar.MAY;
                    break;
                case Calendar.JUNE:
                    month = Calendar.JUNE;
                    break;
                case Calendar.JULY:
                    month = Calendar.JULY;
                    break;
                case Calendar.AUGUST:
                    month = Calendar.AUGUST;
                    break;
                case Calendar.SEPTEMBER:
                    month = Calendar.SEPTEMBER;
                    break;
                case Calendar.OCTOBER:
                    month = Calendar.OCTOBER;
                    break;
                case Calendar.NOVEMBER:
                    month = Calendar.NOVEMBER;
                    break;
                case Calendar.DECEMBER:
                    month = Calendar.DECEMBER;
                    break;
            }
            //If monthDelta is positive (going forward in time relative to this) add to year (rounds down)
            //If monthDelta is negative (going backward in time relative to this) subtract from year (rounds down, hopefully)
            year += (monthDelta > 0) ? (monthDelta /12) : -(monthDelta/12);
        }

        void setIsCurrentDay(boolean isCurrentDay) {
            this.isCurrentDay = isCurrentDay;
        }

        public boolean getIsCurrentDay() {
            return isCurrentDay;
        }

        public int getMonth(){
            return month;
        }

        public int getYear() {
            return year;
        }
    }

    public int getCurrentWeekday() {
        return currentWeekday;
    }

    public String getCurrentWeekdayString() {
        switch (currentWeekday) {
            case Calendar.SUNDAY:
                return "SUNDAY";
            case Calendar.MONDAY:
                return "MONDAY";
            case Calendar.TUESDAY:
                return "TUESDAY";
            case Calendar.WEDNESDAY:
                return "WEDNESDAY";
            case Calendar.THURSDAY:
                return "THURSDAY";
            case Calendar.FRIDAY:
                return "FRIDAY";
            case Calendar.SATURDAY:
                return "SATURDAY";
            default:
                return "";
        }
    }

    public int getCurrentDate() {
        return currentDate;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public String getCurrentMonthString() {
        switch (currentMonth) {
            case Calendar.JANUARY:
                return "JANUARY";
            case Calendar.FEBRUARY:
                return "FEBRUARY";
            case Calendar.MARCH:
                return "MARCH";
            case Calendar.APRIL:
                return "APRIL";
            case Calendar.MAY:
                return "MAY";
            case Calendar.JUNE:
                return "JUNE";
            case Calendar.JULY:
                return "JULY";
            case Calendar.AUGUST:
                return "AUGUST";
            case Calendar.SEPTEMBER:
                return "SEPTEMBER";
            case Calendar.OCTOBER:
                return "OCTOBER";
            case Calendar.NOVEMBER:
                return "NOVEMBER";
            case Calendar.DECEMBER:
                return "DECEMBER";
            default:
                return "";
        }
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public int getNumDaysCurrentMonth() {
        return numDaysCurrentMonth;
    }

    public int getNumDaysInPreviousMonth() {
        return numDaysInPreviousMonth;
    }

    private class MonthViewDayPanelClickedListener implements MouseListener{
        //Fired upon successful press + release;
        //Called after mouseReleased
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2 && viewContext == CONTEXT_MONTH) {
                JingleheimerCalendar.displayView(JingleheimerCalendar.INDEX_DAY_VIEW);
                //Implement logic for changing context to current day here
            } else {
                //TODO
                //Work in progress to change current month
                DayPane parent = (DayPane) e.getComponent();
                if (parent.getMonth() != currentMonth) {
                    changeMonthBy(parent.getMonth() - currentMonth);
                    MonthView.monthHeader.updateMonthLabel();
                }
            }
        }

        //Fired upon mouse press
        @Override
        public void mousePressed(MouseEvent e) {
            DayPane parent = (DayPane) e.getComponent();
            DayPane previousSelected = selectedPanel;
            changeSelectedPanel(parent);
            if (previousSelected == currentDayPanel)
                currentDayPanel.setBackground(GRAY_CURRENT_DAY_BACKGROUND);
        }

        //Fired upon mouse depress
        @Override
        public void mouseReleased(MouseEvent e) {
        }

        //Fired upon mouse cursor entering bounds of component
        @Override
        public void mouseEntered(MouseEvent e) {
        }

        //Fired upon mouse cursor exiting bounds of component
        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    //Work in progress
    /*private class textSizeListener implements ComponentListener {
        @Override
        public void componentResized(ComponentEvent e) {
            Graphics g = getGraphics();
            boolean fontHeadersChanged = false;
            if (fontHeaders != null) {
                FontMetrics fontMetricsHeaders = g.getFontMetrics(fontHeaders);
                for (JLabel columnLabel: columnLabels) {
                    if (fontMetricsHeaders.getStringBounds(columnLabel.getText(),g)
                            .intersects(columnLabel.getBounds())) {
                        setFontSizeHeaders(fontSizeHeaders - 2);
                        fontHeadersChanged = true;
                        break;
                    }
                }
            }
            if (fontHeadersChanged) {
                for (JLabel columnLabel: columnLabels) {
                    columnLabel.setFont(fontHeaders);
                }
            }
            boolean fontOrdinalsChanged = false;
            if (fontOrdinals != null) {
                FontMetrics fontMetricsOrdinals = g.getFontMetrics(fontOrdinals);
                for (JLabel dayLabel: dayOrdinalLabels) {
                    if (fontMetricsOrdinals.getStringBounds(dayLabel.getText(),g)
                            .intersects(dayLabel.getBounds())) {
                        setFontSizeOrdinals(fontSizeOrdinals - 2);
                        fontOrdinalsChanged = true;
                        break;
                    }
                }
            }
            if (fontOrdinalsChanged) {
                for (JLabel dayLabel : dayOrdinalLabels) {
                    dayLabel.setFont(fontOrdinals);
                }
            }

        }

        @Override
        public void componentMoved(ComponentEvent e) {

        }

        @Override
        public void componentShown(ComponentEvent e) {

        }

        @Override
        public void componentHidden(ComponentEvent e) {

        }
    }*/

    //Set up the LayoutManagers
    private void initializeLayoutManagers() {
        //Start with MonthPanel and columnPane,dayPane relationships
        springMonthPanel.putConstraint(SpringLayout.WEST, columnPane, 0, SpringLayout.WEST, this);
        springMonthPanel.putConstraint(SpringLayout.EAST, columnPane, 0, SpringLayout.EAST, this);
        springMonthPanel.putConstraint(SpringLayout.NORTH, columnPane, 0, SpringLayout.NORTH, this);


        springMonthPanel.putConstraint(SpringLayout.WEST, dayPane, 0, SpringLayout.WEST, this);
        springMonthPanel.putConstraint(SpringLayout.EAST, dayPane, 0, SpringLayout.EAST, this);
        springMonthPanel.putConstraint(SpringLayout.NORTH, dayPane, 0, SpringLayout.SOUTH, columnPane);
        springMonthPanel.putConstraint(SpringLayout.SOUTH, dayPane, 0, SpringLayout.SOUTH, this);

        //Configure columnLabels gridlayout
        gridColumnLabels = new GridLayout(1, NUM_COLUMNS, gridPaddingHorizontal, gridPaddingVertical);
        columnPane.setLayout(gridColumnLabels);

        //Configure ordinalLabels and dayPanes gridlayout
        gridDayPanes = new GridLayout(NUM_ROWS, NUM_COLUMNS, gridPaddingHorizontal, gridPaddingVertical);
        dayPane.setLayout(gridDayPanes);
    }

    private Font loadFont(int fSize, String fPath) throws FontFormatException, IOException  {
        URL fontUrl = getClass().getResource(fPath);
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
        font = font.deriveFont(Font.PLAIN, fSize);
        return font;
    }

    public void setGridPaddingHorizontal(int padding) {
        gridPaddingHorizontal = padding;
        gridDayPanes.setHgap(gridPaddingHorizontal);
    }

    public void setGridPaddingVertical(int padding) {
        gridPaddingVertical = padding;
        gridColumnLabels.setVgap(padding);
    }

    private void updateHeaderFonts() {
        if (columnLabels != null) {
            for (JLabel columnLabel : columnLabels) {
                columnLabel.setFont(fontHeaders);
            }
        }

    }

    private void updateOrdinalFonts() {
        if (dayOrdinalLabels != null) {
            for (JLabel dayLabel : dayOrdinalLabels) {
                dayLabel.setFont(fontOrdinals);
            }
        }

    }

    public void setFontSizeHeaders(int size) {
        fontSizeHeaders = size;
        fontHeaders = fontHeaders.deriveFont((float)fontSizeHeaders);
        updateHeaderFonts();
    }

    public void setFontSizeOrdinals(int size) {
        fontSizeOrdinals = size;
        fontOrdinals = fontOrdinals.deriveFont(((float) fontSizeOrdinals));
        updateOrdinalFonts();
    }

    public void setFontPathOrdinals(String path) {
        fontPathOrdinals = path;
        try {
            fontOrdinals = loadFont(fontSizeOrdinals,fontPathOrdinals);
            updateOrdinalFonts();
        } catch (FontFormatException |IOException e) {
            System.err.println("Error loading custom font. Using Times.");
            fontOrdinals = new Font("Times New Roman", Font.BOLD,60);
        }
    }

    public void setFontPathHeaders(String path) {
        fontPathHeaders = path;
        try {
            fontHeaders = loadFont(fontSizeHeaders,fontPathHeaders);
            updateHeaderFonts();
        } catch (FontFormatException |IOException e) {
            System.err.println("Error loading custom font. Using Times.");
            fontHeaders = new Font("Times New Roman", Font.BOLD,60);
        }
    }

    //Supply monthDelta according to the following formula
    //monthDelta = (month - currentMonth) + (12 * (year - currentYear));
    public void changeMonthBy(int monthDelta) {
        //Same month and year was passed as current Calendar object
        if (monthDelta == 0)
            return;
        monthCalendar.add(Calendar.MONTH, monthDelta);
        updateDateFields();
        updateDayOrdinals();
    }

    private void updateDayPanes(int monthDelta) {
        for (DayPane dayPane1 : dayPanes){
            dayPane1.setMonth(monthDelta);
        }
    }

    //Call this when the month displayed would be changed
    private void updateDateFields() {
        currentMonth = monthCalendar.get(Calendar.MONTH);
        currentWeekday = monthCalendar.get(Calendar.DAY_OF_WEEK);
        currentDate = monthCalendar.get(Calendar.DATE);
        currentYear = monthCalendar.get(Calendar.YEAR);
        numDaysCurrentMonth = monthCalendar.getActualMaximum(Calendar.DATE);

        Calendar tempCalendar = (Calendar) monthCalendar.clone();
        tempCalendar.set(Calendar.DATE, 1);
        int weekdayOfFirstDayInMonth = tempCalendar.get(Calendar.DAY_OF_WEEK);

        switch (weekdayOfFirstDayInMonth) {
            case Calendar.SUNDAY:
                numDaysPreviousMonthDisplayed = Calendar.SUNDAY - firstDayOfWeek;
                break;
            case Calendar.MONDAY:
                numDaysPreviousMonthDisplayed = Calendar.MONDAY - firstDayOfWeek;
                break;
            case Calendar.TUESDAY:
                numDaysPreviousMonthDisplayed = Calendar.TUESDAY - firstDayOfWeek;
                break;
            case Calendar.WEDNESDAY:
                numDaysPreviousMonthDisplayed = Calendar.WEDNESDAY - firstDayOfWeek;
                break;
            case Calendar.THURSDAY:
                numDaysPreviousMonthDisplayed = Calendar.THURSDAY - firstDayOfWeek;
                break;
            case Calendar.FRIDAY:
                numDaysPreviousMonthDisplayed = Calendar.FRIDAY - firstDayOfWeek;
                break;
            case Calendar.SATURDAY:
                numDaysPreviousMonthDisplayed = Calendar.SATURDAY - firstDayOfWeek;
                break;
        }

        //Obtain numDays in previous month
        tempCalendar = (Calendar) monthCalendar.clone();
        if (numDaysPreviousMonthDisplayed > 0) {
            tempCalendar.add(Calendar.MONTH, -1);
            numDaysInPreviousMonth = tempCalendar.getActualMaximum(Calendar.DATE);
        }

    }

    //Create the column header labels
    private void createColumnHeaders(int width, int height) {
        //Calculate preferred size of each label
        int columnLabelWidth = width / NUM_COLUMNS;
        int overflowPixelsWidth = width % NUM_COLUMNS;
        int columnLabelHeight = COLUMN_PANE_HEIGHT;

        columnLabels = new JLabel[DAYS_IN_WEEK];
        for (int i = 0; i < DAYS_IN_WEEK; i++,overflowPixelsWidth--) {
            columnLabels[i] = new JLabel("", SwingConstants.CENTER);
            columnLabels[i].setVerticalAlignment(SwingConstants.CENTER);
            columnLabels[i].setForeground(TEXT_BLACK);
            if (overflowPixelsWidth > 0)
                columnLabels[i].setPreferredSize(new Dimension(columnLabelWidth + 1,columnLabelHeight));
            else
                columnLabels[i].setPreferredSize(new Dimension(columnLabelWidth, columnLabelHeight));
            columnPane.add(columnLabels[i]);
        }
    }

    //Used to set the values of the text of the column header labels
    private void updateDayColumnHeaders() {
        String dayLabel = "";
        for (int i = 0; i < DAYS_IN_WEEK; i++) {
            switch ( (firstDayOfWeek + i) % DAYS_IN_WEEK) {
                case Calendar.SUNDAY:
                    dayLabel = DayLabels.SUNDAY.getLabel();
                    break;
                case Calendar.MONDAY:
                    dayLabel = DayLabels.MONDAY.getLabel();
                    break;
                case Calendar.TUESDAY:
                    dayLabel = DayLabels.TUESDAY.getLabel();
                    break;
                case Calendar.WEDNESDAY:
                    dayLabel = DayLabels.WEDNESDAY.getLabel();
                    break;
                case Calendar.THURSDAY:
                    dayLabel = DayLabels.THURSDAY.getLabel();
                    break;
                case Calendar.FRIDAY:
                    dayLabel = DayLabels.FRIDAY.getLabel();
                    break;
                case 0: //Calendar.SATURDAY = 7 so 7 % 7 = 0
                    dayLabel = DayLabels.SATURDAY.getLabel();
                    break;
            }
            columnLabels[i].setText(dayLabel);
            columnLabels[i].setFont(fontHeaders);
        }
    }

    private void createDayOrdinals(int width, int height) {
        //Calculate preferred size of each label/panel (Same size for now)
        int paneWidth = width / NUM_COLUMNS;

        int paneHeight = (height - COLUMN_PANE_HEIGHT) / NUM_ROWS;

        //Random r = new Random();

        SpringLayout sl = new SpringLayout();
        dayOrdinalLabels = new JLabel[NUM_DAYS_DISPLAYED];
        dayPanes = new DayPane[NUM_DAYS_DISPLAYED];
        for (int i = 0; i < NUM_DAYS_DISPLAYED; i++) {
            dayPanes[i] = new DayPane();
            dayPanes[i].setPreferredSize(new Dimension(paneWidth,paneHeight));
            dayPanes[i].setLayout(sl);
            dayPanes[i].setBackground(DEFAULT_PANEL_BACKGROUND);
            dayPanes[i].addMouseListener(new MonthViewDayPanelClickedListener());
            dayOrdinalLabels[i] = new JLabel("",SwingConstants.CENTER);
            dayOrdinalLabels[i].setVerticalAlignment(SwingConstants.CENTER);
            //Testing line to set random borders on each JLabel
            //dayOrdinalLabels[i].setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256))));
            dayPanes[i].add(dayOrdinalLabels[i]);
            sl.putConstraint(SpringLayout.NORTH, dayOrdinalLabels[i], 0, SpringLayout.NORTH, dayPanes[i]);
            sl.putConstraint(SpringLayout.SOUTH, dayOrdinalLabels[i], 0, SpringLayout.SOUTH, dayPanes[i]);
            sl.putConstraint(SpringLayout.WEST, dayOrdinalLabels[i], 0, SpringLayout.WEST, dayPanes[i]);
            sl.putConstraint(SpringLayout.EAST, dayOrdinalLabels[i], 0, SpringLayout.EAST, dayPanes[i]);
            dayPane.add(dayPanes[i]);
        }
    }

    private void changeSelectedPanel(DayPane panel) {
        if (viewContext == CONTEXT_MONTH){
            panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, BLUE_SELECTED, BLUE_SELECTED_DARK));
            panel.setBackground(BLUE_SELECTED_MEDIUM);
            if (selectedPanel != null) {
                selectedPanel.setBackground(DEFAULT_PANEL_BACKGROUND);
                selectedPanel.setBorder(null);
            }
            selectedPanel = panel;
        }
    }

    //Update the text of the Ordinal Day labels
    //TODO
    //Bug in navigation is in here; calling setMonth twice for first and last labels
    private void updateDayOrdinals() {
        int labelsIndex;

        //System.out.println("Previous Month Days: ");
        //Previous month labels
        for (labelsIndex = 0; labelsIndex < numDaysPreviousMonthDisplayed; labelsIndex++) {
            dayOrdinalLabels[labelsIndex].setText(String.valueOf(numDaysInPreviousMonth - numDaysPreviousMonthDisplayed + 1 + labelsIndex));
            dayOrdinalLabels[labelsIndex].setFont(fontOrdinals);
            dayOrdinalLabels[labelsIndex].setForeground(TEXT_GRAY);
            //System.out.println(dayPanes[labelsIndex].getMonth());
            //System.out.println(dayPanes[labelsIndex].getMonth());
        }

        //System.out.println("Current Month Days: ");
        //Current month labels
        for (; labelsIndex < (numDaysCurrentMonth + numDaysPreviousMonthDisplayed); labelsIndex++) {
            //System.out.println("labelsIndex: " + labelsIndex + " - " + " numDayPreviousMonthDisplayed: " + numDaysPreviousMonthDisplayed + " ==  " + "currentDate: " + currentDate);
            if ((labelsIndex - numDaysPreviousMonthDisplayed) == currentDate && viewContext == CONTEXT_MONTH) {
                changeSelectedPanel(dayPanes[labelsIndex - 1]);
            }
            dayOrdinalLabels[labelsIndex].setText(String.valueOf(1 + labelsIndex - numDaysPreviousMonthDisplayed));
            dayOrdinalLabels[labelsIndex].setForeground(Color.BLACK);
            dayOrdinalLabels[labelsIndex].setFont(fontOrdinals);
            //System.out.println(dayPanes[labelsIndex].getMonth());
        }

        //System.out.println("Next Month days: ");
        //Next month labels
        for (int i = 1; labelsIndex < NUM_DAYS_DISPLAYED; labelsIndex++, i++) {
            dayOrdinalLabels[labelsIndex].setText(String.valueOf(i));
            dayOrdinalLabels[labelsIndex].setFont(fontOrdinals);
            dayOrdinalLabels[labelsIndex].setForeground(TEXT_GRAY);
            //System.out.println(dayPanes[labelsIndex].getMonth());
            //System.out.println(dayPanes[labelsIndex].getMonth());
        }
    }

    private void initializeDayOrdinals() {
        int labelsIndex;

        //System.out.println("Previous Month Days: ");
        //Previous month labels
        for (labelsIndex = 0; labelsIndex < numDaysPreviousMonthDisplayed; labelsIndex++) {
            dayOrdinalLabels[labelsIndex].setText(String.valueOf(numDaysInPreviousMonth - numDaysPreviousMonthDisplayed + 1 + labelsIndex));
            dayOrdinalLabels[labelsIndex].setFont(fontOrdinals);
            dayOrdinalLabels[labelsIndex].setForeground(TEXT_GRAY);
            //System.out.println(dayPanes[labelsIndex].getMonth());
            dayPanes[labelsIndex].setMonth(-1);
            //System.out.println(dayPanes[labelsIndex].getMonth());
        }

        //System.out.println("Current Month Days: ");
        //Current month labels
        for (; labelsIndex < (numDaysCurrentMonth + numDaysPreviousMonthDisplayed); labelsIndex++) {
            //System.out.println("labelsIndex: " + labelsIndex + " - " + " numDayPreviousMonthDisplayed: " + numDaysPreviousMonthDisplayed + " ==  " + "currentDate: " + currentDate);
            if ((labelsIndex - numDaysPreviousMonthDisplayed) == currentDate){
                changeSelectedPanel(dayPanes[labelsIndex - 1]);
            }
            dayOrdinalLabels[labelsIndex].setText(String.valueOf(1 + labelsIndex - numDaysPreviousMonthDisplayed));
            dayOrdinalLabels[labelsIndex].setForeground(Color.BLACK);
            dayOrdinalLabels[labelsIndex].setFont(fontOrdinals);
            //System.out.println(dayPanes[labelsIndex].getMonth());
        }

        //System.out.println("Next Month days: ");
        //Next month labels
        for (int i = 1; labelsIndex < NUM_DAYS_DISPLAYED; labelsIndex++,i++) {
            dayOrdinalLabels[labelsIndex].setText(String.valueOf(i));
            dayOrdinalLabels[labelsIndex].setFont(fontOrdinals);
            dayOrdinalLabels[labelsIndex].setForeground(TEXT_GRAY);
            //System.out.println(dayPanes[labelsIndex].getMonth());
            dayPanes[labelsIndex].setMonth(1);
            //System.out.println(dayPanes[labelsIndex].getMonth());
        }
    }

}