package com.example.componentasystemtest.editText_inputManager_trans;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.componentasystemtest.R;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

public class InputManager_Activity extends AppCompatActivity {


    private static final String TAG = "test";
    @BindViews({R.id.code1, R.id.code2, R.id.code3, R.id.code4})
    public List<EditText> codes;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_manager);
        ButterKnife.bind(this);


        for (int i = 0; i < codes.size(); i++) {
            codes.get(i).setFocusableInTouchMode(false);
            codes.get(i).addTextChangedListener(tw);
        }

        codes.get(currentFocusIndex).setFocusableInTouchMode(true);

    }


    private String code = "";

    private TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().length() == 1) {

                codes.get(currentFocusIndex).clearFocus();
                codes.get(currentFocusIndex).setFocusableInTouchMode(false);

                code += codes.get(currentFocusIndex).getText();
                currentFocusIndex++;
                Log.d(TAG, "当前索引: " + currentFocusIndex);//3
                if (currentFocusIndex < codes.size()) {
                    codes.get(currentFocusIndex).setFocusableInTouchMode(true);
                    codes.get(currentFocusIndex).requestFocus();
                }


                if (currentFocusIndex == codes.size()) {
                    //在这里进行提交 ，最后一个edittext
                    Log.d(TAG, "afterTextChanged: " + code);
                }

            }
        }
    };


    private int currentFocusIndex = 0;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        Log.d(TAG, "onKeyDown: " + keyCode);

        if (keyCode == KeyEvent.KEYCODE_DEL) {
            for (int i = 0; i < codes.size(); i++) {
                if (codes.get(i).isFocused()) {
                    currentFocusIndex = i;
                    codes.get(i).clearFocus();
                    codes.get(i).setFocusableInTouchMode(false);
                }
            }
        }

        if (currentFocusIndex > 0) {
            currentFocusIndex--;
            codes.get(currentFocusIndex).setFocusableInTouchMode(true);
            codes.get(currentFocusIndex).requestFocus();
            codes.get(currentFocusIndex).setText("");
        }


        return super.onKeyDown(keyCode, event);
    }
}
