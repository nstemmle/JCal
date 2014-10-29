package jingleheimercalendar;

import java.awt.Dimension;

/**
 * Created by Nathan on 10/28/2014.
 */
public class YearView extends ViewPanel {
    private YearPanel yearPanel;

    YearView(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(JingleheimerCalendar.MINIMUM_WIDTH, JingleheimerCalendar.MINIMUM_VIEW_HEIGHT));
    }
}
