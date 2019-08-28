package com.lyc.socket.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;


/**
 * 连接服务器的obj
 * 用于实现数据图送到服务器的utp通信程序的实现
 */
public class Client {

    private static Client client;
    public static String ip;
    public static int port;


    public static Client getInstance() {

        if (client == null) {
            client = new Client();

        }

        return client;
    }


    public int state = 0;
    private Socket socket;

    private ClientStateChangeListener stateChangeListener;
    private OutputStream os;
    private InputStream ins;

    private Client() {

    }

    private void connect() {
        state = 3;//connecting
        socket = new Socket();

        SocketAddress ad = new InetSocketAddress(ip, port);

        try {
            socket.connect(ad, 5000);
            os = socket.getOutputStream();
            ins = socket.getInputStream();
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    while (true) {
                        try {
                            ins.read();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            setBreak();
                            break;
                        }
                    }
                }
            }).start();


            state = 1;
            if (stateChangeListener != null)
                stateChangeListener.stateChange(state);
        } catch (IOException e) {
            setBreak();
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void setBreak() {

        state = 0;
        if (stateChangeListener != null)
            stateChangeListener.stateChange(state);

    }

    public boolean isConnected() {

        return state == 1;
    }

    public void reconnect() {
        connect();
    }

    public boolean send(byte[] buffer) {
        try {
            if (state == 1) {
                os.write(buffer);
                os.flush();
                return true;
            }
            return false;
        } catch (IOException e) {
            setBreak();
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 设置状态改变的监听
     *
     * @param stateChangeListener
     */
    public void setStateChangeListener(ClientStateChangeListener stateChangeListener) {
        this.stateChangeListener = stateChangeListener;
    }


}
