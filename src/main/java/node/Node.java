package node;

import node.rpc.*;

import java.io.*;
import java.net.*;
import java.math.*;
import java.time.*;
import java.util.*;

import org.apache.commons.codec.digest.DigestUtils;

import main.Driver;

public class Node {

    public static int PORT=7000;

    private static String id;
    private static int addr;

    private static LinkedList<Peer>[] DHT;
    private static HashMap<Peer, TreeSet<Message>> hist;
    private static HashMap<String, Peer> nicknamesByName;
    private static HashMap<Peer, String> nicknamesByPeer;

    private static Server server;

    @SuppressWarnings("unchecked")
    public Node() throws IllegalArgumentException, UnknownHostException, IOException {

        Node.id = new BigInteger((DigestUtils.sha1Hex(LocalDateTime.now().toString()+Math.random())), 16).toString(2);
        Node.addr = PORT;

        Node.DHT = new LinkedList[160+1];
        for (int i = 0; i <= 160; i++) {
            DHT[i] = new LinkedList<Peer>();
        }
        Node.DHT[0].add(new Peer(Node.id, Node.addr));

        Node.hist = new HashMap<Peer, TreeSet<Message>>();
        Node.hist.put(null, new TreeSet<Message>());

        Node.nicknamesByName = new HashMap<String, Peer>();
        Node.nicknamesByPeer = new HashMap<Peer, String>();
        Node.setNickName(Node.getPeer(0), "Me");

        Node.server = new Server();
        Node.server.start();

    }



    //DHT

    public static int hasPeer(int dist) {
        return Node.DHT[dist].size();
    }

    public static Peer getPeer(int dist) {
        return Node.DHT[dist].peek();
    }

    public static Peer getPeer(int dist, int idx) {
        return Node.DHT[dist].get(idx);
    }

    public static LinkedList<Peer>[] getDHT() {
        return Node.DHT;
    }

    public static boolean addPeer(Peer newPeer) {
        if (newPeer.ping()) {
            int dist = newPeer.getDistance();
            if (!Node.DHT[dist].contains(newPeer)) {
                Node.DHT[dist].add(newPeer);
                return true;
            }
        }
        return false;
    }

    public static boolean removePeer(Peer deadPeer) {
        if (!deadPeer.ping()) {
            int dist = deadPeer.getDistance();
            return Node.DHT[dist].remove(deadPeer);
        }
        return false;
    }



    //messaging history

    public static String getNickName(Peer peer) {
        return Node.nicknamesByPeer.get(peer);
    }

    public static Peer getPeerByNickName(String name) {
        return Node.nicknamesByName.get(name);
    }

    public static void setNickName(Peer peer, String name) {
        Node.nicknamesByName.put(name, peer);
        Node.nicknamesByPeer.put(peer, name);
    }

    public static Set<Peer> getContacts() {
        return Node.hist.keySet();
    }

    public static void addContact(Peer newContact) {
        Node.hist.put(newContact, new TreeSet<Message>());
    }

    public static TreeSet<Message> getMessagingHistory(Peer peer) {
        return Node.hist.get(peer);
    }

    public static void setMessagingHistory(Peer peer, Message message) {
        TreeSet<Message> messages = null;
        try {
            messages = Node.hist.get(peer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        messages.add(message);
        Node.hist.put(peer, messages);
    }



    //server

    public static void leave() {
        Node.server.stopServer();
    }
    
}
