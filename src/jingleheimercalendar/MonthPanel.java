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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

class MonthPanel extends JPanel {
    public static final int DAYS_IN_WEEK = 7;
    public static final int NUM_ROWS = 6;
    public static final int NUM_COLUMNS = 7;
    public static final int NUM_DAYS_DISPLAYED = NUM_ROWS  * NUM_COLUMNS;

    private static final int COLUMN_PANE_HEIGHT = 50; //50 px

   // public static final Color BLUE_SELECTED = new Color(76, 200, 237, 64);
    public static final Color BLUE_SELECTED_A25 = new Color(0,150,191);
    //public static final Color BLUE_SELECTED_A25 = new Color(76, 200, 237, 64);
    public static final Color BLUE_SELECTED_DARK = new Color(0,100,128);
    public static final Color BLUE_SELECTED_MEDIUM = new Color(76, 200, 237, 196);
    //public static final Color BLUE_SELECTED_MEDIUM = new Color(0,150,191);
    public static final Color DEFAULT_PANEL_BACKGROUND = Color.WHITE;
    public static final Color GRAY_CURRENT_DAY_BACKGROUND = new Color(128,128,128,128);
    public static final Color GRAY_CURRENT_DAY_SUBTLE = new Color(196, 196, 196, 128);
    public static final Color TEXT_BLACK = Color.BLACK;
    public static final Color TEXT_GRAY = Color.LIGHT_GRAY;

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

    private DayPane currentDay;

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
        updateDayColumnHeaders(DAY_LABELS_MONTHVIEW_CONTEXT);

        //Set the day ordinal labels text
        updateDayOrdinals();
    }

    void updateDayPanelClickListener(MouseListener mouseListener) {
        for (DayPane dayPane : dayPanes) {
            dayPane.addMouseListener(mouseListener);
        }
    }

    public DayPane getDayPane(int day) {
        return dayPanes[numDaysPreviousMonthDisplayed + day - 1];
    }

    public DayPane getDayPane(int day, int monthContext) {
        switch (monthContext) {
            case DayPane.SWITCH_PREVIOUS_MONTH:
                System.out.println("getDayPane.switch_current_month: " + String.valueOf(numDaysInPreviousMonth - day));
                return dayPanes[numDaysInPreviousMonth - day];
            case DayPane.SWITCH_CURRENT_MONTH:
                System.out.println("getDayPane.switch_current_month: " + String.valueOf(numDaysPreviousMonthDisplayed + day - 1));
                return dayPanes[numDaysPreviousMonthDisplayed + day - 1];
            case DayPane.SWITCH_NEXT_MONTH:
                System.out.println("getDayPane.switch_next_month: " + String.valueOf(numDaysPreviousMonthDisplayed + numDaysCurrentMonth + day - 1));
                return dayPanes[numDaysPreviousMonthDisplayed + numDaysCurrentMonth + day - 1];
            default:
                return null;
        }
    }

    public DayPane getDayPaneAtIndex(int index) {
        if (index >= 0 && index < NUM_DAYS_DISPLAYED)
            return dayPanes[index];
        return null;
    }

    protected void setColumnPaneSize(int width, int height) {
        columnPane.setPreferredSize(new Dimension(width, height));
        int columnLabelWidth = width / NUM_COLUMNS;
        int overflowPixelsWidth = width % NUM_COLUMNS;
        for (int i = 0; i < DAYS_IN_WEEK; i++,overflowPixelsWidth--) {
            if (overflowPixelsWidth > 0)
                columnLabels[i].setPreferredSize(new Dimension(columnLabelWidth + 1,height));
            else
                columnLabels[i].setPreferredSize(new Dimension(columnLabelWidth, height));
        }
    }

    protected DayPane getCurrentDayPane() {
        return currentDay;
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
                return "January";
            case Calendar.FEBRUARY:
                return "February";
            case Calendar.MARCH:
                return "March";
            case Calendar.APRIL:
                return "April";
            case Calendar.MAY:
                return "May";
            case Calendar.JUNE:
                return "June";
            case Calendar.JULY:
                return "July";
            case Calendar.AUGUST:
                return "August";
            case Calendar.SEPTEMBER:
                return "September";
            case Calendar.OCTOBER:
                return "October";
            case Calendar.NOVEMBER:
                return "November";
            case Calendar.DECEMBER:
                return "December";
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
        for (DayPane pane : dayPanes) {
            pane.updateOrdinalFont(fontOrdinals);
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

    public void changeMonthBy(int day, int month, int year){
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        int m = month + 1;
        String date = String.valueOf(month >= 10 ? m : "0" + m) + "/" + String.valueOf(day >= 10 ? day : "0" + day)
                + "/" + String.valueOf(year);
        try {
            monthCalendar.setTime(df.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    public static final int DAY_LABELS_MONTHVIEW_CONTEXT = 0;
    public static final int DAY_LABELS_DAYVIEW_CONTEXT = 1;
    public static final int DAY_LABELS_YEARVIEW_CONTEXT = 2;

    //Used to set the values of the text of the column header labels
    void updateDayColumnHeaders(int context) {
        String dayLabel = "";
        for (int i = 0; i < DAYS_IN_WEEK; i++) {
            if (context == DAY_LABELS_MONTHVIEW_CONTEXT) {
                switch ( (firstDayOfWeek + i) % DAYS_IN_WEEK) {
                    case Calendar.SUNDAY:
                        dayLabel = "Sun";
                        break;
                    case Calendar.MONDAY:
                        dayLabel = "Mon";
                        break;
                    case Calendar.TUESDAY:
                        dayLabel = "Tue";
                        break;
                    case Calendar.WEDNESDAY:
                        dayLabel = "Wed";
                        break;
                    case Calendar.THURSDAY:
                        dayLabel = "Thu";
                        break;
                    case Calendar.FRIDAY:
                        dayLabel = "Fri";
                        break;
                    case 0: //Calendar.SATURDAY = 7 so 7 % 7 = 0
                        dayLabel = "Sat";
                        break;
                }
            } else if (context == DAY_LABELS_DAYVIEW_CONTEXT) {
                switch ( (firstDayOfWeek + i) % DAYS_IN_WEEK) {
                    case Calendar.SUNDAY:
                        dayLabel = "Su";
                        break;
                    case Calendar.MONDAY:
                        dayLabel = "Mo";
                        break;
                    case Calendar.TUESDAY:
                        dayLabel = "Tu";
                        break;
                    case Calendar.WEDNESDAY:
                        dayLabel = "We";
                        break;
                    case Calendar.THURSDAY:
                        dayLabel = "Th";
                        break;
                    case Calendar.FRIDAY:
                        dayLabel = "Fr";
                        break;
                    case 0: //Calendar.SATURDAY = 7 so 7 % 7 = 0
                        dayLabel = "Sa";
                        break;
                }
            } else if (context == DAY_LABELS_YEARVIEW_CONTEXT) {
                switch ( (firstDayOfWeek + i) % DAYS_IN_WEEK) {
                    case Calendar.SUNDAY:
                        dayLabel = "S";
                        break;
                    case Calendar.MONDAY:
                        dayLabel = "M";
                        break;
                    case Calendar.TUESDAY:
                        dayLabel = "T";
                        break;
                    case Calendar.WEDNESDAY:
                        dayLabel = "W";
                        break;
                    case Calendar.THURSDAY:
                        dayLabel = "R";
                        break;
                    case Calendar.FRIDAY:
                        dayLabel = "F";
                        break;
                    case 0: //Calendar.SATURDAY = 7 so 7 % 7 = 0
                        dayLabel = "S";
                        break;
                }
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
        dayPanes = new DayPane[NUM_DAYS_DISPLAYED];
        for (int i = 0; i < NUM_DAYS_DISPLAYED; i++) {
            JLabel ordinal = new JLabel("",SwingConstants.CENTER);
            ordinal.setVerticalAlignment(SwingConstants.CENTER);
            dayPanes[i] = new DayPane(this, paneWidth, paneHeight, ordinal);
            //Testing line to set random borders on each JLabel
            //dayOrdinalLabels[i].setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256))));
            dayPane.add(dayPanes[i]);
        }
    }

    public Calendar getCalendarCopy() {
        return (Calendar)monthCalendar.clone();
    }

    public int getNumDaysPreviousMonthDisplayed() {
        return numDaysPreviousMonthDisplayed;
    }

    //Update the text of the Ordinal Day labels
    protected void updateDayOrdinals() {
        if (currentDay != null)
            currentDay.setIsCurrentDay(false);

        int labelsIndex;

        //Previous month labels
        for (labelsIndex = 0; labelsIndex < numDaysPreviousMonthDisplayed; labelsIndex++) {
            dayPanes[labelsIndex].updateOrdinalLabel(String.valueOf(numDaysInPreviousMonth - numDaysPreviousMonthDisplayed + 1 + labelsIndex), fontOrdinals ,TEXT_GRAY);
            dayPanes[labelsIndex].setMonthContext(DayPane.SWITCH_PREVIOUS_MONTH);
        }

        //Current month labels
        for (; labelsIndex < (numDaysCurrentMonth + numDaysPreviousMonthDisplayed); labelsIndex++) {
            dayPanes[labelsIndex].updateOrdinalLabel(String.valueOf(1 + labelsIndex - numDaysPreviousMonthDisplayed), fontOrdinals, Color.BLACK);
            dayPanes[labelsIndex].setMonthContext(DayPane.SWITCH_CURRENT_MONTH);
        }

        //Next month labels
        for (int i = 1; labelsIndex < NUM_DAYS_DISPLAYED; labelsIndex++, i++) {
            dayPanes[labelsIndex].updateOrdinalLabel(String.valueOf(i), fontOrdinals, TEXT_GRAY);
            dayPanes[labelsIndex].setMonthContext(DayPane.SWITCH_NEXT_MONTH);
        }

        markCurrentDay();
    }

    private void markCurrentDay() {
        //Logic for marking current day
        Calendar temp = Calendar.getInstance();
        if (temp.get(Calendar.MONTH) == currentMonth && temp.get(Calendar.YEAR) == currentYear) {
            int day = temp.get(Calendar.DATE);
            dayPanes[numDaysPreviousMonthDisplayed + day - 1].setIsCurrentDay(true);
            currentDay = dayPanes[numDaysPreviousMonthDisplayed + day - 1];
        }
    }
}