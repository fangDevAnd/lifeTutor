package com.kangren.cpr.receiveMessage;


/**
 * 抬头（仰头）操作接受的消息
 */
public class RaiseHeadReceiveMessage implements IReceiveMessage {


    public boolean headState;

    @Override
    public ReceiveMessageType getMsgType() {
        // TODO Auto-generated method stub
        return ReceiveMessageType.RaiseHead;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return String.format("ͷ��״̬%s", headState ? "��ͷ " : "ƽ��");
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
