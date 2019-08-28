package com.example.momomusic.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.momomusic.R;
import com.example.momomusic.activity.ParentActivity;
import com.example.momomusic.dao.MusicDataDb;
import com.example.momomusic.model.Collect;
import com.example.momomusic.model.Music;
import com.example.momomusic.notification.NotificationCollect;
import com.example.momomusic.servie.PlayServiceBindService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;

/**
 * 由于程序复用，定义的已经写好布局和实现的dialog
 */
public class DialogCollect {

    public static final String[] MENU = {
            "正在播放列表",
            "我收藏的单曲",
            "新建歌单"
    };
    /**
     * 弹出的窗口里面的listView的适配器
     */
    private static ArrayAdapter<String> arrayAdapter;

    private static List<String> menuS;

    static {
        //初始化菜单
        menuS = new ArrayList<>(Arrays.asList(MENU));
    }


    public static void openCollectDialog(Context context, List<Music> selectedMusic) {
        //弹出一个窗口,选泽播放列表
        DialogTool<String> dialogTool = new DialogTool<String>() {
            @Override
            public void bindView(DialogTool<String> d, Dialog dialog, String... t) {
                ViewGroup viewGroup = d.getView();
                List<String> tables = MusicDataDb.getInstance(context).queryNewMusicSheet();//查询当前的表
                if (!menuS.containsAll(tables)) {
                    menuS.addAll(tables);
                }
                arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, menuS);
                ListView listView = viewGroup.findViewById(R.id.listView);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        switch (position) {
                            case 0://添加到正在播放
                                /**
                                 * 添加到当前额播放源
                                 */
                                PlayServiceBindService.additionalData(context, selectedMusic, false);
                                Toast.makeText(context, "添加到播放歌单", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                                break;
                            case 1://添加我的收藏
                                for (Music music : selectedMusic) {
                                    /**
                                     * 由于使用的数据库存在自动的对象判断，所以不需要我们进行再一次的对象重复添加判断
                                     */
                                    new Collect(music.getDataUrl()).save();
                                }
                                dialog.cancel();
                                Toast.makeText(context, "已被添加到收藏夹", Toast.LENGTH_SHORT).show();

                                break;
                            case 2://新建歌单
                                //弹出窗口
                                newMusicSheet(dialog);
                                break;
                            default:
                                /**
                                 * 默认情况下，需要获得当前的item的值，然后通过判断
                                 * 这个title就是当前的表的名称
                                 */
                                String title = arrayAdapter.getItem(position);
                                List<String> selectUrl = new ArrayList<>();
                                for (int i = 0; i < selectedMusic.size(); i++) {
                                    selectUrl.add(selectedMusic.get(i).getDataUrl());
                                }
                                MusicDataDb.getInstance(context).insertData(title, selectUrl);
                                dialog.cancel();
                                break;
                        }
                    }
                });
            }
        };

        Dialog dialog = dialogTool.openDialog(context, R.layout.dialog_add_to_music_dan, true,
                true, Gravity.BOTTOM, 0, R.drawable.corner_bg_white);
        dialog.show();//显示弹出菜单的抽屉栏
    }


    /**
     * 新建歌单
     *
     * @param dialogParent
     */
    private static void newMusicSheet(Dialog dialogParent) {

        DialogTool<String> dialogTool = new DialogTool<String>() {
            @Override
            public void bindView(DialogTool<String> d, Dialog dialog, String... t) {
                d.setClickListener(R.id.cancel, (v) -> {
                    //取消的点击操作
                    dialog.cancel();

                });

                d.setClickListener(R.id.submit, (v) -> {

                    ViewGroup viewGroup = d.getView();
                    EditText editText = viewGroup.findViewById(R.id.editMusicSheet);

                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                        Toast.makeText(dialog.getContext(), "请输入歌单名称", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (menuS.contains(editText.getText().toString())) {
                        Toast.makeText(dialog.getContext(), "该播放列表已经存在", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //确定的点击操作
                    MusicDataDb.getInstance(dialog.getContext()).createTable(editText.getText().toString());

                    menuS.clear();
                    menuS.addAll(new ArrayList<>(Arrays.asList(MENU)));
                    menuS.addAll(MusicDataDb.getInstance(dialog.getContext()).queryNewMusicSheet());//查询当前的表

                    arrayAdapter.notifyDataSetChanged();

                    /**
                     * 1,新建数据表
                     * 2.刷新musicS
                     * 3.需要刷新dialoagparent的数据
                     * 4.取消当前的dailog的显示
                     */
                    dialog.cancel();
                    NotificationCollect.generNoti("添加收藏", "添加收藏成功", "13", dialog.getContext());
                });

            }
        };

        Dialog dialog = dialogTool.getDialog(dialogParent.getContext(), R.layout.dialog_new_music_sheet, "");
        dialog.show();
    }


    /**
     * 显示一个警告dialog
     *
     * @param title
     * @param message
     * @param context
     * @return
     */
    public static Dialog showWarnDialog(String title, String message, Context context, EnterProgress progress) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setNegativeButton("确定", (dialog, which) -> {
                    progress.onProgre(dialog, builder);
                    dialog.cancel();
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.cancel();
                })
                .setMessage(message);
        return builder.create();

    }

    public interface EnterProgress {
        void onProgre(DialogInterface dialog, AlertDialog.Builder builder);
    }


    /**
     * 加载一个弹出窗口
     * 这个弹出的窗口的实现是通过
     * #{{@link android.app.ActivityManager}}实现的，用户体验较为差
     *
     * @param context
     * @param eventProgress
     * @param height
     * @param res
     * @param anim
     */
    public static void loadPop(Context context, EventProgress eventProgress, int height, @LayoutRes int res, @StyleRes int anim) {

        WindowManager windowManager = ((ParentActivity) context).getWindowManager();

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, (int) height, WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_DITHER, PixelFormat.TRANSPARENT
        );
        layoutParams.windowAnimations = anim;
        layoutParams.gravity = Gravity.BOTTOM;
        View view = LayoutInflater.from(context).inflate(res, null, false);
        eventProgress.eventProgress(view);
        windowManager.addView(view, layoutParams);
        //设置背景变暗
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
    public static void openDialog(Context context, @LayoutRes int viewid, boolean cancelable, boolean isWidthScreen, int position, @StyleRes int style, @DrawableRes int drawableBg) {
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
                dialog.getWindow().getDecorView().setBackground(context.getResources().getDrawable(drawableBg));
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


    public interface EventProgress {

        void eventProgress(View view);

    }


}
