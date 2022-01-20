package node;

import java.util.*;

public class Message implements Comparable<Message> {

    private Peer to;
    private Peer from;
    private String message;
    private Date timestamp;

    public Message(Peer to, Peer from, String message) {
        this.to = to;
        this.from = from;
        this.message = message;
        this.timestamp = Calendar.getInstance().getTime();
    }

    public Peer getTo() {
        return this.to;
    }

    public Peer getFrom() {
        return this.from;
    }

    public String getMessage() {
        return this.message;
    }

    public String getTimeStamp() {
        return this.timestamp.toString();
    }

    @Override
    public int compareTo(Message message) {
        if (this.timestamp.getTime() - message.timestamp.getTime() == 0) {
            return this.message.compareTo(message.message);
        }
        return (int) (this.timestamp.getTime() - message.timestamp.getTime());
    }
    
}
