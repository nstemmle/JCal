package jingleheimercalendar;

import javax.swing.JButton;
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

    public static final String TEXT_BUTTON_TODAY = "Today";
    public static final String TEXT_BUTTON_DAY = "Day";
    public static final String TEXT_BUTTON_WEEK = "Week";
    public static final String TEXT_BUTTON_MONTH = "Month";
    public static final String TEXT_BUTTON_YEAR = "Year";

    public static final Color COLOR_BUTTON_DEFAULT = new Color(128,128,128);
    public static final Color COLOR_BUTTON_SELECTED = new Color(192,192,192);
    public static final Color COLOR_BACKGROUND_DEFAULT = Color.WHITE;

    private Font fontLabels;
    private int fontSizeLabels = 16;

    private Font fontButtons;
    private int fontSizeButtons = 16;

    private JButton buttonToday;

    private JLabel labelTime;

    private JButton buttonDay;
    private JButton buttonWeek;
    private JButton buttonMonth;
    private JButton buttonYear;
    private JButton lastSelected;

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
        todayButtonPane.setPreferredSize(new Dimension(width / 4, MINIMUM_HEIGHT));
        todayButtonPane.setMinimumSize(new Dimension(width / 4, MINIMUM_HEIGHT));

        gbConstraints.fill = GridBagConstraints.BOTH;
        gbConstraints.gridx = 0;
        gbConstraints.gridy = 0;
        gbConstraints.anchor = GridBagConstraints.EAST;
        gbConstraints.ipadx = 125;

        add(todayButtonPane, gbConstraints);

        buttonToday = new JButton(TEXT_BUTTON_TODAY);
        buttonToday.setPreferredSize(new Dimension(width / 10, MINIMUM_HEIGHT / 2));
        buttonToday.setBackground(COLOR_BUTTON_DEFAULT);
        buttonToday.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JingleheimerCalendar.displayView(JingleheimerCalendar.INDEX_DAY_VIEW);
                DayView.goToCurrentDay();
            }
        });
        todayButtonPane.setLayout(new FlowLayout());
        todayButtonPane.add(buttonToday);

        //buttonToday.setLocation(PADDING_HORIZONTAL_CONTAINER_EDGE, PADDING_VERTICAL_CONTAINER_EDGE);


        JPanel navButtonsPane = new JPanel();
        navButtonsPane.setBackground(COLOR_BACKGROUND_DEFAULT);
        //navButtonsPane.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.GREEN));
        navButtonsPane.setPreferredSize(new Dimension(width / 2, MINIMUM_HEIGHT));
        navButtonsPane.setMinimumSize(new Dimension(width / 2, MINIMUM_HEIGHT));

        gbConstraints.gridx = 1;
        gbConstraints.gridy = 0;
        gbConstraints.anchor = GridBagConstraints.CENTER;
        add(navButtonsPane, gbConstraints);

        buttonDay = new JButton(TEXT_BUTTON_DAY);
        buttonDay.setPreferredSize(new Dimension(width / 10, MINIMUM_HEIGHT / 2));
        buttonDay.setBackground(COLOR_BUTTON_DEFAULT);
        buttonDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonDay.setBackground(COLOR_BUTTON_SELECTED);
                if (lastSelected != buttonDay) {
                    JingleheimerCalendar.displayView(JingleheimerCalendar.INDEX_DAY_VIEW);
                    lastSelected.setBackground(COLOR_BUTTON_DEFAULT);
                }
                lastSelected = buttonDay;
            }
        });
        navButtonsPane.add(buttonDay);

        buttonWeek = new JButton(TEXT_BUTTON_WEEK);
        buttonWeek.setPreferredSize(new Dimension(width / 10, MINIMUM_HEIGHT / 2));
        buttonWeek.setBackground(COLOR_BUTTON_DEFAULT);
        buttonWeek.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonWeek.setBackground(COLOR_BUTTON_SELECTED);
                if (lastSelected != buttonWeek) {
                    JingleheimerCalendar.displayView(JingleheimerCalendar.INDEX_WEEK_VIEW);
                    lastSelected.setBackground(COLOR_BUTTON_DEFAULT);
                }
                lastSelected = buttonWeek;
            }
        });
        navButtonsPane.add(buttonWeek);

        buttonMonth = new JButton(TEXT_BUTTON_MONTH);
        buttonMonth.setPreferredSize(new Dimension(width / 10, MINIMUM_HEIGHT / 2));
        buttonMonth.setBackground(COLOR_BUTTON_DEFAULT);
        buttonMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonMonth.setBackground(COLOR_BUTTON_SELECTED);
                if (lastSelected != buttonMonth) {
                    JingleheimerCalendar.displayView(JingleheimerCalendar.INDEX_MONTH_VIEW);
                    lastSelected.setBackground(COLOR_BUTTON_DEFAULT);
                }
                lastSelected = buttonMonth;
            }
        });
        navButtonsPane.add(buttonMonth);

        buttonYear = new JButton(TEXT_BUTTON_YEAR);
        buttonYear.setPreferredSize(new Dimension(width / 10, MINIMUM_HEIGHT / 2));
        buttonYear.setBackground(COLOR_BUTTON_DEFAULT);
        buttonYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonYear.setBackground(COLOR_BUTTON_SELECTED);
                if (lastSelected != buttonYear) {
                    JingleheimerCalendar.displayView(JingleheimerCalendar.INDEX_YEAR_VIEW);
                    lastSelected.setBackground(COLOR_BUTTON_DEFAULT);
                }
                lastSelected = buttonYear;
            }
        });
        navButtonsPane.add(buttonYear);

        JPanel timeLabelPane = new JPanel();
        timeLabelPane.setBackground(COLOR_BACKGROUND_DEFAULT);
        //timeLabelPane.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.PINK));
        timeLabelPane.setPreferredSize(new Dimension(width / 4, MINIMUM_HEIGHT));
        timeLabelPane.setMinimumSize(new Dimension(width / 4, MINIMUM_HEIGHT));

        gbConstraints.gridx = 2;
        gbConstraints.gridy = 0;
        gbConstraints.anchor = GridBagConstraints.EAST;
        gbConstraints.ipadx = 150;
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
