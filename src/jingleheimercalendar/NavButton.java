package jingleheimercalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Created by Nathan on 12/8/2014.
 */
public class NavButton extends JPanel {
    public static final Color COLOR_TEXT = Color.BLACK;
    private final String context;
    private Color currentColor;
    private NavLabel label;

    NavButton(String text, String context, int width, int height) {
        super();
        this.context = context;
        setOpaque(false);
        initiliaze(text, width, height);
    }

    private class NavLabel extends JLabel {
        NavLabel(String text, int horizontalAlignment) {
            super(text, horizontalAlignment);
            setFont(NavigationPanel.fontButtons);
            setForeground(COLOR_TEXT);
        }
    }

    private void initiliaze(String labelText, int width, int height) {
        Dimension dimens = new Dimension(width, height);
        setPreferredSize(dimens);
        currentColor = NavigationPanel.COLOR_BUTTON_DEFAULT;

        SpringLayout sl = new SpringLayout();
        setLayout(sl);

        label = new NavLabel(labelText, SwingConstants.CENTER);
        label.setPreferredSize(dimens);
        label.setVerticalAlignment(SwingConstants.CENTER);

        sl.putConstraint(SpringLayout.NORTH, label, 0, SpringLayout.NORTH, this);
        sl.putConstraint(SpringLayout.SOUTH, label, 0, SpringLayout.SOUTH, this);
        sl.putConstraint(SpringLayout.WEST, label, 0, SpringLayout.WEST, this);
        sl.putConstraint(SpringLayout.EAST, label, 0, SpringLayout.EAST, this);
        add(label);
    }

    public void selected() {
        setColor(NavigationPanel.COLOR_BUTTON_SELECTED);
    }

    public void unselected() {
        setColor(NavigationPanel.COLOR_BUTTON_DEFAULT);
    }

    protected void setColor(Color color) {
        currentColor = color;
        repaint();
    }

    public int getViewIndex() {
        return JingleheimerCalendar.getViewIndex(context);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        //g.setColor(NavigationPanel.COLOR_BACKGROUND_DEFAULT);
        //g.fillRect(0, 0, getWidth(), getHeight());
        g2.setColor(currentColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        g2.dispose();
        //label.repaint();
        //JingleheimerCalendar.mNavigationPanel.repaint();
    }
}
