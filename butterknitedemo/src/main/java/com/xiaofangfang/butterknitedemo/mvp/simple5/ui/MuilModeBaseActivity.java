package com.xiaofangfang.butterknitedemo.mvp.simple5.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xiaofangfang.butterknitedemo.mvp.simple5.base.BasePresenter_5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public abstract class MuilModeBaseActivity<H extends HashMap<V, P>, V extends BaseView, P extends BasePresenter_5<V>> extends AppCompatActivity {

    private H hash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (Map.Entry<V, P> entry : hash.entrySet()) {

//            builder.add(entry.getKey(), entry.getValue());


            if (entry.getValue() == null) {
                entry.setValue(createPresenter());
            }

//            if (entry.getKey() == null) {
//                this.se = createView();
//            }

//            if (this.presenter != null && this.view != null) {
//                this.presenter.attacgView(this.view);
//            }
        }

    }


    public abstract V createView();


    public abstract P createPresenter();


    private void demo1() {

    }


}