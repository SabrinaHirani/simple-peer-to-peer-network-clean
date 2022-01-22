package node.rpc;

import node.*;
import main.*;

import net.minidev.json.*;
import com.thetransactioncompany.jsonrpc2.*;

import graphics.*;

public class Service {

    public static JSONRPC2Response connect(int port, JSONRPC2Request req) {

        System.out.println("connect");
        
        String id = (String)((JSONObject) req.getNamedParams().get("new")).get("id");
        String addr = (String)((JSONObject) req.getNamedParams().get("new")).get("addr");
        Peer newPeer = new Peer(id, addr);

        Driver.networks.get(port).addPeer(newPeer);

        return new JSONRPC2Response(Driver.networks.get(port).getDHT(), req.getID());

    }

    public static JSONRPC2Response disconnect(int port, JSONRPC2Request req) {

        String id = (String)((JSONObject) req.getNamedParams().get("dead")).get("id");
        String addr = (String)((JSONObject) req.getNamedParams().get("dead")).get("addr");
        Peer deadPeer = new Peer(id, addr);

        if (Driver.networks.get(port).removePeer(deadPeer)) {
            Client.gossip(port, req);
        }
       
        return null;
    }

    public static JSONRPC2Response find(int port, JSONRPC2Request req) {

        String id1 = (String)((JSONObject) req.getNamedParams().get("from")).get("id");
        String addr1 = (String)((JSONObject) req.getNamedParams().get("from")).get("addr");
        Peer from = new Peer(id1, addr1);

        String id2 = (String)((JSONObject) req.getNamedParams().get("target")).get("id");
        String addr2 = (String)((JSONObject) req.getNamedParams().get("target")).get("addr");
        Peer targetPeer = new Peer(id2, addr2);
        
        if (Driver.networks.get(port).getPeer(0).getId().equals(targetPeer.getId())) {
            Client.found(from.getAddr(), port);

            Driver.networks.get(port).setNickName(from, "TO DO");
            Driver.networks.get(port).addContact(from);
            Main.reloadContacts();

            Main.THIS_PEER = from;
            Main.reloadMessages();

            App.displayInfo("Contact added!");

        } else if (Driver.networks.get(port).getPeer(targetPeer.getDistance()) != null) {
            Client.forward(Driver.networks.get(port).getPeer(targetPeer.getDistance()).getAddr(), port, req);
        } else {
            Client.notfound(from.getAddr(), port);
        }

        return new JSONRPC2Response(true, req.getID());

    }

    public static JSONRPC2Response found(int port, JSONRPC2Request req) {

        Peer foundPeer = null;
        try {
            String id = (String)((JSONObject) req.getNamedParams().get("found")).get("id");
            String addr = (String)((JSONObject) req.getNamedParams().get("found")).get("addr");
            foundPeer = new Peer(id, addr);
        } catch (NullPointerException e) {
            //do nothing
        }

        if (foundPeer == null) {

            App.displayInfo("Peer not found!");

        } else {

            String nn = Driver.networks.get(port).getNickName(foundPeer);
            Driver.networks.get(port).setNickName(foundPeer, nn);
            Driver.networks.get(port).addContact(foundPeer);
            Main.reloadContacts();

            Main.THIS_PEER = foundPeer;
            Main.reloadMessages();

            App.displayInfo("Contact added!");

        }

        return null;
    }

    public static JSONRPC2Response message(int port, JSONRPC2Request req) {

        String id1 = (String)((JSONObject) ((JSONObject) req.getNamedParams().get("message")).get("from")).get("id");
        String addr1 = (String)((JSONObject) ((JSONObject) req.getNamedParams().get("message")).get("from")).get("addr");

        String id2 = (String)((JSONObject) ((JSONObject) req.getNamedParams().get("message")).get("to")).get("id");
        String addr2 = (String)((JSONObject) ((JSONObject) req.getNamedParams().get("message")).get("to")).get("addr");

        String text = (String)((JSONObject) req.getNamedParams().get("message")).get("message");

        Message message = new Message(new Peer(id1, addr1), new Peer(id2, addr2), text);
        
        Driver.networks.get(port).setMessagingHistory(new Peer(id1, id2), message);
        Main.reloadMessages();

        return new JSONRPC2Response(true, req.getID());

    }

    public static JSONRPC2Response broadcast(int port, JSONRPC2Request req) {

        String id1 = (String)((JSONObject) ((JSONObject) req.getNamedParams().get("message")).get("from")).get("id");
        String addr1 = (String)((JSONObject) ((JSONObject) req.getNamedParams().get("message")).get("from")).get("addr");

        String id2 = (String)((JSONObject) ((JSONObject) req.getNamedParams().get("message")).get("to")).get("id");
        String addr2 = (String)((JSONObject) ((JSONObject) req.getNamedParams().get("message")).get("to")).get("addr");

        String text = (String)((JSONObject) req.getNamedParams().get("message")).get("message");

        Message message = new Message(new Peer(id1, addr1), new Peer(id2, addr2), text);

        if (!(Driver.networks.get(port).getMessagingHistory(message.getFrom()).pollLast().compareTo(message) == 0)) {

            Driver.networks.get(port).setMessagingHistory(null, message);
            Main.reloadMessages();
            Client.gossip(port, req);

        }

        return null;
    }
    
}
