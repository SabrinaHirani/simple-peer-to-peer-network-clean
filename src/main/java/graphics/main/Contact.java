package graphics.main;

import node.*;
import main.*;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import javax.swing.event.*;

import graphics.*;

public class Contact extends JPanel implements MouseInputListener {

    private Peer contact;

    private JLabel name;
    private JLabel id;

    public static int contactCount = 0;

    public Contact(Peer contact) {
        this.contact = contact;

        this.setSize(new Dimension(300, 60));
        this.setPreferredSize(new Dimension(300, 60));
        this.setBounds(25, contactCount*60+10, 300, 60);
        this.setBackground(Color.decode(App.COLOR_BKG_DARK));
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.decode(App.COLOR_EGGPLANT)));
        this.setLayout(null);

        name = new JLabel(Node.getNickName(this.contact));
        name.setBounds(10, 10, 200, 15);
        name.setFont(new Font("Fira Sans Bold", Font.PLAIN, 13));
        name.setForeground(Color.decode(App.COLOR_MARZIPAN));
        this.add(name);

        if (this.contact != null) {
            id = new JLabel(this.contact.getId());
            id.setBounds(10, 30, 300, 10);
            id.setFont(new Font("Fira Sans Light", Font.PLAIN, 10));
            id.setForeground(Color.decode(App.COLOR_BKG_LIGHT));
            this.add(id);
        }

        this.addMouseListener(this);

        this.setVisible(true);

        contactCount++;

    }

    public static void resetContactCount() {
        contactCount = 0;
    }

    public static int getContactCount() {
        return contactCount;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Main.THIS_PEER = this.contact;
        Main.reloadMessages();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.setBackground(Color.decode(App.COLOR_EGGPLANT));
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.setBackground(Color.decode(App.COLOR_BKG_DARK));
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //do nothing
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //do nothing
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //do nothing
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //do nothing
        
    }
    
}
