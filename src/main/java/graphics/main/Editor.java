package graphics.main;

import main.*;
import node.*;
import node.rpc.*;

import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

import com.thetransactioncompany.jsonrpc2.*;

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
        Message newMessage = new Message(Main.THIS_PEER, Driver.networks.get(Driver.THIS_NETWORK).getPeer(0), write.getText());
        try {
            Client.message(Main.THIS_PEER.getAddr(), newMessage);
        } catch (SocketTimeoutException|JSONRPC2ParseException e1) {
            App.displayInfo("Failed! Try Again!");
            return;
        } catch (UnknownHostException e1) {
            App.displayInfo("Peer does not exist!");
            return;
        } catch (IOException e1) {
            App.displayInfo("Failed to send message!");
            return;
        }
        Driver.networks.get(Driver.THIS_NETWORK).setMessagingHistory(newMessage.getTo(), newMessage);
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
        //do nothing
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            actionPerformed(new ActionEvent(send, ActionEvent.ACTION_PERFORMED, null));
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //do nothing
        
    }

    
}
