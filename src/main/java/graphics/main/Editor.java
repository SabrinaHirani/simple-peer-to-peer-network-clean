package graphics.main;

import main.*;
import node.*;
import node.rpc.*;

import java.awt.*;
import javax.swing.*;

import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;

import java.awt.event.*;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import graphics.*;

public class Editor extends JPanel implements ActionListener, FocusListener, KeyListener {

    private static JTextField write = null;
    private static JButton send = null;

    public Editor() {

        this.setBackground(Color.decode(App.COLOR_BKG_LIGHT));
        this.setLayout(null);

        write = new JTextField();
        write.setBounds(20, 0, 800, 30);
        write.setBackground(Color.decode(App.COLOR_EGGPLANT));
        write.setBorder(BorderFactory.createLineBorder(Color.decode(App.COLOR_HERITAGE), 2, true));
        this.add(write);

        write.addFocusListener(this);
        write.addKeyListener(this);

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

        Message newMessage = new Message(Main.THIS_PEER, Node.getPeer(0), write.getText());
        try {
            Client.message(Main.THIS_PEER.getAddr(), newMessage);
        } catch (Exception e1) {
            e1.printStackTrace();
            App.displayInfo("Failed! Try Again!");
            return;
        }
        Node.setMessagingHistory(newMessage.getTo(), newMessage);
        Main.reloadMessages();
        write.setText("");
    }

    @Override
    public void focusGained(FocusEvent e) {
        write.setBorder(BorderFactory.createLineBorder(Color.decode(App.COLOR_HERITAGE), 3, true));
        
    }

    @Override
    public void focusLost(FocusEvent e) {
        write.setBorder(BorderFactory.createLineBorder(Color.decode(App.COLOR_HERITAGE), 2, true));
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //do nothign
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            actionPerformed(new ActionEvent(send, ActionEvent.ACTION_PERFORMED, null));
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    
}
