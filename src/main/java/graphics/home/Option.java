package graphics.home;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import javax.swing.event.*;

import graphics.*;
import main.Driver;

public class Option extends JPanel implements ActionListener, MouseInputListener {

    private String net;

    private JLabel network = null;
    private JButton enter = null;
    private JButton leave = null;

    private static int netOptionCount = 0;

    public Option(String net) {
        this.net = net;

        this.setSize(new Dimension(650, 50));
        this.setPreferredSize(new Dimension(650, 50));
        this.setBounds(20, netOptionCount*60, 650, 50);
        this.setBackground(Color.decode(App.COLOR_BKG_LIGHT));
        this.setLayout(null);

        this.addMouseListener(this);

        network = new JLabel(this.net);
        network.setBounds(275, 18, 100, 15);
        network.setFont(new Font("Fira Sans", Font.PLAIN, 15));
        network.setForeground(Color.decode(App.COLOR_BONFIRE));
        this.add(network);

        enter = new JButton();
        enter.setBounds(560, 16, 16, 16);
        enter.setContentAreaFilled(false);
        enter.setBorderPainted(false);
        enter.setFocusPainted(false);
        this.add(enter);

        enter.addActionListener(this);
        enter.addMouseListener(this);

        leave = new JButton();
        leave.setBounds(600, 16, 16, 16);
        leave.setContentAreaFilled(false);
        leave.setBorderPainted(false);
        leave.setFocusPainted(false);
        this.add(leave);

        leave.addActionListener(this);
        leave.addMouseListener(this);

        netOptionCount++;

    }

    public static void resetNetOptionCount() {
        netOptionCount = 0;
    }

    public static int getNetOptionCount() {
        return netOptionCount;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enter) {
            App.displayInfo("IP: "+Driver.networks.get(Driver.nicknames.get(net)).getPeer(0).getAddr()+"     PORT: "+Driver.nicknames.get(net));
        }

        if (e.getSource() == leave) {
            Driver.leaveNetwork(net);
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        App.enterNetwork();
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //do nothing
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //do nothing
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        network.setForeground(Color.decode(App.COLOR_BKG_LIGHT));
        enter.setIcon(new ImageIcon("./media/enter.png"));
        leave.setIcon(new ImageIcon("./media/leave.png"));
        this.setBackground(Color.decode(App.COLOR_HERITAGE));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        network.setForeground(Color.decode(App.COLOR_BONFIRE));
        enter.setIcon(new ImageIcon());
        leave.setIcon(new ImageIcon());
        this.setBackground(Color.decode(App.COLOR_BKG_LIGHT));
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
