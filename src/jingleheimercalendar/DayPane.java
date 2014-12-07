package jingleheimercalendar;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Created by Nathan on 12/7/2014.
 */
class DayPane extends JPanel {

    public static final int SWITCH_PREVIOUS_MONTH = -1;
    public static final int SWITCH_CURRENT_MONTH = 0;
    public static final int SWITCH_NEXT_MONTH = 1;
    private int monthContext;
    private MonthPanel parentPanel;
    private boolean isCurrentDay;
    private Color currentColor;

    DayPane(MonthPanel parentPanel) {
        super();
        this.parentPanel = parentPanel;
        currentColor = MonthPanel.DEFAULT_PANEL_BACKGROUND;
        //Default date context to current values
    }

    void setMonthContext(int monthContext) {
        this.monthContext = monthContext;
    }

    int getMonthContext() {
        return monthContext;
    }

    protected Color getCurrentColor() {
        return currentColor;
    }

    protected void setCurrentColor(Color color) {
        currentColor = color;
        //setBackground(color);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        g.setColor(MonthPanel.DEFAULT_PANEL_BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(currentColor);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
    }

    void setIsCurrentDay(boolean isCurrentDay) {
        this.isCurrentDay = isCurrentDay;
    }

    public boolean getIsCurrentDay() {
        return isCurrentDay;
    }
}