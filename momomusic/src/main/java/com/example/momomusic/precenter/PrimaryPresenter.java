package com.example.momomusic.precenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.example.momomusic.R;
import com.example.momomusic.activity.ParentActivity;
import com.example.momomusic.activity.PrimaryActivity;
import com.example.momomusic.activity.ui.PrimaryView;
import com.example.momomusic.fragment.ParentFragment;
import com.example.momomusic.tool.XmlParseUtil;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class PrimaryPresenter extends BasePresenter<PrimaryView> implements XmlParseUtil.ParseXMLListener {


    private XmlParseUtil xmlParseUtil;

    private String fileName = "fragmentList.xml";

    private String className;

    private ParentActivity context;

    @Inject
    public PrimaryPresenter() {
        xmlParseUtil = new XmlParseUtil();
    }


//    @Deprecated
//    public void daymicFragment(PrimaryActivity primaryActivity) throws KeyNotValueException {
//
//        context = primaryActivity;
//        Intent intent = primaryActivity.getIntent();
//
//        className = intent.getStringExtra(PrimaryActivity.INTENT_KEY);
//
//
//        if (TextUtils.isEmpty(className)) {
//            throw new KeyNotValueException("PrimaryActivity启动的时候传递的Extra为空");
//        }
//        xmlParseUtil.setParseXMLListener(this);
//        xmlParseUtil.parseXML(primaryActivity, fileName);
//    }


    /**
     * 这个接口是为了取代之前的接口，之前的借口在设计上面较为复杂，不建议使用
     * #{daymicFragment}
     *
     * @param primaryActivity
     * @param
     * @throws KeyNotValueException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void daymicFragment(PrimaryActivity primaryActivity) throws KeyNotValueException, InstantiationException, IllegalAccessException {

        context = primaryActivity;
        Intent intent = primaryActivity.getIntent();

        Class aClass = (Class) intent.getSerializableExtra(PrimaryActivity.INTENT_KEY);
        if (aClass == null) {
            throw new KeyNotValueException("PrimaryActivity启动的时候传递的Extra为空");
        }

        Fragment fragment = (Fragment) aClass.newInstance();
        getView().replaceFragment((ParentFragment) fragment);
    }


    @Override
    public boolean parsering(String value) {
        boolean has = false;
        if (className.equals(value)) {
            try {
                Class fragmentClass = Class.forName(value);
                Fragment fragment = (Fragment) fragmentClass.newInstance();
                getView().replaceFragment((ParentFragment) fragment);
                has = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return has;
    }


    /**
     * 传递的key的值为空的异常
     */
    public class KeyNotValueException extends Exception {

        public KeyNotValueException(String message) {
            super(message);
        }
    }


}
