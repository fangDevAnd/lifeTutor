package com.example.momomusic.fragment.local;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.Toast;

import com.example.momomusic.R;
import com.example.momomusic.adapter.MyAdapter;
import com.example.momomusic.dao.MusicDataDb;
import com.example.momomusic.dialog.DialogCollect;
import com.example.momomusic.dialog.DialogTool;
import com.example.momomusic.exception.ParamNotBindException;
import com.example.momomusic.fragment.ModifyMusicInfoFragment;
import com.example.momomusic.fragment.ParentFragment;
import com.example.momomusic.model.Collect;
import com.example.momomusic.model.Music;
import com.example.momomusic.servie.AnimationControllService;
import com.example.momomusic.servie.LocalMusicIndexUtil;
import com.example.momomusic.servie.PlayService;
import com.example.momomusic.servie.PlayServiceBindService;
import com.example.momomusic.servie.RingtoneSetUtil;
import com.example.momomusic.servie.SystemSettingService;
import com.example.momomusic.tool.Tools;
import com.example.momomusic.tool.UiThread;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * 本地音乐的歌曲fragment,本身来说并不需要
 */
public class LocalMusicGQFragment extends ParentFragment implements AdapterView.OnItemClickListener {


    /**
     * 当前的路径
     */
    public static final String FIEL_PATH = "file_path";


    public static final Class DEFAULT_TABLE = Music.class;


    @BindView(R.id.listView)
    ListView listView;

    private MyAdapter<Music> musicMyAdapter;

    private List<Music> musics;

    static {
        LitePal.getDatabase();
    }

    private LocalMusicIndexUtil localMusicIndexUtil;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getMyActivity().getBundle();


//        if (bundle == null) {
//            //采用默认的加载机制
//            musics = DataSupport.findAll(DEFAULT_TABLE);
//        } else if (bundle != null) {//如果bundle不为空.同时filepath不为空
//            String filePath = bundle.getString(FIEL_PATH);
//            //规定了参数必须是Music的之类或者本身
//            Class table = (Class) bundle.getSerializable(TABLE);
//            if (filePath != null && table != null) {
//                //取出filepath.进行过滤
//                musics = DataSupport.where("dataUrl like ?", filePath + "%").find(table);
//            } else if (filePath == null && table != null) {//代表的是查询其他数据表，但是没有筛选条件
//                musics = DataSupport.findAll(table);
//            } else if (filePath != null && table == null) {
//                musics = DataSupport.where("dataUrl like ?", filePath + "%").find(DEFAULT_TABLE);
//            } else {//都不匹配的时候，代表的是创建了Bundle，但是没有绑定参数
//                musics = DataSupport.findAll(DEFAULT_TABLE);
//            }
//        }


        if (bundle == null) {
            //采用默认的加载机制
            musics = DataSupport.findAll(DEFAULT_TABLE);
        } else if (bundle != null) {//如果bundle

            String filePath = bundle.getString(FIEL_PATH);
            if (filePath != null) {
                musics = DataSupport.where("dataUrl like ?", filePath + "%").find(DEFAULT_TABLE);
            }
        }

        if (musics.size() == 0) {

            localMusicIndexUtil = LocalMusicIndexUtil.getInstance();
            localMusicIndexUtil.setMusicScaleListener(new LocalMusicIndexUtil.MusicScaleListener() {
                @Override
                public void scaling(Music music) {
                    music.save();
                    //这里需要注意的是不能添加相同的歌曲进去，会出现你问题
                    if (!musics.contains(music)) {
                        musics.add(music);
                    }
                }

                @Override
                public void scaleComplate() {
                    UiThread.getUiThread().post(new Runnable() {
                        @Override
                        public void run() {
                            musicMyAdapter.notifyDataSetChanged();
                        }
                    });
                }
            });
            localMusicIndexUtil.indexLocalMusicI(getActivity());
        }

        musicMyAdapter = new MyAdapter<Music>((ArrayList<Music>) musics, R.layout.listview_music_list) {
            @Override
            public void bindView(ViewHolder holder, Music obj) {
                holder.setText(R.id.musicName, obj.getTitle());
                holder.setText(R.id.singerAndAlbumName, obj.getArtist() + " | " + obj.getAlbumName());
                holder.setText(R.id.title, obj.getTitle());
                holder.setOnClickListener(R.id.menu, (v) -> {
                    openDialog(obj);
                });
            }

            @Override
            public int getCount() {
                return musics.size();
            }
        };

        listView.setAdapter(musicMyAdapter);
        listView.setLayoutAnimation(AnimationControllService.setLayoutAnim(R.anim.anim_item, 0.2f, LayoutAnimationController.ORDER_NORMAL, getContext()));
        listView.setOnItemClickListener(this);

    }


    /**
     * 打开一个菜单的弹出窗口
     *
     * @param obj
     */
    private void openDialog(Music obj) {
        /**
         * 1。弹出窗口
         * 2. 菜单包括
         *     下一首播放   ----->插入到当前的播放列表中，
         *     添加到歌单   ----->弹出选择歌单的列表
         *     收藏        -----> toast
         *     设为铃声     ----->需要和系统进行交互
         *     修改歌曲信息  ---->UpdateMusicInfoFragment
         *     删除         ---->数据库删除
         *
         *
         *
         */
        //弹出一个窗口,选泽播放列表
        DialogTool<String> dialogTool = new DialogTool<String>() {
            @Override
            public void bindView(DialogTool<String> d, Dialog dialog, String... t) {
                d.setClickListener(R.id.downOne, (v) -> {
                    //下一首播放的点击
                    List<Music> temp = new ArrayList<>();
                    temp.add(obj);
                    PlayServiceBindService.additionalData(getContext(), temp, true);
                });

                d.setClickListener(R.id.addMusicSheet, (v) -> {
                    //添加到歌单
                    List<Music> temp = new ArrayList<>();
                    temp.add(obj);
                    DialogCollect.openCollectDialog(getContext(), temp);
                });
                d.setClickListener(R.id.collect, (v) -> {
                    //收藏
                    /**
                     * 这个是添加到收藏夹，同上面的我的收藏不一样，需要注意
                     */

                    /**
                     * 收藏的歌单可以直接通过标志位来判断，但是其他的情况需要通过新建表来判断数据
                     */

                    obj.setCollect(true);
                    obj.save();

                    Toast.makeText(getContext(), "已添加到收藏列表", Toast.LENGTH_SHORT).show();
                });

                d.setClickListener(R.id.setRing, (v) -> {
                    //设置铃声
                    RingtoneSetUtil.setRingtoneImpl(obj, getContext());
                });

                d.setClickListener(R.id.updateMusicInfo, (v) -> {
                    //修改歌曲信息

                    Bundle bundle = new Bundle();
                    bundle.putParcelable(ModifyMusicInfoFragment.SOURCE, obj);
                    Tools.startActivity(getContext(), ModifyMusicInfoFragment.class, bundle);
                    return;
                });
                d.setClickListener(R.id.deleteMusic, (v) -> {
                    //删除音乐
                    DialogCollect.showWarnDialog("删除提示", "是否确定删除", getContext(), new DialogCollect.EnterProgress() {
                        @Override
                        public void onProgre(DialogInterface dialog, AlertDialog.Builder builder) {
                            obj.delete();
                            musics.clear();
                            musics.addAll(DataSupport.findAll(DEFAULT_TABLE));
                            musicMyAdapter.notifyDataSetChanged();
                        }
                    });
                });
                d.setClickListener(R.id.artist, (v) -> {
                    //歌手的点击
                    /**
                     * 进入到歌手的详情界面
                     */


                });
                d.setClickListener(R.id.album, (v) -> {
                    //专辑的点击
                });


            }
        };


        Dialog dialog = dialogTool.openDialog(getContext(), R.layout.dialog_music_menu, true,
                true, Gravity.BOTTOM, 0, R.drawable.corner_bg_white);
        dialog.show();//显示弹出菜单的抽屉栏

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_localmusic_gq, null);
        ButterKnife.bind(this, view);
        return view;
    }

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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Music music = (Music) musics.get(position);

        //绑定
        Intent intent = new Intent(getActivity(), PlayService.class);

        getActivity().bindService(intent, getMyActivity().conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                myBinder = (PlayService.MyBinder) service;
                myBinder.setDataSources(musics);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        }, Context.BIND_AUTO_CREATE);
        /**
         * 这里我们看出,startService通过这个方式直接进行交互,
         */
        intent.putExtra(PlayService.DATA, music.getDataUrl());
        intent.putExtra(PlayService.ACTION, PlayService.WITH_DATA_PLAY);
        getActivity().startService(intent);
    }
}

