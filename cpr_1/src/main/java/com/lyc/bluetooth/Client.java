package com.lyc.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;

/**
 * 代表的是当前的客户端
 * <p>
 * 提供了对蓝牙的连接操作
 * <p>
 * <p>
 * 以及对连接成功和失败的回调接口
 * <p>
 * 回调接口中 保存了当前对象和蓝牙连接的套接字，用于实现同蓝牙的通信
 */
public class Client {
    private static final String TAG = "test";
    //private Activity context;
    private String name;
    private String mac;
    private BluetoothAdapter btAdapt = null;
    private BluetoothSocket btSocket = null;
    private IConnectedListener connectedListener;
    private IConnectedListener failListener;

    private BluetoothDevice btDev;

    public Client(String mac) {
        this.mac = mac;
        //	this.context = context;
        btAdapt = BluetoothAdapter.getDefaultAdapter();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setConnectedListener(IConnectedListener connectedListener) {
        this.connectedListener = connectedListener;

    }

    public void setFailListener(IConnectedListener failListener) {
        this.failListener = failListener;

    }

    public void connection() throws Exception {

        if (this.mac == "") {
            throw new Exception("请先设置MAC地址");
        }
        if (btAdapt.getState() != BluetoothAdapter.STATE_ON) {// 如果蓝牙还没开启
            throw new Exception("蓝牙未开启");
        }
        if (btAdapt == null) {
            throw new Exception("本机无蓝牙，连接失败");

        }

        if (btAdapt.getState() != BluetoothAdapter.STATE_ON) {
            throw new Exception("本机蓝牙状态不正常，连接失败");
        }
        connect();
    }

    public void close() {
        try {
            btSocket.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 进行蓝牙的连接
     */
    private void connect() {
        btDev = btAdapt.getRemoteDevice(mac);
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                ConnectedEvent me = null;
                try {
                    Thread.sleep(500);
                    Log.d(TAG, "尝试连接远程蓝牙名称" + btDev.getName());
                    btSocket = btDev.createRfcommSocketToServiceRecord(BluetoothToolConfig.PRIVATE_UUID);
//                    btSocket = (BluetoothSocket) btDev.getClass().getMethod("createRfcommSocket", new Class[]{int.class}).invoke(btDev, 1);
                    btAdapt.cancelDiscovery();//连接前一定要取消搜索蓝牙
                    /**
                     * 先进行配对
                     */
//                    if (btDev.getBondState() == BluetoothDevice.BOND_NONE) {
//                        btDev.createBond();
//                    }
                    btSocket.connect();
                    if (btSocket != null) {
                        me = new ConnectedEvent(this, btSocket);
                        if (connectedListener != null)
                            connectedListener.Done(me);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "蓝牙连接错误信息" + e.toString() + "远程蓝牙地址" + btDev.getAddress());
                    me = new ConnectedEvent(this, null);
                    if (failListener != null)
                        failListener.Done(me);
                }
            }
        }).start();

    }
}
