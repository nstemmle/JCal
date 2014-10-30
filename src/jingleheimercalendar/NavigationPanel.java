package jingleheimercalendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Roach on 10/24/2014.
 */
public class NavigationPanel extends JPanel {
    public static final int MINIMUM_WIDTH = 800;
    public static final int MINIMUM_HEIGHT = 40;

    public static final int PADDING_HORIZONTAL_CONTAINER_EDGE = 25;
    public static final int PADDING_VERTICAL_CONTAINER_EDGE = 10;

    public static final String TEXT_BUTTON_TODAY = "Today";
    public static final String TEXT_BUTTON_DAY = "Day";
    public static final String TEXT_BUTTON_WEEK = "Week";
    public static final String TEXT_BUTTON_MONTH = "Month";
    public static final String TEXT_BUTTON_YEAR = "Year";


    public static final Color COLOR_BACKGROUND_BUTTON = new Color(192,192,192);
    public static final Color COLOR_BACKGROUND_DEFAULT = Color.WHITE;

    private Font fontLabels;
    private int fontSizeLabels = 16;
    private String fontPathLabels = JingleheimerCalendar.PATH_FONT_KALINGA;

    private Font fontButtons;
    private int fontSizeButtons = 16;
    private String fontPathButtons = JingleheimerCalendar.PATH_FONT_KALINGA;

    private int gridPaddingHorizontal = 10;
    private int gridPaddingVertical = 10;

    private GridBagLayout gridBagLayout;

    private JPanel todayButtonPane;
    private JButton buttonToday;

    private JPanel timeLabelPane;
    private JLabel labelTime;

    private JPanel navButtonsPane;
    private GridLayout gridLayout;
    private JButton buttonDay;
    private JButton buttonWeek;
    private JButton buttonMonth;
    private JButton buttonYear;

    public NavigationPanel(int width) {
        setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
        setPreferredSize(new Dimension(width, MINIMUM_HEIGHT));
        setBackground(COLOR_BACKGROUND_DEFAULT);
        //setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.BLACK));

        gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);

        GridBagConstraints gbConstraints = new GridBagConstraints();

        //Set custom font
        try {
            fontLabels = loadFont(fontSizeLabels,fontPathLabels);
            fontButtons = loadFont(fontSizeButtons,fontPathButtons);
        } catch (FontFormatException |IOException e) {
            System.err.println("Error loading custom font. Using Times.");
            fontLabels = fontButtons = new Font("Times New Roman", Font.BOLD,60);
        }


        todayButtonPane = new JPanel();
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
        buttonToday.setBackground(COLOR_BACKGROUND_BUTTON);
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


        navButtonsPane = new JPanel();
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
        buttonDay.setBackground(COLOR_BACKGROUND_BUTTON);
        buttonDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JingleheimerCalendar.displayView(JingleheimerCalendar.INDEX_DAY_VIEW);
            }
        });
        navButtonsPane.add(buttonDay);

        buttonWeek = new JButton(TEXT_BUTTON_WEEK);
        buttonWeek.setPreferredSize(new Dimension(width / 10, MINIMUM_HEIGHT / 2));
        buttonWeek.setBackground(COLOR_BACKGROUND_BUTTON);
        buttonWeek.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JingleheimerCalendar.displayView(JingleheimerCalendar.INDEX_WEEK_VIEW);
            }
        });
        navButtonsPane.add(buttonWeek);

        buttonMonth = new JButton(TEXT_BUTTON_MONTH);
        buttonMonth.setPreferredSize(new Dimension(width / 10, MINIMUM_HEIGHT / 2));
        buttonMonth.setBackground(COLOR_BACKGROUND_BUTTON);
        buttonMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JingleheimerCalendar.displayView(JingleheimerCalendar.INDEX_MONTH_VIEW);
            }
        });
        navButtonsPane.add(buttonMonth);

        buttonYear = new JButton(TEXT_BUTTON_YEAR);
        buttonYear.setPreferredSize(new Dimension(width / 10, MINIMUM_HEIGHT / 2));
        buttonYear.setBackground(COLOR_BACKGROUND_BUTTON);
        buttonYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JingleheimerCalendar.displayView(JingleheimerCalendar.INDEX_YEAR_VIEW);
            }
        });
        navButtonsPane.add(buttonYear);

        timeLabelPane = new JPanel();
        timeLabelPane.setBackground(COLOR_BACKGROUND_DEFAULT);
        //timeLabelPane.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.PINK));
        timeLabelPane.setPreferredSize(new Dimension(width / 4, MINIMUM_HEIGHT));
        timeLabelPane.setMinimumSize(new Dimension(width / 4, MINIMUM_HEIGHT));

        gbConstraints.gridx = 2;
        gbConstraints.gridy = 0;
        gbConstraints.anchor = GridBagConstraints.EAST;
        gbConstraints.ipadx = 150;
        add(timeLabelPane, gbConstraints);

        labelTime = new JLabel("11:30 AM", SwingConstants.CENTER);
        timeLabelPane.add(labelTime);

        updateLabelFonts();

        updateButtonFonts();

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

    public void setFontPathLabels(String path) {
        fontPathLabels = path;
        try {
            fontLabels = loadFont(fontSizeLabels,fontPathLabels);
            updateLabelFonts();
        } catch (FontFormatException |IOException e) {
            System.err.println("Error loading custom font. Using Times.");
            fontLabels = new Font("Times New Roman", Font.BOLD,60);
        }
    }

    public void setFontPathButtons(String path) {
        fontPathButtons = path;
        try {
            fontButtons = loadFont(fontSizeButtons,fontPathButtons);
            updateButtonFonts();
        } catch (FontFormatException |IOException e) {
            System.err.println("Error loading custom font. Using Times.");
            fontButtons = new Font("Times New Roman", Font.BOLD,60);
        }
    }

    private Font loadFont(int fSize, String fPath) throws FontFormatException, IOException  {
        URL fontUrl = getClass().getResource(fPath);
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
        font = font.deriveFont(Font.PLAIN, fSize);
        return font;
    }

}
