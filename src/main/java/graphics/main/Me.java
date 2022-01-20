package graphics.main;

import main.*;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import javax.swing.event.*;
import java.awt.datatransfer.*;

import graphics.*;

public class Me extends JPanel implements ActionListener, MouseInputListener {

    private static JLabel me = null;
    private static JLabel id = null;

    private static JButton add = null;
    private static JButton exit = null;

    public Me() {

        this.setBackground(Color.decode(App.COLOR_BKG_DARKER));
        this.setLayout(null);

        this.addMouseListener(this);

        me = new JLabel("Me");
        me.setBounds(260, 25, 50, 30);
        me.setFont(new Font("Fira Sans Bold", Font.PLAIN, 20));
        me.setForeground(Color.decode(App.COLOR_BKG_LIGHT));
        this.add(me);

        id = new JLabel("101101010101010101010101010000011111111111...");
        id.setBounds(100, 55, 200, 10);
        id.setFont(new Font("Fira Sans Light", Font.PLAIN, 10));
        id.setForeground(Color.decode(App.COLOR_MARZIPAN));
        this.add(id);

        add = new JButton(new ImageIcon("./media/add.png"));
        add.setBounds(40, 20, 24, 24);
        add.setContentAreaFilled(false);
        add.setBorderPainted(false);
        add.setFocusPainted(false);
        this.add(add);

        add.addActionListener(this);

        exit = new JButton(new ImageIcon("./media/exit.png"));
        exit.setBounds(40, 50, 24, 24);
        exit.setContentAreaFilled(false);
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        this.add(exit);

        exit.addActionListener(this);

        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == add) {
            int opt = JOptionPane.showConfirmDialog(null, new AddContactForm(), "Add Contact", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (opt == JOptionPane.OK_OPTION) {
                //do something
            }
        }

        if (e.getSource() == exit) {
            App.exitNetwork();
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(Driver.networks.get(Driver.THIS_NETWORK).getPeer(0).getId()), null);
        App.displayInfo("User ID copied!");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}
