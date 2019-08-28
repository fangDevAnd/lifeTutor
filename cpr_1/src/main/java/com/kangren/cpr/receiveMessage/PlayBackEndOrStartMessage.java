package com.kangren.cpr.receiveMessage;


/**
 * 播放
 */
public class PlayBackEndOrStartMessage implements IReceiveMessage {

    private long time;

    @Override
    public ReceiveMessageType getMsgType() {
        // TODO Auto-generated method stub
        return ReceiveMessageType.PlayBackEndOrStart;
    }

    @Override
    public long getTime() {
        // TODO Auto-generated method stub
        return this.time;
    }

    @Override
    public void setTime(long time) {
        // TODO Auto-generated method stub
        this.time = time;
    }

}
