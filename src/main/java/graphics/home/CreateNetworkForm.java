package graphics.home;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

import graphics.*;

public class CreateNetworkForm extends JPanel implements FocusListener {

    private static JTextField portField = null;
    private static JTextField nicknameField = null;

    public CreateNetworkForm() {

        this.setPreferredSize(new Dimension(300, 90));
        this.setBackground(Color.decode(App.COLOR_BKG_DARKER));
        this.setLayout(null);

        JLabel nicknameTag = new JLabel("Nickname: ");
        nicknameTag.setBounds(20, 15, 80, 25);
        nicknameTag.setFont(new Font("Fira Sans", Font.PLAIN, 12));
        nicknameTag.setForeground(Color.decode(App.COLOR_MARZIPAN));
        this.add(nicknameTag);

        nicknameField = new JTextField();
        nicknameField.setBounds(82, 15, 200, 25);
        nicknameField.setBackground(Color.decode(App.COLOR_BKG_DARKER));
        nicknameField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode(App.COLOR_MARZIPAN)));
        this.add(nicknameField);

        nicknameField.addFocusListener(this);

        JLabel portTag = new JLabel("Port: ");
        portTag.setBounds(20, 55, 40, 25);
        portTag.setFont(new Font("Fira Sans", Font.PLAIN, 12));
        portTag.setForeground(Color.decode(App.COLOR_MARZIPAN));
        this.add(portTag);

        portField = new JTextField();
        portField.setBounds(50, 55, 50, 25);
        portField.setBackground(Color.decode(App.COLOR_BKG_DARKER));
        portField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode(App.COLOR_MARZIPAN)));
        this.add(portField);

        portField.addFocusListener(this);

        this.setVisible(true);

    }

    public static String getNickName() {
        return nicknameField.getText();
    }

    public static int getPort() {
        try {
            return Integer.parseInt(portField.getText().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == nicknameField) {
            nicknameField.setBackground(Color.decode(App.COLOR_BKG_DARK));
        }

        if (e.getSource() == portField) {
            portField.setBackground(Color.decode(App.COLOR_BKG_DARK));
        }
        
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == nicknameField) {
            nicknameField.setBackground(Color.decode(App.COLOR_BKG_DARKER));
        }

        if (e.getSource() == portField) {
            portField.setBackground(Color.decode(App.COLOR_BKG_DARKER));
        }
        
    }
    
}
