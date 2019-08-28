package com.rcs.nchumanity.dialog;

import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;

public interface DialogSet {

    void setText(@IdRes int id, String text);

    void setClickListener(@IdRes int id, View.OnClickListener click);

    void setAsyncImage(@IdRes int id, @DrawableRes int res);
}
