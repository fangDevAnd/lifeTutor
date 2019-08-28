package com.xiaofangfang.butterknitedemo.NetworkEncopeSuper;

import android.app.Service;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xiaofangfang.butterknitedemo.R;
import com.xiaofangfang.butterknitedemo.butter.ButterKniferActivity;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LoginFragment extends BaseFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        view.findViewById(R.id.send).setOnClickListener((v) -> {

            openNetwork("http://192.168.43.170:8080/LoginTest/Login", 12);

        });

        view.findViewById(R.id.send2).setOnClickListener((v) -> {
            openNetwork("http://192.168.43.170:8080/LoginTest/Login", 10);
        });


        view.findViewById(R.id.send3).setOnClickListener((v) -> {

            //null
//            getMyActivity().openNetwork("http://192.168.43.170:8080/LoginTest/Login", 12);  //ondattach

            context.openNetwork("http://192.168.43.170:8080/LoginTest/Login", 12);

            //activity  --->fragment--->fragment


            //context     activity是context的子类
            //service
//           context的数量  ==activity个数+service的个数+Application

        });


        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void failth(Exception e, int what) {

        //EventBus

        //broadcast


    }

    class Event {

    }


    @Override
    public void success(String response, int what) {


        if (what == 12) {
            Toast.makeText(getActivity(), response + "\t按钮一的请求", Toast.LENGTH_SHORT).show();
        }

        if (what == 10) {
            Toast.makeText(getActivity(), response + "\t按钮二的请求", Toast.LENGTH_SHORT).show();
        }

        if (what == 3456) {
            Toast.makeText(getActivity(), response + "\t接收到activity的请求结果", Toast.LENGTH_SHORT).show();
        }
    }
}
