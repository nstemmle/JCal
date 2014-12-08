package jingleheimercalendar;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Calendar;

/**
 * Created by Nathan on 10/29/2014.
 */

class YearPanel extends JPanel {
    public static int NUM_MONTHS = 12;
    private static MonthPanelYearView[] monthPanels;
    private GridLayout gridLayout;
    private int currentMonth;
    private static int currentYear;

    YearPanel(int width, int height) {
        setBackground(Color.WHITE);
        Calendar c = Calendar.getInstance();
        currentMonth = c.get(Calendar.MONTH);
        monthPanels = new MonthPanelYearView[NUM_MONTHS];
        for (int i = 0; i < NUM_MONTHS; i++) {
            monthPanels[i] = new MonthPanelYearView((width / 4) - 10, (height / 3) - 10, -currentMonth + i);
            monthPanels[i].setFontSizeHeaders(16);
            monthPanels[i].setFontSizeOrdinals(16);
            add(monthPanels[i]);
        }
        gridLayout = new GridLayout(3,4,10,10);
        this.setLayout(gridLayout);
        currentYear = monthPanels[0].getCurrentYear();
    }

    private static void updateMonthPanels(int monthDelta) {
        if (monthDelta != 0) {
            for (MonthPanel mPanel : monthPanels) {
                mPanel.changeMonthBy(monthDelta);
                System.out.println(mPanel.getCurrentYear());
            }
        }

    }

    static void changeYearBy(int yearDelta) {
        if (yearDelta != 0) {
            updateMonthPanels(yearDelta*12);
        }
        currentYear = monthPanels[0].getCurrentYear();
    }

    public static int getCurrentYear() {
        return currentYear;
    }
}
