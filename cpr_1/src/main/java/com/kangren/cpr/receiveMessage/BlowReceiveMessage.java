package com.kangren.cpr.receiveMessage;

public class BlowReceiveMessage implements IReceiveMessage {
    public int num;

    public boolean isIn;


    public boolean flag;

    @Override
    public ReceiveMessageType getMsgType() {
        // TODO Auto-generated method stub
        return ReceiveMessageType.Blow;
    }

    public int lights;

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        int temp = num & 0xff;
        return String.format("������%s ����%s", Integer.toHexString(temp), isIn ? "����" : "����");
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
