package node.rpc;

import node.*;
import main.*;

import com.thetransactioncompany.jsonrpc2.*;

public class Service {

    public static JSONRPC2Response connect(JSONRPC2Request req) {
        
        Peer newPeer = (Peer)(req.getNamedParams().get("new"));
        Driver.networks.get(Driver.THIS_NETWORK).addPeer(newPeer);

        return new JSONRPC2Response(Driver.networks.get(Driver.THIS_NETWORK).getDHT(), req.getID());

    }

    public static JSONRPC2Response disconnect(JSONRPC2Request req) {

        Peer deadPeer = (Peer)(req.getNamedParams().get("dead"));

        if (Driver.networks.get(Driver.THIS_NETWORK).removePeer(deadPeer)) {
            Client.gossip(req);
        }
       
        return null;
    }

    public static JSONRPC2Response find(JSONRPC2Request req) {

        Peer from = (Peer)(req.getNamedParams().get("from"));
        Peer targetPeer = (Peer)(req.getNamedParams().get("dead"));
        
        if (Driver.networks.get(Driver.THIS_NETWORK).getPeer(0).getId().equals(targetPeer.getId())) {
            Client.found(from.getAddr());
        } else if (Driver.networks.get(Driver.THIS_NETWORK).getPeer(targetPeer.getDistance()) != null) {
            Client.forward(Driver.networks.get(Driver.THIS_NETWORK).getPeer(targetPeer.getDistance()).getAddr(), req);
        } else {
            Client.notfound(from.getAddr());
        }

        return new JSONRPC2Response(true, req.getID());

    }

    public static JSONRPC2Response found(JSONRPC2Request req) {

        //TODO alert found

        //TODO add peer w/ null value to hist

        //TODO reload main screen

        return null;
    }

    public static JSONRPC2Response notfound(JSONRPC2Request req) {

        //TODO alert failed: peer not found

        return null;
    }

    public static JSONRPC2Response message(JSONRPC2Request req) {
        
        //TODO save message by sender

        //TODO reload main screen

        return new JSONRPC2Response(true, req.getID());

    }

    public static JSONRPC2Response broadcast(JSONRPC2Request req) {

        //TODO check if message previously received

        //if no:

        //TODO save message w/ null key

        //TODO reload main screen

        //TODO gossip req

        return null;
    }
    
}
