package com.xiaofangfang.butterknitedemo.mvp.simple6;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xiaofangfang.butterknitedemo.R;
import com.xiaofangfang.butterknitedemo.mvp.simple5.presenter.LoginPresenter_5;
import com.xiaofangfang.butterknitedemo.mvp.simple5.ui.LoginView_5;

public class LoginFragment1 extends BaseFragment<LoginView_5, LoginPresenter_5> implements LoginView_5 {


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.send).setOnClickListener((v) -> {
            getPresenter().login("asfasf", "121324");
        });
    }

    @Override
    public void onLoginResult(String result) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "返回" + result, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public LoginPresenter_5 createPresenter() {
        return new LoginPresenter_5();
    }

    @Override
    public LoginView_5 createView() {
        return this;
    }
}
