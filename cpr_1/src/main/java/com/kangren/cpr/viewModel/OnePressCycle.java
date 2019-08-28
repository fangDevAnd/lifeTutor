package com.kangren.cpr.viewModel;

import com.kangren.cpr.receiveMessage.PressReceiveMessage;

public class OnePressCycle {
    public float startNum = -1;
    public float deepth = -1;
    public float endNum = -1;
    public long startTime = -1;
    public long endTime = -1;
    public int pinlv;
    public PressReceiveMessage lastPress = new PressReceiveMessage();
}
