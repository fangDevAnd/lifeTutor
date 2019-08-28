package com.kangren.services;

import com.kangren.cpr.entity.ScoreReceiveMessage;
import com.kangren.cpr.receiveMessage.BatteryReceiveMessage;
import com.kangren.cpr.receiveMessage.BlowReceiveMessage;
import com.kangren.cpr.receiveMessage.IReceiveMessage;
import com.kangren.cpr.receiveMessage.OperationBeforeReceiveMessage;
import com.kangren.cpr.receiveMessage.PressReceiveMessage;
import com.kangren.cpr.receiveMessage.RaiseHeadReceiveMessage;
import com.kangren.services.DataService.IReceiver;

import java.util.ArrayList;
import java.util.List;

public class PlayBackDataService {


    private static PlayBackDataService dataService;

    public static PlayBackDataService getInstance() {
        if (dataService == null)
            dataService = new PlayBackDataService();
        return dataService;
    }

    private ScoreReceiveMessage receiveMsg;

    private playBackThread playbackThread;
    private List<IReceiver> receiverList;

    private PlayBackDataService() {
        receiverList = new ArrayList<IReceiver>();
    }


    public ScoreReceiveMessage getReceiveMsg() {
        return receiveMsg;
    }


    public void setReceiveMsg(ScoreReceiveMessage receiveMsg) {
        this.receiveMsg = receiveMsg;
    }


    public void start() {
        playbackThread = new playBackThread();
        playbackThread.start();
    }

    public void stop() {
        playbackThread.goStop();
    }

    public void pause() {
        playbackThread.pause();
    }

    public void resume() {
        playbackThread.unPause();
    }


    public void addIreceiver(IReceiver receiver) {
        this.receiverList.add(receiver);
    }

    public void removeIreceiver(IReceiver receiver) {
        this.receiverList.remove(receiver);
    }

    //���������ɷ�����
    private void sendOut(IReceiveMessage msg) {

        switch (msg.getMsgType()) {
            case Battery:
                for (IReceiver receiver : receiverList)
                    receiver.battery((BatteryReceiveMessage) msg);
                break;
            case Blow:
                for (IReceiver receiver : receiverList)
                    receiver.blow((BlowReceiveMessage) msg);
                break;
            case OperationBefore:
                for (IReceiver receiver : receiverList)
                    receiver.operationBefore((OperationBeforeReceiveMessage) msg);
                break;
            case Press:
                for (IReceiver receiver : receiverList)
                    receiver.press((PressReceiveMessage) msg);
                break;
            case RaiseHead:
                for (IReceiver receiver : receiverList)
                    receiver.raiseHead((RaiseHeadReceiveMessage) msg);
                break;
            default:
                break;
        }
    }

    class playBackThread extends Thread {
        private boolean isRuning;
        private long offsetTime;
        private boolean isStop = false;
        private int index;

        @Override
        public void run() {
            while (!isStop) {
                while (isRuning) {

                    if (index >= receiveMsg.msgList.size()) {
                        goStop();
                        //	LogUtil.WriteLog("eeeeeeeegoStop"+receiverList.size());
                        for (IReceiver receiver : receiverList)
                            receiver.palyBackCompeled();
                        break;
                    }
                    IReceiveMessage msg;
                    msg = receiveMsg.msgList.get(index);
                    index++;
                    long time = System.currentTimeMillis();
                    long cha = (msg.getTime() + offsetTime) - time;
                    if (cha <= 0) {
                        sendOut(msg);
                        continue;
                    }
                    //δ����Ӧʱ�䣬�͵ȴ�
                    try {
                        Thread.sleep(cha);
                        sendOut(msg);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                if (isStop) {
                    break;
                }
                try {
                    Thread.sleep(1000 * 36000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void start() {


            index = 0;
            offsetTime = System.currentTimeMillis() - receiveMsg.msgList.get(0).getTime();
            isStop = false;
            isRuning = true;
            super.start();
        }

        public void pause() {
            isRuning = false;
        }

        public void unPause() {

            offsetTime = System.currentTimeMillis() - receiveMsg.msgList.get(index).getTime();
            isRuning = true;
            this.interrupt();
        }

        public void goStop() {
            isRuning = false;
            isStop = true;
        }
    }


}
