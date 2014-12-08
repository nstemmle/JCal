package jingleheimercalendar;

import java.awt.Dimension;

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

    public static void goToCurrentDay() {
        dayPanel.resetEventPanel();
        dayPanel.resetTaskPanel();
    }
    
    public void refresh(){
        dayPanel.refresh();
    }
}
