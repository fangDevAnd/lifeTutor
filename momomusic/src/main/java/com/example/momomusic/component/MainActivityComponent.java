package com.example.momomusic.component;

import com.example.momomusic.activity.MainActivity;

import dagger.Component;


@Component
public interface MainActivityComponent {

    void inject(MainActivity activity);
}
