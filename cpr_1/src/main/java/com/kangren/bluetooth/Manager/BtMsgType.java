package com.kangren.bluetooth.Manager;

public enum BtMsgType {

    /**
     * 模型人连接中。。。。
     */
    modelConnected,
    /**
     * 模型人连接失败
     */
    modelConnectFaild,
    /**
     * 模型人连接丢失
     */
    modelMiss,
    /**
     * 打印机连接中
     */
    printConnected,
    /**
     * 打印机连接失败
     */
    printConnectFaild,
    /**
     * 打印机连接丢失
     */
    printMiss
}
