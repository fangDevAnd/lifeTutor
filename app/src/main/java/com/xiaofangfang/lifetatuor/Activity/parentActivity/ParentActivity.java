package com.xiaofangfang.lifetatuor.Activity.parentActivity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * 这个类用于实现对一些权限，系统功能的封住以及回调
 */
public class ParentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    /**
     * 检查自身的权限
     *
     * @param permissionList
     * @return 返回没有申请的危险权限
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public List<String> checkSelfPermission(String[] permissionList) {
        List<String> unobtain = new ArrayList<>();
        for (int i = 0; i < permissionList.length; i++) {
            if (checkSelfPermission(permissionList[i]) != PackageManager.PERMISSION_GRANTED) {
                unobtain.add(permissionList[i]);
            }
        }

        return unobtain;
    }


}
