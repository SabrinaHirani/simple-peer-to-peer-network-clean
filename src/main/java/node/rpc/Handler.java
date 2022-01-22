package node.rpc;

import java.io.*;
import java.net.*;

import com.thetransactioncompany.jsonrpc2.*;

public class Handler implements Runnable {

    private int PORT;
    private Socket connection;

    private JSONRPC2Request req;
    private JSONRPC2Response res;

    public Handler(int port, Socket connection) throws JSONRPC2ParseException, IOException {

        this.PORT = port;
        this.connection = connection;

        BufferedReader reader = new BufferedReader(new InputStreamReader(this.connection.getInputStream(), "UTF-8"));
        req = JSONRPC2Request.parse(reader.readLine());

    }

    @Override
    public void run() {
        
        try {
            if (req.getMethod().equals("connect")) {
                res = Service.connect(this.PORT, req);
            } else if  (req.getMethod().equals("disconnect")) {
                res = Service.disconnect(this.PORT, req);
            } else if (req.getMethod().equals("find")) {
                res = Service.find(this.PORT, req);
            } else if (req.getMethod().equals("found")) {
                res = Service.found(this.PORT, req);
            } else if (req.getMethod().equals("message")) {
                res = Service.message(this.PORT, req);
            } else if (req.getMethod().equals("broadcast")) {
                res = Service.broadcast(this.PORT, req);
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
