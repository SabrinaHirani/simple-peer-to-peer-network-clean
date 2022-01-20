package node;

import main.*;

import java.io.*;
import java.net.*;
import java.math.*;

public class Peer {

    private String id;
    private String addr;

    public Peer(String id, String addr) {
        this.id = id;
        this.addr = addr;
    }

    public String getId() {
        return this.id;
    }

    public String getAddr() {
        return this.addr;
    }

    public int getDistance() {
        return 160-((new BigInteger(Driver.networks.get(Driver.THIS_NETWORK).getPeer(0).getId(), 2).xor(new BigInteger(this.id, 2))).toString(2).length());
    }

    // public int getDistance(Peer peer) {
    //     return 160-((new BigInteger(this.getId(), 2).xor(new BigInteger(peer.getId(), 2))).toString(2).length());
    // }

    public boolean ping() {
        try {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(this.addr, Driver.THIS_NETWORK), 5000);
                return true;
            }
        } catch (IOException e) {
            return false;
        }
    }
    
}
