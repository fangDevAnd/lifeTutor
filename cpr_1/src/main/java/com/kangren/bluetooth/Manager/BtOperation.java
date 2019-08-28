package com.kangren.bluetooth.Manager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.kangren.cpr.AppConfig;
import com.lyc.bluetooth.Client;
import com.lyc.bluetooth.ConnectedEvent;
import com.lyc.bluetooth.DeviceEvent;
import com.lyc.bluetooth.IConnectedListener;
import com.lyc.bluetooth.MessageDevice;
import com.lyc.bluetooth.MessageDevice.IBreakListener;
import com.lyc.bluetooth.Print;
import com.lyc.bluetooth.Print.IPrintConnectedListener;
import com.lyc.bluetooth.SearchDevice;
import com.lyc.bluetooth.SearchDevice.IFindDeviceListener;
import com.lyc.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Bluetooth-related operation package is provided.
 * <p>
 * 内部封装了 #{{@link SearchDevice}}
 * 实现对 设备的搜索，搜索到设备的回调监听
 * <p>
 * 同时封装了 #{{@link Client}} 以及 #{{@link Client#connectedListener}}
 */
public class BtOperation {

    public static String TAG = "test";

    private static BtOperation bt;

    public static BtOperation getInstance() {
        if (bt == null)
            bt = new BtOperation();
        return bt;
    }

    //模型人自动连接次数
    private int mAutoConnectTimes = 10;
    //打印机自动连接次数
    private int pAutoConnectTimes = 10;
    //模型人被连接
    private boolean modelConnected, printConnected;
    //打印机
    private Print print;

    private Context context;
    /**
     * 操作类型的列表
     */
    private List<IObBt> obList;
    /**
     * 打印机的名称和模型人的名称
     */
    private String printName, modelName;
    /**
     * 搜索设备
     */
    private SearchDevice sd;

    private BtOperation() {
    }

    public void setPar(Context context, String modelName, String printName) {
        this.context = context;
        this.printName = printName;
        this.modelName = modelName;
        obList = new ArrayList<IObBt>();
    }

    /**
     * 初始化搜索设备 ，同时定义搜索的监听
     * 当设备被发现时，自动回调当前的接口
     */
    public void doWork() {
        if (sd == null) {
            sd = new SearchDevice(context);

            /**
             * 2019-07-21 03:37:24.192 10780-10780/com.example.cpr_1 D/test: Done: 发现设备的名称S39
             * 2019-07-21 03:37:24.197 10780-10780/com.example.cpr_1 D/test: Done: 发现设备的名称Bluetooth Keyboard HT7.1
             * 2019-07-21 03:37:24.202 10780-10780/com.example.cpr_1 D/test: Done: 发现设备的名称Air plus
             * 2019-07-21 03:37:24.205 10780-10780/com.example.cpr_1 D/test: Done: 发现设备的名称BB8
             * 2019-07-21 03:37:24.209 10780-10780/com.example.cpr_1 D/test: Done: 发现设备的名称fang-TM1801
             * 2019-07-21 03:37:37.772 10780-10780/com.example.cpr_1 D/test: Done: 发现设备的名称S39
             * 2019-07-21 03:37:37.777 10780-10780/com.example.cpr_1 D/test: Done: 发现设备的名称Bluetooth Keyboard HT7.1
             * 2019-07-21 03:37:37.785 10780-10780/com.example.cpr_1 D/test: Done: 发现设备的名称Air plus
             * 2019-07-21 03:37:37.796 10780-10780/com.example.cpr_1 D/test: Done: 发现设备的名称BB8
             * 2019-07-21 03:37:37.799 10780-10780/com.example.cpr_1 D/test: Done: 发现设备的名称fang-TM1801
             *
             *蓝牙的名称
             *
             *
             */
            sd.setFindDeviceListener(new IFindDeviceListener() {

                @Override
                public void Done(final DeviceEvent me) {
                    // TODO Auto-generated method stub
                    String name = me.getName();
                    Log.d(TAG, "Done: 发现设备的名称" + name);
                    if (name == null)
                        return;
                    /**
                     * 代表连接的是打印机
                     */
                    if (name.equals(printName)) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        connectPrint(me.getMac());
                    }
                    /**
                     * 代表连接的是模型人
                     */
                    if (name.equalsIgnoreCase(modelName)) {

//                        AlertDialog.Builder builder =
//                                new AlertDialog.Builder(context).setTitle("连接蓝牙")
//                                        .setMessage("是否准备连接蓝牙设备" + me.getName() + "?")
//                                        .setCancelable(false)
//                                        .setPositiveButton("连接", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                //进行连接。传递mac地址
//                                                connectModel(me.getMac());
//                                            }
//                                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        dialogInterface.cancel();
//                                    }
//                                });
//                        builder.create().show();

                        //进行连接。传递mac地址
                        connectModel(me.getMac());

                    }
                }
            });
        }
        try {
            sd.start();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void rework() {
        mAutoConnectTimes = 10;
        pAutoConnectTimes = 10;
        stop();
        doWork();
    }

    public void stop() {
        if (print != null)
            print.stop();
    }

    public void addOb(IObBt ob) {

        obList.add(ob);
    }

    /**
     * 设置蓝牙设备状态监听对象
     *
     * @param ob
     */
    public void setOb(IObBt ob) {
        obList.clear();
        obList.add(ob);
    }

    public void removeOb(IObBt ob) {
        obList.remove(ob);

    }

    /**
     * 连接模型人
     *
     * @param mac 蓝牙设备的Adddress
     */
    private void connectModel(String mac) {

        /**
         *如果已经连接，就不在连接
         */
        if (AppConfig.getInstance().ModelConnected)
            return;

        final Client conn = new Client(mac);

        /**
         *
         * 下面代码的注释 当进行蓝牙连接完成之后，
         * 创建 MessageDevice 进行
         *
         *
         */
        conn.setConnectedListener(new IConnectedListener() {
            @Override
            public void Done(ConnectedEvent me) {
                // TODO Auto-generated method stub
                MessageDevice md;
                try {
                    md = new MessageDevice(me.getBtSocket());
                    md.start();
                    notifyMsg(BtMsgType.modelConnected, md);
                    modelConnected = true;
                    if (print != null && !printConnected)
                        print.connect();

                    /**
                     * 设置断开连接的监听
                     */
                    md.setBreakListener(new IBreakListener() {

                        @Override
                        public void Done() {
                            modelConnected = false;
                            //退出连接 返回设备信息为null，因为套接字为null
                            notifyMsg(BtMsgType.modelMiss, null);
                        }
                    });
                } catch (Exception e) {
                    // TODO Auto-generated catch block

                    LogUtil.WriteLog(e);
                }
            }
        });

        /**
         * 设置失败连接的回调
         *
         */
        conn.setFailListener(new IConnectedListener() {

            @Override
            public void Done(ConnectedEvent me) {
                try {
                    modelConnected = false;
                    mAutoConnectTimes--;
                    if (mAutoConnectTimes > 0)
                        conn.connection();
                } catch (Exception e) {
                    /**
                     *
                     */
                    e.printStackTrace();
                    notifyMsg(BtMsgType.modelConnectFaild, null);
                }
            }
        });
        try {
            /**
             * 进行蓝牙的连接
             */
            conn.connection();
        } catch (Exception e) {

        }
    }

    /**
     * 发送消息类型
     *
     * @param msgType
     * @param o
     */
    private void notifyMsg(BtMsgType msgType, Object o) {
        /**
         * 不用找了 这个ob是 MainActivity 在#{{@link com.kangren.cpr.MainActivity}}
         * 中调用了 #{setOb()}方法，传递了this
         */
        for (IObBt ob : obList) {
            ob.notify(msgType, o);
        }
    }

    private void connectPrint(String mac) {
        if (AppConfig.getInstance().PrintConnected)
            return;
        print = new Print(context, mac);
        print.setIConnectedListener(new IPrintConnectedListener() {

            @Override
            public void Done() {
                // TODO Auto-generated method stub
                notifyMsg(BtMsgType.printConnected, print);
            }
        });
        print.setIFailToPrintConnectedListener(new IPrintConnectedListener() {

            @Override
            public void Done() {
                // TODO Auto-generated method stub
                notifyMsg(BtMsgType.printConnectFaild, null);

                if (mAutoConnectTimes > 0)
                    if (modelConnected && pAutoConnectTimes > 0)
                        print.connect();
            }
        });
        print.connect();
    }


}

