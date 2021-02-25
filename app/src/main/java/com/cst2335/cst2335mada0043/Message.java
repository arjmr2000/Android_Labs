package com.cst2335.cst2335mada0043;

public class Message {
    //class variable
//define variables -> 3
    //string variable called msg
    String msg;
    long id;
    boolean send;
    protected String textValue;

    //long variable called id
    //boolean variable:

    //constructor
    public Message(long i,String e){
        id = i;
        textValue = e;
    }
    public void update(String e){
        textValue = e;
    }
    public Message(String e) { this( 0,e);}

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
    public String getTextValue() {
        return textValue;
    }



}
