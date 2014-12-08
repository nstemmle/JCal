package jingleheimercalendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;

/**
 * Created by Nathan on 10/24/2014.
 */
public class NavigationPanel extends JPanel {
    public static final int MINIMUM_WIDTH = 800;
    public static final int MINIMUM_HEIGHT = 40;

    //Constant for time-delay of mTimer in miliseconds
    //Want 1 minute = 60 seconds * 1000 miliseconds/second = 60,000 ms
    private static final int DELAY_INTERVAL = 60000;

    private static final int BUTTON_HEIGHT = (MINIMUM_HEIGHT /4) * 3;

    public static final String TEXT_BUTTON_TODAY = "TODAY";
    public static final String TEXT_BUTTON_DAY = "DAY";
    public static final String TEXT_BUTTON_WEEK = "WEEK";
    public static final String TEXT_BUTTON_MONTH = "MONTH";
    public static final String TEXT_BUTTON_YEAR = "YEAR";

    public static final Color COLOR_BUTTON_DEFAULT = new Color(100,100,100);
    public static final Color COLOR_BUTTON_HIGHLIGHTED = new Color(192,192,192);
    public static final Color COLOR_BUTTON_SELECTED = new Color(164,164,164);
    public static final Color COLOR_BACKGROUND_DEFAULT = Color.WHITE;

    private Font fontLabels;
    private int fontSizeLabels = 20;

    protected static Font fontButtons;
    private int fontSizeButtons = 20;

    private NavButton buttonToday;

    private JLabel labelTime;

    private NavButton buttonDay;
    private NavButton buttonWeek;
    private NavButton buttonMonth;
    private NavButton buttonYear;
    private NavButton lastSelected;

    public NavigationPanel(int width) {
        setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
        setPreferredSize(new Dimension(width, MINIMUM_HEIGHT));
        setBackground(COLOR_BACKGROUND_DEFAULT);
        //setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.BLACK));

        GridBagLayout gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);

        GridBagConstraints gbConstraints = new GridBagConstraints();

        //Set custom font
        fontLabels = JingleheimerCalendar.defaultFont.deriveFont((float)fontSizeLabels);
        fontButtons = JingleheimerCalendar.defaultFont.deriveFont((float)fontSizeButtons);

        JPanel todayButtonPane = new JPanel();
        todayButtonPane.setBackground(COLOR_BACKGROUND_DEFAULT);
        //todayButtonPane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLUE));
        todayButtonPane.setPreferredSize(new Dimension(width / 5, MINIMUM_HEIGHT));
        todayButtonPane.setMinimumSize(new Dimension(width / 5, MINIMUM_HEIGHT));

        gbConstraints.fill = GridBagConstraints.BOTH;
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 0;
        gbConstraints.anchor = GridBagConstraints.EAST;
        //TODO: see if you can fix navigation objects layout
        gbConstraints.ipadx = 0;

        add(todayButtonPane, gbConstraints);

        MouseListener navButtonListener = new NavButtonMouseListener();

        //NavButton replacement
        buttonToday = new NavButton(TEXT_BUTTON_TODAY, JingleheimerCalendar.VIEW_DAY, width / 10, BUTTON_HEIGHT);
        //buttonToday.setPreferredSize(new Dimension(width / 10, BUTTON_HEIGHT));
        buttonToday.addMouseListener(navButtonListener);
        todayButtonPane.setLayout(new FlowLayout());
        todayButtonPane.add(buttonToday);

        //buttonToday.setLocation(PADDING_HORIZONTAL_CONTAINER_EDGE, PADDING_VERTICAL_CONTAINER_EDGE);


        JPanel navButtonsPane = new JPanel();
        navButtonsPane.setBackground(COLOR_BACKGROUND_DEFAULT);
        //navButtonsPane.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.GREEN));
        navButtonsPane.setPreferredSize(new Dimension((width / 5) * 3, MINIMUM_HEIGHT));
        navButtonsPane.setMinimumSize(new Dimension((width / 5) * 3, MINIMUM_HEIGHT));

        gbConstraints.gridx = 1;
        gbConstraints.gridy = 0;
        gbConstraints.anchor = GridBagConstraints.CENTER;
        gbConstraints.ipadx = 150;
        add(navButtonsPane, gbConstraints);

        buttonDay = new NavButton(TEXT_BUTTON_DAY, JingleheimerCalendar.VIEW_DAY, width /10, BUTTON_HEIGHT);
        //buttonDay.setPreferredSize(new Dimension(width / 10, BUTTON_HEIGHT));
        buttonDay.addMouseListener(navButtonListener);
        navButtonsPane.add(buttonDay);

        buttonWeek = new NavButton(TEXT_BUTTON_WEEK, JingleheimerCalendar.VIEW_WEEK, width /10, BUTTON_HEIGHT);
        //buttonWeek.setPreferredSize(new Dimension(width / 10, BUTTON_HEIGHT));
        buttonWeek.addMouseListener(navButtonListener);
        navButtonsPane.add(buttonWeek);

        buttonMonth = new NavButton(TEXT_BUTTON_MONTH, JingleheimerCalendar.VIEW_MONTH, width /10, BUTTON_HEIGHT);
        //buttonMonth.setPreferredSize(new Dimension(width / 10, BUTTON_HEIGHT));
        buttonMonth.addMouseListener(navButtonListener);
        navButtonsPane.add(buttonMonth);

        buttonYear = new NavButton(TEXT_BUTTON_YEAR, JingleheimerCalendar.VIEW_YEAR, width /10, BUTTON_HEIGHT);
        //buttonYear.setPreferredSize(new Dimension(width / 10, BUTTON_HEIGHT));
        buttonYear.addMouseListener(navButtonListener);
        navButtonsPane.add(buttonYear);

        JPanel timeLabelPane = new JPanel();
        timeLabelPane.setBackground(COLOR_BACKGROUND_DEFAULT);
        //timeLabelPane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.PINK));
        timeLabelPane.setPreferredSize(new Dimension(width / 5, BUTTON_HEIGHT));
        timeLabelPane.setMinimumSize(new Dimension(width / 5, BUTTON_HEIGHT));

        gbConstraints.gridx = 2;
        gbConstraints.gridy = 0;
        gbConstraints.anchor = GridBagConstraints.EAST;
        gbConstraints.ipadx = 0;
        add(timeLabelPane, gbConstraints);

        Calendar a  = Calendar.getInstance();
        String time = "";
        int min = a.get(Calendar.MINUTE);
        time = time.concat(String.valueOf(a.get(Calendar.HOUR))).concat(":");
        time = time.concat(min < 10 ? "0" + String.valueOf(min) : String.valueOf(min));
        time = time.concat((a.get(Calendar.AM_PM ) == Calendar.AM ? " AM" : " PM"));
        labelTime = new JLabel(time, SwingConstants.CENTER);
        timeLabelPane.add(labelTime);

        Timer timer = new Timer(DELAY_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar c  = Calendar.getInstance();
                String time = "";
                int min = c.get(Calendar.MINUTE);
                time = time.concat(String.valueOf(c.get(Calendar.HOUR))).concat(":");
                time = time.concat(min < 10 ? "0" + String.valueOf(min) : String.valueOf(min));
                time = time.concat((c.get(Calendar.AM_PM ) == Calendar.AM ? " AM" : " PM"));
                labelTime.setText(time);
            }
        });

        Calendar c = Calendar.getInstance();
        int second = c.get(Calendar.SECOND);
        int millis = c.get(Calendar.MILLISECOND);
        int delay = (((59 - second) * 1000) + (1000 - millis));
        timer.setInitialDelay(delay);
        timer.start();

        updateLabelFonts();
        updateButtonFonts();

        buttonDay.selected();
        lastSelected = buttonDay;
    }

    public static String getTimeStamp() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        int milis = c.get(Calendar.MILLISECOND);
        return hour + ":" + minute + ":" + second + "." + milis;
    }

    //TODO
    private void initializeLayouts() {

    }

    private class NavButtonMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            NavButton clicked = (NavButton) e.getComponent();
            if (lastSelected != clicked) {
                JingleheimerCalendar.displayView(clicked.getViewIndex());
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            NavButton hovered = (NavButton) e.getComponent();
            hovered.setColor(NavigationPanel.COLOR_BUTTON_HIGHLIGHTED);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            NavButton hovered = (NavButton) e.getComponent();
            if (hovered == lastSelected)
                hovered.setColor(NavigationPanel.COLOR_BUTTON_SELECTED);
            else
                hovered.setColor(NavigationPanel.COLOR_BUTTON_DEFAULT);
        }
    }

    public void viewChanged(String newView) {
        lastSelected.unselected();
        switch (newView) {
            case JingleheimerCalendar.VIEW_TODAY:
            case JingleheimerCalendar.VIEW_DAY:
                buttonDay.selected();
                lastSelected = buttonDay;
                break;
            case JingleheimerCalendar.VIEW_WEEK:
                buttonWeek.selected();
                lastSelected = buttonWeek;
                break;
            case JingleheimerCalendar.VIEW_MONTH:
                buttonMonth.selected();
                lastSelected = buttonMonth;
                break;
            case JingleheimerCalendar.VIEW_YEAR:
                buttonYear.selected();
                lastSelected = buttonYear;
                break;
        }
    }

    private void updateLabelFonts() {
        if (labelTime != null) {
            labelTime.setFont(fontLabels);
        }
    }

    private void updateButtonFonts() {
        if (buttonToday != null) {
            buttonToday.setFont(fontButtons);
        }
        if (buttonDay != null) {
            buttonDay.setFont(fontButtons);
        }
        if (buttonWeek != null) {
            buttonWeek.setFont(fontButtons);
        }
        if (buttonMonth != null) {
            buttonMonth.setFont(fontButtons);
        }
        if (buttonYear != null) {
            buttonYear.setFont(fontButtons);
        }
    }

    public void setFontSizeLabels(int size) {
        fontSizeLabels = size;
        fontLabels = fontLabels.deriveFont(((float) fontSizeLabels));
        updateLabelFonts();
    }

    public void setFontSizeButtons(int size) {
        fontSizeButtons = size;
        fontButtons = fontButtons.deriveFont(((float) fontSizeButtons));
        updateButtonFonts();
    }
}
