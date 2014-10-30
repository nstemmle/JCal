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
    private String fontPathLabels = JingleheimerCalendar.PATH_FONT_KALINGA;

    private Font fontButtons;
    private int fontSizeButtons = 30;
    private String fontPathButtons = JingleheimerCalendar.PATH_FONT_KALINGA;

    private JButton buttonLeft;
    private JButton buttonRight;
    private JLabel yearLabel;

    private SpringLayout springHeader;

    YearHeader(int width){
        setPreferredSize(new Dimension(width, MINIMUM_HEIGHT));
        setBackground(DEFAULT_COMPONENT_BACKGROUND);

        springHeader = new SpringLayout();
        setLayout(springHeader);

        try {
            fontLabels = loadFont(fontSizeLabels,fontPathLabels);
            fontButtons = loadFont(fontSizeButtons,fontPathButtons);
        } catch (FontFormatException |IOException e) {
            System.err.println("Error loading custom font. Using Times.");
            fontLabels = fontButtons = new Font("Times New Roman", Font.BOLD,60);
        }

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
