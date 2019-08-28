package com.example.momomusic.activity.ui;

import com.example.momomusic.fragment.ParentFragment;

import androidx.fragment.app.Fragment;

public interface PrimaryView extends BaseView {

    /**
     * 动态的替换fragment
     *
     * @param fragment
     */
    public void replaceFragment(ParentFragment fragment);


}
