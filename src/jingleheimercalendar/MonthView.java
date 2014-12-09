package jingleheimercalendar;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Nathan on 10/28/2014.
 */
public class MonthView extends ViewPanel {
    //static MonthPanel monthPanel;
    static MPanelMonthView monthPanel;
    static MonthHeader monthHeader;

    MonthView(int width, int height) {
        this.value = JingleheimerCalendar.VIEW_MONTH;
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(JingleheimerCalendar.MINIMUM_WIDTH, JingleheimerCalendar.MINIMUM_VIEW_HEIGHT));

        monthPanel = new MPanelMonthView(width, height - MonthHeader.HEADER_MINIMUM_HEIGHT, 0);
        monthHeader = new MonthHeader(width, MonthHeader.HEADER_MINIMUM_HEIGHT);

        SpringLayout springPanelAndHeader = new SpringLayout();
        this.setLayout(springPanelAndHeader);

        add(monthHeader);
        add(monthPanel);

        int gridPaddingVertical = 0;
        int gridPaddingHorizontal = 0;

        springPanelAndHeader.putConstraint(SpringLayout.NORTH, monthHeader, gridPaddingVertical, SpringLayout.NORTH, this);
        springPanelAndHeader.putConstraint(SpringLayout.WEST, monthHeader, gridPaddingHorizontal, SpringLayout.WEST, this);
        springPanelAndHeader.putConstraint(SpringLayout.EAST, monthHeader, gridPaddingHorizontal, SpringLayout.EAST, this);

        springPanelAndHeader.putConstraint(SpringLayout.NORTH, monthPanel, gridPaddingVertical, SpringLayout.SOUTH, monthHeader);
        springPanelAndHeader.putConstraint(SpringLayout.WEST, monthPanel, gridPaddingHorizontal, SpringLayout.WEST, this);
        springPanelAndHeader.putConstraint(SpringLayout.EAST, monthPanel, gridPaddingHorizontal, SpringLayout.EAST, this);
        springPanelAndHeader.putConstraint(SpringLayout.SOUTH, monthPanel, gridPaddingVertical, SpringLayout.SOUTH, this);
    }

    protected static void setMonth(int month, int year) {
        monthPanel.changeMonthBy((month - monthPanel.getCurrentMonth()) + ((year - monthPanel.getCurrentYear()) * 12));
        monthHeader.updateMonthLabel();
    }
}
