package jingleheimercalendar;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.LayoutManager;

/**
 * Created by Nathan on 10/28/2014.
 */
public class ViewPanel extends JPanel {
    String value;

    ViewPanel() {
        super();
        setBackground(Color.WHITE);
    }

    ViewPanel(LayoutManager Layout) {
        super(Layout);
    }

    public String getStringValue() {
        return value;
    }

    public void refresh(){}
}
