package jingleheimercalendar;

import java.awt.Font;
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

    @Override
    protected void updateDayOrdinals() {
        super.updateDayOrdinals();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        int m = getCurrentMonth();
        ArrayList<Event> allDayEvents = null;
        Font bold = JingleheimerCalendar.defaultFont.deriveFont(Font.BOLD, 22f);
        for (int i = 0; i < MonthPanel.NUM_DAYS_DISPLAYED; i ++) {
            int year = getCurrentYear();
            DayPane current = getDayPaneAtIndex(i);
            int month = m + 1 + current.getMonthContext();
            if (month == 0) {
                month = 12;
                year--;
            } else if (month == 13){
                month = 1;
                year++;
            }
            int day = current.getDay();
            String date = String.valueOf(month >= 10 ? month : "0" + month);
            date = date.concat("/").concat(String.valueOf(day >= 10 ? day : "0" + day )).concat("/");
            date = date.concat(String.valueOf(year));
            try {
                allDayEvents = UserCalendar.getInstance().getAllDayEventsByDate(df.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (allDayEvents != null && allDayEvents.size() > 0) {
                current.setLabelColor(allDayEvents.get(0).getCategoryColor());
                current.setLabelFont(bold);
            }

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
