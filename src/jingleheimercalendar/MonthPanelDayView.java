package jingleheimercalendar;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;

/**
 * Created by Nathan on 12/7/2014.
 */
public class MonthPanelDayView extends MonthPanel {
    private DayPane lastClicked;
    private DayPane currentlyHighlighted;

    public MonthPanelDayView(int width, int height, int monthDelta) {
        super(width, height, monthDelta);
        updateDayPanelClickListener(new DayViewDayPanelClickedListener());
        updateDayColumnHeaders(MonthPanel.DAY_LABELS_DAYVIEW_CONTEXT);
        currentlyHighlighted = getCurrentDayPane();
        currentlyHighlighted.setCurrentColor(MonthPanel.GRAY_CURRENT_DAY_BACKGROUND);
    }

    public void changeHighlightedDay(Calendar c) {
        if (currentlyHighlighted != null)
            if (currentlyHighlighted.isCurrentDay()) {
                currentlyHighlighted.setCurrentColor(MonthPanel.GRAY_CURRENT_DAY_SUBTLE);
            } else if (currentlyHighlighted == lastClicked) {
                currentlyHighlighted.setCurrentColor(MonthPanel.BLUE_SELECTED_MEDIUM);
            }
            else {
                currentlyHighlighted.setCurrentColor(MonthPanel.DEFAULT_PANEL_BACKGROUND);
            }
        currentlyHighlighted = getDayPane(c.get(Calendar.DATE));
        currentlyHighlighted.setCurrentColor(MonthPanel.GRAY_CURRENT_DAY_BACKGROUND);
    }

    private class DayViewDayPanelClickedListener implements MouseListener {
        //Fired upon successful press + release;
        //Called after mouseReleased
        @Override
        public void mouseClicked(MouseEvent e) {
            DayPane parent = (DayPane) e.getComponent();
            //First recolor
            if (lastClicked !=  null && parent != lastClicked) {
                parent.setCurrentColor(MonthPanel.BLUE_SELECTED_MEDIUM);
                if (lastClicked.isCurrentDay())
                    lastClicked.setCurrentColor(MonthPanel.GRAY_CURRENT_DAY_SUBTLE);
                else
                    lastClicked.setCurrentColor(MonthPanel.DEFAULT_PANEL_BACKGROUND);
            }
            lastClicked = parent;
            if (currentlyHighlighted != null && parent != currentlyHighlighted) {
                currentlyHighlighted.setCurrentColor(MonthPanel.GRAY_CURRENT_DAY_BACKGROUND);
            }
            //Check to see if an action needs to be performed
            if (e.getClickCount() >= 2) {
                JingleheimerCalendar.changeDayViewDay(parent.getDay(), (getCurrentMonth() + parent.getMonthContext()), getCurrentYear());
            }
        }

        //Fired upon mouse cursor entering bounds of component
        @Override
        public void mouseEntered(MouseEvent e) {
            DayPane parent = (DayPane) e.getComponent();
            parent.setCurrentColor(MonthPanel.BLUE_SELECTED_A25);
        }

        //Fired upon mouse cursor exiting bounds of component
        @Override
        public void mouseExited(MouseEvent e) {
            DayPane parent = (DayPane) e.getComponent();
            if (lastClicked != null && parent == lastClicked) {
                parent.setCurrentColor(MonthPanel.BLUE_SELECTED_MEDIUM);
            }else if (currentlyHighlighted != null && parent == currentlyHighlighted) {
                currentlyHighlighted.setCurrentColor(MonthPanel.GRAY_CURRENT_DAY_BACKGROUND);
            } else if (parent.isCurrentDay()) {
                parent.setCurrentColor(MonthPanel.GRAY_CURRENT_DAY_SUBTLE);
            } else {
                parent.setCurrentColor(MonthPanel.DEFAULT_PANEL_BACKGROUND);
            }
        }

        //Fired upon mouse press
        @Override
        public void mousePressed(MouseEvent e) {
        }

        //Fired upon mouse depress
        @Override
        public void mouseReleased(MouseEvent e) {

        }
    }
}
