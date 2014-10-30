package jingleheimercalendar;

import javax.swing.SpringLayout;
import java.awt.Dimension;

/**
 * Created by Nathan on 10/28/2014.
 */
public class MonthView extends ViewPanel {
    static MonthPanel monthPanel;
    static MonthHeader monthHeader;
    private SpringLayout springPanelAndHeader;

    private int gridPaddingHorizontal = 0;
    private int gridPaddingVertical = 0;


    //
    MonthView(int width, int height) {
        this.value = "monthView";
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(JingleheimerCalendar.MINIMUM_WIDTH, JingleheimerCalendar.MINIMUM_VIEW_HEIGHT));

        monthPanel = new MonthPanel(width, height - MonthHeader.HEADER_MINIMUM_HEIGHT, 0, MonthPanel.CONTEXT_MONTH);
        monthHeader = new MonthHeader(width, MonthHeader.HEADER_MINIMUM_HEIGHT);
        //monthHeader.setBackground(Color.BLACK);

        springPanelAndHeader = new SpringLayout();
        this.setLayout(springPanelAndHeader);

        add(monthHeader);
        add(monthPanel);

        springPanelAndHeader.putConstraint(SpringLayout.NORTH, monthHeader, gridPaddingVertical, SpringLayout.NORTH, this);
        springPanelAndHeader.putConstraint(SpringLayout.WEST, monthHeader, gridPaddingHorizontal, SpringLayout.WEST, this);
        springPanelAndHeader.putConstraint(SpringLayout.EAST, monthHeader, gridPaddingHorizontal, SpringLayout.EAST, this);

        springPanelAndHeader.putConstraint(SpringLayout.NORTH, monthPanel, gridPaddingVertical, SpringLayout.SOUTH, monthHeader);
        springPanelAndHeader.putConstraint(SpringLayout.WEST, monthPanel, gridPaddingHorizontal, SpringLayout.WEST, this);
        springPanelAndHeader.putConstraint(SpringLayout.EAST, monthPanel, gridPaddingHorizontal, SpringLayout.EAST, this);
        springPanelAndHeader.putConstraint(SpringLayout.SOUTH, monthPanel, gridPaddingVertical, SpringLayout.SOUTH, this);
    }
}
