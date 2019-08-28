package com.example.componentasystemtest.dialog.simple1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.componentasystemtest.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;


/**
 * 使用DialogFragment来创建一个dialog
 * 其中MyDialogFragment就是继承了DialogFragment的案例，具有了创建dialig的能力
 */
public class DialogActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

    }

}
