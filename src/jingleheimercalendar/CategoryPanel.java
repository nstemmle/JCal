package jingleheimercalendar;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
/**
 * Created by Roach on 10/24/2014.
 */
public class CategoryPanel extends JPanel {
    private static final int MINIMUM_WIDTH = 450;
    private static final int MINIMUM_HEIGHT = 35;

    public CategoryPanel() {
        setBorder(BorderFactory.createMatteBorder(2,0,0,0, Color.BLACK));
        //setBorder(JingleheimerCalendar.mResizableBorder);
        // MatteBorder takes arguments for the width of each border's side.
        this.setBackground(Color.WHITE);
        this.setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
    }
    public Dimension getPreferredSize() {
        return new Dimension(1280,35);
    }
}
