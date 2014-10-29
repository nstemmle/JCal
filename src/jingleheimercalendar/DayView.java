package jingleheimercalendar;

import java.awt.Dimension;

/**
 * Created by Roach on 10/28/2014.
 */
public class DayView extends ViewPanel {
    private DayPanel dayPanel;

    DayView(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(JingleheimerCalendar.MINIMUM_WIDTH, JingleheimerCalendar.MINIMUM_VIEW_HEIGHT));
    }
}
