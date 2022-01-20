package graphics;

import main.*;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

import graphics.home.*;

public class Home extends JPanel implements ActionListener {

    private static JLabel heading = null;

    private static JScrollPane scrollable = null;
    private static JPanel networks = null;

    private static JButton create = null;
    private static JButton join = null;

    public Home() {

        this.setBackground(Color.decode(App.COLOR_BKG_DARK));
        this.setLayout(null);

        this.add(new Logo());

        heading = new JLabel("NETWORKS", JLabel.LEFT);
        heading.setBounds(300, 160, 200, 50);
        heading.setFont(new Font("Fira Sans", Font.PLAIN, 25));
        heading.setForeground(Color.decode(App.COLOR_HERITAGE));
        this.add(heading);

        networks = new JPanel();
        networks.setBackground(Color.decode(App.COLOR_BKG_LIGHT));
        networks.setLayout(null);

        scrollable = new JScrollPane(networks, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollable.setBounds(300, 210, 700, 300);
        scrollable.setPreferredSize(new Dimension(700, 300));
        scrollable.setBorder(BorderFactory.createLineBorder(Color.decode(App.COLOR_BKG_LIGHT), 5, true));
        this.add(scrollable);

        create = new JButton("create");
        create.setBounds(300, 530, 350, 50);
        create.setFont(new Font("Fira Sans", Font.PLAIN, 15));
        create.setBackground(Color.decode(App.COLOR_HERITAGE));
        create.setForeground(Color.decode(App.COLOR_BKG_DARK));
        create.setFocusPainted(false);
        create.setBorderPainted(false);
        this.add(create);

        create.addActionListener(this);

        join = new JButton("join");
        join.setBounds(650, 530, 350, 50);
        join.setFont(new Font("Fira Sans", Font.PLAIN, 15));
        join.setBackground(Color.decode(App.COLOR_HERITAGE));
        join.setForeground(Color.decode(App.COLOR_BKG_DARK));
        join.setFocusPainted(false);
        join.setBorderPainted(false);
        this.add(join);

        join.addActionListener(this);

        this.setVisible(true);

        Home.reloadNetworkOptions();

    }

    public static void reloadNetworkOptions() {
        networks.removeAll();
        Option.resetNetOptionCount();
        for (String net: Driver.nicknames.keySet()) {
            networks.add(new Option(net));
        }
        networks.setPreferredSize(new Dimension(700, Option.getNetOptionCount()*60));
        networks.revalidate();
        networks.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == create) {
            int opt = JOptionPane.showConfirmDialog(null, new CreateNetworkForm(), "Create Network", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (opt == JOptionPane.OK_OPTION) {
                Driver.createNetwork(CreateNetworkForm.getPort(), CreateNetworkForm.getNickName());
            }
        }

        if (e.getSource() == join) {
            int opt = JOptionPane.showConfirmDialog(null, new JoinNetworkForm(), "Join Network", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (opt == JOptionPane.OK_OPTION) {
                Driver.joinNetwork(JoinNetworkForm.getIP(), JoinNetworkForm.getPort(), JoinNetworkForm.getNickName());
            }
        }
        
    }
    
}
