package com.cst2335mada0043;

public class Message {
    String msg;
    long id;
    boolean send;
    public Message(String msg, long id, boolean send) {
        this.msg = msg;
        this.id = id;
        this.send = send;
    }
    public String getMsg(){
        return msg;
    }
    public long getId(){
        return id;
    }
    public boolean getSend(){
        return send;
    }
}
