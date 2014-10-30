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
public class CategoryPanel extends JPanel {
    public static final int MINIMUM_WIDTH = 800;
    public static final int MINIMUM_HEIGHT = 40;
    public static final int MIN_BUTTON_WIDTH = 50;
    public static final int NUM_DEFAULT_CATEGORIES = 5;
    public static final int NUM_DISPLAYED_CATEGORIES = 4;

    public static final int PADDING_VERTICAL_CONTAINER_EDGE = 25;
    public static final int PADDING_VERTICAL_BUTTON_LABEL = 10;
    public static final int PADDING_HORIZONTAL_CONTAINER_EDGE = 25;
    public static final int PADDING_HORIZONTAL_BUTTON_LABEL = 10;

    public static final Color COLOR_BACKGROUND_DEFAULT = Color.WHITE;

    public static final Color COLOR_TEXT_SELECTED = Color.WHITE;
    //public static final Color COLOR_TEXT_SELECTED = Color.BLACK;
    public static final Color COLOR_TEXT_DEFAULT = new Color(128,128,128);

    /*public static final Color COLOR_CAT_ONE_DEFAULT = new Color(255,183,0,64);
    public static final Color COLOR_CAT_ONE_SELECTED = new Color(240,200,0);
    public static final Color COLOR_CAT_TWO_DEFAULT = new Color(90,255,0,64);
    public static final Color COLOR_CAT_TWO_SELECTED = new Color(90,255,0);
    public static final Color COLOR_CAT_THREE_DEFAULT = new Color(255,68,0,64);
    public static final Color COLOR_CAT_THREE_SELECTED = new Color(255,68,0);
    public static final Color COLOR_CAT_FOUR_DEFAULT = new Color(0,200,255,64);
    public static final Color COLOR_CAT_FOUR_SELECTED = new Color(0,200,255);
    public static final Color COLOR_CAT_FIVE_DEFAULT = new Color(196,0,255,64);
    public static final Color COLOR_CAT_FIVE_SELECTED = new Color(196,0,255);*/

    public static final Color COLOR_CAT_ONE_DEFAULT = new Color(63,169,245);
    public static final Color COLOR_CAT_TWO_DEFAULT = new Color(255,147,30);
    public static final Color COLOR_CAT_THREE_DEFAULT = new Color(122,201,67);
    public static final Color COLOR_CAT_FOUR_DEFAULT = new Color(102,45,145);
    public static final Color COLOR_CAT_FIVE_DEFAULT = new Color(196,0,255,64);


    public static final Color COLOR_CAT_ONE_SELECTED = new Color(171,216,244);
    public static final Color COLOR_CAT_TWO_SELECTED = new Color(255,217,179);
    public static final Color COLOR_CAT_THREE_SELECTED = new Color(165,198,139);
    public static final Color COLOR_CAT_FOUR_SELECTED = new Color(120,95,142);
    public static final Color COLOR_CAT_FIVE_SELECTED = new Color(196,0,255);

    private Font fontLabels;
    private int fontSizeLabels = 36;
    private String fontPathLabels = JingleheimerCalendar.PATH_FONT_KALINGA;

    private Font fontButtons;
    private int fontSizeButtons = 28;
    private String fontPathButtons = JingleheimerCalendar.PATH_FONT_KALINGA;

    public static enum Categories {
        SCHOOL ("School", COLOR_CAT_ONE_DEFAULT, COLOR_CAT_ONE_SELECTED),
        FAMILY ("Family", COLOR_CAT_TWO_DEFAULT, COLOR_CAT_TWO_SELECTED),
        WORK ("Work", COLOR_CAT_THREE_DEFAULT, COLOR_CAT_THREE_SELECTED),
        WELLNESS ("Wellness", COLOR_CAT_FOUR_DEFAULT, COLOR_CAT_FOUR_SELECTED),
        FUN ("Fun", COLOR_CAT_FIVE_DEFAULT, COLOR_CAT_FIVE_SELECTED);

        private final String text;
        private final Color color;
        private final Color colorSelected;
        Categories(String text, Color color, Color colorSelected){
            this.text = text;
            this.color = color;
            this.colorSelected = colorSelected;
        }
        public String getText() {
            return text;
        }
        public Color getColor() {
            return color;
        }
        public Color getColorSelected() {
            return colorSelected;
        }
    }

    //Components
    private String[] categories;
    private JLabel[] categoryLabels;
    //The index in the categories[] array of the last displayed JLabel
    private int indexLastDisplayedCategory;
    private SpringLayout springLayout;

    private JButton buttonLeft;
    private JButton buttonRight;
    private JButton buttonPlus;

    public CategoryPanel(int width) {
        setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
        setPreferredSize(new Dimension(width, MINIMUM_HEIGHT));
        setBackground(COLOR_BACKGROUND_DEFAULT);
        setBorder(BorderFactory.createMatteBorder(2,0,0,0, Color.BLACK));

        springLayout = new SpringLayout();
        this.setLayout(springLayout);

        //Set custom font
        try {
            fontLabels = loadFont(fontSizeLabels,fontPathLabels);
            fontButtons = loadFont(fontSizeButtons,fontPathButtons);
        } catch (FontFormatException |IOException e) {
            System.err.println("Error loading custom font. Using Times.");
            fontLabels = fontButtons = new Font("Times New Roman", Font.BOLD,60);
        }

        //This line will have to change when we want to implement persistence
        //For now just use defaults
        initializeCategories(NUM_DEFAULT_CATEGORIES, width);

        buttonLeft = new JButton("<");
        buttonLeft.setMinimumSize(new Dimension(MIN_BUTTON_WIDTH, MINIMUM_HEIGHT));
        buttonLeft.setPreferredSize(new Dimension(MIN_BUTTON_WIDTH, MINIMUM_HEIGHT));
        buttonLeft.setBackground(COLOR_BACKGROUND_DEFAULT);
        buttonLeft.setDefaultCapable(false);
        buttonLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeDisplayedCategories(-1);
            }
        });
        add(buttonLeft);

        buttonRight = new JButton(">");
        buttonRight.setMinimumSize(new Dimension(MIN_BUTTON_WIDTH, MINIMUM_HEIGHT));
        buttonRight.setPreferredSize(new Dimension(MIN_BUTTON_WIDTH, MINIMUM_HEIGHT));
        buttonRight.setBackground(COLOR_BACKGROUND_DEFAULT);
        buttonRight.setDefaultCapable(false);
        buttonRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeDisplayedCategories(1);
            }
        });
        add(buttonRight);

        buttonPlus = new JButton("+");
        buttonPlus.setMinimumSize(new Dimension(MIN_BUTTON_WIDTH, MINIMUM_HEIGHT));
        buttonPlus.setPreferredSize(new Dimension(MIN_BUTTON_WIDTH, MINIMUM_HEIGHT));
        buttonPlus.setBackground(COLOR_BACKGROUND_DEFAULT);
        buttonPlus.setDefaultCapable(false);
        buttonPlus.setBorder(BorderFactory.createMatteBorder(5,0,0,0,COLOR_TEXT_DEFAULT));
        buttonPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
                //Insert logic for new category here
            }
        });
        add(buttonPlus);

        updateLabelFonts();
        updateButtonFonts();

        initializeLayout();
    }

    private void initializeLayout() {
        springLayout.putConstraint(SpringLayout.NORTH, buttonLeft, 0, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, buttonLeft, 0, SpringLayout.WEST, this);

        for (int i = 0; i < categoryLabels.length; i++) {
            if (i == 0)
                springLayout.putConstraint(SpringLayout.WEST, categoryLabels[i], 0, SpringLayout.EAST, buttonLeft);
            else
                springLayout.putConstraint(SpringLayout.WEST, categoryLabels[i], 0, SpringLayout.EAST, categoryLabels[i - 1]);
            springLayout.putConstraint(SpringLayout.NORTH, categoryLabels[i], 0, SpringLayout.NORTH, this);
        }

        springLayout.putConstraint(SpringLayout.WEST, buttonPlus, 0, SpringLayout.EAST, categoryLabels[categoryLabels.length - 1]);
        springLayout.putConstraint(SpringLayout.NORTH, buttonPlus, 0, SpringLayout.NORTH, this);

        springLayout.putConstraint(SpringLayout.NORTH, buttonRight, 0, SpringLayout.NORTH, this);
        springLayout.putConstraint(SpringLayout.WEST, buttonRight, 0, SpringLayout.EAST, buttonPlus);
        springLayout.putConstraint(SpringLayout.EAST, buttonRight, 0, SpringLayout.EAST, this);
    }

    private void initializeCategories(int numCats, int width) {
        categories = new String[numCats];
        Categories[] cats = Categories.values();
        categoryLabels = new JLabel[NUM_DISPLAYED_CATEGORIES];
        for (int i = 0; i < numCats; i++) {
            categories[i] = cats[i].getText();
            if (i < NUM_DISPLAYED_CATEGORIES) {
                categoryLabels[i] = new JLabel(categories[i], SwingConstants.CENTER);
                categoryLabels[i].setPreferredSize(new Dimension((width - (3 * MIN_BUTTON_WIDTH)) / 4, CategoryPanel.MINIMUM_HEIGHT ));
                //categoryLabels[i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
                categoryLabels[i].setBorder(BorderFactory.createMatteBorder(5, 0, 0, 0, cats[i].getColor()));
                categoryLabels[i].setOpaque(true);
                add(categoryLabels[i]);
                //if (i == 0) {
                //    categoryLabels[i].setBackground(cats[i].getColorSelected());
                //    categoryLabels[i].setForeground(COLOR_TEXT_SELECTED);
                //} else {
                    categoryLabels[i].setBackground(cats[i].getColorSelected());
                    categoryLabels[i].setForeground(COLOR_TEXT_SELECTED);
                //}
                indexLastDisplayedCategory = i;
            }
        }
    }

    //TODO
    //Pass in the amount you wish to change the displayed categories by
    //E.g. "+" button should be 1, "-" should be -1
    private void changeDisplayedCategories(int delta) {
        //Ensure we aren't trying to access elements that don't exist
        if (delta == 0 || indexLastDisplayedCategory + delta > categories.length ||
                indexLastDisplayedCategory + delta < 0)
            return;
        //Else, swap the currently displayed labels

        indexLastDisplayedCategory += delta;

    }

    private void updateLabelFonts() {
        for (JLabel label : categoryLabels) {
            label.setFont(fontLabels);
        }
    }

    private void updateButtonFonts() {
        if (buttonLeft != null) {
            buttonLeft.setFont(fontButtons);
        }
        if (buttonRight != null) {
            buttonRight.setFont(fontButtons);
        }
        if (buttonPlus != null) {
            buttonPlus.setFont(fontButtons);
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
