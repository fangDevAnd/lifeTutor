package com.kangren.cpr.receiveMessage;

public class PressReceiveMessage implements IReceiveMessage {


    public int lights;
    public float num;

    public boolean isIn;

    @Override
    public ReceiveMessageType getMsgType() {
        // TODO Auto-generated method stub
        return ReceiveMessageType.Press;
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
