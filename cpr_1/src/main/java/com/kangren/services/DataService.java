package com.kangren.services;

import android.util.Log;

import com.kangren.cpr.AppConfig;
import com.kangren.cpr.analyzer.AnalyzerFactory;
import com.kangren.cpr.analyzer.IAnalyzer;
import com.kangren.cpr.receiveMessage.BatteryReceiveMessage;
import com.kangren.cpr.receiveMessage.BlowReceiveMessage;
import com.kangren.cpr.receiveMessage.IReceiveMessage;
import com.kangren.cpr.receiveMessage.OperationBeforeReceiveMessage;
import com.kangren.cpr.receiveMessage.PressReceiveMessage;
import com.kangren.cpr.receiveMessage.RaiseHeadReceiveMessage;
import com.kangren.cpr.sendMessage.ISendMessage;
import com.lyc.bluetooth.MessageDevice;
import com.lyc.bluetooth.MessageDevice.IRecevedMsgListener;
import com.lyc.bluetooth.MessageDevice.MsgEvent;
import com.lyc.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataService {


    private static DataService dataService;

    public static DataService getInstance() {
        if (dataService == null)
            dataService = new DataService();
        return dataService;
    }


    private boolean modelConncted = false;
    private IRecevedMsgListener recevedMsgListener;

    private int receiveCount = 0;
    private byte[] messsageBuffer;
    private IAnalyzer analyzer;
    private List<IReceiver> receiverList;
    private int packageLen;
    private MessageDevice md;

    private AllocateThread allocateThread;//

    /**
     * 创建DataService的实例的时候。就去开启了消息轮训机制
     */
    private DataService() {
        receiverList = new ArrayList<IReceiver>();
        this.analyzer = AnalyzerFactory.createFactory(AppConfig.getInstance().AnlyzerType);
        packageLen = AppConfig.getInstance().BluetoothPackageLength;
        messsageBuffer = new byte[1024];
        /**
         *         消息轮训
         *         将接收到的消息回调到
         */
        allocateThread = new AllocateThread();
        allocateThread.start();
    }

    private void print(byte[] data) {

        //Integer[] buffer = new  Integer[20];
        //List<Integer> subList=null;

        String st = "";
        for (int i : data) {
            //list.add(i);
            int temp = i & 0xff;
            //	list.add(Integer.toHexString(temp));
            st += Integer.toHexString(temp);
        }
        //if(list.size()<20){
        //	return;
        //}
        //subList=list.subList(0, 20);
        //for(int i=0;i<20;i++){
        //	int temp=subList.get(i)&0xff;
        //	st+=Integer.toHexString(temp)+" ";

        LogUtil.WriteLog(st);
        //subList.toArray(buffer);
        //subList.clear();
        //AnalyzerFactory rmf=AnalyzerFactory.getInstance();
        //List<IReceiveMessage> msgs=rmf.analyze(buffer);
        // for(IReceiveMessage msg :msgs){
        //	 LogUtil.WriteLog(msg.toString());
        // }

    }

    /**
     * 发送一个消息
     *
     * @param msg
     */
    public void send(ISendMessage msg) {
        byte[] buffer = analyzer.unParse(msg);
        md.write(buffer);

    }

    //private void setThreadMsg(){

    //}

    private boolean checkMsg(byte[] oneMsg) {
        return (oneMsg[0] == -16 && oneMsg[1] == -16 && oneMsg[2] == -16);
    }

    public void start() {

        /**
         * 从蓝牙连接接受到消息完成的回调监听接口
         */
        if (recevedMsgListener == null)
            recevedMsgListener = new IRecevedMsgListener() {

                @Override
                public void Done(MsgEvent me) {
                    // TODO Auto-generated method stub

                    /**
                     * 在单个对象中，保证线程的同步
                     */
                    synchronized (messsageBuffer) {


                        byte[] data = me.getMsg();

                        if (receiveCount < 0) {
                            receiveCount = 0;
                        }
                        System.arraycopy(data, 0, messsageBuffer, receiveCount, data.length);
                        receiveCount += data.length;
                        while (receiveCount >= packageLen) {
                            byte[] oneMsg = new byte[packageLen];
                            byte[] temp = new byte[1024];
                            System.arraycopy(messsageBuffer, 0, oneMsg, 0, packageLen);
                            Log.d("test", "Done: ======接受到消息" + Arrays.toString(oneMsg));
                            if (!checkMsg(oneMsg)) {
                                LogUtil.WriteLog("eeeeeeeeecheckMsg");
                                receiveCount--;//������ǰ�ͷ ��ƫ��һλ
                                if (receiveCount < 0)
                                    receiveCount = 0;
                                System.arraycopy(messsageBuffer, 1, messsageBuffer, 0, receiveCount);

                                continue;
                            }


                            receiveCount -= packageLen;
                            System.arraycopy(messsageBuffer, packageLen, temp, 0, messsageBuffer.length - packageLen);
                            messsageBuffer = temp;


                            List<IReceiveMessage> msgList = analyzer.parse(oneMsg);

                            /**
                             * 添加消息
                             */
                            allocateThread.add(msgList);
                            allocateThread.interrupt();

                        }
                        byte[] temp = messsageBuffer;
                        int i = 0;
                    }


                }
            };


        /**
         *
         *
         * 这个代码建立了MessageDevice 和 DataService(当前类)的连接
         *
         * 通过这种方法 ，连接产生的消息会回调到当前的监听器中
         *
         *
         */

        this.md.setRecevedMsgListener(recevedMsgListener);
    }

    public void stop() {
        this.md.removeRecevedMsgListener(recevedMsgListener);
    }

    /**
     * 设置设备连接状态的消息
     *
     * @param md
     */
    public void setMessageDevice(MessageDevice md) {
        this.md = md;
        start();
        md.start();
    }

    public void addIreceiver(IReceiver receiver) {
        this.receiverList.add(receiver);
    }

    public void removeIreceiver(IReceiver receiver) {
        this.receiverList.remove(receiver);
    }

    public interface IReceiver {
        void press(PressReceiveMessage press);

        void blow(BlowReceiveMessage blow);

        void battery(BatteryReceiveMessage battery);

        void raiseHead(RaiseHeadReceiveMessage raiseHead);

        void operationBefore(OperationBeforeReceiveMessage operationBefore);

        void palyBackCompeled();
    }


    /**
     * 用于实现数据的分发
     */
    class AllocateThread extends Thread {

        List<IReceiveMessage> backList = new ArrayList<IReceiveMessage>();
        List<IReceiveMessage> msgList = new ArrayList<IReceiveMessage>();

        void add(List<IReceiveMessage> msgList) {
            synchronized (backList) {
                this.backList.addAll(msgList);
            }

        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            while (true) {
                for (IReceiveMessage msg : msgList) {

                    long time = System.currentTimeMillis();
                    long cha = msg.getTime() - time;
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

                //����Ƿ��зַ��ģ�����м�����while��û�еľ�sleep�ȴ������ź�
                this.msgList = new ArrayList<IReceiveMessage>(backList);
                backList.clear();
                if (msgList.size() == 0) {
                    try {


                        Thread.sleep(3600 * 1000);

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    this.msgList = new ArrayList<IReceiveMessage>(backList);
                    backList.clear();
                }

            }
        }

        /**
         * 发出消息
         * <p>
         * 消息被回调到 #{{@link com.kangren.cpr.CprFragment}}的 iReceiver中
         *
         * @param msg
         */
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


    }

}
