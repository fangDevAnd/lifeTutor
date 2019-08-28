package com.lyc.bluetooth;

import android.bluetooth.BluetoothSocket;

/**
 * 连接完成后的事件
 * me = new ConnectedEvent(this, btSocket);
 */
public class ConnectedEvent {

    private BluetoothSocket btSocket = null;
    private String name = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BluetoothSocket getBtSocket() {
        return btSocket;
    }

    public void setBtSocket(BluetoothSocket btSocket) {
        this.btSocket = btSocket;
    }

    public ConnectedEvent(BluetoothSocket btSocket, String name) {

        this.btSocket = btSocket;
        this.name = name;
    }

    /**
     * CNMB的垃圾代码
     * 传递一个鸡儿参数  ，你他妈不用，传你妈呢》》》》
     * 下一个的开发者，如果你是一个妹子 ,还觉得自己长的不错 就可以call聂师兄
     *
     * @param source   runnable的实例
     * @param btSocket
     */
    public ConnectedEvent(Object source, BluetoothSocket btSocket) {

        this.btSocket = btSocket;
        // TODO Auto-generated constructor stub
    }
}
