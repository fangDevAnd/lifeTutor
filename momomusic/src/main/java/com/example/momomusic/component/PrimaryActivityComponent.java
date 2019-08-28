package com.example.momomusic.component;

import com.example.momomusic.activity.PrimaryActivity;

import dagger.Component;

@Component
public interface PrimaryActivityComponent {

    void inject(PrimaryActivity primaryActivity);
}
