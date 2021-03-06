package jingleheimercalendar;

import javax.swing.SpringLayout;
import java.awt.Color;
import java.awt.Dimension;

/**
 * Created by Nathan on 10/28/2014.
 */
public class YearView extends ViewPanel {
    private YearPanel yearPanel;
    private YearHeader yearHeader;
    private static DayPane lastClicked;

    YearView(int width, int height) {
        this.value = JingleheimerCalendar.VIEW_YEAR;
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(JingleheimerCalendar.MINIMUM_WIDTH, JingleheimerCalendar.MINIMUM_VIEW_HEIGHT));
        setBackground(Color.WHITE);
        SpringLayout springPanelAndHeader = new SpringLayout();

        yearPanel = new YearPanel(width, height - YearHeader.MINIMUM_HEIGHT - 10);
        yearHeader = new YearHeader(width);

        add(yearHeader);
        add(yearPanel);

        int gridPaddingVertical = 0;
        springPanelAndHeader.putConstraint(SpringLayout.NORTH, yearHeader, gridPaddingVertical, SpringLayout.NORTH, this);
        int gridPaddingHorizontal = 0;
        springPanelAndHeader.putConstraint(SpringLayout.WEST, yearHeader, gridPaddingHorizontal, SpringLayout.WEST, this);
        springPanelAndHeader.putConstraint(SpringLayout.EAST, yearHeader, gridPaddingHorizontal, SpringLayout.EAST, this);

        springPanelAndHeader.putConstraint(SpringLayout.NORTH, yearPanel, gridPaddingVertical, SpringLayout.SOUTH, yearHeader);
        springPanelAndHeader.putConstraint(SpringLayout.WEST, yearPanel, gridPaddingHorizontal, SpringLayout.WEST, this);
        springPanelAndHeader.putConstraint(SpringLayout.EAST, yearPanel, gridPaddingHorizontal, SpringLayout.EAST, this);
        springPanelAndHeader.putConstraint(SpringLayout.SOUTH, yearPanel, 0, SpringLayout.SOUTH, this);

    }

    @Override
    public void refresh() {
        yearPanel.refresh1();
    }

    protected static  DayPane getLastClicked() {
        return lastClicked;
    }

    protected static void setLastClicked(DayPane clicked) {
        lastClicked = clicked;
    }
}
