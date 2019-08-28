package com.rcs.nchumanity.ul;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.rcs.nchumanity.entity.PersistenceData;
import com.rcs.nchumanity.tool.Tool;

/**
 * 该类的主要功能是用来实现登录跳转的
 * <p>
 * 对于部分的接口，我们必须进行登陆之后才能进行跳转，大量的Activity需要这个的实现，如果自己每一个界面都去编写这样的代码，效率很低
 * <p>
 * 当然下面给出另一种方案，使用  Aspect 面向切面编程
 */
public class AssessLoginActivity extends ParentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PersistenceData.getUserId(this).equals(PersistenceData.DEF_USER)) {
            Tool.startActivity(this, RegisterUserActivity.class);
        }
    }

}
