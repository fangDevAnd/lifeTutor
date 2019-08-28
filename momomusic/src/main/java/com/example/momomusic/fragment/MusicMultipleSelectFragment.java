package com.example.momomusic.fragment;


import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.example.momomusic.R;
import com.example.momomusic.adapter.MyAdapter;
import com.example.momomusic.dao.MusicDataDb;
import com.example.momomusic.dialog.DialogCollect;
import com.example.momomusic.dialog.DialogTool;
import com.example.momomusic.model.Collect;
import com.example.momomusic.model.Music;
import com.example.momomusic.notification.NotificationTools;
import com.example.momomusic.servie.ColorSerivice;
import com.example.momomusic.servie.PlayService;
import com.example.momomusic.servie.PlayServiceBindService;
import com.example.momomusic.tool.Tools;
import com.orhanobut.logger.Logger;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Response;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 歌曲多选的界面实现
 * 这个界面可能需要复用
 */
public class MusicMultipleSelectFragment extends ParentFragment implements AdapterView.OnItemClickListener {


    private List<String> menuS;

    public static final String[] MENU = {
            "正在播放列表",
            "我收藏的单曲",
            "新建歌单"
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_music_multiple_select, null);
        ButterKnife.bind(this, view);

        //初始化菜单
        menuS = new ArrayList<>(Arrays.asList(MENU));

        return view;
    }

    private List<Music> musicList;


    @BindView(R.id.listView)
    ListView listView;

    private List<Music> selectedMusic;

    private MyAdapter<Music> myAdapter;

    @BindViews({R.id.addGeDan, R.id.del, R.id.shoucang, R.id.share})
    List<TextView> menus;

    /**
     * 代表的是选中的状态的保存
     */
    SparseArray<Boolean> selectStatus;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedMusic = new ArrayList<>();

        selectStatus = new SparseArray<>();

        musicList = DataSupport.findAll(Music.class);

        /**
         * 进行默认选中状态的初始化
         */
        for (int i = 0; i < musicList.size(); i++) {
            selectStatus.put(i, false);
        }

        myAdapter = new MyAdapter<Music>((ArrayList<Music>) musicList, R.layout.listview_multiple_music) {
            @Override
            public void bindView(ViewHolder holder, Music obj) {
                holder.setText(R.id.musicName, obj.getTitle());
                holder.setText(R.id.singerAndAlbumName, obj.getArtist() + " | " + obj.getAlbumName());
                holder.setText(R.id.title, obj.getTitle());
                holder.setTag(R.id.menu, new Boolean(false));
                holder.setText(R.id.one, holder.getItemPosition() + 1 + "");

                ViewGroup viewGroup = (ViewGroup) holder.getItemView();

                CheckBox checkBox = viewGroup.findViewById(R.id.menu);
                checkBox.setChecked(selectStatus.get(holder.getItemPosition()));

                Logger.d("当前的选中状态" + checkBox.isChecked());
            }

            @Override
            public int getCount() {
                return musicList.size();
            }
        };

        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(this);
    }


    /**
     * 弹出的窗口里面的listView的适配器
     */
//    ArrayAdapter<String> arrayAdapter;
    @OnClick({R.id.addGeDan, R.id.del, R.id.shoucang, R.id.share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addGeDan:
//                openCollectDialog();
                DialogCollect.openCollectDialog(getContext(), selectedMusic);


                break;
            case R.id.del:
                for (Music music : selectedMusic) {//直接从数据库中删除
                    music.delete();
                }
                musicList.clear();
                musicList.addAll(DataSupport.findAll(Music.class));//这里不能去更改指针的指向
                myAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();

                break;
            case R.id.shoucang:
                //在这里将addUrl的相关music里面的收藏属性更改为true,也就是更新

                /**
                 * 这个是添加到收藏夹，同上面的我的收藏不一样，需要注意
                 */

                /**
                 * 收藏的歌单可以直接通过标志位来判断，但是其他的情况需要通过新建表来判断数据
                 */

                for (Music music : musicList) {
                    music.setCollect(true);
                    music.save();//因为是已经保存的数据,就会执行更新
                }
                Toast.makeText(getContext(), "已添加到收藏列表", Toast.LENGTH_SHORT).show();

                //Notificcation消息处理
//                notification3("添加到收藏", "收藏音乐成功", "15");
                break;

            case R.id.share:
                /**
                 * 分享我们需要接入微信  支付宝 短信   钉钉  豆瓣   等 ，使用的是友盟社会化分享
                 *
                 *
                 */
                Intent intent = new Intent(Intent.ACTION_VIEW);
                startActivity(intent);
                break;
        }
    }

//    private void openCollectDialog() {
//
//        //弹出一个窗口,选泽播放列表
//        DialogTool<String> dialogTool = new DialogTool<String>() {
//            @Override
//            public void bindView(DialogTool<String> d, Dialog dialog, String... t) {
//                ViewGroup viewGroup = d.getView();
//                List<String> tables = MusicDataDb.getInstance(getContext()).queryNewMusicSheet();//查询当前的表
//                if (!menuS.containsAll(tables)) {
//                    menuS.addAll(tables);
//                }
//                arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, menuS);
//                ListView listView = viewGroup.findViewById(R.id.listView);
//                listView.setAdapter(arrayAdapter);
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                        switch (position) {
//                            case 0://添加到正在播放
//                                /**
//                                 * 添加到当前额播放源
//                                 */
//                                PlayServiceBindService.additionalData(getContext(), selectedMusic, false);
//                                Toast.makeText(getContext(), "添加到播放歌单", Toast.LENGTH_SHORT).show();
//                                dialog.cancel();
//                                break;
//                            case 1://添加我的收藏
//                                for (Music music : selectedMusic) {
//                                    /**
//                                     * 由于使用的数据库存在自动的对象判断，所以不需要我们进行再一次的对象重复添加判断
//                                     */
//                                    new Collect(music.getDataUrl()).save();
//                                }
//                                dialog.cancel();
//                                Toast.makeText(getContext(), "已被添加到收藏夹", Toast.LENGTH_SHORT).show();
//
//                                break;
//                            case 2://新建歌单
//                                //弹出窗口
//                                newMusicSheet(dialog);
//                                break;
//                            default:
//                                /**
//                                 * 默认情况下，需要获得当前的item的值，然后通过判断
//                                 * 这个title就是当前的表的名称
//                                 */
//                                String title = arrayAdapter.getItem(position);
//                                List<String> selectUrl = new ArrayList<>();
//                                for (int i = 0; i < selectedMusic.size(); i++) {
//                                    selectUrl.add(selectedMusic.get(i).getDataUrl());
//                                }
//                                MusicDataDb.getInstance(getContext()).insertData(title, selectUrl);
//                                dialog.cancel();
//                                break;
//                        }
//                    }
//                });
//            }
//        };
//
//        Dialog dialog = dialogTool.openDialog(getContext(), R.layout.dialog_add_to_music_dan, true,
//                true, Gravity.BOTTOM, 0, R.drawable.corner_bg_white);
//        dialog.show();//显示弹出菜单的抽屉栏
//    }


    @OnClick({R.id.cancel, R.id.allSelect})
    public void onClick1(View view) {

        switch (view.getId()) {
            case R.id.cancel:

                /**
                 * 重置状态
                 */
                /**
                 * 进行默认选中状态的初始化
                 */
                for (int i = 0; i < musicList.size(); i++) {
                    selectStatus.put(i, false);
                }

                selectedMusic.clear();

                myAdapter.notifyDataSetChanged();

                if (selectedMusic.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setMenuEnable(true);
                    }
                } else if (selectedMusic.size() == 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setMenuEnable(false);
                    }
                }

                break;

            case R.id.allSelect:
                for (int i = 0; i < musicList.size(); i++) {
                    selectStatus.put(i, true);
                }

                /**
                 * 取消之前选中的
                 */
                selectedMusic.clear();

                /**
                 * 添加所有的列表
                 */
                selectedMusic.addAll(musicList);

                myAdapter.notifyDataSetChanged();


                if (selectedMusic.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setMenuEnable(true);
                    }
                } else if (selectedMusic.size() == 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setMenuEnable(false);
                    }
                }
                break;
        }

    }


    /**
     * 新建歌单
     *
     * @param
     */
//    private void newMusicSheet(Dialog dialogParent) {
//
//        DialogTool<String> dialogTool = new DialogTool<String>() {
//            @Override
//            public void bindView(DialogTool<String> d, Dialog dialog, String... t) {
//                d.setClickListener(R.id.cancel, (v) -> {
//                    //取消的点击操作
//                    dialog.cancel();
//
//                });
//
//                d.setClickListener(R.id.submit, (v) -> {
//
//                    ViewGroup viewGroup = d.getView();
//                    EditText editText = viewGroup.findViewById(R.id.editMusicSheet);
//
//                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
//                        Toast.makeText(getContext(), "请输入歌单名称", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    if (menuS.contains(editText.getText().toString())) {
//                        Toast.makeText(getContext(), "该播放列表已经存在", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//
//                    //确定的点击操作
//                    MusicDataDb.getInstance(getContext()).createTable(editText.getText().toString());
//
//                    menuS.clear();
//                    menuS.addAll(new ArrayList<>(Arrays.asList(MENU)));
//                    menuS.addAll(MusicDataDb.getInstance(getContext()).queryNewMusicSheet());//查询当前的表
//
//                    arrayAdapter.notifyDataSetChanged();
//
//                    /**
//                     * 1,新建数据表
//                     * 2.刷新musicS
//                     * 3.需要刷新dialoagparent的数据
//                     * 4.取消当前的dailog的显示
//                     */
//                    dialog.cancel();
//
////                    notification3("添加收藏", "添加收藏成功", "13");
//
//                });
//
//            }
//        };
//
//        Dialog dialog = dialogTool.getDialog(getContext(), R.layout.dialog_new_music_sheet, null);
//        dialog.show();
//
//    }
    @Override
    protected void loadData() {

    }

    @Override
    public void onError(IOException e, String what) {

    }

    @Override
    public void onSucess(Response response, String what, String... backData) throws IOException {

    }

    @Override
    public Class getClassName() {
        return null;
    }


    /**
     * 选项中的点击操作，代表的是多选中是否被选中
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CheckBox checkBox = view.findViewById(R.id.menu);
//        checkBox.setChecked(!(Boolean) checkBox.getTag());

        if (selectStatus.get(position)) {//如果是选中状态
            selectStatus.put(position, false);
        } else {//如果不是选中状态
            selectStatus.put(position, true);
        }

        checkBox.setChecked(selectStatus.get(position));
        checkBox.setTag(checkBox.isChecked());
        Music music = myAdapter.getItem(position);
        if (checkBox.isChecked()) {
            selectedMusic.add(music);
        } else {
            selectedMusic.remove(music);
        }

        if (selectedMusic.size() > 0) {
            setMenuEnable(true);
        } else if (selectedMusic.size() == 0) {
            setMenuEnable(false);
        }
    }

    /**
     * 设置菜单是否可用
     *
     * @param b
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setMenuEnable(boolean b) {
        if (b) {

            for (TextView textView : menus) {
                textView.setEnabled(true);
                Tools.setDrawableColor(Color.BLACK, textView);
            }
        } else {
            for (TextView textView : menus) {
                textView.setEnabled(false);
                Tools.setDrawableColor(Color.DKGRAY, textView);
            }
        }
    }

//    private void notification3(String title, String content, String id) {
//
//        NotificationTools notificationTools = new NotificationTools() {
//            @Override
//            public void bindView(RemoteViews remoteViews, Notification.Builder builder) {
//                //当isConsume为false的时候，代表的是没有通过自定义视图，所以RemoteViews为null
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    builder.setChannelId("14");
//                }
//
//                builder.setContentTitle(title)
//                        .setContentText(content)
//                        .setAutoCancel(true)
//                        .setSmallIcon(R.drawable.rice);
////                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////                    NotificationChannel channel = new NotificationChannel(id, "消息通知", NotificationManager.IMPORTANCE_HIGH);
////
////                    AudioAttributes.Builder builder1 = new AudioAttributes.Builder();
////                    channel.setSound(Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "yujian.mp3")), builder1.build());
////                } else {
////                    builder.setSound(Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), "yujian.mp3")));
////                }
//            }
//        };
//
//        Notification notification = notificationTools.createNoti(getContext(), false);
//
//        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            notificationManager.createNotificationChannel(new NotificationChannel(id, "消息通知", NotificationManager.IMPORTANCE_HIGH));
//        }
//        notificationManager.notify(Integer.parseInt(id), notification);
//    }


}
