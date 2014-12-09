package jingleheimercalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Created by Nathan on 12/7/2014.
 */
class DayPane extends JPanel {
    public static final int SWITCH_PREVIOUS_MONTH = -1;
    public static final int SWITCH_CURRENT_MONTH = 0;
    public static final int SWITCH_NEXT_MONTH = 1;
    private int monthContext;
    private boolean isCurrentDay;
    private Color currentColor;
    private JLabel ordinalLabel;

    DayPane(int width, int height, JLabel ordinalLabel) {
        super();
        this.ordinalLabel = ordinalLabel;
        setBackground(MonthPanel.DEFAULT_PANEL_BACKGROUND);
        currentColor = MonthPanel.DEFAULT_PANEL_BACKGROUND;
        setPreferredSize(new Dimension(width, height));

        add(ordinalLabel);

        SpringLayout sl = new SpringLayout();
        setLayout(sl);
        sl.putConstraint(SpringLayout.NORTH, ordinalLabel, 0, SpringLayout.NORTH, this);
        sl.putConstraint(SpringLayout.SOUTH, ordinalLabel, 0, SpringLayout.SOUTH, this);
        sl.putConstraint(SpringLayout.WEST, ordinalLabel, 0, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.EAST, ordinalLabel, 0, SpringLayout.EAST, this);
    }

    //TODO:
    public void updateOrdinalLabel(String text, Font font, Color textColor) {
        ordinalLabel.setText(text);
        ordinalLabel.setFont(font);
        ordinalLabel.setForeground(textColor);
    }

    public void updateOrdinalFont(Font font) {
        ordinalLabel.setFont(font);
    }

    public void setLabelColor(Color color) {
        ordinalLabel.setForeground(color);
    }

    public void setLabelFont(Font font) {
        ordinalLabel.setFont(font);
    }

    public int getDay() {
        return Integer.valueOf(ordinalLabel.getText());
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
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g.setColor(MonthPanel.DEFAULT_PANEL_BACKGROUND);
        //g.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(currentColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        g2.dispose();
    }

    void setIsCurrentDay(boolean isCurrentDay) {
        this.isCurrentDay = isCurrentDay;
        if (isCurrentDay)
            setCurrentColor(MonthPanel.GRAY_CURRENT_DAY_SUBTLE);
        else
            setCurrentColor(MonthPanel.DEFAULT_PANEL_BACKGROUND);
    }

    public boolean isCurrentDay() {
        return isCurrentDay;
    }
}