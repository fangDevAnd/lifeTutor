package com.kangren.cpr.analyzer;

import com.kangren.cpr.receiveMessage.IReceiveMessage;
import com.kangren.cpr.sendMessage.ISendMessage;

import java.util.List;

public interface IAnalyzer {

    public List<IReceiveMessage> parse(byte[] buffer);
    public byte[] unParse(ISendMessage msg);

}
