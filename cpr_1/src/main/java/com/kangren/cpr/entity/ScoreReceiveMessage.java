package com.kangren.cpr.entity;

import com.kangren.cpr.receiveMessage.IReceiveMessage;

import java.util.ArrayList;
import java.util.List;


public class ScoreReceiveMessage {


    public String id;


    public int elapsedTime;

    public List<IReceiveMessage> msgList;
    public  ScoreReceiveMessage(String id){
        this.id=id;
        msgList=new ArrayList<IReceiveMessage>();
    }
    public  ScoreReceiveMessage(){}


}
