package jingleheimercalendar;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Roach on 10/24/2014.
 */
public class NavigationPanel extends JPanel {
    private static final int MINIMUM_WIDTH = 450;
    private static final int MINIMUM_HEIGHT = 35;

    public NavigationPanel() {
        setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.BLACK));
        // MatteBorder takes arguments for the width of each border's side.
        this.setBackground(Color.WHITE);
        this.setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
    }
    public Dimension getPreferredSize() {
        return new Dimension(1280,35);
    }
}
