package node.util;

import node.*;
import java.util.*;

import main.Driver;

public class SortByLastSeen implements Comparator<Peer> {

    @Override
    public int compare(Peer p1, Peer p2) {
        try {
            return Math.toIntExact(Driver.networks.get(Driver.THIS_NETWORK).getMessagingHistory(p1).pollLast().getTimeStamp() - Driver.networks.get(Driver.THIS_NETWORK).getMessagingHistory(p2).pollLast().getTimeStamp());
        } catch (ArithmeticException e) {
            if (Driver.networks.get(Driver.THIS_NETWORK).getMessagingHistory(p1).pollLast().getTimeStamp() > Driver.networks.get(Driver.THIS_NETWORK).getMessagingHistory(p2).pollLast().getTimeStamp()) {
                return Integer.MAX_VALUE;
            } else {
                return Integer.MIN_VALUE;
            }
        }
    }
    
}
