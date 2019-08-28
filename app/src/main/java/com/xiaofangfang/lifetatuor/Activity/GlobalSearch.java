package com.xiaofangfang.lifetatuor.Activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.xiaofangfang.lifetatuor.R;

public class GlobalSearch extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.globalsearch);
        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }


    class MyLocationDialog extends Dialog {


        public MyLocationDialog(@NonNull Context context) {
            super(context);
            setContentView(R.layout.location_select);
            initView();
        }

        private void initView() {
            findViewById(R.id.backLocation);

        }
    }
}
