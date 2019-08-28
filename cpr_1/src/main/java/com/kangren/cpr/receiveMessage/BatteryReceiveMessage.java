package com.kangren.cpr.receiveMessage;

/**
 * 电量接受的消息
 */
public class BatteryReceiveMessage implements IReceiveMessage {


    public int num;

    @Override
    public ReceiveMessageType getMsgType() {
        // TODO Auto-generated method stub
        return ReceiveMessageType.Battery;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return String.format("%s电量", num);
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
