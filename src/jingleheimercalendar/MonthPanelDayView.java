package jingleheimercalendar;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Nathan on 12/7/2014.
 */
public class MonthPanelDayView extends MonthPanel {
    private DayPane lastClicked;
    private DayPane lastHovered;
    private DayPane currentDay;

    public MonthPanelDayView(int width, int height, int monthDelta) {
        super(width, height, monthDelta);
        updateDayPanelClickListener(new DayViewDayPanelClickedListener());
    }

    private class DayViewDayPanelClickedListener implements MouseListener {
        //Fired upon successful press + release;
        //Called after mouseReleased
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                //TODO: Implement logic for changing context to current day here
            } else {
                DayPane parent = (DayPane) e.getComponent();
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

        //Fired upon mouse press
        @Override
        public void mousePressed(MouseEvent e) {
            DayPane parent = (DayPane) e.getComponent();
            if (lastClicked != null) {
                //lastClicked.setBorder(null);
                lastClicked.setCurrentColor(MonthPanel.DEFAULT_PANEL_BACKGROUND);
            }
            //parent.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, MonthPanel.BLUE_SELECTED, MonthPanel.BLUE_SELECTED_DARK));
            parent.setCurrentColor(MonthPanel.BLUE_SELECTED_MEDIUM);
            lastClicked = parent;
        }

        //Fired upon mouse depress
        @Override
        public void mouseReleased(MouseEvent e) {

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
            if (parent != lastClicked)
                parent.setCurrentColor(MonthPanel.DEFAULT_PANEL_BACKGROUND);
            else
                parent.setCurrentColor(MonthPanel.BLUE_SELECTED_MEDIUM);
        }
    }
}
