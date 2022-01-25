package node;

import main.*;

import java.io.*;
import java.net.*;
import java.math.*;

public class Peer {

    private String id;
    private int addr;

    public Peer(String id, int addr) {
        this.id = id;
        this.addr = addr;
    }

    public String getId() {
        return this.id;
    }

    public int getAddr() {
        return this.addr;
    }

    public int getDistance() {
        if ((new BigInteger(Node.getPeer(0).getId(), 2).xor(new BigInteger(this.id, 2))).intValue() == 0) {
            return 0;
        }
        return 161-((new BigInteger(Node.getPeer(0).getId(), 2).xor(new BigInteger(this.id, 2))).toString(2).length());
    }

    // public int getDistance(Peer peer) {
    //     return 160-((new BigInteger(this.getId(), 2).xor(new BigInteger(peer.getId(), 2))).toString(2).length());
    // }

    public boolean ping() {
        try {
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress("0.0.0.0", this.addr), 5000);
                return true;
            }
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    } 

    @Override
    public boolean equals(Object o) {
        return this.id.equals(((Peer) o).getId());
    } 

    public String toString() {
        return String.format("peer:{id:"+this.id+"     addr:"+this.addr+"}");
    }
    
}
