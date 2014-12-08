package jingleheimercalendar;

/**
 * Created by Nathan on 10/21/2014.
 */

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.util.Calendar;

class MonthPanel extends JPanel {
    public static final int DAYS_IN_WEEK = 7;
    public static final int NUM_ROWS = 6;
    public static final int NUM_COLUMNS = 7;
    public static final int NUM_DAYS_DISPLAYED = NUM_ROWS  * NUM_COLUMNS;

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

    protected int currentWeekday;
    protected int currentDate;
    //This value is 0 based; i.e. Calendar.JANUARY = 0 & Calendar.DECEMBER = 11
    protected int currentMonth;
    protected int currentYear;
    protected int numDaysCurrentMonth;
    protected int numDaysInPreviousMonth;

    private Font fontHeaders;
    private int fontSizeHeaders = 48;

    private Font fontOrdinals;
    private int fontSizeOrdinals = 48;

    MonthPanel(int width, int height, int monthDelta){
        setPreferredSize(new Dimension(width, height));

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

        springMonthPanel = new SpringLayout();
        this.setLayout(springMonthPanel);

        //Initialize the layout managers
        initializeLayoutManagers();

        //Set custom font
       fontHeaders = JingleheimerCalendar.defaultFont.deriveFont((float)fontSizeHeaders);
       fontOrdinals = JingleheimerCalendar.defaultFont.deriveFont((float)fontSizeOrdinals);

        //Set the column headers text
        updateDayColumnHeaders();

        //Set the day ordinal labels text
        initializeDayOrdinals();

        //Initialize the currentDayPanel to the current selectedPanel
        //currentDayPanel = selectedPanel;
        //currentDayPanel.setIsCurrentDay(true);
    }

    void updateDayPanelClickListener(MouseListener mouseListener) {
        for (DayPane dayPane : dayPanes) {
            dayPane.addMouseListener(mouseListener);
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

        if (weekdayOfFirstDayInMonth == firstDayOfWeek) {
            numDaysPreviousMonthDisplayed = 7;
        } else {
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
        }

        //Obtain numDays in previous month
        tempCalendar = (Calendar) monthCalendar.clone();
        //Need to get dates in previous month
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
            dayPanes[i] = new DayPane(this);
            dayPanes[i].setPreferredSize(new Dimension(paneWidth,paneHeight));
            dayPanes[i].setLayout(sl);
            dayPanes[i].setBackground(DEFAULT_PANEL_BACKGROUND);
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

    //Update the text of the Ordinal Day labels
    private void updateDayOrdinals() {
        int labelsIndex;

        //Previous month labels
        for (labelsIndex = 0; labelsIndex < numDaysPreviousMonthDisplayed; labelsIndex++) {
            dayOrdinalLabels[labelsIndex].setText(String.valueOf(numDaysInPreviousMonth - numDaysPreviousMonthDisplayed + 1 + labelsIndex));
            dayOrdinalLabels[labelsIndex].setFont(fontOrdinals);
            dayOrdinalLabels[labelsIndex].setForeground(TEXT_GRAY);
            dayPanes[labelsIndex].setMonthContext(DayPane.SWITCH_PREVIOUS_MONTH);
        }

        //Current month labels
        for (; labelsIndex < (numDaysCurrentMonth + numDaysPreviousMonthDisplayed); labelsIndex++) {
            dayOrdinalLabels[labelsIndex].setText(String.valueOf(1 + labelsIndex - numDaysPreviousMonthDisplayed));
            dayOrdinalLabels[labelsIndex].setForeground(Color.BLACK);
            dayOrdinalLabels[labelsIndex].setFont(fontOrdinals);
            dayPanes[labelsIndex].setMonthContext(DayPane.SWITCH_CURRENT_MONTH);
        }

        //Next month labels
        for (int i = 1; labelsIndex < NUM_DAYS_DISPLAYED; labelsIndex++, i++) {
            dayOrdinalLabels[labelsIndex].setText(String.valueOf(i));
            dayOrdinalLabels[labelsIndex].setFont(fontOrdinals);
            dayOrdinalLabels[labelsIndex].setForeground(TEXT_GRAY);
            dayPanes[labelsIndex].setMonthContext(DayPane.SWITCH_NEXT_MONTH);
        }
    }

    private void initializeDayOrdinals() {
        int labelsIndex;

        //Previous month labels
        for (labelsIndex = 0; labelsIndex < numDaysPreviousMonthDisplayed; labelsIndex++) {
            dayOrdinalLabels[labelsIndex].setText(String.valueOf(numDaysInPreviousMonth - numDaysPreviousMonthDisplayed + 1 + labelsIndex));
            dayOrdinalLabels[labelsIndex].setFont(fontOrdinals);
            dayOrdinalLabels[labelsIndex].setForeground(TEXT_GRAY);
            dayPanes[labelsIndex].setMonthContext(DayPane.SWITCH_PREVIOUS_MONTH);
        }

        //Current month labels
        for (; labelsIndex < (numDaysCurrentMonth + numDaysPreviousMonthDisplayed); labelsIndex++) {
            dayOrdinalLabels[labelsIndex].setText(String.valueOf(1 + labelsIndex - numDaysPreviousMonthDisplayed));
            dayOrdinalLabels[labelsIndex].setForeground(Color.BLACK);
            dayOrdinalLabels[labelsIndex].setFont(fontOrdinals);
            dayPanes[labelsIndex].setMonthContext(DayPane.SWITCH_CURRENT_MONTH);
        }

        //Next month labels
        for (int i = 1; labelsIndex < NUM_DAYS_DISPLAYED; labelsIndex++,i++) {
            dayOrdinalLabels[labelsIndex].setText(String.valueOf(i));
            dayOrdinalLabels[labelsIndex].setFont(fontOrdinals);
            dayOrdinalLabels[labelsIndex].setForeground(TEXT_GRAY);
            dayPanes[labelsIndex].setMonthContext(DayPane.SWITCH_NEXT_MONTH);
        }
    }

}