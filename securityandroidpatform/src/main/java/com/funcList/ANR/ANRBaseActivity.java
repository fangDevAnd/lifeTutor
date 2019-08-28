package com.funcList.ANR;

import androidx.appcompat.app.AppCompatActivity;

public class ANRBaseActivity extends AppCompatActivity {

    public ANRApplication getApplication(String... v) {
        return (ANRApplication) getApplication();
    }




}
