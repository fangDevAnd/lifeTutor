package com.xiaofangfang.rice2_verssion.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaofangfang.rice2_verssion.R;
import com.xiaofangfang.rice2_verssion.activity.InternetManyou;
import com.xiaofangfang.rice2_verssion.activity.MessageWork;
import com.xiaofangfang.rice2_verssion.activity.ServerTatour;
import com.xiaofangfang.rice2_verssion.activity.ShortMessageTatour1;
import com.xiaofangfang.rice2_verssion.activity.ShortMessageTatour2;
import com.xiaofangfang.rice2_verssion.view.CommandBar;

import java.io.IOException;

import okhttp3.Response;

public class TutorFragment extends ParentFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tutor_fragment, null, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        View[] views = {
                view.findViewById(R.id.tatour1),
                view.findViewById(R.id.tatour2),
                view.findViewById(R.id.message),
                view.findViewById(R.id.serverTatour),
                view.findViewById(R.id.internetManyou)
        };

        Class[] classSet = {

                ShortMessageTatour1.class,
                ShortMessageTatour2.class,
                MessageWork.class,
                ServerTatour.class,
                InternetManyou.class
        };

        for (int i = 0; i < classSet.length; i++) {

            int finalI = i;
            views[i].setOnClickListener((v) -> {
                Intent intent = new Intent(getActivity(), classSet[finalI]);
                startActivity(intent);
            });
        }

        CommandBar com = view.findViewById(R.id.commandBar);
        com.setTitle("通讯指导");
        com.hiddenBack();
    }

    @Override
    public void onError(IOException e, String what) {

    }

    @Override
    public void onSucess(Response response, String what, String... backData) {

    }


}
