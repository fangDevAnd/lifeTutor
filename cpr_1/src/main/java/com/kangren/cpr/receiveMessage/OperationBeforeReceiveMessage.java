package com.kangren.cpr.receiveMessage;


/**
 * 操作开始之前 接受的消息
 */
public class OperationBeforeReceiveMessage implements IReceiveMessage {


    public OperationBeforeType operationBeforeType;


    @Override
    public ReceiveMessageType getMsgType() {
        // TODO Auto-generated method stub
        return ReceiveMessageType.OperationBefore;
    }

    private long time;

    @Override
    public long getTime() {
        // TODO Auto-generated method stub
        return time;
    }

    @Override
    public void setTime(long time) {
        // TODO Auto-generated method stub
        this.time = time;

    }
}
