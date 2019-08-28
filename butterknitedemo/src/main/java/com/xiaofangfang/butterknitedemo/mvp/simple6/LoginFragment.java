package com.xiaofangfang.butterknitedemo.mvp.simple6;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xiaofangfang.butterknitedemo.R;
import com.xiaofangfang.butterknitedemo.mvp.simple5.presenter.LoginPresenter_5;
import com.xiaofangfang.butterknitedemo.mvp.simple5.ui.LoginView_5;

public class LoginFragment extends Fragment implements LoginView_5 {


    private LoginPresenter_5 loginPresenter_5;

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

            loginPresenter_5 = new LoginPresenter_5();
            loginPresenter_5.attacgView(this);
            loginPresenter_5.login("asfasf", "121324");
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
    public void onDestroy() {
        super.onDestroy();
        loginPresenter_5.detachView();
    }
}
