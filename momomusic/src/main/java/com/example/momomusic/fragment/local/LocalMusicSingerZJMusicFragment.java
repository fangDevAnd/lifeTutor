package com.example.momomusic.fragment.local;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.momomusic.R;
import com.example.momomusic.adapter.MyAdapter;
import com.example.momomusic.dao.DaoHelper;
import com.example.momomusic.dao.MusicDataDb;
import com.example.momomusic.exception.ParamNotBindException;
import com.example.momomusic.fragment.ParentFragment;
import com.example.momomusic.model.Music;
import com.example.momomusic.servie.PlayService;
import com.example.momomusic.servie.SystemSettingService;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Response;

import static com.example.momomusic.servie.LocalMusicIndexUtil.DEFAULT_VALUE;

/**
 * 这个界面的作用是用来显示专辑点进去的的显示效果
 */
public class LocalMusicSingerZJMusicFragment extends ParentFragment implements AdapterView.OnItemClickListener {


    /**
     * 过来的条件，传递的数据是一个Map
     */
    public static final String CONDITIONS = "conditions";

    /**
     * 当前查询的table的name
     */
    public static final String SOURCE = "source";


    private static final String TAG = "test";


    private List<Music> musics;


    @BindView(R.id.listView)
    ListView listView;

    private MyAdapter<Music> myAdapter;


    @Override

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        musics = new ArrayList<>();


//        album = getMyActivity().getBundle().getString(ALBUM);
//        if (album == null) {
//            try {
//                throw new ParamNotBindException("当前页面需要传递的参数没有传递" + getClass().getName());
//            } catch (ParamNotBindException e) {
//                e.printStackTrace();
//            }
//        } else {
//            musics = DataSupport.where("albumName=?", album).find(Music.class);
//        }


        HashMap<String, String[]> map = (HashMap<String, String[]>) getMyActivity().getBundle().getSerializable(CONDITIONS);
        String table = getMyActivity().getBundle().getString(SOURCE);
        if (map == null) {
            //代表没有过滤条件
            musics = MusicDataDb.getInstance(getContext()).queryBySql(table, null, null);
        } else {
            List<String> keys = new ArrayList<>(map.keySet());
            String[] values = map.get(keys.get(0));
            musics = MusicDataDb.getInstance(getContext()).queryBySql(table, keys.get(0), values);
        }

        /**
         *
         * 上面的实现较为死板，我们使用较为高级的写法，将该界面作为一个公用的LisstView的维持界面
         */


        for (int i = 0; i < musics.size(); i++) {
            Log.d(TAG, musics.toString());
        }


        myAdapter = new MyAdapter<Music>((ArrayList<Music>) musics, R.layout.listview_music_not_icon) {
            @Override
            public void bindView(ViewHolder holder, Music obj) {
                holder.setText(R.id.musicName, obj.getTitle());
                holder.setText(R.id.singerAndAlbumName, obj.getArtist() + " | " + obj.getAlbumName());
                holder.setOnClickListener(R.id.menu, (v) -> {
                    //点击事件的处理
                });
            }

            @Override
            public int getCount() {
                return musics.size();
            }
        };
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(this);


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_localmusic_singer_zj_music, null);
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
        Music music = musics.get(position);
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
