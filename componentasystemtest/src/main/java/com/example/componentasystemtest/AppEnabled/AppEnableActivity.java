package com.example.componentasystemtest.AppEnabled;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.componentasystemtest.R;


/**
 * 不能使用，直接装不上
 * <p>
 * 对于实现禁用的方式  我们使用pm命令可以实现，但是遗憾的是，我们没有权限
 * <p>
 * 首先需要进入 adb shell
 * <p>
 * 输入pm list packages | grep hwouc 回车
 *  这个是显示所有手里面安装的包名列表，grep的功能是进行过滤，hwouc是华为系统里面的更新软件包名，如果是华为手机直接可以用，如果是其他手机请百度下吧。正常来说应该出现com.huawei.android.hwouc，就说明一切正常。
 * <p>
 * 输入pm hide com.huawei.android.hwouc 回车
 *  这部命令是隐藏/冻结了hwouc软件
 * <p>
 * 输入pm list packages | grep hwouc 回车
 *  这时候就发现搜索不到软件，确定了可以隐藏/冻结了软件。
 * <p>
 * 输入exit 回车
 *   推出adb命令模式。
 * <p>
 * 输入adb reboot 回车
 *  手机会重启，等待吧。
 * ---------------------
 * 作者：玩命阿飞
 * 来源：CSDN
 * 原文：https://blog.csdn.net/starsjf/article/details/71244619
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 */
public class AppEnableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_enabled);
        init();
    }

    private void init() {
        Button bt = (Button) findViewById(R.id.dis);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText text = (EditText) findViewById(R.id.input);
                String packageName = text.getText().toString();
                PackageManager pm = getPackageManager();
                pm.setApplicationEnabledSetting(packageName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER, 0);
            }
        });


        Button en = (Button) findViewById(R.id.en);
        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText text = (EditText) findViewById(R.id.input);
                String packageName = text.getText().toString();
                PackageManager pm = getPackageManager();
                pm.setApplicationEnabledSetting(packageName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, 0);
            }
        });
    }
}
