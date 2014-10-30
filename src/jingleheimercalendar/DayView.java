package jingleheimercalendar;

import java.awt.Dimension;
import java.text.DateFormatSymbols;
import java.util.Calendar;

/**
 * Created by Nathan on 10/28/2014.
 */
public class DayView extends ViewPanel {
    private static DayPanel dayPanel;
    private static Calendar currentCalendar;

    DayView(int width, int height) {
        currentCalendar = Calendar.getInstance();
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(JingleheimerCalendar.MINIMUM_WIDTH, JingleheimerCalendar.MINIMUM_VIEW_HEIGHT));
        dayPanel = new DayPanel();
        add(dayPanel);
        this.value = "dayView";
    }

    public static void goToCurrentDay() {
        dayPanel.resetEventPanel();
        dayPanel.resetTaskPanel();
        dayPanel.dateText.setText(String.valueOf(currentCalendar.get(Calendar.DATE)));
        dayPanel.monthText.setText(new DateFormatSymbols().getMonths()[currentCalendar.get(Calendar.MONTH)]);
        dayPanel.weekdayText.setText(new DateFormatSymbols().getWeekdays()[currentCalendar.get(Calendar.DAY_OF_WEEK)]);
    }
}
