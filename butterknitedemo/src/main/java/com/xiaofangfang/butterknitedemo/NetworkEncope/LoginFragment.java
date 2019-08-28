package com.xiaofangfang.butterknitedemo.NetworkEncope;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xiaofangfang.butterknitedemo.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import okhttp3.OkHttpClient;

public class LoginFragment extends BaseFragment {


    private String url = "http://10.109.3.112:8080/LoginTest/Login";


    // mvp  precenter


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_net, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "执行了点击事件", Toast.LENGTH_SHORT).show();
                openNetwork(url);//ipconfig     ifconfig
            }
        });
    }

    @Override
    public void failth(Exception e) {

        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void success(String response) {
        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
    }
}
