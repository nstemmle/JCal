package jingleheimercalendar;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Nathan on 12/7/2014.
 */
public class MonthPanelMonthView extends MonthPanel {
    private DayPane lastClicked;

    public MonthPanelMonthView(int width, int height, int monthDelta){
        super(width, height, monthDelta);

        updateDayPanelClickListener(new MonthViewDayPanelClickedListener());
    }

    private class MonthViewDayPanelClickedListener implements MouseListener {
        //Fired upon successful press + release;
        //Called after mouseReleased
        @Override
        public void mouseClicked(MouseEvent e) {
            DayPane parent = (DayPane) e.getComponent();
            //First recolor
            if (lastClicked !=  null && parent != lastClicked) {
                parent.setCurrentColor(BLUE_SELECTED_MEDIUM);
                if (lastClicked.isCurrentDay())
                    lastClicked.setCurrentColor(GRAY_CURRENT_DAY_BACKGROUND);
                else
                    lastClicked.setCurrentColor(DEFAULT_PANEL_BACKGROUND);
            }
            lastClicked = parent;
            int context = parent.getMonthContext();
            //Panel in non-current month selected
            int date = parent.getDay();
            int month = getCurrentMonth();
            int year = getCurrentYear();
            if (context != DayPane.SWITCH_CURRENT_MONTH) {
                if (lastClicked.isCurrentDay())
                    lastClicked.setCurrentColor(GRAY_CURRENT_DAY_BACKGROUND);
                else
                    lastClicked.setCurrentColor(DEFAULT_PANEL_BACKGROUND);
                changeMonthBy(context);
                MonthView.monthHeader.update();
                lastClicked = getDayPane(date);
                lastClicked.setCurrentColor(BLUE_SELECTED_MEDIUM);
            }
            if (e.getClickCount() >= 2) {
                JingleheimerCalendar.displayView(JingleheimerCalendar.INDEX_DAY_VIEW);
                JingleheimerCalendar.changeDayViewDay(date, month, year);
            }
            JingleheimerCalendar.repaintDisplayedCategoryWindow();
        }

        //Fired upon mouse cursor entering bounds of component
        @Override
        public void mouseEntered(MouseEvent e) {
            DayPane parent = (DayPane) e.getComponent();
            parent.setCurrentColor(MonthPanel.BLUE_SELECTED_A25);
            JingleheimerCalendar.repaintDisplayedCategoryWindow();
        }

        //Fired upon mouse cursor exiting bounds of component
        @Override
        public void mouseExited(MouseEvent e) {
            DayPane parent = (DayPane) e.getComponent();
            if (lastClicked != null && parent == lastClicked ) {
                parent.setCurrentColor(MonthPanel.BLUE_SELECTED_MEDIUM);
            } else if (parent.isCurrentDay()) {
                parent.setCurrentColor(MonthPanel.GRAY_CURRENT_DAY_BACKGROUND);
            } else {
                parent.setCurrentColor(MonthPanel.DEFAULT_PANEL_BACKGROUND);
            }
            JingleheimerCalendar.repaintDisplayedCategoryWindow();
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
