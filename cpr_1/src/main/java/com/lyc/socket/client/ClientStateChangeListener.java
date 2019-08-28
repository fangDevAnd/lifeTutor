package com.lyc.socket.client;

/**
 *
 * 连接状态改变的监听器
 *
 */
public interface ClientStateChangeListener {
    void stateChange(int state);
}
