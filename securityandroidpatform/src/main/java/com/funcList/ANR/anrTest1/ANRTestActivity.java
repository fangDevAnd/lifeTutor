package com.funcList.ANR.anrTest1;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.securityandroidpatform.R;
import com.funcList.ANR.ANRBaseActivity;


/**
 * 下面是抛出的异常
 * Reason: Input dispatching timed out (Waiting to send non-key event because the touched window has not finished processing certain input events that were delivered to it over 500.0ms ago.  Wait queue length: 2.  Wait queue head age: 22381.9ms.)
 * Parent: com.example.securityandroidpatform/com.funcList.ANR.anrTest1.ANRTestActivity
 * Load: 0.58 / 0.18 / 0.05
 * CPU usage from 12830ms to 0ms ago (2019-06-28 00:45:05.698 to 2019-06-28 00:45:18.527):
 * 99% 12459/com.example.securityandroidpatform: 98% user + 0.7% kernel / faults: 1263 minor 24 major
 * 6.3% 1934/system_server: 4.5% user + 1.7% kernel / faults: 4896 minor 42 major
 * 5.1% 2098/com.android.systemui: 4.1% user + 1% kernel / faults: 3851 minor 11 major
 * 4.5% 1719/surfaceflinger: 3.5% user + 0.9% kernel / faults: 480 minor
 * 0.9% 1698/android.hardware.audio@2.0-service: 0% user + 0.9% kernel
 * 0.7% 2172/com.android.phone: 0.4% user + 0.2% kernel / faults: 1062 minor 6 major
 * 0.5% 1716/audioserver: 0% user + 0.5% kernel / faults: 228 minor
 * 0.5% 1736/adbd: 0.3% user + 0.2% kernel / faults: 4 minor
 */

public class ANRTestActivity extends ANRBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anr_test1_1);

//         getApplication("").setDebug(true);
    }


    public void click(View view) {



        while (true) {

        }

    }
}
