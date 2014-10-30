package jingleheimercalendar;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Calendar;

class YearPanel extends JPanel {
    public static int NUM_MONTHS = 12;
    private MonthPanel[] monthPanels;
    private GridLayout gridLayout;
    private int currentMonth;

    YearPanel(int width, int height) {
        setBackground(Color.WHITE);
        Calendar c = Calendar.getInstance();
        currentMonth = c.get(Calendar.MONTH);
        monthPanels = new MonthPanel[NUM_MONTHS];
        for (int i = 0; i < NUM_MONTHS; i++) {
            monthPanels[i] = new MonthPanel((width / 4) - 10, (height / 3) - 10, -currentMonth + i, MonthPanel.CONTEXT_YEAR);
            monthPanels[i].setFontSizeHeaders(16);
            monthPanels[i].setFontSizeOrdinals(16);
            add(monthPanels[i]);
        }
        gridLayout = new GridLayout(3,4,10,10);
        this.setLayout(gridLayout);
    }
}
