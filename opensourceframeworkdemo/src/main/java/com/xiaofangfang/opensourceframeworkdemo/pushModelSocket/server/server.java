package com.xiaofangfang.opensourceframeworkdemo.pushModelSocket.server;

import android.os.Bundle;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class server extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            initView();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initView() throws IOException {

        final ServerSocket serverSocket = new ServerSocket(8989);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket;
                while (true) {
                    try {
                        socket = serverSocket.accept();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}
