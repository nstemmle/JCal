package jingleheimercalendar;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Nathan on 10/28/2014.
 */
public class MonthHeader extends JPanel {
    public static final int HEADER_MINIMUM_HEIGHT = 100; //100 pixels

    public static final int PADDING_VERTICAL_CONTAINER_EDGE = 25;
    public static final int PADDING_VERTICAL_BUTTON_LABEL = 10;
    public static final int PADDING_HORIZONTAL_CONTAINER_EDGE = 25;
    public static final int PADDING_HORIZONTAL_BUTTON_LABEL = 10;

    public static final Color DEFAULT_COMPONENT_BACKGROUND = Color.WHITE;

    private Font fontLabels;
    private int fontSizeLabels = 48;

    private Font fontButtons;
    private int fontSizeButtons = 30;

    private JButton buttonLeft;
    private JButton buttonRight;
    private JLabel monthLabel;
    private JLabel yearLabel;
    private SpringLayout springHeader;

    MonthHeader(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setBackground(DEFAULT_COMPONENT_BACKGROUND);

        springHeader = new SpringLayout();
        setLayout(springHeader);

        //Set custom font
        fontLabels = JingleheimerCalendar.defaultFont.deriveFont((float)fontSizeLabels);
        fontButtons = JingleheimerCalendar.defaultFont.deriveFont((float)fontSizeButtons);

        buttonLeft = new JButton();
        buttonLeft.setIcon(new ImageIcon(getClass().getResource("/images/leftArrow32.png")));
        buttonLeft.setBackground(DEFAULT_COMPONENT_BACKGROUND);
        buttonLeft.setBorderPainted(false);
        buttonLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MonthView.monthPanel.changeMonthBy(-1);
                updateMonthLabel();
                updateYearLabel();
            }
        });

        buttonRight = new JButton();
        buttonRight.setIcon(new ImageIcon(getClass().getResource("/images/rightArrow32.png")));
        buttonRight.setBackground(DEFAULT_COMPONENT_BACKGROUND);
        buttonRight.setBorderPainted(false);
        buttonRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MonthView.monthPanel.changeMonthBy(1);
                updateMonthLabel();
                updateYearLabel();
            }
        });

        monthLabel = new JLabel("", SwingConstants.CENTER);
        updateMonthLabel();

        yearLabel = new JLabel("", SwingConstants.RIGHT);
        updateYearLabel();

        updateLabelFonts();

        updateButtonFonts();

        add(buttonLeft);
        add(buttonRight);
        add(monthLabel);
        add(yearLabel);

        initializeLayouts(width);
    }

    void updateMonthLabel() {
        monthLabel.setText(MonthView.monthPanel.getCurrentMonthString());
    }

    public void update() {
        updateMonthLabel();
        updateYearLabel();
    }

    private void initializeLayouts(int width) {
        springHeader.putConstraint(SpringLayout.NORTH, monthLabel, PADDING_VERTICAL_CONTAINER_EDGE, SpringLayout.NORTH, this);
        springHeader.putConstraint(SpringLayout.NORTH, buttonLeft, 0, SpringLayout.NORTH, monthLabel);
        springHeader.putConstraint(SpringLayout.SOUTH, buttonLeft, 0, SpringLayout.SOUTH, monthLabel);

        springHeader.putConstraint(SpringLayout.NORTH, buttonRight, 0, SpringLayout.NORTH, monthLabel);
        springHeader.putConstraint(SpringLayout.SOUTH, buttonRight, 0, SpringLayout.SOUTH, monthLabel);

        springHeader.putConstraint(SpringLayout.NORTH, yearLabel, PADDING_VERTICAL_CONTAINER_EDGE, SpringLayout.NORTH, this);

        springHeader.putConstraint(SpringLayout.WEST, buttonLeft, PADDING_HORIZONTAL_CONTAINER_EDGE, SpringLayout.WEST, this);
        springHeader.putConstraint(SpringLayout.WEST, monthLabel, PADDING_HORIZONTAL_BUTTON_LABEL, SpringLayout.EAST, buttonLeft);
        springHeader.putConstraint(SpringLayout.WEST, buttonRight, PADDING_HORIZONTAL_BUTTON_LABEL, SpringLayout.EAST, monthLabel);

        springHeader.putConstraint(SpringLayout.EAST, yearLabel, -PADDING_HORIZONTAL_CONTAINER_EDGE, SpringLayout.EAST, this);
    }

    private void updateYearLabel() {
        yearLabel.setText(String.valueOf(MonthView.monthPanel.getCurrentYear()));
    }

    private void updateLabelFonts() {
        if (monthLabel != null) {
            monthLabel.setFont(fontLabels);
        }
        if (yearLabel != null) {
            yearLabel.setFont(fontLabels);
        }
    }

    private void updateButtonFonts() {
        if (buttonLeft != null) {
            buttonLeft.setFont(fontButtons);
        }
        if (buttonRight != null) {
            buttonRight.setFont(fontButtons);
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
