package jingleheimercalendar;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Nathan on 12/7/2014.
 */
public class MonthPanelYearView extends MonthPanel {

    public MonthPanelYearView(int width, int height, int monthDelta){
        super(width, height, monthDelta);

        updateDayPanelClickListener(new YearViewDayPanelClickedListener());
        updateDayColumnHeaders(MonthPanel.DAY_LABELS_YEARVIEW_CONTEXT);
        setColumnPaneSize(width, 20);

        updateDayOrdinals();
    }

    protected void updateMonthLabel() {
    }

    //TODO: Debug + fix labels being incorrect
    @Override
    protected void updateDayOrdinals() {
        super.updateDayOrdinals();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        int day = getNumDaysInPreviousMonth() - getNumDaysPreviousMonthDisplayed();
        int m = getCurrentMonth();
        int year = getCurrentYear();
        ArrayList<Event> allDayEvents = null;

        int labelsIndex = 0;
        for (; labelsIndex < getNumDaysPreviousMonthDisplayed(); labelsIndex++,day++) {
            String date = String.valueOf(getCurrentMonth() >= 10 ? m : "0" + m) + "/" +
                    String.valueOf(day >= 10 ? day : "0" + day) + "/" + String.valueOf(year);
            try {
                allDayEvents = UserCalendar.getInstance().getAllDayEventsByDate(df.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (allDayEvents != null && allDayEvents.size() > 0)
                getDayPaneAtIndex(labelsIndex).setLabelColor(allDayEvents.get(0).getCategoryColor());
        }

        day = 1;
        for (; labelsIndex < getNumDaysCurrentMonth() + getNumDaysPreviousMonthDisplayed(); labelsIndex++, day++) {
            String date = String.valueOf(getCurrentMonth() >= 10 ? m : "0" + m) + "/" +
                    String.valueOf(day >= 10 ? day : "0" + day) + "/" + String.valueOf(year);
            try {
                allDayEvents = UserCalendar.getInstance().getAllDayEventsByDate(df.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (allDayEvents != null && allDayEvents.size() > 0)
                getDayPaneAtIndex(labelsIndex).setLabelColor(allDayEvents.get(0).getCategoryColor());
        }

        day = 1;
        for (int i = 1; labelsIndex < NUM_DAYS_DISPLAYED; labelsIndex++, i++) {
            String date = String.valueOf(getCurrentMonth() >= 10 ? m : "0" + m) + "/" +
                    String.valueOf(day >= 10 ? day : "0" + day) + "/" + String.valueOf(year);
            try {
                allDayEvents = UserCalendar.getInstance().getAllDayEventsByDate(df.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (allDayEvents != null && allDayEvents.size() > 0)
                getDayPaneAtIndex(labelsIndex).setLabelColor(allDayEvents.get(0).getCategoryColor());
        }

    }

    private class YearViewDayPanelClickedListener implements MouseListener {
        //Fired upon successful press + release;
        //Called after mouseReleased
        @Override
        public void mouseClicked(MouseEvent e) {
            DayPane parent = (DayPane) e.getComponent();
            //First recolor
            DayPane lastClicked = YearView.getLastClicked();
            if (lastClicked !=  null && parent != lastClicked) {
                parent.setCurrentColor(MonthPanel.BLUE_SELECTED_MEDIUM);
                if (lastClicked.isCurrentDay())
                    lastClicked.setCurrentColor(MonthPanel.GRAY_CURRENT_DAY_BACKGROUND);
                else
                    lastClicked.setCurrentColor(MonthPanel.DEFAULT_PANEL_BACKGROUND);
            }
            YearView.setLastClicked(parent);
            //Check to see if an action needs to be performed
            if (e.getClickCount() == 2) {
                JingleheimerCalendar.displayView(JingleheimerCalendar.INDEX_DAY_VIEW);
                JingleheimerCalendar.changeDayViewDay(parent.getDay(), getCurrentMonth(), getCurrentYear());
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
            if (YearView.getLastClicked() != null && parent == YearView.getLastClicked()) {
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
