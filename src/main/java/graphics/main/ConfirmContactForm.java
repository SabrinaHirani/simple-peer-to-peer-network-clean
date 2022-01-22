package graphics.main;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

import graphics.*;

public class ConfirmContactForm extends JPanel implements FocusListener {

    private static JTextField nicknameField = null;

    public ConfirmContactForm() {

        this.setPreferredSize(new Dimension(300, 90));
        this.setBackground(Color.decode(App.COLOR_BKG_DARKER));
        this.setLayout(null);

        JLabel heading = new JLabel("Someone want to talk to you!");
        heading.setBounds(20, 15, 280, 25);
        heading.setFont(new Font("Fira Sans", Font.PLAIN, 12));
        heading.setForeground(Color.decode(App.COLOR_MARZIPAN));
        this.add(heading);

        JLabel nicknameTag = new JLabel("Nickname: ");
        nicknameTag.setBounds(20, 55, 80, 25);
        nicknameTag.setFont(new Font("Fira Sans", Font.PLAIN, 12));
        nicknameTag.setForeground(Color.decode(App.COLOR_MARZIPAN));
        this.add(nicknameTag);

        nicknameField = new JTextField();
        nicknameField.setBounds(82, 55, 200, 25);
        nicknameField.setBackground(Color.decode(App.COLOR_BKG_DARKER));
        nicknameField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode(App.COLOR_MARZIPAN)));
        this.add(nicknameField);

        nicknameField.addFocusListener(this);

        this.setVisible(true);

    }

    public static String getNickName() {
        return nicknameField.getText();
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == nicknameField) {
            nicknameField.setBackground(Color.decode(App.COLOR_BKG_DARK));
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == nicknameField) {
            nicknameField.setBackground(Color.decode(App.COLOR_BKG_DARKER));
        }
        
    }
    
}
