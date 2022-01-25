package node.rpc;

import node.*;
import main.*;

import java.io.*;
import java.net.*;
import java.util.*;

import net.minidev.json.*;
import com.thetransactioncompany.jsonrpc2.*;

public class Client {

    private static int requestIdCount;

    @SuppressWarnings("unchecked")
    public static void connect(int port) {

        String rpc = "connect";
        String id = "req-"+requestIdCount;

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("new", Node.getPeer(0));

        JSONRPC2Request req = new JSONRPC2Request(rpc, param, id);

        try (Socket socket = new Socket("0.0.0.0", port)) {
            socket.setSoTimeout(300000);

            Writer writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            writer.write(req.toString()+"\r");
            writer.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            JSONRPC2Response res = JSONRPC2Response.parse(reader.readLine());

            if (res.getError() != null) {
                throw new JSONRPC2ParseException(res.getError().toString());
            } else {
                try {
                    ArrayList<ArrayList<JSONObject>> peerlist = (ArrayList<ArrayList<JSONObject>>) res.getResult();
                    for (int i = 0; i <= 160; i++) {
                    for (int j = 0; j < peerlist.get(i).size(); j++) {
                        String idd = (String)((JSONObject)peerlist.get(i).get(j)).get("id");
                        int addr = ((Long)((JSONObject)peerlist.get(i).get(j)).get("addr")).intValue();
                        Peer newPeer = new Peer(idd, addr);
                        if (newPeer.ping()) {
                            if (Node.addPeer(newPeer)) {
                                Client.connect((newPeer).getAddr());
                            }
                        }
                    }
                }
                } catch (Exception e) {
                    e.printStackTrace();
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
            e.printStackTrace();
            //TODO alert failed
        }

        requestIdCount++;

    }

    public static void disconnect() {

        String rpc = "disconnect";
        String id = "";

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("dead", Node.getPeer(0));

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

    public static void find(Peer targetPeer) throws JSONRPC2ParseException, IOException {

        String rpc = "find";
        String id = "req-"+requestIdCount;

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("from", Node.getPeer(0));
        param.put("target", targetPeer);

        JSONRPC2Request req = new JSONRPC2Request(rpc, param, id);

        try (Socket socket = new Socket("0.0.0.0", Node.getPeer(targetPeer.getDistance()).getAddr())) {
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

        }

        requestIdCount++;

    }

    public static void found(int port) {

        String rpc = "found";
        String id = "";

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("found", Node.getPeer(0));

        JSONRPC2Request req = new JSONRPC2Request(rpc, param, id);

        try (Socket socket = new Socket("0.0.0.0", port)) {
            socket.setSoTimeout(300000);

            Writer writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            writer.write(req.toString()+"\r");
            writer.flush();

            socket.close();

        } catch (IOException e) {
            //do nothing
        }

    }

    public static void notfound(int port) {

        String rpc = "found";
        String id = "";

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("found", null);

        JSONRPC2Request req = new JSONRPC2Request(rpc, param, id);

        try (Socket socket = new Socket("0.0.0.0", port)) {
            socket.setSoTimeout(300000);

            Writer writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            writer.write(req.toString()+"\r");
            writer.flush();

            socket.close();

        } catch (IOException e) {
            //do nothing
        }

    }

    public static void message(int port, Message message) throws JSONRPC2ParseException, SocketTimeoutException, UnknownHostException, IOException {

        String rpc = "message";
        String id = "req-"+requestIdCount;

        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("message", message);

        JSONRPC2Request req = new JSONRPC2Request(rpc, param, id);

        try (Socket socket = new Socket("0.0.0.0", port)) {
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

    public static void forward(int port, JSONRPC2Request req) {
        try (Socket socket = new Socket("0.0.0.0", port)) {

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
            for (int j = 0; j < Node.hasPeer(i); j++) {
                try (Socket socket = new Socket("0.0.0.0", Node.getPeer(i, j).getAddr())) {

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
