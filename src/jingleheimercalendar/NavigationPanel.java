package jingleheimercalendar;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Roach on 10/24/2014.
 */
public class NavigationPanel extends JPanel {
    public static final int MINIMUM_WIDTH = 800;
    public static final int MINIMUM_HEIGHT = 35;

    public NavigationPanel(int width) {
        setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.BLACK));
        this.setBackground(Color.WHITE);
        this.setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
        this.setPreferredSize(new Dimension(width, MINIMUM_HEIGHT));
    }
}
