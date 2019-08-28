package com.kangren.bluetooth.Manager;

public interface IObBt {

    /**
     * 根据代码
     *
     * @param msgType
     * @param o       #{{@link com.lyc.bluetooth.MessageDevice}} 类型
     */
    public void notify(BtMsgType msgType, Object o);
}
