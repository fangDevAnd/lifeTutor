package com.example.componentasystemtest.dialog.Simple2;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.componentasystemtest.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


/**
 * 默认的dialog都是有边距的，不能和屏幕的宽度重合，通过案例测试能不能实现alertdialog的宽度为屏幕的width值的显示效果
 */
public class DialogWidthTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dialog_width_test);


    }

    public void onClick(View view) {


        if (view.getId() == R.id.openDialog) {

            /**
             * 我们通过style进行配置，发现并不能做到实际想要的效果
             *
             */
            AlertDialog alertDialog = new AlertDialog.Builder(this, android.R.style.ThemeOverlay_Material_Dialog_Alert)
                    .setMessage("提示")
                    .setPositiveButton("取消", (dialog, which) -> dialog.cancel())
                    .setNegativeButton("确定", (dialog, which) -> dialog.cancel())
                    .setCancelable(true)
                    .create();

            WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
            lp.width = getWindowManager().getDefaultDisplay().getWidth();
            alertDialog.getWindow().setAttributes(lp);

            alertDialog.show();
        }


        /**
         * 下面同样不能实现，那么我们应该去猜想一下dialog的宽度到底是谁去进行控制的
         *
         */
        if (view.getId() == R.id.openDialog1) {


            Dialog dialog = new Dialog(this);
            dialog.setCancelable(true);

            TextView textView = new TextView(dialog.getContext());


            textView.setText("asgqegqegqegq");

            dialog.setContentView(textView);

            dialog.show();

/**
 * 调整布局的代码只能通过show之后进行设置，不然没有效果，猜测与setContentView有关，重新对布局进行了测量和布局的关系
 */
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = getResources().getDisplayMetrics().widthPixels;
            dialog.getWindow().setAttributes(lp);


        }


        /**
         * 这个可以实现,但是不是dialog的实现,况且实现的效果不行
         */
        if (view.getId() == R.id.openDialog2) {


            WindowManager windowManager = getWindowManager();
            //设置弹出的窗口的高度的值为屏幕大小的3/4
            float height = getResources().getDisplayMetrics().heightPixels * 3 / 4;

            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, (int) height, WindowManager.LayoutParams.TYPE_APPLICATION,
                    WindowManager.LayoutParams.FLAG_DITHER, PixelFormat.RGBA_8888
            );
//            layoutParams.windowAnimations = R.style.windowanim;//设置动画
            layoutParams.dimAmount = 0.4f;
            layoutParams.gravity = Gravity.BOTTOM;
            view1 = LayoutInflater.from(this).inflate(R.layout.dialog_content, null, false);
            eventProgress(view1);//事件处理函数
            windowManager.addView(view1, layoutParams);

        }


        /**
         * 可以实现的效果
         *
         *
         *
         *具体的关于设置Dialog的样式,见R.style.CardDialogStyle
         *
         *
         */
        if (view.getId() == R.id.openDialog4) {
            Dialog dialog = new Dialog(this, R.style.CardDialogStyle);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialog_content);
            /**
             * dialog的顶层布局是FrameLayout,这个布局默认带有12dp的padding
             * 你所看到的dialog默认是不能全屏显示的原因就在这里,因为背景原因
             */
            dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
//            getWindow().setDimAmount(0.5f);//设置暗淡的量
            /**
             * 我们发现了,上面所说的是正确的,那么通过设置背景,就能将dectorView的确定大小确定下来
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                dialog.getWindow().getDecorView().setBackground(getResources().getDrawable(R.drawable.corner_bg_white));
            }
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, (int) 300, WindowManager.LayoutParams.TYPE_APPLICATION,
                    WindowManager.LayoutParams.FLAG_DITHER, PixelFormat.RGBA_8888
            );
//            layoutParams.dimAmount=0.4f;//感觉没啥用啊
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.windowAnimations = R.style.window_anim;//小心使用的style错误
            dialog.getWindow().setAttributes(layoutParams);
            dialog.show();
        }


        if (view.getId() == R.id.openDialog3) {
            openDialog(this, R.layout.dialog_content, true, true, Gravity.BOTTOM, R.style.window_anim, R.drawable.corner_bg_white);
        }


        if (view.getId() == R.id.openDialog5) {//一个带有listview的dialog

            List<String> menu = new ArrayList<>();
            menu.add("最近的播放曲目");
            menu.add("最近的播放曲目");
            menu.add("最近的播放曲目");


            View view2 = LayoutInflater.from(this).inflate(R.layout.dialog_listview, null);

            /**
             * 还必须使解析后view进行findView 不能使用dialog,同样会报空指针异常
             */

            //在这里进行动画的测量
            view2.findViewById(R.id.container).addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    Toast.makeText(DialogWidthTestActivity.this, "布局发生变换", Toast.LENGTH_SHORT).show();
                    if (!isM) {
                        bottom1 = bottom;
                        isM = true;
                    }
                    Log.d("test", "onLayoutChange: " + bottom);

                    if (Math.abs(bottom1 - bottom) > 100) {
                        /**
                         * 下面是第一种实现方式
                         */
                        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
                        lp.height = bottom1 + 100;
                        v.setLayoutParams(lp);
                    }
                }
            });

            ArrayAdapter arrayAdapter;
            ListView listView = view2.findViewById(R.id.listView);
            listView.setAdapter(arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));

            view2.findViewById(R.id.add).setOnClickListener((v) -> {
                menu.add("最近的播放曲目");
                arrayAdapter.notifyDataSetChanged();
            });


            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setView(view2)
                    .setCancelable(true);
            AlertDialog dialog = builder.create();

            dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
            /**
             * 我们发现了,上面所说的是正确的,那么通过设置背景,就能将dectorView的确定大小确定下来
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                dialog.getWindow().getDecorView().setBackground(getResources().getDrawable(R.drawable.corner_bg_white));
            }
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, (int) -2, WindowManager.LayoutParams.TYPE_APPLICATION,
                    WindowManager.LayoutParams.FLAG_DITHER, PixelFormat.RGBA_8888
            );
            layoutParams.gravity = Gravity.BOTTOM;
            dialog.getWindow().setAttributes(layoutParams);
            dialog.show();


        }

        if (view.getId() == R.id.openDialog6) {

            View view2 = LayoutInflater.from(this).inflate(R.layout.dialog_6, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.BottomDialog)
                    .setView(view2)
                    .setCancelable(true);
            AlertDialog dialog = builder.create();
            dialog.show();

        }
        if (view.getId() == R.id.openDialog7) {

            View view2 = LayoutInflater.from(this).inflate(R.layout.dialog_7, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.BottomDialog)
                    .setView(view2)
                    .setCancelable(true);
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        if (view.getId() == R.id.openDialog8) {

            View view2 = LayoutInflater.from(this).inflate(R.layout.dialog_inner, null);
            DialogTool.openDialog(new DialogTool(this, view2) {
                @Override
                public void bindViewEvent(View view, AlertDialog dialog) {

                }
            });
        }
        
    }

    /**
     * 是不是进行了第一次测量
     */
    boolean isM = false;
    /**
     * 当前dialog的bottom的初始值
     */
    int bottom1;

    View view1;


    private void eventProgress(View view) {

        view.findViewById(R.id.click).setOnClickListener((V) -> {

            getWindowManager().removeViewImmediate(view1);
        });

    }


    /**
     * 打开一个dialog
     *
     * @param context
     * @param viewid
     * @param cancelable
     * @param isWidthScreen
     * @param position
     * @param style
     */
    public void openDialog(Context context, @LayoutRes int viewid, boolean cancelable, boolean isWidthScreen, int position, @StyleRes int style, @DrawableRes int drawableBg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(viewid)
                .setCancelable(cancelable);

        AlertDialog dialog = builder.create();
        if (isWidthScreen) {
            dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
            /**
             * 我们发现了,上面所说的是正确的,那么通过设置背景,就能将dectorView的确定大小确定下来
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                dialog.getWindow().getDecorView().setBackground(getResources().getDrawable(drawableBg));
            }
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, (int) -2, WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_DITHER, PixelFormat.RGBA_8888
        );
        layoutParams.gravity = position;
        layoutParams.windowAnimations = style;//小心使用的style错误
        dialog.getWindow().setAttributes(layoutParams);
        dialog.show();
    }

}


abstract class DialogTool {

    public void openDialog() {
        ViewGroup view2 = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.dialog_7, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.BottomDialog)
                .setView(view2)
                .setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
        ((ViewGroup) view2.findViewById(R.id.innerRoot)).addView(view);
        bindViewEvent(view, dialog);
    }

    private Context context;
    private View view;

    public DialogTool(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    public static void openDialog(DialogTool dialogTool) {
        dialogTool.openDialog();
    }


    public abstract void bindViewEvent(View view, AlertDialog dialog);


}
