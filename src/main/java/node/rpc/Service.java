package node.rpc;

import node.*;
import main.*;

import java.util.*;

import javax.swing.JOptionPane;

import net.minidev.json.JSONObject;
import com.thetransactioncompany.jsonrpc2.*;

import graphics.App;
import graphics.Main;
import graphics.main.ConfirmContactForm;

public class Service {

    public static JSONRPC2Response connect(JSONRPC2Request req) {

        try {
            String id = (String)((JSONObject)req.getNamedParams().get("new")).get("id");
        int addr = ((Long)((JSONObject)req.getNamedParams().get("new")).get("addr")).intValue();
        Peer newPeer = new Peer(id, addr);
        Node.addPeer(newPeer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new JSONRPC2Response(Node.getDHT(), req.getID());

    }

    public static JSONRPC2Response disconnect(JSONRPC2Request req) {

        String id = (String)((JSONObject)req.getNamedParams().get("dead")).get("id");
        int addr = ((Long)((JSONObject)req.getNamedParams().get("new")).get("addr")).intValue();

        Peer deadPeer = new Peer(id, addr);

        if (Node.removePeer(deadPeer)) {
            Client.gossip(req);
        }
       
        return null;
    }

    public static JSONRPC2Response find(JSONRPC2Request req) {

        String idfr = (String)((JSONObject)req.getNamedParams().get("from")).get("id");
        int addrfr = ((Long)((JSONObject)req.getNamedParams().get("from")).get("addr")).intValue();
        Peer from = new Peer(idfr, addrfr);

        String idtar = (String)((JSONObject)req.getNamedParams().get("target")).get("id");
        int addrtar = ((Long)((JSONObject)req.getNamedParams().get("target")).get("addr")).intValue();
        Peer targetPeer = new Peer(idtar, addrtar);
        
        if (Node.getPeer(0).getId().equals(targetPeer.getId())) {
            int opt = JOptionPane.showConfirmDialog(null, new ConfirmContactForm(), "Confirm Contact", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (opt == JOptionPane.OK_OPTION) {
                Node.setNickName(from, ConfirmContactForm.getNickName());
            Node.addContact(from);
            Main.reloadContacts();

            Main.THIS_PEER = from;
            Main.reloadMessages();

            }
            Client.found(from.getAddr());
        } else if (Node.getPeer(targetPeer.getDistance()) != null) {
            Client.forward(Node.getPeer(targetPeer.getDistance()).getAddr(), req);
        } else {
            Client.notfound(from.getAddr());
        }

        return new JSONRPC2Response(true, req.getID());

    }

    public static JSONRPC2Response found(JSONRPC2Request req) {

        Peer foundPeer = null;
        try {
            String id = (String)((JSONObject)req.getNamedParams().get("found")).get("id");
            int addr = ((Long)((JSONObject)req.getNamedParams().get("found")).get("addr")).intValue();

            foundPeer = new Peer(id, addr);
        } catch (NullPointerException e) {
            //do nothgin
        }

        if (foundPeer == null) {

            App.displayInfo("Peer not found!");

        } else {

            String nn = Node.getNickName(foundPeer);
            Node.setNickName(foundPeer, nn);
            Node.addContact(foundPeer);
            Main.reloadContacts();

            Main.THIS_PEER = foundPeer;
            Main.reloadMessages();
        }

        return null;
    }

    public static JSONRPC2Response message(JSONRPC2Request req) {
        
        String fromid = (String)((JSONObject)((JSONObject)req.getNamedParams().get("message")).get("from")).get("id");
        int fromaddr = ((Long)((JSONObject)((JSONObject)req.getNamedParams().get("message")).get("from")).get("addr")).intValue();
        String toid = (String)((JSONObject)((JSONObject)req.getNamedParams().get("message")).get("to")).get("id");
        int toaddr = ((Long)((JSONObject)((JSONObject)req.getNamedParams().get("message")).get("to")).get("addr")).intValue();
        String messagestring = (String)((JSONObject)req.getNamedParams().get("message")).get("message");
        Message message = new Message(new Peer(toid, toaddr), new Peer(fromid, fromaddr), messagestring);

        Node.setMessagingHistory(message.getFrom(), message);

        Main.reloadMessages();

        return new JSONRPC2Response(true, req.getID());

    }

    public static JSONRPC2Response broadcast(JSONRPC2Request req) {

        String fromid = (String)((JSONObject)((JSONObject)req.getNamedParams().get("message")).get("from")).get("id");
        int fromaddr = ((Long)((JSONObject)((JSONObject)req.getNamedParams().get("message")).get("from")).get("addr")).intValue();
        String toid = (String)((JSONObject)((JSONObject)req.getNamedParams().get("message")).get("to")).get("id");
        int toaddr = ((Long)((JSONObject)((JSONObject)req.getNamedParams().get("message")).get("to")).get("addr")).intValue();
        String messagestring = (String)((JSONObject)req.getNamedParams().get("message")).get("message");
        Message message = new Message(new Peer(fromid, fromaddr), new Peer(toid, toaddr), messagestring);

        if (!(Node.getMessagingHistory(message.getFrom()).pollLast().compareTo(message) == 0)) {

            Node.setMessagingHistory(null, message);
            Main.reloadMessages();
            Client.gossip(req);

        }

        return null;
    }
    
}
