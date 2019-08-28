package com.kangren.cpr.receiveMessage;

public interface IReceiveMessage {


    ReceiveMessageType getMsgType();


    long getTime();

    void setTime(long time);
}

