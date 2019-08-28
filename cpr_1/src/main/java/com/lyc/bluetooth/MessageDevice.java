package com.lyc.bluetooth;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.lyc.utils.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 该类用于实现消息的传递
 * 传递的参数是一个已经建立连接的对象
 *
 *
 *
 * 内部可以添加监听列表  ，用于实现对消息的监听
 *
 */
public class MessageDevice {

    /**
     * 蓝牙连接的套接字
     */
    private BluetoothSocket btSocket = null;
    /**
     * 接收消息监听列表
     */
    private ArrayList<IRecevedMsgListener> recevedMsgListenerList = new ArrayList<IRecevedMsgListener>();
    private IBreakListener breakListener;
    private boolean isStart = false;

    public BluetoothSocket getBtSocket() {
        return btSocket;
    }

    private OutputStream outStream = null;
    private InputStream inStream = null;
    private String lastSt;

    public MessageDevice(BluetoothSocket btSocket) {


        this.btSocket = btSocket;
        try {
            outStream = btSocket.getOutputStream();
            inStream = btSocket.getInputStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block

        }
    }

    List<Integer> list = new ArrayList<Integer>();

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
     * 开始进行连接
     * 并将连接的数据回调到监听器中
     */
    public void start() {
        isStart = true;
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (isStart) {
                    byte res[] = null;
                    try {
                        byte[] buffer = new byte[1024 * 8];//使用8个字节
                        int len;

                        len = inStream.read(buffer);
                        res = new byte[len];
                        System.arraycopy(buffer, 0, res, 0, len);
                        MsgEvent me = new MsgEvent(this, res);
                        for (int i = 0; i < recevedMsgListenerList.size(); i++) {
                            recevedMsgListenerList.get(i).Done(me);
                        }
                        Log.d("test", "run: ====" + res);
                        //LogUtil.WriteLog(len+"");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        LogUtil.WriteLog(e);
                        if (breakListener != null)
                            breakListener.Done();
                        break;
                    }
                }

            }
        }).start();
    }

    public void close() {
        isStart = false;
        try {
            btSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void stop() {
        isStart = false;
    }

    public boolean write(byte[] buffer) {
        try {
            outStream.write(buffer);
            outStream.flush();
            return true;
            //LogUtil.WriteLog("ת���ɹ�");
        } catch (IOException e) {

            LogUtil.WriteLog(e);
            // TODO Auto-generated catch block
            if (breakListener != null)
                breakListener.Done();
            return false;
        }

    }

    public void removeRecevedMsgListener(IRecevedMsgListener recevedMsgListener) {
        if (recevedMsgListenerList.indexOf(recevedMsgListener) > -1)
            recevedMsgListenerList.remove(recevedMsgListener);
    }

    public void setRecevedMsgListener(IRecevedMsgListener recevedMsgListener) {
        if (recevedMsgListenerList.indexOf(recevedMsgListener) == -1)
            recevedMsgListenerList.add(recevedMsgListener);
    }

    public void setBreakListener(IBreakListener breakListener) {
        this.breakListener = breakListener;

    }

    /**
     * 接受消息的监听器
     */
    public interface IRecevedMsgListener {

        void Done(MsgEvent me);
    }

    /**
     * 断开连接的监听器
     */
    public interface IBreakListener {

        void Done();
    }


    /**
     * 消息主题，代表的是从蓝牙接受的消息
     */
    public class MsgEvent extends java.util.EventObject {

        private byte[] msg = null;

        public byte[] getMsg() {
            return msg;
        }

        /**
         * 传递的消息 byte类型  当前的消息最大为8个字节，原因是缓存。
         *
         * @param msg
         */
        public void setMsg(byte[] msg) {
            this.msg = msg;
        }

        public MsgEvent(Object source, byte[] msg) {
            super(source);
            this.msg = msg;
            // TODO Auto-generated constructor stub
        }
    }
}
