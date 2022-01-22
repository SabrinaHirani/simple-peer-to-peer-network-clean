package main;

import node.*;
import node.rpc.*;
import graphics.*;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

import com.github.weisj.darklaf.*;
import com.github.weisj.darklaf.theme.*;
import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;

public class Driver 
{

    public static int THIS_NETWORK = -1;

    public static HashMap<Integer, Node> networks;
    public static HashMap<String, Integer> nicknames;

    public static void createNetwork(int port, String nn) {
        THIS_NETWORK = port;

        try {
            networks.put(port, new Node());
        } catch (IllegalArgumentException e) {
            THIS_NETWORK = -1;
            App.displayInfo("Invalid port!");
            return;
        } catch (UnknownHostException e) {
            THIS_NETWORK = -1;
            App.displayInfo("Network not found!");
            return;
        } catch (IOException e) {
            THIS_NETWORK = -1;
            App.displayInfo("Could not start server!");
            return;
        }

        nicknames.put(nn, port);
        Driver.networks.get(Driver.THIS_NETWORK).setNickName(null, nn);

        Home.reloadNetworkOptions();
        App.displayInfo("Network Added!");
        App.enterNetwork();
    }

    public static void joinNetwork(String ip, int port, String nn) {
        THIS_NETWORK = port;
        
        try {
            networks.put(port, new Node());
        } catch (IllegalArgumentException e) {
            THIS_NETWORK = -1;
            App.displayInfo("Invalid port!");
            return;
        } catch (UnknownHostException e) {
            THIS_NETWORK = -1;
            App.displayInfo("Network not found!");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            THIS_NETWORK = -1;
            App.displayInfo("Could not start server!");
            return;
        }

        try {
            Client.connect(ip);
        } catch (SocketTimeoutException|JSONRPC2ParseException e) {
            THIS_NETWORK = -1;
            App.displayInfo("Failed! Try Again!");
            networks.remove(port);
            return;
        } catch (UnknownHostException e) {
            THIS_NETWORK = -1;
            App.displayInfo("Peer does not exist!");
            networks.remove(port);
            return;
        } catch (IOException e) {
            THIS_NETWORK = -1;
            App.displayInfo("Failed to connect to network!");
            networks.remove(port);
            return;
        }

        nicknames.put(nn, port);
        Driver.networks.get(Driver.THIS_NETWORK).setNickName(null, nn);

        Home.reloadNetworkOptions();
        App.displayInfo("Network Added!");
        App.enterNetwork();
    }

    public static void leaveNetwork(String nn) {
        Driver.networks.get(Driver.nicknames.get(nn)).leave();
        Client.disconnect(Driver.nicknames.get(nn));
        Driver.networks.remove(Driver.nicknames.remove(nn));
        Home.reloadNetworkOptions();
        App.displayInfo("Network Removed!");
    }
    
    public static void main( String[] args )
    {

        networks = new HashMap<Integer, Node>();
        nicknames = new HashMap<String, Integer>();

        SwingUtilities.invokeLater(() -> {
            LafManager.install(new DarculaTheme());
            new App();
        });
        
    }
}
