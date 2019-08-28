package com.xiaofangfang.rice2_verssion.view;

import android.view.View;

public class OnItemClickListener implements View.OnClickListener {


    private long startTime;
    private long distance = 1000;
    private long cal;

    @Override
    public void onClick(View v) {

        cal = System.currentTimeMillis() - startTime;

        if (Math.abs(cal) < distance) {
            return;
        }
        startTime = System.currentTimeMillis();
    }

}
