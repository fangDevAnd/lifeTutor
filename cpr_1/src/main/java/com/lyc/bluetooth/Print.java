package com.lyc.bluetooth;


import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import btmanager.ConnectThread;
import btmanager.Pos;

/**
 *打印机的实体类模型
 */
public class Print {

    private String mac;
    public Context context;
    private BroadcastReceiver broadcastReceiver;
    private IPrintConnectedListener iPrintConnectedListener,
            iFailToPrintConnectedListener;

    public Print(Context context, String mac) {
        this.mac = mac;
        this.context = context;

        regReceiver();

    }


    public Print() {

    }

    public void connect() {

        try {
            Pos.APP_Init(context);

        } catch (Exception e) {
        }
        Pos.POS_Open(mac);
    }

    public void write(String font) {

        Pos.POS_S_TextOut(font, 0, 0, 0, 0x01, 0x00);


        Pos.POS_FeedLine();
    }

    public void writePic(String picPath) {

        Bitmap bitmap = BitmapFactory.decodeFile(picPath);
        Pos.POS_PrintPicture(bitmap, 370, 0x00);

        Pos.POS_FeedLine();
    }

    public void stop() {
        this.context.unregisterReceiver(broadcastReceiver);
        Pos.POS_Close();
        Pos.APP_UnInit();
    }

    public void setIFailToPrintConnectedListener(
            IPrintConnectedListener iFailToPrintConnectedListener) {
        this.iFailToPrintConnectedListener = iFailToPrintConnectedListener;
    }

    public void setIConnectedListener(
            IPrintConnectedListener iPrintConnectedListener) {
        this.iPrintConnectedListener = iPrintConnectedListener;
    }

    private void regReceiver() {
        if (broadcastReceiver != null)
            return;
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                String action = intent.getAction();
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (action.equals(ConnectThread.ACTION_CONNECTED)) {

                    if (iPrintConnectedListener != null) {
                        iPrintConnectedListener.Done();
                    }
                } else if (action.equals(ConnectThread.ACTION_DISCONNECTED)) {
                    if (iFailToPrintConnectedListener != null) {

                        iFailToPrintConnectedListener.Done();
                    }

                } else if (action.equals(ConnectThread.ACTION_STARTCONNECTING)) {

                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectThread.ACTION_DISCONNECTED);
        intentFilter.addAction(ConnectThread.ACTION_CONNECTED);
        intentFilter.addAction(ConnectThread.ACTION_STARTCONNECTING);

        try {
            this.context.registerReceiver(broadcastReceiver, intentFilter);
        } catch (Exception ex) {
        }
    }

    public static interface IPrintConnectedListener {
        void Done();
    }


}
