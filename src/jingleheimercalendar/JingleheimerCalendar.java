/*
Mockup layout for Calendar application
If you hate this we don't have to use any of it - just trying to get the ball rolling
*/

package jingleheimercalendar;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;

public class JingleheimerCalendar extends JFrame {
    //Constants for preferred/target screen width and height
    public static final int PREFERRED_WIDTH = 1280;
    public static final int PREFERRED_HEIGHT = 720;

    //Constants for preventing user resizing screen too small
    // these will have to adjusted/tested
    public static final int MINIMUM_WIDTH = 800;
    public static final int MINIMUM_HEIGHT = 450;

    //These might need to be accessed from other methods so moving them outside constructor scope
    //Can re-insert into constructor scope if they don't need to be accessed again
    private Rectangle workableBounds;
    private boolean maxWidth = false;
    private boolean maxHeight = false;

    // Class variables
    private NavigationPanel mNavigationPanel;
    private CategoryPanel mCategoryPanel;
    private JLabel topLabel, bottomLabel, viewLabel;
    private Font sans, customFont;
    //static ResizableBorder mResizableBorder;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private static void createAndShowGUI() {
        new JingleheimerCalendar();
        // Forgive my terrible names
            //@nstemmle - Renamed and forgiven
    }
    
    public JingleheimerCalendar() {
        //@nstemmle
        //Get optimal initial bounds of window while accomadating available screen real estate where
        // initialWidth preferred is 1280 px (target)
        // initialHeight preferred is 720 px (target)
        // Feel free to adjust these parameters
        // This will currently set the initial size of the window to be the full workable screen real estate
        // e.g. If screen is 1280x720 it will be set to 1280x~680 or so
        // this means the application is essentially full screen without actually being maximized

        //Taken from the following stackoverflow
        //http://stackoverflow.com/questions/1936566/how-do-you-get-the-screen-width-in-java/8101318#8101318

        workableBounds = getScreenBounds(null);
        int initialHeight, initialWidth;
        if (workableBounds.width <= PREFERRED_WIDTH) {
            maxWidth = true;
            initialWidth = workableBounds.width;
        } else
            initialWidth = JingleheimerCalendar.PREFERRED_WIDTH;
        if (workableBounds.height <= PREFERRED_HEIGHT) {
            maxHeight = true;
            initialHeight = workableBounds.height;
        } else
            initialHeight = PREFERRED_HEIGHT;

        this.setSize(initialWidth, initialHeight);
        //Output for testing
        System.out.println("workable bounds x: " + workableBounds.width + "\nworkable bounds y: " + workableBounds.height);
        System.out.println("init width: " + initialWidth + "\ninit height: " + initialHeight);

        // JFrame dimensions include the operating system's borders.
        // The actual size of the JFrame on windows is 1296x758, but this is left out for now because
        // I'm sure it's different on mac and the frame stretches to fit the components with pack() anyway
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Jingleheimer Dingleheimer");
        
        mNavigationPanel = new NavigationPanel();
        mCategoryPanel = new CategoryPanel();
        PlaceholderView view = new PlaceholderView();

        // Separate JPanel classes for top and bottom because they have different borders.
        
        view.setLayout(new GridBagLayout());
        // default GridBag just for centering the label.
        
        topLabel    = new JLabel("Top navigation area (Swing default font)");
        viewLabel   = new JLabel("Placeholder view area (Nexa Bold 60pt)");
        bottomLabel = new JLabel("Bottom navigation area (Arial Bold 20pt)");
        
        try {
            customFont = loadFont();
            // I'm not sure the following two lines are completely necessary.
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (FontFormatException|IOException e) {
            // If you see times at all, something is wrong.
            System.err.println("Error loading custom font. Using Times.");
            customFont = new Font("Times New Roman", Font.BOLD,60);
        }
        
        sans = new Font("Arial", Font.BOLD,20);
        // test reference font for the bottom panel
        
        //topLabel is Swing default just for reference, so I don't change it
        viewLabel.setFont(customFont);
        bottomLabel.setFont(sans);
        
        mNavigationPanel.add(topLabel);
        mCategoryPanel.add(bottomLabel);
        view.add(viewLabel);

        
        BasePanel base = new BasePanel();
        base.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
                
        base.add(mNavigationPanel);
        base.add(view);
        base.add(mCategoryPanel);
        
        this.add(base);
        pack();
        this.setVisible(true);

        //If the application window size is set to the maximum workable bounds, then maximize the application
        //Take from the following stackoverflow
        //http://stackoverflow.com/questions/479523/java-swing-maximize-window
        if (maxWidth && maxHeight) {
            this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        }
    }
    
    private Font loadFont() throws FontFormatException, IOException  {
        URL fontUrl = getClass().getResource("/font/Nexa_Bold.ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
        font = font.deriveFont(Font.PLAIN,60);
        return font;
    }

    //@nstemmle
    //This will have enough functionality that it should be its own class, I think
    //Do its fields need to be accessed directly?
    /*class TopNavigation extends JPanel {
        public TopNavigation() {
            setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.BLACK));
            // MatteBorder takes arguments for the width of each border's side.
            this.setBackground(Color.WHITE);
            this.setMinimumSize(new Dimension(600,35));
            this.setMaximumSize(new Dimension());
        }
        public Dimension getPreferredSize() {
            return new Dimension(1280,35);
        }
    }
    */
    /*
    class BottomNavigation extends JPanel {
        public BottomNavigation() {
            setBorder(BorderFactory.createMatteBorder(2,0,0,0,Color.BLACK));
            // MatteBorder takes arguments for the width of each border's side.
            this.setBackground(Color.WHITE);
        }
        public Dimension getPreferredSize() {
            return new Dimension(1280,35);
        }
    }*/

    class PlaceholderView extends JPanel {
        public PlaceholderView() {
            this.setBackground(Color.WHITE);
        }
        public Dimension getPreferredSize() {
            return new Dimension(1280,650);
        }
    }

    class BasePanel extends JPanel {
        public BasePanel(){
            this.setBackground(Color.WHITE);
        }
        public Dimension getPreferredSize() {
            return new Dimension(1280,720);
        }
    }

    public static Rectangle getScreenBounds(Window currentWindow) {
        Rectangle screenBounds;

        //Get the operating system insets (dock, taskbar, etc.)
        Insets screenInsets = getScreenInsets(currentWindow);

        //If our window has been instantiated
        if (currentWindow != null) {
            screenBounds = currentWindow.getGraphicsConfiguration().getBounds();
        } else {
            screenBounds = GraphicsEnvironment
                    .getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration()
                    .getBounds();
        }

        //Take into account operating system insets (dock, taskbar, etc.) in usable bounds
        screenBounds.x += screenInsets.left;
        screenBounds.y += screenInsets.top;
        screenBounds.width -= screenInsets.right;
        screenBounds.height -= screenInsets.bottom;
        return screenBounds;
    }

    public static Insets getScreenInsets(Window currentWindow) {
        Insets screenInsets;

        if (currentWindow != null) {
            screenInsets = currentWindow.getToolkit().getScreenInsets(currentWindow.getGraphicsConfiguration());
        } else { //Pull system defaults
            screenInsets = Toolkit.getDefaultToolkit()
                    .getScreenInsets(GraphicsEnvironment
                            .getLocalGraphicsEnvironment()
                            .getDefaultScreenDevice()
                            .getDefaultConfiguration());
        }
        return screenInsets;
    }
}
