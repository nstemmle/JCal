package jingleheimercalendar;

import java.awt.Dimension;
import java.util.Calendar;
/**
 * Created by Nathan on 10/28/2014.
 */
public class DayView extends ViewPanel {
    private static DayPanel dayPanel;

    DayView(int width, int height) {
        value = JingleheimerCalendar.VIEW_DAY;
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(JingleheimerCalendar.MINIMUM_WIDTH, JingleheimerCalendar.MINIMUM_VIEW_HEIGHT));

        dayPanel = new DayPanel(width, height);
        add(dayPanel);
    }

    public static void changeDay(int day, int month, int year) {
        dayPanel.changeDay(day, month, year);
    }

    public static void goToCurrentDay() {
        Calendar c = Calendar.getInstance();
        System.out.println("c.date: " + c.get(Calendar.DATE));
        System.out.println("c.month: " + c.get(Calendar.MONTH));
        System.out.println("c.year: " + c.get(Calendar.YEAR));
        dayPanel.changeDay(c.get(Calendar.DATE), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
    }
    
    public void refresh(){
        dayPanel.refresh();
    }
}
