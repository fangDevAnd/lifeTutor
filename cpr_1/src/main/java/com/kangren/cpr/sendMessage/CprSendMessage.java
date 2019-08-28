package com.kangren.cpr.sendMessage;


/**
 * 代表发送消息的类型是  Cpr类型的消息
 */
public class CprSendMessage implements ISendMessage {


    public int cprflag;


    public int neckflag = 1;


    public int eyeflag = 1;


    @Override
    public SendMessageType getMsgType() {
        // TODO Auto-generated method stub
        return SendMessageType.Cpr;
    }

}
