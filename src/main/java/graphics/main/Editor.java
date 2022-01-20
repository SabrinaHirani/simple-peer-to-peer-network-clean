package graphics.main;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

import graphics.*;

public class Editor extends JPanel implements ActionListener, FocusListener {

    private static JTextField write = null;
    private static JButton send = null;

    public Editor() {

        this.setBackground(Color.decode(App.COLOR_BKG_LIGHT));
        this.setLayout(null);

        write = new JTextField("Write here...");
        write.setBounds(20, 0, 800, 30);
        write.setBackground(Color.decode(App.COLOR_EGGPLANT));
        write.setBorder(BorderFactory.createLineBorder(Color.decode(App.COLOR_HERITAGE), 2, true));
        this.add(write);

        write.addFocusListener(this);

        send = new JButton(new ImageIcon("./media/send.png"));
        send.setBounds(830, 7, 16, 16);
        send.setContentAreaFilled(false);
        send.setBorderPainted(false);
        send.setFocusPainted(false);
        this.add(send);

        send.addActionListener(this);

        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void focusGained(FocusEvent e) {
        write.setBorder(BorderFactory.createLineBorder(Color.decode(App.COLOR_HERITAGE), 3, true));
        
    }

    @Override
    public void focusLost(FocusEvent e) {
        write.setBorder(BorderFactory.createLineBorder(Color.decode(App.COLOR_HERITAGE), 2, true));
        
    }

    
}
