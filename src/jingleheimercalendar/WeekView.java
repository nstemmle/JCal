package jingleheimercalendar;

import java.awt.Container;
import java.awt.Dimension;

/**
 * Created by Nathan on 10/28/2014.
 */
public class WeekView extends ViewPanel {
    private static WeekPanel weekPanel;

    WeekView(int width, int height, Container contentPane) {
        this.value = JingleheimerCalendar.VIEW_WEEK;
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(JingleheimerCalendar.MINIMUM_WIDTH, JingleheimerCalendar.MINIMUM_VIEW_HEIGHT));

        weekPanel = new WeekPanel(this);
        add(weekPanel);
    }
    
    public void refresh(){
        weekPanel.refresh();
    }
}
