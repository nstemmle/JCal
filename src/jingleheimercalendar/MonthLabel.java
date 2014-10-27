package jingleheimercalendar;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Roach on 10/25/2014.
 */
public class MonthLabel extends JLabel {
    int width;
    int height;

    MonthLabel() {
        super();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width,height);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(width,height);
    }
}
