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

    private String id;
    private String addr;

    private LinkedList<Peer>[] DHT;
    private HashMap<Peer, TreeSet<Message>> hist;
    private HashMap<String, Peer> nicknamesByName;
    private HashMap<Peer, String> nicknamesByPeer;

    private Server server;

    @SuppressWarnings("unchecked")
    public Node() throws IllegalArgumentException, UnknownHostException, IOException {

        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
        this.addr = in.readLine();
        this.id = new BigInteger((DigestUtils.sha1Hex(this.addr+LocalDateTime.now().toString()+Math.random())), 16).toString(2);

        System.out.println(this.addr);

        this.DHT = new LinkedList[160+1];
        for (int i = 0; i <= 160; i++) {
            DHT[i] = new LinkedList<Peer>();
        }
        this.DHT[0].add(new Peer(this.id, this.addr));

        this.hist = new HashMap<Peer, TreeSet<Message>>();
        this.hist.put(null, new TreeSet<Message>());

        this.nicknamesByName = new HashMap<String, Peer>();
        this.nicknamesByPeer = new HashMap<Peer, String>();

        this.server = new Server();
        this.server.start();

    }



    //DHT

    public int hasPeer(int dist) {
        return this.DHT[dist].size();
    }

    public Peer getPeer(int dist) {
        return this.DHT[dist].peek();
    }

    public Peer getPeer(int dist, int idx) {
        return this.DHT[dist].get(idx);
    }

    public LinkedList<Peer>[] getDHT() {
        return this.DHT;
    }

    public boolean addPeer(Peer newPeer) {
        if (newPeer.ping()) {
            int dist = newPeer.getDistance();
            if (!this.DHT[dist].contains(newPeer)) {
                this.DHT[dist].add(newPeer);
                return true;
            }
        }
        return false;
    }

    public boolean removePeer(Peer deadPeer) {
        if (!deadPeer.ping()) {
            int dist = deadPeer.getDistance();
            return this.DHT[dist].remove(deadPeer);
        }
        return false;
    }



    //messaging history

    public String getNickName(Peer peer) {
        return this.nicknamesByPeer.get(peer);
    }

    public Peer getPeerByNickName(String name) {
        return this.nicknamesByName.get(name);
    }

    public void setNickName(Peer peer, String name) {
        this.nicknamesByName.put(name, peer);
        this.nicknamesByPeer.put(peer, name);
    }

    public Set<Peer> getContacts() {
        return Driver.networks.get(Driver.THIS_NETWORK).hist.keySet();
    }

    public void addContact(Peer newContact) {
        this.hist.put(newContact, new TreeSet<Message>());
    }

    public TreeSet<Message> getMessagingHistory(Peer peer) {
        return this.hist.get(peer);
    }

    public void setMessagingHistory(Peer peer, Message message) {
        TreeSet<Message> messages = this.hist.get(peer);
        messages.add(message);
        this.hist.put(peer, messages);
    }



    //server

    public void leave() {
        this.server.stopServer();
    }
    
}
