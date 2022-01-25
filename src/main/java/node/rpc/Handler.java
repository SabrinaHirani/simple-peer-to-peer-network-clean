package node.rpc;

import java.io.*;
import java.net.*;

import com.thetransactioncompany.jsonrpc2.*;

public class Handler implements Runnable {

    private Socket connection;

    private JSONRPC2Request req;
    private JSONRPC2Response res;

    public Handler(Socket connection) throws JSONRPC2ParseException, IOException {

        this.connection = connection;

        BufferedReader reader = new BufferedReader(new InputStreamReader(this.connection.getInputStream(), "UTF-8"));
        req = JSONRPC2Request.parse(reader.readLine()+" ");

    }

    @Override
    public void run() {
        
        try {
            if (req.getMethod().equals("connect")) {
                res = Service.connect(req);
            } else if  (req.getMethod().equals("disconnect")) {
                res = Service.disconnect(req);
            } else if (req.getMethod().equals("find")) {
                res = Service.find(req);
            } else if (req.getMethod().equals("found")) {
                res = Service.found(req);
            } else if (req.getMethod().equals("message")) {
                res = Service.message(req);
            } else if (req.getMethod().equals("broadcast")) {
                res = Service.broadcast(req);
            } else {
                return;
            }
        } catch (NullPointerException e) {
            return;
        }

        if (res != null) {
            try {

                Writer writer = new BufferedWriter(new OutputStreamWriter(this.connection.getOutputStream(), "UTF-8"));
                writer.write(this.res.toString()+"/r");
                writer.flush();

                connection.close();

            } catch (IOException e) {
                //do nothing
            }
        }
        
    }
    
}
