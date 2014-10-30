package jingleheimercalendar;

import java.awt.Container;
import java.awt.Dimension;

/**
 * Created by Roach on 10/28/2014.
 */
public class WeekView extends ViewPanel {
    private static WeekPanel weekPanel;

    WeekView(int width, int height, Container contentPane) {
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(JingleheimerCalendar.MINIMUM_WIDTH, JingleheimerCalendar.MINIMUM_VIEW_HEIGHT));
        this.value = "weekView";
        weekPanel = new WeekPanel(this);
        add(weekPanel);
    }
}
