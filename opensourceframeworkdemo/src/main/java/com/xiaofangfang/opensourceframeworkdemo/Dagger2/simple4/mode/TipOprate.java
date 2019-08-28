package com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.mode;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

//import com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.component.DaggerContextComponent;
import com.xiaofangfang.opensourceframeworkdemo.Dagger2.simple4.module.ContextModule;
import com.xiaofangfang.opensourceframeworkdemo.R;

import javax.inject.Inject;

public class TipOprate {

    @Inject
    public Context context;


    public TipOprate(Context context) {

//        DaggerContextComponent.builder().contextModule(new ContextModule(context)).build().inject(this);

    }

    public void tipAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setIcon(R.drawable.ic_launcher_background)
                .setTitle("哈哈")
                .setMessage("你好!人类")
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void toast() {
        Toast.makeText(context, "哈哈你好啊", Toast.LENGTH_SHORT).show();
    }

}
