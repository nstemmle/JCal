package jingleheimercalendar;

import javax.swing.*;
import java.util.Calendar;

class MonthPanel extends JPanel {
    public static final int NUM_DAYS_DISPLAYED = 35;
    public static final int DAYS_IN_WEEK = 7;

    //TODO
    public static enum Label {
        SUNDAY ("SUN",1),
        MONDAY ("MON",2),
        TUESDAY ("TUE",3),
        WEDNESDAY ("WED",4),
        THURSDAY ("THU",5),
        FRIDAY ("FRI",6),
        SATURDAY ("SAT",7);

        private final String label;
        private final int asd;
        Label(String label, int asd) {
            this.label = label;
            this.asd=asd;
        }

        private String label() { return label; }
    }

    //Date values from the current system date
    //This will be 1 (Calendar.SUNDAY) in America but would be 2 (Calendar.MONDAY) in France
    //Currently not really used
    static int firstDayOfWeek;

    private int numDaysPreviousMonthDisplayed;
    private int numDaysNextMonthDisplayed;

    private int currentWeekday;
    private int currentDate;

    //This value is 0 based; i.e. Calendar.JANUARY = 0 & Calendar.DECEMBER = 11
    private int currentMonth;
    private int currentYear;
    private int numDaysCurrentMonth;
    private int numDaysInPreviousMonth;

    private int width;
    private int height;


    private int weekdayOfFirstDayInMonth;

    //Use for context of user actions - the current label highlighted/selected
    private int selectedDay;
    private int selectedWeek;
    private int selectedMonth;
    private int selectedYear;

    private Calendar monthCalendar;
    private JLabel[] columnLabels;
    private JLabel[] monthLabels;

    MonthPanel(int width, int height) {
        this.width=width;
        this.height=height;
        monthCalendar = Calendar.getInstance();
        firstDayOfWeek = monthCalendar.getFirstDayOfWeek();

        //Start with current system date
        updateDateFields();

        //Initialize JLabels with correct Text Label
        monthLabels = new JLabel[NUM_DAYS_DISPLAYED];
        updateTextLabels(monthCalendar.get(Calendar.MONTH),monthCalendar.get(Calendar.YEAR));

        //Position JLabels appropriately
    }

    public void updateDateFields() {
        currentWeekday = monthCalendar.get(Calendar.DAY_OF_WEEK);
        currentDate = monthCalendar.get(Calendar.DATE);
        currentMonth = monthCalendar.get(Calendar.MONTH);
        currentYear = monthCalendar.get(Calendar.YEAR);
        numDaysCurrentMonth = monthCalendar.getActualMaximum(Calendar.DATE);

        numDaysPreviousMonthDisplayed = DAYS_IN_WEEK - (currentDate % DAYS_IN_WEEK);
        numDaysNextMonthDisplayed = NUM_DAYS_DISPLAYED - numDaysCurrentMonth - numDaysPreviousMonthDisplayed;

        weekdayOfFirstDayInMonth = numDaysPreviousMonthDisplayed + 1;

        Calendar previousMonthCalendar = (Calendar) monthCalendar.clone();

        //Obtain numDays in previous month
        if (numDaysPreviousMonthDisplayed > 0) {
            previousMonthCalendar.add(Calendar.MONTH, -1);
            numDaysInPreviousMonth = previousMonthCalendar.getActualMaximum(Calendar.DATE);
        }

    }

    public void updateTextLabels(int month, int year) {
        int monthDelta = (month - currentMonth) + (12 * (year - currentYear));
        if (monthDelta != 0) {
            monthCalendar.add(Calendar.MONTH, monthDelta);
            updateDateFields();
        }

        int labelsIndex;

        for (labelsIndex = 0; labelsIndex < numDaysPreviousMonthDisplayed; labelsIndex++) {
            monthLabels[labelsIndex] = new JLabel(String.valueOf(numDaysInPreviousMonth - numDaysPreviousMonthDisplayed + 1 + labelsIndex));
        }

        for (; labelsIndex < (numDaysCurrentMonth + numDaysPreviousMonthDisplayed); labelsIndex++) {
            monthLabels[labelsIndex] = new JLabel(String.valueOf(1 + labelsIndex - numDaysPreviousMonthDisplayed));
        }

        for (; labelsIndex < NUM_DAYS_DISPLAYED; labelsIndex++) {
            monthLabels[labelsIndex] = new JLabel(String.valueOf(NUM_DAYS_DISPLAYED - labelsIndex));
        }
    }

    //TODO
    public void updateLabelPositions() {

    }

    //TODO
    public void initializeColumnLabels() {
        columnLabels = new JLabel[DAYS_IN_WEEK];
        for (int i = 0; i < DAYS_IN_WEEK; i++) {
            //columnLabels[i] = new JLabel()
        }
    }

    //TODO
    public void calculateLabelDimensions() {

    }

    //TODO
    public void calculatePadding() {

    }
}
