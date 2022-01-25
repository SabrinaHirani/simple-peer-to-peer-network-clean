package graphics.main;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

import graphics.*;

public class AddContactForm extends JPanel implements FocusListener {

    private static JTextField nicknameField = null;
    private static JTextField idField = null;

    public AddContactForm() {

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

        JLabel idTag = new JLabel("Id: ");
        idTag.setBounds(20, 55, 80, 25);
        idTag.setFont(new Font("Fira Sans", Font.PLAIN, 12));
        idTag.setForeground(Color.decode(App.COLOR_MARZIPAN));
        this.add(idTag);

        idField = new JTextField();
        idField.setBounds(38, 55, 245, 25);
        idField.setBackground(Color.decode(App.COLOR_BKG_DARKER));
        idField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode(App.COLOR_MARZIPAN)));
        this.add(idField);

        idField.addFocusListener(this);

        this.setVisible(true);

    }

    public static String getNickName() {
        return nicknameField.getText();
    }

    public static String getId() {
        return idField.getText().trim();
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == nicknameField) {
            nicknameField.setBackground(Color.decode(App.COLOR_BKG_DARK));
        }
        
        if (e.getSource() == idField) {
            idField.setBackground(Color.decode(App.COLOR_BKG_DARK));
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == nicknameField) {
            nicknameField.setBackground(Color.decode(App.COLOR_BKG_DARKER));
        }
        
        if (e.getSource() == idField) {
            idField.setBackground(Color.decode(App.COLOR_BKG_DARKER));
        }
        
    }
    
}
