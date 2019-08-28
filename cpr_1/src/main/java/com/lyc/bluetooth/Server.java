package com.lyc.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;

/**
 * 该类没有被使用
 */
@SuppressLint("NewApi")
public class Server {
    BluetoothServerSocket serverSocket;
    private BluetoothAdapter btAdapt = null;
    private IConnectedListener connectedListener;
    private boolean isListening = false;

    public Server() {
        btAdapt = BluetoothAdapter.getDefaultAdapter();

        //	setDiscoverableTimeout(1000);
    }

    public void listen() throws Exception {

        if (btAdapt.getState() != BluetoothAdapter.STATE_ON) {// ���������û����
            throw new Exception("blueTooth is not opened");
        }
        if (btAdapt == null) {
            throw new Exception("blueTooth adapter is null point");

        }
        if (btAdapt.getState() != BluetoothAdapter.STATE_ON) {
            throw new Exception("blueTooth is not opened");
        }
        isListening = true;
        if (!btAdapt.isEnabled()) {

            //�����Ի�����ʾ�û��Ǻ��
            //	Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            //startActivityForResult(enabler, BluetoothAdapter.ACTION_REQUEST_ENABLE);

            //������ʾ��ǿ�д�

            btAdapt.enable();


        }
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                accept();
            }
        }).start();

    }

    public void setIConnectedListener(IConnectedListener connectedListener) {
        this.connectedListener = connectedListener;
    }

    public void stop() {
        isListening = false;
        if (serverSocket != null)
            try {

                serverSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    private void accept() {
        BluetoothSocket btSocket = null;

        try {
            btAdapt.cancelDiscovery();
            serverSocket = btAdapt.listenUsingInsecureRfcommWithServiceRecord("smallPad", BluetoothToolConfig.PRIVATE_UUID);

            while (isListening) {
                btSocket = serverSocket.accept();

                if (connectedListener != null) {
                    ConnectedEvent me = new ConnectedEvent(btSocket, btSocket.getRemoteDevice().getName());
                    connectedListener.Done(me);
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block n
            e.printStackTrace();
        }

    }

}
