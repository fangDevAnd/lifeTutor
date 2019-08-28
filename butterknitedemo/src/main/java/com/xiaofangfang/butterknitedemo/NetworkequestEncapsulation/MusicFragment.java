package com.xiaofangfang.butterknitedemo.NetworkequestEncapsulation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.xiaofangfang.butterknitedemo.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


// MVP   Fragmetn 与activity的通信问题 ，我们


public class MusicFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_net, null);

        return view;
    }


    private Button button;

    private String url = "http://10.109.3.112:8080/LoginTest/Login";


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        button = view.findViewById(R.id.login);

        button.setOnClickListener((v) -> {

            //触发点击事件
            Toast.makeText(getActivity(), "点击了", Toast.LENGTH_SHORT).show();

            sendNetwork(url);
        });

    }


    @Override
    public void failure(Exception e) {

    }

    @Override
    public void success(String string) {


    }
}
