package node.rpc;

import main.*;

public class PingService implements Runnable {

    @Override
    public void run() {

        while (true) {

            try {
                Thread.sleep(86400000);
            } catch (InterruptedException e) {
                //do nothing
            }

            for (int i = 1; i <= 160; i++) {
                if (Driver.networks.get(Driver.THIS_NETWORK).hasPeer(i) > 0) {
                     if (!Driver.networks.get(Driver.THIS_NETWORK).getPeer(i).ping()) {
                         Driver.networks.get(Driver.THIS_NETWORK).removePeer(Driver.networks.get(Driver.THIS_NETWORK).getPeer(i));
                     }
                }
            }

        }
        
    }
    
}
