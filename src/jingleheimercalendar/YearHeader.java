package jingleheimercalendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Nathan on 10/29/2014.
 */
public class YearHeader extends JPanel{
    public static final int MINIMUM_HEIGHT = 100;
    public static final Color DEFAULT_COMPONENT_BACKGROUND = Color.WHITE;

    public static final int PADDING_VERTICAL_CONTAINER_EDGE = 25;
    public static final int PADDING_VERTICAL_BUTTON_LABEL = 10;
    public static final int PADDING_HORIZONTAL_CONTAINER_EDGE = 25;
    public static final int PADDING_HORIZONTAL_BUTTON_LABEL = 10;

    private Font fontLabels;
    private int fontSizeLabels = 48;

    private Font fontButtons;
    private int fontSizeButtons = 30;

    private JButton buttonLeft;
    private JButton buttonRight;
    private JLabel yearLabel;

    private SpringLayout springHeader;

    YearHeader(int width){
        setPreferredSize(new Dimension(width, MINIMUM_HEIGHT));
        setBackground(DEFAULT_COMPONENT_BACKGROUND);

        springHeader = new SpringLayout();
        setLayout(springHeader);

        fontLabels = JingleheimerCalendar.defaultFont.deriveFont((float)fontSizeLabels);
        fontButtons = JingleheimerCalendar.defaultFont.deriveFont((float)fontSizeButtons);

        buttonLeft = new JButton("<");
        buttonLeft.setBackground(DEFAULT_COMPONENT_BACKGROUND);
        buttonLeft.setBorderPainted(false);
        buttonLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                YearPanel.changeYearBy(-1);
            }
        });

        buttonRight = new JButton(">");
        buttonRight.setBackground(DEFAULT_COMPONENT_BACKGROUND);
        buttonRight.setBorderPainted(false);
        buttonRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                YearPanel.changeYearBy(1);
            }
        });

        yearLabel = new JLabel("", SwingConstants.CENTER);
        updateYearLabel();

        updateLabelFonts();
        updateButtonFonts();

        add(buttonLeft);
        add(buttonRight);
        add(yearLabel);

        initializeLayouts(width);
    }

    private void initializeLayouts(int width) {
        springHeader.putConstraint(SpringLayout.NORTH, yearLabel, PADDING_VERTICAL_CONTAINER_EDGE, SpringLayout.NORTH, this);
        //springHeader.putConstraint(SpringLayout.NORTH, buttonLeft, PADDING_VERTICAL_BUTTON_LABEL, SpringLayout.NORTH, monthLabel);
        //springHeader.putConstraint(SpringLayout.NORTH, buttonRight, PADDING_VERTICAL_BUTTON_LABEL, SpringLayout.NORTH, monthLabel);
        springHeader.putConstraint(SpringLayout.NORTH, buttonLeft, 0, SpringLayout.NORTH, yearLabel);
        springHeader.putConstraint(SpringLayout.SOUTH, buttonLeft, 0, SpringLayout.SOUTH, yearLabel);

        springHeader.putConstraint(SpringLayout.NORTH, buttonRight, 0, SpringLayout.NORTH, yearLabel);
        springHeader.putConstraint(SpringLayout.SOUTH, buttonRight, 0, SpringLayout.SOUTH, yearLabel);

        springHeader.putConstraint(SpringLayout.WEST, buttonLeft, PADDING_HORIZONTAL_CONTAINER_EDGE, SpringLayout.WEST, this);
        springHeader.putConstraint(SpringLayout.WEST, yearLabel, PADDING_HORIZONTAL_BUTTON_LABEL, SpringLayout.EAST, buttonLeft);
        springHeader.putConstraint(SpringLayout.WEST, buttonRight, PADDING_HORIZONTAL_BUTTON_LABEL, SpringLayout.EAST, yearLabel);

        //springHeader.putConstraint(SpringLayout.EAST, yearLabel, -PADDING_HORIZONTAL_CONTAINER_EDGE, SpringLayout.EAST, this);
    }

    private void updateYearLabel() {
        yearLabel.setText(String.valueOf(YearPanel.getCurrentYear()));
    }

    private void updateLabelFonts() {
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
