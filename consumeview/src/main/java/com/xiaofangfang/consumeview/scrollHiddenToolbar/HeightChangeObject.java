package com.xiaofangfang.consumeview.scrollHiddenToolbar;

import android.view.View;
import android.view.ViewGroup;

public class HeightChangeObject {

    ViewGroup.MarginLayoutParams ml;

    public HeightChangeObject(View view) {

        ml = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
    }

    public void setHeight(int height) {
        ml.height = height;
    }

    public int getHeight() {
        return ml.height;
    }

}
