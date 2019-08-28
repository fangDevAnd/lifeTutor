package com.example.momomusic.activity;

import android.content.ServiceConnection;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.momomusic.R;
import com.example.momomusic.activity.ui.MusicPlayView;
import com.example.momomusic.activity.ui.PrimaryView;
import com.example.momomusic.fragment.ParentFragment;
import com.example.momomusic.precenter.MusicPlayPresenter;
import com.example.momomusic.precenter.PrimaryPresenter;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 该界面被命名为main，不是因为该界面是程序的启动界面，而是该界面承当的是界面的主activity ，如果可能我们竟可能的将所有的页面 全部使用fragment去实现
 */
public class PrimaryActivity extends BaseActivity<PrimaryView, PrimaryPresenter> implements PrimaryView {

    //每一个启动primaryActivity都要传递当前的key，值为fragment的className
    public static final String INTENT_KEY = "class";
    public static final String BUNDLE = "bundle";


    @BindView(R.id.replaceArea)
    FrameLayout frameLayout;

//    @Inject
//    PrimaryPresenter primaryPresenter;   由于加载问题导致空指针，暂时不在使用


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);
        ButterKnife.bind(this);//必须在之前写
//        DaggerPrimaryActivityComponent.create().inject(this);

        setBundle(getIntent().getBundleExtra(BUNDLE));//设置传递过来的bundle
        try {
            getPresenter().daymicFragment(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PrimaryView createView() {
        return this;
    }

    @Override
    public PrimaryPresenter createPresenter() {
        return new PrimaryPresenter();
    }


    @Override
    public void replaceFragment(ParentFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Bundle bundle = getBundle();
        if (bundle == null) {
            bundle = new Bundle();
        }
        fragment.setBundle(bundle);
        ft.replace(R.id.replaceArea, fragment);
        ft.commit();
    }


}
