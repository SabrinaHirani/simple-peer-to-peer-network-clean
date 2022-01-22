package graphics;

import node.*;
import main.*;

import java.awt.*;
import javax.swing.*;

import javax.swing.text.*;

import graphics.main.*;

public class Main extends JPanel {

    public static Peer THIS_PEER = null;

    private static JTextPane messenger = null;
    private static JPanel contacts = null;
    private static JPanel editor = null;
    private static JPanel me = null;

    private static JScrollPane scrollable1 = null;
    private static JScrollPane scrollable2 = null;

    public Main() {

        this.setBackground(Color.decode(App.COLOR_BKG_LIGHT));
        this.setLayout(null);

        messenger = new JTextPane();
        messenger.setBackground(Color.decode(App.COLOR_BKG_LIGHT));
        messenger.setBorder(BorderFactory.createLineBorder(Color.decode(App.COLOR_BKG_LIGHT), 10));
        messenger.setEditable(false);
        messenger.setHighlighter(null);

        scrollable1 = new JScrollPane(messenger, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollable1.setPreferredSize(new Dimension(350, 720));
        scrollable1.setBounds(350, 0, 930, 620);
        this.add(scrollable1);

        contacts = new JPanel();
        contacts.setBackground(Color.decode(App.COLOR_BKG_DARK));
        contacts.setLayout(null);

        scrollable2 = new JScrollPane(contacts, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollable2.setPreferredSize(new Dimension(350, 720));
        scrollable2.setBounds(0, 0, 350, 600);
        this.add(scrollable2);

        editor = new Editor();
        editor.setBounds(350, 620, 930, 100);
        this.add(editor);

        me = new Me();
        me.setBounds(0, 600, 350, 120);
        this.add(me);

        this.setVisible(true);

        Main.reloadContacts();
        Main.reloadMessages();

    }

    public static void reloadContacts() {
        contacts.removeAll();
        Contact.resetContactCount();
        for (Peer contact: Driver.networks.get(Driver.THIS_NETWORK).getContacts()) {
            contacts.add(new Contact(contact));
        }
        contacts.setPreferredSize(new Dimension(300, Contact.getContactCount()*65+10));
        contacts.revalidate();
        contacts.repaint();
    }

    public static void addMessage(Message message) {

        SimpleAttributeSet senderStyles = new SimpleAttributeSet();
        StyleConstants.setForeground(senderStyles, Color.decode(App.COLOR_EGGPLANT));
        StyleConstants.setFontFamily(senderStyles, "Fira Sans Bold");
        StyleConstants.setFontSize(senderStyles, 15);

        SimpleAttributeSet timestampStyles = new SimpleAttributeSet();
        StyleConstants.setForeground(timestampStyles, Color.decode(App.COLOR_MARZIPAN));
        StyleConstants.setFontFamily(timestampStyles, "Fira Sans");
        StyleConstants.setFontSize(timestampStyles, 10);

        SimpleAttributeSet messageStyles = new SimpleAttributeSet();
        StyleConstants.setForeground(messageStyles, Color.decode(App.COLOR_HERITAGE));
        StyleConstants.setFontFamily(messageStyles, "Fira Sans Light");
        StyleConstants.setFontSize(messageStyles, 12);

        try {
            messenger.getDocument().insertString(messenger.getDocument().getLength(), String.format(Driver.networks.get(Driver.THIS_NETWORK).getNickName(message.getFrom())+""), senderStyles);
            messenger.getDocument().insertString(messenger.getDocument().getLength(), String.format("\t"+message.getTimeStampReadable()+"\n"), timestampStyles);
            messenger.getDocument().insertString(messenger.getDocument().getLength(), String.format(message.getMessage()+"\n\n\n"), messageStyles);
        } catch (BadLocationException e) {
            //do nothing
        }
    }

    public static void reloadMessages() {
        messenger.setText("");
        for (Message message: Driver.networks.get(Driver.THIS_NETWORK).getMessagingHistory(THIS_PEER)) {
            Main.addMessage(message);
        }
    }
    
}
