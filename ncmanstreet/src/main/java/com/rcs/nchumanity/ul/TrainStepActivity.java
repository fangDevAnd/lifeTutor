package com.rcs.nchumanity.ul;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.rcs.nchumanity.R;


/**
 * 该类并没有具体的实现，因为部分功能的重合，将所有的业务步骤
 * 放到了 #{{@link com.rcs.nchumanity.fragment.JYPXFragment}}中  ，
 */
public class TrainStepActivity extends ParentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_step);
    }
}
