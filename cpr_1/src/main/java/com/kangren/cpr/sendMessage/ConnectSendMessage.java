package com.kangren.cpr.sendMessage;


/**
 * 代表的是连接信号
 */
public class ConnectSendMessage implements ISendMessage {

    @Override
    public SendMessageType getMsgType() {
        // TODO Auto-generated method stub
        return SendMessageType.Connect;
    }

}
