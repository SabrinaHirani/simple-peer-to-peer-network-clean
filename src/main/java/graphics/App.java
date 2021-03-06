package graphics;

import java.io.*;
import java.awt.*;
import javax.swing.*;

public class App {

    private static JFrame app = null;
    private static JPanel home = null;
    private static JPanel main = null;

    public static final String COLOR_BONFIRE = "#ff9e33";
    public static final String COLOR_CARAMEL = "#b87700";
    public static final String COLOR_EGGPLANT = "#6e575e";
    public static final String COLOR_HERITAGE = "#94717c";
    public static final String COLOR_MARZIPAN = "#eac683";
    public static final String COLOR_BKG_LIGHT = "#eee8d5";
    public static final String COLOR_BKG_DARK = "#403739";
    public static final String COLOR_BKG_DARKER = "#302a2c";

    public App() {
        
        app = new JFrame("Custard");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        UIManager.put("Panel.background", Color.decode(App.COLOR_BKG_DARKER));
        UIManager.put("OptionPane.background", Color.decode(App.COLOR_BKG_DARKER));
        UIManager.put("Button.defaultFillColor", Color.DARK_GRAY);
        UIManager.put("Button.defaultFillColorClick", Color.BLACK);
        UIManager.put("Button.defaultFillColorRollOver", Color.decode(App.COLOR_MARZIPAN));
        UIManager.put("Button.border", false);
        UIManager.put("TextField.selectionBackground", Color.decode(App.COLOR_CARAMEL));

        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./media/Fira Sans/FiraSans-Light.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./media/Fira Sans/FiraSans-Regular.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./media/Fira Sans/FiraSans-Bold.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./media/Grand Hotel/GrandHotel-Regular.ttf")));
         } catch (IOException|FontFormatException e) {
            //do nothing
         }

        home = new Home();
        app.add(home);
        
        app.pack();
        app.setVisible(true);

    }

    public static void displayInfo(String info) {
        JOptionPane.showOptionDialog(null, new JLabel(info, JLabel.CENTER), "Info", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
    }

    public static void enterNetwork() {
        main = new Main();
        app.add(main);
        app.remove(home);

        app.revalidate();
        app.repaint();
    }

    public static void exitNetwork() {
        app.add(home);
        app.remove(main);
        
        app.revalidate();
        app.repaint();
    }
    
}
