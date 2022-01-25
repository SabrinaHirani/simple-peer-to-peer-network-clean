package node.rpc;

import node.*;
import main.*;

import java.io.*;
import java.net.*;

import java.util.concurrent.*;

import com.thetransactioncompany.jsonrpc2.*;

import graphics.App;

public class Server extends Thread {

    private int PORT = -1;

    private ExecutorService executor;
    private ServerSocket server;

    public Server() throws IllegalArgumentException, IOException {

        this.PORT = Node.PORT;

        executor = Executors.newFixedThreadPool(3);

        this.server = new ServerSocket(this.PORT);

    }

    public void stopServer() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(3, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            App.displayInfo("Termination Interrupted!");
        }
        try {
            this.server.close();
        } catch (IOException e) {
            //do nothing
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket connection = server.accept();
                try {
                    executor.submit(new Handler(connection));
                } catch (JSONRPC2ParseException|IOException e) {
                    //do nothing
                }
            } catch (IOException e) {
                //do nothing
            }
        }
    }
    
}
