package com.xiaofangfang.butterknitedemo.NetworkEncope;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xiaofangfang.butterknitedemo.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MusicFragment extends BaseFragment {


    private String url = "http://10.109.3.112:8080/LoginTest/gainMusicList";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        view.findViewById(R.id.obtain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNetwork(url);
            }
        });
    }

    @Override
    public void failth(Exception e) {

    }

    @Override
    public void success(String response) {
        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
    }
}
