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
        req = JSONRPC2Request.parse(reader.readLine());

    }

    @Override
    public void run() {
        
        switch(req.getMethod()) {
            case "connect":
                res = Service.connect(req);
            case "disconnect":
                res = Service.disconnect(req);
            case "find":
                res = Service.find(req);
            case "found":
                res = Service.found(req);
            case "message":
                res = Service.message(req);
            case "broadcast":
                res = Service.broadcast(req);
            default:
                return;
        }

        // if (res != null) {
        //     try {

        //         Writer writer = new BufferedWriter(new OutputStreamWriter(this.connection.getOutputStream(), "UTF-8"));
        //         writer.write(this.res.toString()+"/r");
        //         writer.flush();

        //         connection.close();

        //     } catch (IOException e) {
        //         //do nothing
        //     }
        // }
        
    }
    
}
