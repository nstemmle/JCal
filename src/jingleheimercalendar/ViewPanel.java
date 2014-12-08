package jingleheimercalendar;

import javax.swing.JPanel;
import java.awt.LayoutManager;

/**
 * Created by Roach on 10/28/2014.
 */
public class ViewPanel extends JPanel {
    String value;
    ViewPanel() {
        super();
    }

    ViewPanel(LayoutManager Layout) {
        super(Layout);
    }

    public String getStringValue() {
        return value;
    }

    public void refresh(){}
}
