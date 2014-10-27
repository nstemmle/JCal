package jingleheimercalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Random;

class MonthPanel extends JPanel {
    public static final int DAYS_IN_WEEK = 7;
    public static final int NUM_ROWS = 5;
    public static final int NUM_COLUMNS = 7;
    public static final int NUM_DAYS_DISPLAYED = NUM_ROWS  * NUM_COLUMNS;

    private static final int COLUMN_PANE_HEIGHT = 50; //50 px

    int gridPaddingHorizontal = 0;
    int gridPaddingVertical = 0;

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
    private JPanel[] dayPanes;
    private JLabel[] dayOrdinalLabels;

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

    private String fontPathHeaders = "/font/Nexa_Bold.ttf";
    private String fontPathOrdinals = "/font/Nexa_Bold.ttf";

    private Font fontHeaders;
    private int fontSizeHeaders = 54;

    private Font fontOrdinals;
    private int fontSizeOrdinals = 54;


    MonthPanel(int width, int height, int monthDelta){
        setPreferredSize(new Dimension(width, height));

        //Begin creating components
        columnPane = new JPanel();
        this.add(columnPane);
        columnPane.setLayout(gridColumnLabels);
        columnPane.setPreferredSize(new Dimension(width,COLUMN_PANE_HEIGHT));
        columnPane.setOpaque(false);
        //columnPane.addComponentListener(new textSizeListener());

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
        setMonth(monthDelta);

        //Start by getting current system date
        updateDateFields();

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
        updateDayOrdinals();
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
    public void setMonth(int monthDelta) {
        //Same month and year was passed as current Calendar object
        if (monthDelta == 0)
            return;
        monthCalendar.add(Calendar.MONTH, monthDelta);
        updateDateFields();
        updateDayOrdinals();
    }

    //Call this when the month displayed would be changed
    private void updateDateFields() {
        currentWeekday = monthCalendar.get(Calendar.DAY_OF_WEEK);
        currentDate = monthCalendar.get(Calendar.DATE);
        currentMonth = monthCalendar.get(Calendar.MONTH);
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

        //Want these headers to be 50 px at all times
        //int minWidth = 0;
        //int minHeight = COLUMN_PANE_HEIGHT;
        columnLabels = new JLabel[DAYS_IN_WEEK];
        for (int i = 0; i < DAYS_IN_WEEK; i++,overflowPixelsWidth--) {
            columnLabels[i] = new JLabel("", SwingConstants.CENTER);
            //columnLabels[i].setMinimumSize(new Dimension(minWidth, minHeight));
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
        int overflowPixelsWidth = width % NUM_COLUMNS;

        int paneHeight = (height - COLUMN_PANE_HEIGHT) / NUM_ROWS;
        int overflowPixelsHeight = (height - COLUMN_PANE_HEIGHT) % NUM_ROWS;

        Random r = new Random();

        dayOrdinalLabels = new JLabel[NUM_DAYS_DISPLAYED];
        dayPanes = new JPanel[NUM_DAYS_DISPLAYED];
        for (int i = 0; i < NUM_DAYS_DISPLAYED; i++) {
            dayPanes[i] = new JPanel();
            dayPanes[i].setPreferredSize(new Dimension(paneWidth,paneHeight));
            dayOrdinalLabels[i] = new JLabel("",SwingConstants.CENTER);
            //dayOrdinalLabels[i].setPreferredSize(new Dimension(paneWidth,paneHeight));
            //dayOrdinalLabels[i].setBorder(BorderFactory.
            //        createMatteBorder(2,2,2,2,new Color
            //                (r.nextInt(256),r.nextInt(256),r.nextInt(256))));
            dayPanes[i].add(dayOrdinalLabels[i]);
            dayPanes[i].setOpaque(false);
            dayPane.add(dayPanes[i]);
        }
    }

    //Update the text of the Ordinal Day labels
    private void updateDayOrdinals() {
        int labelsIndex;

        for (labelsIndex = 0; labelsIndex <= numDaysPreviousMonthDisplayed; labelsIndex++) {
            dayOrdinalLabels[labelsIndex].setText(String.valueOf(numDaysInPreviousMonth - numDaysPreviousMonthDisplayed + 1 + labelsIndex));
            dayOrdinalLabels[labelsIndex].setFont(fontOrdinals);
        }

        for (; labelsIndex < (numDaysCurrentMonth + numDaysPreviousMonthDisplayed); labelsIndex++) {
            dayOrdinalLabels[labelsIndex].setText(String.valueOf(1 + labelsIndex - numDaysPreviousMonthDisplayed));
            dayOrdinalLabels[labelsIndex].setFont(fontOrdinals);
        }

        for (int i = 1; labelsIndex < NUM_DAYS_DISPLAYED; labelsIndex++,i++) {
            dayOrdinalLabels[labelsIndex].setText(String.valueOf(i));
            dayOrdinalLabels[labelsIndex].setFont(fontOrdinals);
        }
    }

}