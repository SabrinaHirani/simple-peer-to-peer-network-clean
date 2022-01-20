package node.rpc;

import node.*;
import main.*;

import java.io.*;
import java.net.*;
import java.util.*;

import com.thetransactioncompany.jsonrpc2.*;

public class Client {

    private static int requestIdCount;

    @SuppressWarnings("unchecked")
    public static void connect(String addr) {

        String rpc = "connect";
        String id = "req-"+requestIdCount;

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("new", Driver.networks.get(Driver.THIS_NETWORK).getPeer(0));

        JSONRPC2Request req = new JSONRPC2Request(rpc, param, id);

        try (Socket socket = new Socket(addr, Driver.THIS_NETWORK)) {
            socket.setSoTimeout(300000);

            Writer writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            writer.write(req.toString()+"\r");
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            JSONRPC2Response res = JSONRPC2Response.parse(reader.readLine());

            if (res.getError() != null) {
                throw new JSONRPC2ParseException(res.getError().toString());
            } else {
                LinkedList<Peer>[] peerlist = (LinkedList<Peer>[]) res.getResult();
                for (int i = 0; i <= 160; i++) {
                    for (int j = 0; j < peerlist[i].size(); j++) {
                        if (peerlist[i].get(j).ping()) {
                            if (Driver.networks.get(Driver.THIS_NETWORK).addPeer(peerlist[i].get(j))) {
                                Client.connect(peerlist[i].get(j).getAddr());
                            }
                        }
                    }
                }
            }

            try {
                socket.close();
            } catch (IOException e) {
                //do nothing
            }

        } catch (SocketTimeoutException|JSONRPC2ParseException e) {
            //TODO alert failed: try again
        } catch (UnknownHostException e) {
            //TODO alert failed: peer does not exist
        } catch (IOException e) {
            //TODO alert failed
        }

        requestIdCount++;

    }

    public static void disconnect() {

        String rpc = "disconnect";
        String id = "";

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("dead", Driver.networks.get(Driver.THIS_NETWORK).getPeer(0));

        JSONRPC2Request req = new JSONRPC2Request(rpc, param, id);

        Client.gossip(req);

    }

    public static void disconnect(Peer deadPeer) {

        String rpc = "disconnect";
        String id = "";

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("dead", deadPeer);

        JSONRPC2Request req = new JSONRPC2Request(rpc, param, id);

        Client.gossip(req);

    }

    public static void find(Peer targetPeer) {

        String rpc = "find";
        String id = "req-"+requestIdCount;

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("from", Driver.networks.get(Driver.THIS_NETWORK).getPeer(0));
        param.put("target", targetPeer);

        JSONRPC2Request req = new JSONRPC2Request(rpc, param, id);

        try (Socket socket = new Socket(Driver.networks.get(Driver.THIS_NETWORK).getPeer(targetPeer.getDistance()).getAddr(), Driver.THIS_NETWORK)) {
            socket.setSoTimeout(300000);

            Writer writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            writer.write(req.toString()+"\r");
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            JSONRPC2Response res = JSONRPC2Response.parse(reader.readLine());

            if (!(res.getError() == null && (boolean)(res.getResult()))) {
                throw new JSONRPC2ParseException(res.getError().toString());
            }

            try {
                socket.close();
            } catch (IOException e) {
                //do nothing
            }

        } catch (JSONRPC2ParseException|SocketTimeoutException e) {
            //TODO alert failed: try again
        } catch (UnknownHostException e) {
            //TODO alert failed: peer does not exist
        } catch (IOException e) {
            //TODO alert failed
        }

        requestIdCount++;

    }

    public static void found(String addr) {

        String rpc = "found";
        String id = "";

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("found", Driver.networks.get(Driver.THIS_NETWORK).getPeer(0));

        JSONRPC2Request req = new JSONRPC2Request(rpc, param, id);

        try (Socket socket = new Socket(addr, Driver.THIS_NETWORK)) {
            socket.setSoTimeout(300000);

            Writer writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            writer.write(req.toString()+"\r");
            writer.flush();

            socket.close();

        } catch (IOException e) {
            //do nothing
        }

    }

    public static void notfound(String addr) {

        String rpc = "found";
        String id = "";

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("found", null);

        JSONRPC2Request req = new JSONRPC2Request(rpc, param, id);

        try (Socket socket = new Socket(addr, Driver.THIS_NETWORK)) {
            socket.setSoTimeout(300000);

            Writer writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            writer.write(req.toString()+"\r");
            writer.flush();

            socket.close();

        } catch (IOException e) {
            //do nothing
        }

    }

    public static void message(String addr, Message message) throws UnknownHostException, IOException {

        String rpc = "message";
        String id = "req-"+requestIdCount;

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("message", message);

        JSONRPC2Request req = new JSONRPC2Request(rpc, param, id);

        try (Socket socket = new Socket(addr, Driver.THIS_NETWORK)) {
            socket.setSoTimeout(300000);

            Writer writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            writer.write(req.toString()+"\r");
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            JSONRPC2Response res = JSONRPC2Response.parse(reader.readLine());

            if (!(res.getError() == null && (boolean)(res.getResult()))) {
                throw new JSONRPC2ParseException(res.getError().toString());
            }

            try {
                socket.close();
            } catch (IOException e) {
                //do nothing
            }

        } catch (JSONRPC2ParseException|SocketTimeoutException e) {
            //TODO alert failed: try again
        } catch (UnknownHostException e) {
            //TODO alert failed: peer does not exist
        } catch (IOException e) {
            //TODO alert failed
        }

        requestIdCount++;

    }

    public static void broadcast(Message message) {

        String rpc = "broadcast";
        String id = "";

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("message", message);

        JSONRPC2Request req = new JSONRPC2Request(rpc, param, id);

        Client.gossip(req);

    }

    public static void forward(String addr, JSONRPC2Request req) {
        try (Socket socket = new Socket(addr, Driver.THIS_NETWORK)) {

            Writer writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            writer.write(req.toString()+"\r");
            writer.flush();

            socket.close();

        } catch (IOException e) {
            //do nothing
        }
    }

    public static void gossip(JSONRPC2Request req) {
        for (int i = 1; i <= 160; i++) {
            for (int j = 0; j < Driver.networks.get(Driver.THIS_NETWORK).hasPeer(i); j++) {
                try (Socket socket = new Socket(Driver.networks.get(Driver.THIS_NETWORK).getPeer(i, j).getAddr(), Driver.THIS_NETWORK)) {

                    Writer writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
                    writer.write(req.toString()+"\r");
                    writer.flush();
        
                    socket.close();
        
                } catch (IOException e) {
                    //do nothing
                }
            }
        }
    }
    
}
