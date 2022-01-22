package node.rpc;

import node.*;
import main.*;
import net.minidev.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.*;

import com.thetransactioncompany.jsonrpc2.*;

public class Client {

    private static int requestIdCount;

    @SuppressWarnings("unchecked")
    public static void connect(String addr) throws JSONRPC2ParseException, SocketTimeoutException, UnknownHostException, IOException {

        String rpc = "connect";
        String id = "req-"+requestIdCount;

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("new", Driver.networks.get(Driver.THIS_NETWORK).getPeer(0));

        JSONRPC2Request req = new JSONRPC2Request(rpc, param, id);

        try (Socket socket = new Socket(addr, Driver.THIS_NETWORK)) {
            socket.setSoTimeout(300000);

            Writer writer = new PrintWriter(socket.getOutputStream(), true);
            writer.write(req.toString()+"\r");
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            JSONRPC2Response res = JSONRPC2Response.parse(reader.readLine());

            if (res.getError() != null) {
                throw new JSONRPC2ParseException(res.getError().toString());
            } else {
                ArrayList<ArrayList<JSONObject>> peerlist = (ArrayList<ArrayList<JSONObject>>) res.getResult();
                for (int i = 0; i <= 160; i++) {
                    for (int j = 0; j < peerlist.get(i).size(); j++) {
                        String peerid = (String)((JSONObject) peerlist.get(i).get(j)).get("id");
                        String peeraddr = (String)((JSONObject) peerlist.get(i).get(j)).get("addr");
                        Peer thisPeer = new Peer(peerid, peeraddr);
                        if (thisPeer.ping()) {
                            if (Driver.networks.get(Driver.THIS_NETWORK).addPeer(thisPeer)) {
                                Client.connect(thisPeer.getAddr());
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

        } catch (Exception e) {
            e.printStackTrace();
        }

        requestIdCount++;

    }

    public static void disconnect(int port) {

        String rpc = "disconnect";
        String id = "";

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("dead", Driver.networks.get(Driver.THIS_NETWORK).getPeer(0));

        JSONRPC2Request req = new JSONRPC2Request(rpc, param, id);

        Client.gossip(req);

    }

    public static void disconnect(int port, Peer deadPeer) {

        String rpc = "disconnect";
        String id = "";

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("dead", deadPeer);

        JSONRPC2Request req = new JSONRPC2Request(rpc, param, id);

        Client.gossip(port, req);

    }

    public static void find(Peer targetPeer) throws JSONRPC2ParseException, SocketTimeoutException, UnknownHostException, IOException {

        String rpc = "find";
        String id = "req-"+requestIdCount;

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("from", Driver.networks.get(Driver.THIS_NETWORK).getPeer(0));
        param.put("target", targetPeer);

        JSONRPC2Request req = new JSONRPC2Request(rpc, param, id);

        try (Socket socket = new Socket(Driver.networks.get(Driver.THIS_NETWORK).getPeer(targetPeer.getDistance()).getAddr(), Driver.THIS_NETWORK)) {
            socket.setSoTimeout(300000);

            Writer writer = new PrintWriter(socket.getOutputStream(), true);
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

        }

        requestIdCount++;

    }

    public static void found(String addr, int port) {

        String rpc = "found";
        String id = "";

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("found", Driver.networks.get(port).getPeer(0));

        JSONRPC2Request req = new JSONRPC2Request(rpc, param, id);

        try (Socket socket = new Socket(addr, port)) {
            socket.setSoTimeout(300000);

            Writer writer = new PrintWriter(socket.getOutputStream(), true);
            writer.write(req.toString()+"\r");
            writer.flush();

            socket.close();

        } catch (IOException e) {
            //do nothing
        }

    }

    public static void notfound(String addr, int port) {

        String rpc = "found";
        String id = "";

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("found", null);

        JSONRPC2Request req = new JSONRPC2Request(rpc, param, id);

        try (Socket socket = new Socket(addr, port)) {
            socket.setSoTimeout(300000);

            Writer writer = new PrintWriter(socket.getOutputStream(), true);
            writer.write(req.toString()+"\r");
            writer.flush();

            socket.close();

        } catch (IOException e) {
            //do nothing
        }

    }

    public static void message(String addr, Message message) throws JSONRPC2ParseException, SocketTimeoutException, UnknownHostException, IOException {

        String rpc = "message";
        String id = "req-"+requestIdCount;

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("message", message);

        JSONRPC2Request req = new JSONRPC2Request(rpc, param, id);

        try (Socket socket = new Socket(addr, Driver.THIS_NETWORK)) {
            socket.setSoTimeout(300000);

            Writer writer = new PrintWriter(socket.getOutputStream(), true);
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

    public static void forward(String addr, int port, JSONRPC2Request req) {
        try (Socket socket = new Socket(addr, Driver.THIS_NETWORK)) {

            Writer writer = new PrintWriter(socket.getOutputStream(), true);
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

                    Writer writer = new PrintWriter(socket.getOutputStream(), true);
                    writer.write(req.toString()+"\r");
                    writer.flush();
        
                    socket.close();
        
                } catch (IOException e) {
                    //do nothing
                }
            }
        }
    }

    public static void gossip(int port, JSONRPC2Request req) {
        for (int i = 1; i <= 160; i++) {
            for (int j = 0; j < Driver.networks.get(Driver.THIS_NETWORK).hasPeer(i); j++) {
                try (Socket socket = new Socket(Driver.networks.get(Driver.THIS_NETWORK).getPeer(i, j).getAddr(), Driver.THIS_NETWORK)) {

                    Writer writer = new PrintWriter(socket.getOutputStream(), true);
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
