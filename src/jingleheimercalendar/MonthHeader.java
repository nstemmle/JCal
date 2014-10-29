package jingleheimercalendar;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

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
    private String fontPathLabels = "/font/Nexa_Bold.ttf";

    private Font fontButtons;
    private int fontSizeButtons = 30;
    private String fontPathButtons = "/font/Nexa_Bold.ttf";

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
        try {
            fontLabels = loadFont(fontSizeLabels,fontPathLabels);
            fontButtons = loadFont(fontSizeButtons,fontPathButtons);
        } catch (FontFormatException |IOException e) {
            System.err.println("Error loading custom font. Using Times.");
            fontLabels = fontButtons = new Font("Times New Roman", Font.BOLD,60);
        }

        buttonLeft = new JButton("<");
        buttonLeft.setBackground(DEFAULT_COMPONENT_BACKGROUND);
        buttonLeft.setDefaultCapable(false);
        buttonLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MonthView.monthPanel.changeMonthBy(-1);
                updateMonthLabel();
                updateYearLabel();
            }
        });

        buttonRight = new JButton(">");
        buttonRight.setBackground(DEFAULT_COMPONENT_BACKGROUND);
        buttonRight.setDefaultCapable(false);
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

    private void initializeLayouts(int width) {
        springHeader.putConstraint(SpringLayout.NORTH, monthLabel, PADDING_VERTICAL_CONTAINER_EDGE, SpringLayout.NORTH, this);
        //springHeader.putConstraint(SpringLayout.NORTH, buttonLeft, PADDING_VERTICAL_BUTTON_LABEL, SpringLayout.NORTH, monthLabel);
        //springHeader.putConstraint(SpringLayout.NORTH, buttonRight, PADDING_VERTICAL_BUTTON_LABEL, SpringLayout.NORTH, monthLabel);
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