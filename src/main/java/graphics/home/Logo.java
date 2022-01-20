package graphics.home;

import java.awt.*;
import javax.swing.*;

import graphics.*;

public class Logo extends JPanel {

    JLabel logo = null;
    JLabel name = null; 
    JLabel ver = null;

    public Logo() {

        this.setSize(new Dimension(300, 170));
        this.setPreferredSize(new Dimension(300, 170));
        this.setBackground(Color.decode(App.COLOR_BKG_DARK));
        this.setLayout(null);

        Image ico = new ImageIcon("./media/logo.png").getImage().getScaledInstance(90, 100, Image.SCALE_SMOOTH);
        logo = new JLabel(new ImageIcon(ico));
        logo.setBounds(20, 50, 100, 100);
        this.add(logo);

        name = new JLabel("Custard");
        name.setBounds(120, 80, 200, 70);
        name.setFont(new Font("Grand Hotel", Font.PLAIN, 65));
        name.setForeground(Color.decode(App.COLOR_MARZIPAN));
        this.add(name);

        ver = new JLabel("v1.0.0");
        ver.setBounds(240, 150, 50, 20);
        ver.setFont(new Font("Fira Sans", Font.PLAIN, 15));
        ver.setForeground(Color.decode(App.COLOR_EGGPLANT));
        this.add(ver);

        this.setVisible(true);
        
    }
    
}
