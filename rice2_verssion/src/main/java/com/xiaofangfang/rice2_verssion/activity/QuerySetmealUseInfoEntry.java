package com.xiaofangfang.rice2_verssion.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiaofangfang.rice2_verssion.ParentActivity;
import com.xiaofangfang.rice2_verssion.R;

/**
 * 这个界面的作用是进入到关注公众号的流程介绍，在我们的套餐推荐输入套餐里面我门如果不知道自己的使用套餐的情况，这个就是套餐查询信息的界面，不过
 * 该界面不能实现真正的查询套餐的数据，只是通过关注微信公众号来实现的
 */
public class QuerySetmealUseInfoEntry extends ParentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.query_setmeal_use_info_entry);
        super.onCreate(savedInstanceState);
    }

    int[] imgAddress = {
            R.drawable.weixin1,
            R.drawable.weixin2,
            R.drawable.weixin3,
            R.drawable.weixin4
    };

    int[] imgViews = {
            R.id.weixin1,
            R.id.weixin2,
            R.id.weixin3,
            R.id.weixin4
    };


    public void initView() {

        for (int i = 0; i < imgAddress.length; i++) {
            Glide.with(this).load(imgAddress[i]).into((ImageView) findViewById(imgViews[i]));
        }

    }
}
