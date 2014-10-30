package jingleheimercalendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

    private Font fontLabels;
    private int fontSizeLabels = 48;
    private String fontPathLabels = JingleheimerCalendar.PATH_FONT_KALINGA;

    private Font fontButtons;
    private int fontSizeButtons = 30;
    private String fontPathButtons = JingleheimerCalendar.PATH_FONT_KALINGA;

    private JButton buttonLeft;
    private JButton buttonRight;
    private JLabel monthLabel;

    YearHeader(int width){
        setPreferredSize(new Dimension(width, MINIMUM_HEIGHT));

        buttonLeft = new JButton("<");
        buttonLeft.setBackground(DEFAULT_COMPONENT_BACKGROUND);
        buttonLeft.setBorderPainted(false);
        buttonLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        buttonRight = new JButton(">");
        buttonRight.setBackground(DEFAULT_COMPONENT_BACKGROUND);
        buttonRight.setBorderPainted(false);
        buttonRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
