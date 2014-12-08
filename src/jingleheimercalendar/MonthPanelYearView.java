package jingleheimercalendar;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Nathan on 12/7/2014.
 */
public class MonthPanelYearView extends MonthPanel {
    private DayPane lastClicked;

    public MonthPanelYearView(int width, int height, int monthDelta){
        super(width, height, monthDelta);

        updateDayPanelClickListener(new YearViewDayPanelClickedListener());
        updateDayColumnHeaders(MonthPanel.DAY_LABELS_YEARVIEW_CONTEXT);
        setColumnPaneSize(width, 20);
    }

    private class YearViewDayPanelClickedListener implements MouseListener {
        //Fired upon successful press + release;
        //Called after mouseReleased
        @Override
        public void mouseClicked(MouseEvent e) {
            DayPane parent = (DayPane) e.getComponent();
            //First recolor
            if (lastClicked !=  null && parent != lastClicked) {
                parent.setCurrentColor(MonthPanel.BLUE_SELECTED_MEDIUM);
                if (lastClicked.isCurrentDay())
                    lastClicked.setCurrentColor(MonthPanel.GRAY_CURRENT_DAY_BACKGROUND);
                else
                    lastClicked.setCurrentColor(MonthPanel.DEFAULT_PANEL_BACKGROUND);
            }
            lastClicked = parent;
            //Check to see if an action needs to be performed
            if (e.getClickCount() == 2) {
                JingleheimerCalendar.displayView(JingleheimerCalendar.INDEX_DAY_VIEW);
                //TODO: Implement logic for changing context to day clicked here
            } else {
                int context = parent.getMonthContext();
                if (context == DayPane.SWITCH_NEXT_MONTH) {
                    changeMonthBy(1);
                    MonthView.monthHeader.update();
                } else if (context == DayPane.SWITCH_PREVIOUS_MONTH) {
                    changeMonthBy(-1);
                    MonthView.monthHeader.update();
                }
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
            if (lastClicked != null && parent == lastClicked ) {
                parent.setCurrentColor(MonthPanel.BLUE_SELECTED_MEDIUM);
            } else if (parent.isCurrentDay()) {
                parent.setCurrentColor(MonthPanel.GRAY_CURRENT_DAY_BACKGROUND);
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
