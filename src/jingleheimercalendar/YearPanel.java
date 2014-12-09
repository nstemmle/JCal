package jingleheimercalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;

/**
 * Created by Nathan on 10/29/2014.
 */

class YearPanel extends JPanel {
    public static int NUM_MONTHS = 12;
    private static MonthPanelYearViewWrapper[] monthPanelWrappers;
    private static int currentYear;
    private static final int NUM_ROWS = 3;
    private static final int NUM_COLUMNS = 4;
    private int hgap = 10;
    private int vgap = 10;

    YearPanel(int width, int height) {
        setBackground(Color.WHITE);
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH);
        monthPanelWrappers = new MonthPanelYearViewWrapper[NUM_MONTHS];
        for (int i = 0; i < NUM_MONTHS; i++) {
            monthPanelWrappers[i] = new MonthPanelYearViewWrapper((width - (hgap*(NUM_COLUMNS - 1))) / 4, (height - (vgap*(NUM_ROWS - 1))) / 3, -currentMonth + i);
            add(monthPanelWrappers[i]);
        }
        GridLayout gridLayout = new GridLayout(3, 4, hgap, vgap);
        this.setLayout(gridLayout);
        currentYear = monthPanelWrappers[0].getCurrentYear();
    }

    private class MonthLabelHolder extends JPanel {
        private Color currentColor;

        MonthLabelHolder() {
            setOpaque(false);
            setCurrentColor(MonthPanel.DEFAULT_PANEL_BACKGROUND);
        }
        public void setCurrentColor(Color c) {
            currentColor = c;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(currentColor);
            g2.fillRoundRect(0,0,getWidth(),getHeight(),10,10);
            g2.dispose();
        }
    }

    private class MonthPanelYearViewWrapper extends JPanel {
        private MonthPanelYearView monthPanel;
        private JLabel monthLabel;
        private MonthLabelHolder monthLabelHolder;

        MonthPanelYearViewWrapper(int width, int height, int monthdelta) {
            super();
            setPreferredSize(new Dimension(width, height));
            setBackground(Color.WHITE);
            monthPanel = new MonthPanelYearView(width, (height / 5) * 4, monthdelta);
            monthPanel.setFontSizeHeaders(22);
            monthPanel.setFontSizeOrdinals(22);
            add(monthPanel);

            MonthLabelMouseListener mouseListener = new MonthLabelMouseListener();

            monthLabelHolder = new MonthLabelHolder();

            monthLabel = new JLabel(monthPanel.getCurrentMonthString(), SwingConstants.CENTER);
            monthLabel.setFont(JingleheimerCalendar.defaultFont.deriveFont(Font.PLAIN,28f));
            monthLabel.setPreferredSize(new Dimension(width, height / 5));
            monthLabel.setVerticalAlignment(SwingConstants.TOP);
            monthLabel.setVerticalTextPosition(SwingConstants.TOP);

            monthLabelHolder.add(monthLabel);
            monthLabelHolder.setPreferredSize(new Dimension(width, height / 5));
            monthLabelHolder.addMouseListener(mouseListener);

            SpringLayout sl2 = new SpringLayout();

            sl2.putConstraint(SpringLayout.NORTH, monthLabel, 0, SpringLayout.NORTH, monthLabelHolder);
            sl2.putConstraint(SpringLayout.EAST, monthLabel, 0, SpringLayout.EAST, monthLabelHolder);
            sl2.putConstraint(SpringLayout.WEST, monthLabel, 0, SpringLayout.WEST, monthLabelHolder);
            sl2.putConstraint(SpringLayout.SOUTH, monthLabel, 0, SpringLayout.SOUTH, monthLabelHolder);

            monthLabelHolder.setLayout(sl2);

            add(monthLabelHolder);

            SpringLayout sl = new SpringLayout();

            sl.putConstraint(SpringLayout.NORTH, monthLabelHolder, 0, SpringLayout.NORTH, this);
            sl.putConstraint(SpringLayout.EAST, monthLabelHolder, -75, SpringLayout.EAST, this);
            sl.putConstraint(SpringLayout.WEST, monthLabelHolder, 75, SpringLayout.WEST, this);

            sl.putConstraint(SpringLayout.NORTH, monthPanel, 0, SpringLayout.SOUTH, monthLabelHolder);
            sl.putConstraint(SpringLayout.EAST, monthPanel, 0, SpringLayout.EAST, this);
            sl.putConstraint(SpringLayout.WEST, monthPanel, 0, SpringLayout.WEST, this);
            sl.putConstraint(SpringLayout.SOUTH, monthPanel, 0, SpringLayout.SOUTH, this);

            setLayout(sl);
        }

        protected int getCurrentYear() {
            return monthPanel.getCurrentYear();
        }

        protected int getCurrentMonth() {
            return monthPanel.getCurrentMonth();
        }

        protected void changeMonthBy(int monthDelta) {
            monthPanel.changeMonthBy(monthDelta);
        }

        private class MonthLabelMouseListener implements MouseListener {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    JingleheimerCalendar.displayView(JingleheimerCalendar.INDEX_MONTH_VIEW);
                    JingleheimerCalendar.changeMonthViewMonth(getCurrentMonth(), getCurrentYear());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                MonthLabelHolder parent = (MonthLabelHolder) e.getComponent();
                parent.setCurrentColor(MonthPanel.BLUE_SELECTED_MEDIUM);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                MonthLabelHolder parent = (MonthLabelHolder) e.getComponent();
                parent.setCurrentColor(MonthPanel.DEFAULT_PANEL_BACKGROUND);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }
        }
    }

    private static void updateMonthPanels(int monthDelta) {
        if (monthDelta != 0) {
            for (MonthPanelYearViewWrapper mPanel : monthPanelWrappers) {
                mPanel.changeMonthBy(monthDelta);
            }
        }
    }

    static void changeYearBy(int yearDelta) {
        if (yearDelta != 0) {
            updateMonthPanels(yearDelta*12);
        }
        currentYear = monthPanelWrappers[0].getCurrentYear();
    }

    public static int getCurrentYear() {
        return currentYear;
    }
}
